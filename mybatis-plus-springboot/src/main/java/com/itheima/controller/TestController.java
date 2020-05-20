package com.itheima.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GG Bond
 * @date 2020/4/27 17:06
 * @description
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }
}
