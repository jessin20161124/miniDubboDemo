package com.example.demo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jessin.practice.dubbo.model.AppInfo;
import com.jessin.practice.dubbo.model.BizType;
import com.jessin.practice.dubbo.model.Result;
import com.jessin.practice.dubbo.model.User;
import com.jessin.practice.dubbo.model.UserParam;
import com.jessin.practice.dubbo.processor.Service;
import com.jessin.practice.dubbo.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jessin
 * @Date: 19-11-27 下午11:33
 */
@Service(group="myGroup")
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public User getException() {
        throw new RuntimeException("woshi");
    }

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
        user.setId((long) userParam.getId());
        user.setName(userParam.getName());
        user.setAge(11);
        user.setNote("测试带参数UserParam");
        return user;
    }

    @Override
    public User getUser(int age) {
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(age);
        user.setNote("测试带参数int");
        return user;

    }

    @Override
    public User getUser(UserParam userParam, int type, String note, int[] ages, List<Integer> list) {
        User user = new User();
        user.setId((long) userParam.getId());
        user.setName(userParam.getName());
        user.setAge(11);
        user.setNote("测试带多个参数");
        return user;
    }

    @Override
    public User getUser(List<UserParam> list, ArrayList<UserParam> arrayList, Map<String, UserParam> map,
            HashMap<String, UserParam> hashMap) {
        list.forEach(a -> {
            log.info("id:{}, name:{}", a.getId(), a.getName());
        });
        arrayList.forEach(a -> {
            log.info("id:{}, name:{}", a.getId(), a.getName());
        });
        map.forEach((a, b) -> {
            log.info("key:{},id:{}, name:{}", a, b.getId(), b.getName());
        });
        hashMap.forEach((a, b) -> {
            log.info("key:{},id:{}, name:{}", a, b.getId(), b.getName());
        });
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带list/map泛型参数");
        return user;
    }

    @Override
    public User getUser(List list, Map userParamMap) {
        list.forEach(a -> {
            log.info("ele:{}", a);
        });
        userParamMap.forEach((a, b) -> {
            log.info("ele:{},{}", a, b);
        });
        User user = new User();
        user.setId(2L);
        user.setName("小红");
        user.setAge(11);
        user.setNote("测试带list/map无参");
        return user;
    }

    @Override
    public Map<String, User> getReturn(AppInfo<User> appInfo) {
        User user = new User();
        user.setId((long)appInfo.getAppId());
        user.setName(appInfo.getAppName());
        user.setAge(11);
        user.setNote(appInfo.getDetail() != null ? appInfo.getDetail().getNote() : "none");
        Map<String, User> res = Maps.newHashMap();
        res.put("a", user);
        res.put("b", appInfo.getDetail());
        return res;
    }

    @Override
    public Map<String, Map<String, User>> getReturn2(AppInfo appInfo) {
        User user = new User();
        user.setId((long)appInfo.getAppId());
        user.setName(appInfo.getAppName());
        user.setAge(12);
        user.setNote("getReturn2");
        Map<String, User> inner = Maps.newHashMap();
        inner.put("a", user);

        Map<String, Map<String, User>> outer = Maps.newHashMap();
        outer.put("out", inner);
        return outer;
    }

    /**
     * 返回带泛型的复杂类型
     * @param appInfos
     * @return
     */
    @Override
    public Map<String, List<User>> getReturn3(AppInfo<User>[] appInfos) {
        List<User> list = Lists.newArrayList();
        User user = new User();
        if (appInfos != null && appInfos.length > 0) {
            for (int i = 0; i < appInfos.length; i++) {
                if (appInfos[i].getDetail() != null) {
                    list.add(appInfos[i].getDetail());
                }
            }
            user.setId((long)appInfos[0].getAppId());
            user.setName(appInfos[0].getAppName());
            if (appInfos[0].getDetail() != null) {
                user.setNote(appInfos[0].getDetail().getNote());
            }
        } else {
            user.setNote("getReturn3");
        }
        user.setAge(13);
        list.add(user);
        Map<String, List<User>> inner = Maps.newHashMap();
        inner.put("a", list);
        return inner;
    }

    @Override
    public Result getReturn4(List<List<UserParam>> list) {
        List<String> userNameList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i).size() == 0) {
                    continue;
                }
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (list.get(i).get(j) != null) {
                        userNameList.add(list.get(i).get(j).getName());
                    }
                }
            }
        }
        Result<List<String>> result = new Result<>();
        result.setCode(1);
        result.setData(userNameList);
        result.setErrorMsg("getReturn4");
        return result;
    }

    @Override
    public Result<User> getReturn5(List<ArrayList<UserParam>> list) {
        List<String> userNameList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i).size() == 0) {
                    continue;
                }
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (list.get(i).get(j) != null) {
                        userNameList.add(list.get(i).get(j).getName());
                    }
                }
            }
        }
        User user = new User();
        user.setNote("getReturn5");
        Result<User> result = new Result<>();
        result.setCode(1);
        result.setData(user);
        result.setErrorMsg("aa");
        return result;
    }

    @Override
    public Result<User> getReturn6(List<AppInfo<User>> list, BizType bizType) {
        List<User> data = Lists.newArrayList();
        User user = new User();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDetail() != null) {
                    data.add(list.get(i).getDetail());
                }
            }
            user.setId((long)list.get(0).getAppId());
            user.setName(list.get(0).getAppName());
            if (list.get(0).getDetail() != null) {
                user.setNote(list.get(0).getDetail().getNote());
            }
        } else {
            user.setNote("getReturn6");
        }
        user.setAge(bizType.getCode());
        data.add(user);
        Result<User> result = new Result<>();
        result.setCode(1);
        result.setData(user);
        result.setErrorMsg(bizType.getDesc());
        return result;
    }
}
