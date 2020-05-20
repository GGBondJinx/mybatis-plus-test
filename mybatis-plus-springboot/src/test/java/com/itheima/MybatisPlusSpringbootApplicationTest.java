package com.itheima;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusSpringbootApplicationTest {

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
        for (User user : userList) {
            System.out.println(user);
        }
    }

}
