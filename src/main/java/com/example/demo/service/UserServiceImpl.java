package com.example.demo.service;

import com.jessin.practice.dubbo.model.User;
import com.jessin.practice.dubbo.model.UserParam;
import com.jessin.practice.dubbo.processor.Service;
import com.jessin.practice.dubbo.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jessin
 * @Date: 19-11-27 下午11:33
 */
@Service(group="myGroup")
public class UserServiceImpl implements UserService {
    @Override
    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("小明");
        user.setAge(10);
        user.setNote("测试无参miniDubbo");
        return user;
    }

    @Override
    public User getUser(UserParam userParam) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带参数UserParam");
        return user;
    }

    @Override
    public User getUser(int age) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带参数int");
        return user;

    }

    @Override
    public User getUser(UserParam userParam, int type, String note, int[] ages, List<Integer> list) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带多个参数");
        return user;
    }

    @Override
    public User getUser(List<UserParam> list, ArrayList<UserParam> arrayList, Map<String, UserParam> map,
            HashMap<String, UserParam> hashMap) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带list/map泛型参数");
        return user;
    }

    @Override
    public User getUser(List list, Map userParamMap) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带list/map无参");
        return user;

    }
}
