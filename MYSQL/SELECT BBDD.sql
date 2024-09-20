## Ejercicio #3 – SELECT

## a)	Listado de los peticionarios
SELECT * FROM `contxi_peticionarios` WHERE 1;

## b)	Listado de los miembros de la unidad 
SELECT `identificador`, `nombre`, `apellido1`, `apellido2`, `funcion`, `correo`, `activo`, `comentarios` FROM `contxi_tecnicos` WHERE 1;

## c)	Listado de las peticiones pendientes de asignar
SELECT * FROM `contxi_peticiones` WHERE `id_tecnico` IS NULL;

## d)	Listado de las peticiones agrupadas por la unidad del peticionario
SELECT * FROM `contxi_peticiones` 
JOIN contxi_peticionarios ON contxi_peticionarios.identificador= contxi_peticiones.id_peticionario
WHERE 1
ORDER BY equipo;

## e)	Listado de las peticiones que hayan tardado más de 10 días en ser resueltas
SELECT * FROM `contxi_peticiones` WHERE DATEDIFF(fbaja, falta)>10;

## f)	Contar las peticiones resueltas por cada uno de los miembros de la unidad, por mes y año.
SELECT `id_tecnico`, YEAR(fbaja), MONTH(fbaja), COUNT(*) 
FROM `contxi_peticiones` 
WHERE `fbaja` IS NOT NULL
GROUP BY id_tecnico, YEAR(fbaja), MONTH(fbaja);

## g)	Listado de los peticionarios que tengan peticiones resueltas por diferentes miembros de la unidad. 
## (Los peticionarios a los que todas sus peticiones han sido resueltas por el mismo miembro de la unidad no deben aparecer en este listado)
SELECT id_peticionario, COUNT(DISTINCT(id_tecnico))
FROM contxi_peticiones
WHERE `fbaja` IS NOT NULL
GROUP BY `id_peticionario`
HAVING COUNT(DISTINCT(id_tecnico))>1;
