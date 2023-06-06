
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

CREATE TABLE IF NOT EXISTS `mydb`.`solicitud` (
  `idSolicitud` INT NOT NULL,
  `estado` VARCHAR(45) NULL,
  `fechaAdopcion` DATE NULL,
  `fechaEnvio` DATE NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`animal` (
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
    REFERENCES `mydb`.`solicitud` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`persona` (
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
    REFERENCES `mydb`.`solicitud` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`vacuna` (
  `idVacuna` INT NOT NULL,
  `dosis` INT NULL,
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

