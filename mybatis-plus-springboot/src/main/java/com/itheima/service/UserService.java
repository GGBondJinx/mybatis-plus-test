package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.User;

/**
 * @author GG Bond
 * @date 2020/3/12 19:22
 * @description
 */
public interface UserService extends IService<User> {

    String insert();
}
