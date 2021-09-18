CREATE DATABASE `demo`;
use demo;
CREATE TABLE `sys_system` (
                              `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '子系统名称' COLLATE 'utf8_general_ci',
                              `path` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'path' COLLATE 'utf8_general_ci',
                              `icon_url` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '子系统图标URL' COLLATE 'utf8_general_ci',
                              `category` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '子系统类别' COLLATE 'utf8_general_ci',
                              `status` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '子系统状态（0正常 1停用）' COLLATE 'utf8_general_ci',
                              `create_by` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '创建者' COLLATE 'utf8_general_ci',
                              `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                              `remark` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '备注' COLLATE 'utf8_general_ci',
                              `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常 1删除）' COLLATE 'utf8_general_ci',
                              `owner` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '所有权' COLLATE 'utf8_general_ci',
                              `topic` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '主题名称' COLLATE 'utf8_general_ci',
                              `system_key` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '私钥' COLLATE 'utf8_general_ci',
                              PRIMARY KEY (`id`) USING BTREE
)
    COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;
