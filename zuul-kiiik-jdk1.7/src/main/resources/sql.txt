CREATE TABLE `user_info` (
  `user_id` INT ( 32 ) NOT NULL,
  `user_name` VARCHAR ( 64 ) NOT NULL COMMENT '用户名',
  `user_password` CHAR ( 255 ) NOT NULL COMMENT '密码',
  `user_phone` VARCHAR ( 16 ) NOT NULL COMMENT '手机号',
  `user_description` VARCHAR ( 64 ) COMMENT '描述',
  `user_icon` VARCHAR ( 512 ) COMMENT '小图',
  `roles_id` INT ( 32 ) NOT NULL DEFAULT 0 COMMENT '权限',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ( `user_id` ),
  UNIQUE KEY `idx_user_name` ( `user_name` ),
  UNIQUE KEY `idx_user_phone` ( `user_phone` )
) COMMENT '用户表';

INSERT INTO `BMS`.`user_info` (`user_id`, `user_name`, `user_password`, `user_phone`, `user_description`, `user_icon`, `roles_id`, `create_time`, `update_time`) VALUES ('0', 'admin', 'admin', '13711111111', 'NULL', 'NULL', 0, DEFAULT, DEFAULT)