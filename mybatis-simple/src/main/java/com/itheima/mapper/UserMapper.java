package com.itheima.mapper;

import com.itheima.pojo.User;

import java.util.List;

/**
 * @author GG Bond
 * @date 2020/3/11 22:33
 * @description
 */
public interface UserMapper {

    /**
     * 查询所有用户信息
     * @return 用户集合
     */
    List<User> findAll();
}
