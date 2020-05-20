package com.itheima.demo1;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GG Bond
 * @date 2020/5/13 21:13
 * @description
 */
public class Demo2 {
    public static void main(String[] args) {

        Set<User> set = new HashSet();
        User user = new User("tom", 22);
        User user2 = new User("tom", 22);
        System.out.println(set.add(user));
        System.out.println(set.add(user2));
        System.out.println(set);
    }
}
