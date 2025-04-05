proyecto nombre: 
testing_g2_movies_24
Logo:

Modelos:
Usuario->hecho por Ana
*id
*name
*email
*password
@OneToMany-> Pelicula


Valoración:->Gines

id
usuario @ManyToOne
película @ManyToOne
comentario: String
puntuación: Integer

Película:->David +Ana

id
name
duration
año
usuarios @ManyToOne
categoría @ManyToOne

Categoria->Dani

id
name
descripcion

Cine (Opcion)-> no

Diseño BBDD-> draw.io-> DIAGRAMA DE CLASES-> DIAGRAMA UML-> link de drive->GINES

BBDD-> PROGRAMA DE CLASE->david +Gines-> MY SQL WORKBENCH


## PASOS QUE DEBE REALIZAR CADA PERSONA:

1. Modelo: src/main/java/com/demo/model/Comentario.java
2. Repositorio: src/main/java/com/demo/repository/ComentarioRepository.java
3. Controlador: src/main/java/com/demo/controller/ComentarioController.java
4. HTML listado: src/main/java/resources/comentario-list.html
5. HTML detalle: src/main/java/resources/comentario-detail.html
6. HTML formulario: src/main/java/resources/comentario-form.html
+ ESTILISMO CSS
+ SERVICIO
+ CONSULTAS JPQL
7. Test unitario: src/test/java/com/demo/controller/ComentarioControllerUnitTest.java
8. Test integración parcial: src/test/java/com/demo/controller/ComentarioControllerPartialIntegrationTest.java
9. Test integración: src/test/java/com/demo/controller/ComentarioControllerIntegrationTest.java
10. Test UI funcional: src/test/java/com/demo/ui/ComentarioListTest.java
11. Github Actions y Sonar Cloud-> Que cada persona haga todas las capas de un modelo

