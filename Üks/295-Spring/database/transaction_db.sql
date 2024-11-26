SET @AUTOCOMMIT=@@autocommit, AUTOCOMMIT=0;
-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema transaction_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `transaction_db` DEFAULT CHARACTER SET utf8mb4 ;
USE `transaction_db` ;

-- -----------------------------------------------------
-- Table `transaction_db`.`test_table`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction_db`.`test_table`;
CREATE TABLE IF NOT EXISTS `transaction_db`.`test_table` (
`name` VARCHAR(255) NOT NULL,
`value` INT NOT NULL);

ALTER TABLE `transaction_db`.`test_table` ADD PRIMARY KEY (`name`);

-- -----------------------------------------------------
-- End of schema / DDL
-- -----------------------------------------------------

-- -----------------------------------------------------
-- DCL
-- -----------------------------------------------------

-- SET SQL_MODE = '';
-- DROP USER IF EXISTS 'transactionDBuser';
-- SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- CREATE USER 'transactionDBuser' IDENTIFIED BY 'transactionDBp@ssw0rd';

-- GRANT SELECT, INSERT, TRIGGER, UPDATE, CREATE, ALTER, DELETE ON TABLE transaction_db.* TO 'transactionDBuser';



-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- DML
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `transaction_db`.`test_table`
VALUE 
('COUNTER', 0);
COMMIT;

START TRANSACTION;
INSERT INTO `transaction_db`.`test_table`
VALUE 
('TEST', 0);
ROLLBACK;

-- -----------------------------------------------------
-- DQL
-- -----------------------------------------------------
START TRANSACTION;
SHOW SESSION VARIABLES LIKE 'autocommit';
SHOW VARIABLES LIKE '%transaction_isolation%';
COMMIT;

START TRANSACTION;
SELECT * FROM `transaction_db`.`test_table`;
COMMIT;