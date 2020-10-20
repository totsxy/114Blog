CREATE TABLE `user`
(
    `id`      bigint(20)  NOT NULL AUTO_INCREMENT,
    `name`    varchar(24) NOT NULL,
    `pass`    varchar(64) NOT NULL,
    `mail`    varchar(64) NOT NULL,
    `role`    varchar(24) NOT NULL,
    `mark`    varchar(24) NOT NULL,
    `created` datetime    NOT NULL,
    `visited` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `UK_USERNAME` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `blog`
(
    `id`      bigint(20)   NOT NULL AUTO_INCREMENT,
    `uid`     bigint(20)   NOT NULL,
    `title`   varchar(255) NOT NULL,
    `cover`   varchar(255) DEFAULT NULL,
    `summary` varchar(255) NOT NULL,
    `content` longtext     NOT NULL,
    `created` datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    `status`  varchar(24)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `comm`
(
    `id`      bigint(20)  NOT NULL AUTO_INCREMENT,
    `uid`     bigint(20)  NOT NULL,
    `bid`     bigint(20)  NOT NULL,
    `content` longtext    NOT NULL,
    `created` datetime    NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    `status`  varchar(24) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `star`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `uid`     bigint(20) NOT NULL,
    `bid`     bigint(20) NOT NULL,
    `created` datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
