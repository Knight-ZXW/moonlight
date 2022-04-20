-- ----------------------------
-- Table structure for upms_organization
-- ----------------------------
DROP TABLE IF EXISTS `upms_organization`;
CREATE TABLE `upms_organization`
(
    `organization_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `pid`             int(10)       DEFAULT NULL COMMENT '所属上级',
    `name`            varchar(50)   DEFAULT NULL COMMENT '组织名称',
    `description`     varchar(1000) DEFAULT NULL COMMENT '组织描述',
    PRIMARY KEY (`organization_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织';


DROP TABLE IF EXISTS `upms_system`;
CREATE TABLE `upms_system`
(
    `system_id`   int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `icon`        varchar(50)  DEFAULT NULL COMMENT '图标',
    `banner`      varchar(150) DEFAULT NULL COMMENT '背景',
    `theme`       varchar(50)  DEFAULT NULL COMMENT '主题',
    `basepath`    varchar(100) DEFAULT NULL COMMENT '根目录',
    `status`      tinyint(4)   DEFAULT NULL COMMENT '状态(-1:黑名单,1:正常)',
    `name`        varchar(20)  DEFAULT NULL COMMENT '系统名称',
    `title`       varchar(20)  DEFAULT NULL COMMENT '系统标题',
    `description` varchar(300) DEFAULT NULL COMMENT '系统描述',
    `orders`      bigint(20)   DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`system_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统';

-- ----------------------------
-- Records of upms_system
-- ----------------------------
INSERT INTO `upms_system`
VALUES ('1', 'zmdi zmdi-shield-security', '/resources/zheng-admin/images/zheng-upms.png', '#29A176',
        'http://upms.zhangshuzheng.cn:1111', '1', 'zheng-upms-server', '权限管理系统', '用户权限管理系统（RBAC细粒度用户权限、统一后台、单点登录、会话管理）',
        '1');



-- ----------------------------
-- Table structure for upms_permission
-- ----------------------------
DROP TABLE IF EXISTS `upms_permission`;
CREATE TABLE `upms_permission`
(
    `permission_id`    int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `system_id`        int(10) unsigned NOT NULL COMMENT '所属系统',
    `pid`              int(10)      DEFAULT NULL COMMENT '所属上级',
    `name`             varchar(20)  DEFAULT NULL COMMENT '名称',
    `type`             tinyint(4)   DEFAULT NULL COMMENT '类型(1:目录,2:菜单,3:按钮)',
    `permission_value` varchar(50)  DEFAULT NULL COMMENT '权限值',
    `uri`              varchar(100) DEFAULT NULL COMMENT '路径',
    `icon`             varchar(50)  DEFAULT NULL COMMENT '图标',
    `status`           tinyint(4)   DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
    `order`            bigint(20)   DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`permission_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 86
  DEFAULT CHARSET = utf8mb4 COMMENT ='权限';

-- ----------------------------
-- Records of upms_permission
-- ----------------------------
INSERT INTO `upms_permission`
VALUES ('1', '1', '0', '系统组织管理', '1', '', '', 'zmdi zmdi-accounts-list', '1', '1');
INSERT INTO `upms_permission`
VALUES ('2', '1', '1', '系统管理', '2', 'upms:system:read', '/manage/system/index', '', '1', '2');
INSERT INTO `upms_permission`
VALUES ('3', '1', '1', '组织管理', '2', 'upms:organization:read', '/manage/organization/index', '', '1', '3');
INSERT INTO `upms_permission`
VALUES ('4', '1', '0', '角色用户管理', '1', '', '', 'zmdi zmdi-accounts', '1', '4');
INSERT INTO `upms_permission`
VALUES ('5', '1', '4', '角色管理', '2', 'upms:role:read', '/manage/role/index', '', '1', '6');
INSERT INTO `upms_permission`
VALUES ('6', '1', '4', '用户管理', '2', 'upms:user:read', '/manage/user/index', '', '1', '5');
INSERT INTO `upms_permission`
VALUES ('7', '1', '0', '权限资源管理', '1', '', '', 'zmdi zmdi-lock-outline', '1', '7');
INSERT INTO `upms_permission`
VALUES ('12', '1', '0', '其他数据管理', '1', '', '', 'zmdi zmdi-more', '1', '12');
INSERT INTO `upms_permission`
VALUES ('14', '1', '12', '会话管理', '2', 'upms:session:read', '/manage/session/index', '', '1', '14');
INSERT INTO `upms_permission`
VALUES ('15', '1', '12', '日志记录', '2', 'upms:log:read', '/manage/log/index', '', '1', '15');

INSERT INTO `upms_permission`
VALUES ('24', '1', '2', '新增系统', '3', 'upms:system:create', '/manage/system/create', 'zmdi zmdi-plus', '1', '24');
INSERT INTO `upms_permission`
VALUES ('25', '1', '2', '编辑系统', '3', 'upms:system:update', '/manage/system/update', 'zmdi zmdi-edit', '1', '25');
INSERT INTO `upms_permission`
VALUES ('26', '1', '2', '删除系统', '3', 'upms:system:delete', '/manage/system/delete', 'zmdi zmdi-close', '1', '26');
INSERT INTO `upms_permission`
VALUES ('27', '1', '3', '新增组织', '3', 'upms:organization:create', '/manage/organization/create', 'zmdi zmdi-plus', '1',
        '27');
INSERT INTO `upms_permission`
VALUES ('28', '1', '3', '编辑组织', '3', 'upms:organization:update', '/manage/organization/update', 'zmdi zmdi-edit', '1',
        '28');
INSERT INTO `upms_permission`
VALUES ('29', '1', '3', '删除组织', '3', 'upms:organization:delete', '/manage/organization/delete', 'zmdi zmdi-close', '1',
        '29');
INSERT INTO `upms_permission`
VALUES ('30', '1', '6', '新增用户', '3', 'upms:user:create', '/manage/user/create', 'zmdi zmdi-plus', '1', '30');
INSERT INTO `upms_permission`
VALUES ('31', '1', '6', '编辑用户', '3', 'upms:user:update', '/manage/user/update', 'zmdi zmdi-edit', '1', '31');
INSERT INTO `upms_permission`
VALUES ('32', '1', '6', '删除用户', '3', 'upms:user:delete', '/manage/user/delete', 'zmdi zmdi-close', '1', '32');
INSERT INTO `upms_permission`
VALUES ('33', '1', '5', '新增角色', '3', 'upms:role:create', '/manage/role/create', 'zmdi zmdi-plus', '1', '33');
INSERT INTO `upms_permission`
VALUES ('34', '1', '5', '编辑角色', '3', 'upms:role:update', '/manage/role/update', 'zmdi zmdi-edit', '1', '34');
INSERT INTO `upms_permission`
VALUES ('35', '1', '5', '删除角色', '3', 'upms:role:delete', '/manage/role/delete', 'zmdi zmdi-close', '1', '35');
INSERT INTO `upms_permission`
VALUES ('36', '1', '39', '新增权限', '3', 'upms:permission:create', '/manage/permission/create', 'zmdi zmdi-plus', '1',
        '36');
INSERT INTO `upms_permission`
VALUES ('37', '1', '39', '编辑权限', '3', 'upms:permission:update', '/manage/permission/update', 'zmdi zmdi-edit', '1',
        '37');
INSERT INTO `upms_permission`
VALUES ('38', '1', '39', '删除权限', '3', 'upms:permission:delete', '/manage/permission/delete', 'zmdi zmdi-close', '1',
        '38');
INSERT INTO `upms_permission`
VALUES ('39', '1', '7', '权限管理', '2', 'upms:permission:read', '/manage/permission/index', null, '1', '39');

INSERT INTO `upms_permission`
VALUES ('46', '1', '5', '角色权限', '3', 'upms:role:permission', '/manage/role/permission', 'zmdi zmdi-key', '1',
        '46');
INSERT INTO `upms_permission`
VALUES ('48', '1', '6', '用户组织', '3', 'upms:user:organization', '/manage/user/organization', 'zmdi zmdi-accounts-list',
        '1', '48');
INSERT INTO `upms_permission`
VALUES ('50', '1', '6', '用户角色', '3', 'upms:user:role', '/manage/user/role', 'zmdi zmdi-accounts', '1', '50');
INSERT INTO `upms_permission`
VALUES ('51', '1', '6', '用户权限', '3', 'upms:user:permission', '/manage/user/permission', 'zmdi zmdi-key', '1',
        '51');
INSERT INTO `upms_permission`
VALUES ('53', '1', '14', '强制退出', '3', 'upms:session:forceout', '/manage/session/forceout', 'zmdi zmdi-run', '1',
        '53');
INSERT INTO `upms_permission`
VALUES ('57', '1', '15', '删除权限', '3', 'upms:log:delete', '/manage/log/delete', 'zmdi zmdi-close', '1', '57');

-- ----------------------------
-- Table structure for upms_role
-- ----------------------------
DROP TABLE IF EXISTS `upms_role`;
CREATE TABLE `upms_role`
(
    `role_id`     int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(20)   DEFAULT NULL COMMENT '角色名称',
    `title`       varchar(20)   DEFAULT NULL COMMENT '角色标题',
    `description` varchar(1000) DEFAULT NULL COMMENT '角色描述',
    `ctime`       bigint(20)       NOT NULL COMMENT '创建时间',
    `orders`      bigint(20)       NOT NULL COMMENT '排序',
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色';

-- ----------------------------
-- Records of upms_role
-- ----------------------------
INSERT INTO `upms_role`
VALUES ('1', 'super', '超级管理员', '拥有所有权限', '1');
INSERT INTO `upms_role`
VALUES ('2', 'admin', '管理员', '拥有除权限管理系统外的所有权限', '2');

-- ----------------------------
-- Table structure for upms_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `upms_role_permission`;
CREATE TABLE `upms_role_permission`
(
    `role_permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `role_id`            int(10) unsigned NOT NULL COMMENT '角色编号',
    `permission_id`      int(10) unsigned NOT NULL COMMENT '权限编号',
    PRIMARY KEY (`role_permission_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 126
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限关联表';


-- ----------------------------
-- Records of upms_role_permission
-- ----------------------------
INSERT INTO `upms_role_permission`
VALUES ('1', '1', '1');
INSERT INTO `upms_role_permission`
VALUES ('2', '1', '2');
INSERT INTO `upms_role_permission`
VALUES ('3', '1', '3');
INSERT INTO `upms_role_permission`
VALUES ('4', '1', '4');
INSERT INTO `upms_role_permission`
VALUES ('5', '1', '5');
INSERT INTO `upms_role_permission`
VALUES ('6', '1', '6');
INSERT INTO `upms_role_permission`
VALUES ('7', '1', '7');
INSERT INTO `upms_role_permission`
VALUES ('8', '1', '39');
INSERT INTO `upms_role_permission`
VALUES ('12', '1', '12');
INSERT INTO `upms_role_permission`
VALUES ('14', '1', '14');
INSERT INTO `upms_role_permission`
VALUES ('15', '1', '15');
INSERT INTO `upms_role_permission`
VALUES ('17', '1', '17');
INSERT INTO `upms_role_permission`
VALUES ('19', '1', '19');
INSERT INTO `upms_role_permission`
VALUES ('20', '1', '20');
INSERT INTO `upms_role_permission`
VALUES ('21', '1', '21');
INSERT INTO `upms_role_permission`
VALUES ('24', '1', '24');
INSERT INTO `upms_role_permission`
VALUES ('27', '1', '27');
INSERT INTO `upms_role_permission`
VALUES ('28', '1', '28');
INSERT INTO `upms_role_permission`
VALUES ('29', '1', '29');
INSERT INTO `upms_role_permission`
VALUES ('30', '1', '30');
INSERT INTO `upms_role_permission`
VALUES ('31', '1', '31');
INSERT INTO `upms_role_permission`
VALUES ('32', '1', '32');
INSERT INTO `upms_role_permission`
VALUES ('33', '1', '33');
INSERT INTO `upms_role_permission`
VALUES ('34', '1', '34');
INSERT INTO `upms_role_permission`
VALUES ('35', '1', '35');
INSERT INTO `upms_role_permission`
VALUES ('36', '1', '36');
INSERT INTO `upms_role_permission`
VALUES ('37', '1', '37');
INSERT INTO `upms_role_permission`
VALUES ('38', '1', '38');
INSERT INTO `upms_role_permission`
VALUES ('39', '1', '46');
INSERT INTO `upms_role_permission`
VALUES ('40', '1', '51');
INSERT INTO `upms_role_permission`
VALUES ('44', '1', '48');
INSERT INTO `upms_role_permission`
VALUES ('45', '1', '50');
INSERT INTO `upms_role_permission`
VALUES ('47', '1', '53');
INSERT INTO `upms_role_permission`
VALUES ('48', '1', '18');
INSERT INTO `upms_role_permission`
VALUES ('49', '1', '54');
INSERT INTO `upms_role_permission`
VALUES ('50', '1', '54');
INSERT INTO `upms_role_permission`
VALUES ('51', '1', '55');
INSERT INTO `upms_role_permission`
VALUES ('52', '1', '54');
INSERT INTO `upms_role_permission`
VALUES ('53', '1', '55');
INSERT INTO `upms_role_permission`
VALUES ('54', '1', '56');
INSERT INTO `upms_role_permission`
VALUES ('55', '1', '57');
INSERT INTO `upms_role_permission`
VALUES ('56', '1', '58');
INSERT INTO `upms_role_permission`
VALUES ('57', '1', '58');
INSERT INTO `upms_role_permission`
VALUES ('58', '1', '59');
INSERT INTO `upms_role_permission`
VALUES ('59', '1', '60');
INSERT INTO `upms_role_permission`
VALUES ('60', '1', '61');
INSERT INTO `upms_role_permission`
VALUES ('61', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('62', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('63', '1', '63');
INSERT INTO `upms_role_permission`
VALUES ('64', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('65', '1', '63');
INSERT INTO `upms_role_permission`
VALUES ('66', '1', '64');
INSERT INTO `upms_role_permission`
VALUES ('67', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('68', '1', '63');
INSERT INTO `upms_role_permission`
VALUES ('69', '1', '64');
INSERT INTO `upms_role_permission`
VALUES ('70', '1', '65');
INSERT INTO `upms_role_permission`
VALUES ('71', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('72', '1', '63');
INSERT INTO `upms_role_permission`
VALUES ('73', '1', '64');
INSERT INTO `upms_role_permission`
VALUES ('74', '1', '65');
INSERT INTO `upms_role_permission`
VALUES ('75', '1', '66');
INSERT INTO `upms_role_permission`
VALUES ('76', '1', '62');
INSERT INTO `upms_role_permission`
VALUES ('77', '1', '63');
INSERT INTO `upms_role_permission`
VALUES ('78', '1', '64');
INSERT INTO `upms_role_permission`
VALUES ('79', '1', '65');
INSERT INTO `upms_role_permission`
VALUES ('80', '1', '66');
INSERT INTO `upms_role_permission`
VALUES ('81', '1', '67');
INSERT INTO `upms_role_permission`
VALUES ('82', '1', '68');
INSERT INTO `upms_role_permission`
VALUES ('83', '1', '69');
INSERT INTO `upms_role_permission`
VALUES ('84', '1', '69');
INSERT INTO `upms_role_permission`
VALUES ('85', '1', '70');
INSERT INTO `upms_role_permission`
VALUES ('86', '1', '69');
INSERT INTO `upms_role_permission`
VALUES ('87', '1', '70');
INSERT INTO `upms_role_permission`
VALUES ('88', '1', '71');
INSERT INTO `upms_role_permission`
VALUES ('89', '1', '72');
INSERT INTO `upms_role_permission`
VALUES ('90', '1', '72');
INSERT INTO `upms_role_permission`
VALUES ('91', '1', '73');
INSERT INTO `upms_role_permission`
VALUES ('92', '1', '72');
INSERT INTO `upms_role_permission`
VALUES ('93', '1', '73');
INSERT INTO `upms_role_permission`
VALUES ('94', '1', '74');
INSERT INTO `upms_role_permission`
VALUES ('95', '1', '72');
INSERT INTO `upms_role_permission`
VALUES ('96', '1', '73');
INSERT INTO `upms_role_permission`
VALUES ('97', '1', '74');
INSERT INTO `upms_role_permission`
VALUES ('98', '1', '75');
INSERT INTO `upms_role_permission`
VALUES ('99', '1', '76');
INSERT INTO `upms_role_permission`
VALUES ('100', '1', '76');
INSERT INTO `upms_role_permission`
VALUES ('101', '1', '77');
INSERT INTO `upms_role_permission`
VALUES ('102', '1', '76');
INSERT INTO `upms_role_permission`
VALUES ('103', '1', '77');
INSERT INTO `upms_role_permission`
VALUES ('105', '1', '79');
INSERT INTO `upms_role_permission`
VALUES ('106', '1', '80');
INSERT INTO `upms_role_permission`
VALUES ('107', '1', '81');
INSERT INTO `upms_role_permission`
VALUES ('108', '1', '81');
INSERT INTO `upms_role_permission`
VALUES ('109', '1', '82');
INSERT INTO `upms_role_permission`
VALUES ('110', '1', '81');
INSERT INTO `upms_role_permission`
VALUES ('111', '1', '82');
INSERT INTO `upms_role_permission`
VALUES ('112', '1', '83');
INSERT INTO `upms_role_permission`
VALUES ('113', '1', '84');
INSERT INTO `upms_role_permission`
VALUES ('114', '1', '84');
INSERT INTO `upms_role_permission`
VALUES ('115', '1', '85');
INSERT INTO `upms_role_permission`
VALUES ('121', '1', '78');
INSERT INTO `upms_role_permission`
VALUES ('122', '1', '78');
INSERT INTO `upms_role_permission`
VALUES ('124', '1', '25');
INSERT INTO `upms_role_permission`
VALUES ('125', '1', '26');


-- ----------------------------
-- Table structure for upms_user
-- ----------------------------
DROP TABLE IF EXISTS `upms_user`;
CREATE TABLE `upms_user`
(
    `user_id`  int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username` varchar(20)      NOT NULL COMMENT '帐号',
    `password` varchar(32)      NOT NULL COMMENT '密码MD5(密码+盐)',
    `salt`     varchar(32)  DEFAULT NULL COMMENT '盐',
    `realname` varchar(20)  DEFAULT NULL COMMENT '姓名',
    `avatar`   varchar(150) DEFAULT NULL COMMENT '头像',
    `phone`    varchar(20)  DEFAULT NULL COMMENT '电话',
    `email`    varchar(50)  DEFAULT NULL COMMENT '邮箱',
    `sex`      tinyint(4)   DEFAULT NULL COMMENT '性别',
    `locked`   tinyint(4)   DEFAULT NULL COMMENT '状态(0:正常,1:锁定)',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

INSERT INTO `upms_user`
VALUES ('1',
        'admin',
        '3038D9CB63B3152A79B8153FB06C02F7',
        '66f1b370c660445a8657bf8bf1794486',
        '卓修武',
        '/resources/zheng-admin/images/avatar.jpg',
        '',
        '469741414@qq.com',
        '1',
        '0');


