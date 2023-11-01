-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `cinema` ;

-- -----------------------------------------------------
-- Table `cinema`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`movies` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `run_time_in_seconds` BIGINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `cinema`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`rooms` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number_of_rows` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `cinema`.`room_rows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`room_rows` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `seats_in_row` INT NOT NULL,
  `cinema_room_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKjf5n9bpuirq7ft8xtwlli67hk` (`cinema_room_id` ASC) VISIBLE,
  CONSTRAINT `FKjf5n9bpuirq7ft8xtwlli67hk`
    FOREIGN KEY (`cinema_room_id`)
    REFERENCES `cinema`.`rooms` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `cinema`.`screenings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`screenings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `screening_start_time` TIMESTAMP NULL DEFAULT NULL,
  `movie_id` BIGINT NULL DEFAULT NULL,
  `room_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKrnko8743nv2o7jd7ix2wtcyf` (`movie_id` ASC) VISIBLE,
  INDEX `FKq9rxs70hfk4yejjiqw86cxj6t` (`room_id` ASC) VISIBLE,
  CONSTRAINT `FKq9rxs70hfk4yejjiqw86cxj6t`
    FOREIGN KEY (`room_id`)
    REFERENCES `cinema`.`rooms` (`id`),
  CONSTRAINT `FKrnko8743nv2o7jd7ix2wtcyf`
    FOREIGN KEY (`movie_id`)
    REFERENCES `cinema`.`movies` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `cinema`.`seats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`seats` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `is_occupied` BIT(1) NOT NULL,
  `seat_number` INT NOT NULL,
  `status` ENUM('AVAILABLE', 'BOUGHT', 'RESERVED') NULL DEFAULT NULL,
  `room_id` BIGINT NULL DEFAULT NULL,
  `room_row_id` BIGINT NULL DEFAULT NULL,
  `screening_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKg993pi7ucgy616icmddq8u335` (`room_id` ASC) VISIBLE,
  INDEX `FKi3680iwv3olco0f4ham6idr4u` (`room_row_id` ASC) VISIBLE,
  INDEX `FKlply6gd8uk8rrqr1muf7s8hn` (`screening_id` ASC) VISIBLE,
  CONSTRAINT `FKg993pi7ucgy616icmddq8u335`
    FOREIGN KEY (`room_id`)
    REFERENCES `cinema`.`rooms` (`id`),
  CONSTRAINT `FKi3680iwv3olco0f4ham6idr4u`
    FOREIGN KEY (`room_row_id`)
    REFERENCES `cinema`.`room_rows` (`id`),
  CONSTRAINT `FKlply6gd8uk8rrqr1muf7s8hn`
    FOREIGN KEY (`screening_id`)
    REFERENCES `cinema`.`screenings` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `cinema`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`tickets` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `reservation_expiration` TIMESTAMP NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `screening_start_time` TIMESTAMP NULL DEFAULT NULL,
  `surname` VARCHAR(255) NULL DEFAULT NULL,
  `type` TINYINT NULL DEFAULT NULL,
  `screening_id` BIGINT NULL DEFAULT NULL,
  `seat_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_dvmspsturfwlx8cf8f3ewn87c` (`seat_id` ASC) VISIBLE,
  INDEX `FKa8cgc3b3atbn8sr3nebdygo4a` (`screening_id` ASC) VISIBLE,
  CONSTRAINT `FK1f6n3pv4b80wl6gj4ra32ctxk`
    FOREIGN KEY (`seat_id`)
    REFERENCES `cinema`.`seats` (`id`),
  CONSTRAINT `FKa8cgc3b3atbn8sr3nebdygo4a`
    FOREIGN KEY (`screening_id`)
    REFERENCES `cinema`.`screenings` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
