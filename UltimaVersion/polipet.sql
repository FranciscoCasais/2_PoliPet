-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema PoliPet
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PoliPet
-- -----------------------------------------------------
drop schema if exists `PoliPet`;
CREATE SCHEMA IF NOT EXISTS `PoliPet` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `PoliPet` ;

-- -----------------------------------------------------
-- Table `PoliPet`.`Animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`Animal` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL DEFAULT NULL,
  `Especie` VARCHAR(45) NULL DEFAULT NULL,
  `Raza` VARCHAR(45) NULL DEFAULT NULL,
  `Fecha_nacimiento` DATE NULL DEFAULT NULL,
  `Genero` VARCHAR(45) NULL DEFAULT NULL,
  `Descripcion` VARCHAR(400) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `PoliPet`.`Vacuna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`Vacuna` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `PoliPet`.`DetallesVacuna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`DetallesVacuna` (
  `Animal_ID` INT NOT NULL,
  `Vacuna_ID` INT NOT NULL,
  `Dosis` FLOAT NULL DEFAULT NULL,
  `Fecha` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`Animal_ID`, `Vacuna_ID`),
  INDEX `fk_Animal_has_Vacuna_Vacuna1_idx` (`Vacuna_ID` ASC) VISIBLE,
  INDEX `fk_Animal_has_Vacuna_Animal_idx` (`Animal_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Animal_has_Vacuna_Animal`
    FOREIGN KEY (`Animal_ID`)
    REFERENCES `PoliPet`.`Animal` (`ID`),
  CONSTRAINT `fk_Animal_has_Vacuna_Vacuna1`
    FOREIGN KEY (`Vacuna_ID`)
    REFERENCES `PoliPet`.`Vacuna` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `PoliPet`.`Estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`Estado` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Detalles` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `PoliPet`.`Persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`Persona` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre_completo` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  `Telefono` VARCHAR(45) NULL DEFAULT NULL,
  `Direccion` VARCHAR(45) NULL DEFAULT NULL,
  `Fecha_nacimiento` DATE NULL DEFAULT NULL,
  `Ocupacion` VARCHAR(45) NULL DEFAULT NULL,
  `Experiencia_previa` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `PoliPet`.`Solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PoliPet`.`Solicitud` (
  `Persona_ID` INT NOT NULL,
  `Animal_ID` INT NOT NULL,
  `Fecha_envio` DATE NULL DEFAULT NULL,
  `Fecha_adopcion` DATE NULL,
  `Estado_ID` INT NOT NULL,
  PRIMARY KEY (`Persona_ID`, `Animal_ID`),
  INDEX `fk_Persona_has_Animal_Animal1_idx` (`Animal_ID` ASC) VISIBLE,
  INDEX `fk_Persona_has_Animal_Persona1_idx` (`Persona_ID` ASC) VISIBLE,
  INDEX `fk_Solicitud_Estado1_idx` (`Estado_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Persona_has_Animal_Animal1`
    FOREIGN KEY (`Animal_ID`)
    REFERENCES `PoliPet`.`Animal` (`ID`),
  CONSTRAINT `fk_Persona_has_Animal_Persona1`
    FOREIGN KEY (`Persona_ID`)
    REFERENCES `PoliPet`.`Persona` (`ID`),
  CONSTRAINT `fk_Solicitud_Estado1`
    FOREIGN KEY (`Estado_ID`)
    REFERENCES `PoliPet`.`Estado` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

delimiter //

create procedure animalesSolicitados()
begin
	select ID from Animal inner join Solicitud;
end //

create function mayorDeEdad(fecha date) returns boolean deterministic
begin
	if year(current_date())-year(fecha)>18 then
		return true;
	else if year(current_date())-year(fecha)=18 and month(current_date())>month(fecha) then
		return true;
	else if year(current_date())-year(fecha)=18 and month(current_date())=month(fecha) and day(current_date())>=day(fecha) then
		return true;
	else return false;
    end if;
    end if;
    end if;
end //

create procedure esValida(in idAnimal int,in idPersona int,out resultado boolean)
begin
	declare fecha date;
    set fecha=(select Fecha_nacimiento from Persona where ID=idPersona);
	if mayorDeEdad(fecha) and 1<=(select count(*) from DetallesVacuna where Animal_ID=idAnimal and year(Fecha)=year(current_date())) then set resultado=true;
    else set resultado=false;
    end if;
end//

CREATE FUNCTION porcentajeAnimalesVacunados(especie varchar(45)) RETURNS float deterministic
BEGIN
	declare porcentaje float;
    declare aux int;
    declare total int;
    select count(*) into total from animal;
    select count(*) into aux from Animal where especie=Especie and ID in (select Animal_ID from DetallesVacuna);                      
	set porcentaje = aux*100/total;
    RETURN porcentaje;
END //

CREATE FUNCTION adopcionesPersona (idPersona int)
RETURNS int deterministic
BEGIN 	
	declare cantAdop int;
    select count(*) into cantAdop from Solicitud where idPersona=Persona_ID and month(Fecha_adopcion) = month(current_date());
	return antAdop;
END //

create function masAdopciones() returns varchar(45) deterministic
begin
	declare email varchar(45);
    select Email into email from Persona inner join Solicitud on ID=Persona_ID where count(*)=(select max(cantidadAdopciones) from (select count(*) as "cantidadAdopciones" from Solicitud group by Persona_ID) as tabla1);
    return email;
end //

create procedure especiesSolicitadas()
begin
	select Especie from Animal inner join Solicitud;
end //

delimiter ;