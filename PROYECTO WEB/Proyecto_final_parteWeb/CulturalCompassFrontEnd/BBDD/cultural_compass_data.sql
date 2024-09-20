-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-04-2024 a las 11:14:30
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cultural_compass_data`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name_category` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`id`, `name_category`) VALUES
(1, 'ARTE DIGITAL'),
(2, 'EXPOSICION CULTURAL'),
(3, 'TEATRO'),
(4, 'CONCIERTOS'),
(5, 'CULTURA CATALANA'),
(6, 'EXPOSICION ARTISTICA'),
(7, 'FERIA GASTRONOMICA'),
(8, 'EVENTO DEPORTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(50) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `pago` varchar(255) NOT NULL,
  `delivery` int(1) NOT NULL,
  `gps` varchar(255) NOT NULL,
  `orden` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `id_user`, `nombre`, `direccion`, `telefono`, `correo`, `pago`, `delivery`, `gps`, `orden`) VALUES
(1, 13, ' ', '', '', '', '   ', 0, 'https://www.google.com/maps/dir/?api=1&destination=,', 1),
(2, 12, ' ', '', '', '', ' Dolares  ', 0, 'https://www.google.com/maps/dir/?api=1&destination=41.39883109917547,2.1621864136504234', 2),
(3, 11, ' ', '', '', '', 'Francos Suizos   ', 0, 'https://www.google.com/maps/dir/?api=1&destination=,', 3),
(4, 12, ' ', '', '', '', '  Euros ', 0, 'https://www.google.com/maps/dir/?api=1&destination=,', 5),
(5, 13, 'ana cascante', 'lsdkfjsdlfkjsdlfksjdf', '000000000', 'anacascanterodriguez@gmail.com', 'Francos Suizos   ', 0, 'https://www.google.com/maps/dir/?api=1&destination=41.39885585126054,2.16218371800214', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE `evento` (
  `id` int(11) NOT NULL,
  `name_event` varchar(100) DEFAULT NULL,
  `start_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `end_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `description` varchar(1000) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `imagen` varchar(255) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `organizer_id` int(11) DEFAULT NULL,
  `precio_evento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evento`
--

INSERT INTO `evento` (`id`, `name_event`, `start_at`, `end_at`, `description`, `status`, `url`, `imagen`, `category_id`, `location_id`, `organizer_id`, `precio_evento`) VALUES
(1, 'THE GREAT LIBRARY OF TOMORROW', '2024-04-20 09:56:34', '2024-05-15 05:00:00', 'Más de 1000 m2 de instalaciones de arte digital para vivir inmersivamente en un metaverso de realidad virtual en el Espacio Aribau.', 1, 'https://www.thegreatlibraryoftomorrow.com', 'tomorrowland.jpg', 1, 1, 1, 20),
(2, 'DINOSAURIOS DE LA PATAGONIA ', '2024-04-20 09:56:39', '2024-06-02 07:00:00', 'Los descubrimientos más emocionantes sobre los dinosaurios del CosmoCaixa de Barcelona.', 1, 'https://cosmocaixa.org/es/p/dinosaurios-de-la-patagonia_a161193314?gad_source=1&gclid=CjwKCAjw48-vBh', 'dinosaurios patagonia.jpg', 1, 2, 2, 15),
(3, 'BARCELONA BEER FESTIVAL', '2024-04-20 09:56:44', '2024-04-24 20:01:36', 'El Barcelona Beer Festival es el punto de encuentro de aficionados y profesionales de la cerveza artesana más grande del sur de Europa. Una experiencia gastronómica que te adentrará en un auténtico universo sensorial donde descubrirás las mil y una posibilidades de esta suculenta bebida.', 1, 'https://barcelonabeerfestival.com/es/', 'bcn beer festival.jpg', 7, 3, 3, 20),
(4, 'OFFF.BARCELONA', '2024-04-20 09:57:14', '2024-04-06 20:09:18', 'Vuelve el festival de creatividad digital OFFF Barcelona. Conferencias, exposiciones, instalaciones interactivas y mucho más para descubrir la actualidad las personas más talentosas del sector.', 2, 'https://www.offf.barcelona/', 'offf barcelona.jpg', 6, 4, 4, 20),
(5, 'TROFEO CONDE GODO', '2024-04-20 09:57:28', '2024-04-21 20:15:45', 'El tenis de máximo nivel llega a Barcelona y enfrenta a las figuras más destacadas del mundo durante una emocionante semana.', 1, 'https://www.barcelonaopenbancsabadell.com/', 'conde godo.jpg', 8, 5, 5, 35),
(6, 'SANT JORDI', '2024-04-20 09:57:23', '2024-04-24 20:21:16', 'La fiesta más esperada por los barceloneses y barcelonesas te dejará con la boca abierta. La ciudad se inunda de puestos con libros y rosas para celebrar por todo lo alto la cultura y el amor.', 1, 'https://www.rosessantjordi.com/es/sant-jordi-2024/', 'santjordi.jpg', 5, 6, 6, 5),
(7, 'SALON DEL COMIC', '2024-04-20 09:57:32', '2024-05-05 20:27:39', 'Una cita ideal para toda la familia: exposiciones, talleres, encuentros con autores, mesas redondas, juegos de rol… ¡diversión asegurada!', 1, 'https://www.comic-barcelona.com/es/inicio.cfm', 'salon comic.jpg', 2, 3, 7, 20),
(8, 'MOTO GP', '2024-04-20 09:57:36', '2024-05-26 20:35:01', 'Vibra con los grandes de las dos ruedas y disfruta de actividades complementarias en el Circuit de Barcelona-Catalunya con el Gran premio Monster Energy de Catalunya Moto GP, la competición para los y las amantes del motociclismo.', 1, 'https://www.circuitcat.com/es', 'moto gp.jpg', 8, 7, 8, 25),
(9, 'PRIMAVERA SOUND', '2024-04-20 09:57:40', '2024-06-02 20:42:56', 'No te pierdas uno de los festivales de música alternativa más importante de Europa. El Barcelona Primavera Sound impacta cada año con su extenso cartel de artistas de prestigio internacional ¡Imprescindible si lo tuyo es la música independiente!', 1, 'www.primaverasound.com', 'primavera sound.jpg', 4, 8, 9, 25),
(10, 'SONAR', '2024-04-20 09:57:45', '2024-06-15 20:48:33', 'Forma parte de un hito único asistiendo al festival más innovador del mundo. Desde 1994 el Sónar trasciende y ofrece lo último en lenguaje multimedia y arte y música electrónica, lo que lo convierte en un referente internacional.', 1, 'https://sonar.es/', 'sonar.jpg', 4, 3, 10, 25),
(12, 'hola', '2024-04-20 09:57:48', '2024-04-20 06:00:00', 'asdad', 1, 'patata', 'a0cb3885c44b8305ac89ba7ce98e8cd978bf3ebba6a151a00dbf2d528e98bf3b.jpg', 1, 1, 1, 5),
(13, 'hola', '2024-04-20 09:57:52', '2024-04-19 06:00:00', 'q', 1, 'q', 'images.png', 1, 1, 1, 5),
(16, 'FSDASDS', '2024-04-12 16:26:00', '2024-05-09 16:26:00', 'FSD', 1, 'DFS', 'gato1.jpg', 1, 1, 11, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event_users`
--

CREATE TABLE `event_users` (
  `id` int(11) NOT NULL,
  `id_event` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `event_users`
--

INSERT INTO `event_users` (`id`, `id_event`, `id_user`, `date`) VALUES
(26, 2, 13, '2024-04-14 09:39:17'),
(27, 2, 12, '2024-04-21 18:26:06'),
(28, 5, 12, '2024-04-21 18:26:07'),
(29, 3, 12, '2024-04-21 18:44:58');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `location`
--

CREATE TABLE `location` (
  `id` int(11) NOT NULL,
  `name_location` varchar(200) DEFAULT NULL,
  `additional_info` varchar(1000) DEFAULT NULL,
  `longitud` float DEFAULT NULL,
  `latitud` float DEFAULT NULL,
  `ubicationlink` varchar(535) NOT NULL,
  `redirectionlink` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `location`
--

INSERT INTO `location` (`id`, `name_location`, `additional_info`, `longitud`, `latitud`, `ubicationlink`, `redirectionlink`) VALUES
(1, 'BARCELONA- ARIBAU IMMERSIVE SPACE', 'C/ d\'Aribau, 5, 08011, Barcelona, Spain', 2.15977, 41.3902, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2993.455795509259!2d2.1598244764078145!3d41.38590689608646!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a311332b5b81%3A0x444cb7a54cff8454!2sCarrer%20 d\'Aribau%2C%205%2C%20 L\'Eixample%2C%2008011%20Barcelona!5e0!3m2!1ses!2ses!4v1712399448089!5m2!1ses!2ses', 'https://maps.app.goo.gl/TmQcMCrhUXNpL9GX9'),
(2, 'BARCELONA- COSMOCAIXA', 'C.ISAAC NEWTON, 26, BARCELONA', 2.12855, 41.4132, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2992.186202308763!2d2.128396040688813!3d41.413476494495626!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4981a300a4c93%3A0x1bd9ec11746a88ac!2sC.%20de%20Issac%20Newton%2C%2026%2C%20Distrito%20de%20Sarri%C3%A0-Sant%20Gervasi%2C%2008022%20Barcelona!5e0!3m2!1ses!2ses!4v1712405033669!5m2!1ses!2ses', 'https://maps.app.goo.gl/NjobbSn56HoUxWRH7'),
(3, 'BARCELONA- FIRA DE BARCELONA', 'Av. de la Reina Maria Cristina, s/n, Sants-Montjuïc, 08004 Barcelona', 2.15151, 41.3719, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d95808.22066695667!2d2.006318143359378!3d41.37393490000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a27ba652ef8f%3A0x7d0d287990636de7!2sFira%20de%20Barcelona!5e0!3m2!1ses!2ses!4v1712405066411!5m2!1ses!2ses', 'https://maps.app.goo.gl/2ZgJm3H1Lsananmr6'),
(4, 'BARCELONA- PLAZA GLORIES CATALANES', 'Pl. De Les Glòries Catalanes, 38, Sant Martí, 08018 Barcelona', 2.1882, 41.403, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5985.244641386369!2d2.1836196246813473!3d41.40400774903351!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a3210230d687%3A0x47dd0b77cd51d072!2sPlaza%20de%20las%20Glorias%20Catalanas!5e0!3m2!1ses!2ses!4v1712405097205!5m2!1ses!2ses', 'https://maps.app.goo.gl/k6TvPAYvFCLghpieA'),
(5, 'BARCELONA- REAL CLUB DE TENIS', 'C/ De Bosch I Gimpera, 5, Les Corts, 08034 Barcelona', 2.11832, 41.3937, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2993.1480209251945!2d2.1150058406879575!3d41.39259169579147!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a49844fcaadfef%3A0x17cd546318ca50c!2sC%2F%20de%20Bosch%20i%20Gimpera%2C%205%2C%20Les%20Corts%2C%2008034%20Barcelona!5e0!3m2!1ses!2ses!4v1712405271865!5m2!1ses!2ses', 'https://maps.app.goo.gl/GbSRN8eRErgYnJMUA'),
(6, 'BARCELONA- PASEO DE GRACIA', 'PASEO DE GRACIA, 45', 2.16391, 41.393, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2993.1868126741056!2d2.162330740687864!3d41.391749195843815!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a2ed4c380905%3A0x39c7bc81d76d9222!2sPasseig%20de%20Gr%C3%A0cia%2C%2045%2C%20Eixample%2C%2008007%20Barcelona!5e0!3m2!1ses!2ses!4v1712405305871!5m2!1ses!2ses', 'https://maps.app.goo.gl/3RhnTiHeqzhqAeCw6'),
(7, 'MONTMELÓ- CIRCUITO', 'Ctra. Granollers, km. 2, 08160 Parets del Vallès, Barcelona', 2.256, 41.5722, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2984.9932317704693!2d2.255233240695353!3d41.569391084805424!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4b8ecc053a0f1%3A0x150ed08bef7e2b48!2sCircuito%20de%20Barcelona%20-%20Catalunya!5e0!3m2!1ses!2ses!4v1712405331977!5m2!1ses!2ses', 'https://maps.app.goo.gl/BMCnfxVxmn73ndvu6'),
(8, 'PARC DEL FORUM', 'Carrer de la Pau, 12, 08930 Sant Adrià de Besòs, Barcelona', 2.22276, 41.4091, 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2992.208238763724!2d2.2260162406887924!3d41.412998094525314!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a4a932d34b83%3A0xabb2cbac05231a!2sCarrer%20de%20la%20Pau%2C%2012%2C%2008930%20Sant%20Adri%C3%A0%20de%20Bes%C3%B2s%2C%20Barcelona!5e0!3m2!1ses!2ses!4v1712405357680!5m2!1ses!2ses', 'https://maps.app.goo.gl/FDYjG6ZrfA25jEfM9');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL,
  `id_user` int(255) NOT NULL,
  `rol` int(255) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mensaje` varchar(255) NOT NULL,
  `fecha` varchar(29) NOT NULL,
  `hora` varchar(20) NOT NULL,
  `leido` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`id`, `id_user`, `rol`, `nombre`, `numero`, `email`, `mensaje`, `fecha`, `hora`, `leido`) VALUES
(1, 0, 0, 'usertest', '00000000', '123@123.es', 'FSDDFS', '2024-04-21', '06:04:26', 0),
(2, 13, 3, 'ana', '000000000', 'anacascanterodriguez@gmail.com', 'aaa', '2024-04-27', '10:04:40', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `id` int(11) NOT NULL,
  `total_price` varchar(100) NOT NULL,
  `created` varchar(10) NOT NULL,
  `mes` int(2) NOT NULL,
  `dia` int(2) NOT NULL,
  `year` int(4) NOT NULL,
  `hora` varchar(10) NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1',
  `metodo` varchar(255) NOT NULL,
  `idprecio` varchar(255) NOT NULL,
  `id_cliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`id`, `total_price`, `created`, `mes`, `dia`, `year`, `hora`, `status`, `metodo`, `idprecio`, `id_cliente`) VALUES
(1, '20', '2024-04-21', 4, 21, 2024, '18:24:37', '1', '', '820', 0),
(2, '20', '2024-04-21', 4, 21, 2024, '18:24:56', '1', '', '820', 0),
(3, '20', '2024-04-21', 4, 21, 2024, '18:25:14', '1', '', '820', 0),
(4, '30', '2024-04-21', 4, 21, 2024, '18:47:07', '1', '', '430', 0),
(5, '45', '2024-04-21', 4, 21, 2024, '18:47:46', '1', '', '545', 0),
(6, '20', '2024-04-21', 4, 21, 2024, '18:54:39', '1', '', '820', 0),
(7, '20', '2024-04-24', 4, 24, 2024, '16:54:30', '1', '', '820', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_articulos`
--

CREATE TABLE `orden_articulos` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `orden_articulos`
--

INSERT INTO `orden_articulos` (`id`, `order_id`, `product_id`, `quantity`) VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 1, 1),
(4, 4, 2, 2),
(5, 5, 2, 3),
(6, 6, 1, 1),
(7, 7, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organizer`
--

CREATE TABLE `organizer` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `name_org` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `organizer`
--

INSERT INTO `organizer` (`id`, `id_user`, `name_org`, `email`, `phone`, `company`, `url`) VALUES
(1, 1, 'TOMORROWLAND', 'info@tomorrowland.com', NULL, 'TOMORROWLAND', 'www.tomorrowland.com'),
(2, 2, 'COSMOCAIXA', 'icosmocaixa@magmacultura.com', '932 126 050', 'COSMOCAIXA', 'https://cosmocaixa.org/'),
(3, 3, 'BBF', 'info@barcelonabeerfestival.com', NULL, 'BEER EVENTS S.L.', 'https://barcelonabeerfestival.com/'),
(4, 4, 'OFFFEST', 'INFO@OFFF.BARCELONA', NULL, 'OFFF', 'https://www.offf.barcelona/'),
(5, 5, 'BCN OPEN BANC SABADELL ', 'info@barcelonaopenbancsabadell.com', '932 03 78 52', 'OPEN BANC SABADELL', 'https://www.barcelonaopenbancsabadell.com/'),
(6, 6, 'BARCELONA- AJUNTAMENT', 'bigestio@bcn.cat', '934027000', 'AJUNTAMENT BARCELONA', 'https://ajuntament.barcelona.cat/'),
(7, 7, 'FICOMIC', ' info@ficomic.com', NULL, 'FICOMIC', 'https://www.ficomic.com/es/inicio.cfm'),
(8, 8, 'CIRCUIT DE CATALUNYA', 'info@circuitcat.com', '(34) 93 571 97 00', 'CIRCUIT DE CATALUNYA', 'https://www.circuitcat.com/es/como-llegar-a-montmelo-2/'),
(9, 9, 'PRIMAVERA SOUND', 'info@primaverasound.com', '+34 93 301 00 90', 'PRIMAVERA SOUND S.L.', 'www.primaverasound.com'),
(10, 10, 'ADVANCED MUSIC', 'info@sonar.es', ' 933 20 81 63 ', 'ADVANCED MUSIC.S.L', 'http://www.sonar.es'),
(11, 11, 'anatest', 'ana@ana.com', '000 000 000', 'ANAORG', 'www.ana-org.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pref_users`
--

CREATE TABLE `pref_users` (
  `id_preferencia` int(255) NOT NULL,
  `id_usuario` int(255) NOT NULL,
  `id_categoria` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pref_users`
--

INSERT INTO `pref_users` (`id_preferencia`, `id_usuario`, `id_categoria`) VALUES
(9, 1, 1),
(10, 1, 2),
(11, 1, 1),
(12, 1, 2),
(14, 1, 1),
(15, 1, 2),
(18, 1, 1),
(19, 1, 2),
(21, 1, 1),
(22, 1, 2),
(23, 1, 1),
(24, 1, 2),
(26, 1, 1),
(27, 1, 2),
(28, 13, 1),
(29, 13, 2),
(30, 13, 1),
(31, 13, 2),
(32, 13, 5),
(34, 11, 6),
(35, 11, 6),
(36, 11, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name_rol` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `role`
--

INSERT INTO `role` (`id`, `name_rol`) VALUES
(1, 'user'),
(2, 'organizer'),
(3, 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tag`
--

CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `name_tag` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `email` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role_id`, `is_active`, `created_at`, `email`) VALUES
(1, 'TOMORROWLAND', '123', 2, 1, '2024-04-13 15:48:51', 'info@tomorrowland.com'),
(2, 'COSMOCAIXA', '123', 2, 1, '2024-04-13 15:48:51', 'icosmocaixa@magmacultura.com'),
(3, 'BBF', '123', 2, 1, '2024-04-13 15:49:52', 'info@barcelonabeerfestival.com'),
(4, 'OFFFEST', '123', 2, 1, '2024-04-13 15:49:52', 'INFO@OFFF.BARCELONA'),
(5, 'BCN OPEN BANC SABADELL', '123', 2, 1, '2024-04-13 15:51:12', 'info@barcelonaopenbancsabadell.com'),
(6, 'BARCELONA- AJUNTAMENT', '123', 2, 1, '2024-04-13 15:51:12', 'bigestio@bcn.cat'),
(7, 'FICOMIC', '123', 2, 1, '2024-04-13 15:53:18', 'info@ficomic.com'),
(8, 'CIRCUIT DE CATALUNYA', '123', 2, 1, '2024-04-13 15:53:23', 'info@circuitcat.com'),
(9, 'PRIMAVERA SOUND', '123', 2, 1, '2024-04-13 15:54:28', 'info@primaverasound.com'),
(10, 'ADVANCED MUSIC', '123', 2, 1, '2024-04-13 15:55:07', 'info@sonar.es'),
(11, 'anatest', '123', 2, 1, '2024-03-31 09:40:25', 'ana@ana.com'),
(12, 'usertest', '123', 1, 1, '2024-03-31 09:40:21', '123@123.es'),
(13, 'ana', '123', 3, 1, '2024-03-17 09:52:31', 'anacascanterodriguez@gmail.com'),
(14, 'yassir', '1234', 3, 1, '2024-03-18 15:21:22', 'ysrmrn1998@gmail.com'),
(15, 'bernat', '1234', 3, 1, '2024-03-18 15:21:22', 'bernesimobosch@gmail.com'),
(16, 'artur1', '1234', 3, 1, '2024-03-31 09:40:15', 'ludmilla.tartu@gmail.com'),
(17, 'artur2', '1234', 3, 1, '2024-03-18 15:23:01', 'arthur.trubitsin@gmail.com'),
(18, 'artur3', '1234', 1, 1, '2024-03-18 15:24:41', 'gynvael@pm.me');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `location_id` (`location_id`),
  ADD KEY `organizer_id` (`organizer_id`);

--
-- Indices de la tabla `event_users`
--
ALTER TABLE `event_users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_users_ibfk_1` (`id_event`),
  ADD KEY `event_users_ibfk_2` (`id_user`);

--
-- Indices de la tabla `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orden_articulos`
--
ALTER TABLE `orden_articulos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indices de la tabla `organizer`
--
ALTER TABLE `organizer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `organizer_ibfk_1` (`id_user`);

--
-- Indices de la tabla `pref_users`
--
ALTER TABLE `pref_users`
  ADD PRIMARY KEY (`id_preferencia`),
  ADD KEY `pref_users_ibfk_1` (`id_usuario`),
  ADD KEY `pref_users_ibfk_2` (`id_categoria`);

--
-- Indices de la tabla `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `evento`
--
ALTER TABLE `evento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `event_users`
--
ALTER TABLE `event_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `location`
--
ALTER TABLE `location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `orden`
--
ALTER TABLE `orden`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `orden_articulos`
--
ALTER TABLE `orden_articulos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `organizer`
--
ALTER TABLE `organizer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `pref_users`
--
ALTER TABLE `pref_users`
  MODIFY `id_preferencia` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `evento_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  ADD CONSTRAINT `evento_ibfk_3` FOREIGN KEY (`organizer_id`) REFERENCES `organizer` (`id`);

--
-- Filtros para la tabla `event_users`
--
ALTER TABLE `event_users`
  ADD CONSTRAINT `event_users_ibfk_1` FOREIGN KEY (`id_event`) REFERENCES `evento` (`id`),
  ADD CONSTRAINT `event_users_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `organizer`
--
ALTER TABLE `organizer`
  ADD CONSTRAINT `organizer_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `pref_users`
--
ALTER TABLE `pref_users`
  ADD CONSTRAINT `pref_users_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `pref_users_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `category` (`id`);

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
