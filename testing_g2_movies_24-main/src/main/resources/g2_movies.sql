-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: peliculasgrupo2
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

use g2_movies;
--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Acción','Películas con escenas de acción intensa'),(2,'Ciencia Ficción','Películas basadas en temas de ciencia y tecnología'),(3,'Drama','Películas con elementos dramáticos fuertes'),(4,'Crimen','Películas sobre crimen y justicia'),(5,'Suspenso','Películas que mantienen el suspenso hasta el final');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_categoria`
--

DROP TABLE IF EXISTS `movie_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_categoria` (
  `movie_id` INT NOT NULL,
  `categoria_id` INT NOT NULL,
  PRIMARY KEY (`movie_id`, `categoria_id`), -- Clave primaria compuesta
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `movie_categoria_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE,
  CONSTRAINT `movie_categoria_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_categoria`
--

LOCK TABLES `movie_categoria` WRITE;
/*!40000 ALTER TABLE `movie_categoria` DISABLE KEYS */;
INSERT INTO `movie_categoria` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(4,3),(5,3),(4,4),(5,4),(5,5);
/*!40000 ALTER TABLE `movie_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `duration` INT NOT NULL,
  `year` INT NOT NULL,
  `Available` BOOLEAN DEFAULT TRUE,	
  `rental_price_per_day` INT NOT NULL,
  `categoria_id` INT DEFAULT NULL,
  PRIMARY KEY (`id`), -- Solo una PRIMARY KEY
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `movie_fk_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rental`;
CREATE TABLE rental (
    id INT NOT NULL AUTO_INCREMENT,                
    movie_id INT DEFAULT NULL,                            
    customer_id INT DEFAULT NULL,                         
    rental_date DATETIME NOT NULL,                       
    return_due_date DATETIME DEFAULT NULL,               
    returned_date DATETIME DEFAULT NULL,                 
    rental_price DOUBLE DEFAULT NULL,        
    PRIMARY KEY (id),            
    CONSTRAINT fk_rental_movie FOREIGN KEY (movie_id) REFERENCES movie (id) ON DELETE CASCADE,  -- Relación con 'movie'
    CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE -- Relación con 'customer'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`id`, `name`, `duration`, `year`, `Available`, `rental_price_per_day`, `categoria_id`)
VALUES (1,'Inception',148,2010,true,5.00,1),(2,'Matrix',136,1999,true,5.00,1),(3,'Mad Max: Fury Road',120,2015,true,5.00,2),(4,'The Godfather',175,1972,true,5.00,2),(5,'Pulp Fiction',154,1994,true,5.00,2);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `customer_movies`;
CREATE TABLE `customer_movies` (
  `customer_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `movie_id`),
  CONSTRAINT `fk_customer_movies_customer`
    FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_customer_movies_movie`
    FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE customer_movies ADD CONSTRAINT unique_customer_movie UNIQUE (customer_id, movie_id);

INSERT INTO `customer_movies` (`customer_id`, `movie_id`) VALUES(1, 1),(1, 2),(2, 3),(3, 1);

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'juan@example.com','password1','Juan', 'Cuesta'),(2,'maria@example.com','password2','Maria', 'Jesus'),(3,'luis@example.com','password3','Luis', 'Fernandez'),(4,'ana@example.com','password4','Ana', 'Ruiz'),(5,'carlos@example.com','password5','Carlos', 'Latre');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valoracion`
--

DROP TABLE IF EXISTS `valoracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE valoracion (
    id int NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    movie_id int NOT NULL,
    comentario text,
    puntuacion int DEFAULT NULL,
    PRIMARY KEY (id),
    KEY customer_id (customer_id),
    KEY movie_id (movie_id),
    CONSTRAINT valoracion_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    CONSTRAINT valoracion_ibfk_2 FOREIGN KEY (movie_id) REFERENCES movie (id) ON DELETE CASCADE,
    CONSTRAINT valoracion_chk_1 CHECK ((puntuacion BETWEEN 1 AND 10))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valoracion`
--

LOCK TABLES `valoracion` WRITE;
/*!40000 ALTER TABLE `valoracion` DISABLE KEYS */;
INSERT INTO `valoracion` (id, customer_id, movie_id, comentario, puntuacion) 
VALUES (1,1,1,'Increíble historia y efectos visuales',9),(2,2,1,'Muy original y bien dirigida',8),(3,3,2,'Un clásico del cine moderno',10),(4,1,3,'Acción sin parar',9),(5,2,4,'Una obra maestra',10),(6,4,5,'Excelente narrativa y diálogos',9),(7,3,1,'Me encantó el final',8),(8,5,3,'Intensa y emocionante',7),(9,4,2,'Revolucionaria para su época',9),(10,5,5,'Gran combinación de géneros',8);
/*!40000 ALTER TABLE `valoracion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-10 19:09:04
