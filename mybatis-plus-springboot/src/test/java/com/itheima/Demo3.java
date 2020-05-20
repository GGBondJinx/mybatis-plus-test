package com.itheima;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mapper.ColumnDataMapper;
import com.itheima.mapper.ColumnNameMapper;
import com.itheima.mapper.TablenameMapper;
import com.itheima.pojo.ColumnData;
import com.itheima.pojo.ColumnName;
import com.itheima.pojo.Tablename;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author GG Bond
 * @date 2020/3/25 19:53
 * @description
 */
@Slf4j
@SpringBootTest
public class Demo3 {

    @Autowired
    TablenameMapper tablenameMapper;

    @Autowired
    ColumnNameMapper columnNameMapper;

    @Autowired
    ColumnDataMapper columnDataMapper;



    /**
     * 新增表和字段
     */
    @Test
    public void fun1() {
        String tabName = "学生表";
        Tablename tablename = new Tablename();
        tablename.setTbName(tabName);
        int i = tablenameMapper.insert(tablename);
        log.info("***************{}", i);

        if (i <= 0) {
            log.error("新增" + tabName + "失败");
            throw new RuntimeException("新增" + tabName + "失败");
        }
        log.info("新增" + tabName + "成功");

        // 新增字段
        List<String> columnNameList = Arrays.asList("姓名", "年龄", "性别");
        // String 为 列名 Long 为 所属表的 id，ColumnName 为字段名称
        BiFunction<String, Long, ColumnName> bf = ColumnName::new;

        // 循环字段名称集合  创建字段对象
        for (String s : columnNameList) {
            ColumnName col = bf.apply(s, tablename.getId());
            i = columnNameMapper.insert(col);
        }
        log.info("新增字段成功");
    }

    /**
     * 添加数据
     * 往已存在的学生表中的 姓名、年龄、性别字段插入数据
     */
    @Test
    public void fun2() {
        // 模拟页面传过来的数据 map 的 key 为往哪个字段插入（这些 key 是库中已存在的），key 为要插入的数据集合
        Map<String, List<String>> map = new HashMap(4);
        map.put("姓名", Arrays.asList("tom", "jack", "jerry", "rose"));
        map.put("年龄", Arrays.asList("20", "21", "22", "23"));
        map.put("性别", Arrays.asList("男", "男", "女", "女"));

        // 把数据放入两个集合中
        List<String> keyList = new ArrayList<>(4);
        List<List<String>> valueList = new ArrayList<>(4);
        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        // 1. 先查出学生表的 id
        QueryWrapper<Tablename> queryWrapper = new QueryWrapper();
        queryWrapper.eq("tb_name", "学生表");
        List<Tablename> tablenameList = tablenameMapper.selectList(queryWrapper);
        if (tablenameList.isEmpty()) {
            log.error("error");
            return;
        }
        Long tableId = tablenameList.get(0).getId();
        // 2. 根据学生表的 id 查出该表有哪些字段
        QueryWrapper<ColumnName> columnNameQueryWrapper = new QueryWrapper();
        columnNameQueryWrapper.eq("table_id", tableId);

        // 该map 的 key 为字段名称，value 为字段名称对应的字段 id
        Map<String, Long> keyMapAndKeyId = new HashMap(map.size());
        // 查出字段信息集合
        List<ColumnName> columnNameList = columnNameMapper.selectList(columnNameQueryWrapper);
        columnNameList.stream().peek(l -> keyMapAndKeyId.put(l.getColumnName(), l.getId())).collect(Collectors.toList());


        // 循环构建学生对象
        for (int i = 0; i < valueList.get(0).size(); i++) {
            String uuid = IdUtil.simpleUUID();
            // 该学生第一个字段信息
            for (int j = 0; j < keyList.size(); j++) {
                String s = keyList.get(j);
                // 获取该字段对应的 id
                Long id = keyMapAndKeyId.get(s);
                ColumnData columnData = new ColumnData();
                columnData.setColumnId(id);
                columnData.setUuid(uuid);
                columnData.setColumnData(valueList.get(j).get(i));
                columnDataMapper.insert(columnData);
            }
        }
    }

    /**
     * 查询学生表中的信息
     */
    @Test
    public void fun3() {
        // 模拟页面传过来的数据
        String table = "学生表";
        // 查询该表对应的 id
        QueryWrapper<Tablename> tablenameQueryWrapper = new QueryWrapper<>();
        tablenameQueryWrapper.eq("tb_name", table);
        List<Tablename> tablenameList = tablenameMapper.selectList(tablenameQueryWrapper);
        Tablename tablename = tablenameList.get(0);

        // 查询该表下所有字段
        QueryWrapper<ColumnName> columnNameQueryWrapper = new QueryWrapper<>();
        columnNameQueryWrapper.eq("table_id", tablename.getId());
        List<ColumnName> columnNameList = columnNameMapper.selectList(columnNameQueryWrapper);
        Map<Long, String> map = new HashMap<>(8);
        // 将该表下的字段名称和字段 id 映射到 map 中
        columnNameList.stream().peek(l -> map.put(l.getId(), l.getColumnName())).collect(Collectors.toList());

        QueryWrapper<ColumnData> columnDataQueryWrapper = new QueryWrapper<>();
        List<ColumnData> columnData = columnDataMapper.selectList(columnDataQueryWrapper);
        // 数据按照 uuid 分组，标明为一个学生对象
        Map<String, List<ColumnData>> collect = columnData.stream().collect(Collectors.groupingBy(ColumnData::getUuid));

        // 外层 List 装的是学生对象集合 内层 List 是拼接的学生信息字符串
        List<List<String>> resultList = new ArrayList<>(4);
        for (Map.Entry<String, List<ColumnData>> entry : collect.entrySet()) {
            // 一个 list 是一个学生对象
            List<ColumnData> value = entry.getValue();
            StringBuilder sb = new StringBuilder();
            for (ColumnData data : value) {
                Long columnId = data.getColumnId();
                // 根据 columnId 获取对应的字段名称
                String columnName = map.get(columnId);
                // 拿到值
                String columnValue = data.getColumnData();
                sb.append(columnName + " : " + columnValue + "\t");
            }
            // 把该对象封装成一个 List 再放入 resultList
            resultList.add(Arrays.asList(sb.toString()));
        }
        System.out.println("****************************************************************************************************");
        for (List<String> strings : resultList) {
            System.out.println(strings);
        }
    }
}
