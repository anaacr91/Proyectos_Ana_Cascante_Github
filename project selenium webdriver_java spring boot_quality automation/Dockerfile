# OBLIGATORIO: EJECUTAR PRIMERO MAVEN: ./mvnw clean package -DskipTests
FROM eclipse-temurin:23-jre-alpine
# copiar el jar
COPY target/*.jar app.jar
# ejecutar el jar
CMD ["java", "-jar", "app.jar"]

# Construir la imagen
# docker build -t testing_g2_movies_24:1.0.0 .

# Ejecutar la imagen
# docker run -p 8080:8080 testing_g2_movies_24