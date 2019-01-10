/*
Navicat MySQL Data Transfer

Source Server         :
Source Server Version : 50544
Source Host           :
Source Database       : contract

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2019-01-10 11:09:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contract_id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_sign_year` varchar(20) NOT NULL,
  `item_coding` varchar(100) NOT NULL,
  `sale_person` varchar(40) NOT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `contract_amount` varchar(255) DEFAULT NULL,
  `invoice_amount_2015` varchar(255) DEFAULT NULL,
  `recovered_amount_2015` varchar(255) DEFAULT NULL,
  `invoice_amount_2016` varchar(255) DEFAULT NULL,
  `recovered_amount_date_2016` varchar(100) DEFAULT NULL,
  `recovered_amount_2016` varchar(255) DEFAULT NULL,
  `invoice_amount_2017` varchar(255) DEFAULT NULL,
  `recovered_amount_date_2017` varchar(100) DEFAULT NULL,
  `recovered_amount_2017` varchar(255) DEFAULT NULL,
  `invoice_amount_date_2018` varchar(255) DEFAULT NULL,
  `invoice_amount_2018` varchar(100) DEFAULT NULL,
  `recovered_amount_date_2018` varchar(255) DEFAULT NULL,
  `recovered_amount_2018` varchar(100) DEFAULT NULL,
  `due` varchar(255) DEFAULT NULL,
  `invoice_amount` varchar(255) DEFAULT NULL,
  `unbilled_amount` varchar(255) DEFAULT NULL,
  `receivable_total` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `pass_word` varchar(50) DEFAULT NULL,
  `enabled` varchar(5) DEFAULT NULL,
  `credential` varchar(5) DEFAULT NULL,
  `locked` varchar(5) DEFAULT NULL,
  `expired` varchar(5) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_name` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT 'EumJi' COMMENT '昵称',
  `phone` char(11) DEFAULT NULL COMMENT '电话号码',
  `e_mail` varchar(50) DEFAULT 'eumji025@gmail.com' COMMENT '邮箱',
  `signature` varchar(2000) DEFAULT NULL COMMENT '个性签名',
  `address` varchar(50) DEFAULT NULL COMMENT '地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
