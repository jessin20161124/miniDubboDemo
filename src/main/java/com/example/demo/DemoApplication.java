package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mvn spring-boot:run -Dspring-boot.run.profiles=provider
 * mvn spring-boot:run -Dspring-boot.run.profiles=consumer -Dspring-boot.run.jvmArguments="-Dserver.port=8082"
 * mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8082 -Dmini-dubbo.type=client"
 * java -jar demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=provider
 * zkCli.sh -server 1.15.130.58:2181
 *
 *
 *
 * 默认9999，20880端口
 * java -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat1 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
 *
 * java -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat2 -Dserver.port=9998 -Dmini-dubbo.port=20881 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
//        UserParam user2 = new UserParam();
//        user2.setId(1132);
//        user2.setName("hello");
//        UserParam user3 = new UserParam();
//        user3.setId(353);
//        user3.setName("world");
//        Map<UserParam, UserParam> map = Maps.newHashMap();
//        map.put(user3, user2);
//        HashMap<UserParam, UserParam> hashMap = Maps.newHashMap();
//        hashMap.put(user2, user3);
//        String value = JSON.toJSONString(hashMap);
//        JSONObject ma2p = JSON.parseObject(value);
//        System.out.println(int.class.isPrimitive());
//        System.out.println(Integer.class.isPrimitive());
//        System.out.println(ma2p.keySet());
//        System.out.println(JSON.parseObject(value).toJavaObject(HashMap.class));
          SpringApplication.run(DemoApplication.class, args);
    }

//    @Profile("provider")
//    @Bean
//    public ServiceBeanPostProcessor serviceBeanPostProcessor() {
//        return new ServiceBeanPostProcessor("com.example.demo");
//    }
//
//    @Profile("consumer")
//    @Bean
//    public ReferenceBeanPostProcessor referenceBeanPostProcessor() {
//        return new ReferenceBeanPostProcessor();
//    }
}
