## Ejercicio #2 – INSERT, UPDATE y DELETE



## a)	Hay 6 médicos preventivistas, 2 informáticos y dos administrativos.
INSERT INTO `contxi_tecnicos`(`nombre`, `apellido1`, `apellido2`, `funcion`, `correo`, `activo`, `comentarios`) VALUES 
('pere','martinez','garcia','preventivista','pmartinez@medicos.com','1', ''),
('antoni','roca','mora','preventivista','aroca@medicos.com','1', ''),
('joan','perez','gonzalez','preventivista','jperez@medicos.com','1', ''),
('mar','muñoz','garcia','preventivista','mmuñoz@medicos.com','1', ''),
('ana','murcia','garcia','preventivista','amurcia@medicos.com','1', ''),
('maria','quiles','garcia','preventivista','mquiles@medicos.com','1', ''),
('ferran','pou','garcia','informatic','fpou@tic.com','1', ''),
('monica','villacampa','garcia','informatic','mvillacampa@tic.com','1', ''),
('ester','sanchez','garcia','administratiu','esanchez@adm.com','1', ''),
('miquel','gomez','garcia','administratiu','mgomez@adm.com','1', '');


## b)	Hay 10 peticionarios que han hecho cada uno, una petición de datos (contxi)
INSERT INTO `contxi_peticionarios`(`equipo`, `nombre`, `apellido1`, `apellido2`, `cargo`, `telefono`, `activo`, `comentarios`) VALUES 
('C','maria','dalmau','arenyes','administrativo','654789314','1',''),
('B','sonia','mijana','trillo','informatico','654871714','1',''),
('A','quim','crespo','moreno','medico','625478524','1',''),
('C','miriam','rey','villar','administrativo','678954254','1',''),
('A','yaiza','vila','trias','medico','698587264','1',''),
('C','aleix','fernandez','arenyes','administrativo','684659754','1',''),
('A','guillermo','yelo','arenyes','medico','678945624','1',''),
('B','carlos','miranda','arenyes','informatico','698761542','1',''),
('C','alberto','reinozo','arenyes','administrativo','678932144','1',''),
('B','gerard','gras','arenyes','informatico','664123544','1','');

## c)	Dar de alta dos peticiones nuevas. Estarán pendientes de asignar
INSERT INTO `contxi_estados`( `estado`, `activo`, `comentarios`) VALUES 
('PENDIENTE DE ASIGNAR','1',''),
('ASIGNADO','1',''),
('RESUELTO','0','');
INSERT INTO `contxi_peticiones`(`id_tecnico`, `categoria`, `id_estado`, `id_peticionario`, `falta`, `fbaja`, `titulo`, `comentarios`) VALUES 
(NULL,'salut',1,1,'2022-12-31 10:15:23','2023-01-01 12:15:23','VACUNA',NULL),
(NULL,'salut',1,2,'2022-11-30 10:15:23','2023-01-02 13:15:23','COVID',NULL);


## d)	Asignar una de esas peticiones (apartado c) a un médico y la otra a un informático.
UPDATE `contxi_peticiones` SET `id_tecnico`=4,`id_estado`=2 WHERE `identificador`=1;
UPDATE `contxi_peticiones` SET `id_tecnico`=8,`id_estado`=2 WHERE `identificador`=2;


## e)	De las peticiones del apartado b) borrar dos de ellas
DELETE FROM `contxi_peticionarios` WHERE `identificador`= 10 OR `identificador`= 9;
