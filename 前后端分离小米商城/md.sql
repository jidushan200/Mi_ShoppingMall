/*
 Navicat Premium Data Transfer

 Source Server         : sql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : md

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 08/08/2022 16:04:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_address_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `FK_address_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (7, 7, '张飞', '18268871431', '浙江省金华市', '1', '1');
INSERT INTO `address` VALUES (16, 11, 'swq', '15009873738', '北京市东城区', 'swq', '');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (10, 'admin', '123456', '0');
INSERT INTO `admin` VALUES (12, 'lyh', '123', '1');
INSERT INTO `admin` VALUES (16, 'a', '123', '0');
INSERT INTO `admin` VALUES (17, 'story', '123', '0');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `goods_id` int(0) NULL DEFAULT NULL,
  `goods_num` int(0) NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_cart_user_id`(`user_id`) USING BTREE,
  INDEX `FK_cart_goods_id`(`goods_id`) USING BTREE,
  CONSTRAINT `FK_cart_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_cart_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (21, 7, 15, 1, '');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `index_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `detail_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'Redmi Note 11T Pro', '天玑8100｜144Hz高配LCD屏幕｜6400万像素｜VC液冷散热', 1699.00, '手机', '/images/goods/indexImg1.png', '/images/goods/detailImg1.png');
INSERT INTO `goods` VALUES (8, 'Redmi Note 11SE', '「新品热卖，到手价999元起」天玑700｜18W快充｜5000mAh大电池｜6.5\"FHD+全高清屏｜双卡双5G｜90Hz 四挡变速高刷屏｜4096级 亮度调节｜全系128GB 大内存｜大功率扬声器｜4800万 高清双摄', 999.00, '手机', '/images/goods/indexImg8.webp', '/images/goods/detailImg8.png');
INSERT INTO `goods` VALUES (9, 'Xiaomi Civi 1S', '原生美肌人像2.0 | 4D光追焕颜美妆技术 | 3200万高清质感自拍 | 升级人眼追焦 | 6400万AI三摄 | 3D曲面OLED柔性屏 | 120Hz+Dolby Vision | 55W有线闪充 | 立体声双扬声器 | 多功能NFC', 2299.00, '手机', '/images/goods/indexImg9.webp', '/images/goods/detailImg9.png');
INSERT INTO `goods` VALUES (10, '小米电视6 65\" OLED 65英寸', 'OLED自发光屏 | 百万级对比度 | 4.6mm超薄全面屏', 6699.00, '电视', '/images/goods/indexImg10.webp', '/images/goods/detailImg10.jpg');
INSERT INTO `goods` VALUES (11, '小米电视6至尊版 65英寸', '百级分区背光 | 双120Hz高刷 | 4.5GB+64GB', 7199.00, '电视', '/images/goods/indexImg11.webp', '/images/goods/detailImg11.jpg');
INSERT INTO `goods` VALUES (12, '小米电视ES55 2022款 55英寸', '多分区背光 | MEMC动态补偿 | 杜比视界', 2599.00, '电视', '/images/goods/indexImg12.webp', '/images/goods/detailImg12.jpg');
INSERT INTO `goods` VALUES (13, 'Redmi Book Pro 15 2022', '全新12代英特尔处理器 | Windows 11 家庭中文版 | 可选RTX 2050高性能独立显卡 | 3.2K 90Hz 原色超清屏', 5299.00, '笔记本', '/images/goods/indexImg13.webp', '/images/goods/detailImg13.png');
INSERT INTO `goods` VALUES (14, 'Redmi G 2021锐龙版 3050Ti版本', 'RTX™ 3050Ti光追独显与硬核锐龙芯梦幻联合，迎来游戏性能释放新境界。视野再突破，天生为赢而战，全方位为热爱进阶，Redmi G 2021 锐龙版，实力超能打。', 5799.00, '笔记本', '/images/goods/indexImg14.webp', '/images/goods/detailImg14.png');
INSERT INTO `goods` VALUES (15, 'Pro 14 增强版 i5 独显 - Win11', '2.5K超视网膜全面屏 | 一体精雕工艺 | Windows 11', 4799.00, '笔记本', '/images/goods/indexImg15.webp', '/images/goods/detailImg15.png');
INSERT INTO `goods` VALUES (16, '小米平板5', '骁龙860芯片 | 2.5K超清屏 | 120Hz高刷新率｜8720mAh大电池', 1799.00, '平板', '/images/goods/indexImg16.webp', '/images/goods/detailImg16.png');
INSERT INTO `goods` VALUES (17, 'Xiaomi 12S Ultra', '1 英寸大底专业主摄｜120倍潜望式长焦｜128°超广角镜头', 5999.00, '手机', '/images/goods/indexImg17.webp', '/images/goods/detailImg17.png');
INSERT INTO `goods` VALUES (19, '巨省电 | 米家空调 1.5匹 新1级能效 睡眠版', '强劲制冷 | 安静舒适 | 智能互联', 2099.00, '家电', '/images/goods/indexImg19.jpg', '/images/goods/detailImg19.jpg');
INSERT INTO `goods` VALUES (20, '巨省电 3匹|变频|新一级能效（鎏金款）', '舒适送风 格调出众 3匹变频 新一级能效', 5499.00, '家电', '/images/goods/indexImg20.jpg', '/images/goods/detailImg20.jpg');
INSERT INTO `goods` VALUES (21, '米家风冷对开门冰箱 483L', '风冷无霜不结冰 / 环绕立体出风，长久锁水保鲜 / 全直流双变频，高效制冷低噪节能 / 低能耗，1天不到1度电 / 20格储物分区 / 自动恒温校准', 2299.00, '家电', '/images/goods/indexImg21.png', '/images/goods/detailImg21.png');
INSERT INTO `goods` VALUES (22, 'Redmi电竞路由器AX5400', '2.5G次世代网口 | 高通专业级芯片 | 双WAN接入+双LAN聚合，支持双运营商宽带叠加 | 赠330天游戏加速器', 549.00, '路由器', '/images/goods/indexImg22.webp', '/images/goods/detailImg22.jpg');
INSERT INTO `goods` VALUES (23, '小米路由器AX6000', 'WiFi6增强版，宽带提速降费，匹配设备预先准备 | 6000兆级无线速率 | 6路独立信号放大器 | 支持Mesh组网，全屋无死角覆盖', 549.00, '路由器', '/images/goods/indexImg23.jpg', '/images/goods/detailImg23.jpg');
INSERT INTO `goods` VALUES (24, '小米路由器AX9000', '电竞旗舰款，配备电竞专属频段，网游爱好者必备 | 疾速WiFi 6 | 9000兆级疾速体验 | 电竞专属深度优化 | 三频Mesh组网，全屋无死角覆盖', 1299.00, '路由器', '/images/goods/indexImg24.jpg', '/images/goods/detailImg24.jpg');

-- ----------------------------
-- Table structure for goods_color
-- ----------------------------
DROP TABLE IF EXISTS `goods_color`;
CREATE TABLE `goods_color`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_goods_config_goods`(`goods_id`) USING BTREE,
  CONSTRAINT `FK_goods_config_goods` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_color
-- ----------------------------
INSERT INTO `goods_color` VALUES (10, 8, '暗影黑');
INSERT INTO `goods_color` VALUES (11, 8, '深空蓝');
INSERT INTO `goods_color` VALUES (12, 9, '怦怦粉');
INSERT INTO `goods_color` VALUES (13, 9, '奇迹阳光');
INSERT INTO `goods_color` VALUES (14, 9, '闪闪黑');
INSERT INTO `goods_color` VALUES (15, 10, '黑色');
INSERT INTO `goods_color` VALUES (16, 11, '灰色');
INSERT INTO `goods_color` VALUES (17, 12, '灰色');
INSERT INTO `goods_color` VALUES (18, 14, '黑色');
INSERT INTO `goods_color` VALUES (19, 15, '灰色');
INSERT INTO `goods_color` VALUES (20, 16, '绿色');
INSERT INTO `goods_color` VALUES (21, 16, '黑色');
INSERT INTO `goods_color` VALUES (22, 16, '白色');
INSERT INTO `goods_color` VALUES (26, 19, '白色');
INSERT INTO `goods_color` VALUES (27, 20, '白色');
INSERT INTO `goods_color` VALUES (28, 21, '银色');
INSERT INTO `goods_color` VALUES (29, 22, '黑色');
INSERT INTO `goods_color` VALUES (30, 23, '黑色');
INSERT INTO `goods_color` VALUES (51, 24, '黑色');
INSERT INTO `goods_color` VALUES (59, 17, '经典黑');
INSERT INTO `goods_color` VALUES (60, 17, '冷杉绿');
INSERT INTO `goods_color` VALUES (64, 1, '时光蓝');
INSERT INTO `goods_color` VALUES (65, 1, '子夜黑');
INSERT INTO `goods_color` VALUES (66, 1, '原子银');

-- ----------------------------
-- Table structure for goods_config
-- ----------------------------
DROP TABLE IF EXISTS `goods_config`;
CREATE TABLE `goods_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NULL DEFAULT NULL,
  `config` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_goods_config_goodsId`(`goods_id`) USING BTREE,
  CONSTRAINT `FK_goods_config_goodsId` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_config
-- ----------------------------
INSERT INTO `goods_config` VALUES (10, 8, '4GB+128GB');
INSERT INTO `goods_config` VALUES (11, 8, '8GB+128GB');
INSERT INTO `goods_config` VALUES (12, 9, '8GB+128GB');
INSERT INTO `goods_config` VALUES (13, 9, '8GB+256GB');
INSERT INTO `goods_config` VALUES (14, 9, '12GB+256GB');
INSERT INTO `goods_config` VALUES (15, 13, 'i7-12650H/RTX 2050/16G/512G');
INSERT INTO `goods_config` VALUES (16, 13, 'i5-12450H/RTX 2050/16G/512G');
INSERT INTO `goods_config` VALUES (17, 13, 'i5-12450H/集显/16G/512G');
INSERT INTO `goods_config` VALUES (18, 16, '6GB+128GB');
INSERT INTO `goods_config` VALUES (19, 16, '6GB+256GB');
INSERT INTO `goods_config` VALUES (20, 16, '8GB+256GB');
INSERT INTO `goods_config` VALUES (56, 17, '8GB+256GB');
INSERT INTO `goods_config` VALUES (57, 17, '12GB+256GB');
INSERT INTO `goods_config` VALUES (58, 17, '12GB+512GB');
INSERT INTO `goods_config` VALUES (62, 1, '6GB+128GB');
INSERT INTO `goods_config` VALUES (63, 1, '8GB+128GB');
INSERT INTO `goods_config` VALUES (64, 1, '8GB+256GB');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creation_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_order_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `FK_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (6, 7, '张飞', '浙江省金华市adfdsaf', '2022-06-23 16:29:32');
INSERT INTO `order` VALUES (7, 7, '张飞', '浙江省金华市1', '2022-06-23 16:35:25');
INSERT INTO `order` VALUES (8, 7, '张飞', '浙江省金华市1', '2022-06-23 16:37:34');
INSERT INTO `order` VALUES (9, 7, '张飞', '浙江省金华市1', '2022-06-23 17:48:43');
INSERT INTO `order` VALUES (10, 11, 'swq', '北京市东城区swq', '2022-08-08 12:01:32');
INSERT INTO `order` VALUES (11, 11, 'swq', '北京市东城区swq', '2022-08-08 16:03:20');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `order_id` int(0) NULL DEFAULT NULL,
  `goods_id` int(0) NULL DEFAULT NULL,
  `goods_num` int(0) NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_orderdetail_order_id`(`order_id`) USING BTREE,
  INDEX `FK_orderdetail_goods_id`(`goods_id`) USING BTREE,
  CONSTRAINT `FK_orderdetail_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_orderdetail_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES (4, 6, 11, 2, '灰色');
INSERT INTO `orderdetail` VALUES (5, 6, 15, 1, '灰色');
INSERT INTO `orderdetail` VALUES (6, 6, 1, 2, '6GB+128GB 时光蓝');
INSERT INTO `orderdetail` VALUES (7, 6, 9, 1, '12GB+256GB 怦怦粉');
INSERT INTO `orderdetail` VALUES (8, 7, 9, 2, '12GB+256GB 怦怦粉');
INSERT INTO `orderdetail` VALUES (9, 8, 9, 3, '8GB+128GB 怦怦粉');
INSERT INTO `orderdetail` VALUES (10, 9, 1, 1, '6GB+128GB 子夜黑');
INSERT INTO `orderdetail` VALUES (11, 10, 8, 1, '');
INSERT INTO `orderdetail` VALUES (12, 11, 16, 1, '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `admin_id` int(0) NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_user_admin`(`admin_id`) USING BTREE,
  CONSTRAINT `FK_user_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (7, 10, 'asdf', '男', '/images/avatar/headImg7.jpg');
INSERT INTO `user` VALUES (10, 16, NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, 17, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
