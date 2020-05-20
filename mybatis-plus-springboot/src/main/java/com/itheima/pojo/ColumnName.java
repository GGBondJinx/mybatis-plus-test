package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GG Bond
 * @date 2020/3/25 19:54
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("column_name")
public class ColumnName {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String columnName;
    private Long tableId;

    public ColumnName(String columnName, Long tableId) {
        this.columnName = columnName;
        this.tableId = tableId;
    }
}
