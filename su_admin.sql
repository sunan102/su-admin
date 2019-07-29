/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : admin_web

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 29/07/2019 11:04:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `admin_menu_id` varchar(36) NOT NULL,
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `vue_name` varchar(100) DEFAULT NULL COMMENT 'vue router的name字段',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `page` varchar(255) DEFAULT NULL COMMENT '页面文件',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` int(11) DEFAULT NULL COMMENT '1-菜单；2-功能',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` varchar(36) DEFAULT NULL,
  `mark` varchar(100) DEFAULT NULL COMMENT '功能按钮标识',
  PRIMARY KEY (`admin_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
BEGIN;
INSERT INTO `admin_menu` VALUES ('027fa013d302451688b00ba52f081475', '222', 'sdafcf', 'avav', 'wvw', 'wbb', 1, 1233443, 'ecba86034dc840fa965f2eab7cba4430', NULL);
INSERT INTO `admin_menu` VALUES ('21954b1c9d1d4895af81d7a61606c125', '账号管理', 'user', '/accounts/user', 'admin/user/index', 'icon-zhanghao', 1, 1, '6586287f72044c8c87285ee483d7be1b', NULL);
INSERT INTO `admin_menu` VALUES ('6586287f72044c8c87285ee483d7be1b', '账户及权限', 'accounts', '/accounts', '', 'icon-caidanyingyong1', 1, 1, '0', NULL);
INSERT INTO `admin_menu` VALUES ('d706f23d5434437696b438f20cc6866a', '菜单管理', 'menu', '/accounts/menu', 'admin/menu/index', 'icon-zhanghao', 1, 2, '6586287f72044c8c87285ee483d7be1b', NULL);
COMMIT;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_role_id` varchar(36) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`admin_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES ('0', '超级管理员', '2019-07-12 17:54:32', '2019-07-12 17:54:34');
COMMIT;

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu` (
  `admin_role_menu_id` varchar(36) NOT NULL,
  `admin_role_id` varchar(36) DEFAULT NULL,
  `admin_menu_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`admin_role_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `admin_user_id` varchar(36) NOT NULL COMMENT '主键',
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`admin_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES ('1', 'admin', '$2a$10$bDUU/YnKpd6qeFBL2lvL5eTRAL.65jmstBeC.I.1ozH04aWIXt2nm', 0, NULL, '2019-07-18 16:57:24');
INSERT INTO `admin_user` VALUES ('c46071b478e44a9fb78fa7908c01876e', 'admin1', '$2a$10$a2ppxDgyCKg2RTcpPbs25OITLxpBtk6SA7CplCagcScCFJrvtOcsC', 0, '2019-07-18 16:44:00', '2019-07-19 09:10:51');
COMMIT;

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `admin_user_role_id` varchar(36) NOT NULL,
  `admin_user_id` varchar(36) DEFAULT NULL,
  `admin_role_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`admin_user_role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_role` VALUES ('1', '1', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
