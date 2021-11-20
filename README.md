#### 启动步骤

##### 1. 启动zk

##### 2. 部署api到本地仓库：[api](https://github.com/jessin20161124/api)，部署miniDubbo到本地仓库：[miniDubbo](https://github.com/jessin20161124/miniDubboOpen)
```
mvn install
```

##### 3. 修改本demo yaml中zk地址和扫描的服务端路径（只在角色为服务端生效）
```
mini-dubbo:
  package-path: com.example.demo
  registry: "your_zk_ip:2181"

```

##### 4. 编译demo
```
mvn package
```

##### 5. 开启两个服务端

> server.port=9997，指定http端口

> public.ip=1.15.130.58，可以指定当前注册到zk上的公网地址，如果用的是云主机的话，可以在云端部署两台服务实例，在本地触发调用。

> mini-dubbo.type=server，表示角色是服务端

> catalina.base=xxx，设置的是日志目录，改为你的即可。
```
 默认9999 http端口，20880 miniDubbo端口
 java -Dmini-dubbo.type=server -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat1 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
```

```
 默认9998 http端口，20881 miniDubbo端口
 java -Dmini-dubbo.type=server -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat2 -Dserver.port=9998 -Dmini-dubbo.port=20881 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
```

##### 6. 本地开启客户端进行调用
```
 java -Dmini-dubbo.type=client -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat3  -Dserver.port=9997 -jar target/demo-0.0.1-SNAPSHOT.jar

```

- 通过客户端restapi触发miniDubbo调用，可以挂掉一台服务，两台服务，看看效果，并通过type调用不同的api方法
```
http://localhost:9997/practice/helloParam
```


#### 博客参考：
[简易版dubbo实现](https://blog.csdn.net/ac_dao_di/article/details/121445493)


#### 更多精彩样例，请关注公众号：
![扫一扫](https://raw.githubusercontent.com/jessin20161124/springboot-demo/main/scan.png)


