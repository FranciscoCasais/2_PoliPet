-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`solicitudes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitudes` (
  `idSolicitud` INT NOT NULL,
  `estado` VARCHAR(45) NULL,
  `fechaAdopcion` DATE NULL,
  `fechaEnvio` DATE NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`animales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`animales` (
  `idAnimal` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `especie` VARCHAR(45) NULL,
  `raza` VARCHAR(45) NULL,
  `edad` VARCHAR(45) NULL,
  `descripcion` VARCHAR(400) NULL,
  `genero` VARCHAR(45) NULL,
  `solicitudes_idSolicitud` INT NOT NULL,
  PRIMARY KEY (`idAnimal`),
  INDEX `fk_animales_solicitudes1_idx` (`solicitudes_idSolicitud` ASC) VISIBLE,
  CONSTRAINT `fk_animales_solicitudes1`
    FOREIGN KEY (`solicitudes_idSolicitud`)
    REFERENCES `mydb`.`solicitudes` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`personas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`personas` (
  `idPersona` INT NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `telefono` INT NULL,
  `direccion` VARCHAR(45) NULL,
  `ocupacion` VARCHAR(45) NULL,
  `experiencia` VARCHAR(45) NULL,
  `solicitudes_idSolicitud` INT NOT NULL,
  PRIMARY KEY (`idPersona`),
  INDEX `fk_personas_solicitudes1_idx` (`solicitudes_idSolicitud` ASC) VISIBLE,
  CONSTRAINT `fk_personas_solicitudes1`
    FOREIGN KEY (`solicitudes_idSolicitud`)
    REFERENCES `mydb`.`solicitudes` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`vacunas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`vacunas` (
  `idVacuna` INT NOT NULL,
  `dosis` INT NULL,
  `fecha` DATE NULL,
  `animales_idAnimal` INT NOT NULL,
  PRIMARY KEY (`idVacuna`),
  INDEX `fk_vacunas_animales1_idx` (`animales_idAnimal` ASC) VISIBLE,
  CONSTRAINT `fk_vacunas_animales1`
    FOREIGN KEY (`animales_idAnimal`)
    REFERENCES `mydb`.`animales` (`idAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitudes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitudes` (
  `idSolicitud` INT NOT NULL,
  `estado` VARCHAR(45) NULL,
  `fechaAdopcion` DATE NULL,
  `fechaEnvio` DATE NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`table5`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`table5` (
)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
