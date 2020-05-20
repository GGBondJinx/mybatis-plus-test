package com.itheima;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.Test;


/**
 * @author GG Bond
 * @date 2020/3/12 16:39
 * @description
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Demo1 {

    @Autowired
    UserMapper userMapper;

    @Test
    public void fun1() {
        log.info("begin");
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void fun2() {
        User u = new User();
        u.setName("张三");
        u.setAge(20);
        u.setUsername("zhangsan");
        u.setPassword("123456");
        u.setEmail("aa@qq.com");
        int insert = userMapper.insert(u);
        log.info("新增用户成功:{}，插入 {} 条数据", u, insert);
    }

    @Test
    public void funUpdate() {
        User u = new User();
        u.setAge(22);
        u.setUsername("tom");
        u.setId(1L);
        int i = userMapper.updateById(u);
//        userMapper.update();
        log.info("更新成功{},{}", u, i);
    }

    @Test
    public void batchUpdate() {
        User u = new User();
        u.setAge(22);
        u.setUsername("tom");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("id", 1);
        userMapper.update(u, wrapper);
    }

    @Test
    public void fun3() {
        QueryWrapper param = new QueryWrapper();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("age", 20);
        map.put("a", "a");
        param.allEq((k, v) -> {
                return "age".equals(k) || "name".equals(k);}, map);
        List list = userMapper.selectList(param);
        list.forEach(System.out::println);
    }
}
