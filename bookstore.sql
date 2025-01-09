/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : bookstore

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 09/01/2025 10:35:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `book_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `directory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stock_quantity` int NULL DEFAULT NULL,
  `storage_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `suppliers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `min_stock_quantity` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '路遥', '74', 'https://file.dzzgsw.com/newdisk4/9/f9a507f12e4440128bbb351913f1b2ca/31346f28d0c340b2b70ad641868f8f38/cover.png?wDWm', 'Lorem in', '人生', 45, '华中科技大学出版社', 61, '东区102', '郭颂', '平凡的世界', 100);
INSERT INTO `book` VALUES (2, '余华', '10', 'http://img.alicdn.com/i2/2256772368/O1CN019Xns7q1TMaXxdEHnb_!!2256772368.jpg', 'incididunt labore', 'sed fugiat', 10, '北京大学出版社', 85, '东区103', 'adipisicing dolore id laboris proident', '活着', 5);
INSERT INTO `book` VALUES (3, '加布里埃尔·加西亚·马尔克斯', '90', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.ttAy0afdN3NUdR_tmnTiRwHaHa?w=200&h=201&c=7&r=0&o=5&dpr=1.3&pid=1.7', 'id Ut reprehenderit', '魔幻现实主义、家族史、孤独', 21, '中华书局', 68, '经典文学103', 'quis commodo irure laborum in', '百年孤独', 5);
INSERT INTO `book` VALUES (4, '刘慈欣', '52', 'https://tse2-mm.cn.bing.net/th/id/OIP-C.SiJFc1WgXQtGqkRxcvKPPgHaKv?w=156&h=187&c=7&r=0&o=5&dpr=1.3&pid=1.7', 'Excepteur cupidatat cillum', '科幻 太空', 60, '人民出版社', 37, '202', 'velit adipisicing do', '三体', 5);
INSERT INTO `book` VALUES (5, '钱钟书', '40', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.R2v9Zb_53t4RVhtwSbKyHwAAAA?w=156&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7', 'culpa cupidatat tempor irure', '幽默、婚姻、社会讽刺', 96, '清华出版社', 38, '302', 'tempor adipisicing laborum exercitation Ut', '围城', 5);
INSERT INTO `book` VALUES (6, '卡勒德·胡赛尼', '91', 'https://img.alicdn.com/bao/uploaded/i1/901499946/O1CN01ZfTAMr2NLJqsIxCiJ_!!0-item_pic.jpg', 'magna eiusmod qui in', '友谊、救赎、阿富汗', 62, '武汉大学出版社', 71, '302', 'esse anim in', '追风筝的人', 5);
INSERT INTO `book` VALUES (7, 'J.K.罗琳', '7', 'http://dummyimage.com/400x400', 'do labore sit', ' 魔法、冒险、成长', 97, '美国出版社', 45, '404', 'sed deserunt officia', '哈利·波特', 5);
INSERT INTO `book` VALUES (8, '安托万·德·圣埃克苏佩里', '19', 'http://dummyimage.com/400x400', 'quis consectetur ullamco', '哲理、成长、友谊', 79, '美国出版社', 51, '404', 'ad', '小王子', 5);
INSERT INTO `book` VALUES (9, '老舍', '79', 'http://dummyimage.com/400x400', 'cupidatat cillum sed eiusmod exercitation', '社会底层、奋斗、悲剧', 7, '华中科技大学出版社', 5, '102', 'qui anim dolore amet', '骆驼祥子', 5);
INSERT INTO `book` VALUES (10, '村上春树', '36', 'http://dummyimage.com/400x400', 'laborum aliquip cupidatat', ' 青春、爱情、成长', 46, '日本出版社', 76, '405', 'eiusmod Duis anim nulla', '挪威的森林', 5);
INSERT INTO `book` VALUES (11, '夏洛蒂·勃朗特', '57', 'http://dummyimage.com/400x400', 'esse', ' 爱情、自尊、女性独立', 11, '美国出版社', 16, '404', 'sed sit deserunt exercitation', '简爱', 5);
INSERT INTO `book` VALUES (12, '乔治·奥威尔', NULL, 'http://dummyimage.com/400x400', 'laborum aliquip cupidatat', '反乌托邦、极权、监控', 46, '美国出版社', 76, '404', 'eiusmod Duis anim nulla', '1984', 5);
INSERT INTO `book` VALUES (13, '雨果', NULL, 'http://dummyimage.com/400x400', 'laborum aliquip cupidatat', '社会问题、人性、救赎', 46, '美国出版社', 76, '408', 'eiusmod Duis anim nulla', '悲惨世界', 5);
INSERT INTO `book` VALUES (14, '乔治·奥威尔', NULL, 'https://img12.360buyimg.com/n0/jfs/t15469/335/837283764/448629/cf77cd16/5a3a3629N2e7c5d6e.jpg', NULL, '政治讽刺、革命、权力腐败', 2, '北京大学出版社', 15, 'dd', 'gs', '动物农场', 15);
INSERT INTO `book` VALUES (17, '哈珀·李', NULL, 'https://img12.360buyimg.com/n0/jfs/t15469/335/837283764/448629/cf77cd16/5a3a3629N2e7c5d6e.jpg', NULL, '人生', 4, '中华书局', 5, '东区101', 'gs', '杀死一只知更鸟', 5);
INSERT INTO `book` VALUES (19, '曹雪芹', NULL, 'https://so1.360tres.com/t0198f3032b4e942d43.jpg', NULL, '社会 爱情', 100, '中华书局', 100, '东区101', 'gs', '红楼梦', 5);
INSERT INTO `book` VALUES (21, '列夫托尔斯泰', '30', 'http://dummyimage.com/400x400', 'id', '战争', 14, 'velit culpa', 49, '东区101', 'gs', '安娜·卡列尼娜', 5);
INSERT INTO `book` VALUES (23, '吴承恩', NULL, 'https://p1.ssl.qhimg.com/t01b947957eccf72a3b.jpg', NULL, '神话', 4, '武汉大学出版社', 17, '东区101', 'gs', '西游记', 5);
INSERT INTO `book` VALUES (24, '罗贯中', NULL, 'https://img.alicdn.com/bao/uploaded/i3/2608525902/O1CN01aiqrZC1tTACKtQvRu_!!0-item_pic.jpg', NULL, '战争 历史', 34, '武汉大学出版社', 10, '东区101', 'gs', '三国演义', 5);
INSERT INTO `book` VALUES (26, '玛格丽特·米切尔', NULL, 'https://img12.360buyimg.com/n0/jfs/t15469/335/837283764/448629/cf77cd16/5a3a3629N2e7c5d6e.jpg', NULL, '英雄', 19, '华中科技大学出版社', 13, '东区101', 'gs', '飘', 5);
INSERT INTO `book` VALUES (27, '郭颂', NULL, 'https://img12.360buyimg.com/n0/jfs/t18460/285/1723454385/162282/94d680fc/5ad5c2a1Nbdcf8ea3.jpg', NULL, '科普', 6, '华中科技大学出版社', 5, '东区101', 'gs', '十万个为什么', 5);
INSERT INTO `book` VALUES (28, '张爱玲', '51', 'http://dummyimage.com/400x400', 'cupidatat ad velit nisi commodo', '爱情', 9, '武汉大学出版社', 82, '101', NULL, '百年好合', 82);
INSERT INTO `book` VALUES (29, ' 阿瑟·柯南·道尔', '24', 'http://dummyimage.com/400x400', 'anim ad id magna', '侦探、悬疑、推理', 92, '华中科技大学出版社', 54, '102', NULL, '福尔摩斯探案集', 54);
INSERT INTO `book` VALUES (30, '施耐庵', NULL, 'https://img12.360buyimg.com/n0/jfs/t15469/335/837283764/448629/cf77cd16/5a3a3629N2e7c5d6e.jpg', NULL, '英雄 义气', 4, '人民出版社', 48, '东区202', NULL, '水浒传', 50);
INSERT INTO `book` VALUES (32, 'dd', NULL, 'https://img12.360buyimg.com/n0/jfs/t15469/335/837283764/448629/cf77cd16/5a3a3629N2e7c5d6e.jpg', NULL, '教学', 3, '华中科技大学出版社', 2, '102', NULL, '数据库', 5);

-- ----------------------------
-- Table structure for browse
-- ----------------------------
DROP TABLE IF EXISTS `browse`;
CREATE TABLE `browse`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NULL DEFAULT NULL,
  `browse_time` datetime(6) NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of browse
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_867x3yysb1f3jk41cv3vsoejj`(`customer_id` ASC) USING BTREE,
  CONSTRAINT `FKdebwvad6pp1ekiqy5jtixqbaj` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (1, 5);
INSERT INTO `cart` VALUES (2, 6);

-- ----------------------------
-- Table structure for cart_item
-- ----------------------------
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `cart_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK1uobyhgl1wvgt1jpccia8xxs3`(`cart_id` ASC) USING BTREE,
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart_item
-- ----------------------------
INSERT INTO `cart_item` VALUES (30, 1, 1, 1);
INSERT INTO `cart_item` VALUES (31, 2, 1, 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_balance` double NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `credit_level` enum('LEVEL_1','LEVEL_2','LEVEL_3','LEVEL_4','LEVEL_5') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_recharge` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, 25, '山西省永州市资中县', 'LEVEL_1', 'vip@qq.com', 'admin', '$2a$10$fBwCBiZp.t3wJuFI6G9i4uP3s/nloZol7sem2F5PmL3d7bEVLuX8.', 25);
INSERT INTO `customer` VALUES (5, 1009905.25, '广东省咸宁市大方县', 'LEVEL_5', '123456@qq.com', 'gs', '$2a$10$iS8ManTcWUypIyyJph8n0OQDI.YQZaOzdvMqhiexcoZ6Qj5fTJsfa', 1010282.5);
INSERT INTO `customer` VALUES (6, 1009010, '华科', 'LEVEL_5', 'ymx@163.com', 'ymx', '$2a$10$Um0cnRtg6X4tYtY1STrsQ.mG0xSomUnqOATHEZyNEo2W20f.yb6BW', 1010010);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_amount` double NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK1oduxyuuo3n2g98l3j7754vym`(`customer_id` ASC) USING BTREE,
  CONSTRAINT `FK1oduxyuuo3n2g98l3j7754vym` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'SHIPPED', 75, 5);
INSERT INTO `order` VALUES (6, 'SHIPPED', 144.75, 5);
INSERT INTO `order` VALUES (8, 'SHIPPED', 190.5, 5);
INSERT INTO `order` VALUES (9, 'SHIPPED', 7.5, 5);
INSERT INTO `order` VALUES (10, 'SHIPPED', 77.25, 5);
INSERT INTO `order` VALUES (11, 'SHIPPED', 30.75, 5);
INSERT INTO `order` VALUES (12, 'PENDING', 162.75, 5);
INSERT INTO `order` VALUES (13, 'SHIPPED', 99, 5);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NULL DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int NULL DEFAULT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`order_item_id`) USING BTREE,
  INDEX `FKs234mi6jususbx4b37k44cipy`(`order_id` ASC) USING BTREE,
  CONSTRAINT `FKs234mi6jususbx4b37k44cipy` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1, 2, 10, 10, 1);
INSERT INTO `order_item` VALUES (8, 5, 96, 1, 6);
INSERT INTO `order_item` VALUES (9, 7, 97, 1, 6);
INSERT INTO `order_item` VALUES (12, 6, 62, 3, 8);
INSERT INTO `order_item` VALUES (13, 30, 4, 2, 8);
INSERT INTO `order_item` VALUES (14, 4, 60, 1, 8);
INSERT INTO `order_item` VALUES (15, 2, 10, 1, 9);
INSERT INTO `order_item` VALUES (16, 2, 10, 2, 10);
INSERT INTO `order_item` VALUES (17, 3, 21, 1, 10);
INSERT INTO `order_item` VALUES (18, 6, 62, 1, 10);
INSERT INTO `order_item` VALUES (19, 2, 10, 2, 11);
INSERT INTO `order_item` VALUES (20, 3, 21, 1, 11);
INSERT INTO `order_item` VALUES (21, 2, 10, 1, 12);
INSERT INTO `order_item` VALUES (22, 3, 21, 1, 12);
INSERT INTO `order_item` VALUES (23, 1, 45, 2, 12);
INSERT INTO `order_item` VALUES (24, 5, 96, 1, 12);
INSERT INTO `order_item` VALUES (25, 6, 62, 1, 13);
INSERT INTO `order_item` VALUES (26, 4, 60, 1, 13);
INSERT INTO `order_item` VALUES (27, 2, 10, 1, 13);

-- ----------------------------
-- Table structure for out_of_stock_record
-- ----------------------------
DROP TABLE IF EXISTS `out_of_stock_record`;
CREATE TABLE `out_of_stock_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shortage_quantity` int NULL DEFAULT NULL,
  `book_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKld7hkhypwr9iq3k9p14nt3jbj`(`book_id` ASC) USING BTREE,
  CONSTRAINT `FKld7hkhypwr9iq3k9p14nt3jbj` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of out_of_stock_record
-- ----------------------------

-- ----------------------------
-- Table structure for purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE `purchase_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_price` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_order
-- ----------------------------
INSERT INTO `purchase_order` VALUES (23, NULL, 1772);

-- ----------------------------
-- Table structure for purchase_order_books
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order_books`;
CREATE TABLE `purchase_order_books`  (
  `purchase_order_id` bigint NOT NULL,
  `books_id` bigint NOT NULL,
  UNIQUE INDEX `UK_7lhxxexs3e9mn0el0nb83wb15`(`books_id` ASC) USING BTREE,
  INDEX `FK70il113pwyocx3aeahv68duca`(`purchase_order_id` ASC) USING BTREE,
  CONSTRAINT `FK70il113pwyocx3aeahv68duca` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKgmv0vrjbuqeipy6dvd8t3y4ys` FOREIGN KEY (`books_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_order_books
-- ----------------------------
INSERT INTO `purchase_order_books` VALUES (23, 1);
INSERT INTO `purchase_order_books` VALUES (23, 30);
INSERT INTO `purchase_order_books` VALUES (23, 32);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (1, '郭颂', '华中科技大学');
INSERT INTO `supplier` VALUES (4, '雷军', '武汉大学');
INSERT INTO `supplier` VALUES (5, 'gs', 'hello');
INSERT INTO `supplier` VALUES (6, '11111', '中华书局');
INSERT INTO `supplier` VALUES (7, '18870664566', '新供应商');
INSERT INTO `supplier` VALUES (8, 'dd', '北大');

-- ----------------------------
-- Table structure for supplier_book
-- ----------------------------
DROP TABLE IF EXISTS `supplier_book`;
CREATE TABLE `supplier_book`  (
  `supplier_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  INDEX `FKke1y0uri91fkg5m51ytrucvha`(`book_id` ASC) USING BTREE,
  INDEX `FKn4u4cithtn9vn9a814151yyuy`(`supplier_id` ASC) USING BTREE,
  CONSTRAINT `FKke1y0uri91fkg5m51ytrucvha` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKn4u4cithtn9vn9a814151yyuy` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier_book
-- ----------------------------
INSERT INTO `supplier_book` VALUES (7, 1);
INSERT INTO `supplier_book` VALUES (4, 1);
INSERT INTO `supplier_book` VALUES (5, 5);
INSERT INTO `supplier_book` VALUES (8, 10);
INSERT INTO `supplier_book` VALUES (6, 13);
INSERT INTO `supplier_book` VALUES (6, 21);
INSERT INTO `supplier_book` VALUES (1, 2);
INSERT INTO `supplier_book` VALUES (1, 1);

-- ----------------------------
-- Table structure for supplier_book_catalog
-- ----------------------------
DROP TABLE IF EXISTS `supplier_book_catalog`;
CREATE TABLE `supplier_book_catalog`  (
  `supplier_id` bigint NOT NULL,
  `book_catalog` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  INDEX `FKk74auws0mx1qwueakwnuqm4yp`(`supplier_id` ASC) USING BTREE,
  CONSTRAINT `FKk74auws0mx1qwueakwnuqm4yp` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier_book_catalog
-- ----------------------------
INSERT INTO `supplier_book_catalog` VALUES (4, 'aliqua');
INSERT INTO `supplier_book_catalog` VALUES (4, 'cillum cupidatat');
INSERT INTO `supplier_book_catalog` VALUES (5, 'cupidatat dolor in magna veniam');
INSERT INTO `supplier_book_catalog` VALUES (5, 'in velit mollit');

-- ----------------------------
-- View structure for view_customerorders
-- ----------------------------
DROP VIEW IF EXISTS `view_customerorders`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_customerorders` AS select `orders`.`OrderID` AS `OrderID`,`customers`.`FullName` AS `CustomerName`,`orders`.`TotalAmount` AS `TotalAmount`,`orders`.`OrderDate` AS `OrderDate`,`orders`.`OrderStatus` AS `OrderStatus` from (`orders` join `customers` on((`orders`.`CustomerID` = `customers`.`CustomerID`)));

-- ----------------------------
-- View structure for view_stockstatus
-- ----------------------------
DROP VIEW IF EXISTS `view_stockstatus`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_stockstatus` AS select `books`.`BookID` AS `BookID`,`books`.`Title` AS `Title`,`books`.`StockQuantity` AS `StockQuantity`,`books`.`Price` AS `Price` from `books`;

-- ----------------------------
-- Procedure structure for AddStock
-- ----------------------------
DROP PROCEDURE IF EXISTS `AddStock`;
delimiter ;;
CREATE PROCEDURE `AddStock`(IN p_BookID INT,
    IN p_Quantity INT)
BEGIN
    UPDATE Books
    SET StockQuantity = StockQuantity + p_Quantity
    WHERE BookID = p_BookID;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for ProcessOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `ProcessOrder`;
delimiter ;;
CREATE PROCEDURE `ProcessOrder`(IN p_OrderID INT)
BEGIN
    UPDATE Orders
    SET OrderStatus = 'Shipped'
    WHERE OrderID = p_OrderID;

    -- 减少库存
    UPDATE Books
    SET StockQuantity = StockQuantity - (
        SELECT Quantity FROM OrderDetails WHERE OrderDetails.OrderID = p_OrderID AND OrderDetails.BookID = Books.BookID
    )
    WHERE BookID IN (
        SELECT BookID FROM OrderDetails WHERE OrderID = p_OrderID
    );
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
