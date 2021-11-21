package com.example.demo.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jessin.practice.dubbo.model.User;
import com.jessin.practice.dubbo.model.UserParam;
import com.jessin.practice.dubbo.processor.Reference;
import com.jessin.practice.dubbo.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 泛化调用
 *
 * @Author: jessin
 * @Date: 19-8-3 下午4:25
 */
@RestController
public class HelloController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Reference(group = "myGroup")
    private UserService userService;

    /**
     * http://localhost:9999/practice/hello
     *
     * @return
     */
    @RequestMapping("/hello")
    public User hello(UserParam userParam, @RequestParam int type) {
        log.info("test miniDubbo param");
        if (type == 1) {
            return userService.getUser();
        } else if (type == 2) {
            return userService.getUser(userParam);
        } else if (type == 3) {
            return userService.getUser(3);
        } else if (type == 4) {
            return userService.getUser(userParam, 3, "hello", new int[]{1, 2, 3}, Lists.newArrayList(3, 5, 7));
        } else if (type == 5) {
            UserParam user2 = new UserParam();
            user2.setId(1132);
            user2.setName("hello");
            UserParam user3 = new UserParam();
            user3.setId(353);
            user3.setName("world");

            Map<String, UserParam> map = Maps.newHashMap();
            map.put("key1", user2);
            HashMap<String, UserParam> hashMap = Maps.newHashMap();
            hashMap.put("key2", user3);
            return userService.getUser(Lists.newArrayList(user2, user3),
                    Lists.newArrayList(userParam), map, hashMap);
        } else {
            UserParam user2 = new UserParam();
            user2.setId(1132);
            user2.setName("hello");
            UserParam user3 = new UserParam();
            user3.setId(353);
            user3.setName("world");
            Map<String, UserParam> map = Maps.newHashMap();
            map.put("key1", user2);
            HashMap<String, UserParam> hashMap = Maps.newHashMap();
            hashMap.put("key2", user3);
            return userService.getUser(Lists.newArrayList(userParam, user2, user3), map);
        }
    }
}
