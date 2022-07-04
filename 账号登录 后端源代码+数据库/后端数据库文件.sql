/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : jdbc

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/03/2020 20:05:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phoneNum` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('14908d88-dad6-4f45-bd17-4315295ca607', NULL, NULL, NULL);
INSERT INTO `t_user` VALUES ('6a16e423-4f5b-49b3-bcaf-8e4e290fa452', '1', '1', 'hex');
INSERT INTO `t_user` VALUES ('8367924b-8f60-4fee-868f-48694b83678f', NULL, NULL, NULL);
INSERT INTO `t_user` VALUES ('8612fa55-cf46-49e1-8ad1-0b65b3632342', NULL, NULL, NULL);
INSERT INTO `t_user` VALUES ('c730fe4a-5fc1-4df3-ba27-fa1d40523191', '19924812037', '13553417603', 'hex');
INSERT INTO `t_user` VALUES ('d7e3243d-3cd5-49f9-995b-e9ac8baba2b0', '1111', '1111', '111');
INSERT INTO `t_user` VALUES ('e102b701-f610-4103-9db9-f87083145e29', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
