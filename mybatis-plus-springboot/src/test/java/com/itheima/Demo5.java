package com.itheima;

import com.itheima.mapper.User1Mapper;
import com.itheima.mapper.User2Mapper;
import com.itheima.mapper.User3Mapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User1;
import com.itheima.pojo.User2;
import com.itheima.pojo.User3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author GG Bond
 * @date 2020/4/30 21:19
 * @description 多线程查询多个表的数据
 */

@Slf4j
@SpringBootTest
public class Demo5 {

    @Autowired
    UserMapper userMapper;

    @Autowired
    User1Mapper user1Mapper;

    @Autowired
    User2Mapper user2Mapper;

    @Autowired
    User3Mapper user3Mapper;

    Map map = new HashMap<>(8);

    volatile Long parTime = 0L;
    volatile Long callTime = 0L;
    volatile Long runnTime = 0L;

    CountDownLatch countDownLatch = new CountDownLatch(3);

    ThreadPoolExecutor pool = new ThreadPoolExecutor(30,
            100,
            60L,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    @Test
    public void fun1() throws ExecutionException, InterruptedException {
//        runnableClient();
//        runnableClient();
//        runnableClient();
//        runnableClient();

        for (int i = 0; i < 10; i++) {
//            parallel();
//            callableClient();
            runnableClient();
        }
//        System.out.println("parTime: " + parTime / 10);
//        System.out.println("callTime: " + callTime / 10);
        System.out.println("runnTime: " + runnTime / 10);
    }

    @Test
    public void parallel() {
        long begin = System.currentTimeMillis();
        List<User1> user1 = user1Mapper.selectList(null);
        List<User2> user2 = user2Mapper.selectList(null);
        List<User3> user3 = user3Mapper.selectList(null);
        map.put("user1", user1);
        map.put("user2", user2);
        map.put("user3", user3);
        long end = System.currentTimeMillis();
        Long t = end - begin;
        this.parTime += t;
        System.out.println("parallel " + t);
    }

    private void callable() {
        try {
            Future<List<User1>> future1 = pool.submit(() -> user1Mapper.selectList(null));
            Future<List<User2>> future2 = pool.submit(() -> user2Mapper.selectList(null));
            Future<List<User3>> future3 = pool.submit(() -> user3Mapper.selectList(null));
            map.put("user1", future1.get());
            map.put("user2", future2.get());
            map.put("user3", future3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runnable() {
        try {
            pool.submit(() -> {
                List<User1> users = user1Mapper.selectList(null);
                map.put("user1", users);
                countDownLatch.countDown();
            });
            pool.submit(() -> {
                List<User2> users = user2Mapper.selectList(null);
                map.put("user2", users);
                countDownLatch.countDown();
            });
            pool.submit(() -> {
                List<User3> users = user3Mapper.selectList(null);
                map.put("user3", users);
                countDownLatch.countDown();
            });
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runnableClient() {
        long begin = System.currentTimeMillis();
        runnable();
        long end = System.currentTimeMillis();
        Long t = end - begin;
        this.runnTime += t;
        System.out.println("runnable " + t);
    }

    @Test
    public void callableClient() {
        long begin = System.currentTimeMillis();
        callable();
        long end = System.currentTimeMillis();
        Long t = end - begin;
        this.callTime += t;
        System.out.println("callable " + t);
    }

    @Test
    public void insert() {
        for (int i = 1; i <= 100000; i++) {
            User3 u3 = new User3();
            u3.setName3("tom3-" + i);
            u3.setAge3(3 + i);
            boolean insert = u3.insert();
            System.out.println(i + "数据插入" + insert);
        }
    }
}
