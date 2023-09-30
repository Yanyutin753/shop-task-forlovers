# loveShopping

项目描述：这个项目是基于[Rainbow-Cats-Personal-WeChat-MiniProgram](https://github.com/UxxHans/Rainbow-Cats-Personal-WeChat-MiniProgram)的代码改造而来，转变为网页端应用。它具备多人同时登陆、自定义任务、自定义用户信息、登录、注册、自定义商品和购买商品等功能,
如果是学生的话，可以申请Azure的免费服务器，项目将完全免费！如果你喜欢并感兴趣，请给我一个星星吧！感谢！

## 功能特性

- **多人同时登陆：** 允许多个用户同时访问系统，进行各种操作。

- **自定义任务：** 用户可以创建和管理自己的任务列表，以便更好地组织工作和生活。

- **自定义用户信息：** 提供用户个人信息管理功能，包括头像、昵称、联系信息等。

- **自定义商品：** 允许用户添加、编辑和删除商品信息，以便展示和销售。

- **登录和注册：** 用户可以通过注册账户和登录来访问系统。

- **查看商品，任务，用户信息：** 提供信息查看功能，包括商品、任务、用户信息等。

- **完成删除任务：** 用户可以选择完成自己创建给他人的任务或删除任务。
  
- **购买兑换商品：** 用户可以选择购买系统中的商品，并完成交易流程。

- **丝滑的操作：** 用户体验良好，操作简单

- **可靠使用：** 只有特定的用户才能进行特定用户的操作，保护了用户的使用安全

## 部署指南
## 前端部署

1. **打包前端项目：** 使用VSCode或其他编辑器打包前端WebServer项目，并确保您已经生成了前端的`dist`文件。

2. **复制文件到宝塔：** 将`dist`文件夹内的所有文件复制到宝塔服务器的网站根目录。您可以使用FTP工具或SSH将文件上传到服务器。

3. **新建站点：** 使用宝塔面板，在网站管理中新建一个站点。配置站点的域名、根目录等信息，确保站点的根目录指向您上传的`dist`文件夹。

## 后端部署

1. **打包后端项目：**
-1.使用IntelliJ IDEA或其他Java集成开发环境打包后端项目

-2.没有idea，直接启动我打包好的包。 [后端启动包]（）

-3 **运行jar启动包** 
```
nohup java -jar 【 打包好的项目文件名（jar结尾）】 > output.log 2>&1 &
tail -f output.log
```
-4.用宝塔新建mysql数据库，
数据库名字命名为：webserver
用户名命名为：webserver
用户名密码为= webserver123

-5.配置mysql数据库表
![image](https://github.com/Yanyutin753/loveShopping/assets/132346501/ae048adb-cea9-4ec9-aede-4222af18424f)
点击管理
![image](https://github.com/Yanyutin753/loveShopping/assets/132346501/82fc9390-104b-4d1a-b035-f8928bb7ac45)
点击SQL复制[文件](https://github.com/Yanyutin753/loveShopping/blob/main/MySql%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)里的内容

-6.可以自行在userTable填写内容，或者打开网页进行注册，添加内容

## 展示内容
<iframe width="560" height="315" src="https://www.yyandywt99.love/yy2/1.mp4" frameborder="0" allowfullscreen></iframe>

<video src="https://www.yyandywt99.love/yy2/1.mp4" controls autoplay loop></video>


![442b7ae7901465b7aecbbc16d39abf1](https://github.com/Yanyutin753/loveShopping/assets/132346501/5a64fc8f-6d98-4165-a644-746a3f6d2745)
![87a10556af16c6fe88e57f93e09959a](https://github.com/Yanyutin753/loveShopping/assets/132346501/322230c2-ed26-47d2-9214-cd5b38ba7fb8)


## 贡献

如果您有兴趣为项目做出贡献或报告问题，请查看项目的GitHub仓库并提出您的建议和改进。

## 许可证

这个项目基于 [许可证名称] 许可证。请查看项目的许可证文件以获取更多信息。

感谢您的兴趣和支持！

