## 不接受白嫖，使用请点star⭐
## 安装jdk11（推荐） 和 mysql 5.7以上

```
#先拿到管理员权限
sudo su -
#提示你输入密码进行确认。输入密码并按照提示完成验证。

#安装 OpenJDK 11：
sudo apt install openjdk-11-jdk
安装完成后，可以通过运行以下命令来验证 JDK 安装：
java -version

在终端中运行以下命令来安装 MySQL 5.7：
sudo apt install mysql-server-5.7
安装完成后，可以通过运行以下命令来验证 MySQL 安装：
mysql --version

```
- 或者宝塔选择装java和mysql就行（推荐）

# 直接下载压缩包到桌面
<img width="751" alt="image" src="https://github.com/Yanyutin753/shop-task-forlovers/assets/132346501/b6974218-8be3-4d09-98ac-e8d9d5971677">

## 将解压之后的simpleDeploy压缩包里的文件拷贝到/www/wwwroot/webServer路径下，暂不支持修改别的路径

![image](https://github.com/Yanyutin753/shop-task-forlovers/assets/132346501/74ca8130-491e-4ed2-b6e5-eacd635dfb0d)

##  新建mysql数据表
![image](https://github.com/Yanyutin753/shop-task-forlovers/assets/132346501/fd7945d4-6d55-4e84-b293-09c36639c769)


- 数据表命名为webserver,用户名为webserver,密码自取（推荐强密码）

## 终端输入命令
```
#需要拿到管理员权限
sudo su - 
#注意替换spring.datasource.password=（你的webserver数据库密码）
cd /www/wwwroot/webServer
nohup java -jar -Xmx1024M -Xms256M  /www/wwwroot/webServer/WebServer-0.0.1-SNAPSHOT.jar --spring.datasource.password=（你的webserver数据库密码） --spring.datasource.username=webserver> output.log 2>&1 &
```

## 启动成功后 开放防火墙端口8081 即可访问

## 初始注册密钥：小羊是帅哥
- 注册之后可以修改

## 修改webserver表中的loginCheck里wechatNoticeKey的值
wechatNoticeKey获取方法：
###### 1.进入https://work.weixin.qq.com/
点击注册信息随意填写
然后依次点击 我的企业-微信插件-下拉使用微信扫描该二维码。
（注）以上步骤是为了微信能够接受微信企业消息
###### 2.进入手机微信
进入自己新建的企业，新建一个群组。

###### 3.进入手机企业微信
选择新建的企业登录，进入创立的群组-选择右上角三个点-点击群组机器人-点击添加-随意输入名字-得到一个链接：
https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=05e8b9b2-6be7-4d4b-bbce-54846851b450
拿取（05e8b9b2-6be7-4d4b-bbce-54846851b450）部分 放入wechatNoticeKey

## 记得一键三连，必须要给star⭐

