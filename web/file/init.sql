CREATE TABLE `sys_system` (
                              `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` VARCHAR(50) NULL DEFAULT NULL COMMENT '子系统名称' COLLATE 'utf8_general_ci',
                              `path` VARCHAR(50) NULL DEFAULT NULL COMMENT 'path' COLLATE 'utf8_general_ci',
                              `icon_url` VARCHAR(50) NULL DEFAULT NULL COMMENT '子系统图标URL' COLLATE 'utf8_general_ci',
                              `category` VARCHAR(50) NULL DEFAULT NULL COMMENT '子系统类别' COLLATE 'utf8_general_ci',
                              `status` VARCHAR(50) NULL DEFAULT NULL COMMENT '子系统状态（0正常 1停用）' COLLATE 'utf8_general_ci',
                              `create_by` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建者' COLLATE 'utf8_general_ci',
                              `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
                              `remark` VARCHAR(50) NULL DEFAULT NULL COMMENT '备注' COLLATE 'utf8_general_ci',
                              `del_flag` VARCHAR(50) NULL DEFAULT NULL COMMENT '删除标记（0正常 1删除）' COLLATE 'utf8_general_ci',
                              `owner` VARCHAR(50) NULL DEFAULT NULL COMMENT '所有权' COLLATE 'utf8_general_ci',
                              `topic` VARCHAR(50) NULL DEFAULT NULL COMMENT '主题名称' COLLATE 'utf8_general_ci',
                              `system_key` VARCHAR(50) NULL DEFAULT NULL COMMENT '私钥' COLLATE 'utf8_general_ci',
                              PRIMARY KEY (`id`) USING BTREE
)
    COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
