package com.itheima.controller;

import com.itheima.pojo.Order;
import com.itheima.pojo.User;
import com.itheima.pojo.Vo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author GG Bond
 * @date 2020/3/16 14:27
 * @description
 */
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("hello")
    public Object hello(@RequestBody Map map) {
        Vo v1 = new Vo();
        User u = new User();
        Order o = new Order();
        u.setId(1L);
        u.setUsername("tom");
        o.setOrderId("1");
        o.setOrderName("苹果");
        v1.setUser(u);
        v1.setOrder(o);
        return v1;
    }

    @RequestMapping("insert")
    public Object insert(@RequestBody User u, String token) {
        return u;
    }

    @RequestMapping("getMap")
    public Object getMap() {
        List list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap();
        Map<String, Object> map2 = new HashMap();
        Map<String, Object> map3 = new HashMap();

        map1.put("name", "tom");
        map1.put("age", "20");
        map2.put("name", "tom");
        map2.put("age", "20");
        map3.put("name", "tom");
        map3.put("age", "20");
        List<Map<String, Object>> maps = Arrays.asList(map1, map2, map3);
        return maps;
    }
}
