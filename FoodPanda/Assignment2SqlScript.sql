-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema foodpanda
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema foodpanda
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `foodpanda` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `foodpanda` ;

-- -----------------------------------------------------
-- Table `foodpanda`.`administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`administrator` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email_address` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `delivery_zones` INT NULL DEFAULT NULL,
  `location` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `administrator_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKflyva1hs3v89e4i54v5au2iv5` (`administrator_id` ASC) VISIBLE,
  CONSTRAINT `FKflyva1hs3v89e4i54v5au2iv5`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `foodpanda`.`administrator` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`food` (
  `food_id` INT NOT NULL AUTO_INCREMENT,
  `category` INT NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `restaurant_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`food_id`),
  INDEX `FKm9xrxt95wwp1r2s7andom1l1c` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKm9xrxt95wwp1r2s7andom1l1c`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `foodpanda`.`restaurant` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`orders` (
  `food_id` INT NOT NULL,
  `client_id` INT NOT NULL,
  INDEX `FK17yo6gry2nuwg2erwhbaxqbs9` (`client_id` ASC) VISIBLE,
  INDEX `FK5g4j2r53ncoltplogbnqlpt30` (`food_id` ASC) VISIBLE,
  CONSTRAINT `FK17yo6gry2nuwg2erwhbaxqbs9`
    FOREIGN KEY (`client_id`)
    REFERENCES `foodpanda`.`client` (`id`),
  CONSTRAINT `FK5g4j2r53ncoltplogbnqlpt30`
    FOREIGN KEY (`food_id`)
    REFERENCES `foodpanda`.`food` (`food_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
