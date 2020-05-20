package com.itheima;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class Demo2 {

    @Autowired
    PersonService personService;

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(personService.insert());
    }

    @Test
    void fun1() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    void fun2() {
        User u = new User();
        u.setUsername("lajf");
        u.setAge(30);
        u.setPassword("123456");
        int insert = userMapper.insert(u);
        log.info("成功插入{}条数据，{}", insert, u);
    }

}
