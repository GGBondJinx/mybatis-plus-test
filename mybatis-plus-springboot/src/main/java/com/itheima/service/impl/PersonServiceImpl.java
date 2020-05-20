package com.itheima.service.impl;

import com.itheima.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author GG Bond
 * @date 2020/3/12 19:24
 * @description
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public String insert() {
        log.info("新增数据成功");
        return "success";
    }
}
