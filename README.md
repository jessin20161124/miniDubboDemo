### 1. 启动步骤

#### 1.1 启动zk

&nbsp;&nbsp;&nbsp;&nbsp;参考[zk安装](https://blog.csdn.net/ac_dao_di/article/details/54882670)

&nbsp;&nbsp;&nbsp;&nbsp;博主的zk部署到云主机上，需保证2181端口已经启动：
```
ubuntu@VM-0-14-ubuntu:~$ lsof -i:2181
COMMAND   PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
java     3904 ubuntu   27u  IPv6 545176752      0t0  TCP VM-0-14-ubuntu:35306->1.15.130.58:2181 (ESTABLISHED)
java    26361 ubuntu   45u  IPv6 452967153      0t0  TCP *:2181 (LISTEN)
java    26361 ubuntu   49u  IPv6 545176753      0t0  TCP VM-0-14-ubuntu:2181->1.15.130.58:35306 (ESTABLISHED)
```

#### 1.2 部署api和miniDubbo到本地仓库

&nbsp;&nbsp;&nbsp;&nbsp;部署api到本地仓库：[api](https://github.com/jessin20161124/api)，部署miniDubbo到本地仓库：[miniDubbo](https://github.com/jessin20161124/miniDubboOpen)
```
mvn install
```

#### 1.3 在demo yaml配置miniDubbo。
&nbsp;&nbsp;&nbsp;&nbsp;配置zk地址和扫描的服务端路径（只在角色为服务端生效）
```
mini-dubbo:
  package-path: "@Service注解所在包"
  registry: "your_zk_ip:2181"
```

#### 1.4 编译demo为可执行jar
```
mvn package
```

#### 1.5 开启两个服务端

&nbsp;&nbsp;&nbsp;&nbsp;这两个服务端都部署在云服务器上，参数说明：
> server.port=9997，指定http端口

> public.ip=1.15.130.58，可以指定当前注册到zk上的公网地址，如果用的是云主机的话，可以在云端部署两台服务实例，以便在本地能进行服务发现和调用。

> mini-dubbo.type=server，表示角色是服务端

> catalina.base=xxx，设置的是日志目录，改为你的即可。

&nbsp;&nbsp;&nbsp;&nbsp;启动第一个provider实例： 默认9999 http端口，20880 miniDubbo端口
```
 java -Dmini-dubbo.type=server -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat1 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/77d06b23dcbe40d4b6ba62a7a27afecb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

&nbsp;&nbsp;&nbsp;&nbsp;启动第二个provider实例：  默认9998 http端口，20881 miniDubbo端口


```
 java -Dmini-dubbo.type=server -Dcatalina.base=/home/ubuntu/program/java/miniDubboDemo/tomcat2 -Dserver.port=9998 -Dmini-dubbo.port=20881 -Dpublic.ip=1.15.130.58  -jar target/demo-0.0.1-SNAPSHOT.jar
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/654dee79e2fc498189fda23acc08a0ef.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


#### 1.6 本地开启客户端进行调用
&nbsp;&nbsp;&nbsp;&nbsp;在本地启动客户端，并与两个provider建立连接：
```
java -Dmini-dubbo.type=client -Dcatalina.base=./tomcat3  -Dserver.port=9997 -jar target/demo-0.0.1-SNAPSHOT.jar```
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/e0ab1506e7294c6dba171134664b7544.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


&nbsp;&nbsp;&nbsp;&nbsp;通过客户端restapi触发miniDubbo调用，可以挂掉一台服务，两台服务，看看效果，并通过type调用不同的api方法
```
http://localhost:9997/practice/hello?type=2
```

&nbsp;&nbsp;&nbsp;&nbsp;得到结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f00c2dd75f354a39b0b85915a3dbbd4a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


&nbsp;&nbsp;&nbsp;&nbsp;客户端发起请求，可以看到调用的是20881这个服务：
![在这里插入图片描述](https://img-blog.csdnimg.cn/797086088d1b47b8a14131cd94dd8901.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)



&nbsp;&nbsp;&nbsp;&nbsp;服务端返回结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/e1c3b41fb84442f98954f69c9ca38785.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
&nbsp;&nbsp;&nbsp;&nbsp;上面将20881销毁后，再次调用客户端发起请求，自动转移到20880这个服务：
![在这里插入图片描述](https://img-blog.csdnimg.cn/721eb1bc3e00447799aa69b14631f94e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
&nbsp;&nbsp;&nbsp;&nbsp;将20880也销毁后，再次调用客户端发起请求，直接抛出no provider异常：
![在这里插入图片描述](https://img-blog.csdnimg.cn/693a6f87393b41f4a7b1de886171b231.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


### 2. 博客参考：
[简易版dubbo实现](https://blog.csdn.net/ac_dao_di/article/details/121445493)


### 3. 更多精彩样例，请关注公众号：
![扫一扫](https://img-blog.csdnimg.cn/e021faa547534e0080356b65d995b6f8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAYWNfZGFvX2Rp,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


