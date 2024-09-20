/*
Tarea #1 Eventos
*/

## a)	Ejecute el procedimiento del ejercicio 1, tarea 1 cada día a las 2:00h AM.
use contxi;
DROP EVENT IF EXISTS T1event;
CREATE EVENT T1event
ON SCHEDULE EVERY 1 DAY
STARTS '2023-03-30 02:00:00'
ENDS CURRENT_TIMESTAMP+INTERVAL 1 year
DO
CALL backup_peticiones();

## b)	Ejecute el procedimiento del ejercicio 1, tarea 2 cada semana, el miércoles a las 18:00 PM.

DROP EVENT IF EXISTS T2event;
CREATE EVENT T2event
ON SCHEDULE EVERY 1 WEEK
STARTS '2023-03-29 18:00:00'
ENDS CURRENT_TIMESTAMP+INTERVAL 1 year
DO
CALL registro_peticiones();


## c)	Ejecute el procedimiento del ejercicio 1, tarea 3 cada mes, El último día del mes, a las 22:00 PM.

DROP EVENT IF EXISTS T3event;
CREATE EVENT T3event
ON SCHEDULE EVERY 1 MONTH
STARTS '2023-03-31 22:00:00'
ENDS CURRENT_TIMESTAMP+INTERVAL 1 year
DO
CALL auto_documentacion();