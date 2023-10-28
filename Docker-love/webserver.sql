-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2023-10-28 13:21:12
-- 服务器版本： 5.7.40-log
-- PHP 版本： 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `webserver`
--

-- --------------------------------------------------------

--
-- 表的结构 `detailusertable`
--

CREATE TABLE `detailusertable` (
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `useName` varchar(10) NOT NULL COMMENT '用户名',
  `allCredit` int(11) DEFAULT '0' COMMENT '用户获得积分总数',
  `dayCredit` int(11) DEFAULT '0' COMMENT '用户今日获得积分',
  `allCompleteTask` int(11) DEFAULT '0' COMMENT '用户完成任务总数',
  `dayCompleteTask` int(11) DEFAULT '0' COMMENT '用户每日完成任务数',
  `allAddTask` int(11) DEFAULT '0' COMMENT '添加任务总数',
  `dayAddTask` int(11) DEFAULT '0' COMMENT '用户每日新增任务数',
  `allAddProduce` int(11) DEFAULT '0' COMMENT '增加商品总数',
  `dayAddProduce` int(11) DEFAULT '0' COMMENT '用户每日新增商品数',
  `allBuyProduce` int(11) DEFAULT '0' COMMENT '购买商品总数',
  `dayBuyProduce` int(11) DEFAULT '0' COMMENT '用户每日新增购买商品数',
  `dayReduceRoom` int(11) DEFAULT '0' COMMENT '用户每日新增兑换商品数',
  `allReduceRoom` int(11) DEFAULT '0' COMMENT '兑换商品总数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `detailusertable`
--

INSERT INTO `detailusertable` (`userId`, `useName`, `allCredit`, `dayCredit`, `allCompleteTask`, `dayCompleteTask`, `allAddTask`, `dayAddTask`, `allAddProduce`, `dayAddProduce`, `allBuyProduce`, `dayBuyProduce`, `dayReduceRoom`, `allReduceRoom`) VALUES
(1, 'yangyang', 1000000, 1000000, 3, 3, 2, 2, 0, 0, 4, 4, 4, 4),
(2, 'he', 0, 0, 2, 2, 3, 3, 4, 4, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `homebroadcast`
--

CREATE TABLE `homebroadcast` (
  `broadCastId` int(11) NOT NULL COMMENT '广播Id',
  `broadCastText` varchar(50) DEFAULT NULL COMMENT '广播文本'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `logincheck`
--

CREATE TABLE `logincheck` (
  `attribute` varchar(30) NOT NULL COMMENT '属性名',
  `value` varchar(50) DEFAULT NULL COMMENT '属性名对应的值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `logincheck`
--

INSERT INTO `logincheck` (`attribute`, `value`) VALUES
('loginCheck', '小羊是帅哥'),
('wechatNoticeKey', '05e8b9bb-6be7-4d8b-bbce-54846851b450');

-- --------------------------------------------------------

--
-- 表的结构 `operate_log`
--

CREATE TABLE `operate_log` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'ID',
  `operate_user` int(10) UNSIGNED DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(1000) DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) DEFAULT NULL COMMENT '返回值',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '方法执行耗时, 单位:ms'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

--
-- 转存表中的数据 `operate_log`
--

INSERT INTO `operate_log` (`id`, `operate_user`, `operate_time`, `class_name`, `method_name`, `method_params`, `return_value`, `cost_time`) VALUES
(1, NULL, '2023-10-28 02:04:08', 'com.yyandywt99.webserver.controller.userController', 'addUser', '[user(nameId=0, name=yangyang, password=123456, userText=ada, userImage=http://101.42.31.20:8081/image/e23c3b58-da79-4101-b2f2-b9410672795d.gif, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=null, displayItem=null, displayDay=null, displayLogo=null, displayUrl=null)]', '{\"code\":1,\"data\":\"添加成功！\",\"msg\":\"success\"}', 70),
(2, NULL, '2023-10-28 02:05:00', 'com.yyandywt99.webserver.controller.userController', 'addUser', '[user(nameId=0, name=he, password=123456, userText=he, userImage=http://101.42.31.20:8081/image/02836a17-2b76-436a-806f-aa0693298172.jpg, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=null, displayItem=null, displayDay=null, displayLogo=null, displayUrl=null)]', '{\"code\":1,\"data\":\"添加成功！\",\"msg\":\"success\"}', 23),
(3, 2, '2023-10-28 02:10:48', 'com.yyandywt99.webserver.controller.produceController', 'addProduce', '[produce(produceId=null, founderId=2, produceName=奶茶权限, produceText=凭此券可以向对方索要一杯奶茶。, produceImage=http://101.42.31.20:8081/image/14f66f86-a785-45af-992a-3ddd46d46c0a.jpg, produceCredit=100, produceNum=1, checkProduce=false, customer=yangyang, checkLove=false, updateTime=null, registerTime=null)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 663),
(4, 2, '2023-10-28 02:17:38', 'com.yyandywt99.webserver.controller.taskController', 'addTask', '[task(taskId=null, founderId=2, useName=yangyang, userImage=null, taskName=打扫房间, taskText=有一段时间没有打扫房间了，一屋不扫，何以扫天下？, taskCredit=0, registerTime=null, updateTime=null, checkTask=false, checkLove=false)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 703),
(5, 2, '2023-10-28 02:19:14', 'com.yyandywt99.webserver.controller.produceController', 'addProduce', '[produce(produceId=null, founderId=2, produceName=美味薯片, produceText=诱人的零食，夜宵绝佳伴侣，咔嘣脆！凭此商品可以向对方索要薯片。, produceImage=http://101.42.31.20:8081/image/4bcf8a15-f3cc-433b-b1d8-909692f9fea7.jpg, produceCredit=100, produceNum=1, checkProduce=false, customer=yangyang, checkLove=false, updateTime=null, registerTime=null)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 375),
(6, 2, '2023-10-28 02:22:33', 'com.yyandywt99.webserver.controller.produceController', 'addProduce', '[produce(produceId=null, founderId=2, produceName=奶茶权限, produceText=凭此券可以向对方索要一杯奶茶。, produceImage=http://101.42.31.20:8081/image/1a51c0ce-a3c6-474f-b99c-f2d992190095.jpg, produceCredit=10, produceNum=1, checkProduce=false, customer=yangyang, checkLove=false, updateTime=null, registerTime=null)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 368),
(7, 2, '2023-10-28 02:26:08', 'com.yyandywt99.webserver.controller.taskController', 'addTask', '[task(taskId=null, founderId=2, useName=yangyang, userImage=null, taskName=hello, taskText=w, taskCredit=0, registerTime=null, updateTime=null, checkTask=false, checkLove=false)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 348),
(8, 2, '2023-10-28 02:31:35', 'com.yyandywt99.webserver.controller.produceController', 'addProduce', '[produce(produceId=null, founderId=2, produceName=h, produceText=j, produceImage=http://101.42.31.20:8081/image/e2e60c0a-0692-4579-b608-bea3623bf20e.jpeg, produceCredit=10, produceNum=1, checkProduce=false, customer=yangyang, checkLove=false, updateTime=null, registerTime=null)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 624),
(9, 1, '2023-10-28 02:35:33', 'com.yyandywt99.webserver.controller.taskController', 'addTask', '[task(taskId=null, founderId=1, useName=he, userImage=null, taskName=早睡早起, taskText=熬夜对身体很不好，还是要早点睡觉第二天才能有精神！, taskCredit=0, registerTime=null, updateTime=null, checkTask=false, checkLove=false)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 399),
(10, 1, '2023-10-28 02:41:10', 'com.yyandywt99.webserver.controller.taskController', 'addTask', '[task(taskId=null, founderId=1, useName=he, userImage=null, taskName=白天不睡觉, taskText=白天不睡觉，晚上早睡，早上早起，养成良好生活作息从我做起！, taskCredit=0, registerTime=null, updateTime=null, checkTask=false, checkLove=false)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 347),
(11, 1, '2023-10-28 02:41:49', 'com.yyandywt99.webserver.controller.userController', 'requireUser', '[user(nameId=1, name=null, password=null, userText=null, userImage=null, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=hrllo, displayItem=null, displayDay=null, displayLogo=null, displayUrl=null)]', '{\"code\":1,\"data\":\"修改成功\",\"msg\":\"success\"}', 43),
(12, 2, '2023-10-28 02:54:48', 'com.yyandywt99.webserver.controller.taskController', 'completedTask', '[2]', '{\"code\":1,\"data\":\"成功完成任务，你真棒！\",\"msg\":\"success\"}', 751),
(13, 2, '2023-10-28 02:54:55', 'com.yyandywt99.webserver.controller.taskController', 'completedTask', '[1]', '{\"code\":1,\"data\":\"成功完成任务，你真棒！\",\"msg\":\"success\"}', 658),
(14, 2, '2023-10-28 02:55:25', 'com.yyandywt99.webserver.controller.taskController', 'addTask', '[task(taskId=null, founderId=2, useName=yangyang, userImage=null, taskName=hello, taskText=哈哈哈, taskCredit=1000000, registerTime=null, updateTime=null, checkTask=false, checkLove=false)]', '{\"code\":1,\"data\":\"添加成功\",\"msg\":\"success\"}', 371),
(15, 1, '2023-10-28 02:55:58', 'com.yyandywt99.webserver.controller.taskController', 'completedTask', '[4]', '{\"code\":1,\"data\":\"成功完成任务，你真棒！\",\"msg\":\"success\"}', 679),
(16, 1, '2023-10-28 02:56:02', 'com.yyandywt99.webserver.controller.taskController', 'completedTask', '[3]', '{\"code\":1,\"data\":\"成功完成任务，你真棒！\",\"msg\":\"success\"}', 699),
(17, 2, '2023-10-28 02:56:23', 'com.yyandywt99.webserver.controller.taskController', 'completedTask', '[5]', '{\"code\":1,\"data\":\"成功完成任务，你真棒！\",\"msg\":\"success\"}', 727),
(18, 1, '2023-10-28 02:56:57', 'com.yyandywt99.webserver.controller.produceController', 'buyProduce', '[4]', '{\"code\":1,\"data\":1,\"msg\":\"success\"}', 429),
(19, 1, '2023-10-28 02:57:01', 'com.yyandywt99.webserver.controller.produceController', 'buyProduce', '[3]', '{\"code\":1,\"data\":2,\"msg\":\"success\"}', 385),
(20, 1, '2023-10-28 02:57:07', 'com.yyandywt99.webserver.controller.produceController', 'buyProduce', '[2]', '{\"code\":1,\"data\":3,\"msg\":\"success\"}', 452),
(21, 1, '2023-10-28 02:57:10', 'com.yyandywt99.webserver.controller.produceController', 'buyProduce', '[1]', '{\"code\":1,\"data\":4,\"msg\":\"success\"}', 392),
(22, 1, '2023-10-28 02:57:20', 'com.yyandywt99.webserver.controller.roomController', 'reduceRoom', '[4]', '{\"code\":1,\"data\":5,\"msg\":\"success\"}', 27),
(23, 1, '2023-10-28 02:57:26', 'com.yyandywt99.webserver.controller.roomController', 'reduceRoom', '[1]', '{\"code\":1,\"data\":6,\"msg\":\"success\"}', 26),
(24, 1, '2023-10-28 02:57:29', 'com.yyandywt99.webserver.controller.roomController', 'reduceRoom', '[2]', '{\"code\":1,\"data\":7,\"msg\":\"success\"}', 28),
(25, 1, '2023-10-28 02:57:32', 'com.yyandywt99.webserver.controller.roomController', 'reduceRoom', '[3]', '{\"code\":1,\"data\":8,\"msg\":\"success\"}', 25),
(26, 1, '2023-10-28 09:28:55', 'com.yyandywt99.webserver.controller.userController', 'requireUser', '[user(nameId=1, name=null, password=null, userText=null, userImage=null, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=null, displayItem=, displayDay=, displayLogo=🏰捏捏的魔仙堡, displayUrl=, signPassword=null)]', '{\"code\":1,\"data\":\"修改成功\",\"msg\":\"success\"}', 64),
(27, 1, '2023-10-28 09:29:04', 'com.yyandywt99.webserver.controller.userController', 'requireUser', '[user(nameId=1, name=null, password=null, userText=null, userImage=null, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=null, displayItem=, displayDay=, displayLogo=, displayUrl=http://101.42.31.20:8081/#/, signPassword=null)]', '{\"code\":1,\"data\":\"修改成功\",\"msg\":\"success\"}', 11),
(28, NULL, '2023-10-28 10:56:42', 'com.yyandywt99.webserver.controller.userController', 'addUser', '[user(nameId=0, name=yy, password=123456, userText=gjkhk, userImage=http://101.42.31.20:8081/image/ece7fd0b-f091-4285-8412-ed6ca23b0d29.jpg, credit=0, registerTime=null, updateTime=null, remindTime=null, remindSwitch=false, remindText=null, displayItem=null, displayDay=null, displayLogo=null, displayUrl=null, signPassword=小羊是帅哥)]', '{\"code\":1,\"data\":\"添加成功！\",\"msg\":\"success\"}', 86),
(29, 3, '2023-10-28 10:56:59', 'com.yyandywt99.webserver.controller.userController', 'deleteUser', '[3]', '{\"code\":1,\"data\":\"删除成功！\",\"msg\":\"success\"}', 20);

-- --------------------------------------------------------

--
-- 表的结构 `producetable`
--

CREATE TABLE `producetable` (
  `produceId` int(11) NOT NULL COMMENT '商品ID',
  `produceName` varchar(50) NOT NULL COMMENT '商品名字',
  `founderId` int(11) NOT NULL COMMENT '商品创始人',
  `produceText` varchar(100) DEFAULT NULL COMMENT '商品简介',
  `produceImage` text COMMENT '商品图片地址',
  `produceCredit` int(11) DEFAULT NULL COMMENT '单个任务积分',
  `produceNum` tinyint(4) DEFAULT '1' COMMENT '商品数量,默认为1',
  `customer` varchar(20) NOT NULL COMMENT '出售商品人名字',
  `updateTime` datetime DEFAULT NULL COMMENT '更新商品时间',
  `registerTime` datetime DEFAULT NULL COMMENT '注册商品时间',
  `checkLove` tinyint(1) DEFAULT '0' COMMENT '商品是否收藏'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `roomtable`
--

CREATE TABLE `roomtable` (
  `produceId` int(11) NOT NULL COMMENT '商品代号',
  `founderId` int(11) NOT NULL COMMENT '商品拥有者',
  `produceName` varchar(20) NOT NULL COMMENT '商品名称',
  `produceText` varchar(100) DEFAULT NULL COMMENT '商品详情',
  `produceImage` text COMMENT '商品图片',
  `produceNum` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `checkProduce` tinyint(1) NOT NULL DEFAULT '0' COMMENT '判断任务是否核对',
  `useName` varchar(20) NOT NULL COMMENT '商品所有人',
  `updateTime` datetime DEFAULT NULL COMMENT '商品更新时间',
  `registerTime` datetime NOT NULL COMMENT '商品注册时间',
  `checkLove` tinyint(1) DEFAULT '0' COMMENT '商品是否收藏'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `roomtable`
--

INSERT INTO `roomtable` (`produceId`, `founderId`, `produceName`, `produceText`, `produceImage`, `produceNum`, `checkProduce`, `useName`, `updateTime`, `registerTime`, `checkLove`) VALUES
(5, 1, '奶茶权限', '凭此券可以向对方索要一杯奶茶。', 'http://101.42.31.20:8081/image/14f66f86-a785-45af-992a-3ddd46d46c0a.jpg', 1, 1, 'yangyang', '2023-10-28 02:57:20', '2023-10-28 02:57:20', 0),
(6, 1, 'h', 'j', 'http://101.42.31.20:8081/image/e2e60c0a-0692-4579-b608-bea3623bf20e.jpeg', 1, 1, 'yangyang', '2023-10-28 02:57:26', '2023-10-28 02:57:26', 0),
(7, 1, '奶茶权限', '凭此券可以向对方索要一杯奶茶。', 'http://101.42.31.20:8081/image/1a51c0ce-a3c6-474f-b99c-f2d992190095.jpg', 1, 1, 'yangyang', '2023-10-28 02:57:29', '2023-10-28 02:57:29', 0),
(8, 1, '美味薯片', '诱人的零食，夜宵绝佳伴侣，咔嘣脆！凭此商品可以向对方索要薯片。', 'http://101.42.31.20:8081/image/4bcf8a15-f3cc-433b-b1d8-909692f9fea7.jpg', 1, 1, 'yangyang', '2023-10-28 02:57:32', '2023-10-28 02:57:32', 0);

-- --------------------------------------------------------

--
-- 表的结构 `tasktable`
--

CREATE TABLE `tasktable` (
  `taskId` int(11) NOT NULL COMMENT '任务编号',
  `founderId` int(11) NOT NULL COMMENT '任务创建者ID',
  `useName` varchar(20) NOT NULL COMMENT '发起任务的人',
  `taskName` varchar(100) NOT NULL COMMENT '任务名称',
  `taskText` varchar(200) DEFAULT NULL COMMENT '任务备注',
  `taskCredit` int(11) DEFAULT '0' COMMENT '任务积分',
  `updateTime` datetime DEFAULT NULL COMMENT '任务更新时间',
  `registerTime` datetime DEFAULT NULL COMMENT '任务创建时间',
  `checkTask` tinyint(1) DEFAULT '0' COMMENT '任务是否完成',
  `checkLove` tinyint(4) DEFAULT '0' COMMENT '是否收藏'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `tasktable`
--

INSERT INTO `tasktable` (`taskId`, `founderId`, `useName`, `taskName`, `taskText`, `taskCredit`, `updateTime`, `registerTime`, `checkTask`, `checkLove`) VALUES
(1, 2, 'yangyang', '打扫房间', '有一段时间没有打扫房间了，一屋不扫，何以扫天下？', 0, '2023-10-28 02:54:55', '2023-10-28 02:17:38', 1, 0),
(2, 2, 'yangyang', 'hello', 'w', 0, '2023-10-28 02:54:47', '2023-10-28 02:26:07', 1, 0),
(3, 1, 'he', '早睡早起', '熬夜对身体很不好，还是要早点睡觉第二天才能有精神！', 0, '2023-10-28 02:56:02', '2023-10-28 02:35:33', 1, 0),
(4, 1, 'he', '白天不睡觉', '白天不睡觉，晚上早睡，早上早起，养成良好生活作息从我做起！', 0, '2023-10-28 02:55:58', '2023-10-28 02:41:09', 1, 1),
(5, 2, 'yangyang', 'hello', '哈哈哈', 1000000, '2023-10-28 02:56:23', '2023-10-28 02:55:25', 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `temtasktable`
--

CREATE TABLE `temtasktable` (
  `taskId` int(11) NOT NULL COMMENT '任务编号',
  `founderId` int(11) NOT NULL COMMENT '任务创建者ID',
  `useName` varchar(20) NOT NULL COMMENT '发起任务的人',
  `taskName` varchar(100) NOT NULL COMMENT '任务名称',
  `taskText` varchar(200) DEFAULT NULL COMMENT '任务备注',
  `taskCredit` int(11) DEFAULT '0' COMMENT '任务积分',
  `updateTime` datetime DEFAULT NULL COMMENT '任务更新时间',
  `registerTime` datetime DEFAULT NULL COMMENT '任务创建时间',
  `checkTask` tinyint(1) DEFAULT '0' COMMENT '任务是否完成',
  `checkLove` tinyint(4) DEFAULT '0' COMMENT '是否收藏'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `usertable`
--

CREATE TABLE `usertable` (
  `nameId` int(11) NOT NULL COMMENT 'name 的 id',
  `name` varchar(20) NOT NULL COMMENT '唯一',
  `password` varchar(20) DEFAULT '123456' COMMENT '用户密码',
  `userText` text COMMENT '用户的介绍',
  `userImage` text,
  `credit` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `remindText` text COMMENT '用户提醒文案',
  `remindSwitch` tinyint(4) DEFAULT '0' COMMENT '提醒开关',
  `remindTime` varchar(20) DEFAULT '0 30 11 * * ?' COMMENT '提醒时间',
  `registerTime` datetime DEFAULT NULL COMMENT '注册时间',
  `displayItem` varchar(10) DEFAULT '♥在一起' COMMENT '展示事件',
  `displayDay` varchar(20) NOT NULL DEFAULT '2022-10-06' COMMENT '展示事件时间',
  `displayLogo` varchar(20) NOT NULL DEFAULT '?捏捏的魔仙堡' COMMENT '展示logo',
  `displayUrl` varchar(50) NOT NULL DEFAULT 'http://121.37.243.173/#/' COMMENT '展示二维码内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `usertable`
--

INSERT INTO `usertable` (`nameId`, `name`, `password`, `userText`, `userImage`, `credit`, `updateTime`, `remindText`, `remindSwitch`, `remindTime`, `registerTime`, `displayItem`, `displayDay`, `displayLogo`, `displayUrl`) VALUES
(1, 'yangyang', '123456', 'ada', 'http://101.42.31.20:8081/image/e23c3b58-da79-4101-b2f2-b9410672795d.gif', 999780, '2023-10-28 09:29:03', 'hrllo', 1, '0 30 11 * * ?', '2023-10-28 02:04:07', '♥在一起', '2022-10-06', '🏰捏捏的魔仙堡', 'http://101.42.31.20:8081/#/'),
(2, 'he', '123456', 'he', 'http://101.42.31.20:8081/image/02836a17-2b76-436a-806f-aa0693298172.jpg', 0, '2023-10-28 02:56:02', NULL, 1, '0 30 11 * * ?', '2023-10-28 02:04:59', '♥在一起', '2022-10-06', '?捏捏的魔仙堡', 'http://121.37.243.173/#/');

--
-- 转储表的索引
--

--
-- 表的索引 `detailusertable`
--
ALTER TABLE `detailusertable`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `detailUserTable_userId_uindex` (`userId`),
  ADD UNIQUE KEY `detailusertable_useName_uindex` (`useName`);

--
-- 表的索引 `homebroadcast`
--
ALTER TABLE `homebroadcast`
  ADD PRIMARY KEY (`broadCastId`),
  ADD UNIQUE KEY `homeBroadCast_broadCastId_uindex` (`broadCastId`);

--
-- 表的索引 `logincheck`
--
ALTER TABLE `logincheck`
  ADD PRIMARY KEY (`attribute`),
  ADD UNIQUE KEY `logincheck_attribute_uindex` (`attribute`);

--
-- 表的索引 `operate_log`
--
ALTER TABLE `operate_log`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `producetable`
--
ALTER TABLE `producetable`
  ADD UNIQUE KEY `producetable_produceId_uindex` (`produceId`),
  ADD KEY `producetable_usertable_name_fk` (`customer`);

--
-- 表的索引 `roomtable`
--
ALTER TABLE `roomtable`
  ADD PRIMARY KEY (`registerTime`),
  ADD UNIQUE KEY `roomtable_produceId_uindex` (`produceId`),
  ADD UNIQUE KEY `roomtable_registerTime_uindex` (`registerTime`),
  ADD KEY `roomtable_usertable_name_fk` (`useName`);

--
-- 表的索引 `tasktable`
--
ALTER TABLE `tasktable`
  ADD PRIMARY KEY (`taskId`),
  ADD KEY `tasktable_usertable_name_fk` (`useName`);

--
-- 表的索引 `temtasktable`
--
ALTER TABLE `temtasktable`
  ADD PRIMARY KEY (`taskId`),
  ADD KEY `temTasktable_usertable_name_fk` (`useName`);

--
-- 表的索引 `usertable`
--
ALTER TABLE `usertable`
  ADD PRIMARY KEY (`nameId`),
  ADD UNIQUE KEY `usertable_nameId_uindex` (`nameId`),
  ADD UNIQUE KEY `usertable_name_uindex` (`name`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `detailusertable`
--
ALTER TABLE `detailusertable`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID', AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `homebroadcast`
--
ALTER TABLE `homebroadcast`
  MODIFY `broadCastId` int(11) NOT NULL AUTO_INCREMENT COMMENT '广播Id';

--
-- 使用表AUTO_INCREMENT `operate_log`
--
ALTER TABLE `operate_log`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID', AUTO_INCREMENT=30;

--
-- 使用表AUTO_INCREMENT `producetable`
--
ALTER TABLE `producetable`
  MODIFY `produceId` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID', AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `roomtable`
--
ALTER TABLE `roomtable`
  MODIFY `produceId` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品代号', AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `tasktable`
--
ALTER TABLE `tasktable`
  MODIFY `taskId` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务编号', AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `temtasktable`
--
ALTER TABLE `temtasktable`
  MODIFY `taskId` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务编号';

--
-- 使用表AUTO_INCREMENT `usertable`
--
ALTER TABLE `usertable`
  MODIFY `nameId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'name 的 id', AUTO_INCREMENT=4;

--
-- 限制导出的表
--

--
-- 限制表 `detailusertable`
--
ALTER TABLE `detailusertable`
  ADD CONSTRAINT `detailusertable_usertable_name_fk` FOREIGN KEY (`useName`) REFERENCES `usertable` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `producetable`
--
ALTER TABLE `producetable`
  ADD CONSTRAINT `producetable_usertable_name_fk` FOREIGN KEY (`customer`) REFERENCES `usertable` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `roomtable`
--
ALTER TABLE `roomtable`
  ADD CONSTRAINT `roomtable_usertable_name_fk` FOREIGN KEY (`useName`) REFERENCES `usertable` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `tasktable`
--
ALTER TABLE `tasktable`
  ADD CONSTRAINT `tasktable_usertable_name_fk` FOREIGN KEY (`useName`) REFERENCES `usertable` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `temtasktable`
--
ALTER TABLE `temtasktable`
  ADD CONSTRAINT `temTasktable_usertable_name_fk` FOREIGN KEY (`useName`) REFERENCES `usertable` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
