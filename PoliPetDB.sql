drop database mydb;
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
-- Table `mydb`.`solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitud` (
  `idSolicitud` INT NOT NULL,
  `estado` VARCHAR(45) NULL,
  `fechaAdopcion` DATE NULL,
  `fechaEnvio` DATE NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB;


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
  `solicitudes_idSolicitud` INT NOT NULL,
  PRIMARY KEY (`idAnimal`),
  INDEX `fk_animales_solicitudes1_idx` (`solicitudes_idSolicitud` ASC) VISIBLE,
  CONSTRAINT `fk_animales_solicitudes1`
    FOREIGN KEY (`solicitudes_idSolicitud`)
    REFERENCES `mydb`.`solicitud` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`persona`
-- -----------------------------------------------------
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


-- -----------------------------------------------------
-- Table `mydb`.`vacuna`
-- -----------------------------------------------------
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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


/*select * from animal;
select * from vacuna;
/*select * from persona;
select * from solicitud*/

-- 1-A --
DELIMITER //
create procedure animalesAdopcion()
begin
    declare idAnimal int;
    declare nombre varchar(45);
    declare especie varchar(45);
    declare raza varchar(45);
    declare edad int;
    declare genero varchar(45);
    declare done boolean default false;
    declare cursor1 cursor for select animal.idAnimal, animal.nombre, animal.especie, animal.raza, animal.edad, animal.genero from animal inner join solicitud;
    declare continue handler for not found set done = true;
    open cursor1;
    read_loop : loop
        fetch cursor1 into idAnimal, nombre, especie, raza, edad, genero;
        select idAnimal, nombre, especie, raza, edad, genero;
        if done then leave read_loop;
        end if;
    end loop;
    close cursor1;
end //
DELIMITER ;
-- 1-B --
delimiter //
create procedure esValida (in idPersona_ int, in idAnimal_ int, out valida boolean)
begin
if ((select edad from persona where idPersona = idPersona_) >= 18 and 1 <= (select count(*) from vacuna where animal_idAnimal = idAnimal_ and fecha = year(GETDATE()))) then
set valida = true;
else 
set valida = false;
end if;
end//
delimiter ;


-- 1-C --
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



-- 1-D --
DELIMITER //
CREATE FUNCTION adopcionesPersona (idPersona_ int)
RETURNS int
READS SQL DATA
BEGIN 	
	declare cantAdop int;
    select count(*) from solicitud where idPersona_ = idPersona and fechaAdopcion = month(GETDATE()) into cantAdop;
	return (antAdop);
END //
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


