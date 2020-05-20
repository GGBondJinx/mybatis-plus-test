package com.itheima;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author GG Bond
 * @date 2020/4/24 12:56
 * @description
 */
@Slf4j
@SpringBootTest
public class Demo4 {
    @Autowired
    UserMapper userMapper;

    @Test
    public void fun1() {
        log.info("begin");
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
