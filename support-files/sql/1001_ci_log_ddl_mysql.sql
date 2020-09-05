USE devops_ci_log;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_LOG_INDICES
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_LOG_INDICES`
(
    `ID`            bigint(20)  NOT NULL AUTO_INCREMENT,
    `BUILD_ID`      varchar(64) NOT NULL,
    `INDEX_NAME`    varchar(20) NOT NULL,
    `LAST_LINE_NUM` bigint(20)  NOT NULL DEFAULT '1' COMMENT '最后行号',
    `CREATE_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `UPDATED_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `ENABLE`        bit(1)      NOT NULL DEFAULT b'0' COMMENT 'build is enable v2 or not',
    `LOG_CLUSTER_NAME` varchar(64) NOT NULL DEFAULT '' COMMENT 'multi es log cluster name',
    `USE_CLUSTER` bit(1) NOT NULL DEFAULT b'0' COMMENT 'use multi es log cluster or not',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `BUILD_ID` (`BUILD_ID`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `T_LOG_STATUS`
(
    `ID`            bigint(20)  NOT NULL AUTO_INCREMENT,
    `BUILD_ID`      varchar(64) NOT NULL,
    `TAG`           varchar(64)          DEFAULT NULL,
    `SUB_TAG` varchar(256) DEFAULT NULL,
    `JOB_ID`        varchar(64)          DEFAULT NULL,
    `EXECUTE_COUNT` int(11)     NOT NULL,
    `FINISHED`      bit(1)      NOT NULL DEFAULT b'0' COMMENT 'build is finished or not',
    `CREATE_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `BUILD_ID_2` (`BUILD_ID`, `TAG`, `SUB_TAG`, `EXECUTE_COUNT`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `T_LOG_SUBTAGS`
(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BUILD_ID` varchar(64) NOT NULL,
  `TAG` varchar(64) NOT NULL DEFAULT '' COMMENT '插件标签',
  `SUB_TAGS` text NOT NULL COMMENT '插件子标签',
  `CREATE_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BUILD_ID_2` (`BUILD_ID`,`TAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
