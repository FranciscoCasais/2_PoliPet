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
-- Table `mydb`.`animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`animal` (
  `idAnimal` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `especie` VARCHAR(45) NULL,
  `raza` VARCHAR(45) NULL,
  `edad` VARCHAR(45) NULL,
  `descripcion` VARCHAR(400) NULL,
  `genero` VARCHAR(45) NULL,
  PRIMARY KEY (`idAnimal`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`persona` (
  `idPersona` INT NOT NULL,
  `edad` INT NULL,
  `nombre` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `telefono` INT NULL,
  `direccion` VARCHAR(45) NULL,
  `ocupacion` VARCHAR(45) NULL,
  `experiencia` VARCHAR(45) NULL,
  PRIMARY KEY (`idPersona`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`vacuna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`vacuna` (
  `idVacuna` INT NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `dosis` FLOAT NULL,
  `fecha` DATE NULL,
  `animales_idAnimal` INT NOT NULL,
  PRIMARY KEY (`idVacuna`),
  INDEX `fk_vacunas_animales1_idx` (`animales_idAnimal` ASC) VISIBLE,
  CONSTRAINT `fk_vacunas_animales1`
    FOREIGN KEY (`animales_idAnimal`)
    REFERENCES `mydb`.`animal` (`idAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitud` (
  `animal_idAnimal` INT NOT NULL,
  `persona_idPersona` INT NOT NULL,
  `estado` INT NULL,
  `fechaAdopcion` DATE NULL,
  `fechaEnvio` DATE NULL,
  PRIMARY KEY (`animal_idAnimal`, `persona_idPersona`),
  INDEX `fk_animal_has_persona_persona1_idx` (`persona_idPersona` ASC) VISIBLE,
  INDEX `fk_animal_has_persona_animal1_idx` (`animal_idAnimal` ASC) VISIBLE,
  CONSTRAINT `fk_animal_has_persona_animal1`
    FOREIGN KEY (`animal_idAnimal`)
    REFERENCES `mydb`.`animal` (`idAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_has_persona_persona1`
    FOREIGN KEY (`persona_idPersona`)
    REFERENCES `mydb`.`persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
