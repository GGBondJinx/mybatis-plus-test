package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GG Bond
 * @date 2020/3/11 22:22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user1")
public class User1 extends Model<User1> {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name1;
    private Integer age1;
}
