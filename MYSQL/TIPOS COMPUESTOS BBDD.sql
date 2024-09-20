/* EJERCICIO 2*/
CREATE OR REPLACE TYPE contact_T FORCE AS OBJECT(
  numero number,
  correo varchar2(50),
  tipo varchar2(1)
  );
/

CREATE OR REPLACE TYPE tipo_telefono FORCE AS varray(2) OF contact_T;
/


CREATE OR REPLACE TYPE clientes_Nested_Tbl FORCE AS TABLE OF cliente_standar_T;

/

CREATE OR REPLACE TYPE comercial_contactos_T AS OBJECT(
  idcomercial number,
  pais varchar2(25),
  nombre varchar2(50),
  apellido1 varchar2(50),
  apellido2 varchar2(50),
  telefono tipo_telefono,
  t_clientes clientes_Nested_Tbl,
  MEMBER FUNCTION displayDetails RETURN VARCHAR2
  );
/

CREATE OR REPLACE TYPE BODY comercial_contactos_T AS
	MEMBER FUNCTION displayDetails RETURN VARCHAR2 IS
	BEGIN
		RETURN 'ID: ' || idComercial || ', Nombre: ' || nombre || ', Primer apellido: ' || apellido1 || ', Segundo apellido: ' || apellido2;
	END;
END;
/


CREATE TABLE tbl_comercial_contactos_T OF comercial_contactos_T(idComercial PRIMARY KEY) NESTED TABLE t_clientes STORE AS tabla_clientes;
/

INSERT INTO tbl_comercial_contactos_T VALUES (1, 'Italia', 'Marta', 'Perez', 'Rodriguez', 
                                      tipo_telefono(
        											contact_T(123456789, 'Personal@gmail.com', 'P'), 
        											contact_T(987654321, 'Trabajo@gmail.com','T')),
        											clientes_Nested_Tbl(
        																cliente_standar_T('56432Z', 'Japon', 'Shin', 'Chan', 'Lee', 'konichigua@china.es', 5, 2, 'Y', 'N'), 
                                                                        cliente_standar_T('89453B', 'Portugal', 'Agustin', 'Tapia', 'Lorenzo', 'AGTAWP@Worldpadeltour.es', 2, 5, 'N', 'Y'), 
                                                                        cliente_standar_T('12356D', 'Marruecos', 'Omar', 'Moha', 'OMdelrio@Marracheck.ma')));

INSERT INTO TABLE(SELECT t_clientes FROM tbl_comercial_contactos_T WHERE idComercial = 1) VALUES (4, 'Rumania', 'Dimitri', 'Bereskof', 'Alexander', 'DBA@gmail.ru', 3, 3, 'N', 'N');

SELECT * FROM tbl_comercial_contactos_T  WHERE idComercial = 1;

SELECT * FROM tbl_comercial_contactos_T T, TABLE(T.t_clientes) WHERE T.idComercial = 1;




