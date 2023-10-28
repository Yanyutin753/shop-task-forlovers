## 将安装包解压到/www/wwwroot/webServer路径下，如果更换路径请在脚本中自行替换

运行install.sh 一键安装

如遇数据库导入报错
从新执行该命令即可

docker exec -i -t mysql /bin/bash -c "/sql.sh"

启动成功后 开放防火墙端口80 即可访问