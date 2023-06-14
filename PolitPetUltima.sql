drop database mydb;
-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
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
  `animal_idAnimal` INT NOT NULL,
  PRIMARY KEY (`idVacuna`),
  INDEX `fk_vacuna_animal1_idx` (`animal_idAnimal` ASC) VISIBLE,
  CONSTRAINT `fk_vacuna_animal1`
    FOREIGN KEY (`animal_idAnimal`)
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
  `estado` int NULL,
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

-- 1-A --
DELIMITER //
create procedure animalesAdopcion()
    declare edad int;
    declare genero varchar(45);
    declare done boolean default false;
    declare cursor1 cursor for select animal.idAnimal, animal.nombre, animal.especie, animal.raza, animal.edad, animal.genero from animal inner join solicitud;
    declare continue handler for not found set done = true;
    open cursor1;
    read_loop : loop
DELIMITER //
CREATE FUNCTION porcentajeAnimales(especie_ varchar(45))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE aux INT;
    DECLARE total INT;
    DECLARE porcentaje DOUBLE;
    SELECT COUNT(*) INTO total FROM animal;
    SELECT COUNT(*) INTO aux FROM animal WHERE especie = especie_ AND idAnimal IN (SELECT animal_idAnimal FROM vacunas);
    SET porcentaje = (aux / CAST(100 AS decimal)) * total;
    RETURN (porcentaje);
END //
DELIMITER ;
DELIMITER //
CREATE FUNCTION adopcionesPersona (idPersona_ int)
RETURNS int
READS SQL DATA
BEGIN 	
	declare cantAdop int;
    select count(*) from solicitud where idPersona_ = idPersona and fechaAdopcion = month(GETDATE()) into cantAdop;
DELIMITER ;

-- 1-E --
DELIMITER //
CREATE FUNCTION masAdopciones()
RETURNS VARCHAR(255)
READS SQL DATA
BEGIN
  DECLARE correo_ VARCHAR(255);
  DECLARE aux INT;

  SELECT cliente.email, COUNT(solicitud.idSolicitud)
  INTO correo_, aux
  FROM cliente
  JOIN solicitud ON cliente.idCliente = solicitud.idCliente
  GROUP BY cliente.email
  ORDER BY COUNT(solicitud.idSolicitud) DESC
  LIMIT 1;
  RETURN correo_;
END //
DELIMITER ;

-- 1)f)
delimiter //
create procedure especiesPorSolicitud()
begin
	declare idAnimal int;
    declare especieAnimal varchar(45);
	declare numeroSolicitud int default 1;
	declare terminar boolean default 0;
    declare nombreCursor cursor for select animal_idAnimal from solicitud;
    declare continue handler for not found set terminar=1;
    open nombreCursor;
    bucle:loop
		fetch nombreCursor into idAnimal;
        if terminar=1 then
			leave bucle;
		end if;
        select animal.especie into especieAnimal from animal where animal.idAnimal=idAnimal;
        select numeroSolicitud as "N° de solicitud",especieAnimal as "Especie del animal";
        set numeroSolicitud=numeroSolicitud+1;
	end loop bucle;
    close nombreCursor;
end //
delimiter ;
select * from animal;
