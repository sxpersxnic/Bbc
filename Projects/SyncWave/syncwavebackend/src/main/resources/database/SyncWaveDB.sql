-- -----------------------------------------------------
-- Schema Configuration
-- -----------------------------------------------------
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SET @OLD_TIME_ZONE=@@TIME_ZONE, TIME_ZONE='+02:00';
-- -----------------------------------------------------
-- Schema syncwave_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS syncwave_db;
CREATE SCHEMA IF NOT EXISTS syncwave_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE syncwave_db;
-- -----------------------------------------------------
-- Table syncwave_db.user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.user; 
CREATE TABLE IF NOT EXISTS syncwave_db.user (
	id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status ENUM('ONLINE', 'OFFLINE'),
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    UNIQUE INDEX username_UNIQUE (username ASC) VISIBLE,
    UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.chat
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS chat;
CREATE TABLE IF NOT EXISTS syncwave_db.chat (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    is_group BOOLEAN NOT NULL,
    team_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_chat_team
        FOREIGN KEY (team_id)
            REFERENCES syncwave_db.team (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
    
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.message
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.message;
CREATE TABLE IF NOT EXISTS syncwave_db.message (
	id BIGINT NOT NULL AUTO_INCREMENT,
    chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    content VARCHAR(4096) NOT NULL,
    send_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX fk_message_sender_id_idx (sender_id ASC) VISIBLE,
    INDEX fk_message_chat_id_idx (chat_id ASC) VISIBLE,
    CONSTRAINT fk_message_sender_id
		FOREIGN KEY (sender_id)
        REFERENCES syncwave_db.user (id)
			ON DELETE RESTRICT
            ON UPDATE CASCADE,
	CONSTRAINT fk_message_chat_id
		FOREIGN KEY (chat_id)
        REFERENCES syncwave_db.chat (id)
			ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.notification
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.notification;
CREATE TABLE IF NOT EXISTS syncwave_db.notification (
	id BIGINT NOT NULL AUTO_INCREMENT,
    sender_id BIGINT,
    recipient_id BIGINT,
    content TEXT,
    notification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX fk_notification_sender_id (sender_id ASC) VISIBLE,
    INDEX fk_notification_recipient_id (recipient_id ASC) VISIBLE,
    CONSTRAINT fk_notification_sender_id
		FOREIGN KEY (sender_id)
        REFERENCES syncwave_db.user (id)
			ON DELETE RESTRICT
            ON UPDATE CASCADE,
	CONSTRAINT fk_notification_recipient_id
		FOREIGN KEY (recipient_id)
        REFERENCES syncwave_db.user (id)
			ON DELETE RESTRICT
            ON UPDATE CASCADE

) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.team
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS team;
CREATE TABLE IF NOT EXISTS syncwave_db.team (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    initials VARCHAR(255) NOT NULL,
    description varchar(4096),
    owner_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    CONSTRAINT fk_team_owner_id
        FOREIGN KEY (owner_id)
            REFERENCES syncwave_db.user (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.teamrole
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS teamrole;
CREATE TABLE IF NOT EXISTS syncwave_db.teamrole (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    team_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    CONSTRAINT fk_team_id
        FOREIGN KEY (team_id)
            REFERENCES syncwave_db.team (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.team_user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.team_user;
CREATE TABLE IF NOT EXISTS syncwave_db.team_user (
    user_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, team_id),
    CONSTRAINT fk_team_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES syncwave_db.user (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_team_user_team_id
        FOREIGN KEY (team_id)
            REFERENCES syncwave_db.team (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.chat_user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.chat_user;
CREATE TABLE IF NOT EXISTS syncwave_db.chat_user (
    chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    INDEX fk_chat_user_chat_id (chat_id ASC) VISIBLE,
    INDEX fk_chat_user_user_id (user_id ASC) VISIBLE,
    CONSTRAINT fk_chat_user_chat_id
        FOREIGN KEY (chat_id)
        REFERENCES syncwave_db.chat (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_chat_user_user_id
        FOREIGN KEY (user_id)
        REFERENCES syncwave_db.user (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table syncwave_db.permission
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS permission;
CREATE TABLE IF NOT EXISTS syncwave_db.permission (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.teamrole_permission
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS teamrole_permission;
CREATE TABLE IF NOT EXISTS syncwave_db.teamrole_permission (
    permission_id BIGINT NOT NULL,
    teamrole_id BIGINT NOT NULL,
    PRIMARY KEY (permission_id, teamrole_id),
    CONSTRAINT fk_teamrole_id
        FOREIGN KEY (teamrole_id)
            REFERENCES syncwave_db.teamrole (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_permission_id
        FOREIGN KEY (permission_id)
            REFERENCES syncwave_db.permission (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.user_teamrole
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS user_teamrole;
CREATE TABLE IF NOT EXISTS syncwave_db.user_teamrole (
    user_id BIGINT NOT NULL,
    teamrole_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, teamrole_id),
    CONSTRAINT forkey_teamrole_id
       FOREIGN KEY (teamrole_id)
           REFERENCES syncwave_db.teamrole (id)
           ON DELETE RESTRICT
           ON UPDATE CASCADE,
    CONSTRAINT forkey_user_id
       FOREIGN KEY (user_id)
           REFERENCES syncwave_db.user (id)
           ON DELETE RESTRICT
           ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.task
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.task;
CREATE TABLE IF NOT EXISTS syncwave_db.task (
id BIGINT NOT NULL AUTO_INCREMENT,
title VARCHAR(25),
description TEXT,
creator_id BIGINT NOT NULL,
due_at TIMESTAMP,
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id),
CONSTRAINT fk_task_user_id
    FOREIGN KEY (creator_id)
        REFERENCES syncwave_db.user (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.task_user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.task_user;
CREATE TABLE IF NOT EXISTS syncwave_db.task_user (
id  BIGINT NOT NULL AUTO_INCREMENT,
user_id BIGINT NOT NULL,
task_id BIGINT NOT NULL,
status ENUM('CURRENT', 'OVERDUE', 'DONE') NOT NULL DEFAULT 'CURRENT',
PRIMARY KEY (id),
CONSTRAINT fk_task_user_user_id
 FOREIGN KEY (user_id)
     REFERENCES syncwave_db.user (id)
     ON DELETE RESTRICT
     ON UPDATE CASCADE,
CONSTRAINT fk_task_user_task_id
 FOREIGN KEY (task_id)
     REFERENCES syncwave_db.task (id)
     ON DELETE RESTRICT
     ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.team
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS team;
CREATE TABLE IF NOT EXISTS syncwave_db.team (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    initials VARCHAR(255) NOT NULL,
    description text(4096),
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table syncwave_db.team_user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS syncwave_db.team_user;
CREATE TABLE IF NOT EXISTS syncwave_db.team_user (
    user_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, team_id),
    CONSTRAINT fk_team_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES syncwave_db.user (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_team_user_team_id
        FOREIGN KEY (team_id)
            REFERENCES syncwave_db.team (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;
-- -----------------------------------------------------
-- procedure InsertUserAndGrantPermissions
-- -----------------------------------------------------
DELIMITER $$
USE syncwave_db$$
DROP PROCEDURE IF EXISTS InsertUserAndGrantPermissions$$
CREATE PROCEDURE InsertUserAndGrantPermissions(
	IN p_username VARCHAR(256),
    IN p_email VARCHAR(256),
    IN p_password VARCHAR(256),
    IN p_status ENUM('ONLINE', 'OFFLINE'),
    IN p_role VARCHAR(256))
BEGIN
	DECLARE permissions VARCHAR(255);
    DECLARE user_id BIGINT;
    
    INSERT INTO syncwave_db.user(username, email, password, status)
    VALUES
		(p_username, p_email, p_password, p_status);
	
	SET user_id = (SELECT id FROM user WHERE username = p_username);

	IF p_role LIKE '%ADMIN' OR p_role LIKE '%admin' THEN
        SET permissions = 'ALL PRIVILEGES';
    ELSEIF p_role LIKE '%MODERATOR' OR p_role LIKE '%moderator' THEN
        SET permissions = 'SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES';
    ELSEIF p_role LIKE '%USER' OR p_role LIKE '%user' THEN
        SET permissions = 'SELECT, INSERT, UPDATE, DELETE';
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid Role!';
    END IF;

    -- Create the user and grant permissions
    SET @s = CONCAT('CREATE USER IF NOT EXISTS ', p_username, ' IDENTIFIED BY ', QUOTE(p_password));
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

	IF permissions <> '' THEN
		SET @s = CONCAT('GRANT ', permissions, ' ON syncwave_db.* TO ', QUOTE(p_username));
		PREPARE stmt FROM @s;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
	END IF;

    FLUSH PRIVILEGES;
END;$$
DELIMITER ;      
-- -----------------------------------------------------
-- DatabaseSeed - DML
-- -----------------------------------------------------
CALL InsertUserAndGrantPermissions('syncwave_db_admin', 'admin@localhost.com', 'syncwave_db_password', 'ONLINE', 'ROLE_ADMIN');
CALL InsertUserAndGrantPermissions('syncwave_db_moderator', 'moderator@localhost.com', 'syncwave_db_password', 'OFFLINE', 'ROLE_MODERATOR');
CALL InsertUserAndGrantPermissions('syncwave_db_user', 'user@localhost.com', 'syncwave_db_password', 'ONLINE', 'ROLE_USER');
-- -----------------------------------------------------
-- DatabaseSeed - DQL
-- -----------------------------------------------------
-- USE syncwave_db;
SELECT * FROM syncwave_db.user;
SELECT * FROM syncwave_db.message;
SELECT * FROM syncwave_db.chat;
-- -----------------------------------------------------
-- DatabaseSeed - DCL
-- -----------------------------------------------------
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- -----------------------------------------------------
-- Premade permissions
-- -----------------------------------------------------
INSERT INTO permission (id, name)
values (1, 'ALL PRIVILEGES'),(2, 'GIVE PRIVILEGES'),(3, 'remove Other privileges'), (4, 'delete other messages'),(5, 'update Channels'), (6, 'Delete Channels'), (7, 'add Users'), (8, 'Remove users'),(9, 'create assignment'), (10, 'manage others assignments') ;

