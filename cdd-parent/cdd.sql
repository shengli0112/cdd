/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : cdd

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 11/01/2019 18:42:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin_info
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_info`;
CREATE TABLE `t_admin_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role_id` bigint(20) NOT NULL COMMENT '角色',
  `status` int(4) NOT NULL DEFAULT 1,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_attention_lease_house
-- ----------------------------
DROP TABLE IF EXISTS `t_attention_lease_house`;
CREATE TABLE `t_attention_lease_house`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `lease_house_id` bigint(20) NOT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_browse_record
-- ----------------------------
DROP TABLE IF EXISTS `t_browse_record`;
CREATE TABLE `t_browse_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `obj_id` bigint(20) NOT NULL COMMENT 'house/park/enterprise对应的ID',
  `type` int(4) NULL DEFAULT NULL COMMENT 'house:1 sell_park:2 lease_park:3 storage:4 plant:5 land:6',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_common_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_common_dict`;
CREATE TABLE `t_common_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `dict_code` int(255) NULL DEFAULT NULL COMMENT '编码',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `seq` int(255) NULL DEFAULT 0 COMMENT '顺序',
  `status` int(4) NULL DEFAULT 1,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_common_dict
-- ----------------------------
INSERT INTO `t_common_dict` VALUES (9, 'tag', 1, '品牌房企', 0, 1, '标签类', '2019-01-08 10:20:50', '2019-01-08 10:20:50');
INSERT INTO `t_common_dict` VALUES (10, 'tag', 2, '绿化率高', 0, 1, '标签类', '2019-01-08 10:21:20', '2019-01-08 10:21:25');
INSERT INTO `t_common_dict` VALUES (11, 'tag', 3, '低密度', 0, 1, '标签类', '2019-01-08 10:21:44', '2019-01-08 10:21:44');
INSERT INTO `t_common_dict` VALUES (12, 'floor', 1, '一层', 0, 1, '楼层', '2019-01-08 10:23:08', '2019-01-08 10:23:08');
INSERT INTO `t_common_dict` VALUES (13, 'floor', 2, '二层', 0, 1, '楼层', '2019-01-08 10:23:32', '2019-01-08 10:23:38');
INSERT INTO `t_common_dict` VALUES (14, 'floor', 3, '二层以上', 0, 1, '楼层', '2019-01-08 10:24:03', '2019-01-08 10:24:03');
INSERT INTO `t_common_dict` VALUES (15, 'floor', 4, '独栋', 0, 1, '楼层', '2019-01-08 10:24:36', '2019-01-08 10:24:36');
INSERT INTO `t_common_dict` VALUES (16, 'floor', 5, '独院', 0, 1, '楼层', '2019-01-08 10:24:52', '2019-01-08 10:24:52');

-- ----------------------------
-- Table structure for t_company_info
-- ----------------------------
DROP TABLE IF EXISTS `t_company_info`;
CREATE TABLE `t_company_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '``',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公司名',
  `user_id` bigint(20) NOT NULL COMMENT '管理员',
  `license` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '营业执照图',
  `address` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公司地址',
  `line_business` varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业务范围',
  `register_date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '注册日期',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公司简介',
  `contacts` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` int(4) NOT NULL DEFAULT 1 COMMENT '是否可用 0 删除 1可用',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_company_user_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_company_user_mapping`;
CREATE TABLE `t_company_user_mapping`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司ID',
  `user_id` bigint(20) NOT NULL,
  `agree` int(4) NOT NULL COMMENT '是否同意加入公司 0 待审核 1 同意 2 不同意',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_enterprise_info
-- ----------------------------
DROP TABLE IF EXISTS `t_enterprise_info`;
CREATE TABLE `t_enterprise_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `main_business` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主营业务',
  `enterprise_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经营地址',
  `register_date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成立日期',
  `product_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品详情',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司简介',
  `contacts` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_ts` datetime(0) NULL DEFAULT NULL,
  `update_ts` datetime(0) NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT NULL COMMENT ' 0已删除  1未删除 ',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_follow_info
-- ----------------------------
DROP TABLE IF EXISTS `t_follow_info`;
CREATE TABLE `t_follow_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `follow_id` bigint(20) NOT NULL COMMENT '关注对应的id',
  `follow_type` int(4) NOT NULL COMMENT '1 厂房 2 仓库 3 土地 4 园区',
  `user_id` bigint(20) NOT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_house_info
-- ----------------------------
DROP TABLE IF EXISTS `t_house_info`;
CREATE TABLE `t_house_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `country` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县',
  `street` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道',
  `area` int(10) NOT NULL COMMENT '面积',
  `selling_price` int(5) NULL DEFAULT NULL COMMENT '售价 万/元',
  `electricity` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电力 kv',
  `house_type` int(4) NOT NULL COMMENT '房源类型 1 厂房 2 仓库 3 土地',
  `house_number` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门牌号',
  `house_use_type` int(4) NULL DEFAULT NULL COMMENT '1 求租 2 求购 3 出租 4 出售',
  `floor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '楼层',
  `fire_control` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消防',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `status` int(4) NOT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `background` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_house_type
-- ----------------------------
DROP TABLE IF EXISTS `t_house_type`;
CREATE TABLE `t_house_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房源类型',
  `house_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房源类型代码',
  `status` int(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_inform_house_record
-- ----------------------------
DROP TABLE IF EXISTS `t_inform_house_record`;
CREATE TABLE `t_inform_house_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `house_id` bigint(20) NOT NULL COMMENT '出租房ID',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_inform_lease_house_record
-- ----------------------------
DROP TABLE IF EXISTS `t_inform_lease_house_record`;
CREATE TABLE `t_inform_lease_house_record`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `lease_house_id` bigint(20) NOT NULL COMMENT '求租房ID',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_land_info
-- ----------------------------
DROP TABLE IF EXISTS `t_land_info`;
CREATE TABLE `t_land_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `county` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县/区',
  `area` int(10) NOT NULL COMMENT '亩',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `type` int(255) NULL DEFAULT NULL COMMENT '1 求租 2 求购 3 出租 4 出售',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lease_park_info
-- ----------------------------
DROP TABLE IF EXISTS `t_lease_park_info`;
CREATE TABLE `t_lease_park_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `county` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县/区',
  `address` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
  `park_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '园区名',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `total_area` int(10) NULL DEFAULT NULL COMMENT '总建筑面积 m*2',
  `single_area` int(10) NULL DEFAULT NULL COMMENT '单栋建筑面积 m*2',
  `unit_price` int(10) NULL DEFAULT NULL COMMENT '单价 元/m*2/天',
  `fire_control` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消防等级',
  `enterprise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已入住企业',
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '适用企业',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '园区介绍',
  `status` int(4) NULL DEFAULT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_info`;
CREATE TABLE `t_menu_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单ID 0代表没有父菜单',
  `status` tinyint(255) NOT NULL COMMENT '菜单状态 0删除 1可用',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_plant_info
-- ----------------------------
DROP TABLE IF EXISTS `t_plant_info`;
CREATE TABLE `t_plant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `county` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县/区',
  `area` int(10) NOT NULL COMMENT '面积',
  `business` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `type` int(4) NULL DEFAULT NULL COMMENT '1 求租 2 求购 3 出租 4 出售',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(4) NOT NULL DEFAULT 1,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_record_info
-- ----------------------------
DROP TABLE IF EXISTS `t_record_info`;
CREATE TABLE `t_record_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '跟进的记录',
  `userId` bigint(20) NOT NULL,
  `house_id` bigint(20) NOT NULL COMMENT '厂房、仓库、园区、土地对应的ID',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(4) NOT NULL DEFAULT 1 COMMENT '是否删除 0 删除 1 可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role_info
-- ----------------------------
DROP TABLE IF EXISTS `t_role_info`;
CREATE TABLE `t_role_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名',
  `role_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色的唯一值',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL COMMENT '角色状态 0删除 1可用',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role_menu_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu_mapping`;
CREATE TABLE `t_role_menu_mapping`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sell_park_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sell_park_info`;
CREATE TABLE `t_sell_park_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `county` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县/区',
  `address` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
  `park_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '园区名',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `total_area` int(10) NULL DEFAULT NULL COMMENT '面积 m*2',
  `total_price` int(10) NULL DEFAULT NULL COMMENT '总价格 万/元',
  `unit_price` int(10) NULL DEFAULT NULL COMMENT '单价 元/m*2',
  `interest` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产权',
  `decoration` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '装修',
  `open_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开盘时间',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '园区介绍',
  `status` int(4) NULL DEFAULT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_storage_info
-- ----------------------------
DROP TABLE IF EXISTS `t_storage_info`;
CREATE TABLE `t_storage_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
  `county` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '县/区',
  `area` int(10) NOT NULL COMMENT '面积/m*2',
  `contacts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `type` int(255) NOT NULL COMMENT '1 求租 2 求购 3 出租 4 出售',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_third_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_third_user_info`;
CREATE TABLE `t_third_user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '三方（weichat weibo qq）',
  `uuid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '三方唯一标识',
  `portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_trade_info
-- ----------------------------
DROP TABLE IF EXISTS `t_trade_info`;
CREATE TABLE `t_trade_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行业名',
  `trade_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `user_type` int(11) NOT NULL COMMENT '用户类型 1 个人 2 经纪人',
  `integral` int(10) NOT NULL DEFAULT 5000 COMMENT '积分',
  `portrait` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `status` int(255) NULL DEFAULT NULL COMMENT '用户状态 0 删除 1 可用',
  `create_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
