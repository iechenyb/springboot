--测试种类  端口，url和ip三类
INSERT INTO `web_eye_class` (`id`, `domain`, `ip`, `name`, `port`, `sys_Type`, `type`, `url`) VALUES ('1', 'localhost', '127.0.0.1', '本地8080端口测试', '8080', 'window', 'port', NULL);
INSERT INTO `web_eye_class` (`id`, `domain`, `ip`, `name`, `port`, `sys_Type`, `type`, `url`) VALUES ('2', 'localhost', '127.0.0.1', 'tomcat根服务', NULL, 'window', 'url', 'http://localhost:8080');
INSERT INTO `web_eye_class` (`id`, `domain`, `ip`, `name`, `port`, `sys_Type`, `type`, `url`) VALUES ('3', 'localhost', '127.0.0.1', '服务ip通道测试', NULL, 'window', 'ip', NULL);
--监控者信息
insert into WEB_EYE_MONITOR (id,account,type) values(3,'823762864@qq.com','email');