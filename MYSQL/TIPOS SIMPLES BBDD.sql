/* EJERCICIO 1*/

create or replace TYPE cliente_standar_T FORCE AS OBJECT( 
  idcliente varchar2(10), 
  pais varchar2(30),
  nombre varchar2(30),
  apellido1 varchar2(25),
  apellido2 varchar2(25),
  correo_electronico varchar2(200), 
  valoracion_comercial number, 
  valoracion_pagos number, 
  participa_eventos varchar2(1),
  comunicacion_mail varchar2(1), 
  CONSTRUCTOR FUNCTION cliente_standar_T(idCliente VARCHAR2, pais VARCHAR2, nombre VARCHAR2, apellido1 VARCHAR2, correo_electronico VARCHAR2) RETURN SELF AS RESULT,
  MEMBER FUNCTION get_info RETURN VARCHAR2,
  MEMBER PROCEDURE evaluar_cliente,  
  MAP MEMBER FUNCTION ordenarcliente RETURN NUMBER
  
) NOT FINAL;
/
    
CREATE OR REPLACE TYPE cliente_vip_T UNDER cliente_standar_T (
    fecha_alta_vip DATE,
    credito NUMBER(8,2),
    OVERRIDING MEMBER PROCEDURE evaluar_cliente,
    OVERRIDING MEMBER FUNCTION get_info RETURN VARCHAR2
);
/
    
CREATE OR REPLACE TYPE BODY cliente_standar_T AS
    CONSTRUCTOR FUNCTION cliente_standar_T(idCliente VARCHAR2, pais VARCHAR2, nombre VARCHAR2, apellido1 VARCHAR2, correo_electronico VARCHAR2) RETURN SELF AS RESULT IS
	BEGIN
    	SELF.idCliente := idCliente;
		SELF.pais := pais;
		SELF.nombre := nombre;
		SELF.apellido1 := apellido1;
		SELF.correo_electronico := correo_electronico;
		RETURN;
	END;

    MEMBER FUNCTION get_info RETURN VARCHAR2 IS
    BEGIN
		RETURN 'idcliente: ' || idcliente || ', pais: ' || pais || ', nombre: ' || nombre || ', apellido1: ' || apellido1 || ', apellido2: ' || apellido2 ||
      	', correo_electronico : ' || correo_electronico || ', valoracion comercial : ' || valoracion_comercial || ', valoracion pagos: ' || valoracion_pagos ||
    	', participa eventos : ' || participa_eventos || ', comunicacion mail : ' || comunicacion_mail;
    END;

	MEMBER PROCEDURE evaluar_cliente IS
		cliente NUMBER;
	BEGIN
		cliente := (valoracion_comercial*3) + (valoracion_pagos*3) + (participa_eventos*2) + (comunicacion_mail*2);

		if cliente < 5 then
           cliente :=  ((valoracion_comercial*4) + (valoracion_pagos*4))/2;
        else
           cliente := ((valoracion_comercial*4) + (valoracion_pagos*4) + (participa_eventos) + (comunicacion_mail))/4;
        end if;
            
		DBMS_OUTPUT.PUT_LINE('El cliente ' || idcliente || ' tiene una valoración comercial de: ' || cliente);
	END;

	MAP MEMBER FUNCTION ordenarcliente RETURN NUMBER IS
	BEGIN
		RETURN valoracion_comercial;  --ordena por valoracion_comercial
	END;
END;
/
    
CREATE OR REPLACE TYPE BODY cliente_vip_T AS
    OVERRIDING MEMBER PROCEDURE evaluar_cliente IS
    	cliente NUMBER;
    BEGIN
        cliente := (valoracion_comercial*3) + (valoracion_pagos*3) + (participa_eventos*2) + (comunicacion_mail*2);
        
        if cliente < 9 then
            if cliente > 5 then
            	cliente :=  (valoracion_comercial*0.4) + (valoracion_pagos*0.4) + (participa_eventos*0.1);
            else
            	cliente :=  (valoracion_comercial*0.4) + (valoracion_pagos*0.4);
            end if;
        else
            cliente := (valoracion_comercial*0.4) + (valoracion_pagos*0.4) + (participa_eventos*0.1) + (comunicacion_mail*0.1);
        end if; 
        
        DBMS_OUTPUT.PUT_LINE('El cliente ' || idcliente || ' tiene una valoración comercial de: ' || cliente);       
    END;

	OVERRIDING MEMBER FUNCTION get_info RETURN VARCHAR2 IS
    BEGIN
        RETURN 'idcliente: ' || idcliente || ', pais: ' || pais || ', nombre: ' || nombre || ', apellido1: ' || apellido1 || ', apellido2: ' || apellido2 ||
      	', correo_electronico : ' || correo_electronico || ', valoracion comercial : ' || valoracion_comercial || ', valoracion pagos: ' || valoracion_pagos ||
    	', participa eventos : ' || participa_eventos || ', comunicacion mail : ' || comunicacion_mail || ' Fecha alta VIP: ' || fecha_alta_vip || ' Crédito: ' || credito || '€';
	END;
END;
/
    
DECLARE
	c1 cliente_standar_T;
	c2 cliente_standar_T;
	c4 cliente_vip_T;
BEGIN
    c1 := NEW cliente_standar_T('1234655D','España','Ana','Perez','Galdos','cliente@lasalle.es',3,2,4,0);
	c2 := NEW cliente_standar_T('67823490Z','Portugal','Pedro','Martin','Dominguez','cliente@lasalles.es',5,5,1,1);

	c4 := NEW cliente_vip_T('4325324F','Espana','Ana','Perez','Galdos','cliente@lasalle.es',4,3,1,0, TO_DATE('2022-04-04','YYYY-MM-DD'), 78956);
  	
    

	DBMS_OUTPUT.PUT_LINE('La datos del cliente es: ' || c1.get_info());
  	DBMS_OUTPUT.PUT_LINE('La datos del cliente es: ' || c2.get_info());

	DBMS_OUTPUT.PUT_LINE('La datos del cliente es: ' || c4.get_info());

	c1.evaluar_cliente();
	c2.evaluar_cliente();

	c4.evaluar_cliente();

	IF c1>c2 THEN
    	DBMS_OUTPUT.PUT_LINE('El cliente de España tiene mejor nota');
  	ELSIF c1<c2 THEN
		DBMS_OUTPUT.PUT_LINE('El cliente de Portugal tiene mejor nota');
  	ELSE 
    	DBMS_OUTPUT.PUT_LINE('Son iguales');
  	END IF;
END;
/