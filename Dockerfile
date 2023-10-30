# 该镜像需要依赖的基础镜像
FROM openjdk:11
 
# 指定维护者的名字
MAINTAINER spring

# 将指定目录下的jar包复制到docker容器的/www/wwwroot/webServer/目录下
COPY WebServer-0.0.1-SNAPSHOT_docker.jar /www/wwwroot/webServer/WebServer-0.0.1-SNAPSHOT.jar
COPY target /www/wwwroot/webServer/target
 
# 声明服务运行在8081端口
EXPOSE 8081
WORKDIR /www/wwwroot/webServer/
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/www/wwwroot/webServer/WebServer-0.0.1-SNAPSHOT.jar","--spring.datasource.username=root","--spring.datasource.password=lichun123456"]
