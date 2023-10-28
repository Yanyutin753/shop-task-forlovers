## 安装jdk11（推荐） 和 mysql 5.7以上

## 将simpleDeploy里的文件拷贝到/www/wwwroot/webServer路径下，暂不支持修改别的路径

![image](https://github.com/Yanyutin753/shop-task-forlovers/assets/132346501/74ca8130-491e-4ed2-b6e5-eacd635dfb0d)

##  新建mysql数据表
![image](https://github.com/Yanyutin753/shop-task-forlovers/assets/132346501/fd7945d4-6d55-4e84-b293-09c36639c769)


- 数据表命名为webserver,用户名为webserver,密码自取（推荐强密码）

## 终端输入命令
```
注意替换spring.datasource.password=（你的webserver数据库密码）
nohup java -jar -Xmx1024M -Xms256M  /www/wwwroot/webServer/WebServer-0.0.1-SNAPSHOT.jar --spring.datasource.password=（你的webserver数据库密码） --spring.datasource.username=webserver> output.log 2>&1 &
```

## 启动成功后 开放防火墙端口8081 即可访问

## 初始注册密钥：小羊是帅哥
- 注册之后可以修改
- 记得一键三连，必须要给star⭐

