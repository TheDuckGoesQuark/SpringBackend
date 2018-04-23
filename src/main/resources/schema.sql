CREATE TABLE IF NOT EXISTS `metadata` (
  `metadataID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `namespaces` JSON,
  `version` INT UNSIGNED NOT NULL,
  `type`    VARCHAR(15),
  PRIMARY KEY (`metadataID`)
);

CREATE TABLE IF NOT EXISTS `logging` (
  `component` VARCHAR(15) NOT NULL,
  `level` VARCHAR(15) NOT NULL,
  `value` VARCHAR(15) NOT NULL,
  `username`    VARCHAR(15) NOT NULL,
  `timestamp`  VARCHAR(25) NOT NULL,
  PRIMARY KEY (`value`)
);

CREATE TABLE IF NOT EXISTS `property` (
  `id` VARCHAR(15) NOT NULL,
  `readonly` BOOL NOT NULL,
  `type` VARCHAR(15),
  `value`    VARCHAR(15),
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `email`    VARCHAR(320),
  `public_user_metadata` INT UNSIGNED,
  `private_user_metadata` INT UNSIGNED,
  `public_admin_metadata` INT UNSIGNED,
  `private_admin_metadata` INT UNSIGNED,
  PRIMARY KEY (`username`),
  FOREIGN KEY (`public_user_metadata`)
  REFERENCES `metadata` (`metadataID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`private_user_metadata`)
  REFERENCES `metadata` (`metadataID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`public_admin_metadata`)
  REFERENCES `metadata` (`metadataID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`private_admin_metadata`)
  REFERENCES `metadata` (`metadataID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `privilege` (
  `name`        VARCHAR(45)  NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `internal`    BOOL         NOT NULL,
  PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `role` (
  `role`        VARCHAR(45)  NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `internal`    BOOL         NOT NULL,
  PRIMARY KEY (`role`)
);

CREATE TABLE IF NOT EXISTS `has_privilege` (
  `username`       VARCHAR(100) NOT NULL,
  `privilege_name` VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`username`, `privilege_name`),
  INDEX `privilege_idx` (`privilege_name` ASC),
  CONSTRAINT `username`
  FOREIGN KEY (`username`)
  REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `privilege`
  FOREIGN KEY (`privilege_name`)
  REFERENCES `privilege` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS file (
  `file_id`        INT UNSIGNED    NOT NULL AUTO_INCREMENT,
  `file_name`      VARCHAR(54)     NOT NULL,
  `type`           VARCHAR(45)     NOT NULL,
  `status`         VARCHAR(10)     NOT NULL,
  `last_modified`  TIMESTAMP       NOT NULL,
  `length`         BIGINT UNSIGNED NOT NULL,
  `parent_file_id` INT UNSIGNED,
  `metadata_id` INT UNSIGNED,
  PRIMARY KEY (file_id),
  FOREIGN KEY (`metadata_id`)
  REFERENCES `metadata` (`metadataID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `header` (
  `file_id` INT UNSIGNED NOT NULL,
  `name`    VARCHAR(100) NOT NULL,
  `type`    VARCHAR(100) NOT NULL,
  `index`   INT UNSIGNED NOT NULL,
  PRIMARY KEY (`file_id`, `index`),
  INDEX `header_idx` (`file_id` ASC),
  CONSTRAINT `header_id`
  FOREIGN KEY (`file_id`)
  REFERENCES `file` (`file_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `row_count` (
  `file_id` INT UNSIGNED NOT NULL,
  `rows`    INT UNSIGNED NOT NULL,
  PRIMARY KEY (`file_id`),
  INDEX `row_count_idx` (`file_id` ASC),
  CONSTRAINT `row_count_id`
  FOREIGN KEY (`file_id`)
  REFERENCES `file` (`file_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `project` (
  `name`        VARCHAR(100) NOT NULL,
  `root_dir_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `root_dir_id_UNIQUE` (`root_dir_id` ASC),
  CONSTRAINT `root_dir_id`
  FOREIGN KEY (`root_dir_id`)
  REFERENCES `file` (`file_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS `involved_in` (
  `username`     VARCHAR(100) NOT NULL,
  `project_name` VARCHAR(100) NOT NULL,
  `role`         VARCHAR(45)  NOT NULL,
  `access_level` VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`username`, `project_name`),
  INDEX `project_name_idx` (`project_name` ASC),
  CONSTRAINT `involved_user`
  FOREIGN KEY (`username`)
  REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `involved_project`
  FOREIGN KEY (`project_name`)
  REFERENCES `project` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `token_id`      VARCHAR(256) NOT NULL,
  `username`      VARCHAR(100) NOT NULL,
  `created`       TIMESTAMP    NOT NULL,
  `refresh_token` VARCHAR(256),
  PRIMARY KEY (`token_id`),
  INDEX `user_id_idx` (`username` ASC),
  CONSTRAINT `user_id`
  FOREIGN KEY (`username`)
  REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `supported_view` (
  `view` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`view`)
);

CREATE TABLE IF NOT EXISTS `supports_view` (
  `file_id` INT UNSIGNED NOT NULL,
  `view`    VARCHAR(10)  NOT NULL,
  PRIMARY KEY (`file_id`, `view`),
  INDEX `supports_view_idx` (`file_id` ASC),
  CONSTRAINT `file_id`
  FOREIGN KEY (`file_id`)
  REFERENCES `file` (`file_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `view`
  FOREIGN KEY (`view`)
  REFERENCES `supported_view` (`view`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);