-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-12-2023 a las 18:24:15
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bbddsopaletras`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidas`
--

CREATE TABLE `partidas` (
  `id` int(11) NOT NULL,
  `tablero` text NOT NULL,
  `palabras_a_encontrar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `palabras_encontradas` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `terminado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partidas`
--

INSERT INTO `partidas` (`id`, `tablero`, `palabras_a_encontrar`, `palabras_encontradas`, `terminado`) VALUES
(27, 'K,B,H,C,F,K,O,H,L,F,X,D,D,O,T,R,O,D,N,B,P,P,R,U,E,V,A,Z,K,V,E,W,J,H,S,P,V,X,A,Z,P,Z,M,C,T,K,F,R,V,S,H,S,C,S,A,C,H,G,P,T,E,C,U,A,E,E,X,B,B,Y,F,O,H,R,G,B,E,M,J,P,C,M,A,U,H,C,L,K,L,O,Q,G,W,M,V,J,Y,M,O,W', 'TEST,PRUEVA,OTRO', ',TEST,PRUEVA,OTRO', 1),
(28, 'W,M,G,G,X,Z,O,O,K,D,I,U,C,E,T,J,I,T,S,K,F,V,I,D,E,O,U,P,O,U,J,I,M,H,S,Y,M,W,T,K,A,C,F,P,T,W,X,K,F,O,G,T,R,J,T,K,H,X,E,P,P,Z,M,V,W,L,O,W,W,F,O,M,P,M,I,D,M,B,I,I,U,I,J,M,L,Z,E,U,R,N,V,R,G,J,O,T,R,P,A,C', 'TEST,VIDEO,PAC', ',TEST,PAC,VIDEO', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `partidas`
--
ALTER TABLE `partidas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `partidas`
--
ALTER TABLE `partidas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
