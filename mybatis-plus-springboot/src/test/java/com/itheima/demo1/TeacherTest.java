package com.itheima.demo1;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author GG Bond
 * @date 2020/5/13 20:19
 * @description
 */
public class TeacherTest {

    public static void main(String[] args) {
        Consumer<User> con = a -> System.out.println(a);
        Supplier<User> supplier = () -> new User("tom", 22);
        Supplier<User> supplier1 = User::new;
        Function<User, User> function = u -> {
            User us = null;
            return us;
        };

        Predicate<User> predicate = u -> u.getAge() > 20;

        User user = supplier.get();
        boolean test = predicate.test(user);
        System.out.println(test);


        User apply = function.apply(user);
        apply.setAge(10);
        boolean test1 = predicate.test(apply);
        System.out.println(test1);

    }

}

class AA implements Consumer<User> {

    @Override
    public void accept(User o) {
        System.out.println(o);
    }
}