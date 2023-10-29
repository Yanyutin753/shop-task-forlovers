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
('wechatNoticeKey', '***********************');

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
