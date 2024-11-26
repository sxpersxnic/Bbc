SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- -----------------------------------------------------
-- DDL - Schema ticketshop_v1_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ticketshop_v1_db` DEFAULT CHARACTER SET utf8mb4 ;
USE `ticketshop_v1_db` ;

DROP TABLE IF EXISTS `ticketshop_v1_db`.`user`;

-- -----------------------------------------------------
-- Table `ticketshop_v1_db`.`person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticketshop_v1_db`.`person`;
CREATE TABLE IF NOT EXISTS `ticketshop_v1_db`.`person` (
`id` INT  AUTO_INCREMENT NOT NULL,
`email` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
PRIMARY KEY (id),
UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;
TRUNCATE TABLE `ticketshop_v1_db`.`person`;
    
-- -----------------------------------------------------
-- Table `ticketshop_v1_db`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticketshop_v1_db`.`role`;
CREATE TABLE IF NOT EXISTS `ticketshop_v1_db`.`role` (
`id` INT  AUTO_INCREMENT NOT NULL,
`name` VARCHAR(45) NOT NULL,
PRIMARY KEY (id))
ENGINE = InnoDB;
TRUNCATE TABLE `ticketshop_v1_db`.`role`;

-- -----------------------------------------------------
-- Table `ticketshop_v1_db`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticketshop_v1_db`.`event`;
CREATE TABLE IF NOT EXISTS `ticketshop_v1_db`.`event` (
`id` INT  AUTO_INCREMENT NOT NULL,
`name` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) NULL,
`date` DATE NULL,
`owner_id` INT NOT NULL,
PRIMARY KEY (id),
INDEX `fk_event_person_idx` (`owner_id` ASC) VISIBLE,
CONSTRAINT `fk_event_person`
	FOREIGN KEY (`owner_id`)
		REFERENCES `ticketshop_v1_db`.`person` (`id`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ticketshop_v1_db`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticketshop_v1_db`.`ticket`;
CREATE TABLE IF NOT EXISTS `ticketshop_v1_db`.`ticket` (
`id` INT  AUTO_INCREMENT NOT NULL,
`name` VARCHAR(255) NULL,
`description` VARCHAR(255) NULL,
`amount` INT NULL,
`event_id` INT NOT NULL,
PRIMARY KEY (id),
INDEX `fk_ticket_event1_idx` (`event_id` ASC) VISIBLE,
CONSTRAINT `fk_ticket_event1`
	FOREIGN KEY (`event_id`)
		REFERENCES `ticketshop_v1_db`.`event` (`id`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
    ENGINE = InnoDB;
    
-- -----------------------------------------------------
-- Table `ticketshop_v1_db`.`person_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticketshop_v1_db`.`person_role`;
CREATE TABLE IF NOT EXISTS `ticketshop_v1_db`.`person_role` (
  `person_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`person_id`, `role_id`),
  INDEX `fk_person_has_role_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_person_has_role_person1_idx` (`person_id` ASC) VISIBLE,
  CONSTRAINT `fk_person_has_role_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `ticketshop_v1_db`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ticketshop_v1_db`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
TRUNCATE TABLE `ticketshop_v1_db`.`person_role`;
-- -----------------------------------------------------
-- DCL
-- -----------------------------------------------------
SET SQL_MODE = '';
DROP USER IF EXISTS 'ticketshopDBuser';
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'ticketshopDBuser' IDENTIFIED BY 'ticketshopDBp@ssw0rd';

GRANT SELECT, INSERT, TRIGGER, UPDATE, CREATE, ALTER, DELETE ON TABLE ticketshop_v1_db.* TO 'ticketshopDBuser';

-- -----------------------------------------------------
-- DML
-- -----------------------------------------------------

-- ticketshop-seed.sql

-- -----------------------------------------------------
-- DQL
-- -----------------------------------------------------
-- SHOW SESSION VARIABLES LIKE 'autocommit';
-- SELECT * FROM `ticketshop_v1_db`.`role`;
-- SELECT * FROM `ticketshop_v1_db`.`person`;
-- SELECT `person`.`id` AS personID , `person`.`email`, `person`.`password`, `role`.`id` AS roleID, `role`.`name` AS roleName FROM `ticketshop_v1_db`.`person_role`
-- JOIN `ticketshop_v1_db`.`person` ON `ticketshop_v1_db`.`person_role`.`person_id` = `ticketshop_v1_db`.`person`.`id`
-- JOIN `ticketshop_v1_db`.`role` ON `ticketshop_v1_db`.`role`.`id` = `ticketshop_v1_db`.`person_role`.`role_id`;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;