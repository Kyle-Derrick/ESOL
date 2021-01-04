/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MariaDB
 Source Server Version : 100210
 Source Host           : localhost:3306
 Source Schema         : esol

 Target Server Type    : MariaDB
 Target Server Version : 100210
 File Encoding         : 65001

 Date: 03/01/2021 15:54:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for exm_question_lib
-- ----------------------------
DROP TABLE IF EXISTS `exm_question_lib`;
CREATE TABLE `exm_question_lib`  (
  `question_lib_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考题编号',
  `question_kind` int(11) NULL DEFAULT NULL COMMENT '考题类型（1单选2多选3判断）',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_a` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_b` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_c` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_d` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ref_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `analysis` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `vsrsion` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`question_lib_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2914 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exm_result
-- ----------------------------
DROP TABLE IF EXISTS `exm_result`;
CREATE TABLE `exm_result`  (
  `result_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考试结果编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `test_paper_id` int(11) NULL DEFAULT NULL COMMENT '考试编号',
  `duration` int(11) NULL DEFAULT NULL COMMENT '考试耗时',
  `result_date` date NULL DEFAULT NULL COMMENT '考试日期',
  `sorce` int(11) NULL DEFAULT NULL COMMENT '考试成绩',
  `sucess_num` int(11) NULL DEFAULT NULL COMMENT '正确率',
  `error_num` int(11) NULL DEFAULT NULL COMMENT '错误题数',
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`result_id`) USING BTREE,
  INDEX `FK_fk_test_paper_reuslt`(`test_paper_id`) USING BTREE,
  INDEX `FK_fk_user_reslt`(`user_id`) USING BTREE,
  CONSTRAINT `FK_fk_test_paper_reuslt` FOREIGN KEY (`test_paper_id`) REFERENCES `exm_test_paper` (`test_paper_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_fk_user_reslt` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exm_result_question
-- ----------------------------
DROP TABLE IF EXISTS `exm_result_question`;
CREATE TABLE `exm_result_question`  (
  `result_question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考试答案编号',
  `result_id` int(11) NULL DEFAULT NULL COMMENT '考试结果编号',
  `question_lib_id` int(11) NULL DEFAULT NULL,
  `question_kind` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考题类型（1单选2多选3判断）',
  `title` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_a` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_b` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_c` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_d` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ref_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `analysis` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `answer` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`result_question_id`) USING BTREE,
  INDEX `FK_fk_result_question`(`result_id`) USING BTREE,
  CONSTRAINT `FK_fk_result_question` FOREIGN KEY (`result_id`) REFERENCES `exm_result` (`result_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 445 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exm_test_paper
-- ----------------------------
DROP TABLE IF EXISTS `exm_test_paper`;
CREATE TABLE `exm_test_paper`  (
  `test_paper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考试编号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `start_date` date NULL DEFAULT NULL COMMENT '考试起始时间',
  `end_date` date NULL DEFAULT NULL COMMENT '考试截止时间',
  `duration` int(11) NULL DEFAULT NULL COMMENT '考试时长',
  `max_num` int(11) NULL DEFAULT NULL COMMENT '考试次数',
  `sorce_rule` int(11) NULL DEFAULT NULL COMMENT '1最高分2平均分',
  `total_sorce` int(11) NULL DEFAULT NULL COMMENT '总分',
  `question_rule` int(11) NULL DEFAULT NULL COMMENT '1预先生成2考试随机',
  `radio_num` int(11) NULL DEFAULT NULL,
  `mulitple_num` int(11) NULL DEFAULT NULL,
  `judge_num` int(11) NULL DEFAULT NULL,
  `total_num` int(11) NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`test_paper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exm_test_question
-- ----------------------------
DROP TABLE IF EXISTS `exm_test_question`;
CREATE TABLE `exm_test_question`  (
  `test_question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷试题编号',
  `question_lib_id` int(11) NULL DEFAULT NULL COMMENT '考题编号',
  `test_paper_id` int(11) NULL DEFAULT NULL COMMENT '考试编号',
  `sequence` int(11) NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL COMMENT '试卷试题编号',
  PRIMARY KEY (`test_question_id`) USING BTREE,
  INDEX `FK_fk_question_lib_question`(`question_lib_id`) USING BTREE,
  INDEX `FK_fk_test_paper_question`(`test_paper_id`) USING BTREE,
  CONSTRAINT `FK_fk_question_lib_question` FOREIGN KEY (`question_lib_id`) REFERENCES `exm_question_lib` (`question_lib_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_fk_test_paper_question` FOREIGN KEY (`test_paper_id`) REFERENCES `exm_test_paper` (`test_paper_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `leader` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门负责人',
  `info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级部门编号',
  `full_id` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门编号',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pass_word` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_kind` int(11) NULL DEFAULT NULL COMMENT '用户类型:1管理员2考试用户',
  `version` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `FK_fk_dept_user`(`dept_id`) USING BTREE,
  CONSTRAINT `FK_fk_dept_user` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`dept_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
