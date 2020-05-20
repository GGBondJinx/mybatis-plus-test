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
@TableName("user2")
public class User2 extends Model<User2> {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name2;
    private Integer age2;
}
