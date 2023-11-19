
# 1.下载mysql镜像
docker pull mysql:5.7
dir=$(pwd)
echo "当前路径: $dir"

echo "创建mysql容器并启动"
docker run -p 8080:3306 --name mysql \
-v /export/mysql/conf:/etc/mysql/conf.d \
-v /export/mysql/log:/var/log/mysql \
-v /export/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=lichun123456  \
-d mysql:5.7
echo "mysql运行成功"

echo "迁移数据"
# 3.将sql脚本拷贝到mysql容器的根目录下
docker cp $dir/webserver.sql mysql:/;

docker cp $dir/sql.sh mysql:/;
echo "迁移成功"
echo "----------------------------------------------------"
# 4.进入运行mysql的docker容器
# docker exec -i -t mysql /bin/bash 
echo "执行sql脚本"
docker exec -i -t mysql /bin/bash -c "chmod +x /sql.sh && /sql.sh"


# 8.将sql文件导入到数据库
# use webserver;
# source /webserver.sql;

# exit
# exit

echo "构建docker webserver应用镜像"
docker build -t webserver/webserver-0.0.1 .
echo "创建容器并启动"
# 2.创建容器并启动
docker run -d -p 8082:8081 \
--name webserver \
--link mysql:db \
-v /export/Logs/WebServer-0.0.1:/export/Logs/WebServer-0.0.1 \
webserver/webserver-0.0.1
echo "启动成功"