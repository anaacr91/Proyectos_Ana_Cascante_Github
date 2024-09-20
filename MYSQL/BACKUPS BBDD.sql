
/*Tarea #1 Back Ups*/

DROP PROCEDURE IF EXISTS backup_peticiones;
DELIMITER $$

CREATE PROCEDURE IF NOT EXISTS backup_peticiones(in ano int, in mes int)
BEGIN 
DECLARE archivoSalida varchar (40);
DECLARE nombre_tabla_fichero varchar (40);
DECLARE i INT;
-- genero un numero aleatorio para evitar que al volver a ejecutar la consulta, no ponga el archivo ya existe --
SET i= FLOOR(RAND()*(100+1));

set nombre_tabla_fichero= concat('peticiones_backup_',ano,'_',mes);
set @st = concat('CREATE TABLE IF NOT EXISTS ',nombre_tabla_fichero,' AS
select * from contxi_peticiones where 
year(FECHA_ALTA) like ', "'", ano, "'", ' and month(FECHA_ALTA) like ', "'", mes, "'");
    IF EXISTS(select @st) then
    prepare queryPeticiones from @st;
    execute queryPeticiones;
    deallocate prepare queryPeticiones;
    END IF;

SET archivoSalida=CONCAT('peticiones_backup_', ano, '-',mes,'-', i,'.txt');
 
SET @sq = CONCAT('select * from contxi_peticiones 
    where year(FECHA_ALTA) like ',"'",ano,"'", ' and month(FECHA_ALTA) like ',"'",mes,"'",
    ' into outfile ', "'", archivoSalida , "'");
    IF EXISTS(select @sq) then
PREPARE stmt2 FROM @sq;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;
END IF;

END $$
DELIMITER ;

call backup_peticiones('2022','05');

/*Tarea #2 Listado informe*/
USE `contxi`;

DROP procedure IF EXISTS `registro_peticiones`;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `informe_peticiones`; 
CREATE TABLE if not exists `informe_peticiones` (
  `peticionario` int(11) NOT NULL,
  `anno` int(11) NOT NULL,
  `mes` int(11) NOT NULL,
  `estado` int(11) NOT NULL,
  `num_peticiones` int(11) NOT NULL,
  PRIMARY KEY (`peticionario`,`anno`,`mes`,`estado`) 
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci; 

DELIMITER $$
CREATE PROCEDURE if not exists `registro_peticiones`()

BEGIN

DECLARE peticionario_actual INT;
DECLARE anno_actual INT;
DECLARE mes_actual INT;
DECLARE estado_actual INT;
DECLARE num_peticiones INT;
DECLARE finished INT DEFAULT FALSE; 

DECLARE cur1 CURSOR FOR
	SELECT id_peticionarios as peticionario_actual, 
	YEAR(fecha_alta) as anno_actual, MONTH(fecha_alta) as mes_actual, id_estado as estado_actual, COUNT(id_peticiones) AS num_peticiones
	FROM contxi_peticiones
	GROUP BY id_peticionarios, YEAR(fecha_alta), MONTH(fecha_alta), id_estado
	ORDER BY id_peticionarios, YEAR(fecha_alta), MONTH(fecha_alta), id_estado;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished=TRUE;
OPEN cur1;

label: LOOP
	FETCH cur1 INTO peticionario_actual, anno_actual, mes_actual, estado_actual, num_peticiones;
    IF finished THEN 
		LEAVE label;
	END IF;
    
	INSERT INTO informe_peticiones (peticionario, anno, mes, estado, num_peticiones)
	VALUES (peticionario_actual, anno_actual, mes_actual, estado_actual, num_peticiones);

Set @informe = CONCAT('El peticionario ', peticionario_actual, ' hizo ',num_peticiones, ' peticiones en ',mes_actual,'/',anno_actual,' en el ',estado_actual,CHAR(13,10));
 
SELECT @informe;

END LOOP;
CLOSE cur1;
END$$
DELIMITER ;

-- call registro_peticiones(); 
-- puedo ejecutar el call desde procedimientos, y me llena correctamente la tabla informe_peticiones
-- pero cuando lo vuelvo a ejecutar, me sale que la entrada 1-5-4 (primera linea de la tabla informe_peticiones), está duplicada, si la elimino esa fila, me deja volver a ejecutar el call de registro peticiones desde sql


/*Tarea #3 Auto documentación de la base de datos*/
USE `contxi`;
DROP procedure IF EXISTS `auto_documentacion`;

DELIMITER $$
USE `contxi`$$
CREATE PROCEDURE `auto_documentacion`()
BEGIN

DECLARE archivoSalida varchar (40);
DECLARE i INT;
SET i= FLOOR(RAND()*(30+1));-- =>0 y =<30


--   S A L I D A   P O R   P A N T A L L A

set @sql=concat('SELECT t.table_name,char(9),
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Tamaño (MB)",
(SELECT concat (column_name,char(9),column_type,char(9),column_comment) FROM INFORMATION_SCHEMA.COLUMNS 
where TABLE_SCHEMA=','"contxi"',' and TABLE_NAME=c.table_name and column_name=c.column_name) as "Campo | Tipo | Comentario"
FROM information_schema.TABLES as t inner join INFORMATION_SCHEMA.COLUMNS as c on t.table_name=c.table_name
WHERE t.TABLE_SCHEMA = ','"contxi"',
' ORDER BY c.table_name desc');
IF EXISTS(select @sql) then
    PREPARE stmt1 FROM @sql; 
    EXECUTE stmt1; 
    DEALLOCATE PREPARE stmt1;
END IF;

-- A R C H I V O   D E   S A L I D A

SET archivoSalida=CONCAT('documentacion_contxi','-',i,'.txt');
set @sql1=concat(@sql,' into outfile ', "'", archivoSalida , "'");
IF EXISTS(select @sql1) then
    PREPARE stmt1 FROM @sql1; 
    EXECUTE stmt1; 
    DEALLOCATE PREPARE stmt1;
END IF;

END$$

DELIMITER ;
;
call auto_documentacion();