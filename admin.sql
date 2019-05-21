/*
Navicat MySQL Data Transfer

Source Server         : demo
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-04-21 20:19:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL COMMENT '货物名称',
  `goods_num` int(11) DEFAULT NULL COMMENT '库存',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '货物价格',
  `goods_type` int(10) DEFAULT '0' COMMENT '货物类型 0正常  1缺货 ',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品更新时间',
  `delete_status` int(10) DEFAULT '1' COMMENT '删除状态 1正常  2删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('7', '测试商品', '3000', '40.00', '0', '2019-04-21 19:30:12', '2019-04-21 19:30:12', '1');

-- ----------------------------
-- Table structure for inventoryadmin
-- ----------------------------
DROP TABLE IF EXISTS `inventoryadmin`;
CREATE TABLE `inventoryadmin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_id` int(11) DEFAULT NULL,
  `add_num` int(11) DEFAULT NULL COMMENT '入库数量',
  `reduce_num` int(255) DEFAULT NULL COMMENT '出库数量',
  `goods_price` decimal(10,2) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '盘点时间段开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '盘点时间段结束日期',
  `scrap_num` int(11) DEFAULT NULL COMMENT '报废数量',
  `user_name` varchar(255) DEFAULT NULL COMMENT '盘点人员名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '盘点人员id',
  `delete_status` varchar(255) DEFAULT '1' COMMENT '是否删除 1未删除  2已删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inventoryadmin
-- ----------------------------
INSERT INTO `inventoryadmin` VALUES ('3', '测试商品', '7', '3000', '0', '40.00', '2019-04-19 00:00:00', '2019-04-20 00:00:00', '0', '张三风', '201470024130', '1', '2019-04-21 19:41:09', '2019-04-21 19:41:09');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '盘点记录id',
  `goods_id` int(10) DEFAULT NULL COMMENT '对应的货物id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '货物名称',
  `user_name` varchar(255) DEFAULT NULL COMMENT '操作员姓名',
  `user_id` bigint(20) DEFAULT NULL COMMENT '仓库管理员id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '供应商名称/接收方名称',
  `company_phone` bigint(20) DEFAULT NULL COMMENT '单位电话',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '货物价格',
  `goods_num` int(10) DEFAULT NULL COMMENT '出库或者入库数量',
  `order_type` int(10) DEFAULT NULL COMMENT '记录类型  0 ：入库  1：出库  2:报废 3：缺货',
  `marks` varchar(255) DEFAULT NULL COMMENT '出入库记录的备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` int(10) DEFAULT '1' COMMENT '盘点记录是否删除  1正常  2删除',
  `order_state` int(10) DEFAULT NULL COMMENT '出入库订单是否完成  1：已完成 2：未完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('28', '7', '测试商品', '张三风', '201470024130', '测试公司', '2314', '40.00', '3000', '0', '测试', '2019-04-21 19:30:12', '2019-04-21 19:30:12', '1', null);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('101', 'goods', '货物管理', 'goods:list', '列表', '1');
INSERT INTO `sys_permission` VALUES ('102', 'goods', '货物管理', 'goods:add', '新增', '2');
INSERT INTO `sys_permission` VALUES ('103', 'goods', '货物管理', 'goods:update', '修改', '2');
INSERT INTO `sys_permission` VALUES ('104', 'goods', '货物管理', 'goods:delete', '删除', '2');
INSERT INTO `sys_permission` VALUES ('601', 'user', '用户', 'user:list', '列表', '1');
INSERT INTO `sys_permission` VALUES ('602', 'user', '用户', 'user:add', '新增', '2');
INSERT INTO `sys_permission` VALUES ('603', 'user', '用户', 'user:update', '修改', '2');
INSERT INTO `sys_permission` VALUES ('604', 'user', '用户', 'user:delete', '删除', '2');
INSERT INTO `sys_permission` VALUES ('701', 'role', '角色权限', 'role:list', '列表', '1');
INSERT INTO `sys_permission` VALUES ('702', 'role', '角色权限', 'role:add', '新增', '2');
INSERT INTO `sys_permission` VALUES ('703', 'role', '角色权限', 'role:update', '修改', '2');
INSERT INTO `sys_permission` VALUES ('704', 'role', '角色权限', 'role:delete', '删除', '2');
INSERT INTO `sys_permission` VALUES ('709', 'user', '用户', 'user:upload', '导入', '2');
INSERT INTO `sys_permission` VALUES ('710', 'order', '货物订单管理', 'order:list', '列表', '1');
INSERT INTO `sys_permission` VALUES ('711', 'order', '货物订单管理', 'order:delete', '删除', '2');
INSERT INTO `sys_permission` VALUES ('712', 'order', '货物订单管理', 'order:add', '增加', '2');
INSERT INTO `sys_permission` VALUES ('713', 'order', '货物订单管理', 'order:update', '更新', '2');
INSERT INTO `sys_permission` VALUES ('714', 'inventoryAdmin', '货物盘点记录管理', 'inventoryAdmin:list', '列表', '1');
INSERT INTO `sys_permission` VALUES ('715', 'inventoryAdmin', '货物盘点记录管理', 'inventoryAdmin:update', '更新', '2');
INSERT INTO `sys_permission` VALUES ('716', 'inventoryAdmin', '货物盘点记录管理', 'inventoryAdmin:add', '添加', '2');
INSERT INTO `sys_permission` VALUES ('717', 'inventoryAdmin', '货物盘点记录管理', 'inventoryAdmin:delete', '删除', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '2017-11-22 16:24:34', '2017-11-22 16:24:52', '1');
INSERT INTO `sys_role` VALUES ('2', '作家', '2017-11-22 16:24:34', '2019-01-07 10:46:47', '2');
INSERT INTO `sys_role` VALUES ('3', '程序员', '2017-11-22 16:28:47', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role` VALUES ('4', '普通用户', '2019-01-07 10:50:49', '2019-01-07 10:50:49', '1');
INSERT INTO `sys_role` VALUES ('5', '超级管理员', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role` VALUES ('6', '超超超级管理员', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '2', '101', '2017-11-22 16:26:21', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('2', '2', '102', '2017-11-22 16:26:21', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('5', '2', '602', '2017-11-22 16:28:28', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('6', '2', '601', '2017-11-22 16:28:28', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('7', '2', '603', '2017-11-22 16:28:28', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('8', '2', '703', '2017-11-22 16:28:28', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('9', '2', '701', '2017-11-22 16:28:28', '2019-01-07 10:46:47', '2');
INSERT INTO `sys_role_permission` VALUES ('10', '2', '702', '2017-11-22 16:28:28', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('11', '2', '704', '2017-11-22 16:28:31', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('12', '2', '103', '2017-11-22 16:28:31', '2019-01-07 10:46:19', '2');
INSERT INTO `sys_role_permission` VALUES ('13', '3', '601', '2017-11-22 16:28:47', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('14', '3', '701', '2017-11-22 16:28:47', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('15', '3', '702', '2017-11-22 16:35:01', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('16', '3', '704', '2017-11-22 16:35:01', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('17', '3', '102', '2017-11-22 16:35:01', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('18', '3', '101', '2017-11-22 16:35:01', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('19', '3', '603', '2017-11-22 16:35:01', '2019-01-07 10:45:27', '2');
INSERT INTO `sys_role_permission` VALUES ('20', '4', '101', '2019-01-07 10:50:49', '2019-01-07 15:02:33', '2');
INSERT INTO `sys_role_permission` VALUES ('21', '4', '102', '2019-01-07 10:50:49', '2019-01-07 14:16:22', '2');
INSERT INTO `sys_role_permission` VALUES ('22', '4', '103', '2019-01-07 10:50:49', '2019-01-07 14:16:22', '2');
INSERT INTO `sys_role_permission` VALUES ('23', '4', '601', '2019-01-07 10:50:49', '2019-01-07 14:16:22', '2');
INSERT INTO `sys_role_permission` VALUES ('24', '4', '601', '2019-01-07 14:59:15', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('25', '4', '602', '2019-01-07 14:59:15', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('26', '4', '603', '2019-01-07 14:59:15', '2019-01-07 15:24:31', '2');
INSERT INTO `sys_role_permission` VALUES ('27', '4', '701', '2019-01-07 14:59:15', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('28', '4', '702', '2019-01-07 14:59:15', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('29', '4', '703', '2019-01-07 14:59:15', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('30', '4', '704', '2019-01-07 14:59:15', '2019-01-07 15:43:41', '2');
INSERT INTO `sys_role_permission` VALUES ('31', '4', '102', '2019-01-07 14:59:15', '2019-01-07 15:02:33', '2');
INSERT INTO `sys_role_permission` VALUES ('32', '4', '103', '2019-01-07 14:59:15', '2019-01-07 15:02:33', '2');
INSERT INTO `sys_role_permission` VALUES ('33', '4', '101', '2019-01-07 15:03:17', '2019-01-07 15:03:17', '1');
INSERT INTO `sys_role_permission` VALUES ('34', '4', '705', '2019-01-07 15:03:27', '2019-01-07 15:39:13', '2');
INSERT INTO `sys_role_permission` VALUES ('35', '4', '103', '2019-01-07 15:04:27', '2019-01-18 14:08:36', '2');
INSERT INTO `sys_role_permission` VALUES ('36', '4', '102', '2019-01-07 15:04:27', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('37', '5', '101', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('38', '5', '102', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('39', '5', '103', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('40', '5', '601', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('41', '5', '602', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('42', '5', '603', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('43', '5', '701', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('44', '5', '702', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('45', '5', '703', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('46', '5', '704', '2019-01-07 15:11:13', '2019-01-07 15:11:13', '1');
INSERT INTO `sys_role_permission` VALUES ('47', '5', '705', '2019-01-07 15:11:13', '2019-01-07 15:38:29', '2');
INSERT INTO `sys_role_permission` VALUES ('48', '4', '706', '2019-01-07 15:26:54', '2019-01-07 15:39:13', '2');
INSERT INTO `sys_role_permission` VALUES ('49', '5', '707', '2019-01-07 15:38:29', '2019-01-07 15:38:29', '1');
INSERT INTO `sys_role_permission` VALUES ('50', '4', '707', '2019-01-07 15:39:13', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('51', '4', '604', '2019-01-07 15:39:37', '2019-01-07 15:43:17', '2');
INSERT INTO `sys_role_permission` VALUES ('52', '4', '603', '2019-01-07 15:40:01', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('53', '4', '604', '2019-01-07 15:44:45', '2019-01-07 15:45:07', '2');
INSERT INTO `sys_role_permission` VALUES ('54', '4', '708', '2019-01-07 16:29:28', '2019-01-07 16:29:52', '2');
INSERT INTO `sys_role_permission` VALUES ('55', '4', '705', '2019-01-09 00:13:18', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('56', '4', '706', '2019-01-09 00:13:18', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('57', '5', '705', '2019-01-09 00:14:59', '2019-01-09 00:14:59', '1');
INSERT INTO `sys_role_permission` VALUES ('58', '5', '706', '2019-01-09 00:14:59', '2019-01-09 00:14:59', '1');
INSERT INTO `sys_role_permission` VALUES ('59', '4', '708', '2019-01-18 14:47:22', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('60', '4', '103', '2019-01-18 14:47:22', '2019-01-22 10:42:36', '2');
INSERT INTO `sys_role_permission` VALUES ('61', '5', '708', '2019-01-18 14:47:36', '2019-01-18 14:47:36', '1');
INSERT INTO `sys_role_permission` VALUES ('62', '4', '710', '2019-04-14 20:15:09', '2019-04-14 20:15:09', '1');
INSERT INTO `sys_role_permission` VALUES ('63', '4', '712', '2019-04-14 20:15:09', '2019-04-14 20:15:09', '1');
INSERT INTO `sys_role_permission` VALUES ('64', '4', '711', '2019-04-14 20:15:09', '2019-04-14 20:15:09', '1');
INSERT INTO `sys_role_permission` VALUES ('65', '4', '713', '2019-04-14 20:15:09', '2019-04-14 20:15:09', '1');
INSERT INTO `sys_role_permission` VALUES ('66', '6', '101', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('67', '6', '102', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('68', '6', '103', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('69', '6', '601', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('70', '6', '602', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('71', '6', '603', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('72', '6', '604', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('73', '6', '709', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('74', '6', '701', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('75', '6', '702', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('76', '6', '703', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('77', '6', '704', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('78', '6', '705', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('79', '6', '706', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('80', '6', '707', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('81', '6', '708', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('82', '6', '710', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('83', '6', '711', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('84', '6', '712', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('85', '6', '713', '2019-04-14 20:27:08', '2019-04-14 20:27:08', '1');
INSERT INTO `sys_role_permission` VALUES ('86', '6', '714', '2019-04-15 23:26:18', '2019-04-15 23:26:18', '1');
INSERT INTO `sys_role_permission` VALUES ('87', '6', '104', '2019-04-18 21:17:31', '2019-04-18 21:17:31', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT '123456' COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `stu_num` bigint(20) DEFAULT NULL COMMENT '用户学号',
  `class` varchar(255) DEFAULT NULL COMMENT '学生班级',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `delete_status` varchar(255) DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`username`) USING BTREE COMMENT '用户名索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='运营后台用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('201470024130', '201470024130', '123456', '张三风', '1', '2019-04-02 14:41:30', '2019-04-21 13:30:13', '201470024130', '卓越146', '13183013988', '1');
INSERT INTO `sys_user` VALUES ('201470024135', '201470024135', '123456', '李四', '4', '2019-04-02 14:41:30', '2019-04-15 21:15:31', '201470024135', '卓越142', '13183013983', '1');
INSERT INTO `sys_user` VALUES ('201470024136', '201470024136', '123456', '王五', '4', '2019-04-02 14:41:30', '2019-04-15 23:50:56', '201470024136', '卓越142', '13183013984', '1');
INSERT INTO `sys_user` VALUES ('201470024137', '201470024137', '123456', '张三', '4', '2019-04-02 14:41:30', '2019-04-10 23:40:33', '201470024137', '卓越143', '13183013985', '1');
INSERT INTO `sys_user` VALUES ('201470024138', '201470024138', '123456', '张三', '4', '2019-04-02 14:41:30', '2019-04-15 21:15:35', '201470024138', '卓越144', '13183013986', '1');
INSERT INTO `sys_user` VALUES ('201470024139', '201470024139', '123456', '张三', '4', '2019-04-02 14:41:30', '2019-04-15 21:15:38', '201470024139', '卓越145', '13183013987', '1');
