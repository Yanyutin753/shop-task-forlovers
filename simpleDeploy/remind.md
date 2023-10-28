## 将simpleDeploy里的文件拷贝到/www/wwwroot/webServer路径下，暂不支持修改别的路径

##  新建mysql数据表
- 数据表命名为webserver,用户名为webserver,密码自取（推荐强密码）

## 终端输入命令
’
nohup java -jar -Xmx1024M -Xms256M  /www/wwwroot/webServer/WebServer-0.0.1-SNAPSHOT.jar --spring.datasource.password=（你的webserver数据库密码） --spring.datasource.username=webserver> output.log 2>&1 &

‘

启动成功后 开放防火墙端口8081 即可访问