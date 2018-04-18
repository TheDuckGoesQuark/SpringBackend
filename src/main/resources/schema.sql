/* Author nd33 */

DROP TABLE IF EXISTS `oauth_access_token`;
DROP TABLE IF EXISTS `has_privilege`;
DROP TABLE IF EXISTS `involved_in`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `privilege`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `supports_view`;
DROP TABLE IF EXISTS `supported_view`;
DROP TABLE IF EXISTS `dir_contains`;
DROP TABLE IF EXISTS `header`;
DROP TABLE IF EXISTS `row_count`;
DROP TABLE IF EXISTS `file`;


CREATE TABLE IF NOT EXISTS `user` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `email`    VARCHAR(320) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE IF NOT EXISTS `privilege` (
  `name`        VARCHAR(45)  NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `internal`    BOOL         NOT NULL,
  PRIMARY KEY (`name`)
);

INSERT INTO privilege (name, description, internal) VALUES
  ("admin", "can do everything", TRUE),
  ("user", "can do some stuff", FALSE);

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
  PRIMARY KEY (file_id)
);

CREATE TABLE IF NOT EXISTS `header` (
  `file_id` INT UNSIGNED NOT NULL,
  `name`    VARCHAR(100) NOT NULL,
  `type`    VARCHAR(100) NOT NULL,
  `index`   INT UNSIGNED NOT NULL,
  PRIMARY KEY (`file_id`, `index`),
  INDEX `header_idx` (`file_id` ASC),
  CONSTRAINT `file_id`
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
  CONSTRAINT `file_id`
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

INSERT INTO supported_view (view) VALUES
  ("meta"),
  ("raw");

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