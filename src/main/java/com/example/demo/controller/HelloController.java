package com.example.demo.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jessin.practice.dubbo.model.AppInfo;
import com.jessin.practice.dubbo.model.BizType;
import com.jessin.practice.dubbo.model.DomainInfo;
import com.jessin.practice.dubbo.model.Result;
import com.jessin.practice.dubbo.model.User;
import com.jessin.practice.dubbo.model.UserParam;
import com.jessin.practice.dubbo.spring.processor.Reference;
import com.jessin.practice.dubbo.service.DomainService;
import com.jessin.practice.dubbo.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    @Reference(group = "myGroup", version = "1.0.2")
    private DomainService domainService;

    /**
     * http://localhost:9999/practice/hello?type=7
     */
    @RequestMapping("/hello")
    public Object hello(UserParam userParam, @RequestParam int type) {
        log.info("test miniDubbo param");
        if (type == 0) {
            return userService.getException();
        } else if (type == 1) {
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
        } else if (type == 6) {
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
        } else if (type == 7) {
            AppInfo<User> userAppInfo = new AppInfo<>();
            userAppInfo.setAppId(1233);
            userAppInfo.setAppName("laoge");
            User user = new User();
            userAppInfo.setDetail(user);
            user.setId(123L);
            user.setNote("啦啦啦");
            Map<String, User> ret = userService.getReturn(userAppInfo);
            if (ret != null && ret.size() > 0) {
                ret.forEach((key, value) -> {
                    log.info("age is {}", value.getAge());
                });
            }
            return ret;
        } else if (type == 8) {
            AppInfo<User> userAppInfo = new AppInfo<>();
            userAppInfo.setAppId(1233);
            userAppInfo.setAppName("laoge");
            User user = new User();
            userAppInfo.setDetail(user);
            user.setId(128L);
            user.setNote("bilibili");
            Map<String, Map<String, User>> ret = userService.getReturn2(userAppInfo);
            if (ret != null && ret.size() > 0) {
                ret.forEach((key, value) -> {
                    log.info("key is {} age is {}", key, value);
                    if (value != null && value.size() > 0) {
                        value.forEach((subKey, subValue) -> {
                            log.info("key is {} age is {}", subKey, subValue.getAge());
                        });
                    }
                });
            }
            return ret;
        } else if (type == 9) {
            AppInfo<User> userAppInfo1 = new AppInfo<>();
            userAppInfo1.setAppId(1233);
            userAppInfo1.setAppName("laoge");
            User user1 = new User();
            userAppInfo1.setDetail(user1);
            user1.setId(128L);
            user1.setNote("bilibili");

            AppInfo<User> userAppInfo2 = new AppInfo<>();
            userAppInfo2.setAppId(1234);
            userAppInfo2.setAppName("xiaomi");
            User user2 = new User();
            userAppInfo2.setDetail(user2);
            user2.setId(332L);
            user2.setNote("azhan");

            Map<String, List<User>> ret = userService.getReturn3(new AppInfo[]{userAppInfo1, userAppInfo2});
            if (ret != null && ret.size() > 0) {
                ret.forEach((key, value) -> {
                    log.info("key is {} age is {}", key, value);
                    if (value != null && value.size() > 0) {
                        value.forEach((subValue) -> {
                            log.info("age is {}", subValue.getAge());
                        });
                    }
                });
            }
            return ret;

        } else if (type == 10) {
            UserParam user2 = new UserParam();
            user2.setId(1132);
            user2.setName("hello");
            UserParam user3 = new UserParam();
            user3.setId(353);
            user3.setName("world");
            List<UserParam> list1 = Lists.newArrayList(user2, user3);
            UserParam user4 = new UserParam();
            user4.setId(305);
            user4.setName("sdgsd");
            List<UserParam> list2 = Lists.newArrayList(user4);
            List<List<UserParam>> input = Lists.newArrayList(list1, list2);
            Result ret = userService.getReturn4(input);
            if (ret != null) {
                log.info("data: {}, code:{}", ret.getData(), ret.getCode());
            }
            return ret;
        } else if (type == 11) {
            UserParam user2 = new UserParam();
            user2.setId(1132);
            user2.setName("hello");
            UserParam user3 = new UserParam();
            user3.setId(353);
            user3.setName("world");
            ArrayList<UserParam> list1 = Lists.newArrayList(user2, user3);
            UserParam user4 = new UserParam();
            user4.setId(305);
            user4.setName("sdgsd");
            ArrayList<UserParam> list2 = Lists.newArrayList(user4);
            List<ArrayList<UserParam>> input = Lists.newArrayList(list1, list2);
            Result<User> ret = userService.getReturn5(input);
            if (ret != null) {
                log.info("data: {}, code:{}", ret.getData(), ret.getCode());
                if (ret.getData() != null) {
                    log.info("age: {}, code:{}", ret.getData().getAge(), ret.getCode());
                }
            }
            return ret;
        } else if (type == 12) {
            AppInfo<User> userAppInfo1 = new AppInfo<>();
            userAppInfo1.setAppId(1233);
            userAppInfo1.setAppName("laoge");
            User user1 = new User();
            userAppInfo1.setDetail(user1);
            user1.setId(128L);
            user1.setNote("bilibili");
            AppInfo<User> userAppInfo2 = new AppInfo<>();
            userAppInfo2.setAppId(1234);
            userAppInfo2.setAppName("xiaomi");
            User user2 = new User();
            userAppInfo2.setDetail(user2);
            user2.setId(332L);
            user2.setNote("azhan");
            List<AppInfo<User>> list = Lists.newArrayList();
            list.add(userAppInfo1);
            list.add(userAppInfo2);
            Result<User> ret = userService.getReturn6(list, BizType.FLAGSHIP);
            if (ret != null) {
                log.info("age is {}", ret.getData().getAge());
            }
            return ret;
        } else if (type == 13) {
            CompletableFuture completableFuture = userService.getReturn7(13);
            try {
                return completableFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else if (type == 14) {
            CompletableFuture<User> completableFuture = userService.getReturn8(14);
            try {
                User user = completableFuture.get();
                log.info("user age:{}, note:{}", user.getAge(), user.getNote());
                return user;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else if (type == 15) {
            CompletableFuture<Result<User>> completableFuture = userService.getReturn9(14);
            try {
                Result<User> userResult = completableFuture.get();
                User user = userResult.getData();
                log.info("code:{}, user age:{}, note:{}",
                        userResult.getCode(), user.getAge(), user.getNote());
                return userResult;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else if (type == 16) {
            CompletableFuture<Result<User>> completableFuture = userService.getReturnTimeout(14);
            try {
                Result<User> userResult = completableFuture.get();
                User user = userResult.getData();
                log.info("code:{}, user age:{}, note:{}",
                        userResult.getCode(), user.getAge(), user.getNote());
                return userResult;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * http://localhost:9999/practice/domain?id=321&name=laosiji
     */
    @RequestMapping("/domain")
    public DomainInfo domain(UserParam userParam) {
        return domainService.queryAssociatedDomain(userParam);
    }
}
