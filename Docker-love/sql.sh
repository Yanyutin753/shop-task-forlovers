echo "赋予权限"
ch="grant all privileges on *.* to 'root'@'%'"
mysql -uroot -plichun123456 --default-character-set=utf8mb4 -e"$ch"
# 6.修改root帐号的权限，使得任何ip都能访问
# grant all privileges on *.* to 'root'@'%';
#  drop database webserver

# 7.创建springboot_admin数据库：
echo "创建数据库"
create_table="create database webserver character set utf8mb4";
mysql -uroot -plichun123456 --default-character-set=utf8mb4 -e"$create_table"
echo "导入数据"
mysql -uroot -plichun123456 webserver < /webserver.sql
echo "导入成功"
