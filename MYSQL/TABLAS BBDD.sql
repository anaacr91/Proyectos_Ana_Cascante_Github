## ej1. CREACIÓN DE LA BASE DE DATOS

CREATE DATABASE IF NOT EXISTS CONTXI DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_general_ci';

USE CONTXI;

/*considero que tanto estado(pendiente/resuelto) como activo (activo/inactivo) son dos campos booleanos*/
CREATE TABLE contxi_estados(
	identificador INT UNSIGNED AUTO_INCREMENT,
	estado VARCHAR (30),
    activo BOOLEAN,
    comentarios VARCHAR(200),
    PRIMARY KEY (identificador)
    );
	
	CREATE TABLE contxi_peticionarios(
	identificador INT UNSIGNED AUTO_INCREMENT,
    equipo VARCHAR (50),
    nombre VARCHAR (30),
    apellido1 VARCHAR (30),
    apellido2 VARCHAR (30),
    cargo VARCHAR (20),
    telefono INT,
    activo BOOLEAN,
    comentarios VARCHAR(200),
	PRIMARY KEY (identificador)
	);
	
	CREATE TABLE contxi_tecnicos(
    identificador INT UNSIGNED AUTO_INCREMENT,
    nombre VARCHAR (30),
    apellido1 VARCHAR (30),
    apellido2 VARCHAR (30),
    funcion VARCHAR (20),
    correo VARCHAR (40),
    activo BOOLEAN,
    comentarios VARCHAR(200),
	PRIMARY KEY (identificador)
	);
	
	CREATE TABLE contxi_peticiones(
identificador INT UNSIGNED AUTO_INCREMENT,
    id_tecnico INT UNSIGNED,
    categoria VARCHAR(50),
    id_estado INT UNSIGNED,
    id_peticionario INT UNSIGNED,
    falta DATETIME,
    fbaja DATETIME,
    titulo VARCHAR (50),
    comentarios VARCHAR(200),
    PRIMARY KEY (identificador)
); 
ALTER TABLE contxi_peticiones ADD CONSTRAINT FK_idtecnico FOREIGN KEY (id_tecnico) REFERENCES contxi_tecnicos (identificador) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE contxi_peticiones ADD CONSTRAINT FK_idestado FOREIGN KEY (id_estado) REFERENCES contxi_estados (identificador) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE contxi_peticiones ADD CONSTRAINT FK_idpeticionario FOREIGN KEY (id_peticionario) REFERENCES contxi_peticionarios (identificador) ON UPDATE CASCADE ON DELETE CASCADE;
