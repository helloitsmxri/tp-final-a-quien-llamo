-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-10-2026 a las 15:30:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `a_quien_llamo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id_admin` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `clave` varchar(100) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caracteristica`
--

CREATE TABLE `caracteristica` (
  `id_caracteristica` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `tipo` enum('NEGATIVA','POSITIVA') NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `valor_adjetivo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `caracteristica`
--

INSERT INTO `caracteristica` (`id_caracteristica`, `created_at`, `updated_at`, `tipo`, `uuid`, `valor_adjetivo`) VALUES
(1, '2026-09-27 01:07:46', NULL, 'POSITIVA', '1384d19a-ba06-11f1-8c83-00e04c880385', 'Responsable'),
(2, '2026-09-27 01:07:46', NULL, 'POSITIVA', '1384e385-ba06-11f1-8c83-00e04c880385', 'Puntual'),
(3, '2026-09-27 01:07:46', NULL, 'POSITIVA', '1384e428-ba06-11f1-8c83-00e04c880385', 'Respetuoso'),
(4, '2026-09-27 01:07:46', NULL, 'POSITIVA', '1384e45b-ba06-11f1-8c83-00e04c880385', 'Profesional'),
(5, '2026-09-27 01:07:46', NULL, 'POSITIVA', '1384e487-ba06-11f1-8c83-00e04c880385', 'Confiable'),
(6, '2026-09-27 01:07:46', NULL, 'NEGATIVA', '1385ba33-ba06-11f1-8c83-00e04c880385', 'Impuntual'),
(7, '2026-09-27 01:07:46', NULL, 'NEGATIVA', '1385cced-ba06-11f1-8c83-00e04c880385', 'Irresponsable'),
(8, '2026-09-27 01:07:46', NULL, 'NEGATIVA', '1385cd84-ba06-11f1-8c83-00e04c880385', 'Descortés'),
(9, '2026-09-27 01:07:46', NULL, 'NEGATIVA', '1385cdcb-ba06-11f1-8c83-00e04c880385', 'Mala comunicación'),
(10, '2026-09-27 01:07:46', NULL, 'NEGATIVA', '1385cdfc-ba06-11f1-8c83-00e04c880385', 'Poco profesional');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `certificacion`
--

CREATE TABLE `certificacion` (
  `id_certificacion` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `ente_otorgador` varchar(255) NOT NULL,
  `estado_verificacion` enum('Aprobado','Pendiente','Rechazado') DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `imagen_certificado` mediumblob NOT NULL,
  `notas_admin` text DEFAULT NULL,
  `num_matricula` varchar(100) NOT NULL,
  `tipo_imagen` varchar(50) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_admin_revisor` int(11) DEFAULT NULL,
  `id_tecnico` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chat`
--

CREATE TABLE `chat` (
  `id_chat` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `fecha_chat` datetime(6) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credencial`
--

CREATE TABLE `credencial` (
  `credencial_id` int(11) NOT NULL,
  `clave` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `refresh_token` varchar(2048) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credencial`
--

INSERT INTO `credencial` (`credencial_id`, `clave`, `enabled`, `refresh_token`, `username`, `id_usuario`) VALUES
(1, '$2a$10$U6rHOEvqSsXp3pn0YghhCOXLefJYkK2KzEVAuVrkfdIR4T01z5LHa', 1, NULL, 'geronimosilvestre@hotmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credencial_rol`
--

CREATE TABLE `credencial_rol` (
  `credencial_id` int(11) NOT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credencial_rol`
--

INSERT INTO `credencial_rol` (`credencial_id`, `id_role`) VALUES
(1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `denuncia`
--

CREATE TABLE `denuncia` (
  `id_denuncia` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `estado_denuncia` enum('Aprobada','En_proceso','Pendiente','Rechazada') NOT NULL,
  `fecha_denuncia` datetime(6) NOT NULL,
  `foto` mediumblob DEFAULT NULL,
  `motivo_denuncia` text NOT NULL,
  `nota_del_admin` text DEFAULT NULL,
  `tipo_foto` varchar(50) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `administrador_encargado` int(11) DEFAULT NULL,
  `id_denunciado` int(11) NOT NULL,
  `id_denunciante` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad`
--

CREATE TABLE `especialidad` (
  `id_especialidad` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `nombre_especialidad` varchar(50) DEFAULT NULL,
  `tipo_validacion` enum('Certificado','Ninguna','Portfolio') DEFAULT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidad`
--

INSERT INTO `especialidad` (`id_especialidad`, `created_at`, `updated_at`, `nombre_especialidad`, `tipo_validacion`, `uuid`) VALUES
(1, '2026-09-27 01:07:12', NULL, 'Apoyo Escolar', 'Ninguna', '1387ab6d-ba06-11f1-8c83-00e04c880385'),
(2, '2026-09-27 01:07:12', NULL, 'Coaching y Desarrollo Personal', 'Certificado', '1387be1b-ba06-11f1-8c83-00e04c880385'),
(3, '2026-09-27 01:07:12', NULL, 'Idiomas', 'Certificado', '1387bed0-ba06-11f1-8c83-00e04c880385'),
(4, '2026-09-27 01:07:12', NULL, 'Capacitación Profesional', 'Certificado', '1387bf16-ba06-11f1-8c83-00e04c880385'),
(5, '2026-09-27 01:07:12', NULL, 'Baristería', 'Certificado', '1387bf4e-ba06-11f1-8c83-00e04c880385'),
(6, '2026-09-27 01:07:12', NULL, 'Gastronomía y Cocina', 'Certificado', '1387bf87-ba06-11f1-8c83-00e04c880385'),
(7, '2026-09-27 01:07:12', NULL, 'Catering', 'Portfolio', '1387bfc3-ba06-11f1-8c83-00e04c880385'),
(8, '2026-09-27 01:07:12', NULL, 'Decoración de Eventos', 'Portfolio', '1387bff6-ba06-11f1-8c83-00e04c880385'),
(9, '2026-09-27 01:07:12', NULL, 'Floristería', 'Ninguna', '1387c02e-ba06-11f1-8c83-00e04c880385'),
(10, '2026-09-27 01:07:12', NULL, 'Fotografía', 'Portfolio', '1387c061-ba06-11f1-8c83-00e04c880385'),
(11, '2026-09-27 01:07:12', NULL, 'Video', 'Portfolio', '1387c092-ba06-11f1-8c83-00e04c880385'),
(12, '2026-09-27 01:07:12', NULL, 'Maquillaje', 'Portfolio', '1387c0c4-ba06-11f1-8c83-00e04c880385'),
(13, '2026-09-27 01:07:12', NULL, 'Música y Entretenimiento', 'Ninguna', '1387c0f8-ba06-11f1-8c83-00e04c880385'),
(14, '2026-09-27 01:07:12', NULL, 'Pastelería', 'Portfolio', '1387c142-ba06-11f1-8c83-00e04c880385'),
(15, '2026-09-27 01:07:12', NULL, 'Servicio de Mesa', 'Ninguna', '1387c17f-ba06-11f1-8c83-00e04c880385'),
(16, '2026-09-27 01:07:12', NULL, 'Souvenirs y Regalería', 'Ninguna', '1387c1b2-ba06-11f1-8c83-00e04c880385'),
(17, '2026-09-27 01:07:12', NULL, 'Panadería Artesanal', 'Certificado', '1387c1e5-ba06-11f1-8c83-00e04c880385'),
(18, '2026-09-27 01:07:12', NULL, 'Viandas y Viandas Saludables', 'Ninguna', '1387c221-ba06-11f1-8c83-00e04c880385'),
(19, '2026-09-27 01:07:12', NULL, 'Climatización y Aire Acondicionado', 'Certificado', '1387c254-ba06-11f1-8c83-00e04c880385'),
(20, '2026-09-27 01:07:12', NULL, 'Albañilería', 'Ninguna', '1387c2d5-ba06-11f1-8c83-00e04c880385'),
(21, '2026-09-27 01:07:12', NULL, 'Carpintería', 'Certificado', '1387c307-ba06-11f1-8c83-00e04c880385'),
(22, '2026-09-27 01:07:12', NULL, 'Cerrajería', 'Certificado', '1387c339-ba06-11f1-8c83-00e04c880385'),
(23, '2026-09-27 01:07:12', NULL, 'Electricidad', 'Certificado', '1387c38d-ba06-11f1-8c83-00e04c880385'),
(24, '2026-09-27 01:07:12', NULL, 'Fumigación y Control de Plagas', 'Certificado', '1387c3bd-ba06-11f1-8c83-00e04c880385'),
(25, '2026-09-27 01:07:12', NULL, 'Instalaciones de Gas', 'Certificado', '1387c3ef-ba06-11f1-8c83-00e04c880385'),
(26, '2026-09-27 01:07:12', NULL, 'Impermeabilización', 'Certificado', '1387c41f-ba06-11f1-8c83-00e04c880385'),
(27, '2026-09-27 01:07:12', NULL, 'Interiorismo y Decoración', 'Portfolio', '1387c450-ba06-11f1-8c83-00e04c880385'),
(28, '2026-09-27 01:07:12', NULL, 'Jardinería y Paisajismo', 'Portfolio', '1387c47f-ba06-11f1-8c83-00e04c880385'),
(29, '2026-09-27 01:07:12', NULL, 'Servicios de Limpieza', 'Ninguna', '1387c4b4-ba06-11f1-8c83-00e04c880385'),
(30, '2026-09-27 01:07:12', NULL, 'Persianas y Cortinas', 'Ninguna', '1387c4e8-ba06-11f1-8c83-00e04c880385'),
(31, '2026-09-27 01:07:12', NULL, 'Instalación de Pisos', 'Ninguna', '1387c517-ba06-11f1-8c83-00e04c880385'),
(32, '2026-09-27 01:07:12', NULL, 'Pintura', 'Ninguna', '1387c548-ba06-11f1-8c83-00e04c880385'),
(33, '2026-09-27 01:07:12', NULL, 'Plomería', 'Certificado', '1387c57a-ba06-11f1-8c83-00e04c880385'),
(34, '2026-09-27 01:07:12', NULL, 'Mantenimiento de Piscinas', 'Ninguna', '1387c5ab-ba06-11f1-8c83-00e04c880385'),
(35, '2026-09-27 01:07:12', NULL, 'Sistemas de Seguridad', 'Certificado', '1387c5dc-ba06-11f1-8c83-00e04c880385'),
(36, '2026-09-27 01:07:12', NULL, 'Techos', 'Ninguna', '1387c60b-ba06-11f1-8c83-00e04c880385'),
(37, '2026-09-27 01:07:12', NULL, 'Toldos y Cerramientos', 'Ninguna', '1387c63f-ba06-11f1-8c83-00e04c880385'),
(38, '2026-09-27 01:07:12', NULL, 'Vidriería', 'Ninguna', '1387c66e-ba06-11f1-8c83-00e04c880385'),
(39, '2026-09-27 01:07:12', NULL, 'Zinguería', 'Ninguna', '1387c6a1-ba06-11f1-8c83-00e04c880385'),
(40, '2026-09-27 01:07:12', NULL, 'Adiestramiento Canino', 'Certificado', '1387c6d4-ba06-11f1-8c83-00e04c880385'),
(41, '2026-09-27 01:07:12', NULL, 'Paseo y Cuidado de Mascotas', 'Ninguna', '1387c706-ba06-11f1-8c83-00e04c880385'),
(42, '2026-09-27 01:07:12', NULL, 'Estética y Peluquería Canina', 'Ninguna', '1387c738-ba06-11f1-8c83-00e04c880385'),
(43, '2026-09-27 01:07:12', NULL, 'Depilación', 'Certificado', '1387c769-ba06-11f1-8c83-00e04c880385'),
(44, '2026-09-27 01:07:12', NULL, 'Entrenamiento Personal', 'Certificado', '1387c799-ba06-11f1-8c83-00e04c880385'),
(45, '2026-09-27 01:07:12', NULL, 'Estética y Cosmetología', 'Certificado', '1387c7ca-ba06-11f1-8c83-00e04c880385'),
(46, '2026-09-27 01:07:12', NULL, 'Manicura y Pedicuría', 'Certificado', '1387c7fa-ba06-11f1-8c83-00e04c880385'),
(47, '2026-09-27 01:07:12', NULL, 'Masoterapia y Masajes', 'Certificado', '1387c828-ba06-11f1-8c83-00e04c880385'),
(48, '2026-09-27 01:07:12', NULL, 'Peluquería', 'Certificado', '1387c859-ba06-11f1-8c83-00e04c880385'),
(49, '2026-09-27 01:07:12', NULL, 'Fletes', 'Ninguna', '1387c889-ba06-11f1-8c83-00e04c880385'),
(50, '2026-09-27 01:07:12', NULL, 'Mudanzas', 'Ninguna', '1387c8b9-ba06-11f1-8c83-00e04c880385'),
(51, '2026-09-27 01:07:12', NULL, 'Logística y Reparto', 'Ninguna', '1387c8ea-ba06-11f1-8c83-00e04c880385'),
(52, '2026-09-27 01:07:12', NULL, 'Programación y Desarrollo', 'Portfolio', '1387c91c-ba06-11f1-8c83-00e04c880385'),
(53, '2026-09-27 01:07:12', NULL, 'Diseño Web y UX/UI', 'Portfolio', '1387c950-ba06-11f1-8c83-00e04c880385'),
(54, '2026-09-27 01:07:12', NULL, 'Soporte Técnico', 'Certificado', '1387c980-ba06-11f1-8c83-00e04c880385'),
(55, '2026-09-27 01:07:12', NULL, 'Análisis de Sistemas', 'Certificado', '1387c9b0-ba06-11f1-8c83-00e04c880385'),
(56, '2026-09-27 01:07:12', NULL, 'Arquitectura', 'Certificado', '1387c9e1-ba06-11f1-8c83-00e04c880385'),
(57, '2026-09-27 01:07:12', NULL, 'Construcción en Seco', 'Ninguna', '1387ca11-ba06-11f1-8c83-00e04c880385'),
(58, '2026-09-27 01:07:12', NULL, 'Herrería y Metalurgia', 'Certificado', '1387ca42-ba06-11f1-8c83-00e04c880385'),
(59, '2026-09-27 01:07:12', NULL, 'Diseño Gráfico', 'Portfolio', '1387ca73-ba06-11f1-8c83-00e04c880385'),
(60, '2026-09-27 01:07:12', NULL, 'Ilustración y Arte', 'Portfolio', '1387cb7c-ba06-11f1-8c83-00e04c880385'),
(61, '2026-09-27 01:07:12', NULL, 'Moda e Indumentaria', 'Portfolio', '1387cba4-ba06-11f1-8c83-00e04c880385');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad_tecnico`
--

CREATE TABLE `especialidad_tecnico` (
  `id_tecnico` int(11) NOT NULL,
  `id_especialidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habilidad`
--

CREATE TABLE `habilidad` (
  `id_habilidad` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `nombre_habilidad` varchar(50) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habilidad`
--

INSERT INTO `habilidad` (`id_habilidad`, `created_at`, `updated_at`, `nombre_habilidad`, `uuid`) VALUES
(1, '2026-09-27 01:06:20', NULL, 'Muros', '1389eef2-ba06-11f1-8c83-00e04c880385'),
(2, '2026-09-27 01:06:20', NULL, 'Reparación de grietas', '138a052f-ba06-11f1-8c83-00e04c880385'),
(3, '2026-09-27 01:06:20', NULL, 'Revoques', '138a05db-ba06-11f1-8c83-00e04c880385'),
(4, '2026-09-27 01:06:20', NULL, 'Contrapiso', '138a0617-ba06-11f1-8c83-00e04c880385'),
(5, '2026-09-27 01:06:20', NULL, 'Carpeta niveladora', '138a064c-ba06-11f1-8c83-00e04c880385'),
(6, '2026-09-27 01:06:20', NULL, 'Cerámicos', '138a067d-ba06-11f1-8c83-00e04c880385'),
(7, '2026-09-27 01:06:20', NULL, 'Porcelanato', '138a06b0-ba06-11f1-8c83-00e04c880385'),
(8, '2026-09-27 01:06:20', NULL, 'Piedra natural', '138a06db-ba06-11f1-8c83-00e04c880385'),
(9, '2026-09-27 01:06:20', NULL, 'Asadores', '138a070f-ba06-11f1-8c83-00e04c880385'),
(10, '2026-09-27 01:06:20', NULL, 'Tabiques', '138a0756-ba06-11f1-8c83-00e04c880385'),
(11, '2026-09-27 01:06:20', NULL, 'Zócalos', '138a078f-ba06-11f1-8c83-00e04c880385'),
(12, '2026-09-27 01:06:20', NULL, 'Pozos', '138a07bb-ba06-11f1-8c83-00e04c880385'),
(13, '2026-09-27 01:06:20', NULL, 'Frentes', '138a07e9-ba06-11f1-8c83-00e04c880385'),
(14, '2026-09-27 01:06:20', NULL, 'Microcemento', '138a0813-ba06-11f1-8c83-00e04c880385'),
(15, '2026-09-27 01:06:20', NULL, 'Redes internas de gas', '138a083f-ba06-11f1-8c83-00e04c880385'),
(16, '2026-09-27 01:06:20', NULL, 'Detección de fugas', '138a086d-ba06-11f1-8c83-00e04c880385'),
(17, '2026-09-27 01:06:20', NULL, 'Gas natural', '138a089c-ba06-11f1-8c83-00e04c880385'),
(18, '2026-09-27 01:06:20', NULL, 'Gas envasado', '138a08cc-ba06-11f1-8c83-00e04c880385'),
(19, '2026-09-27 01:06:20', NULL, 'Instalación de Calderas', '138a08f7-ba06-11f1-8c83-00e04c880385'),
(20, '2026-09-27 01:06:20', NULL, 'Estufas', '138a0920-ba06-11f1-8c83-00e04c880385'),
(21, '2026-09-27 01:06:20', NULL, 'Habilitaciones de gas', '138a094d-ba06-11f1-8c83-00e04c880385'),
(22, '2026-09-27 01:06:20', NULL, 'Conversión de combustible', '138a0979-ba06-11f1-8c83-00e04c880385'),
(23, '2026-09-27 01:06:20', NULL, 'Pérdidas de agua', '138a09a4-ba06-11f1-8c83-00e04c880385'),
(24, '2026-09-27 01:06:20', NULL, 'Destapaciones', '138a09d0-ba06-11f1-8c83-00e04c880385'),
(25, '2026-09-27 01:06:20', NULL, 'Sanitarios', '138a0a00-ba06-11f1-8c83-00e04c880385'),
(26, '2026-09-27 01:06:20', NULL, 'Presión de agua', '138a0a2a-ba06-11f1-8c83-00e04c880385'),
(27, '2026-09-27 01:06:20', NULL, 'Cloacas', '138a0a56-ba06-11f1-8c83-00e04c880385'),
(28, '2026-09-27 01:06:20', NULL, 'Conexión domiciliaria', '138a0a80-ba06-11f1-8c83-00e04c880385'),
(29, '2026-09-27 01:06:20', NULL, 'Griferías', '138a0aad-ba06-11f1-8c83-00e04c880385'),
(30, '2026-09-27 01:06:20', NULL, 'Colectores solares', '138a0ad6-ba06-11f1-8c83-00e04c880385'),
(31, '2026-09-27 01:06:20', NULL, 'Chapa', '138a0b00-ba06-11f1-8c83-00e04c880385'),
(32, '2026-09-27 01:06:20', NULL, 'Tejas', '138a0b2a-ba06-11f1-8c83-00e04c880385'),
(33, '2026-09-27 01:06:20', NULL, 'Membrana en rollo', '138a0b58-ba06-11f1-8c83-00e04c880385'),
(34, '2026-09-27 01:06:20', NULL, 'Filtraciones', '138a0b81-ba06-11f1-8c83-00e04c880385'),
(35, '2026-09-27 01:06:20', NULL, 'Techos verdes', '138a0bad-ba06-11f1-8c83-00e04c880385'),
(36, '2026-09-27 01:06:20', NULL, 'Policarbonato', '138a0bd7-ba06-11f1-8c83-00e04c880385'),
(37, '2026-09-27 01:06:20', NULL, 'Zinc', '138a0c03-ba06-11f1-8c83-00e04c880385'),
(38, '2026-09-27 01:06:20', NULL, 'Cumbrera', '138a0c2f-ba06-11f1-8c83-00e04c880385'),
(39, '2026-09-27 01:06:20', NULL, 'Aislación térmica', '138a0c5c-ba06-11f1-8c83-00e04c880385'),
(40, '2026-09-27 01:06:20', NULL, 'Drenajes', '138a0c85-ba06-11f1-8c83-00e04c880385'),
(41, '2026-09-27 01:06:20', NULL, 'Tableros eléctricos', '138a0cb1-ba06-11f1-8c83-00e04c880385'),
(42, '2026-09-27 01:06:20', NULL, 'Baja tensión', '138a0cdd-ba06-11f1-8c83-00e04c880385'),
(43, '2026-09-27 01:06:20', NULL, 'Media tensión', '138a0d05-ba06-11f1-8c83-00e04c880385'),
(44, '2026-09-27 01:06:20', NULL, 'LED', '138a0d32-ba06-11f1-8c83-00e04c880385'),
(45, '2026-09-27 01:06:20', NULL, 'Automatización', '138a0d5e-ba06-11f1-8c83-00e04c880385'),
(46, '2026-09-27 01:06:20', NULL, 'Domótica', '138a0d8a-ba06-11f1-8c83-00e04c880385'),
(47, '2026-09-27 01:06:20', NULL, 'Cableado estructurado', '138a0e09-ba06-11f1-8c83-00e04c880385'),
(48, '2026-09-27 01:06:20', NULL, 'Puesta a tierra', '138a0e30-ba06-11f1-8c83-00e04c880385'),
(49, '2026-09-27 01:06:20', NULL, 'Solar fotovoltaica', '138a0e56-ba06-11f1-8c83-00e04c880385'),
(50, '2026-09-27 01:06:20', NULL, 'Detección de fallas', '138a0e97-ba06-11f1-8c83-00e04c880385'),
(51, '2026-09-27 01:06:20', NULL, 'Semanal', '138a0eb9-ba06-11f1-8c83-00e04c880385'),
(52, '2026-09-27 01:06:20', NULL, 'Post-Obra', '138a0edc-ba06-11f1-8c83-00e04c880385'),
(53, '2026-09-27 01:06:20', NULL, 'Diaria', '138a0f01-ba06-11f1-8c83-00e04c880385'),
(54, '2026-09-27 01:06:20', NULL, 'Java', '138b2fe7-ba06-11f1-8c83-00e04c880385'),
(55, '2026-09-27 01:06:20', NULL, 'Python', '138b35ec-ba06-11f1-8c83-00e04c880385'),
(56, '2026-09-27 01:06:20', NULL, 'JavaScript', '138b3624-ba06-11f1-8c83-00e04c880385'),
(57, '2026-09-27 01:06:20', NULL, 'TypeScript', '138b3655-ba06-11f1-8c83-00e04c880385'),
(58, '2026-09-27 01:06:20', NULL, 'C#', '138b367c-ba06-11f1-8c83-00e04c880385'),
(59, '2026-09-27 01:06:20', NULL, 'Go', '138b36a4-ba06-11f1-8c83-00e04c880385'),
(60, '2026-09-27 01:06:20', NULL, 'Kotlin', '138b36cc-ba06-11f1-8c83-00e04c880385'),
(61, '2026-09-27 01:06:20', NULL, 'APIs REST', '138b36f3-ba06-11f1-8c83-00e04c880385'),
(62, '2026-09-27 01:06:20', NULL, 'GraphQL', '138b3719-ba06-11f1-8c83-00e04c880385'),
(63, '2026-09-27 01:06:20', NULL, 'Microservicios', '138b373f-ba06-11f1-8c83-00e04c880385'),
(64, '2026-09-27 01:06:20', NULL, 'Spring Boot', '138b3766-ba06-11f1-8c83-00e04c880385'),
(65, '2026-09-27 01:06:20', NULL, 'React', '138b378d-ba06-11f1-8c83-00e04c880385'),
(66, '2026-09-27 01:06:20', NULL, 'Angular', '138b37b1-ba06-11f1-8c83-00e04c880385'),
(67, '2026-09-27 01:06:20', NULL, 'Node.js', '138b37d6-ba06-11f1-8c83-00e04c880385'),
(68, '2026-09-27 01:06:20', NULL, 'Git', '138b37fa-ba06-11f1-8c83-00e04c880385'),
(69, '2026-09-27 01:06:20', NULL, 'CI/CD', '138b3823-ba06-11f1-8c83-00e04c880385'),
(70, '2026-09-27 01:06:20', NULL, 'Machine learning', '138b3847-ba06-11f1-8c83-00e04c880385'),
(71, '2026-09-27 01:06:20', NULL, 'Deep learning', '138b386f-ba06-11f1-8c83-00e04c880385'),
(72, '2026-09-27 01:06:20', NULL, 'NLP', '138b3893-ba06-11f1-8c83-00e04c880385'),
(73, '2026-09-27 01:06:20', NULL, 'Análisis de datos', '138b38b9-ba06-11f1-8c83-00e04c880385'),
(74, '2026-09-27 01:06:20', NULL, 'SQL avanzado', '138b38df-ba06-11f1-8c83-00e04c880385'),
(75, '2026-09-27 01:06:20', NULL, 'Power BI', '138b39b3-ba06-11f1-8c83-00e04c880385'),
(76, '2026-09-27 01:06:20', NULL, 'Tableau', '138b39d7-ba06-11f1-8c83-00e04c880385'),
(77, '2026-09-27 01:06:20', NULL, 'Modelos predictivos', '138b39fb-ba06-11f1-8c83-00e04c880385'),
(78, '2026-09-27 01:06:20', NULL, 'Chatbots', '138b3a1d-ba06-11f1-8c83-00e04c880385'),
(79, '2026-09-27 01:06:20', NULL, 'Wireframing', '138b3a41-ba06-11f1-8c83-00e04c880385'),
(80, '2026-09-27 01:06:20', NULL, 'Prototipado', '138b3a64-ba06-11f1-8c83-00e04c880385'),
(81, '2026-09-27 01:06:20', NULL, 'UX Research', '138b3a87-ba06-11f1-8c83-00e04c880385'),
(82, '2026-09-27 01:06:20', NULL, 'UI Design', '138b3aa9-ba06-11f1-8c83-00e04c880385'),
(83, '2026-09-27 01:06:20', NULL, 'Figma', '138b3acb-ba06-11f1-8c83-00e04c880385'),
(84, '2026-09-27 01:06:20', NULL, 'Accesibilidad web', '138b3aed-ba06-11f1-8c83-00e04c880385'),
(85, '2026-09-27 01:06:20', NULL, 'Design System', '138b3b11-ba06-11f1-8c83-00e04c880385'),
(86, '2026-09-27 01:06:20', NULL, 'Identidad de marca', '138b3b33-ba06-11f1-8c83-00e04c880385'),
(87, '2026-09-27 01:06:20', NULL, 'Logotipos', '138b3b56-ba06-11f1-8c83-00e04c880385'),
(88, '2026-09-27 01:06:20', NULL, 'Manual de marca', '138b3b79-ba06-11f1-8c83-00e04c880385'),
(89, '2026-09-27 01:06:20', NULL, 'Packaging', '138b3bb3-ba06-11f1-8c83-00e04c880385'),
(90, '2026-09-27 01:06:20', NULL, 'Flyer y folletería', '138b3bda-ba06-11f1-8c83-00e04c880385'),
(91, '2026-09-27 01:06:20', NULL, 'Infografías', '138b3bfe-ba06-11f1-8c83-00e04c880385'),
(92, '2026-09-27 01:06:20', NULL, 'Motion graphics', '138b3c21-ba06-11f1-8c83-00e04c880385'),
(93, '2026-09-27 01:06:20', NULL, 'Señalética', '138b3c45-ba06-11f1-8c83-00e04c880385'),
(94, '2026-09-27 01:06:20', NULL, 'Godot', '138b3c67-ba06-11f1-8c83-00e04c880385'),
(95, '2026-09-27 01:06:20', NULL, 'Desarrollo de videojuegos', '138b3c89-ba06-11f1-8c83-00e04c880385'),
(96, '2026-09-27 01:06:20', NULL, 'Café de especialidad', '138c425b-ba06-11f1-8c83-00e04c880385'),
(97, '2026-09-27 01:06:20', NULL, 'Latte art', '138c44be-ba06-11f1-8c83-00e04c880385'),
(98, '2026-09-27 01:06:20', NULL, 'Espresso', '138c4505-ba06-11f1-8c83-00e04c880385'),
(99, '2026-09-27 01:06:20', NULL, 'Cold brew', '138c4531-ba06-11f1-8c83-00e04c880385'),
(100, '2026-09-27 01:06:20', NULL, 'Cata de café', '138c455d-ba06-11f1-8c83-00e04c880385'),
(101, '2026-09-27 01:06:20', NULL, 'Vegano', '138c4589-ba06-11f1-8c83-00e04c880385'),
(102, '2026-09-27 01:06:20', NULL, 'Asado y parrilla', '138c45b3-ba06-11f1-8c83-00e04c880385'),
(103, '2026-09-27 01:06:20', NULL, 'Finger food', '138c45e1-ba06-11f1-8c83-00e04c880385'),
(104, '2026-09-27 01:06:20', NULL, 'Comida Sin TACC', '138c460d-ba06-11f1-8c83-00e04c880385'),
(105, '2026-09-27 01:06:20', NULL, 'Kosher', '138c4639-ba06-11f1-8c83-00e04c880385'),
(106, '2026-09-27 01:06:20', NULL, 'Halal', '138c4665-ba06-11f1-8c83-00e04c880385'),
(107, '2026-09-27 01:06:20', NULL, 'Meal prep', '138c468f-ba06-11f1-8c83-00e04c880385'),
(108, '2026-09-27 01:06:20', NULL, 'Vegetariano', '138c46b7-ba06-11f1-8c83-00e04c880385'),
(109, '2026-09-27 01:06:20', NULL, 'Cena de autor', '138c46e2-ba06-11f1-8c83-00e04c880385'),
(110, '2026-09-27 01:06:20', NULL, 'Comida Internacional', '138c470c-ba06-11f1-8c83-00e04c880385'),
(111, '2026-09-27 01:06:20', NULL, 'Cocina Saludable', '138c4734-ba06-11f1-8c83-00e04c880385'),
(112, '2026-09-27 01:06:20', NULL, 'Masa madre', '138c475f-ba06-11f1-8c83-00e04c880385'),
(113, '2026-09-27 01:06:20', NULL, 'Pan de campo', '138c478a-ba06-11f1-8c83-00e04c880385'),
(114, '2026-09-27 01:06:20', NULL, 'Focaccia', '138c47b9-ba06-11f1-8c83-00e04c880385'),
(115, '2026-09-27 01:06:20', NULL, 'Brioche', '138c47e2-ba06-11f1-8c83-00e04c880385'),
(116, '2026-09-27 01:06:20', NULL, 'Facturas', '138c480a-ba06-11f1-8c83-00e04c880385'),
(117, '2026-09-27 01:06:20', NULL, 'Tortas de boda', '138c4834-ba06-11f1-8c83-00e04c880385'),
(118, '2026-09-27 01:06:20', NULL, 'Cupcakes', '138c4912-ba06-11f1-8c83-00e04c880385'),
(119, '2026-09-27 01:06:20', NULL, 'Macarons', '138c493a-ba06-11f1-8c83-00e04c880385'),
(120, '2026-09-27 01:06:20', NULL, 'Cheesecake', '138c495d-ba06-11f1-8c83-00e04c880385'),
(121, '2026-09-27 01:06:20', NULL, 'Cake design', '138c4982-ba06-11f1-8c83-00e04c880385'),
(122, '2026-09-27 01:06:20', NULL, 'Mozos', '138c49a4-ba06-11f1-8c83-00e04c880385'),
(123, '2026-09-27 01:06:20', NULL, 'Maitre', '138c49c7-ba06-11f1-8c83-00e04c880385'),
(124, '2026-09-27 01:06:20', NULL, 'Sommelier', '138c49ea-ba06-11f1-8c83-00e04c880385'),
(125, '2026-09-27 01:06:20', NULL, 'Barman', '138c4a0d-ba06-11f1-8c83-00e04c880385'),
(126, '2026-09-27 01:06:20', NULL, 'Coctelería', '138c4a30-ba06-11f1-8c83-00e04c880385'),
(127, '2026-09-27 01:06:20', NULL, 'Protocolo de mesa', '138c4a53-ba06-11f1-8c83-00e04c880385'),
(128, '2026-09-27 01:06:20', NULL, 'Iluminación ambiental', '138c4a76-ba06-11f1-8c83-00e04c880385'),
(129, '2026-09-27 01:06:20', NULL, 'Globología', '138c4a9b-ba06-11f1-8c83-00e04c880385'),
(130, '2026-09-27 01:06:20', NULL, 'Centros de mesa', '138c4abf-ba06-11f1-8c83-00e04c880385'),
(131, '2026-09-27 01:06:20', NULL, 'Arcos florales', '138c4ae3-ba06-11f1-8c83-00e04c880385'),
(132, '2026-09-27 01:06:20', NULL, 'Photo wall', '138c4b07-ba06-11f1-8c83-00e04c880385'),
(133, '2026-09-27 01:06:20', NULL, 'Limpiezas faciales', '138d2dd7-ba06-11f1-8c83-00e04c880385'),
(134, '2026-09-27 01:06:20', NULL, 'Microdermoabrasión', '138d3f87-ba06-11f1-8c83-00e04c880385'),
(135, '2026-09-27 01:06:20', NULL, 'Peeling químico', '138d3ff5-ba06-11f1-8c83-00e04c880385'),
(136, '2026-09-27 01:06:20', NULL, 'Radiofrecuencia', '138d403d-ba06-11f1-8c83-00e04c880385'),
(137, '2026-09-27 01:06:20', NULL, 'Cavitación', '138d408c-ba06-11f1-8c83-00e04c880385'),
(138, '2026-09-27 01:06:20', NULL, 'Micropigmentación', '138d40d7-ba06-11f1-8c83-00e04c880385'),
(139, '2026-09-27 01:06:20', NULL, 'Microblading', '138d4102-ba06-11f1-8c83-00e04c880385'),
(140, '2026-09-27 01:06:20', NULL, 'Lifting de pestañas', '138d412e-ba06-11f1-8c83-00e04c880385'),
(141, '2026-09-27 01:06:20', NULL, 'Manicura Rusa', '138d415a-ba06-11f1-8c83-00e04c880385'),
(142, '2026-09-27 01:06:20', NULL, 'Uñas esculpidas', '138d4186-ba06-11f1-8c83-00e04c880385'),
(143, '2026-09-27 01:06:20', NULL, 'Nail art', '138d41af-ba06-11f1-8c83-00e04c880385'),
(144, '2026-09-27 01:06:20', NULL, 'Polygel', '138d41db-ba06-11f1-8c83-00e04c880385'),
(145, '2026-09-27 01:06:20', NULL, 'Pedicuria spa', '138d4207-ba06-11f1-8c83-00e04c880385'),
(146, '2026-09-27 01:06:20', NULL, 'Masaje Deportivo', '138d4232-ba06-11f1-8c83-00e04c880385'),
(147, '2026-09-27 01:06:20', NULL, 'Masaje Descontracturante', '138d425b-ba06-11f1-8c83-00e04c880385'),
(148, '2026-09-27 01:06:20', NULL, 'Reflexología', '138d4287-ba06-11f1-8c83-00e04c880385'),
(149, '2026-09-27 01:06:20', NULL, 'Drenaje linfático', '138d42b2-ba06-11f1-8c83-00e04c880385'),
(150, '2026-09-27 01:06:20', NULL, 'Aromaterapia', '138d42dc-ba06-11f1-8c83-00e04c880385'),
(151, '2026-09-27 01:06:20', NULL, 'Corte de cabello', '138d4305-ba06-11f1-8c83-00e04c880385'),
(152, '2026-09-27 01:06:20', NULL, 'Coloración', '138d432e-ba06-11f1-8c83-00e04c880385'),
(153, '2026-09-27 01:06:20', NULL, 'Mechas Balayage', '138d4358-ba06-11f1-8c83-00e04c880385'),
(154, '2026-09-27 01:06:20', NULL, 'Alisado permanente', '138d4381-ba06-11f1-8c83-00e04c880385'),
(155, '2026-09-27 01:06:20', NULL, 'Barbería', '138d43ab-ba06-11f1-8c83-00e04c880385'),
(156, '2026-09-27 01:06:20', NULL, 'Tratamientos capilares', '138d43d8-ba06-11f1-8c83-00e04c880385'),
(157, '2026-09-27 01:06:20', NULL, 'Matemática', '138d4401-ba06-11f1-8c83-00e04c880385'),
(158, '2026-09-27 01:06:20', NULL, 'Física', '138d4429-ba06-11f1-8c83-00e04c880385'),
(159, '2026-09-27 01:06:20', NULL, 'Química', '138d4453-ba06-11f1-8c83-00e04c880385'),
(160, '2026-09-27 01:06:20', NULL, 'Biología', '138d447c-ba06-11f1-8c83-00e04c880385'),
(161, '2026-09-27 01:06:20', NULL, 'Literatura', '138d44bf-ba06-11f1-8c83-00e04c880385'),
(162, '2026-09-27 01:06:20', NULL, 'Economía', '138d44ee-ba06-11f1-8c83-00e04c880385'),
(163, '2026-09-27 01:06:20', NULL, 'Derecho', '138d4516-ba06-11f1-8c83-00e04c880385'),
(164, '2026-09-27 01:06:20', NULL, 'Ingreso universitario', '138d4541-ba06-11f1-8c83-00e04c880385'),
(165, '2026-09-27 01:06:20', NULL, 'IELTS/TOEFL', '138d456a-ba06-11f1-8c83-00e04c880385'),
(166, '2026-09-27 01:06:20', NULL, 'Traducción técnica', '138d4592-ba06-11f1-8c83-00e04c880385'),
(167, '2026-09-27 01:06:20', NULL, 'Interpretación', '138d45bb-ba06-11f1-8c83-00e04c880385'),
(168, '2026-09-27 01:06:20', NULL, 'Conversación de idiomas', '138d45e5-ba06-11f1-8c83-00e04c880385'),
(169, '2026-09-27 01:06:20', NULL, 'Bodas', '138e3097-ba06-11f1-8c83-00e04c880385'),
(170, '2026-09-27 01:06:20', NULL, '15 años', '138e42e4-ba06-11f1-8c83-00e04c880385'),
(171, '2026-09-27 01:06:20', NULL, 'Producto', '138e4333-ba06-11f1-8c83-00e04c880385'),
(172, '2026-09-27 01:06:20', NULL, 'Corporativa', '138e4361-ba06-11f1-8c83-00e04c880385'),
(173, '2026-09-27 01:06:20', NULL, 'Mascotas', '138e4392-ba06-11f1-8c83-00e04c880385'),
(174, '2026-09-27 01:06:20', NULL, 'Video de bodas', '138e43bb-ba06-11f1-8c83-00e04c880385'),
(175, '2026-09-27 01:06:20', NULL, 'Drone', '138e43e7-ba06-11f1-8c83-00e04c880385'),
(176, '2026-09-27 01:06:20', NULL, 'Videoclip', '138e440f-ba06-11f1-8c83-00e04c880385'),
(177, '2026-09-27 01:06:20', NULL, 'Deportes', '138e4436-ba06-11f1-8c83-00e04c880385'),
(178, '2026-09-27 01:06:20', NULL, 'Edición', '138e445f-ba06-11f1-8c83-00e04c880385'),
(179, '2026-09-27 01:06:20', NULL, 'Retratos', '138e4488-ba06-11f1-8c83-00e04c880385'),
(180, '2026-09-27 01:06:20', NULL, 'Artística', '138e44b2-ba06-11f1-8c83-00e04c880385'),
(181, '2026-09-27 01:06:20', NULL, 'Streaming', '138e44da-ba06-11f1-8c83-00e04c880385'),
(182, '2026-09-27 01:06:20', NULL, 'Coberturas', '138e4503-ba06-11f1-8c83-00e04c880385'),
(183, '2026-09-27 01:06:20', NULL, 'Aérea', '138e452a-ba06-11f1-8c83-00e04c880385'),
(184, '2026-09-27 01:06:20', NULL, 'Recitales', '138e4551-ba06-11f1-8c83-00e04c880385'),
(185, '2026-09-27 01:06:20', NULL, 'Culinaria', '138e4578-ba06-11f1-8c83-00e04c880385'),
(186, '2026-09-27 01:06:20', NULL, 'Lightroom', '138e459f-ba06-11f1-8c83-00e04c880385'),
(187, '2026-09-27 01:06:20', NULL, 'Premiere Pro', '138e45c8-ba06-11f1-8c83-00e04c880385'),
(188, '2026-09-27 01:06:20', NULL, 'Photoshop', '138e45ee-ba06-11f1-8c83-00e04c880385'),
(189, '2026-09-27 01:06:20', NULL, 'DaVinci Resolve', '138e4619-ba06-11f1-8c83-00e04c880385'),
(190, '2026-09-27 01:06:20', NULL, 'After Effects', '138e4640-ba06-11f1-8c83-00e04c880385'),
(191, '2026-09-27 01:06:20', NULL, 'Fotografía nocturna', '138e4669-ba06-11f1-8c83-00e04c880385'),
(192, '2026-09-27 01:06:20', NULL, 'Fotografía de eventos', '138e4693-ba06-11f1-8c83-00e04c880385'),
(193, '2026-09-27 01:06:20', NULL, 'Fotografía de moda', '138e46bc-ba06-11f1-8c83-00e04c880385'),
(194, '2026-09-27 01:06:20', NULL, 'Fotografía inmobiliaria', '138e46e5-ba06-11f1-8c83-00e04c880385'),
(195, '2026-09-27 01:06:20', NULL, 'Fotografía gastronómica', '138e470d-ba06-11f1-8c83-00e04c880385'),
(196, '2026-09-27 01:06:20', NULL, 'Fotografía documental', '138e4737-ba06-11f1-8c83-00e04c880385'),
(197, '2026-09-27 01:06:20', NULL, 'Fotoperiodismo', '138e475f-ba06-11f1-8c83-00e04c880385'),
(198, '2026-09-27 01:06:20', NULL, 'Color grading', '138e478b-ba06-11f1-8c83-00e04c880385'),
(199, '2026-09-27 01:06:20', NULL, 'Iluminación', '138e47b2-ba06-11f1-8c83-00e04c880385'),
(200, '2026-09-27 01:06:20', NULL, 'Manejo de cámara', '138e47da-ba06-11f1-8c83-00e04c880385'),
(201, '2026-09-27 01:06:20', NULL, 'Dirección de fotografía', '138e4804-ba06-11f1-8c83-00e04c880385'),
(202, '2026-09-27 01:06:20', NULL, 'Edición de sonido', '138e482b-ba06-11f1-8c83-00e04c880385'),
(203, '2026-09-27 01:06:20', NULL, 'Motion graphics', '138e4852-ba06-11f1-8c83-00e04c880385'),
(204, '2026-09-27 01:06:20', NULL, 'Sesiones exteriores', '138e4879-ba06-11f1-8c83-00e04c880385'),
(205, '2026-09-27 01:06:20', NULL, 'Sesiones de estudio', '138e48a3-ba06-11f1-8c83-00e04c880385'),
(206, '2026-09-27 01:06:20', NULL, 'Cobertura de shows', '138e48cb-ba06-11f1-8c83-00e04c880385'),
(207, '2026-09-27 01:06:20', NULL, 'Video publicitario', '138e48f2-ba06-11f1-8c83-00e04c880385'),
(208, '2026-09-27 01:06:20', NULL, 'Contenido para marcas', '138e6f45-ba06-11f1-8c83-00e04c880385'),
(209, '2026-09-27 01:06:20', NULL, 'Obediencia básica', '138f6bde-ba06-11f1-8c83-00e04c880385'),
(210, '2026-09-27 01:06:20', NULL, 'Corrección de conducta', '138f7d1f-ba06-11f1-8c83-00e04c880385'),
(211, '2026-09-27 01:06:20', NULL, 'Socialización de cachorros', '138f7d82-ba06-11f1-8c83-00e04c880385'),
(212, '2026-09-27 01:06:20', NULL, 'Paseos grupales', '138f7db4-ba06-11f1-8c83-00e04c880385'),
(213, '2026-09-27 01:06:20', NULL, 'Hospedaje de mascotas', '138f7de3-ba06-11f1-8c83-00e04c880385'),
(214, '2026-09-27 01:06:20', NULL, 'Cat sitting', '138f7e0f-ba06-11f1-8c83-00e04c880385'),
(215, '2026-09-27 01:06:20', NULL, 'Transporte veterinario', '138f7e3d-ba06-11f1-8c83-00e04c880385'),
(216, '2026-09-27 01:06:20', NULL, 'Baño y secado', '138f7e67-ba06-11f1-8c83-00e04c880385'),
(217, '2026-09-27 01:06:20', NULL, 'Corte de raza', '138f7e8f-ba06-11f1-8c83-00e04c880385'),
(218, '2026-09-27 01:06:20', NULL, 'Deslanado', '138f7eb8-ba06-11f1-8c83-00e04c880385'),
(219, '2026-09-27 01:06:20', NULL, 'Geriátricas (mascotas)', '138f7ee4-ba06-11f1-8c83-00e04c880385'),
(220, '2026-09-27 01:06:20', NULL, 'Embalaje de mudanza', '138f7f0b-ba06-11f1-8c83-00e04c880385'),
(221, '2026-09-27 01:06:20', NULL, 'Armado de muebles', '138f7f33-ba06-11f1-8c83-00e04c880385'),
(222, '2026-09-27 01:06:20', NULL, 'Seguro de cargas', '138f7f5c-ba06-11f1-8c83-00e04c880385'),
(223, '2026-09-27 01:06:20', NULL, 'Transporte de obras de arte', '138f7f85-ba06-11f1-8c83-00e04c880385'),
(224, '2026-09-27 01:06:20', NULL, 'Reparto Last mile', '138f7fab-ba06-11f1-8c83-00e04c880385'),
(225, '2026-09-27 01:06:20', NULL, 'Logística en frío', '138f7fd3-ba06-11f1-8c83-00e04c880385'),
(226, '2026-09-27 01:06:20', NULL, 'Trazabilidad de envíos', '138f7ffb-ba06-11f1-8c83-00e04c880385'),
(227, '2026-09-27 01:06:20', NULL, 'Apertura de cerraduras', '138f8023-ba06-11f1-8c83-00e04c880385'),
(228, '2026-09-27 01:06:20', NULL, 'Cajas de seguridad', '138f804c-ba06-11f1-8c83-00e04c880385'),
(229, '2026-09-27 01:06:20', NULL, 'Cerraduras digitales', '138f8172-ba06-11f1-8c83-00e04c880385'),
(230, '2026-09-27 01:06:20', NULL, 'Llave maestra', '138f81aa-ba06-11f1-8c83-00e04c880385');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habilidad_especialidad`
--

CREATE TABLE `habilidad_especialidad` (
  `id_habilidad` int(11) NOT NULL,
  `id_especialidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habilidad_especialidad`
--

INSERT INTO `habilidad_especialidad` (`id_habilidad`, `id_especialidad`) VALUES
(1, 20),
(2, 20),
(3, 20),
(4, 20),
(5, 20),
(6, 31),
(7, 31),
(8, 31),
(9, 20),
(10, 20),
(10, 57),
(11, 31),
(12, 20),
(13, 20),
(14, 20),
(14, 31),
(15, 25),
(16, 25),
(17, 25),
(18, 25),
(19, 25),
(20, 25),
(21, 25),
(22, 25),
(23, 33),
(24, 33),
(25, 33),
(26, 33),
(27, 33),
(28, 33),
(29, 33),
(30, 33),
(39, 57),
(40, 57),
(41, 23),
(42, 23),
(43, 23),
(44, 23),
(45, 23),
(45, 35),
(46, 23),
(46, 35),
(47, 35),
(48, 23),
(48, 35),
(49, 23),
(50, 23),
(50, 35),
(51, 29),
(52, 29),
(53, 29),
(54, 52),
(55, 52),
(56, 52),
(56, 53),
(57, 52),
(57, 53),
(58, 52),
(59, 52),
(60, 52),
(61, 52),
(62, 52),
(63, 52),
(64, 52),
(65, 52),
(66, 52),
(67, 52),
(67, 55),
(68, 52),
(69, 52),
(70, 55),
(71, 55),
(72, 55),
(73, 55),
(74, 52),
(74, 55),
(75, 55),
(76, 55),
(77, 55),
(78, 52),
(79, 53),
(80, 53),
(81, 53),
(82, 53),
(83, 53),
(83, 59),
(83, 60),
(83, 61),
(84, 53),
(85, 53),
(86, 59),
(87, 59),
(88, 59),
(89, 59),
(89, 61),
(90, 59),
(91, 59),
(92, 59),
(93, 59),
(94, 52),
(94, 60),
(95, 52),
(95, 60),
(101, 6),
(101, 7),
(101, 17),
(102, 7),
(103, 7),
(104, 6),
(104, 7),
(104, 17),
(105, 7),
(106, 7),
(107, 6),
(107, 17),
(108, 6),
(108, 17),
(109, 6),
(109, 16),
(110, 6),
(111, 6),
(111, 17),
(112, 14),
(112, 16),
(113, 16),
(114, 16),
(115, 16),
(116, 16),
(117, 14),
(118, 14),
(119, 14),
(120, 14),
(121, 14),
(122, 15),
(123, 15),
(124, 15),
(125, 15),
(126, 15),
(127, 15),
(133, 45),
(134, 45),
(135, 45),
(136, 45),
(137, 45),
(138, 45),
(139, 45),
(140, 45),
(141, 46),
(142, 46),
(143, 46),
(144, 46),
(145, 46),
(151, 48),
(152, 48),
(153, 48),
(154, 48),
(155, 48),
(156, 48),
(157, 1),
(158, 1),
(159, 1),
(160, 1),
(161, 1),
(162, 1),
(163, 1),
(164, 1),
(165, 3),
(166, 3),
(167, 3),
(168, 3),
(169, 40),
(170, 40),
(171, 40),
(172, 41),
(173, 41),
(174, 41),
(175, 41),
(176, 42),
(177, 42),
(178, 42),
(179, 42),
(180, 49),
(180, 50),
(181, 50),
(181, 57),
(182, 49),
(182, 50),
(183, 50),
(184, 49),
(184, 51),
(185, 51),
(186, 51),
(187, 22),
(188, 22),
(189, 22),
(190, 22),
(191, 10),
(192, 10),
(193, 10),
(194, 10),
(195, 10),
(196, 11),
(197, 11),
(198, 11),
(199, 11),
(200, 11),
(201, 10),
(202, 60),
(203, 11),
(204, 11),
(205, 11),
(206, 11),
(208, 10),
(209, 11),
(210, 10),
(210, 53),
(210, 59),
(210, 60),
(211, 11),
(212, 11),
(212, 59),
(213, 10),
(214, 10),
(215, 10),
(215, 61),
(216, 10),
(217, 10),
(220, 11),
(221, 10),
(221, 11),
(222, 10),
(222, 11),
(223, 11),
(224, 11),
(225, 11),
(225, 59),
(226, 10),
(227, 10),
(229, 11),
(230, 53);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habilidad_tecnico`
--

CREATE TABLE `habilidad_tecnico` (
  `id_tecnico` int(11) NOT NULL,
  `id_habilidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `id_mensaje` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `archivo_url` varchar(255) DEFAULT NULL,
  `fecha_mensaje` datetime(6) NOT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  `tipo_archivo` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `id_sender` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `estado` enum('Cancelado','Confirmado','Pendiente_de_revision','Rechazado') DEFAULT NULL,
  `fecha_pago` datetime(6) NOT NULL,
  `metodo_pago` enum('Credito','Debito','Efectivo','Transferencia') NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_trabajo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permit`
--

CREATE TABLE `permit` (
  `id_permit` int(11) NOT NULL,
  `permiso` enum('CAMBIAR_CLAVE','CONFIRMAR_PRESUPUESTO','CREAR_USUARIO','DAR_DE_BAJA_USUARIO','DENUNCIAR_USUARIO','EDITAR_PERFIL','EDITAR_PRESUPUESTO','ELIMINAR_USUARIO','REALIZAR_PRESUPUESTO','RECHAZAR_PRESUPUESTO','VER_PERFIL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `portfolio`
--

CREATE TABLE `portfolio` (
  `id_portfolio` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `archivo_adjunto` mediumblob DEFAULT NULL,
  `enlace_externo` varchar(255) DEFAULT NULL,
  `estado_verificacion` enum('Aprobado','Pendiente','Rechazado') DEFAULT NULL,
  `fecha_entrega` datetime(6) DEFAULT NULL,
  `notas_admin` text DEFAULT NULL,
  `notas_aspirante` text DEFAULT NULL,
  `tipo_archivo` varchar(100) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_especialidad` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `presupuesto`
--

CREATE TABLE `presupuesto` (
  `id_presupuesto` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `descripcion_presupuesto` text NOT NULL,
  `estado` enum('Aceptado','Cancelado','Pendiente','Rechazado') NOT NULL,
  `precio_estimado` decimal(10,2) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rating`
--

CREATE TABLE `rating` (
  `id_rating` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `foto` mediumblob DEFAULT NULL,
  `tipo_foto` varchar(50) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `valoracion` int(11) NOT NULL,
  `id_usuario_destinatario` int(11) NOT NULL,
  `id_usuario_remitente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rating_caracteristica`
--

CREATE TABLE `rating_caracteristica` (
  `id_rating` int(11) NOT NULL,
  `id_caracteristica` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_role` int(11) NOT NULL,
  `rol` enum('ROLE_ADMINISTRADOR','ROLE_SUPERADMINISTRADOR','ROLE_TECNICO','ROLE_USUARIO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_role`, `rol`) VALUES
(2, 'ROLE_ADMINISTRADOR'),
(1, 'ROLE_SUPERADMINISTRADOR'),
(4, 'ROLE_TECNICO'),
(3, 'ROLE_USUARIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rubro`
--

CREATE TABLE `rubro` (
  `id_rubro` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `nombre_rubro` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rubro`
--

INSERT INTO `rubro` (`id_rubro`, `created_at`, `updated_at`, `nombre_rubro`, `uuid`) VALUES
(1, '2026-09-27 01:02:31', NULL, 'Hogar', '1386b5d9-ba06-11f1-8c83-00e04c880385'),
(2, '2026-09-27 01:02:31', NULL, 'Construcción', '1386c8e3-ba06-11f1-8c83-00e04c880385'),
(3, '2026-09-27 01:02:31', NULL, 'Mascotas', '1386c994-ba06-11f1-8c83-00e04c880385'),
(4, '2026-09-27 01:02:31', NULL, 'Eventos', '1386c9ce-ba06-11f1-8c83-00e04c880385'),
(5, '2026-09-27 01:02:31', NULL, 'Belleza y Salud', '1386ca05-ba06-11f1-8c83-00e04c880385'),
(6, '2026-09-27 01:02:31', NULL, 'Transporte', '1386ca3a-ba06-11f1-8c83-00e04c880385'),
(7, '2026-09-27 01:02:31', NULL, 'Arte y Diseño', '1386ca6d-ba06-11f1-8c83-00e04c880385'),
(8, '2026-09-27 01:02:31', NULL, 'Tecnología', '1386ca9d-ba06-11f1-8c83-00e04c880385'),
(9, '2026-09-27 01:02:31', NULL, 'Educación', '1386cacf-ba06-11f1-8c83-00e04c880385'),
(10, '2026-09-27 01:02:31', NULL, 'Gastronomía', '1386caff-ba06-11f1-8c83-00e04c880385');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rubro_especialidad`
--

CREATE TABLE `rubro_especialidad` (
  `id_rubro` int(11) NOT NULL,
  `id_especialidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rubro_especialidad`
--

INSERT INTO `rubro_especialidad` (`id_rubro`, `id_especialidad`) VALUES
(9, 1),
(5, 2),
(9, 2),
(9, 3),
(9, 4),
(4, 5),
(10, 5),
(4, 6),
(10, 6),
(4, 7),
(10, 7),
(4, 8),
(4, 9),
(1, 10),
(4, 10),
(7, 10),
(1, 11),
(4, 11),
(7, 11),
(4, 12),
(5, 12),
(4, 13),
(7, 13),
(9, 13),
(10, 14),
(4, 15),
(4, 16),
(7, 16),
(10, 17),
(10, 18),
(1, 19),
(2, 19),
(1, 20),
(2, 20),
(1, 21),
(2, 21),
(7, 21),
(1, 22),
(2, 22),
(1, 23),
(2, 23),
(1, 24),
(2, 24),
(1, 25),
(2, 25),
(1, 26),
(2, 26),
(7, 26),
(1, 27),
(2, 27),
(7, 27),
(1, 28),
(2, 28),
(1, 29),
(2, 29),
(4, 29),
(1, 30),
(2, 30),
(1, 31),
(2, 31),
(1, 32),
(2, 32),
(1, 33),
(2, 33),
(1, 34),
(2, 34),
(1, 35),
(2, 35),
(8, 35),
(1, 36),
(2, 36),
(1, 37),
(2, 37),
(1, 38),
(2, 38),
(1, 39),
(2, 39),
(3, 40),
(3, 41),
(3, 42),
(5, 43),
(5, 44),
(1, 45),
(4, 45),
(5, 45),
(4, 46),
(5, 46),
(5, 47),
(4, 48),
(5, 48),
(1, 49),
(2, 49),
(6, 49),
(1, 50),
(2, 50),
(6, 50),
(1, 51),
(2, 51),
(6, 51),
(8, 52),
(9, 52),
(7, 53),
(8, 53),
(8, 54),
(8, 55),
(1, 56),
(2, 56),
(7, 56),
(1, 57),
(2, 57),
(1, 58),
(2, 58),
(7, 58),
(7, 59),
(7, 60),
(9, 60),
(4, 61),
(7, 61);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tecnico`
--

CREATE TABLE `tecnico` (
  `id_tecnico` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `cuit` varchar(20) NOT NULL,
  `descripcion_trabajo` text DEFAULT NULL,
  `proyectos` text DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo`
--

CREATE TABLE `trabajo` (
  `id_trabajo` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `descripcion_trabajo` text NOT NULL,
  `estado_trabajo` enum('Cancelado','En_proceso','Finalizado','Pendiente') NOT NULL,
  `fecha_estimada_fin` date NOT NULL,
  `fecha_estimada_inicio` date NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_presupuesto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion`
--

CREATE TABLE `ubicacion` (
  `id_ubicacion` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `calle` varchar(50) NOT NULL,
  `ciudad` varchar(50) NOT NULL,
  `codigo_postal` varchar(50) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `numero_piso` int(11) DEFAULT NULL,
  `piso` int(11) DEFAULT NULL,
  `provincia` varchar(50) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `clave` varchar(255) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `email` varchar(255) NOT NULL,
  `fecha_fin_suspension` date DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `foto` mediumblob DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `sobre_mi` text NOT NULL,
  `telefono` varchar(50) NOT NULL,
  `tipo_imagen` varchar(50) NOT NULL,
  `ultima_actividad` datetime(6) NOT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `created_at`, `updated_at`, `activo`, `apellido`, `clave`, `dni`, `email`, `fecha_fin_suspension`, `fecha_nacimiento`, `foto`, `nombre`, `sobre_mi`, `telefono`, `tipo_imagen`, `ultima_actividad`, `uuid`) VALUES
(1, '2026-10-19 03:14:09', NULL, b'1', 'Silvestre', '$2a$10$U6rHOEvqSsXp3pn0YghhCOXLefJYkK2KzEVAuVrkfdIR4T01z5LHa', '38785285', 'geronimosilvestre@hotmail.com', NULL, '1995-08-28', 0x89504e470d0a1a0a0000000d49484452000000d6000000d608020000002281eb79000000017352474200aece1ce90000000467414d410000b18f0bfc61050000000970485973000012740000127401de661f7800000021744558744372656174696f6e2054696d6500323032333a30343a33302030353a32373a33308b8e855d0000ea0449444154785eecfdf9af2c5992e787c5e6e1b12f777df76db96fb52f5dd53dddcd9e9e951a081c082005f1074122288192f88ba0ff6020e807811024111809d20f82000d25521c71380b677a38bd4defd5d35dddd5d5b564656665e6cbf7deddef8d1bfb1ea1cfd7ccddc3eff2b2b2461cf197b1ebd7e31c3b76ecd8b163c78e1d0f778fec7abdcefc6bf8d7f0df1ee4a2cf7f0dff1afe5b82c80bfea7ffd1ff72b958aeb3d95caeb05aad8242b098cfc69309c77432a9371aed762b97cb2de68bf97295cfe783a0389f4d73b9ec6c3a0d82205bc8678ddd7ab5ca17f2855c7e3e9fe7f201ec60bf5a2ee1b25a67a0cfd05a3613168b10d050312cc276321e0741a1d7ef97c2309f2f20c972b52a97cba4219e4ea6f3f92c972f8cc6a3d5620e7e381ac23397c9361b4d0816b455288461389bced76417cb4c96a9b59a8e271984280868ab500826f329fd2d168b954a05cc7ab55eae96b97c9ec478acee94ca65da5ee772c5422197cd65697d3a5b2e9754a1d7e88374be1084c5905a93c9a4542a212489bce8b3942246369b5dad96b3d9bca8746e3a9ba9d7f43e28cc170b3a82aec6e3c97030428c52096584280a4916a6105aac94cb2695a0101446a3b1640bcb52a098af680bc5aad17cb0467a750a261200bd2e20421814b1942e690ea5c1905a6451f83abb0e82301f8488837216cb05f2d117b823216c510d92e70be10ce52e166158a19bfce532398a184d948911a0288d3bec0419ec2217066bb49fcba22e14ac042262218c47c6a954c430c11e60802213fcbbffcfff7b1896c082229bcfe5449fc960285803498c12d1cdc668a9406f17f3a914c790886526a3a15b21a6949dc9a2d0f57aa97655422283e230e06250240d4335af61412d597a49630c249aa26fabe56a6e234dc39416f2792a42090706099110a2100479e94816461b548718518b61090dae41c8c02439d2a202aae7727964475ff4ce45863984f4023a1a653044b35e312c993ca305067a384b7bd4416d2e39bc347b995536f360055e5a650617a47af44311fa6720910a0af5c41498340b995985acca6c0be6744523045770ae1fac79be40c9ea00d5ccbab2cc404872798c298736c0c041accd8b30d9258f6433f95c3c01455009ad1cfc75b6a444029471196d86223649c460b030155092108074c99fead99fa5d4c9ccca4d07621f3e6bde45f0a62391566a7289a89109fe57bff367eded1d442c044506561a71620c5c66271aabcbbf0e1b026b0a129aa233911046a7f150c16abd9078124d0ab51255115d241d800c207c38c14a2d90ad2056cf404bb93602a29237a52a3458b94c1c72b3066bdc7226a2ac409030344eb227f06445e87c44a5a6a437eb408a93a51c651c7ca41d28072950467f6e94aaa74ff0f25808e0b24324fefae40f6b301af1f376bc86c49538620b322a11f3ac66824b652a8f2434882415995720613412382296993b4f66c56c36c5d333619cda19a87dd246c37f945366ed1db0a484e443f3db51268565bca29dad28928f396919cfda60a9333916483ae46c7ee55befb577b6e9398b0576084603a896bcae6a6250422056161f21459969523f6acee8e01b29d565a15995da3f5ab002f30a5147a453e451d678582dd5f3bae00c8c1c0f14f7c55072b14a88d830fa9330644c16ef9dda75a4a6aec45703809199992bcf27d451897a6ea239c007905f933139464468c0c6d89adbb035c9d4b4800fb4e53dbf09a64f558f194444362ea9ee3b1857fff361c465c6f2400a26a9a57e4900658d3212874212ac4b2ca6f89a52b912b59554f1b4d5748ed462aa47fc94f60f3ead1209ff54893296b76402c6536d5b1b4a330d40b85375d2fffa8f3f6c6d6f93ca66f36b731fd633b17320e3944a93f37624a5ec89bc6448685c083b94f5cec888572cfd2a97a8719f5e009ab5320313516747aa9fd6aeb2ae06599af29293f9cdca956735930872c4260d8b83e5dd7b4710b5ef1d82c146e5e26905c6570502864138e3a0229a9368ea9e91790dd38cd590c588461fc8a80f11a54174d2b415888d3b079218378b6f5c85ca49c23eecd31a26496828268634e540eca218cf9834ae268d125413e511ad16f344340a9dac0480c4f46d35c55c1fe2c6617f3a94f6936a4833de903e1045dab2b65c45c46424580a28d55819a9ea6878622ffd6b7ffaa4b9b5a5948f96fe58b0d09c29c8e48352ede051e066036601df9a584d4c2032311401784ab58ca5815a2534b65549aca86f85c6dc75bd21062857c906ed7ddad0b84e40d09c2805444ed208ff6e6c22108bb83cea89a58da1e77596447cc6b8a421c989aaa40dab6eda165a691b9d18a1ca5ecb668a4206c371180560b47e58d6382895d4d4bf239daf9d4dd2880e8c7d584e9261496129745626a41179b99d495b8060e9b880aaf3d98c7ef94e2b6a4d4589284a99015ade95b4610983286735a56eda96d9c8a604ce9284ad560cbcf25e877f2d321916a5426482bffedde78d46cb2a81d16a63d4aaa9938153d20ae5cedbf6708ae263433035894ce5b221935bb8cd69c33006ea5264a562ef9f4e69194e5121107362e6e615aa93b32a464983323fed4b08a02356897235f374f693a26deb91cf4e89ad9c3460e59e515dd1c99bd221599545a26ee062a1a5306e2162683957bc8d7ea45793c42939db01d2b56a9b36b8b1bb5286426b1f86aa23fe661196b56203abcb10b0eb749c5182b4ca54b1f6ac67ee8645201aabb8b4e9ca56c3d81b638132d61175d65c8a4dbf78e0144c8b037f1a7d28b57f3285586b22b3ea6a9c62b2d7781b8025995b2fd6b90235053e0f54263745dd2ca271c086759903293c21c6d2269b820c8163bec8ce5c28d5555fbd49539c5ab15e481a2b32c9e8458c2761e98824397cdd94228ca72474701e20d8441f3e3f9205aed972b22bc4f83448dab349479228aebea9af2eb16a71308dd8ba2d32d3e18431c488cc2001f4aec3954b15afac49a9b4491529d20589cee9837fffccae97d61f073a85fb2767e62b3013f61ea98e458c02af14d7b3462d61f627d2f8b012d66b5a8ad978b18474df2322591d341a154aa5714b33fa39b6fcb4a9118c99c28aaa2ea9da8405c5dad2595eec84543e4f8fb4ebcfe465a55ec1f818a82da3db0065ea329b0a27f215d592195d4c915862e1a01962fdb90d36544943c6dac0f1802eebf910c6544a47e009ce092602ab0a68fc6f371c95c540a39d4e673e9b1b76bda45cfd43b102ed40e98c37921a9f0d581a4a5d6082c07a6b761b1d46043282c422d348d270886d16a722fb05cb79b96218b34bb303b3064b48083bdf050a50341cf1001880a1096b2dae156b3b81bbd9bd00208e0e630b06fe5ea496cd48a26c8450a90b20a0940a51155d15cbe5039b3b5a0a64ae5e3fcd32961f0023cb4059068e89e4f8dd1f5db039d2a8c1d66c74e946ac53c4452983887f22ba4d669b5dd7401245c96b00aba80f1b9e64530d00d4b5f52cee6d246e1abc2710689d30326f31c5dffe6226099e73c24d9898cc4f91d0eab9f24ee9e7141fe51ce3bd4c1358de3f1092f057964ed2a6784cb361754db780172589a434c1dfa04f2021b80d7716391fb3fb489204190193298521410f6c8a5a16c475be6926f661d85b109141b19a670a61e4051d0b32e198b404509a4e40ca2152773411dda6b658596d732e46933a403a1ed3c1887456423cc54a832a9e9b738a4f9a61960da311df864860f52192048cc3a634462641a1742ac60e51c2245267cde29513111283262b45484c0ea64cde9625c65487354b84870b65bf96b3e114ea16383e0d31f246a9d28e51c1ad8a8e4c8323d3a59e76906c31324948ea34a4ab7899ba6e9faed95b10318c894596a224e9aeddd28a00221374848eb8451b8f6b10b18e8a644666108615c7e4102510994b3244f101b1e395b0b325cc9d184648459f5113d7f8a41992b6d8340161c44f2809707df5f444028e79119e06d8eec30051168be5624ec85ff086c1cf08005618a5cc51b22a54d2972293f174349a0c39c6a3f174baa44bd902789784b32780e9743a180c6c305c016a13480d1692e89c606e643f0bcc66b3c56271671564e04c9197252bbea10486be0e31de04493e048ee7ec6c2d2fe2cdf0580e88b2204c6942799ddf7befa254a992d2774b7cb05db61660a4b39139a5fea5b7089ca905412ab1724f6c3ae9d904d278d296f0b1748896578a18b064c638785dc0914ee69804c4cb3479a3c8db02d259ce9e70641a681e3fc19c1d0e06b0abd66afa52613e83deae2e65836271b958f6fac3e96c4211b1e9f1d111fba4a5ed929aadf6c1c141b3d16c54abe572582a06cbe50281d841437c7878d86ab5daed367244cdd9d944222389f8f44e3a0d016722e78b64be0198200a0c82c0b337aaa4997822c19010d6e68b1280c9034434e0c99bab77b859d77862180985973a2885fb2386572c6e157efbddf36ab566fe06c7a25e4b8b30b59ae26510d58e79892069152b94c192852c1202f05a4e9686a4ae0189241dc594d81f168006b55b4f419aa10b66e86b601e35a28c244a4122b3574f12e07cca0970c32bbcdd6c329d5c5d75fbfdfe6432994e716f6328918cd26aad3a1a8d7ffcf1c7ddabee7cb1180d8724e4f258e032eb4ab9da68b60e0eee3fb87ff0e8e1fd870fefd7ab15ddb890c9c087ae6db55b5a8958b3259efe63c12cb95188035d8aba816638a319cf7e4670e69ef02e7b360db1000265e55812e1f81780b95d37a2bf8de7dfacf0569110f45cdf5578e5df7bbf570a438c3e09edc19a9dab69d40499530a6340025d44c471a92b4eff1144958c6a838e885374cac53c93bcdcc66a992f14bc0888e654c4d539ea740ded8d4524515645fc4b7dc2a407985e53241e663dacbaec6d3bfdfeb3c3c38b8bf3c1a07f76727c767c329d4c568bc58ce575b582825a78412af47addf97c66c1acdd46810ba40b39ddcc512c8641582a556aedadf6cedefec1c397f6f6f74ae562bd5a7eb0bf57afd420c804ba710601693d9fb1bb01e4ed90c736f511c4dd155ef22a631075f953c1696e98ac74f00248f394c6acf9cfd85602aae84d504b959d8b216caa03ba16a67fe3fbad0f87f9a04052c556138f28720330a81697502c165535963e6926b21b0d6c54a4267f7a706e7808ceba0d44b6a17b8d2850b112d1e70df0e6bc2f305117af0b400e0bc9eae61d0683427d8f227173d9e54cb72a6573f9fe70707a71717c7a727a76f6f1c74f9e3e7b825b5b13064ec6b3e1b0285bd19d57766b53d66ee9427f99c572aea81a55acd6857c416b748e55bb62b7c964b1c9d97c996729cce7cb955ab55eab54aaedf6d6abafbe86837ce3b5d77676b7ec7b5eef80c24a0d420e579036412be6dfaeeda883c900c7701be3e0c83b4befa407366ab4441a6e633e3b5c1b11d9a06ebff1dbce54f2ad8f47bac749499d90cde91369384796713d3873c004a1a4284daf26c5cb288074da204d9c0059e766979a4463e5a2fc34b8b6f2da54b85e43397d1d4b172054218b25492237f616c0e9c9c90f7ef0830f7ffce1e9d9e955a7331a0c26e3e16a3e659dc8ade7042c5bad76b73f603586582d30870b797d0fb35e624379fb8e41da5c67f3f95cb3d55ce05017fa1ed6bc16e4b2adb0580a82d28245bcde6aefee7fe5ab5ffdf99fff0bbbbb3ba8aea078830e4328959874eaba8c4e69ef03ced6b0d6d9b4ea6e646f8017c5fad1499f9a3a2f8488f83adc89fc7448a4525d7388689f3ff5132c6a73a6bffff1485f0d59c6e79e6a7a36ee1bff227521c04479672dac985ba9d30b8c200d71c14f06e7ad8694bcc5e816243617f98ef82208e0820124406318661f4b5cda93274f8e6573971ffce847c79f7cb254b8d767a10d725996d705a9f592d98a89359a4dd662b626cc90e96c8ec9980ec511eb81b366a0c58888c07c5e5ac8582814502cb68821b2c32e0561b110ca6c717285427367ff4b5ffbc6e73efff9975f7e696f77b75c0a99c4f061a1a74500e1b5be4872bb1d4e0a91f7bea9e79f04ae451fba04406a9e1b38c3045c63512686db98cf0e0943f91507b340664154f007e605c169ca3a4ddcbf1b0d2be3469948799d200d1141dc2810b9464fc778216ef1d8cc84a8ec26c50dc1440ec26ae9335518536657d9dce9e9c915d0e99c1c1d0d47831ffdf0ddcb8bf3f9743c1b8dd6f35958c8cd2663dd4fa11b6c175811c61a96429c3f411d3e0d2b241ae90dfa90b04fc24adc6b79284243da44b3c4e4f370f0ed14e0f8a014a2e2209b0ff4d5580e373c5a658a8d9dfdfbf75f7ee5e5af7ee5ab5ffcc2e71ab51aab39e62ba7689dd7c96e9323bcb01b697084d23c782bff4c102927014b4bc358b658df6d7037f8dfa6f989709b89b5a916d5b09c613c9ffee0c9385fd0c63369868f6b550d2855f12d076e68b7993b20e1095c132885bfa6208394093aa889286990b08586b4e840c421849ba0f0ea6f4e0be26a757671f9a77ffa2727c7472787cf8f0f9f2d17f37eb76b7763cf8342be1416c7e321b68201cded0e8cd5922d8298634f4110ce9733cc0aff34194f64436676c4d09ca1854c9466768520c08a891ae192cfeade688acb9532ae13ea4296f9aeefb686d3e93c1b648b61b152bbfff8a52f7ff9ab8f1e3ddad9dedededa62bfacfbddedfe5c5781464e17cc7eb217747c9a267d71e4068880235626744aa587e6a787dbe25d4b2ba7f151ab2913941724e1d924e135933409d594f7dc70145268a371540ce0bd5694075235137c8a979d3d9f6a2286a81587842b841b56a64c55d5d511217153cbd5f2fce2fcdd777ff4e77ffee79f7cfcd1e5d9e9743c584c27ba2ea56b78f257789d66a3d5ef0f60c566766646c90e0c7e946249783bb332b34c5c1a466617afd1865ad79fda9505a31f8bfc74eb8b5571b940d66a35267ab110341bcdd97832d5067b9ac917f3e5fa60beac37b79a6dec6ffbf1a3873ffbcd6fbcf6fa6be5b0c41cb0c58b037ba411ebaad62aefe70b4152a961d1e85f3901f8f4f019df0d4469331325a29e5972a3e44df53b21ddb44392f684a6136736ffc98ef8f73e1c68ceb991794b56256ad30004b283712b8eb0d65e54c5c0dd0f60a247991bf49e48236fc035862f264b41d496559498fad66da565127b393d3bf9b5dffcf5dffbd6ef754f8ed7731c1c6bec4c974e30208213b9305d032f85215e0a0ed82e3121611cf6044f385021c38ec3ae50e250a1b1868433172b9729a5b87e75ded807d5394b1b59c23f9c66d0acd55bcd66e7ec425fbb2c17acdbf37566ce025bc038056c995f7be3cd9ffbc57fe3f39fff220e518fead0239b5db05707e51d65d90aae1ca47715a701211146679146f200480b922ebbc08077c7b34a6b8c8db3e1c926ac9d12108925c49f5636ecef80a42180b4b2c6861d71fe6ffdadbf45eae9d51cfd3a99096090666aedf069bc3605ce2e25962423e1dd48c0693847f917801324dc3e053e9d151cacf9dc7832f9de7b3ff8a7bff62bbff73bbfd53b3dcdcca7fa3a9ad5723ed7e2b45c6b6c336c5008f9b46e627eb678cacf81c418e1260b154339403e19390002643031a2eb009cdd64691962dbac504597a9299338a63a8696b19551ebe936394a7062bd643ee86e66b6edd57248b0707c78ba98af4282d0725828e6c547eba435a02fd6fdaa4da2f96b0a77a031096f2e5ca29a2824685709bbe6108144f3cf58b38c97f3deb08d4630a655e39e107e43760740e0753dadb3fd99f19afafee09331939e04e28249a8bd14001b23adfe7576d4f22c32d1573e85897b93304943c2210d4e79a375cfba5409ab747543fa21c07884cce48e4fcfbef5ed6ffd8b3ffad6273ffe60d1eb866b9c50817d2e0be86c3ad1c06895cec911d9137a6158c48ab04cc60b96fc79bfd4b48d16de9376a1e4cc283269295a6b7c6591ae3ae8550e3926684f69441caca657c7abd52a9552b184d98f86c320288661381a8fa7589c9e822d944a95f972552e57efdd7ff4e0e557bff6cd9f7bfbf39f6bb79b819e47b0afa1b4b3d462af2fd20d3495eed0a804984ea77a40138834142936f25b6e0b715d79332f7599533a07d22d501cf3d029e2760be0702321908634605a3bbc81c404c96a6c0492c43a28023e1d49531a9d98a39fa9e57c28210fd2b29152a2a2ebe0156f80532645e92ce998ed3502c793b44318ec89f3fb3ffee81ffee37ff4e7dfffeef8ea12bbcb2ee778989cee39984187bd7845cebef18069d1c23b46032bc10ef04bacb9ea044561582e95aeba9d5beda2229de1e0a66909b61b919b74625729ed922711e829dcc256abcdbabc98cd70885833bb9cc16038d3d3d9acc6212ccbe552169172a57b0f5f7beded77bef2335f7beb8dd76ad50a23638f8fc15d560ea89dc4be0c36e219a87554175ba9a12c95526694804d3c5e0987747603ea91ac21624ee9f572879bb50cd095b5c47f2e5a889ff796ea4e6a80ed0c6fa523f6fca59a210d7895444a92694174192122bc0911455cd731378aeecc0251dec047da9070d26063011ffcf883fffc3fffcfbef3477f34ef75d793c97a3ecd6124ba1ea82b82382c2aca6260e91e4b2ca92a9572861bee1177489b1c0818e48346ab39998c2182542d195867a3c50e33228c63dd8431b6c556064a130cd66c93f54015646ea9300f8b218b3155168bc578ccde48510147a0afc55964b50f277065f68f86bd674f3f393c3a865fa55aad566a763508db87bb223635620d01922a1e17c005f0ac9f551ad10a9c2095882a028ee1ece0c884891728e905fe196536e0155daa04b349679258b0c7e265e167ac743564c2485c3fae83334aba9a808dca3548dabb0d49f54fa149204d93ae68f8280bfeeaeaea1ffec37ff087bfff3babc918a7b726ec5a2fd867c8f1d11bbbff4077cad93564ea16033d508b2b92f1a9ef22c30ab00c061ac302bc39ea60c6b4291a6d9c55ec2b217cb08d6aa5ca46bc5eaf91b56db49c9f4b458256c8791a25b927188d47182b94b0c0d7224cbea0c7e96d8ba3b83328e4827c66359b8c86a30ed0ed97d44e156ed8abda8e00b6fa8039dc9867c65f595a8c5c724c2e3d1875244c0c6455c3d269fc0d888a38f3197305a9dc0b2b094463a08c1a537391097e74a10b5d5e461110d1013f896994f206d2bde4cf72691ae01a73831bd9db40154fdc499946763a97ffe01ffcfddffded7f3eb9baca65b1020c69c6b2c504d3a53e7d478c7130426682889bd5a24ccfb5cff5af7d6d9f5b2aebad0cd55a5563694f844e67b3a0802dca934113354a27bda7994cabdd0ef4f28d11fb590c5a94d68aee8ad34b1114620234e11c7c2f301eeb2521166ae7589f29ce178a783eb1cf512553d205ed6cb1902b8741afd7ffe8c92727e717f0696f6d63b1be0ec24727930870d377bc83a723cd1bda31224a9101a6124b5cc7fb105ca38fa20029c14145d72a6de08eea11c426f8ac174d1a481d22ea9b156e8293898e266251044279c1dd1c1ccf7f4240a39e003ebd962736342c2c66086c257ee3377ffd1ffdbdff62d2bdca2c67f821fe589b197a96610831088db72cc0d7530d15e68279811753bb9e87a7c4c7b0441acd1aebc41cb165d9685677b0d2a4cc4b4e93a4446063d36c3567f339acec095d2df8f44716a7f703e8453934992f045bed6d36bfb3e91c3f88b772b61cc88f604c0ebd5026979b4c26f40f312ae50a66aa253ab3eef73ab3f168dcbb3a7a7e48f3dbdb3bd54a45ad4b0cfe356cca199080061081a539834f12868e20aaa793e21252d6b70d24f4e98486d73e1de06c98442b6a097c449caee8c034d42c3370b112e1dc1c254554eb2700faa3e68dc3a4882c737378432f381250164a23fec9b0d22e9115f7e4e4e85bbffbbbfd6e47efe231fb635435bad6297da76b8b54c4dc1a748b44527a4a29432e7eab151630180c58227557a991210d964551b1c8bec56f0235ceb2daccf6363e29a4169c7563a1d9b4948c71e6732caf243134edbde78a1115ca69b5f47db4ae71d3d670381c708c86ba44a462850eec6731d3d164dcb9ba9a4da7ebc56c34e83ef9e8fd7ff62bfff8777ff7b72fae3a8b0cdb78a69f8461d8ac2f6a1749ac9b9f09201431668421997bf39ab799dcc82670071e396203b2a4c0b32286dcd413996054721dd4f80bda4b4344038beb87cdeda81d3efc008376ad82412c530464fd48c0981b2e917f03318e8fdce555e7d77ff5bf7ef6e18fedaa970651236cd744282e97cb24646df22702cf7a278d4697f7186cd9ac3d1b5ba954b6ccfe00ecc3c9a84211d5554da04ee50af9b05c96f763ad07016f0bbf6c77a2ad74503493d5666971d9399fb23dd23d905a9a0b4141d70365469a2ecc9b41bfcf81138602a397596724185123e6389d32377ad3d1e0fce8d96ffcea3ffbd55ffbf567cf0fcdc332db359ab794b481a4c8bb7f1ba2fed8ffa780c8e2b30369785e631b0d590442c4e0e5aa64f16b6c82668ba9e2cf0a69396e40aa3d7df801309cbd6e17e55a6e0349eb70842d675f2f65112c528eba09a0b408cfe6b33ff9f61f3324f3c988a1c3f82ddc13c0163bc06e18c5a852dc1667d8e2f2f5460134a00b340b1b49ad033202cef6da38d96e26e396e47cdc10a94211e67e79d1c1f91503bdcdcd5c98260026a83bf68b45aa48121d19ddf8309b7396e8449d3854fca86947f720acd77850cc8e790207b4e45e10d3a4a9c5525fedd87e6a915b2fcf8e9ffffa3ffba7bff24ffef1e1f3e72841b66fe6ccc872b04f12524c5d9f7272e9be7be22624a6f3a983ebe0047793498c08afd60d3c2bf02c7fe83f42fdab0797c0c7dbde1d68d775af4be9690dc775e55090141ba192fec9985e75aebefffdefffde6fffd6b8dfd34da98ca36e7c96d1a24999c36a4938e5554cb762e859ff70bfa8ebd44442169331f0ddababf3f373e46491add7ebba52a24010207a8c36cb5e5d9340770d9a45ca5a0494d028bb13965728f5863f5a90c6b540c8ddad96acd8b4452dff36d9878424c7683cc6fe0822a1b7f7bee8b571c859abb317ce1372629b6870d2effee1effeceb7fff85f287694e6d41b5721ca3617a38469d44bbdeb2f86480a4184b15a4936499bcec43349dc0088126265d369ff30f8ff9b09ba881282f1c0af24d21bfe66374c54fd27783f6ffa6040e2b273f567dffffebff8d61f1e7ef47190c3c54ef088baf06596048dd9ba5c6fb2287bc534c84ab50e6e989b235344f8fcf9732c6967679799438222e32a4a384b38924416abf574329e8c475886de0768d5a79329f6a7b57332610b522d5721579c2d5bd5da0a787c09536f57cdb349cfae26ba469d0b71a476f105274da93285a25dae919952b098101976fef48ffff8fdf7dea5e3e9b737a0308927b8a946407db8a507401628597e6a4818eaacbfbb212173c06b5f33c174d9bf02a0f35244dc8a9bd40b1bb571d1687b563553ea7373c1aa8e0e8f9e3dffe4bd1f7e7fd0bd9ae23958aab4f040145162280c34949e660c49c0dad3d0c83db0d5d53ed8844129783835a6e6b09fcbcbcb5eaf0b127b90d9458cb53bc1e392d74547364093d97aae77bfca48694ef2cb1467330c71004dbbdd281602ec8922eb837a8715b2669b1d9b24de3beabae41ed2e253754d1b25e470abd0ea3ed84261369d6596440e8bf77ef4835fffd55ffde0fdf711495d8f95462249ff3460e2bda0627a20127062ab6489cd44f854503babc80461ea95ffd581c9adc3137196942f164aa60ea330a900231690c2465c5046b767f7560d2ece3ef9f0bde9643467d3a04b71cbf96a0e15638607c1a3c956525ab306b440c3a290cdb3cc512bbbca84d97c898d45412ff920a02ceab60d2c6135c30a2fceb0e35aada6278aa3806c91cf505d77ac140b7a2d236bb17c2fd26632612e5fccc124532ae60b9955985de557f3f1e0aa985f07d915de1a1aa91e9d635beb8cbdd107e354242085e8f9274accfa9658da78b95eeacdaff6e47429c855cb8498052a5b60399a8e7b4f3efce0831f61828b5447231d9290127d527e2a3895f47b9dd6f9a4212ab80ee0d5903460e6a49926564e0d463a8fd33622ccfb381614eac540e9a713004e7083cceadd869b0cd3394b93f19e46e0482022920de102177897e3e3c3f77ff483c968a4152d0ebccd89444b1be3c8070763ab6b223396693db1a05bf8d7daa91482fc0a67c29ac0225708ca4158ce156ab572bd5163e14383a874b5cccc67b8b929ab6f361f10c76128b54a2d2c96d02446d96cd4b6b71a958a2ed750099f97cfae6ba562a354ac07f907edd65ea356ca642a18a5db1812acb305f624f424cf3864f5825ceac81323392775569d37e7b76007b35c968aa5b01852a848c15e53d967773c9e308abdeed560d8a38a5cab81d464c1b7276e58d50bc16a46b4719534c3041ce990601805201e61cd46199ae5131a4fd808291dcb67f002e3fe4c80ae92f36704e9f716bd23d3101518581bd0308c76c162d8ffceb7ffe8c9473f5e2c665aadec7b2db934a23e1b470e3c0993cf6329860dbfa1d137cf8acba994ca90b07d29a00a3c4c3e530d8bfbb5da4e1856d7abfd7a6dab5acde8be871551db7830606bbddd6a57cae5159558f309c856d962261b6632d57c76b75ad9ad940e9af576356c95825618b40bf9bd4af951abf9b8dddc0ec366a1500fd87eeb1b681db8c0390e52b7a22293bdb25bd307b393d8fa920db474b260abce682120283ab7581075705627a9b15cf67a9df38bd3c160e0d714a5270312e6592d7d4d97aecd08dc329cdeab934f2c4914424540224a9b0de9d380ea8a616ca3690d6e1af02c10e7558d0fc51bf1b7239ad609a553d90cd5612d68e42d6bffd7c185882b0a12b11c2871c6768e202a8be106c618444c9c1e20ef4428fd93271fffde6ffff37ff1addf1ff4ae16f82734a761437d0c125430902a415311bd7838a8ca264658d48bd7b777b631bdd964c242cac25a2de4766ae1cbbb5b0db2f3f9839d9ded4603e74929111c168345100fd62a655a9a2da678e220b30e325902cc46b1f4d2fefea3bd7d1ce376b5d22a954aebd56ebdf6f2c1bd2d7622c5e27a3e63295dce6699e99c2ca22290dd148d3c5a8611da1741502c5aa520088bc164ac8b3264e902e0f1ea5cb775c951b239875eeb37631f14df78ebedf6d6b67cbc29d3cf3e6886d221a5f219198911d878793a1a4a536154067891955ab19b8464b64f8173430c4f4458c347a918c89b2402fc6464824f3a7ecd4cf8b88e28232b0423a40effb813a828e76a0907c7a7e153907716396c8a562b263a88f164747e76f2dbbff96bcf9e7cb85a2e08f770051abcacbe8d4570055916ef5389ea9e06d425ba9dcfeb9ef87cbed96c8d8763f69285ec62bf5c7c737beb71b35e2f16dad50afeaf592e37c3f0a57b7bbbb5ea41bdbea55733672ab96cb354ac16f2cbd130b798b3a32966b3f572a95dafdcdbd96ed76babc9f8ad478f5ebe77efa58383c7fbfbbbed1656dbaad7e6da172f0aebd556a9f270676f3c1d6149ec6a65fdfa1506fbfa46cfc0eb0a25ff4c927c2e6b37d1a8137e55926956a95486a311be6e8e4debb632ddfdc0b9582abdf3b92f3c78f0484b9b464c40523912c6c4f5a191d7e0da87d1483329e24d5a877b2785372851253e5dacc88a95e10c134fa82c06b2c6647344602984d87841b75fa17f22205144c9d9a4bbcef8b3f2b9055e31ee49d49f343794c7520bb0ec5e9e9ffdce6ffeeaa07b89f13130a276359b52183cd0e552091caab3baf2fbac817e94cba546a3deebf6c6c3416e3edb2d055f7e74ff6bafbdda2e979ab52af1fe4ebbbddb6ab104d60a85dd7afdf1deeee3eded8356e3cd07f75fdadd7debf1c3462ebbe877abf9fc16c495523d2cd64a01f2edb49a9562f18d575e797cff7e81508c1dc374ba5aacc22068d4ab187e13532e1626a33e7e975a8da0502f06d562a112149155f223ea3a53ab62ed6c9364371cf55ad56d07f73a188e98ece55a75777787e876315f502757085e7dfdcd575e7935b027d16eaa2e9d954146eee50638990f81650c6b9a2513b9380721ecdfc05002cf26e09888619c05e8090799a8b13f783af732bc05e7a89a0fa90164d8682299a37436608690bddd7c02b725b8014e705d06e12da114047c6a4fb5ce4ca6b3c56cf65ffeddfff41ffdbdfff762d267a330d7ad03946aace44a6cbdb287c3a3473d60426990650dade8075c72b96aa5520c0a9d8b8b309f6de5726f1fecbdf3caa383fbfbc3e1a85ea9ebdd97fa8eae3699cd46e3b1dedd01134d3dfd3c0e0d8461b17bd57bffbdf7fb971d3604ec0cf6eeed37313eb6cd8dfa45e7727f6f8f5deb7434bdbaea8e2763e6038b1bfb9eb3f3332c120ebaa761be1891ce6407fd41ae184c57ebf3c1b0339aa818c7d96c40335b2c7ba351b1183e7a707fd0ef316c7bf7ee7fef07efb21207657638ebc54c77bc1246144af5afffec2ffd8ffebd7f1f4ae474900653e07ae60c0c47033d12603f15e3f8dbf4021fba548991e91f55934ddbcc4f84848c8ee8a2a0ad586ae1f73f99a55980f2763f23881b7c5e2c44d243123f51560fdab00117c4c89dbbd65316a0d97c717e7cf47ff9dbfff1bb7ffe27cbe990085e77afd85d30ce5fb662bfc3239b44366ae2f073857aa1b05dadeee9e6d3d1743c0ab3994631df2e97efefeee0b1daad46b95aa1966e97ca297c2c0661be58d49566220cbb95ab10e82160b2faea2e5798cde6a3c1603a1d77bbbdf6d616b655ae94178b45e7aa43ba56d1b568165f868aa81103aa371a286b381e63fd4118f67bfde787cf8793f1c56587cd78bf3be863cc8bc564b1ca15f2b55a75395f0ca7f3b3d1b050af355a5b97e79741ad5a288647cf9e2fd66b8227848451a95ccc62826165ffe095ffe97ff01fbef5f65baccb772e6be80720811ea7f349b1a82b9d5e7407dcfaf22ccd505ab572e93c3617089cff0d489010444ce4890de16598e0c6c951c18ecf0e66d026caad3e0320310e6bfa339920904842d2c8a32a9820a3deeff77ef883effd5ffff67fdcbf385dcdc75827f6a13b53a965355930b03cf483296a900a79d6c15250787d7f275ccceb61301e0eebacc4b975bb1c6eb5309ead7aadd66ab5a8aadfd962250b0a453610eb6c5eb70be4b41aeae289fe98be7a45b3eeaf16861d3882cd67faca1b8f8b77c48a5673dd6d309d8c178b79b55c66374de4004fb63744ec68242c8504148871797e7ed9e91c9f9c8cba7db6e65830f276fb57cbec5a21de72d5e90f2ec7c33ed3a9da3c3bbb5882cbe5fac391e232a916ff97afd4ca38fe52a55a6fddfbf7ff27fff3af7cedab8c261dbf53d55a48e8182ad52d3e9f3ace9f6682686553dd4dd04b7decfc9cd07b160013213106aa27b1e0d36e72fbfbbf14a881cf645b77d248232fac7b4d2a28f131e3d1908df09f7efb8f96b3c9d26e632ee847f348c41de533abb08f259754312c31ec8ce81e7e29c84ffb836a10dcdfdb39d86a617abb3b3b848cf90cd16199f50ed3d3ad8276a30ac36802e86110401b05294ee12f65945a0b4ae8dade3ac31a8d4be20f5615223959bff8f051add5b3182e264df0d76c86e50aad148262a118ecdd3b78f0e07ebbd966e57df4e0c1c3470f5b5b6de254bd4d3c9ba984c57bfb7bc403b3d57aaebba7a738645670fa6523487cb126349ecfc02d8bc5ca2ffdf25fda3f3880468afb9411f192ebe589a160c1f679b3ba7b56032f8c6bd86cb0d44d48f0564ba00cb51c9dec88dd040d67a021bc9b63024e6f3c59f456e800457bd16d70e2742201efc46d7c0249099400ba7eefdd1ffcca3ffa07bdce85ed82597f75d18e22e6bdd340cbf8047a75935e64a4673a02d6f5652d9f29cb90b2ec7a4b85a0895df80f11e2f6727a1c987d82ac262832b4a85b5fd4b138603805dbc330f1d55dcc5523444a9a849d3d41e7162953450face5a552bdd968b49aa56a057b2c944238c32ac4dcede7f558d4a1c7166b4d45908d76bbb1bd556d35f2215346365c0cc37abd1e86e1eef62ed3e2a2d339eb758808f26bbdd175edbfb5c1ae0405d9428f1eb676f6ffea5ffbeb7875e441c2d89236203ddbe0ca0bca7022fc4d88f0d78ad5f5ebc3a49143d32e819638219d24a1b4e110dca81bd169a58a41c14e4c6d0c0551f62701949f627f0e3f2543d14719294269c443cd2c707ff4877f707672c896834ed32e7213aca1ee46a311d732e360530c056693cf86b96cbb14bc766f77af5e2de756613ed3ace0b2f275b2e55231b09b777205db5dce46a3214e47834484b15eb32790405adc2589aee94b3e56676b89a56da1976b6125f8435d28d655a19c22508c11074b4cd76e6261ed9dbdedddbd52a5b6cce4162bfd6e058418bcac28932f56ca8d9dadb0562dd5aa8d567d6f7fe7fefd03b6387bfb885c79f5e1c39776772a59e655a698cb94e09ccf2f754d9af0d76e248b5cf45aef41341768d24520d51b6888bde89a8637801ac1eb6edf18ac5e44b9b1102de87a32c1caa9a295c18e0d782dced44a2a2aa8760fedf9b4093223137c3a91a41370645a1a48dc0345f99f0426d2ddc431f3280ba42931886eafab1f0165a8970b63243c02f8d2495e4a14c2beec522ab79a4cf393f15b3bdbaf6d6fdf6fd65fbab7575c2fb71bb5bd9d1d5d8c936dc9bb612a6cadf50d1cc6c88ca2aecc585fe24512b82a7473967dbdc72191b428b3a4eac9cd3c7ece0891834d92ee8e10a1cc04643ec716242885acd4b6d6ebb129b8c04b6f99619dcee1acc3928281a0800b6cb5da3b3b3b6cb4dbad9d9dad2fbef5e6dbf71f85cb753ed08f852c67ba5e2d59ccea71c574653a9d5c753a8c31bd02c4dcc084124861a6b224918684924452ea893413c0b21101692f4d1280b18f1a4a30f689a1c9ea226c6282d4b42a09fea7056bf8ba949f0289a080897dade275319050579ba56d7dafbb228262902cd2d6952028483296a4fafdbe770f71708b4b2c016bccac5aa5f0b59dedd777775be54abbd17ce3d5d7b7db3bc45e61b9aa9fef61c075637d7e341a8f4603b8d93d510502400581fa5d52f917b503374c92b02f9aeef2b51a7b5d1c36b5cac5c8255081bc6ef9b29b5529f0ebbacc1b51681b8b17b368b258c814726b4286bcbeada25d0cb118940b3936ab6110109e568b612d08cb5bcdc61bf7eedfab3760b22ee8a954c64c4ccc77f0818cc8fffd1f7cffecfc82e624b66bc3253380d2c639ade16b401134d4153b753ac22760f34e1c2089763cb7b8b1862876bd75b1e6ce76231159ca18aaf8509e02afead5d28cd2a05b02f44b625a0c70399cd307919aee20dd58bcb8dd294702e9526b1479c0d8f091c21033d9e7cf9fdabb4dedcb2dfa497894cfb39ae9fb2e86559e4f1b61c2393c5a2993d92b16df7970706f77ab58092bd52a5ddbd96e4bda95bd1d219bd717be0b36b341abddd6a598202ce4e5d5f4a3a40a28f59e4bc4b16587104e578b5c4e6c89249688cd9998bafb065bd3252294cbda084eef28d46c31885440bbe6a1e9800c9afc4a3f97a3d55b2fbbc4108bc55cb1c89920b254676d6e542b959d56eba0ddda2e95eeefed2ed60bddf69f0fb029dde1608d4d47c3a3a3233626b27740eac366a44307d36b02c84cd5eb8779702b3419392420da8d0fb79014789f3cad4af65dd4c26eda750013a51cacef1160c9512aae9c264e67d2d2ab2b373bf342b8419ab491667e1b92523e485a56be84a949b8d6b9ea6881d38f1c69a573ae6c0a098d3477cd078541a11d165f6d363fb7bfffdaeece83bdbd3c8e477bd24cafd7c367c85ef0a985c06f29d5ba580c2b952a0d497d70343eb422f9258044224d01c4ee27d0b56e0c44322b3737491559a1f7db0bd593d8737a194d7356130a040daf1a50faa1984c172883029b12c518b97ca5d178f4f24bf77777bef4eaab3b6188ed07852c2bbab9e86c744f2486bc9863c30869dc4c723b1c0c710d44938648040c319165034eef2ad14724f3063c5b32708c43d2969fd31099201df644c2f036f77f0980ed8b9868143e4b13b1c4880e310c2f2fcf2f2f2f5dc5804631a3c735706abade8aebb5f5ae945fbfb2d5fcc6cb8f7fe6e5c76f3cbacf8e83151383c38219aa92eeb72b6a21d4ab64427b08bd8a0ddaae36dfeb0f748ddb8c47f2596c89919146006c0861b4626ab196a9808f2431211399656d0c66ac5b0708742b8b3d260547661148f134e527fa9019db42a606ece20e7bec6aabf6faab2f3fdede2acfa6cd7cb6ae5ec9713119d4ae0c6775f8ece9d1f34f54df34a2f6e4a6ef06ab7503100496b6a85914e4ae312ab481506b72f35e775364f25b7b9abf02434633d2cf0038a777101160059f096c0ba636a2fc46803b004ad6870df1b586d4951b45a9d26b20ff628c483f7bf66cd0edcda613dd97c5702b0a2f20101f7ab812af90cbb1dbbd57afdcab96f6396f3576b61b0c56b918e258ca8483eded4aa95a2c960a04f6383fdd8357b446e02f7e7a5284b410b22a9ac610e8b4c9a1bd0be6431a5959efc198e1da9490ac9b1e69106caaa4bb493fcd6f99dd6c46454c654706ca1a2d15cdfe74ff2142b0e969ef6cb5ebd507edf6fd6ae571bb5d216cc5464c72cd92d57ad8bdfce1f7feacd3b9341be230f79b16e0059034ad7e5a36311acd169328caba80326c9d31c7989c1189aced5a973f156e9b6054f3452cc03b90a696a2854f6d2ce98f43924e6a7916b891bd068ecf6427e3d1274f9e50d3ef4b506ca242369385b054aa56eb3acaa5875bad031c5b3ecbde914d052318867a5106fc596d4ba532f19f6c5a6b5dc010cb8058426d27cca00761c96ee153388ba0ee05e50ec0e8c1237b339b793899583cd75dfb52872bc790ea52ac4c952b28340f65181f2a1119a155111365e25a9c01ac161344d45aa3d1da6aef6d6fbdbabfb78bef0e8246adca52cd6e9cd883add36a3e7df7cfbffbd18f7f3c1a8dd08e847ba113bc0951d36a56a02ec90a499093188e8fc1ad50f6e787973b93f49919a2c9e820a4e35544e5c804a36203c74015e5af0393d2d9c554024fbf0820f5ba4a581a6452cb4b1c92ac27acdc314a1077b18cf6fb3d64b09bdacd0a1945e3d568b56d2f19568ac516417c668de763f711e836632c2cb7d24e564075b92efaa2b7b7a0652248ec51ee46457e61c5a23a0c4e8dfb4a2a7aed7c4d9e44420a0bda1125cb967551f4d641fedd4681b820ea234511077387d1526aa34ed72053ca40f1ae760efabe276087522c379b8d83fdddfb3b5bdbadd61b8486db3bc8ce22500a9974ebf313f624cf47e3f16ab9f005356e59608d0a6e0c1cf88da806640d2342db6f6eea3a81a71dc842ecc884b351d9044ec051c218656282a93ab25951c4d52cb70130ae262ffd8ce01349d324667b036ea86303b6de3930305a69f3f9d96ceaa342ba540ae938f3bf52a9e5f2c17034ca2ce7c17a5529161af59a3abacc06d902c664fb06d91fd685fcb4882ca42d21c1f08c381a14a92302dd91600e0f8056542c8b9cc953119116b6f57362dc2a67723676a65b88ac6b46b0e9be1ab5834924fbd307b62249a8e89cadaa74ad7ffcbe6ae70ad942a95cdedadada25bca8551eecefb59a0d7a8a066c3669964e86c361bfef81aa1a63acbc0729807194ba0e12324e789a33fa2011c9ab692fa1bc48f9a89702c790e09c94267c124c0220221304bc9a1234a1afe0134494e20c3897780849a33729dd0823b0b6228c128a522c9cb7c3910901002b4f2448122226f0b204be88c36e5a591c9f9ccc26f342a06fb6b06c88f3c59ca2fac53a5f0cd9102e968bc960582ce42be50a4b2c7e2d631b86dc724dd8946570757feb02c68c190a40c35a51689201cb81d2e28020f23f8866c10efd93ecb6f240c8261a13d1553f5d7cd0953fb718292fea85746240037a4f9c2d8642aa5559946f14e25ff3b6ab580cb436b3f4c8c65cec20d5965f3ac230f98488aed3f75aa511660af7f4ceeaf16c3e6dd6eab5726d3a9b8f673378768e0ea7c33e336aadbb1d24bdf1884099174052aa449cf69e28610ad2a33374c40577d629fe5062fa11bd9d1d4f874d9f3a446de0378d4700919fedc3581b5f07473b246da4910ed7981858fa26d90d801510653e155884a0635cf4def0427e315fe011e7cbc56c31a7eb0cec743266fae0e9d8e7ca3130d299cc8261d4a86b72e1b6d006cd696891cb2435efa5eee0e16459f298ccb14801d61bedab2800b460eb0598f2039a5e7669c6c9ae83193143a28055335659a3749f64b289b357750d381906addb1c45ae21331929d564808c7ea977847ffa4ea8502d9799438d5afddebd7bd8a14a83e0eae2e2d9b3a776ff8e197f3c046266e0d93b2121b841a4ae6aa9d03d44264f0492cc0a938a37e01a3235d04ebf314177da561ef154f27a7d9a4912495a70bb5da93e8654a98dc51d527e3ac455f414cf7436453a8c6f329e20872c06e1f3b95ef74a01f87a5dd12df0fa56839d07c6a26ad62d0feccc1533ce7a77872e1a6bcfaeb595558bd18600566acd4c814f6ac86eb02799a6301887b6a8f9bc51e884ea22ed994e4467256e3e6a82192bc726befc490ada30622b975294a6214c5c9e538e9032f91909a0c7940c1d4d7b0428574ae5528919550e8bed46933d73ad5ad9dfdd0b0b01f372c452dcebb1204806938c9383b528dc6784f4405b4d13d5e68c031464e93269d7831378c29146287064520a4415366536d52c15419234ecb5ca69a636b464501c12319e76969b1545b48445a43f35c40dad87a321e160101447633db549092e83316397dbeb7686c31eeb040eaa562ed7eb357d156be01cdcfab11ded85c9eb8743f40cb9869a05d13ab258ce5d5daa21b1fd028aeae29a20908392d5eb9b3108b02c713690a0069e6648ccdae42365be5aa9895f8db7b528323352336c738da4742015f2e8cb45888d5287e5e4bcf5b75e556bd860381b8dd9eadfdbdfc5f39d9f9d1281a83bb4b55c96c31281b31a8b4572900826c46784a42e8904e0c0d9780b9c617276b0926b603373c3cdcf1b1324e3d8cf221d945271ccc82a45877d31a9f92ab4d1a1dca86ca52b58de96d54aaa6f129f06eb750b68b698556c60195538193f06ca57d515d85658de6ae8c6146a402911a2404d5fdaaaaef6f4b2347932fd8e21a5f6ac31fa2196d44bb4646d08ec56ab7a989d75d9f36ad01892f0eec8ce520a11990148ac140c13c62acbd0f890f1c96d18b1fe38cc4639ac9c88d43e04461ba9b160f7d9ebf28cbc7c48db6c3e56f3057a21509e4e2765bdaa55eb802b87da6a3e16c99211a4b35e0a44f9eb902ef2b483631cacad1752028e04d26920ed05e90e87238471a6694011d4f77384ba0eaa204e9e130d948c5f92bf5dd1314ef622b6808ab2d9d974dab9bab24dadb8e1ca3818b5c9685456745468d6ebdbf57aa35ac5cc74071d1ec3d8baebc3f06085f96242daa9b05d9eeb6d55722cc4d8f2402b4a659ed6242d8053c20e97c13b679fd1252a2a7a1f8d249a150e924f4f57062e069530422b8103f5a44a7aa2eed85a2f1335469697c5aa9629749df129a7b6691723c4cf958ac5fc2a33ee0f8b41b1de68203912552aa5c5627e7c7c341d4f6850e67c174888184c244194fface0824948cf7f0ad057f843699d8eb5946c478c8561aef3322a01e91bcd38f206389d4ef6e94c3d204bc0f9704e1842c84039d231807188092289739797978438419180a720af90cdb0e0d62a15b6a634dda8569a954aa518626cf6d22a35218838208cccc5cc46fb0938a20f3de42623d08fb762b4a80aced8a455e124326a2086272448364b485ef4b7061a9ee0c06dd1b331284ddbb48bb560db4e6f2245c291515216ec621a524c44e82ade28daf441ef4cf87cb9546936ea6cca59926bb56aa3de2895cae3d138affb206be3e1783cd17b989c311525cc0bc0db7520fb2994ea560cca4b7e2b8821c2c7949e8e581b7307cfea57263d4f7f9c5a5af7441a50637c68ac523ab90108c3a11585438b8a4298b8d9e833112b010aa0cc16f2acd3bab3c62e51dc00a4e05f3f8c3e99d8972075cca3a0179f15f42aadcc1adc2b0f1f666d19c5f9acfcfdb96ace82bde5823000f362e9d37d44ebc53ab75e64e6baa0a3bbf3b5fe2d167844dd84a78b2d66bf5454a73d2145c855cf2693b9fd802c8e73365542942ea1cd7523244d1f74b98738500ff82551a32944aa317af7b8aa95b840f2262b4181c86d7a109292864cea230ccd2ac8ab562a5b8d165d9d0cbbf9ec7a399bb10830c1568bb95e0d02337b34573c91c4e0b6f21d12bc27a0f46c1a1c696cd2a572f444d488e9164299271c60e8a0ef0300f969199b2b63b18a4dd0d8ded12afc364530751e2fe8461a4408259f1b7b8dea7b4ae7182c27b89d8d5231b49aed878f1e57ab353d0769dbe16eb78741d02b0671d0efd124ce09071586da8b30a81a367a61093e3594768545d2a905d6f18d0f062be9a4c5481bf456153c63347ec68eb158eadb9a28bfea671ae50c8109ef4d582f8cab9ba77202699216a918d11867527010bd512a9830e7aa527066acd0bb6366e2d54aa5eed9d9e5d1d16a32a914834a2964b33f998ca12904b25478bb28e210f7eb36400f449914381e88b2b730c88b9044a212de1b8bc9ac1420294be5888cd486c14b22137480c54d01654431908cb09f11684687ae676a16525b2b8804b5e52c69edbab8778035afbed51af5b7de79673018f4fb7d30fa46ad5038b87fbf5aad4ec6c3cb8b4b3d0f64cf5e10f1d11f1985cc4d211d23295188f56c9f6882c81ba1be05d664232dc122b7a1f17669494a429319b2446ce50d3c0b9ec63a9d0ee26155609242fb50cab86b0c20b6d66da363ad00461fa9194a1677acda911b2b04eca4f0468f06e4083ff2cb45e7f078391e05a8a85c664dc14d330f9127f6b612c23e04aa1f43847a31243488a1748a8fcb0602f1d0621ae9649e0648ea70635811d3cfa753bd874e91b757033e8b3411980c3f057d0c3685cd1990963c4aeb30713f05bc2dce532d81b3e1684417f137bac9aa5cee75bb0c5488f599af0fece123d6c769f4e3336a82025d9e29eaab02f868415b63a3ba04c8388170dba22032b2d40c0183994876b312793b336dd783839b0804fe5e5e4f1b4478ceaa233b66425aafd95f681b14b18d2bc8f021163d42db4b0d5d66477ac25951849c5b5badbded76bd54c8b2e0cfa7cc4ae2e1951efec04c1563d0fba8720c66131bb0766f905c037a07388d13039eb6723d3561169f62eb13c5801c2d268d9a4fd0a4f29e5ef3820e892c5e39ad688388e90d103686081503988407eb07431f8da76cd183a23b20aa9002e42e164b07070f88bb89bed90fce994c33b61d4b34cdfac746a41404aeaee55a4f83338caac850e60b6465598caa2ee830467a159f1403c86147ae0850a9bdf71c52b2deba27a0c5040b793765b25e2880005bdddddd6d369b92c09ca81a8f41111d0ad0c37a4a78553507a36826c06141052bb1810095f28e3a5333de89e70b813db19767263e38b8776f7fd7eecacd6fb5b76af55aa3d920e3553e058cabc09aba43ed80134499ebe0b528655e3aa163d4a518a40d532f69e783c5ea2d75b63a45bd5555ca9c88ff6b1c6e811539af049c8343844a213759f137540a36a52970a4439245bfd54ab51496f09c4463f3d9b4542c96c3503bcad5b25cd2fb29f54ca66e3366c819278d019d97a18b816c91fe6b75d61722b20a7827d118cad22e0043d0f763aa61ea94559266d4259975c0cc489a508f62000963739a76253906b345759c3375e0104d0d8176e5363efea9abdf46af5d3909436a8c556c7cc4c3cc7d315fe886473d83a731ae96caebf9b25aae50bcbfbfbfbbb3133768e32905980e0c5c30c0d309c60a7f0240063763b801b7189077f24968bd22e5361551fb0d2f6835e1850b59d87315425e6be81ac02b4a7d06b853b2cf04a63e6f2b1fe8de3efb5d8f19beb456ad6eb7b7586618836aa9522a108c976b951ad99c9ea6b8362f8d570ebb6260e5c7e448b01a44926d68cdb3c89881c72e306214a42147663b0c446a3b5799a7f2c6cd216ac20ae0400e9c2db56e52024a74d2ca28dbdf2ceab26d6607ae9ccdb08070809a5e8bacf18f16442a82410300322bf42de9dd2084a15429160262db7aad5ed6db73d49cf3303e96bc0ed6da5d052f00f5fa6ef0e53e2a95aa62a08a6af9d940bd602a1a2a324134e407204676854c9e0325df6e31c6241c3f1d6098e20f4705617618774d090c414f1bd9259c9b0469f5604193d974349dd03063b777708fe1d50558bdc83f988cc6ac41a56a457e0e17c850256ae7239b67bb81eb9185a8d06e67d5034a7829f5452bacdee3a14d80af74326212608a7a9a096120012b41b48fd67d1e8c5fe4f674b98e86540d87696a9298281143f161d6bf1e800acd0fc84b03ea3ff2d88cb0788106c3d572ad1b2a2c74908d58372897d870b4853bd057db19b6bde572050ae46605184f86162904c54a4962e87b3e2a6bc8ff1b0113e60e5747f7edddcad1dd4d9a66f6ce131376d3b8576404944edf299366ca990128d92fd87a29e00d0b8c5dc4540d99b9c784118d4184328850824d9a6a96f5c33140c4cb111cd64004d8c6ceee4e7babad1ee772bd3e5b91ab72a984e531efd99aecefed6145c4c67a1b9f74ef77a9402eb3501d7660de5395dade9c31d25ba9b133c69f83004b87c4b3a84b74b69b16075927d157a0fbbad65ac175c78a8c47618d8bece25a23249575255128948d9159b8f4af1413266f910386c8daa4436f17094b61c2073f289fe051a08d1ae13f498a3136bf0ad56a36994288da6ab7b77777abb53a6dd8380112cc53ced050d7c628012700a27c0a405a27efaa68482fe4401bf3852e341869d49cca0c12d6308c4c10488a9d1430316eca015e0c5207747749f499e04efe09907582848c0168b7dacd56933483c4dc66105aad56a55c9a8c47843f958a5edccbbe9911b3cda840d5a53b31472db69a2987e9294864c00b854c9027b1ce16b25883de3fa38488f494780498a02fc09821e34e1066cb281b02ddfd2f9bb26b3150d8772b9614a8a12869d732977a02d56c1144bec0422a8bc7ac0b455823358106be830e625b51d3d235f2bb99e3030d6bf77543c3161862e293a101ad34b7b64be50a06ee96a7fea734ed4caea16e815ab90ed6a4aa98fa048e77884a5307da403551f18b6163826948b8df68c6e18ed2eb54913406d0008e896143e01c92ec0df0d234303c8d66f3fefd07689f0ed22e46a07b06c7e3d57cd66e34e42c743d82729c1b6718cb55c0ca16052d9a5a2bb0129c5cb46ee4f3c51053a000e432939d13c3a11a6da8d95b4025b7641e4b5692d732aa270138162b624199a4e6a59a92a375f393ddb1cb91a999b73326d6499592358f4b18a04795f1152cec5816732a7a55b63010dbf4967ad413555675e1d448d6d657bdef416f8c9dca0447ddabee60340e6b0d4c3baa21f3d5419504287811dc2855e3069e45b1189667a14c1701495d9072d836008e7911dc3441632820edec38277c6f83ca6c4e90241bd7ddd03b4664118d7dc6e0a5499a329f61d78afc441a7436cb76bed96a637fb3b95e42cfc84fc61336b0611054cb6558b050b11eb171c102186a0e0dad1c88ada6981ae326e3cbeb9c579c07810cb310608872ac76070dda25a2c2e0b28562265f5ce770517a9b2e18d5cd074461a2d49b6beced6f1899308ac310527b0bf63401187bbf0cd2d8a3e92cbb32bb2cee8160b048c5d902ab4380482a45a8f900cbd703e91a20d977a23369d0bf024519342d371ac07b3018b04743758421b546a3a86752cb745e86e715537adfe8f62ef051f034646493ba49e20638b774a9a7d3186bf38e462313bc5146cd74e53440271f72fdb00a2ab57a409496cb31702cf8eb649b263c8b186e8226adc0ca8c02d04aa8bd2a0b318a26cd142324af56148c17f3057f4396b695da6dd82122faa89650aa04909f531abb59eb490c96d350691665ce050bc8e4bdf046199c1c66871dacb0180cb48021ea4136bde7202866698985d20c71c5c16821111e228052875c14d3c07627701a4fe7bdc1583f3e0737b342737e1061794c182c2980bf0cd176367a49053c152ca92f74818901687a102068eae34c1500d0fdfe403fc152ad565bcdf6fec1fdd6f60e31a22b4d15a5f99b1eeb4e808cb39325c48ebc01c62c325087a820a6f773c2e406787512779ba083b1bda3ed3450d12c21aa2eaea42d17a50dd2e9046e6300bc37f89bed5ace78e8373509b47134c43fd858c15fa7b45ab55b4d1ca146c60cdd572ed781d9817aca90617c1a780549488ec1b26be153ce49fe93a44cd87e08c4b8608e66438a14c9404c11487793e01969ddbaa72d33a51489896c454cfc8b10b953f948e2cb1c3e588665ccf4fc9e2e68fa9ed1a6878ccf24636ec14432b07aa313dc7e912d0b6e4fc5f4ca38fbd712210b315b126c92a0108f1896cb25a665a4decd1078e227029409f8409048ce0ee9741a5e84ff14884c70039fca42260948fbd70ea9c9b4c3a1ace6ae14e4135435ee62eb450e64131a4f44058050cac2117678bb46ad5e2c97602e6787c3cae7eb954ab35623a7af402cf6a2ca4adbb2f5921d80c94375ccd31f01d620b37ee9ab3c3c9ceedc4744b300a600fb53bd5f15274491d6475dd8d08dd320187a3a496fad9f66975a3d3963b83836e683d99003ea55552227f6aeb9b0582a95cb44087a7a45fe0ff1707fb87376df32563ac92a8e42599a29d215f265f403b3ecd675d5a650e4b0875ed4559ad6e25f28d4eb8dad769b848242a604ad40af5674d39194e79ef4d628240a7648d249e276158774ad34885d8a096b9a2f6b378a0c0c493f3c27356b880dfde286e5f0cc9d383859c2575d8c3129a47f6e4a13700ce059aa5ccbc704f4c3ae47988c14b2ddb33ba2e78b7991b8a75ea7935451e82b8b977121a8fd028e782088be09d1ebff4a0c177e0b13b2d7f62f2793b1bd89c6e54560bd18c9eff7c0bbe17638c0eafe41eba5d429dbd0e568b1b6a88d0468ce528ec96e2e4e1105a069e25fc42bda33cb413e7caabdb94656afa96b46a552fe355f26a351af73391a0eaf3a9db3d3b38bf3f391fd30bbf6de4160f789d9b72f5430088b614d33907673ecaf1bed6d8212154424fa574754270295a620c21a38c614728dde3109904d20c1782201d78957f784d3581bde50fcd3374f075a1d24b10a7424dd4b83b1da40c2f136d0b6a914b1220cc4c93901cf19592499da95a8d11a4a663c1e1d1e3eb717417b496634ecffd11f7e6b399be92d6ff91c3be276adbabfdd6ac91c6915cf63dfa368bcb18042a952cb15c255ae305f67268bc585ed197bc3c178a27b0af4fbbf7ab1bdec880ae8019b340feb1b61f9526c0e4fa62bd088606ad584b55b638877958d4378754496b4328310807765fa5522d9a37e32643199b0a1621f359d4c276ce9d9d463739717177cf47b5ddd0b34185c5c760e8f8f8f0e8f4e4e4ffbc3210b3e5e1309f0d5b011574998633a1d1d1d1e9e9ce825f195fa3b3ff3b3ed7bf770e7a64e29d266630449d2c58b3231440371ab288d071c994082491719a1000da02599440a9fc811d9d0ef1fea0c78d63368f566538288f25380f93d1c0e9bf54694ff2ca091960d48387d6c9a41fcf178c82c274d29ddb9ba3cfd3ffe47ffdb93274f6a0c8956bee56b07070fb75bdbcd867ac66c5aae59dfe80b7b8df92ad39f8c4f2f3a8c2883cd4897427dadac374a13f6653264abd55ab3d10c8a41b954a6da542fceafd8eb41f40c14826194ac722c858ca5bedbd3ecd61f02e1176993b6802490256284c6fd2bb599072caa2cacb4389b4e27f65bc3c3a17eae18cbd4b547fd8812bcd7a7a7a714359ad5c170389dcdd9bb5c5d7526e351afdbc3a8ef1d1cbcfcd24b8f1f3c6a35ebf2a00c9064c8cda6f36f7fe7dbdffdc1bb41a3d57cf0d2bff93ffc1f6f3d7c449c614e40ef6990edfbc8a12ed3aceb59f2d988df06ba98943a71920099240cbd21b8018e37fd69ba3a12b03475353f6e9a60b428599ada7731a63022be13a88d122f2f2ff77677192547228a73bd53569fa32250827fb99854151d9a469e5aaffaddcedffedfffefdeffde7777ea755d445eafefb75b8f77daed46ddbe9a636d5be2c2b49a86a5efbdffde77de7df76a309c2f5776974359eb2bcb5518620ad572b9d5c07075efe772bec0346be5ca6438c409d61bad8ade5bd0dada6a97c232768b9c0aee72793ac8aec85c9169d7243761f5cdb4064f7b6e707117745f16bba0cc623e3b3f3f1f4f2678c48f3f797a7a72c63ea2bdd5a2f27c3a659d9d4ca6979d8bb052c231e2d566b359582cae174cc211cbb0acb35adbdfdbfdd23befbcfaf825a6917495c9ce66f3f77ffcfe9ffde087d9526de7b5b77ee9bfffef367676834c61854357dc6adb9a0464bbb22197cdcf09785653cc08d2a5e9b14bd210783a8df1449a3e012b55891712d744726082e8ccd3d86cc4d40e0730096b08a3cf6b2024f5b4143abd4550b4e55cbcfa0db13c633180a58c4c752d9dc22976b7b9036e35190ffe6fffe7ffd3efffc6afef359b856c06a37bb8d57ee560bf59ade88b0ac64aafb9cc1482f0f8f2f29fffe1b70e2f3b63ddc387c758b3594452b31e1aca35ea0d967282b36aa57ad5b9644daf576b448ea1debb1b564b957aadd26854f56afe4a73a7d5a856ab25fbde4c57ffd8fad8be597fa90ee25ee5faf4904ade8d4fa52b457887c747dd5ef7e8e4f8ecf212b7dc1d0ca88b35233566b4982f3060dd83b19cd17c77302452c823e77259cce5100c13a40ba542feab9ffbfc5ffb8bbf4c5f6805e6d3d91413fcf69f7d6f5d2c3ffad2d77ef1dffe1f34b777d884afd81a1193b0e9463ae81ca442db841bdc18970511aa36eb913bf75207a7492881a414641a0f78d16da463ec2c82c52a8e059ff4524dc50055c2402d6cd87949fa4877316a984e2aa1a480e6d3ae7803ced64e69b96d5ca314a520a9cc018665ae737ef1bdef7c8748d02e7564c25c76a7d96449657f40aca6b03d2cb1dafdceb7bef5ecf47caeefec5638b9d16434b1ebb744812486e349a7dfeb0dc738c8cb7effac7b355dae7ad309967135e853c4ea385b2e9e1f1d3d3d3cbabcbaea5e756052af55598961c9bf248eb733a4916d6def7e512f24b86d4a7453aa3e3a9dce938f3e3abfb8383c39ee8d479d5effb2df1bcca6c409676c3bae3a57e36167303ceb75afd88810aeea37a60816257a66b9500ca84b34bab4b3dbdebab7bba70ba2bed75f678e4f4f4ece2fc6cbd5bd575e7fe5f35f2cd8bb901548618268d16709b96448e4b8238503ae734f3878360d77221deeac7227bd2375f64296aa683bd2574f844c843220ed07953669a7bc068904a976dd99c97acc0159ab4adf3c22696c1ca3590244b488c338a2c31cf647212b21a04790fef80fbfa537440b9fa907e15eab8957601d9415ea426ff1c71f3f79ef830f71246c182b7a9644afd6a2b26e6be1c336ba6c9c174b4c7332d6fd5f73f813a151cafec4cc7d7d7e7939d10f8c71cc4f4f8ff0c0352dd6e5a5aef6680b8d6d41889b21ab50c12e7b4766c93e4611a0de3c43087878f4fcd9b367b3f92c5f2c9c752ebabd9efc1d72b0a759ad98149a15cc91c502375ecf878d6cee5ea5b25fafbebcb3f5b0dd7aede18376b5727f7777ab5ed74d31a552abdda4456602d128fb95b38b8b65507ae3ebdf7cfcf6dbe801e973da042390742965ea148db00da506ddc18b387b228104e32cae951924049ee6ecfc3d9b40824912a438411a2dafbff7dc03568156643e62467782ea47c90d38ab7815dd8066608c4b8448830cc3966f455189af164a8e45f6a7e6600c5ecf2911bd1f3dffe4fff0bff95fcfaf2e32eb0506f1b8bdf5c683fbedad265ac5520b45bdabf98ffff43befbeff7e365fd8dbd9d9dfdf67d8d8829e773a27e7670c3f1b5eeca63f187527e34cbe403dc61e23c22670a4d55c0eabc568b11f06bbccbe26b3ae8741bb527df5c1a32f7de52bf576532fe155c5355e17d111d03aa398553a94f8528adf33c1aee2397bd6e3d3f3cec56c317d7e7488e9616fa3c572c672ae1f4b9cce4763fd327cb1f8d5af7cb1925987abc5837bfbecc38a41c844ac6fb5b4132e04e3f1e4e2f212eff8f65b6f8741c09e9ef9f6f1c71ffdfeb7bfbd686cfd77ffbdffd9ab5ffa3ac23085f29a678b1911aa2ee0479a47c30e5aa60c73635028e29c464aff64416848540a80492840123d6a943e03c4f5c41545455ef0d960d3a45af854fb4b20dda057771e8adb362d89a33a9c64d345063766575c8a8a72bee52b10bd31a258a5be9b80d96a3e9f1e1f3e3b7efe74369d109db5caa5ed56532f4bd555402a6ac5d21727cbc5e3070f1edfdbe7d86b37ef6d6fdfdbd97ab0bbf3687bfbd5dddd837275bb18eeb75a2fefeb270c1be512a35db6655d9b99cc7ace0a98cb62246ccb5bb5d256b5b49ecdd8b2ecdfdb2b9575339bae8dd39677009da1b768e2e82a22bb22bd856a3e9b8c87674787ddcbcbf3936390f572b1922f64d87767b3fbf5c656a984a13d6a341fd66b9f7b78ffcbafbcfce6fd7bfbf5da76bdce26a951afb71b8d063b76808f469dbd3a4e8ee9b4bdbd1bb0a9d24fd62d079deed3c3c36cadf9859ffbf9d6564b17b819075b8b19675ca2bcb4abd5121affd8048108991a773009a00437602996841dfcdda8eee79f08111967f32f1b2f48016960e30f5300fe5a0396c66539d24b3993b48549cd7891975a5104093e01c738cda696767264d6382b5ddec8adf584486e9dd78fafaf7bbdabdfff8d5ffdd5bff777475797ac8cafeeefbdf3eaabe54a683fdd91d7c635837d14badd6ea5a4b75e284493ca0b0cc87231eb753a8bf138d0b7716c2a8a41a95408cb848cfde1906df2683226d09b4cc764babd01b15cab562be7b3fa0a6cbe78f4e8f15b6fbf13562a6cab830287b8e06b15d6d0b456792d7fbae35411204bfaa477d53d393a1cf6fa580c8be3b0df1d0dc708586673532e87e57a00ab5291759dee1703bd3429bb588dfa6c47d8c30cc34ab0b5bd5d6677542ac9db140afa3d8b4c06c35cad175366c83a7b717cfaebbff5fbf3edfdbff91ffc870f5e79cc3e4813183f28d3d3bda3b21e030dbafda62e5bf764243c4c57d82ac62290856d62778d0db2a5c73176fb1bb831b23706348118a3d2c8ec48250bb106cb53d7017c42ec2c74362419f05e6470dd051a240437f069f0a2888fdc0816a34d1da725cbed6a49203f1b8faa4179676767b69abdffbd3ff995ffd7ffe3e97b3f6c556baf3fbcffc6a3c7a162c1029bd45c80ffd38326847062b95a4ce753f8e12faeba97837e179bc6c1b4b7b78bdadee6ca2c76a5f25a5fafe9371aa6e35121bb2ee07133ebb3339abd64471cea4bbcd56436a9d6ea8d462b2c57ca656dc0b1172c29afafce6830c004b5b1cd66b03cdad5bd8b44a3a321de7aa5b791742fcecf30db5abd59a8d472e5ea325f182d979de190bd2e11a1dded97ab5442f61af72aa5ea6addb9383fb938c3525b4d351a1215341a6cb60a41112320b24533eb7cd0bfeafdfd5ff967f9472ffdbbff8bff55a952eb764eb1ce7aa35dae3751a6be7f894d509f59ed7c93dd6132281a24196c447b6d4452640e6434a90dbc0284699a178d788c51e98a69e074bf7fa8d6857e81093a6c985a6b4adb911401723602ce51db49d14f05b6d7d357fa5426a279ff3bdf7dff8ffe74311a120c1dbcf6fa1b3ff3453cd46ffe97ff9f3ffacd5f6f84a5cfbffcca2b0f1f0561a04b37ab8cd6bff902eba02afe4f937e860d4cd8470f7add40df32d718a762a3d9998c4fbadd4526339c4ccae52a2b5e2928b6ebf56639cc4f8795cc32cce5f48dc57856af137356d6b83979877c292c5761a2dfac0b8ae552312c93c01f4ad6a55e8d359b4d56cb290bf762aa8766f1ada7878778d906d657adcf32d961aef049a77bdc1d3e3b3e3aeb7666ab8534bfceb7b6f7f4ab74b9e52badca2bad466d8da39b5d752e6b8d5a6b6bab5cc5306b344e73b836c2055d7ccd05e3e9f4bff8affec1e3affccc5ff9b7fe9d77bffd9dc5c549893dcd8347f7bffad56cb3810fb45b272248c69af30d13910946c90852237b8d924c6482d044b89b3469b855640e9a6026de113b567043821b1031bace2e9d894bf4f1e9c647e9a748ac3b06d65ae1584cfaa7cfbfff6bffac7478d41a5fe57b9797874f7a97a7957c76351ebdff831fb004be74f060776b4b5e412b89f62bf3055b5a0b1c591017733db339c319b114163385dc7095fde0ece2bb4f9fbf7b7cfeeed3c30f0f4f3e3a3e7f7272fafed3e73f7e7ef8e4f8f4b4d3eff4facbd9a459d60f258a31ab9459b3966e5df0d3e428eae78909cc74af0c48f9601c209dd657cdfa09109a660e100b9e1c1d2de7cbad9ddd767b9b65f4e9d9c5b77ef8fe27fde12797973ffaf18f07ec8e27ecc5c70490f7efbffcb3bff8575a7bf77ff0de873f7cf7bdab6eefdeee0e3e9be670bc8a3e59f18300dde94d2239da2740418fcb93e74f5be5f2fce470f5f18fee67e63bb4ddedadd933ededea3d3a668289d95d33acf428dc1a100844630934eb099d9d362ee29c4ea4c1073a5d4493a8d430da3619304d0c402752a6eb2448cf260086020e23172848888e0df1d220e9b68311df01c2c2369b99cdc6fd7e9779c2667272f8349c8f5a61e1a0163ec68a9e7c70f23bbf357cffbdd26ac15eb55aad3021b522312878f7f51aefa580900508ac6e90ca948a5ac6a68bf5d3abe1ef7ff0e4fbe7fde0de4baf7cf91b8b4c7e301a2c177a5bcd60d8bfec5c1e9f77a685d29f3e39faedefbefff4b49bc906add656ad5a5f2e96e3c1683a1caf660b16d8396beb7ca600d52ef6d805223469b73ad0f012bb9fd99370ab6eb74f487f70ff6077673793cb7ffcfce84f3ef828b3b5f7b5bff4d7eda7c897d51aeb7aa9b05e54f41387f9fdfbfb5ffc99affdd25ffdef6c1fbcfcf4fcea879f3c5d86c5f6ee9eee87cc64022d0d7a1b1d7b265dfc93fe99acf956a536387c5a1bf71e6d35dbcd264bf5e8ecb07f72945fe6f2fa71660de20d7024ff44af7e5c1ba1ebd0e974ec175f22608c54459b1e3171a4f3bc0122be0eb6c489057f92ec4e9080d7d9ddc600b73137c02563f6f8044ae0b6584e09c806b1a4b57e53eef2aa83655d3c7d9e994c702d2ca70c55bb5661c3b8550caacb55231f68f82a65c6dc2696dc1581b61eed60bdc46368ddcc2d978bc170707c39f8e1c7cfdfffe4a8dcdcf9f9bff66ffef2bff537770e1eb05f0d56eb301fdcbfffb0c1ea8c1f9dcf3ff7d52fffd25fffebf946ebdbefbdf7f1b3231c4eabca06b581979de8baf60437abd7a8cc17047f3e6e7ae5a52949d38751b1ed08bd22f067fa3d7ae595edbdfbabd9f2930f9ff4a68b2ffefc2ffc8d7fe7bfb7b5b7d5bbbc28178af7f6ef576a8dcc7cd52c95bff4b9cfd309d6d7975e7fedaffcf5bff1b9b7bf787cd6fdc3ef7eefc3c367848c537bb167215b08b385523eb0cbce72fbe80d2584ecd60f0eea5b5bf972e5acd399cc6741b9a41bd860284b036cb5378b412dd2760426bd1dca5c07c369cb92fca65284b722bbf61e41427c03123c09f82878b00661112dc49f74e11249e0d4b0f30a9cd370039390a5b1268a121196ac9a15584e825eafb1612b86668490866150add772cbf9f3effc69633c6e944b73dcce6cd26837c26a5936962f1c9e1c57eab5fb07f7b58ba4ae0c5daf8c36edd2ac98cde7f38bf3f30f3f79f2fef1e96495fbdceb9fff8bbff0971ebef6fa2a28cd46931ffcd11f146633c6322806abc1b0b8d42f5bfff25ffe6bdffcdacf3cb87780031f5f5c4c3a57f56a65fffebd4aa5d2bdbaa22dddac90cdea379cc2d0a340193c9300a78049e827aaa75807d8d168c4027afffe836ea777fafc30572cbdf6b56f3cfedc971a8d76763a79f2bdef2e2f2fd7b3c96870559ccfea95f23a0cb7b6f70eb6daa5ccaa552e36837c230c9e7ffcd1c5f131616ebd52251cc4a1b302ab7bea203ad3d7dfa3d118ed3c7ae9253a32e8f6179369b1566b7feef3f9fd7be9d99fa8da3e6d206011e3f94f08d200b2cce63dd497930948c9f69e1d5651123ebe7702a549c2d36a56ed260b31e3a63f03cb26a49f11e86472c0c1fa2c4e6986c6dd73666ad721552ae9583af58d3ffbd0c5323f9deed4aab54a95a1ef0d86c72767ba05a1540a6b150699d943e77577805ab4bb19b4a46431d18018289bc1689e1f1e9e1e9f6417eb9ffd99afffcc17bfb01586b56ca6b45cbc727070ffdec3f57416cca68bcbb34a6655ca661f1e3c78f5c1fd6aa1f8ea2baffe95bff6d7befe8d6f6e6d6f5f75d9cc9e371bd843c0a69b9516dd69055e2c32faa1433b7441c6fc0d8e8184d4a1108cc1430622d1fd970edefefa97f61f3d620b93cfe6f77776bef1e52f6364c1a48f0fdc2ae426dddeb0db7b656faf3e9995c7a3e262dcae063ffbd52ffcc2d7bff2f2fe1eb21163e85a0a12b0d7d2fd685a616802d5b55a2df6f58b4c76a0170b8ef1d9f556bb1096311149a3c92988954c224efbe9fa88904d6348c3c13156b229fa14cb830c092190a0b13945d561684ce2ca296b932f7931787d8708e566970665fd108d9f3f05ac4e8a26cafb0cd1af342ce77336a47a87119bde4c7edc1b5d9c5dcea7445dc45f41b5da604780d8d6095bf4972b7bc436abe5389f0f8b41b351dfdfd97ebcb35d592df26c0fae4eb39341a5b0aa548a3ffb17fec2567b3b58afc2d5aa98c9d49b8d9fffa55faa6e35e799b97e673097ddde6a7dfef39f7bf995978783c1d9e9a95da3c4f838130ae93eecd5824559af1b4450751b7de86aae3a822ccc0d3e46fd0122e235e78361eff0283f1e6bb755ad7ef19b7fe1b5773e5f29d7aa854ab5d47878ff95bffa97ffc6f6f66e4e1778d6ab6e2fd31f2e0743365b5ffdea57def9dc5b7b7b3bbad18c4e311d6d71f1d1a5a12a5b96bc6e6a1cf5c7b8c7b0180661796ab7c3e6158fc805b88201d25e31cabf006c2444e9694726b5bcd41c85664342904052d1c191498232ec33de11eb360515243c5e241cf80436d914dec91cd2192f4ad324a2008edc08a7788ab4e67b6e3efbe4cffea455c897c2ca6c36eff7ae6c7d853e5b28049f1c1e369b5b5bdb3b728158876e2b6555d21fe3adf07da5bb06b75aed56a3497ed8eb1656cb6645f705660b8493e57b7b7bfa66255fdcdade7ff0f2cb3ff7cbbffcb3bff88b41b9889fc35016a727a3b393f974389d8eb17382bf011c307c1ad06b0ff44cbbae08ea77acf5cc2f123303d80c73b2483d339f6acb510acb8a21faa3c1f9e5f0ec62361ab256d3c576b385008de6d683072fbdf1d65bdff8a55f7efd8b5fcc14d6f3e960dded2c2ecf9738cfce259656a9d7ab8d7ac8a6b854ca178a32f2e8d7a3e4deb045c2bc8bf34b181633ebd970381c8cb2f57aeee14bc5d6b646c2548b86110650c6000df3275f1d43bad4010c1581c4e1e1168c8d2839db7c8fd2561e01596a79227de6d35c8cda8d4d9058900f93933a4eea904eff544035af694d7e36266e82464f4a75e46f56c73ffa416936ad371aa3c9b0d7efe10ac34a69b6980561f1d9e949b9dad86e6fb1f831e48cbf2adbef3129765eeb0275a554c6f802dba0f4fbbd5eef0a9f545ce58bfab6241396c3478f1fbefedadb9ffbfc97defec2e7df78e74d7c068b7de7c927dd8f9f2ccfcf338b293168a952dadddf9bcd665797978d7acde73dde1791b1c452952021b05e32770cd81ee931f80cce9175596fe26db5ecd790114fdff56426d359b7bb9a4d5bf5eabd3d16e7478f5e7a546bd516d3f1f0f8d9f4e47078f86c74757edebbc81472db5b5b44a8ba1c1d0474095b6090382c848fac0a47d7bdea359bf55250980c07b8e6cac141f1f12bf95a03224c16e19d524a8ec15d0f4245e3f482918acae252ecde1db067192d379b04e3e059c703e9f4423faa8f83496d47dc5e34f006cac470230b787b51c6000ce734d226179f505aee2e1a80ce2418dc1b69cfe6b4c594925948fa47870474bb5bede96cd6bfbab26fb0f2d3c9942e9c5e750aa5f2f6f68e99e03aab2f66d911ebd7fc75d38ceede63d7a29b4ce933bb668d7f26dbedf70f4f8ea6c3fee8e27c7c7931eff596e37e905984f8ccf1787c76367cfeb43c1d97d6cb5258a8b75b0455d5660b266767a7985dabd9725561d51c61a55a087181f64daa5d98a457ba4f5f8e507dd1de19cf985d63400b3dacae1f00ad86c5dc62361df6a6ddab45bf97998eaf8e8ffac727dda74fae9e7cb8e875e7f8bf7e9799b345fc50ade9cd0a38f762a87d0fe3afcb813e68d190a1bbcee545abd92ce95ddcf9b05ac96dede41f3cca96ca90e9cae0752b89d2f1f7bf9c92a23b212925a12ba349d68e1b467903d20643c2413ed5af6439364978da130e37b2778268c460036e7096503a958d1249c51be0c4589eea11db6573b57b076b5c5a2e6bbf38a7e559d789572bc26e0c5d46acc84b338ad002397c7312e14d5fb248bba6d0de6ab3f0bdfacacb18c6e9e9d1b38f7efce4cffeecf87bdf3ffcdef73b3ffe607a7cd4fbf187a3a74f6b995c29972fea4b39da2b606598d3e5f9e5643429ebe13d3c9d4485b36c90086cb1d43da788c59f2ec210a722a706450401bb9dc97c3cc584aab51a81df7cb91a8e27abf932331865880e9f3f1f1c1f779f3eeb3f7fbeea75579331c689b40fee3f7cf8e061a5a46f0261e7bd93f3308373708d491abbb7dc12f4b9c0ccd615693d6b221c34ba50104f72405883683c62cd0351fe0520d5dbea1ff1315630a7a2e35f0411bd3524394c98cd5ec64a376239983cd7c0f14e19a10c84b9bef0df00274ba701cfaa61032fd25921fd129da24be2e979b9bcaed5fbb3a96e53b051670474fb48bdc12249e3b804baaeef2e326b3dd28b1542879f80910c59375c81c7344b61a9d16c54ca2576a3afbff2f2bdbdad76bbb6b7d7de6eb1e32ee6d78bcc72965bcdd904c987e50b9566ab54afe78bc5f1683ce85eb1a2b3239689c5c1906c5d9a5fcae7cba323b92e42112b22039fb831eae4f59b616b42c97c50a8361bad9d9d4abd11d66af52df857d9fd6cedefec3dd8dfdedfb9fff8e1fd470f0fee1f6cefee6cedeed41bad72b5666fe7d0e64c9b525b7f95b0218f34663d4518df67a28a051f81beb35119c21948dd062e7c94790138cd0d000f9f24219e967588302f806ba546cfe7c60413b846f762b841e3b51200037b6b45e034092473c8b34e0f24738802e95b5be26c900b56c552a6d9eacdf4981b034c084854cef0111be9de0e16264df7826eff8429e380abd4ea4203fa3906468616cae50a86883bac542a8d7a7d6f677b6f77fbe183fb6fbefde6fefdfdbd833d1c6495806dabd9deddaeb61a75acf360af582fb3fc4dc763a232ddbaac3785e417f66a6f896d7d509ba93e469dd1bca06964d3bd2da54a45be500f8daeb3c542a9d5a8b65ba546bdd46e736c3f785066bfb4bfd7d8dfadb49b8d9ded3ac15fa3ae27824b61502e21836ec6a119db8aa1062ccd3aaae5d59c9fdb9ea23268582432c562a156cd14f4101336a2b5c39ef576c5f82213b944eb824b0d783a8d71483098759256224ebb3c9e062215a5c63781a8bac972b38220425c83b8950ddcc600d7e48ed311db581a80214cac50d33406af4e110969477ad2bd14955a6359ad2d8aa599ee50c60b1619d172a5d6dede612fcc60cfa9a2ef93759d646e77c7e8bb0a7dc5a06732581c2962fb6b6f199021d69b8d872fbfd4dadedebdb7cf7e1a375395b3a997752f40bbbebd55dddd2ad66bb95251cf1d2f17fdcef97c34d0ebac73ec42a4687caa8f44bc226ada606c1602f8049089a804142287eca18ae6ba745a2e1718a25958b958a905e56a9e6d116b74a341688bdf0deab562ad9aaf84c8c0de0a278afc7854ec1ade3244c0763e4a9862690ad7ebbe10b1f5ca9b50afbfc166b136b58af2b48f31e964cad1e8bb9c9e36d9b49a9348c08b8074fa062494eab241c2d6b346b501cf23f4c6042388264954c12b0330f736dc74003051590c09bd13dc8074119ec9d389d0491a9006014d6702416c70d96cb532adf6a25a99afd6ec7ff50aa2bc7e0db6a19b385ba3c964655785cd29da3c6771241893af6091b488cd400fcee98d060075dbedadedd6f64e8d8d2a3e0f17d8925b0a58f5ca152601660d132c6e3a1a0caece8b1a535ad0bbac6188908480c3e1b03f18d8d74dae7059a23eec4547bacf274f82f55c0f7e004c8ce57cb69a4fe916f2e97a1d4b74a89f6ac0dbb1872fd71b585eb152658b13942bb920cce6d8b5e7f43dacabc88796c3d609ce1a3176d8b4c7664cef01538b785c96855cc8a69ff920c56af0dc719a2408cb01dee6ce06a0c1043947f914a4919e567f23d834e1795342ac946ba0f0dc29bcfec6049d99f52d8234c704ac5604b74bd318d28c16096b5870232dee069e8e0a0c84d1dc45abd27018961bbbbb447f93d59a608c310d8a15bd856da13bb286c31141b7662e1e0bc3951fd583a4312b3ddf8dd1603ffe1513bb4a76976646384ec23c206481a5ef08c1ca3e57a4a707205979179361fff21cdf522844c2ab53b0e52f97eff7fad3e9d41ca175476a94e2d5534d5a24b24b7732109f09f6c20edc0c7b96c50cffba5a2c881e314d8a544b1652d083040c8de9c1409b6c66170a914707d6327a25ec6c4e0ef7bf9c8c278c3a0d8b4f50ca16432937a212b89e9da97521e703e6a524a89e3888343e49033e64d74134b44275caa48a7858ad54105556a31247282b8d4cd05d1f1516443986b0e326443ce2b4271ce43262071e9df917dc9438c2a6202a88eb2620dd7262ecb2b976ad51686e4dc28075362cb160b68ae51256d4acb76613769bcbf99a00481dd1e5266b74a11f295e1543bd8d144efa129784967e71d55041675b890ce7a5de703067a7aa9ee85ddbec1d66bdcef0fc94dd87fda818c6a34d0ada865c92e6b2fb07f7f7f6eec113e698b9eb1553a348468c97c35d63dc745771178e493777c962d8cd2c17eb2556389f13e3da977b88045e0220027f3e816948fb47ea159927b44a3dcd005d4cd1f541c6900459893fd51dd4ea2edb91a032d763082c23b051c7255d0a60ac4be72eb48106c3c892acca0c48271811a58ac0c3478731b4285334492969d3b6729a446b3a002d54f44eafbb31d04cd54f41e23ce8393c1263808527acdd8d7c40922621c7619b44913a31cd460d6fc0d10919e069e793e061650360ba969c199c5fb5d95e07c5e96aa50d45bd1e96b560956b55e61403469ca458d0245767ec290eb5a5af2ff43d32524632693889cf2ca92f5dd7f6cbc46c2251e032874f5a2ff3cbf9ca5e628021863828422b24615cf5c221b35ac98a4deb9d5d34240fe57d5967c262b1c216dec8d491cc1a8f8750fa43e354b1a7ded455aa316f1914b629b32926a449309f83c7d962ef24a032d9c51ca045cec649a0eb8d9081cf6517ba2c34a34f50a3b8bc1eaed23b714c2390441c40dc8034364d00ff24415f28b23623f022205d45ac6e65137acb694495750e9a4106b25fa333a28885a737fc8054c369a0a2455a7a95ceb51a5296d4a1b31dd1c94072d807154cb00d98a5c89e105f2c28cee7ab8d06c33b5df9709abc19bd7c03fde049b012e5d5bad984390604d084d2ab36642ef06495d18248160bd225461c8cad796b16c75956c774351dcdfa9d69bf935b2d4a0455529b5c289c2295e8ed83ba0b964800e9c0337b7525d03a85487a04bdc0626ac68a407ac4ce0c5f1181966324018f2fb00eea7977ddf7809c748d34dbb5f902d1e53e75f55620bd9866bc23ea954c5fba6122a1e3997ef746d35b7890ba10a43766235e4aeb11583fa4733f7b5e89145813aa69851681c4e0c409c10da0305d11703c28cd0549285648ce29ea98b3a12c890344071f0e332388a55051c2c4a8af83185fef03b5e8bc0ed254b783a51aaf00c60fd1c8873367cd1ca226b46e782bd647780084d8c552ad8ec363737a7ef8fcfcf9b3cef151663a9e4d46ddee95dc0156a7455be1086470c3a9ab3eba13d348650b5668dd75a7e15fb17d5e2c0af8a4c53ccb0a30994cfabdc9a0871516b37a6f01ece81b2e933900178dbef921784ee733dad4d71e3273d655ddae00579080141cfb7b8c06db21406391349fa51273eef282506026d814084d1c89a82ff7e8832a6b106245a48c002ba417f4c59b806aa655187d8c67b8521045bd7b5343ac066001a12863308ddc05d7c922a06907d2370810cc13e06f330589a85e457d6060a431758a3ffab459289d14f0ac8a4d4c51cafe44a1843c13b429707a034b0a3c8bc4420a9d2ae23f621839488556d69cac90c81b8e74c64a05c60a0ceea3dc6ab3cb180c7a17478767cf9f1d7ff264391e2ea6d3d168646d316a18b17ece538b9dcd22108c1360fab3462ce462a7415719305ce872365b4e67b3f168311967e6b3c27a1dea7272c8be120ee633318c2506a958257a613a405b3074c5a108ceba42295333a9694bad33192c9853a7d41fa911421f17f4ed4114da92579deb466bcd1c531d42d21cf362341c21320d80a46199b883f3b4304021b18595d2277c4bba228884364698b7c0ea0848a6b3808f6482f4b61ca3fab1793899786e40f4fec1bf210c15031c1c8cbbe411f3782c3626e81009e0266210b59002e394026fd520a28821c2c2cdd8595aeb9e0e8664add7d9627129896db76429098b6add3350801273b9727b7b1d96c78be97034ecf5ba478787c747cf0bd9ccd56567369de29172ec38f3325f35a21ee67d0bc9a868600c5023be4c3e480b349f98a36eb2b2355a9b16820a0d23b5105937c11054e9c663449bcd6867aa6b6d728bb8433593d7fb0b6d79d51bd2a56009a05e6be678d3916e4c059ea4a7d8212dbafbc38a54d3cc171b53886432626ed4c0289966c65be03c9013af2c5edac82fcfcfcff54455a96457a8d6baad554fdadb622e8fac8b3b4c1fcef1e1826cc0841390c6d0e9a9dbbaf523c20349c2f549a9434473dd3ea2b2189c0620e518eb80b5e7d4c64187ca6f5617c415afc19d2d45e7c8fec846ac5de311f35b4da45945542eb12057ac35cadb5b93c53aa854770eeeb5b6b73cb01a0cfa63f425de90e358d81de30d74e5c3daa46d15c07ca10d8559b6991e160073c68c8a41b15828dafdcf7a38484ba7dc1746262f98c5107148c3e1101c63af88454664ebb26672ecd9641e32142a2a58b41f12f305da4d470450cad4b477b6b48a404781a25d63b7782fa7edb45ee55bacd56b2406a3110220067d712f8cd89c194644655ab6b7dac849b759820bfa9137535eac43529ad7caea04d2f1768eccda4179bbfcce992c5d766472be0d8e1765c43522d380db10880513365ddbb84626988058c8468c8ff2461683f11597349f34b8ac0e2e8768350dedca810e4af88fc82292cd3211e19d8fae7ea9290e2bd2f212145b5bf34cbeb6b757dedddf7ef8f0d16b6fdcdbdf65682eae3ab6e4c12f2fdfa1f597b1d0baaceb7c6229262aa2107324bc33535043592cac28df8841eba6d85cb15ad66b792b954218acb0a77c50289686e329beaa5cae6295b8412c537724ea2dd3f6927e999d7757fe121b0a74574b81968852e778684c4a362aabd5359c9c5e732d49cc05a893f2840a678901f27a5b934c466609048572b54225ec5433d306c0ad904fba36d44bbab2ecd8f245d6df4c360857392692b1d078667403a322395b229c83a4d554a1a2c4b0a9ab320374926c0c1cd49cb9aa049252cf92a07380227b42971c8aa021b5e8cd6900a86297eb1d102e3241fa986e0c708ed771481a15a8f1eb1051c41513b8517a1b24bb718456d3c4b420d545e078f59f047159b1d15c04c57cb9acbb758362b559dfbbb7870378f6fcf968328217ab985d66b31d49148f925e8df56bea735befdc0cb054993699b05406618464cd99b1f9c0d3902e14191b5dface665998cae54a602ffb870716a6e788d932db4f396025d23e26a462fef1a779fd9c983937c98f3436185a28cd85d1a04e74503db6f0c4c649df6d682761fae64f0a541ab764d1825dc4562ff2dadcd9d5c793b38b6c2128d76b39e652315805854ca1b8b2775fab933628aa6017c0695c0db9e2ad288258f19472369d47d96b64069088438cbf8b4010652030ff034f1c827a8d81ea61c77821be5d3f059b22d317bceda0522c22906eec457043a6040ca9009190c96e3c4558b5611db406cd68b4f0b1296e6f07adad5926339a4c7abdfe74466496678a9f9c9d5e75bbd0782d2a30c0da1b5a9f01ec127708370642832fa720bcd986fc013b6ead7cbab7a080ade3b5148515c27c582a96abb3b92e3d96f49a7fb9300c8ed5982a8cb71e7c2a875824466097a3b54c6328b2c2f8579910c93c3296a4c6cd166d36689ba54012220e32a6090e6d5f988fe64b64a500f5f46180e466c91a11f62027e717b8490209452010b30ae70bb1d96f940e4b300eca3a360da90132e904a4d590c2e8c86080b4c93819049045a8ebd5014dcb981548f4e0bdda707452ab12d5216747049b162dc52925c335d8d489c1ba7c93dcdb022ced38fbfa2166ec455ed1d31af552b5b2b337d2f588258386654df5ab88fa867e381eeb3209bab25a0596b3a08075eaede062a1158bddec683cc6ee183a733f7278be3433eab840965d0ec64fdfcfda775cf9b05c2895a6f3b9ae7c9630b562be806f53d428cf8c4861117748cce6dff261383e600093d685b7b426863acc3aa52555174d3067c440625d8db16f96c972561d6daef5e3141cf68b88d08850d30c657808abf265af7f75717ec65e24082bc4817ab35818d296beae81372b2dcec69a46980424498c49d29253ad6c90b7c18b4ca20d8dd51038de219d06c831cd7c7d576f854a99e00d6a03309aab14a95404a9b6c426298cea4a1628520225003e4d0944c43124a5f6e1cd89c6313ebd702aec1acaedb6c669b5c269b0ea02e877b9589d9d9db16d44d994ceb5d8d8aa9acf61ad7ab3b81ee858e30b3126e2413115475d14f1e8441712b13fdacc13de0539fc9ff912e27a2a4ea613bdb7a3e826a81f6ed04bd2e70b7b65cc78d0eb0d86fdc97834d785e9099b06802414740187c9f28c4466805a0eb13c59bc79477552d666174a1910dba3c816d198edcaa1802a0a613524744c0a9209925dad2ece4ef402a4f9f293b3cb8bd13c5bac65c3326c1580535517a7d1966685abd49d1909c724450e462aed4101de8ba8e2b51c127a98240907d25e9a3e03d6880facf720991d0609dd4d5095189cbd12a21778d6209dbb5ea27a486fe2450c00686ef4dc31ac5922031dd3aa92561337950ccb629640309b9debfb287d9db8bbbd532c04b3d9b4dfef11f051158f4883b0d742bbd4ed5e1667d15ffd980786045b5aa64c1ff2a0f64c2e048497619989aa2b395a6ef5b33618f162318733d397e537d42b85b043ec72a5d7e2777b835e7f38e84d46c3c96884d96182f40590092eb05e84d59d31b339e1837631d6964535f28b7acc85963821a3d401b5afc5f6882e4a51d6cdc5d4c5a4c3f7d37188b0d9617f787874bcc866bff7f127bff6edef7eeff9452f13cc8b253ca8192f5d45731beb4920d1bf9b97864723c5748889ad14649c8bc0b3004509c66a93b54e5915c72400862205446ad78402934886049e80c8e48a9bb1caced4b80ae7a23b226e7c239f83e80cd25912a2b58ab7c18b1262b4c1998c3054d1b0692cc24a593746572b10cf18e09c1eeda996c2c56cca404d27f8b83915b41e9b9f582c162855a36b42e20de54ab160d667bb15404cad1bd4b2889d2d8feeafa6596c916543b13b349246d7e7c6a3f1f9f9c5b3c3a34f8e0e8fcececf2eceba9dcb61af3f1e0e717f9331b63a393bbf78f747effdf0873f3c3b3e5e4ce7a89dd6bddbdec7a8536a10ff87ab5381624264d3573b08246d30218925348335a322b07965d78ba8b8c85c5c74a0aeb79a57a3c9c978faeed1d9c9703a2fe0bf59876dec20346e49eb92c315ebcdf88844785381ed786c1a3069f58d6844e3e07562866e3c31db0d996392e6483a8fd8c05daef809ba27bd848ee170eda8d8c1db53ce6c934145119194066a86c35b307a4fa485701ea429b4b3c04b3d91ce2a0d3f1396ca643571b0436b653c9e66a7f3e9d939ab2f7158580c9e1d3eef0d270acbf44b4ca5c0be2af56b6ef49881d59518d66b3866b36c2bd933308f14fad90e91169879f4087f480b142ced3dbe983ad2e06b11e4eaec7c319ee0232e2e2f4fcece4ececf3ef8f8c96436f327886957cb3cb1e67a7979d5f9f3effee0cfbefb5d59fa625165b3623f86485bbacacd2acce1aa509758fa756fad4d6bbcb036c23edfacafba648d5b0047506ba20ad0bff9ad4c7f3478faec3976bfbbb75b6eb42ec7f3e3f3eedefec1e3575fa9e9068ebc9eecb78b5baec644db0ea43d0b3e2670e46620e4184d1e4b683602b20d7dcafeec53401ac1e016236112b5621f3a8981ba670c24950fb2414467f5631611c63f120c43c5aa61320b48309c9e76f07a2e8d43420c78d26a090c770d9276650bd6acb7cd993dbc7e45a95259d7ab8b6280a72a6573ec042b8d7a500ea6cbd9c5d5656f38d4a5174278bf0a6d2d12fae9fab059959c2229d4ed9b538afddb0b6b5722add9106bef61796dd1b1e576bb3d9e4d7b83012be9eeeececb0f1f7ef9ad77de7cf99572b1d8bfeab204eb8b3ea2c3d1b8556bbcf9fa6b9f7febedd71fbfd42a5733849e33449b61791a45f76dec6614a7eac62dda2552c40772d8958bc84a901ced98cb33fb34d99489cff3d974d4ef4dc7e3c57cdea8d43ef7fa9b957c10160bb3cca2582a99e168c861ef7625959a7a1d90c2470124ba324d44c364e624db7032a797059afc5664d66c6ed24b01a74f124e23760666bf1669a8671bd898a0537bfd04c40f8ccd1ee7464ed2c5f239b512867130fd486b9e55698a2df804e3e78412704c0ad45b3e1c8fb65899ca95fa3c0ceb2f3dced4ea7a5ff83c53cc1573ab25c76c3ac304c7b39934ca98067a23fa7832e5d0b5bd556646f8a88701704aba1599588db625108e091529494dfd580dddc6a2968bf97aa9af6e5b5badf6cef678ca2644bfbcb0d56a7dfeedb75e7ef0e0e0e0fed6d6162e1631d9cf97c352bbd97aebcd37fee22ffec2ab8f1fb7d8a5e6728bc9143b267ca43baca02ccb667705a608826180acf56028f58414c2bf5309d4777d9065fec4d6d31f0e3bfd1ec102bac7c9ef6db75f7bb0bfdfac366aa5a23f726086cb094e1a925b131e86803525f02c6808135a2f8a32a9014206ced29ee70d40ba6d781527760e6612a2216f1f116cec268198fa1a1d605d40aec822235124b3c0480449455799a701f05ecb093ced4580236f808ba1890641d2533d235c59ac0acbe64ef1f5b766dbf7fa9962452f0cc8add86b2c16bdabde703062ab8270cc0586d91e2196c32380804c61046c5817b526fa5dcddee5b8836605ec6f38e6b3c97c3a669f8b2d36aab566bd3e9bcd2e586b7bbd4eb7379c8e0ba570e7e0607b7fbfd26cd45acd4aad8a179d2f67a530c08c598459407341413fd36f764347f161ea9702ff7ca9acd7d2c9fe1424300df46c0aa5c8a1d9a08e030a1b5425d61886495f2f3a9dcbab2be20a2967b5a815326f3ddc7df3def6e3ed1ddce902c78f69aa3fa641ce298503ced0917e21d3f18013fa590d1b281383db99830fcd6d1acf264d28abf04fb32ac6a974130b1a56206d58b99f8188b5f41489057869d28ce104a4ddf8d2480747a1cc24eb0987342bce51a302d54b48c1c29fd0fffcaab70acbe1d6ceba5e2b6fb7ce2e4e7bbd2b54cf7eb556667944ab45c87dbb897e194ff69e44633086b905fec65821cb5aeb842ed0487868b5515962ab2c50f3855e99bf607b8b3764492c564a6757578727a79ca7e473b946abd56cb502ddcd50188c46fd41ffecfcf4eab2431f74299b68b556d5465e578ba445eb9a5c6ed4d5b873a9a4bece11896244592322daea2bdba514b3beeaf58e8f4fe6e329928d878383fd839dad9d5231d86a6dd5b6f7c2769b98d76c9b40d098c69cad9508e0e689044f2285f4b3c010318b181ca3b3f5254d60935918370678dac453917aee659a63a958d0ba2988b2b1db0320250b2f868984d5dd341681d54bf009ab34b112a9d8c2916948f0891851d6ae961a52bfd78ba934b71ad94261345b32777a6125dbdeda79f8a8bdbd53abd5c372859051bfeaa19b02b1ab28949e2d97383454c54aea1e0fb418eb99615d0c86bf68f5242e96c5e682751baa85dd45a5c7d4fbd30911df93e3a34f4e4f3e3a3afae4ec7cc2c29a5d9f5d5e5c767bcbb5beb0194fa745ac2d937dfaecd97b1ffef86a3ccc954b384338e2c938180975d2b6083e18968d7a4d7b92c8468eee2aebe60836b31e8d46a880e074369f5f5d5d79a5c168a82f76aaf5abf16c345fb3468c57736da6a42b7de3e43f03762778bb6970cdc7fa171307cb5e83a840bb435d0490151a5014578fc0f180f8a9d04aa3018d1762b2d65c628711b80a1cac53513a81883546e24bc5b50e6c783999a7d3048017391842c410a49b06a269462103b35ed49bb5dded2d2d69ba15859a41bbb155d06fe8677056e7979757dd2e7e4b5fd0c5ad17d8961a035c0bacc0eb728386d9620639271c8d7c8d2e39b30aeb8e54bda6924092f0f2b273f5c1938f3ef8e4e3e7a727ac71f930646d2d954a38a6c3c3a3e3b353da65b3727e7e4ee058ab56cbe5f2d1f1c99ffed99f7dfce4632248181351d2047da16b785775d0ec9e84d2a60afe1d6946436f10500454a7009e98f17038ecf57a8c3a7e9e581529db0f1f85fbf7876169582af772b901fb72b36e39194ef1b7d2a606e36b8d72f6b49fbd88b37c4d6a9d359c201e34318e0f013e05492864aa3837007a69356e8e84aff5e263e53ad9395a883feea10eeb7f0c5ed91939460b685ceea540d401fb0495e06f801a36ca84ada1ef0017370d8ea412d53829ea592e8b4158a994ed75bb33c68890ab7779f5d1bb3face8054685716fc4fe52cfedaed6baffcab9d05bfbe656415841bfce4084c80a8d4a587b63e9b48f9e4c74eb3dace7b359bf3f24f0c3a69f1f1ef60703f639b45ead544b61e9febd7bb54a05ed4ce7d3302856aad5f3cb0bd662d61c85a0cb79b91c62ca9dcb4bb971199e5ef9a595c7b401919656ed9aae8db77ca55d288444ddb52f2d110fb341365d909fce58e527b369a552454e1cefbdb73e57debf3f2de4c6d9cc64912de9650dbb745563a2d9255ea63d53be5a11b856d4e7483d06269ba9db093687678d5392d507a18e32807aa24f63e064113852e397b0327f473a658206a24b01183f7b5192b533aa8a885560de2bd1a5e381281fc36d4c1abc7512c9d9c10a054aea661f4d266655ad51b7375f56ea8dda7c3e79ef07df2fe6b2e53098e85be025311d8eb05c2a630d542022443e53aeeecc633c694f71a25eccefcc75f957b7288f469dabce683c3a3b3f3f3925e83b1b0c87aceced560b63d5eb9456ab6aa57270ef1ef438d2e57c590bb1c5dcd9e939758b6c7d8b795c2c06d7a8d7b11b5c72af7bb598cd03ddd910304e88618e490e9ca557ddda383fbb71cbdfd26437ab2a8a82d4e251ddb84a50319b3131c0cd9773364aed478f837a53d727e528f2dbfbf7f4abe18414ae3ae39a0c8ea1d459cee9e1f6b457718c116e20412689340813a361156152a042a19c2c2aa3cd8d097a35202edd50261c01d2ee17c1b9d02ae2df3e6c8d4b377133ed89a42d87db34492b0e49b99d36ddc37aeced1c8d6229ec8f061fbcfb83d1e5a5ee1728060c07c6a5efeb56ab7eaf5fabd5f022bacfcae62ae11e3dd13d82f61a4ce7cc8832c6382d16d38b8b0b9c201ffd7ebfd3e9200c3cc352118b6779c7c7edeeecb2408f47a3ab5e773818546bb5ab011eb33f9b4e71b518330e8bbae530acd7eb38e6c968dc393fd3f7864c21ed8f747702dd605ec4ba6446cb226d09d573c6348af9626d74f8aa730996e971797909923e8cc66326091d9caeb3bbafbd910dcbf3a5ae3035dbdb078f5e221e55609b06d760a44c8f3505513e061f18a52c91408248954483282631a82c6169d9843ac691b0ac0e69209e192948309e7088c4bd8e27ed78c0315aac37f26c6add86349f17912578ffb4e6c8f90450ca9990271a696c6db7f6f71903461763629db2c78b17cf8f8f598cf055fde180884b0f95696897338d98be6f60c9f485b3d3ed7ef2f4e9c9d919c6379dcd88f709b9186f2c86986fabbd852fc4b7351b8d83fd7db61544a2857c36cc662a612067b49c12749582fc6232d60fdd5858399d4d1115ab223ac45bf6bb57474f9f7ef4c1fbddab4b1a21eac4356aa1b5d0d0baa3fd8a8cccac84843645924fdcfcb1016241b6e9f8da622158cf33f962adbe7d506def96dbdb5b078f1ebef6862eddc72a02ec037e3118122049139c451c1faae7fe38aeeee09834389a7f67b9e1cca7586b3271c45f2f6cda8dc14733f982ae1f31bd83d29a77260e8ef4742c8ab262912ccd3137209d50daf29e764897029e05926c9210a83298c80103de166ee78377df3d7ffab4a8273b276118eace2edd5550acd4aaf80f96477f452e15184f2a7b744c7048f5c3a3a38f3ffe18bfe516d0ed767d784a61d86c3459ec6155ad9471553badadc23a57af545e79f9a59d76bb98cfcdfb8369afb755aded6f6f6f355b90dd3bb8c7269a45178f0b3f5dcdb167f660493487f782b915e9c701bc210205758c498c37b4c01129f5db4a4414820c968778cc0a8a70e7f83f7cf074baa8df7bf8f6cffdc2f6bd87adddadd6ee7e58aea1213492e8e71ac4e315c12d1aec0fe0a4e32e0e09325dea6919001f160f1a3a823bf940a502fa7d231674eac4800c17c18d2c20066e79f18a8c0fc4fc95b69e46c8b8dbe9ac8862869ee09c14197a030981678d5d6282b4bd62aead178be7cf9efdf8dd1f66574be243e23e3c0646d6de6a3f7ffe9cc163f10ba38782d512e6c9c0e25f5837712d8787878459d0e36960cade139bc35eab7ac37585d4767bab56296797ab83dddd9d66f3fedebdad46bbdd6c6eb7da8d5225bf5cb7eaad870f1eeeeeee6e6d6d119efa03ed6542d1b27ec73308b05b2db2385f16f002ce525b8b69582a3153ecc2d002a9f4d507b6897b526020276dc2aeb1bdabab2e127a2cc854c7cde39509a5f6df7cf3c1e7bf5469349960f942510f92a49c932792b4c36d4c025ee4d611a1ae4382bfbb5cee21aa8bd8806574ba014e8ec56f4c908e25059e7851f60624784d5503ad99268167d300b103e984e0ceac83d15ecbea0342bbba6228a573766da57375fec10fbfdfae564aa590b8bdd7d52b4a7126ec2458eb58b8f4bdacfd2489dd682847082fbcc9c9b17e825fdfa1e096f4969910de180a34ad66932aec7cabe5f27a3adfc6c9b1d3268061219f4c678349219b6fb5b7eaed76637b6b6b6737c82beccb2e9718aec2478232fdf461a6ccfe5ddfd4ac4aa5329e8e0d3582d1288211ce62eb384b7c17f22103edda5729520afe5b5761fa83b96eb3c82222462adfa8157b1912157cee0bed472fe3e2b1557a845e120f26dddd06f55a24145bf21ac04215f9d3821cd124f538a786c93f054e2048f29e20e985516e03601c7b73214ee046adbb980854cd2aa609ac177757b9461603e974eb8e0134dd63ffeab049aa67495206b9ca65d9cbbefb27df6617b0984f595ebb575d8c89b12c066c9473ad7a9388ad90670dad69839ccd1674fb73eefcecfce4f878381c30ae411896ca255de999ebd5d6b8348caf58c8b1e036abb5cc749e99ad96e3c97c385ecf165902b9fea07fde994f66b83dec63707935e97497c3d15cbf8d83792d02dd3750cc0578495d6f64f7c1cea952a9d90d87c5c9549e8cde231556c84e9fee60a6ec77109b0472e0bf010c1167a980d27ca37e0f312cd2c4ba52db7eed9ded8307b922268b4aa41429c4548aea6e8f0e19508e758403340261855758af6743a30b962a89f9a4ce7e90e6248846d1cb1ca2dc2d887191df4a43da1ad200de21cadfa2bc917d211893041ce7d26273892b25e2198fc720131a806492f3ea86a10a215daeb5b5bdf7e8617f3c42898d7a9d02bac9ee048b822dee4817fc668be168c44e85b5912202e65e5ff30f77d5c2c355ab88823d519df80f1246a2516f56c27266becacfd799c98c9dce62329be13c67641699c56c7c79313e399b1c9f2f3a57d9e94c2f549b2cd7a3f972301b7507a3c138972b94cb7ab72a93816d33db2024614ba1103397d71c383991f3531ca82b32ec333001353162e33b264ec004b563c2fc4cbcedad3691c655b79f2914f55b4b0516705412ed67d38002394b4d3f25dca848422364e0c8144f92d7f8aba69d3da1e25b6025829b264899fcbb3de643638e4957006e645d2c20c94789bbc0c2ec9bdc886c0c2d00e3acd0fbc5c5459a331f77b31689be7fc5b17cee6b5fabb4db855cc08a429815964b84ed4c655a787ef81cf6837effe9b3a7e64aecb701657cc566b379efe0a05cadb03242301a0e5878618c13aa94ca854c7e70de1d9f771783f162309a5ef5faa767974747fdcbce62aac73ae9c36c38598ca7f6e37330ceb1819d63e75783c979777676353fee849355b3de08c3327e5682e94768b3b3e99cfd354bfc643cee76b5fb76d5e83d4b766f2cbb72823f7d03361e8f86a3f38bcb93d3535d8a2fe4d8f08fe78badfb0faacd5616f7a9301cbf65f5637095260a04c87296c212ad5e2f758224816bb692cf045e25666867efcf8b01a2cd42ccd9597066c8683b2d1c20a9ef92db6183b1fdb83472bdf3118011ce4afcf076b50024053a23401d4fb6c1d8bfa5c5999c62577d2a2506846ab3c56af9e18fdeed9c1c4f8743962df6c053dddfaf208ffd47bbd5c6c2f40b4a61519e339b3d3f3f6780d90bd01c16c00e448bb55e8c1e82c1fec2756ed91b4d2eaea657dde57838ef0ed7c3716e3c590d86d34e37331c053826ad9d76f564ba588d676c37fafdabab8bf3e5684c88c71abc1a4f35f908410b794cb45ca9ccecf587aa37d585eef97c4e8c58d20f8ae83a393304a9d8fce2b0d99f773aba4e8e031f8e27388d209f6309ee0ec6abb0fcd297bfd6baff189bd69e8c085507ca718de99c4ef888a4f10048c73b3201d60d15c764603c01388103541aecbb9ab370fd8510d124df8e60820996b613fb433847028e51ed981248a753b021b254c4cad366a1292067f60728773d01a415e4099df5e9b25991bed15acf57cbe1787c7e767af8e4e3209369b55b43866e34628cd925e07fd8a2b2f8b264af168b9ded36c3ecfb5fec005f88fde160f027e52000b39c69cd1d9c770627e72bb61d8b75bb5e0fb3f95abe582b049cabeb7c799d2d4c66f36e9f383498afe6979df1d9f9e8f8747cde595e0df2f3856e9ac225ae9658e89260a05c5ae9dbba75a11010056a614531ab359b096dbdab55548f6cc3212e6f78d5e9b067ee76bbdab5e80b923504ed56733e9de26187f365a6da7cf885afb4efdd63c3aecd36f1add41c99a0b462e009f0cc02cf3a24e91b48cf26674fa4d39e80a1a32da13172ab35a46202278dc922482a3a25e73b2eca40c119704c0237300923c77b02903d91d55f8417b581e5eff0cd2286db0d6163700ec63b9d50b0ec499bf8ea712157188da78bc5ece4d9d3b55d1c6620711e10b9db23e46791c585eced6e1309e27b60c83601cdb5db6db6ae9847b5585c4e46d9f9727cd5eb9d5daec6934aa1c846786b8b48513f7e54ac56cbf55ab5dea8b5db653000eeab5466c7a397a993ab94eb954a8350328fdd122de231470ae6740798de7aad9b27ec5d1fb4cb168454a95c265668365bcc90d16878767a3a9f2fd84ee91a8c5d4464e12e140b952a01aeae178e17f3d3c168e7a5d7f65f7babda6a33aba41226b7867133636f40da4454c1d252610aa2d2d4372b092f2f4d68182ec02ae9ab2ce11940712565941aa408843488f21b3eb149fef3670bce9e06d2a480e36f236f601c8477e2b8fde8d399df3241515a1c986485f9748056b7de092065a55de7f436d5cc324bb4f4ece987bff7abfff4dddffbad40e1bb1c102ebd1c16dbb57aad5ca2b14707f71eddbf9fd557120bd65cdc62a9582c972a4609bb79effcac5e2a0fbbc3d9785265ff1a84e5227655c9e60a61a554d232ba60d075f7f362196474abac2eb4b22d2ae47567544ef7c1db4566bde900833b3e3eea76afa6cb79b1512d6db7326141df7ee886093dd95964fdd52da8ab975e7ab9582c12ff1d1e1db2105d51653ac32f1216e7f239166a581314362aa54fcece3ebe1afce2dffcb75ffed2cf6cedefb16747716e82abb52ee8dcd4b28d8b275cbd9fae642f558564346348d21ab3c806ae39570087a02295db551e739051590cd470b3bae9055f04eec3d32da5d30e6a31b14bce7cdab13196eb358c9f5010305eb6a3507ac3d853094fab22841e2ad93cf028a513906b4fac72369bf8aa8fde7faf737a2cda25cb1ca653dc6ab647fdc1c307f7efdfdb5fcee7b55a85d58dd84b6febd05761b3a01034cad530d005138205d6e55aa9121602f6a738273df951c8ea56ec62255b2ab1d9c9702e97d7a5d292bd45ad9aadd7d6d5ca1a2b2f57825663512a652ae542b5aad7d3e84b19a2cc39e39123940b0af8d0a0142c660b7a4d0c80ef84a25c2e4d8900d8a42c66e62fb388d26ab5747573b9848a2d486fd0c3cd9e5df64a3b7b8fbff8e5bd070f2b950a426951b08541db23f38977822bd0cf3f0524b5aca62ccbc09d9f3353a8907cb36f18c68d841108844a4184d522f6a910911938c6cc4c12f8f93624a5090155ed883824dc9c4660783e237c7c18c49f1188c6c99c119ffae6c1a27838b0e0326c5bdb3b5ffdc6cfb29db37d7686116eb5db6c8db52f592dfb8341e7eacaf728d82bd540b24dc12bb12072d41bf552bd5ea896739552b18e0d55306f5db85618a71f9558add038119d64585254c8adc2601916b2e56206732c15175801865d2cae0bf905c96ab9b1bf73efd597eebff2d24bafbf7effe103fdf844050bafe0f6586c918404c292c00809520921fabd3e43cc7a6dbd93d3909b5b2eafbabd5926fbf2dbef34b6b6e99a14a8d18e40f77e4b4d9ebb0952879d3d91c08dec0d4897a5292dbdc946e362786153832e8c413a4b82f23b4c30a14bea03d1621f434203a035000c040e51c1ad8613b881a142c23f5ddd40332901af778346575cf4e37242b2ae12d61782e0b5b7df6a6fefe020ab1853a1707e763e24a82ae4fbc3d1b3c3a393b3b3a3e3d3e96c8e2d2e1673dcaa7e989f7da5dde8cf4256a9d5eaed56d8a8e56ae57cad1cd42bacd62cebb86bec90a66848b77f158bf6ea836c9e55502572cfc8c3e29bb1574bb142cf33ab19b4b54af3febdfabdfd72ab596a34cbb53a815da3d1d037817eab0e8bb27d3703e0027bfdde65e712ebbcbcec5c5c5cb0d6132ae04151ddc9c56516e9ee3f2a95eb4c3fefb8a959e069dbf4dc042fbd1312269ebd01aeeea434adffa406230844190327ba31580e2999631304e50d185689a46682f1b42712f05237c108f5628067d25092f534904e0351d6d6f11bccd5640a63841bf91b8d7ab55eabd4ebbb3bdbfab184cc7a3c99e1fdb4d0e7726c992f2e2eb1bc6eafcb7e998ff3f3738a7676f730294cc7befbc206565a8fcbe52ceead985fb1936e9403ccb11cca11ce6138d7db977259bc57b556c56e1054d5ecdab2ddb8bad22e983889a5bd52ca574aeb123eb28853645b046fac073bc7ee5988b5d4aed713b6bef69a54a6014e9ad5594f4fcd26babb3b93198dd96d9f8d66b36c587ef50b5f0cebad6aadce6e867e9bae22ed290075881011dc50efbf1ca05ef8dc52bec09d88b7e209072f8d3256375d9d5e4726e8050069e86e60d2d93480a688b6e539ee88373710a12ca6f444824c12c6700349560446627e7dc32a059b0b3d106013c44ff8a7fb07fb15dd38a8d7cbe40bc17846e8b5184f278b0c66da98e8aefe79abd9c40ebc7b5e5f574c82502f8ed64b10967266416e9a5b4ff29959b9302be7a6f9e5603aec0eba979717ddababde78d0d78dfdc3f1643cd713775359a76d55e41b514ca09bbd32416199674fae2747014cd69586cdd128260892adb11665dd4babfeb35423e4643a65bf727a7efefce850cf2be5f22f7ff18b5b0f1e1788188802ed3b4c64976afcbaa07a11e92a598e3deb6364f4022f4a033451cad20ea49d38c9a621565b4496b075e29bd4b799240b31359382742249633a09f7041c91c627f40e922806b294461903a7c10e1628fdc5e0a4ac7288eb1c41a61b22a9bf1828c2aaeab5ea56bd7eb0b78b098ec6934ea77bd1ed888a628bf96ab55a83a3d1dcded9c61a58ecf49648e7ce7c0e0a727276eb8aeefccb64a6ebf970351d2cc69d69fff9d5e98727cf7ef8f4c77ffca33fffce8fbef7fd1fffe8c9d1d3e3f3e3ab4177341dcfd78bd9124b9f2cd6f32c1b664c302c16ca25bdbc35d60926e85d204d6bf83fbf3d512f439a4d2f3a17484854379d4c900de55ff5aeec46ee097b9df6e397b341492e503f89bdf2151f6b8bced29229c23e632b8c512970491c22540c772213b8aefcb881183c1b01c54671bb08a46a26d705d3b729444ce371153665a002d29675a4da30b0b2085e9435c208689133486627e06917c3d337b311ce20260094b25ccc502fbf5c4e874fbefba78d6270d5eb33b6c5b0c8ca58af94d97a94c39029850960fd73fd429d7ec2696c5f3fb0f70d8322ddc30362f42cc850c2136035841cd4643e7befe38fde7ffaf14727874fce8e9f9d9e1c5e9c9df73a679d8bd17c9a61df1c06e643a7bae88795e852895fa6d0752016dda8bf99ec7834eeb30feaf7b1338299c160a0abe5f6741cb368341af7fafd4ab54acdb3f37366ea74367be72b3fd378f028acb7f7ee1d682f4277635558df2370993d0dcda620264b4a1deecca69151daf1967510d2f68856b801b71bfe48536c640917415233f28214b95e00c7f87cb21eeae1065d9b175b3d6f2bd6d721e18cb2d2e048204943e9e018cef8036f344defe0f2246b3780c49160909305a5b45080d3b0ca81cde503c67bafd53a683757abd998f571b6e8f606b3c59c554ff7ea2de61853500c703cc727a76c73f34130c7676ae39b6193a136ec4533c46d6129b47bfe74fb15a1a2def93c9d5c76f546b7f17cf1fcf4f4e9c9497f36fde0a30fcf2e2f66f6a3b5d9403f1231b5273ff484c96a89d1bbfdb9c1cdf424d20cfe00bd6936982f01f11f76c9c1d684e62aa5b2049d2fc6a309916bb3d5beb773af900daa8d7a582aabf39b5e4b818069177ddac8c5476204aea594ae0449d639382641a6d34032484e69a060204ac64ca20132e3513a19354f086fcfc850d96a09d24d4aec142c0c0caf02b1e64f395992afd16af433805302513e8604034f35140369462885119913bb384a5b964442a61226cd7289e11ceced35cba519cbdb1207b6c8052167563bc23176a31302b8859e3229952a857c61024c27f821166b6c459756647ffa2d636d5f9b8d900d4a2e5bad96dbcd6699407332ae8445f6c2cbf98c6d05468fac48c39e86ddb2bd5243e22020fed00121111587ead762140b5a9c2319cae57aadaebbb600fdeaacae34e1114f4ecf16f305bb96af7df5ab8d7a83ddf4d6ce4ebea8b7e9abb7b7949680e30577d178d6cf880418fad3407ca2a4e076156b2422f1b48363ae81d90f05b1cf4b49e009083c0ba010662709aa50aa9ad495ae9d923a2273a088c1037957b311441c2c91c6009e7530a13686053826cabc10e4fdb5d7d2e458ee6fb7df79e5d5927efe203327a45b2c3986932956385bea9d04781928f13cfd3e2be1402be3a0afe79eecd7b589b45092ee74c51da205fd9cb1ee9d5e4ea7b5306c562a7b5bad56b51c64b3a37ecf6cb550ad55c25205cb65a12c95758588cd105ac1e0249c5e1522f0be5081e97d7874448be86d3419d5eab583837b644fce4e47d349b7df3f3fbfc0adbffdd65b2f3f7e1c94c29d7bf7ca55bd36841efdff02b41ea57e122443e055ec5f8d3b3ecd475d4a2c24058e01a2bc83e5ae992060ab5ec43142c597bca94fdaf1562e94d6654a8d17f84d339c2d91109348d20e11a5c1ed52e036d2f93ba4317c26d928c13642cf042d8bf9dcabf71fecd46b6645f92b0c6cc2a65837559d5d5e223c811a866877444db092827e73b8d0eb0fc6fa5acc5e47a99b20f4f2f8a018623aac8ffbf7eebdfeeaab9f7ffb73bff8733fffb52f7ee9eb5ffad24bf71f84b9fc4eab45f35855b55e2d57ca98200b3dee0d97462809a05e3860e89cc9ba17c4ac71b1263411e7aa566fb6dbed5ebfc71c611e41c3b2dfa857df79eb4d267dbe58ac341b74cfe6ff4d8d25dd07aea96e83df10a4c1b41801d917693eca78c3a9ac4352eb465d871b48f77118f266214e205e576f72019316622313a7ebc27875e04616f05a9ef65220493b1ef06c02b731b760c3c13f592e350974bd72dd6ed45f7bf8a05cc8ce08ffe6332659be18ce972b22ff202cb1cd84105b61656446212326c23e603499b24165af80e1e8facd7c31180c3b575dcc686767e79db73ff7b3dffcd99ded9dfb070fbefef56ffefccffdfc57bffaf5d75e797d77779f1080b09135175b8c3a1b8f97db1c675d083473e44c169bc3ab75aeae26d399858233458cf6d4c1c27eadf3cb5ffcc276bb45088bf059bd0d27be0a9502da8a5231a8e90d9075b40f981f3768eed67352e4ddf18495084826c8046f353690203d1181e56e9ae04da21780b70471d2a4031874cafc46b9ce8ae26b149f0dd262dc10896c02112a86b430c4ffd8619e7d855d1579e5c1fd570ef666e3a1b6b5b3d955b77b7a7e81c767f3516fd49bad2d982950cbebd1e3c168c846613c6269eeb3e5383d3bbbec5c124c62a3f57a1d671a86e54aad51afb7cbb546b5de2a576bfb070f5e7ef5d516219abef12d79cc8718d1d9ae5a636a53fb3d234c13e11577eab93efd3832463f1c0cba5d7c5f9fb68ecfce5699dc703c79faf429a1c12bafbcfce61b6fa05af8e039e9185a30beeae97fb3705bab2f021b819f82384ac54007ac0f89095ac6e98c36725480722940a7aed6170155d0b29309d8eed93a0e904b27d2fc9304a0503245e9e904f36280002aad9b1cb0c35120c972a55f8d6b562b5f7ef3cded5a6db59a4f17baa31943d176643cbdb8b83c654bfbec797fd8efda5dcae71717acbf48ce428cffc32db12602b59aeee79b4ee78d461b43b8ea778b61993085e2f16cc67995cbe2382faff43a1b3d7344a06977a4ead62cbd8058ef42870f42d10a2b3f568515b2444f2713b61d78626d800a014de8e902b6d6b3d9e3070fbef9f5af1783023b12b6d639dda32f5f64f765a9c7f611415aa5d7c0156e4702519622e958e0f8046eb04a98a7e98c22aa0e182e8208751da23220e69d98a02e76c05bb6a96b11debf17824be370230bd87e2e7203b08ae6ac2ffe3f09a8c5e025460c06867ef644824fc0e61336c7b84016e50b368bb42dc688167332bbd5e6d7df78b31a14e6ab79265f58acd678a4a01ce68aa5d16cdee9753123180c0623bda687957ab1ecb231190e75f9663295e33c3df9d18fdecbe5f5ab499836c872a5c49e1a47c536029fca663ac807138c67ba9c4d669d8bcbd17080f9b9f42cbe488ef1616d7490990613bc3276069e9091a01001b0515db35c2e86c341a352fbca17beb4bfbb8b1533bb747f97054fba66a42e4b2f696dbc50c372a1ae185dc1261d6765c976dde41a48c5d7959c0078e938ca19d88d73d6c51b6cee06f872c85518796c820ed6b06d67c531857b61d78074d32c3764e4c662260e5e0aaf28616ca3d42d70260e64fd9c805734a16e7248d3a97b4bbd46c81edc3461b2abc7f7ef7dfea557b2b8abf5b23b1a9d5c76de7f7ef8e4e4ac331c678ba542585e3032857cb5de2040c40fb1300e4693d3b34b76d08460e7e79d4231a8359b4498b48e1b0dcbe5d178848d3283594f111c2b6431650f7bd9b9622a2ee78b7eb73be8f6ba9dce643c0e8bba3bda9cbc66089eb0a35fcfd32d3cabe59a8a9d8b4e87f5783ca47eb773f5c6ebaf1edcbba7b812f74b132b6c5fbd5ce85782a425eb6b0464104b09c3a7b593d0b9d222d5498a882a4d6c601c92d214384690aeae824da95269ca3444657169fa360565a32e7d1a2368eec0339fe4f054942ca3e26672390929616265904e4ced46ad46a381c34868361c0ce310e56f80282362588b3beb25f30c2b2ce47341ae5a2d7fe1cdd7f72bf589057917ddfed1d9e5e145e7b8d359e7f42b7390e21df5a636963d7b932e01195b806aad11566ab920dcd93b00c3b61a97858b85c995fdd244b15c229a53ffd0692ecfb69b9acf9f1f5d5e5e3df9f8c98f3f78fff2fcac73a91bf16b955a582c91d0f2bc5a5d5c5d9e5d9c63eef4947d37dba50f9e7cfcc30fde3b3b3f7be7cd37bffac52f55c230a72ff1e42ed471660991a5e9446fce8a959368044c84f4540c56681c0c1ca5233546425ada3a72139ce636a44b130e2f04a7062c0769648231260dee6fafe1ef2213d02a5e5d6b2efd415bb10cde914d3ac6383291d5b37e06341606d69d0d38d221214e40981472c1a6885518a194d3063960052de4dbadc617de7ebd9ecfafb0027d0b979baf16997caed2a8f70643223956a5d174dce9f74f71652cbe171773bd6e66f0fe071f6122b3f9f2e8e4644c84a77b6327981d0b289bd77576cd069640ad56abb188b319662261871f7cf821cd63a3057b4e80f86fa1b7cce8814c669eee6c00399960eb583c5639cbac2e7b5dbce3fd7b7b8480ed564bdf1de946307de993e3a09f9a57fafa45f9eb1d8f34f5a94600bdaa6ce0d388136e5ecbc18b1826c2094a3cfbd3024c75246f53f8f08a5e992b8b8be4d3d5b4b7bd31971be002796944c30709e7715d3c2f49c0339c935ea5fb63c482a4098774963467239746182d18e05b19b4e71fbe77f6a3ef378aba26cc3aa67baae536d6c45bed668b75ede8fc6cb49cc30b6754224acbacd90d50b780752ed7d461ffd0efb335d6cbc9d998c8d58525d27a2a399f6b361b17e71776498f3d726e3818b25ce277d9e260977063938b257df4f14761a9d46c34567ab3f998dd2f8120f8cb6e17478bf3bbbaeae951a6d188c0f4e3a74f0f4f8fd99abcf4e0c12ffffc2fdcdbde2ee89aab7aa8c849b33b3b9a2cae668b1502db851f8d99541d69414a8913f6b901151924594f38dca43648685ccf181c093aeb459e164934da1bd7f3e9105359b09698e0475d773c8683bf09ec69bbe38e14f4397b3b2d101981a537a03a86e4ec86e2e01d888a36e808922220a964983b5a49b2ae08c031119e0fcb7df8e107dffef55f6b4c06157910995f90c31609e788e3b5d3ddda6a8d47e3a39363ec404b7410f40683b052c6cf75aefaf3d9bcd56e5fb2450d421664d47c70ef009f371e4feeedef133434eaf56221383c3cc2d5e1cf68a4d3b95c2e96979797749620ae1c164693314d13491c1e1d122fb24454ab151a628d66c3d11f0e9e1e3fb79f1ccbcfd9b3af572ce847c747a3c1e89d375eff8b3ff773073b3bc59cfd08fc5a8f9fe6567a812c9d1ef5863f7cefbda3c367bd6ed7beafd10520f53d5185a52345a646210129c9287d5c223d5bd1a780d772708cd99f598a371db388d9be1052659a418909228758258784375a630e4f8197082b1022026c2e2217a40a049e4d9049999b698cf79cb23146097c9b21e2ba31884b2c488c5262b6587cf0c17bbff94ffec9eafc6427b7d25e0457a1ada4fe56f6c0c9c26e8dd9dfda61a37b797599d7f7fd192c6699cd8d26f319fbdc62a937e84fa67a0743a55ca6317c1e0a67bcf7f7f7584c6bd51ace0cd6384e7c83afb06cbc9f3f7fde68367163bafa0276b1a8d62a58eec5f919dd2b57f5822fb6e758db7032beea0f06e3092bd90501634fbf21c6d6fa5ebbfd4b7fe12fdcdfdd6500901d69d987047a5d9dcc944e123fbcfffe07579dcbf3c3a7e747c7d97c50add5d909695c2297a87f1d9e31700d019e4e90fea9d3a7da4d5231a149db199f49cdeb637a37dc28db7841abac2369054efe70248b8137c9d98a55e00df12ff00ad7bb41da78461e2b2a523d1dcc7f2c236ad20e35645b57256348aece0854d35623b1522e424a1cb6b2bae5f34fbefb9ddffbcd5f5d9c1d6de5d7a5ec3257c0e7d9b5103d1ba7b0aa8847b165ad5aafe10b3b17e7ddbe5c1441fe5857fe6afbdb3b182e1b615fc0b1ad72b9b4bdbd3d1a0f5bad56b7db1d0d87847d57dd2ec5fe8425e2b3feb2523f7ffe6c7f6fdfee7e9860d3d83a6e979d4f292c566b353d74b25e0dc7e3c393e3f32b267dbe3f1ab11d190c86fa5d94d168bbd1f8cbffc62fbefce8213cd93fb9c30efc7eecb57e422cb7ce2d67f3f73ef9709e5987ebc5f0fcb877724e445bac54c36a59dad380a1111b1e0ea987cf48a5e904678708a7d1066c617d31188dc0d38e24792d6770337f03346c9e886fd602923ab233339df853f6262bb18cf01cd6b57433a4936c3afde9709b2ca99b142509c96d4924e173d30b628595bef3fdd17beffed16ffec6eaf27c39ea4dc6a37cbea02b9c66c41a445d1a2398903f6441c411ee6eef7ce32b5f6917f3cbf98c7dc96abe1c8dfa93e5fcd9d919dc58610bc5909d47a55663e77b7ed1194e27ec45ba16e47110edb90c2cacb8bc7ebf57a95667cb39334973319f9bb1ca17f576c319716aa8479956448db3d970321b4df43d21467f76d5190e7ac16af1dafdfbbff88d6f3e7af0505ecfaeaab202608b6c765c297474b9d2cfc3b79b4d39dee9b456cc2d7ba7effdc16ffff9effcf3d3a7cfe8211dd54ff640ac6969fee33a189f086c40fdb0160ce7d917812cc26d22c5074872e263e0d984fe3640e147bc1dd1ebceaf352e2e60cceb81379efab7c14f40944663a918c87ac346131525c804c802690269ce33313de7c4890249b9d2518a7fcd88f7dffbd16ffdb37f3ab93c5d8f86cf9e7c54cd17eeb17a5ac81ce8f78904b85052581fab1b6e8674b55c29e4b2bd6e6fcabe97d56ea99bac06fed87986a0bf80176c35dbf3f92c088bbde1e8fcb2536f36753ffd685c0a4b4881abd30bcd33192c12b160321a0d265302c1dce1e121d575ff33ed1682f1540fa67430e7e1703851832cc5b3c9b816049f7ffdf55ff8c6375e7ae911425251ee37062dc7babe4447f1516c8d57cfcfcf8f2f7bcb953c65a51c2e46832993623aab6fedb0d6a30f1baf44914aa7b3d7211a911715df863bb98160b02c71ade84e6207b05a5d93edc8073dbf7821b3133fcd21980a8519989c5e244c0442c88694bcd5b02792524f3890965c864cd20045fadf100a1cef0988f9b08c234c0252b9dcc9d1f37ffe2bff707279321c74fbd3e9d5457fbf5edbab5728c7db31a0323d7bcb1b6193ae9e90d1d3e3ba09b2d1ac85b91cd1d5783ec3cb20df7c319b4e27d56ab5d1685e757b61b14854572c974fce2f86e349a95221e6c3e161c538aacb4e070789e561af676767e815fecf9e3d2584e85c7616f69ee0a23da9c94604e2d3f38bee70389a4d303482d1fbadf617de7af39bdff8fabd83fd8c7d2da36796259f20eaafc640d103403cc82afedef3f365b1bacc0578788a1704b27a5361b6d4a887d50a5d0369abaa55ff0cf059e8a47183286fa04189abdf28fa89c06241c7e285183ed1d8476dd8c4137b7d7b131910038a1d58521741f4f55794b90e6e5871ad48ca249b086a8e2952935a25e167234ee83d9164d55d51caf581c2b606c3d1effef66f0c4f0fb393f165af3fcb15c7337b3fda52af8386963668279fc9696be91717f44b0aba8e8900ad5af3f3efbcfdceab8f5b85603199f4fabde56c8afdd107d65c3cd693c3c3e38bcbc97c79d6e95d0d061f7cfcf164b118cd6657831e81e147cf0e4f2f3b672cd33836964efdd4c27a88c561667ae29830338393e298b0ec5e5c9e5d5c4c67538287ded545ab56f9f25b6f7dfdcb5f39b8ff906851feb9a81f1395164c39f2d4d14dbb74161db0455eb2199a2dd697d3e5d522f7e3b3defba717e3f57ad2eb9e7ffcd1873ff8fee0aa9bd5cbdc231dbad64caf91da1dbce406f253c0390051de20661f25d45c0abce88590cd2ccd9a2213847552cd1380c6d95bb5e7625494325525a865789c0160c80854ddeba6a477b637081ce305e9fe259469f0e62230023601bff35bbffd83ef7c87413deff496eb309b29d855de05623170ba908b8b9774fa25373e3041bfdf8483349be872a5fab52f7ef9ebefbcb3532e120faef4ce847caf37e85c75717bf8975c31ec0e47d3c57cb25cf4c7e3653e8fc10dc6e3de6070c6e6f4ea6abe5e9f5d7606a351afdf67a782edac7399c974ca46639dcbb2ed982e97c3e90c1a824b82ce46b982fffb992f7de9b5375faf35ea2cd40400a15efa1b6071f86de241c0ed4f7d25343465920932abc57432980c46cbd9c570f8dee1e98f8ece4faebad3d1e0f2c9938f7ff0c3de55cfa9230d467643ce0f81e12c1d95fe04b873387c043d99641330fc0b41a2981f496241b70171313bb3044c8c17a04a317816323ef02e5474fbbb41e6001282a4284d93f42a42c665fec9394a98b84adb8c30e1f4cdb89a5dafffe00ffef0efffddbf7b757ac420f6c6ec0d1b98d8f0fcf0ad7b3bed52745d1a6073c0b0b220db2aac2b35c455b2437d938cb5e4c2b0d46a35f2cb55eff26abe5c13b6619dacdcd3f91ccec530a46ddcd862b164b1ecf6ba44832ca7f855d99ffdc649bfd743bcc170807f3dbf3c6fb45a10e366a78be5d3e3233d53b2580e8783b090db6dd45e7df8f01b5ffaf2cb2f3daed42a8ba53db31c1059328fd055dc4d57b2cf7b7b75d072b1c866f4c5dec78747a78341582de5f2d9a3e3b3fe10bfbca8954b85cc6a3418cfd7abc6560be1e1e38c5cb3ca0a948d52316c522f86f438a6c1592525c9b026b029bb0e5e2932c11f6b3be2a2a98443dd56cf45e40c6236fec9b0b19dd69771de000a12f62e29130c899fd08d5878cf795a63a2a55458cb0a99c39f65f34f8f8fff93bff3773efae1bb6c0fd741a5d26c636afd5e37d3ebbebe5dab86da5a7a60cf0aa7fad89ddd936277d2f3291bd5c644df74e9f7702a6c4fc2626eb5e80f15e067f2d9e1047fa3d77511635df5706f0596d16ebf3fd2ebdf16bdd17032d75b7e09c4d8154fe7d3c984202fb8e8e9debff95ceb352eb0cffe7982014f6a41fef1ced657df7ef373afbfbe776fbf542a8785226b6eb9522e847a3dbf8c0ef74c1f4d031a025d0e549fd943d9cfc8ea89bccbd3b3e7e7975dfd50d4846dd06cb5c02b435d0ec3b01020c52abfaeb5dbf45a2c5c6da641cf5c03773d9e564ee02d9a08b7e80d1fa562b08b69fceba0d0ea6dc0696e83acedda7604511c27dbd3079c3ce128650c446952c6c43212ca853748b72ab2389bf4ed06a490d78aadaa99258613b52f16b8405cf462bdfca7bff22bbfff9bbfb51a8f894b6bededadad5ddcccd9f36795d5ec95ed46a5141211129b6b7af08fa14537e23b6b6555e6add8855f7c51b3d9dcde6a97caa5a5fdb418511d0e71bed2f3ed1813e12fabad5ebdbb982f326b02bc3008303576be98e0603c62ffcbdcec0d46e79d0e0b711fa734e817d7eb7236fbf62b8fbff0c6eb18dfab2f3faed6aa74aa52a9944bd0678904100fb9b49eb86e6d2c1c4cb1fac8d34fdd5db5c6ae0f4f4fcfc793de7431994f166b82c3059e9020970d57ad545e4ee78402f5562b1f065437fdc1400abca66203116c865740a37e4ec6d4c1f10ed7d23089527c5eab12418cbb5e4b7ff1429c7a8ed879704472a40e953a997a945c47d6292d2a151da27c0a1224ba7656694a71f59481c92493bbc129c728e4f24f9f3ffbcffe93bf33383b5f2f16f8a1adfd83b0521f0e7a97cf9fee049957b69aa5402fbc970fd49d049c7535501b13bc2a9ecf6e6c66af0a439741c36f77b9621907fbbb0ff6f75e7af0b0596f504ed57ebf37c1cea6ba9154976c562b7d77673f1b31b21fe126818112bbe1f01693d9623a2b06f9ec72d1ac965ebb7ff0c5375fffc56f7ef3f5975fdadbd92edb6f1013fc613330d1137a7aefafbd17c1be87456419a269d63b2f3d88bd768788b89a4caf3a17e7579dd12ab7a0d1e53ab00b9903b6448be576b3555c6789497541bcddd42cb30175b8a14c3aced9e7219020933490a461e509204db001db23de8034dd35b6f6177bc16ed461d637b70bfe35f8a9fa9e404192ce39412aaaa8d0719e48d2694823655d3125e048b14998984e5968f4e2c01858a1a483750eb7f41bbff99b7ffaad6f1516b28546abd9dcd92f952bfdcef9ac7376bf947fb8d52c17f5a23645f6ba4b41112106e796872e75f9c3d24451ce9cd1c7b4667a25668e8d69bd5cdd6e6f3dbaffe0a5c70f5f79fcb855adb6aa7afd7e98cb15b3d94246a3ce422e83982f58f2f535c64a2fdfcaad56ed6aa95d2edddfda7ae3f1a39ffd2a0bef1b6fbefe065eba5c2a61e6882c1f9cc96382baf45dad158222a66fd6b54086c07ea80c91345135574d316e46e4209a8d67a3c1b077f5ffadecbc7a2c49b2fb7e6ffaccebcab73763d7ef928400514f7a12a00fa7ef2041d00b5f0590840001140171697657cbddd99de9ae36d55d5dde5e6fd2dcd4ef7fe2564dcdcc2e294565e58d0c7be2c43f8e498bd35dd47e55e9b3ec80b4f60240080d7112ab87a6dfee76932cbbe52ae14e74c57f45dcb1a5dc8d58fed7292e7e1bf93a98f0d06694de1edd6c77625f77b50a2b49fbd71f24ff8968b8aa60e5a8c3e2bba9a4bd34b020484ce92a2ba5a85cab7217582efeade08ab970b7c06dfad7118320651c505ca22ec42df54ce517bbaffedb7ff9cfa7af5eebfd8ff93ceb751f7cfc699ab6f77effcfd383b73fd9c87ef2ec49b7ddd6939411a889fc50fe2671b42f7d60d241b7013470d736986c025d8cc713ddcc57eb7657bdffb995911a65997db2c6ee63d535ba29e62000a2fe643cbdbabcdcdade6ab55a571717bd5e777dad1b7a7516469b9deec6fa7a9c8206afdbeb822a543642b4465fe2654ba678719622ab2109087208254c02845198623ad7675c72ee97a42cbde6c5f5f5c5e1febbdd57bbaf2e076fe6de79d92896257c4ad3eeb26ce2963c7ffae4c7dfffe1f3a79f3cfefcf3279f7f1aa65f2fe33b33a0e0786b7783ad82e3c37767ea6e8a0bdf4a7107df9d75938cabe43fd0886b1d08de028288b047c91b0892c44eeb12daac05ca20dcc5976f42f03670e832bf154877256981e012092ed14548b752df68701598032f3cbe1afcd7bff88bfd175f5597978bd10027f4d1d327db8f9e203f7effcbbf0b87977ffe78e7d3470fba9d6e14c5127ef659a25096ba6ee9635fa92379c704dd3abf58b8eee8da2e7d61c3e9fa1e861df63d0ab6dde9203ec517b9a5fce9d29934bc17609ea1a0e90b6d0f16933869b5326452695f88c5d8a496ae6a80a6b2d4a14e1221070935ce355620de91a8f1f43d263776604057aa525701756194bbbb58a8d4778dafaf2ecf0ef68f0ff6df1f9fec8ff30f557db6400446dd4e4fef9cadebb55ee7cf7efab39ffcf04f926eefa39ffce8e1d3c79a100bdfe6abf5f75d08126ea74004dc1cb27787b7291671bf7f3858e69d0ebe19be56ed5f076aaca0b73aa0b66d064997e652e879d5b9f6dfe844c854e21d2a57c11dde8655ea4d70890c12aeaf1ab466565bddc0e6fadbbffffbdd576f0a74d85c1f3a040dbd76ab9c8d4ff6df94e3618a57619e87d97bfcb397c23495e6d637de2541362669825a82dda807d5e1bec316b9b80760623c1de7959e7e47dfeaae011c1a6b10afa5d75b6b75da9db5eecec3fb6b5bebed6ee7e1e3871bdb9b59abd5e9ac8571d68ce23a8af4bdde486f176140727f340e1997b85380a96ed6b20d64082215edd1242969043ff4b9650ebde2b3a8b2b9b0f72d2d11e459a7bbd5ed3d6d859f25fec328f0978da2d03da440b78033d3d9e1e1feaf7ef5cb2fbff83dd68531efeb70c35097cccad69e74fe8d04055792707b78777f37b8d9fa7fd8ee92b00a2b08aa7f839d60c3c458178603093e98a3abf82a73d3940944cb62af1796e92cf0cd2021d1bad3117b91ff1da26f1355cc95b6f25f67518f04ebc05a6954c4fce6dee1c1dffedd3fb8c77b16b349a9378d2c23bf5ef4cf0747ef92729e352a7d0151ae7043efe70dc3860f859a4526159021c418b68f212c4028c80ebbe95ad2a9ae43d0a013dac89c1c74e82d4555858f291d0a15815e3529cd08b2726d8bd902b7b902664ca69e0d2de338d175e6aa6a4ae7e2f7e8d28c5e3baca7879a2ca430c20b49183454c1d86aa917294191f8c150e5770886e2830897d15de9a17a297118c7ca4182b6bbed56163e48828fda61375ee6851c73fad7f3f855797ef0be7f7afceaf75f9d9ee96e3151ec1ecbd7d9461b2e9826c05f51ad7ce21659cd9d0b2a6be16e1caa5631d7d09ded5b89d431f9c5269de070721bb991822ab00a1cb98e55ce6a2bdf321c016eaf72fc2b59d155b813fd46f24d581d5be0108e5b6c55e0a65bd79dfa2609e608f075c96c4c8ae5fff89f7f737e72e6ebca47855d87ebe837eb1455bbacbcc51cab3e6e361273845d1f2e62824d7dd13e6022a0984974dd319d92977631974324629aa626b0204397cbc01a357551a5d0f74568c26e102cf082c50e1c92458efa06bea8759c5c1b90ee84c5e0239f3cc6e8e94b3b0c59e720e9c5ee36d5d96356918d7b3570eb5722dad68784a60bf40a3e5c5d3a254f2732b30c1b238ba2fb49f43409db2cb12a6719a55190455e954febe562707db9f7fa8dde0a0631263b6805200a793407496c4beb54e3757cb72e2dd0d12af6c7030db9cd052aa89d6f26bab01ae96d93f4ba8add8455899b400b92093761956a648943abb8fe5d1e7b6d9259aba092ffe218eee62a2e19acd62a5d436c2040101be277ed3153fff4abdffefdcf7f8157197b8d563bdddade7efeec19de70e035b7d63ac1320faa026499c8c111d6cb0c6c2ef5efba204e002b286522800fd738d5b73643c647021108d16d510186a3942e85ac8a0420309574a57251ce26d3c56cc680698c960128d4d39371ac0110d9c36c7a4232e99c9db8a2c1420e4a1f5fc81ad7bb66a840c364a92645109b1614d14b6d0c2ebaba48be6c4a7e74337800045b6b9d75e8dfc8e2e7ad782b68f8e653fb1e0065f52c9b3024f0301acf2ece59cde26373c9a2c51d13e5daa96d5bea744d47d6af66819d38f6dd40c17f25ac06faddf0ed9a74b23a29b36b37ee6bfc778291a25411a38355e7cc8772eff6623177e88ab925ec2ab1bf6dd9a5dc0d564a81183dadc00b7214650e3ce66734cb4fae87bffae2cbbffacbbf3eddffd00afdef7ffaf1271f3d7bfce8f193c7cf7abd4e14343a697075f8a19a8eb7badd270f1f743a9d288e9dc7816d457bc00930b91e99efd04ed080028730920d49f238fd30b0b9d17399e4b181181019e9128b04aaacbb28c28e845830c7e8685387a6c7cdc4acb123350a53f7f4e24af2c7a08c3d3a492ecbd2ec3fa261a027571c79fc5841fba347bb0a6ad280e6f569637c760c6168543b4dbfa80b5f5f076d16e57230cff386d74993075bdb503c99ccd356aff4827955757b3d8089e8663169b180448ddbfad31834d32bfe5b82a6d008e0f736d8a152acd437824bfb2e40adfc2a7e1b6e5bf80604bf15ac43d1c7afc39029473be4c7f86e89c2cbaa410abb7574a7cfbb71c21f38949c5343d68a72850969a8e560347cfd766ff7d5db7ffae53fffcddffeeff3830f8be1e0b38f9ffdf0fbdf4300487eb5da711c15f92c8d1ad3ebf3e9e5c5cec6fac3470f22d49c9df903824424bd0c6a7427a5ab133d26f27cbdf9d4f54801374c5daf93dc74efdf450c91051a752699dc222f28c950951c484d5316a90b1f6856d50534dad3e97071495c613a9542100b6bd6803c22abaa7e652461e1196d2b564b2b1a73c40fd782900d0881e06c3a9ecf8160838545814595836ab5d9f0fab379e5070fefed3c7e701fdd7f753d8cd2b69f74a6f36a32cff70e8e5fef1f7e38bd381b8ec79339929bde8330903aa42339c6f4695dba980577781b44d41fdafe58ee9da0c6bed5e61f80e08a051688df9625e220a8b86bdb50484c792e51750d4e16acaca5df095f1fba9ec45f18e078a0d6d8505c17d7fd376fde7ef19bdfecefbd3b3938fec5cfff6172d5c7e583b9ffeecfff2d02c93e3823a5361d8ff2f918d3a7b1980e4e4eee6d6cec6c6ec7763a46f883c1766b824db94284aba207c3c1814c312d20eb9802100424d09a5e881ba36fe7203148a140122744c41095d754d948997989496b41af0406e792790e83428dc93319ac52e5e0196b5f4bc16157e0131b28a9cdaa59e3c651bb3541d51d53d09b52c3d57caeaf22327c019772f2dc9721146318371bf32a8fb3ecf38f3fee753b68f78beb811f67fa4a54d2c20f3f3c3afbcdefbe7cfd666fffddfbf77bfbc74727d78321745004c241b0714201726c765687ec45831db83dbc5ae55958cde56d853be1368bf0eddcdbc797be2b05e1867eee74b3128196e5d22cceafa513a1b025da2109da116e90a66031d2ecc0fd20eff82303cbafae67b3f9c5c5c5dedbbd175fbef8b0f76e361aa386f6dfef1fec7f089a7ebbddfad33ffd93adadcd7c36970ed30af671058a7cdac6b8af8bc1d1c956a7b7b5b911db7916893b94a9dd812761636b06e1079814d1ac4bdcde08a3d5e73df44a0dac34b098e7f221edb5faba01c06ea1523535a23f6c420e813b85d5a64ebbc8ca42a5d210ed509641bbe13a5753a8ba919d22495dd2862850bbc2a1be784d15638f76fad7f5b9122bb09006d6a352f00d8928e9a88bcbcdb00e5830950425822578faecb9ceec44e1f1d96581306401650939599a8efb83ab93e37c389c8e46d7fde1c5e5e5c9f9d96432c3438fd39684395c9209ce9c68e6348fccac56d96afe946454dd85ddedf6ddf08ddcef145841f0d59d9bb56ef700c250e5520c5f5a1c7678535894917513d48d25135649df0c14c5e212b37520198b0982614d4fa3e96ceffdfb972f5fbe7af1eaf8e0683418c0dbd0f72e2fafbfda7d359de5ed4eeff1b3e7f7eeeda42c78b84d23b821be379dcef6f7f75bddf65aa73d3c3eca9acd4d209826400aab8e9910b8cce1d534ab6f2100028422ad75a080bd48126a4d0f6ae0d5525ab6bbc666e7eddc70e80d239f610a3e52e3d474b844d2d280735f284c3146caf82845716d02972e881819aaccdead59729c041253e5dc682ea053cc37d6ba74c0b52ccbdceeb891332e004a7c5a3bb55e08029192947ee1855b8f1f4789ee0f7f7f7c1eb57a519ab088fafab4f6220a7dc057e5655e2c96e52260a94c6767676727e757c3e9026b208cf54e445aa62d71456cb0d11bb94c994c6636a3ed5bc1f1e98f07839405356badfd4b1024e5b67fd73404e98743db8b4d4af99a160adf96511644df1c1168cc96164b5c0d5beb243481d7fb0f075ffcf68b172f5e5c9c9e148b79849cd0d7e0ca77fbfbaf765f5f5e5c218c9e7df6f9c6e646a7d5cae2b02a725d0665d23d6f30e8ffead7bf9d2eca2c49baa1dfccf35e6f23d5276590807a473474ca5613fc84196ab939063d364625439648b2214b76ca3950d0a13d8247210d01b45051669e6c4cf6a009f7997c69726bca6066858572874c5757a865e8709a52eca9a4c68dd52c8b1263d3c93fd58273fa177ba9827043919785ce1fea54a43e0c612dcb6cb5a9e3b0a193d2144cd2f6da662b6b797172787a9126995e7f93b5c6b3f9657f28f75bebcad7bb8fcb22f543bcb6baa8fa57d7070707ef8e8faf2653a628f2cd5f93b304b38c3df422e2acafd5efb7839b6e0dcd64a43bfc83c18d8df0b522d6982dd5ed45a7c56f18610d2b7004762d4b99cab07485aff167e56e8e6e7eacb85493a7b3d90c6a3ecb8f4f4ebff8dd97bffde28bcbd373b88bb64be288057a717ef1e6eddb97bbafaefa03cf0fef3d7ef2e8938fa9b481ffeb3505c1306299c2a4e96c8a7d339d165757d7088a4e92aeb5c16916ca2301252bf9275cc82391182348e0d92ac26bd6606d9c0415be3941bde2827183c4c8dc1a5a008826585d0b8852e1522ddba2a3f8b2d6055ffa53fd5aa772b0e26cbd684a2146c9e4093d32810c958e31ec4486ebd435465cf4e8f52442e2623e97bbad720ed3262f39a658b39e578597b6b2ce5a98647596bd3b3c4509b312a324194de7fdf1843ed238fedef73edbdc585b6fb732b293a8ae2a797ff3f9e8a27f7470a46f9cf4afe7cb9261233f91f6eaceba7411f7e3a6f8eef6adf09d148d4cc3bbc920f20d5bd031dab214d75906abc31111670c90209c59b276268e6f12b477f56f7ec9b5fa02adf612164daf6a3626b3f9c9e9d9eeabddafbefcf2607fbf5a2c127d702184e1d7fdc1abb77bafdfbc3d3a3a9e4f17419c7df2831fdc7ff69449cd58df59dcd04df9b872c2561ceb35f92f5fbc44b8e98543e369e27bdb48802cc580a380d061a872a881026c262224db50348fd026f96541294e20d97d0380582fdcd5753c5597cc4328dcc8512baf4e400b15e889ba269f24e78c018c5993450b82ac71984015fd18c30cab06568a23ed9504bb8cffb60a04530ef4221a969eded4efb86a7bb7494bd30cb547b3c5328adb1bf7e2ce4699666f3f1c01cc38f2e1c86832bb1e8cc208db35fad18f7ef0d1b3a70f1fecdc7fb873fffe76a795d478dad3a95f16cbf96c34e89f9e9e1e1d9f9e9e9d17cb663b6bc551c4b05d97267334b3ab4916adabc8bf123460fd32243b561bdf56c42e880b6653dbeab6144b23d54d9a9867e5ec47c17257cdbb04814d05dcf7865c04e5eb4d17c5e1e1f1eecb5dd4eed1c1e16c32c1a24ba2b0aeeaabebebb77b6f01dfe1e1d170385896cb6e77fdb31ffd78fbc9137406c4f43a9d98c95b96880d94ac0359b9acbffae28b5a27cb9679dd408ff7a2707b639de5adb32a76666e05204dbc01e74e805e916e4a743504b48341049e486219da5c0a60117c7168c8c0a6b45c0255d8ebd8600df09ddc529b3ab12c8cd082716cd5918265c31c90460b24386248b5366de14a02a283ed9a4ca11b0c714a28237572b312dc3a03dd0bf4e964e2b7d7d71e7d1c7537165ef06af755e4359e3ebed7696797fd018a9821e127b5daadf5cdf5a485868eba69b2b5d6452866ed14972c893c8c8372369f0c86e7a76787872770569f86cc329d4ac494872688b349750cb361fd81eddb593a7689abc010bfa18889b821b952e28f3541c4ad684bb6766090f1dd35ead2f8a788aab852d233e2b5d298aaa6379bcf0f4e4e5ebd7e0b5f2e4ecef2e9946675fb8aef0f86a3b77b7baf5ebd3a39399d8ec7559ed308e6d6273ff8e1c3671f7961841c60f2bb691ae2bee8dbe9fabea6a30b43feabdd1798e31870ba7bb0ac989187ebbd5682932c5109102143a370f69c452da298e415010167c32038261034f788203b0479063e4c24f9bf8cdee975574516a771407569e9861d8c9c14b103e5eef045b8c1a22b2ffc5146860904098ed457ba3ddaa2d342c66ac9415d0f048a4e6519e16a44a3c08ac41f5994e5acaad79f7ed25cdb2efd8846afcf8e7ff6bde73ffedef3cdcdf583a3d3d38b3e7c45ae9f9f5f2cf202373fd3954a8d35cdd28dad0dbcbd470fee3d78786f63a3b7b1d6dd68b71af90261787c71e9235cdb1d7c15fa840275ecd6c8ff47d0ba5a4557e19b8a588dba6071a5389fe1e69060711dd19256b013ce56865fd78341d0761212ba2e3e9dcf8f4ece5eeebededbfb707e7ea9532a9871cc4a598e46c383c3a3972f760f0f0fc7a311e0960f69f312a4e9e73ffe69a8b7ba78b3e9ac2aab5612fbcb8a8a489448c0522700e1edfe87a2ae3bfa9a5c431771cb7aa79d6eaf77b1a6edc40c358c1a933e0844d12f409002fbc55323d90621836c052ccaa88e091cb04c5c2874724bbee7ea129f5ab5f25651ed98852766802548a51d81cb8955234325a964e0525da30084912efcd9153f0e49953e96405dea1859585634a3f32f82a375a6af3be9c6598e46b3693369b51e3e5ba66d7b2770d98dfd8f1e6eea8b9fadd6f9e5e0ddc189aef924f178381aa295f14ef40021c661e4381f634163edb4da08c57bdb1b0feeef3cb8b7d35bdf188cc6af5ebf194f67294a995910100946bd06e0765fc75787df0e0e3bab7ccd008c5d29e2be3962e2841a362ebb0395751322250cc37496d5324cc6592971db4a5a5912ad00d6775963f32d4e2e2edfecedbfdf3f1c8e26ba6b08a7adc8d1bf67a7e7efdfbfdf7bffeef0c341bfdfcff305d54219fbb269f2aad87ef0e8f9679fcf8b62be580c0703fa6aa138f411f59079d71966f30bbc287af76e7f349df968de24c1806a96153ee883ed5e5b4f30e9c95ccdbab9c62ed094f6462ac1b0a2a800b14a517098581d4aa84bf2515559028fa90e3756578c44155383e2897560dda98c14b1350f6ed5208584480a1a144d7c42a6094ba1d9d2ecae8ab268eada89be16ab2a261ab5085498ca155382146c548d719efbeb9b416fa70ed306c3aecbb05174bb2d34461045b379fee2d5dbaae965ed0e50a61120d51f0cc793b9d70c41210bcc86a09503790c0432b0763aedd65ab73395f97ef1e1ec0cc4b7f525e550d4bba2621f6373472beee9f0ebcdca1a636eb32cdc42f0cea96931e9269fa098d520a29db6550aed69d6d030a644b0f534aba47b70a3ee4f6787a717afde7f78f5eefdc5557f9617a3e1700c94fae7471ff6dfefed1dec1f626a0cfa83c26e9d87b9125758519a105ded7afcf4a3aae1b9376d608ea001b3345967f05a0b551c09521081787af3f60d6650d30ba14677a078fe743a5e6ba59bdd8e3c08c3a0de10a42916635d777620e7941436370a9725c163b34f0a4976088b54912accbdc62f6c514931d225974c4512d4913545af2e85d65c0a40549b16ac944423e92c26b239b46c55d1ed39b46e22d0719688ec02a962e3982967d163ce905e9a14a7f1e64e23eb35a244e797aba259ce5296acbe0b1f50feb2df1f8ee74198d0f56c3e95846e3641e1f9e5e524cf69d42c6c9de9a4634890f4c5ec69eacc032108a3f93cbf38bb988fa7703449120d47c4423a54b8208ef2e346a1430dcced756cccb31cedbf0341c7a99ba056f4633c72a0bb495220c320e8ba245526cebcacceaf072ff7de7fb9fbf6f5bb83f36ba0381ff4fb877b6fdfbfd93dd97f4be4f0e0c3e0fadaeec1d3fdcb187956dd2932bda56a6b6bf3e1e3273aade0fbad4e6761afa537e684bd344dbc65b9184b062e8bb298867e7df8e1c3703a5b22fef41099e4cd7c31c7a67ebcbd95e9966918ee47416480a023822c0453c11a80c060c1b2be0ea4304c270ba98815cff41bf256599a7eaa8bbde2127bbad0f31fc2ab82718ab894ac6bd055242e29a6e50bf7cca540ec598350e1d0affa829c9e47b105a01f90d1accdd751bb82a4df0cc149b92ce72caade96d7d9ace2b411a660b9512e9a55deed64ba7f1bc2b07b087ad6054f4f1f38c64d8e134aea8993b38babd3f3ab85ee2bf3584318d618b86e44f0c9d6a56eac6ca5ad248ce793d9d5e5d56c3e63203ab9a042123de2838dce06ae111a3c94e6b6db6049fcdd40f0357ed24d06f3e038e7c26d4cd4a804ff3a936bbcb726418e7d957c5e5527d783dfbfdafbcdef5ebc7d7738d44bf4e6e3e1f0e2f8687ff7cb83575ff60f3f0ccf4ec6fd7e319fe9a42ec883f440f7a1c01964c85ab7fbf4e9d34f3ffde4f9f3e7adb51ebe73676d9de617f30543ec76bb7ed35f6b25c1725acc06a9bf8c6a74fa248ebcf97476cdba0c918f006581655ee21ecff2879b3a974d3f721d5657841d10b4a6f9615c0c8501286e2c32b63052294d5a43b031fba4889f9a0e2750751d59a8d2dd848e4742928c50419b63f193b80e55c256172936956e4e08c29f999bca054c2bab51855c0121afd63b492462853a23d00e29267908f79b7a8ab4582e670cb0b75565bd2a886a9d5aafea7c1a3597ed76662f72f266b345bf3f485359729359315be4386ae859d08604b46f29e783c1f0f4e20261695a45960eea089c04be4e2d4230e8855d6914b30086c3e1d5d5f570309c6205e993b7f16a90e2d66a11121c07fe58b885a058e39254e34e9d554c4375c3d7c570756153026b974d3f2f96b85aaff60fbef8eae5fee1f17834f36b2f9f8c4f3eec1dbc7d75f0fac5d5e1fbc5e0a29e8eab22479e317b59ab95b53a5ad6f6ee0984d2c307f77ffae31f7df4fc993eefe179533d491ec263bc107823df4defaa6fecb4e366de6f56537cb348b029b0f566b3fcac3fc61644918bad41b8184fcbbc4c02ffd1ce8624802e88c149610dd46b049a68fe0d711634a45bc1268743688012652900375491802b181ad028ae2b6b34238448c8d008d2832ae2d9cd5e1073a76f8c6b42bf349dda378ab412487765a04f8d893828d44d8a5a060071d5941ec053532ac09f5ec19f632e57f5c48beacea61c9120d4bbe4a8371f477e2d088a246fb6106882a06e773a83c16432cff56c4aa03b3950fa1081bea08fc17874767e25a1787989c732d1c707168b5c0f48181b45966ccb308676e4a2bdaeb83f1e8d9957fb78a3866fa313c10408d700ff481073f9f9abfdd59deb1a9805a56b886204ab959863bac06743077c88f4e93cbf1e0e0fceae0e8ecffbd7d7b3e96431992ca653ad8eb3c3c1d5793e1b578b69bd2c02b52cf921d19ec49f7def879b5bf747e3f1725942641885f777ee63ead11b2677beac06f37c8c386be8225b512cd67a3dc455e2379e74c320bff296f376d20abd60e92da32c7effeee29fbedaafe3763e5d30c1e890d1c525ca6c276dfe879f7efac9f6baeef50b751bb359843ee28311681d21e18431c3817ea0c5b0e538600140c068070b02ed539069a3b4714bff4e46a982f19d62eacbf751ca808608956fb33495a4c8ccd245586bd5aa5ab67aac4b33c3ea6231936c2d742adea65eb952f44bd24ab46f01cc2a16fb6c9c3746d95af8e029be4613c32f4aaa62560e2e5a7eb9b3b5218fccf7fbc3c9c1c13e2b3a48da87e7e35f7ff9a63f9eae6f6ea6598629a9cf31cbe3d73389983194877200a227619bcd4e9aac75dbed4ebb23ef3a4de20cf9c9fa8324dd418bce2f4b2a773a9dadad0db6ac2533116a092bd87c3b88cd42aa1bd55fee6b911153692d6ac3a2180c5f2cbe5225aa6abd56c3d1e8fcaa7f7ed93f39bf3866c95c5dce46e3c564bc188fcaf9b47f7d914f474163a90bbaba5f93ea4196b5d224a1fd24cd3efdfc07719496f87a502fcba70221460c52b1815ddc9f2dcefbe3b58d9d304a279371a79d210c5bfef2710f1d30c1184c93568c591d34f93dbf98fcfeddc5781956ba6b061e3277f6aa8d3affb37bebfffe27dfc31c0233a8a3384ee985e985dd36748623f4e80e2b2c454916512b306293097de4aa88fbb3d10b730c873d7ca0b8cd916aa93d5ddc570126803dc906c180ca063163a6219e3d716bcff612c03add42197a2585e630277407425190444ad5a8ec111ef0a7255115b820e57c598c26f349192cd6769a5bf76b21c30fe2a451e58df175e2951bebac5a85fe70f87eff1dab294edb61d2faddeefb5fff7e376d775add35d607da9c358502290ab8b8409740ce78329e4f277559448dbad7cadaad560ba19ab5118198e659a7ad133a49869c805dd0087148936eb7bdb9b581d4c8740f989635dc11878ce16282f8244630ee1b08be2f48168b641453569786c0b0f2e56fb11874327651e6b8f1e8fec1f5e5c5e9d968d0c7f15ccc907da3b3d3637dae6abe88029c859202b008d0aeafada75982d902253850b4cf824b92f4d9f38f032f746ff82b751766c94aa53f260af78fe287975717c3c9c6d67df9b9551534cb7a31d94c9acf1f6cf9f5a22a16298b37490138aa7756d657737fdad0998272311f8e67d71717a797e7e564d49e0cfee3bff9c9d39d5ed05ca66112e8abb61a16b2d0c10dbe011ae9678c0ac30332ca569cf4a3128458bbc42308ae528c95ba8e470473ea1652e22142514dab86c93b65c15463bdea72e8caab3b1936a6c7cd5fd1b5c79b3ef85d9618bb4555e6200fce545ea18766e0849d9d59eac3b6c5b4aa6745a35f79f5d6fd6063bb8621b8b461dc049f8393b85974f55cb334c36030383a39992f160830b034cbab176f3e0c672ca3a8587a74434774caf229f2dc5d381e8d86c8c42a9f375044ad14c4c9b90e75f7388bc08fa224d57d0ef030c9b298194931975a41e413e976d77add7617c4b65aac525d1385258e0f1a1d7c8021e67cd1dc5fbfabe4d6910d04c579711c3f508f6c957a97379efc702495dfbf389f8daf1bf9ac5a4cd086500670ae90f0c767ac73cc62e36d0341c452048589a6c71a6eda9d01cdc63c2f712c3efee47364033d318bf37c4111c48e96bae6cfcbabe5d1257d16ed4e8f39f4ab623a380feaf2c146f793a78f3c296abdf0aad36ad33efd2d96751e748ba8c7f2c7389b97cbf90c477c5e2f66d76f5eac2d463ffbe8613b0ed244d702009b402032f987035a7d1665a77bf5e097014c5ac60a1804c906322e625cd3b00cbeec45a4e956211bdf135e58227bcaab0ac15983866e9722b94a8249386568bd6bf113500b1483f94bdcf04a1f30a3a5b22ea5f2d1c2b20eea26c2be2c27cd705c47e765b3b17daf91b6725d1ea2344c9a75aac9e36d989421fe19f0e9e9e9d9c505d0118cb0d83c8fb918cdaac1243f3abfce2bad1c060886251142adfcb19ed82f4adce432efb542ddffa51b9062ac0f64a54e04497c025ebd7307e73ae23f43b02205b77b6b1bb01abdd7ed765a99de9f83ccd0ed9b3847c05ce3a4b66eb2d488fff23d72ce1ea242d8d9178830f22e00dd743297861d8ea728c331ec888a59502fea658e36c064c1b967a11f9f9ebfdf3f92cdebaee2a3d1cc7664e46c309a80a880add82434de5b5bffe8934f990b2960ac901c87577a4d6b1b8afc1067ed72345b14cbf58d8df96434eb5fe6e3ebb52cdadadafcf8e9630f7311dfd3f35b5825715cc1238444babe08ba8d40d790e188c8683423af5e8eae666f5edcf3ab7bba62271e19c218af665a11737e8509e4fd922551c169798266288303f69a7009422a49553ba669d14860dbe96b8e74ff95095755716a5738737be14bf58469074d02390c19038329511ea8550f2a4bae4eaa30bd458e5b2e8b50340822052a96e1574b56eaacaa4751ebb4f07ffbe16c1ac775a00fcb034ec45316d4dfdfe97efff90e9a421e98d73c3a3a3abd38a7654417d34184c2c8c0a4d33d391f1c9e5d8d67f61cbc4eae405963ca329e4e81a56ce7c5acd70a7a9dd8acf990fee78b195629d3a767579bcd04cd1cc7e867f0cd222beba0bdb6d95ddfc48b6c672920048bb8496647c6ec751b892e1c0437e705a77a1a171ecf8beaf46ab0bbf7e1cb576f5fbfdabdf8f06e72765c0caf96d37e584c3a8d45d880232cca02fe30bfe263a3793d185e5c5d3a7d0407757bb16e2ad1ccac44a02924962e31a6a0ddd51758c554e4a47087c0d0d925b5e6f9181993297f631cde24682cc683d9e83af4b08bbd4ea7b5b9bece7cd4ba655517515894304d1e86172ec3543713334d7aae72292c68bebd04ef81c5e33761929da6b615a889b680d637bc185a545ef20d74999853cc0823c858042b16d1e0942d91c6bf72c9b214db544b58323785a05a569862aa68bd93265a610b714b54beceb4d0067bdda62feb50d545297dd31e493209aa3aaf1ab338b958367ffdf6e077ef8f26cb667fa8b71b36b16b1bcd38683e5c6f6ff532dd6b45bbbe3f9d4eafaeafa101ac68dc58c07abcc6ebb6b29dad4d4c4694a611882c2ef568f4149bbbc0ad4aa3e6f67afaf1d3adc70f37b6d6dbeba8e4d8c378f26a38b1ecb5d3873b1b0fef6d3c7db8f5707bfdc1f6dacee6da783cdc7dfdf6f4b27f3518e353a3ebe8902a5850f80c83ebab119e833e0678b3a0ffe2f5e86a30c1ffde3b38ba3a3fabc7c3a4cae3ba081b55d2ac75c3a524857b4a16df19412356b004c461cfff707c7a707c829a63693278cda23846d376c143419307439919d87ceffe83870f9fd01481de514456c493048a422079717939190dc802c9a3e1603e9b25519886e1fdfb3b9f7ef4ac986b692205d9f73a1de60e16cfeaa84837170d78eae46eed9e9ca3d77a70910c2e3af56cad2531886322c83ad966f30ac10c03779009305398f9a7494d1c05042617c8805a370a0ba409826e2f90198e8422493b0205e8454ada3483652b5873d61e3f3061d5b6241f0d595aa9571531739553d35ab0cd0a015fc823ae968baa315836fa41f49bf787fff8bbdd79337ef0f1a7b0b4d769e9c60f2f6c47f5cf9e6e7f727f3dd24db17aa8f3ecececfdc107a8420ab22feb06a69cdeb79ea6eb1beb0d2f28ca7a3c9bd3c8148b7f38c8e77ab70e4a03f5d64efd8d2edc93bc87d632af26b3a9fc3e3c15bd31254ad8a95fb8d60cd3e47abcf85f3fffe7aff60e0a2f84e51bbdeef6d6e6f6c6fafdededb55e8746248d82a0dd6aafa4e07ffaef3f7ff9d5ebb76ff62e0f0ee2c5a8db98779af318fbcac32201252cbc12c98cc643ec36913848202d5f849694ef757f98e7a5a48bad631a14e7cd9764a8386862218c661e74a127585fdfc0b122917698158891f4c6dcd64d6f3e7c1f0efb2556a6b79c8c47ba11d8ee7a6274bd6e777dad478a9bcc38d6293fa60826e5e8503f690429dca651a1099106717e8818992fa6a11e9ed58740100ab28c4089848de0eb5067c4881e2226b8eddf6044361183981241834b51819b88e21663b78297ab656d59204eba522c2268210599b39b2e84521864ca0de3cea42a82527a62a5c6b13888c0e651a3b97b39f8edfed117bb6fdc7700761e3d00524c6c1ceae532c0e1e9bdcdb54e46ffa2cbd73d5a0bd4bad1cf9a70eb1f5d5ce8ca9354173d61a66471d06909341beb9df56e7bbd2bc7374d42ec68bbe75b94b25ad1abed2c46c9ea2d8eb896a19ec4f383a0d62795991a2c226f341a8c86d7c8bde978d8bf3a3f3f3fbdb8381f22ff40ae3b159ecf57abf9e8c5ebb27f1d2fa65b51a3db5c0612f510ae9982d958e81870ecede4a1b14576932c71d88a822df239921bd4690e6c21cb1e97bc931882f7b0d6137d529a041ad0ad453635e20d98d0d4ebfb6fe4c27f54068e1b7575ed1cf9a187696cc6609eaec32293c556c916559188a0809b7ca4148510d7babd9dd620328cf3209a872dcc0d0cf0022b5603321148d0480429aa6a679b8313897401cfd591921cb9abb86d0a1aa0054720c29d1f2bb3d2d896a3e0caa823c5b506a487d4bb10a6f306cc30046b9e8d3c0e64bdd88f04608900a4f57963f9f6baff8bdd37fffcfb97e71797cc65a7d3b653f27232a0d6ce95a4623853e0b30f101734070ae994746481044a59527c369b4fa70ba860b38b41f44d29726451d19ad66d80d52b02000eac65bd7b325c918a18388859f9109a08ea32b9b2f2f36e16fee47bcffeecfbcf3f7db8f178abb5d5c1c1c9fb97e75f7ef5e53ffef29f7ef9ab5ffcfa57bff8e277bf59f16ead59a17943a40bba104523eba45253a08e43710ddab42e4d7ed8157ddc1aa1ad0957e8dbe6833df5a4da745a56b3a5394058326d5887643220130ad8fb0105240fc42333bfb449f230c405986e548c9fc5042fcc59f130636d029d8452ffec69a486930b2cfa060e39a682b43bb32474e1fae85a9d40eca7833a1a36b261d19cb0f64adc29d49f6c312c2ea618c90002840cfdb975a67e600e43115e8c56da34a092c3ea12ec57593781f2fcaf62823875c00f530388b47120ccb935e339cc55cc9e6e8224ae67e4e6184c90243b51ac359a1004b86058e166b7ec9e9cfff2cdfedeb17d6076310730419a31f1866d495018c8429477d90c1a7ed80c2216629466c88562a967f5657500b030c653656a689f4ee0a8fb652f19ac6e350e5bdd4050570d5c823a625c2bae315e4141d7c665b952551ce5a79d044f1f6c7ef6f4fe470f363f7eb4f5d9937bdffff8e1a37bbd6531b9ba3c3dbf38393d3a5467d6a2598b225e2b03c94c5c9e8e6645ae3aedb1d99d71cc4009d748d4028168ac2e5f672aad089b3eaf2062610174ea4282ee970228c211de865ee793928a076e5248256050d10c0b2f6e4419360a6978bb884d6258871894cc39d3e1aecc12c0863c1e493dac3f9d62809bb44e19d60ae0a705a803bc80909180230c8581ef5f95cdd1b4316382a8a97382161ce2f4a35f228674102441044b85538ad9f874e2049ea8ac3324e084e60aaa54cb204b20e212d93b28af0265d8c4276b5b3594281da305c6242a589af228e7b84e44126ad938bc1afde38b37076797737d476251a05b6569e8a37754a1c7c264bc64a25d971359b4e0c3a1344e5b95410d7ceb1e22b1d187c3c648fc18264c6b452419d40840402d18310af0e08e25adb1b8150916744e83645517c93763cfec9c7696c49d2c5c6fc71f3fd8f8fe93edc7dbed075bad47dbbd1b086ab9497de8ec6451b121cbd8a403f460acf6ac291d020b6492ae25e8563c665d13c74cd312386169311edf973426cb8ff06f3dddcc4ceb0879b13d0a6366296f3450b4951fd47e5434c24533acd38ed7ea796907f1abd3ce698608840f8c53bc0c032d0e9dc1c9b512fd305f3687d3f26ab8b89a144797a3a3f3fe745e4afb603e47ba179baae20e68408ac6d14257a682226c4d9ac968d198226c755e498b988da912a8d49ff84144f813f674ac6c39dd705432587366e8b31ce5b133366a622484ad96439ee58338e5bb1f822b0d4780a038e8b265c940accaab2d6b4d4188d462604d4d17e52fdfbcfbeae4f26230ceed6b00681ada5b9d8e104c59844b2c3c6c9830926726cecb2e6744cc5896a65d74129dca5cc61215f53a3b46d7367405c56ddc04e25087ef09213abe2de48286a925273e28b82afa811ebb0b22820ee6935f0a35aadc5f2e365ae1562769858d1441a41ad60d8d894ac4a98c3fa01e0569274cbb7edcf1932e91c0e241bc16b7b6c27423ced6fda4e3c52d00a20516456982fd91082ceea9e8ac65965c0007418f060a61752d19cbf486492369d549bb4e7a5ed66da6ed66c2d62a9bfe480f11ca9e73b469789219c03e5ce48bd164b2f442f0f6f2fdc9976f8fbfda3bfbf5cb7db6dfed7e38bab8c67391d6b3f7a9596d66043b005ddf14e496cd593318f8d1c922bf1e4e73bd9dd0f54261048de65cc0739b3480fe08ae3563b966805923417b4b73596e1ad81367b69461c1e52ac596392daa3392a58dd5bcd0ae61ea6c22a37674bbc9e05841829d42946eee1e1fffe6e0605c54f3455ed4157d5296a58fff91ea6d604dd62e0e2f9a6c3a9d50871428a522305e36023f6e7b51ab19243a934a7b3056b7fc467422428d34823a95dc17610e4fb687370a64aaac059a77035f1d5b139471cb8f28b988925696e24aa2f4edf284743a546193e9c28f95c3b0d43ad6eaf343c0e7856990741a61ab1976bc089c758984c97a98b2ad7951cf0b7b0dbfdd506edb8bdb7e8088d743057aaddd8d19083140115a90efad762f4edb59673d6aafc5bdadb8b3e1b77a7eda6d26bd65d4c290a983087b85e58466994e26b692b594e18b88f27577248046f38c67f9c9e5e0d5fec9c1f9e8625c9c0ea61f4eaffb936292d793a9bd2c55fe8c6e7d90296abc414a30729a9a2c16578bd9e5b2bcaabde1d25f547ea51b77c47e215ec8831926f960b42c4a1261b74c113686648b9d5c998f0e26d68371dda1f3ce94b869d57412736dcaaa7193646b43b3aa2e284f96b0261701fa2b2147adad404175c839bd1afd9fbd77a7e3b11e51b5052d84d94d1e5822e8083f10a34061abd5421e849a06f18fce995decc2d9b2b168048d30f1e3169a04455714720cc99fcf7328a7b4235efb9b08c165890cf9e71ab5f6342d1308eeca52a2b006647ad905846b693e2171c453d6ee44714a7fb9784ee360d1cc4f02569d4d34ed2561d246da2d03345a6bd9881a1e3656bc6c84a8d0869768df0cc16823c89a61d6f0333fec36a2d6326ad771a70cd232c85076cbb457b7b61aed9d70ed51e7d1e7bdc73f08369efa1b4fbddefd467bb38cda250dd26c1341ec2fa5e5356d321975debf641c704253675611d4c34ca4675e3787f36aff082d54e46503536830bc662e922866b4b3f92cb63b84910a4efb218319182dc7511a47092644a8cf7336e77e7ad148ae9a18004121b12c2b0aee4a173a70c05fe60da125e34188c12094c421a2152c6c39dc59c4c0e2a6cdf6f40ded14e050a358890de552899d30a9cd54b6e18fa975d555c09c298b32514425218abc7af9e164f7e47a34ced15364528d1cf219e662a14ff1600ecef4beb91aeb38c51a710fcccbf58376bb68d408aa6654d651d25a4328c8c5ae74bf5655d6e3d184c1118778a977d33cc444928d44a8115946a4896d071a6043b2cc0f1b15598e62a0c918515ce3f164341ee7b9cc066964615615210dcb6f05416ca5a4d56e757a59bb174659053258f3982eba4e0a37594a58aca044b62d34d04080d0028b0034481bd1da32dd29a3cd2adaae92ed65b6e3751efabd8745bc5e673b65bc31f6b279d09d7aad226c4f2b6f5e4a5542338330f0eb4a1ecd8285723e974d0c1c452836a524076b49afe80310cde00c2fb03f362727aa740b49d56d67983d40a4b0af81c0eda59dbe80673274e008cb5f6ff34d416a1bc6279917c5d3303a6ff883b4bdf0311365aca29a58b01249b051ee1478639a8539892070a473c205074c07f054bbe419b2c4749bb2d566f61c4942d8ed2669ba0aaa629b3ab421aa25c6876527212a04ab690be4237661da87c17890e319ab3b12199d159213309be95d6fa2b1d24778668b993d81aea5085d8ecd8cd2f7423456e505ba19b3d54d3b1b61d6f3223144129161da909c757043a486e492e84cc2d7457407da72341af6fbfdd1688cb10047c02c350411a9599d06864e609ee3a2bbd96936d1c8ee261a669869ba9182592fee6e44ed5e94b66a3f2cd40548709c25c04f05352cf023d8496a86b656e002636804f1d24f1a2183417d33c8b0ac313232d08d849f4c66d2ac159a22623830d994244dca727253c7f867103a9fe377db8523b91529c665e45e14d49c8ce7e7a7e7fbeff667b3054bc066afd16af5f0af11de34319f4ec65783e56cbe9c8f96f371359b364b7dbea62c740ac659c379552063298cfd3aaea36bbf3d69adcd82d8502861a5a9d75531697349298e8990abd33d2b7f9c606c3356b34a894966daafd235591a9705419252c40c8844403b1d686d504edda967b04ea2b521dda9a665716add935986c94533b8d0357974461381291101a13a0946ed2af096895fc77eb393a51af26296c620400fd3a84df93c3e921610e8f6fd7ab9a8bcc26b35d20d6cf166b69ef4b6a3ceda32d0a7091c99361e0dcf4e2f68ee158c2cda658aead21b5f0faf0e8f2f0f8fab7991a03c83cc8f5a71d60de256a0a90fe50521c36cf4f3d96c3219cd74097b89d7d86eb7654ee8466b0b59ab8d4781494792ce67eab5c635730201d2f3baa481d8d3de4e85589bf0585cd6f8e01e60d48b2374a906eb539f532345c296e91267450a320e7da1364d80c368d607f529412901dc6ba21691582d1668863c6e85615616cdcbf3c1fb7787fbef0f0e3f1c0c060356193dc28d30444107089734c97aed2ed26f787edecce75e3e0feb79542feac5b89af4f3f1759d8fa34631eb5f4f078362369d0c50e0fdc1250275340ae269d259603f144d7c169de89470d1b808b2cbcc1800370cda2642824bbad2e0c5a116a74c1aad4faba8ba0c13fe900fa1023438524d9a51a0007b9b66952487039b2999a2028ca9215f92b9318be2eb76fbe5d5703067312cb77a6bdd4e96a4d1e6e6dacef6c6837b9bcf1edffbd1a74ffee4f3273ffdf8fecf3e79f283470f1eaff736da59603d8ac392ed021253a01991bbe0231d8a65b3587ab8774d1013260b262148bc1003899e3d008ea22a2950fb55032721f183348cda69ba96b6d6032f2b27cbc6b4f4e705865a1aa65ec096795ecbf3b2206ca5e8fa38c3f863b219174c8216bd7020cf89c87f4d936eb7f77f011dbc19257bf509a20000000049454e44ae426082, 'Geronimo', 'esto es una pruebita', '2236976994', 'image/png', '2026-10-19 00:14:09.000000', '6188232d-1705-434e-a04f-a1d76d23c3e9');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `UKm3dykycv19jeq15e8xsi1atr5` (`uuid`);

--
-- Indices de la tabla `caracteristica`
--
ALTER TABLE `caracteristica`
  ADD PRIMARY KEY (`id_caracteristica`),
  ADD UNIQUE KEY `UK43dj7iq3exe0ryadscd77mxbs` (`uuid`);

--
-- Indices de la tabla `certificacion`
--
ALTER TABLE `certificacion`
  ADD PRIMARY KEY (`id_certificacion`),
  ADD UNIQUE KEY `UK2g0wrp2149pupy22ju4wpttbg` (`uuid`),
  ADD KEY `FK7knd03pi88tosb3wicxncp81c` (`id_admin_revisor`),
  ADD KEY `FKr55m7box7l2l866en7plruduk` (`id_tecnico`);

--
-- Indices de la tabla `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id_chat`),
  ADD KEY `FKmfhjx839tw7b2tby5garfv576` (`id_tecnico`),
  ADD KEY `FK1yd9hrld2mou4jdaiqliu88ik` (`id_usuario`);

--
-- Indices de la tabla `credencial`
--
ALTER TABLE `credencial`
  ADD PRIMARY KEY (`credencial_id`),
  ADD UNIQUE KEY `UKkal4f1uym3c6ttymfx1lrvkui` (`username`),
  ADD UNIQUE KEY `UKpog5dow3mxrqivsn98edomnca` (`id_usuario`);

--
-- Indices de la tabla `credencial_rol`
--
ALTER TABLE `credencial_rol`
  ADD PRIMARY KEY (`credencial_id`,`id_role`),
  ADD KEY `FKpd4bekonnhadgw4hqjbf36jbx` (`id_role`);

--
-- Indices de la tabla `denuncia`
--
ALTER TABLE `denuncia`
  ADD PRIMARY KEY (`id_denuncia`),
  ADD UNIQUE KEY `UKr97bx6lskfa7181mc2kyok2fx` (`uuid`),
  ADD KEY `FKedppucslrjcvkic9b9y8wc3pw` (`administrador_encargado`),
  ADD KEY `FK4fc6ogyys8twtmu8uamo1ap16` (`id_denunciado`),
  ADD KEY `FKpgm4jg3tw8vldkgfvik3i5d3p` (`id_denunciante`);

--
-- Indices de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  ADD PRIMARY KEY (`id_especialidad`),
  ADD UNIQUE KEY `UKrhm7jnafmiajhw43qved0inrn` (`uuid`);

--
-- Indices de la tabla `especialidad_tecnico`
--
ALTER TABLE `especialidad_tecnico`
  ADD KEY `FKt7tjjghke4p5v3nauq8hnwjyb` (`id_especialidad`),
  ADD KEY `FKh8c7bf8n16f69k0hya55orwg9` (`id_tecnico`);

--
-- Indices de la tabla `habilidad`
--
ALTER TABLE `habilidad`
  ADD PRIMARY KEY (`id_habilidad`),
  ADD UNIQUE KEY `UK3ej83m8656nvjo33k46nb1r8i` (`uuid`);

--
-- Indices de la tabla `habilidad_especialidad`
--
ALTER TABLE `habilidad_especialidad`
  ADD KEY `FKotnte27wg97k0mnmi1wsv3a7r` (`id_especialidad`),
  ADD KEY `FKpqylas7yu3b29b72h4tccwn6w` (`id_habilidad`);

--
-- Indices de la tabla `habilidad_tecnico`
--
ALTER TABLE `habilidad_tecnico`
  ADD KEY `FK8kacwu0r6ef4ebyv938dih1mu` (`id_habilidad`),
  ADD KEY `FK14s57rijv4u3rb3t7rfy3ot40` (`id_tecnico`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id_mensaje`),
  ADD KEY `FKe19chdnxesv3itlf0v128hmrp` (`id_chat`),
  ADD KEY `FKmipwrttnexwj61hkitg4sdvr2` (`id_sender`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`),
  ADD UNIQUE KEY `UK95x4dibob9y7v0n38ctudmoh3` (`uuid`),
  ADD UNIQUE KEY `UKbc2x9lcyvwdoy417hxlnlq5q4` (`id_trabajo`);

--
-- Indices de la tabla `permit`
--
ALTER TABLE `permit`
  ADD PRIMARY KEY (`id_permit`),
  ADD UNIQUE KEY `UK7fqhqc0bxljtajffs68a9b1j` (`permiso`);

--
-- Indices de la tabla `portfolio`
--
ALTER TABLE `portfolio`
  ADD PRIMARY KEY (`id_portfolio`),
  ADD UNIQUE KEY `UKfkvfkavvp1j775h86rfb5iv0m` (`uuid`),
  ADD KEY `FK8yo07coyy7nwt4opjvim8l1o1` (`id_especialidad`),
  ADD KEY `FKdtr49vtt968lp9uwgyblywbv7` (`id_tecnico`);

--
-- Indices de la tabla `presupuesto`
--
ALTER TABLE `presupuesto`
  ADD PRIMARY KEY (`id_presupuesto`),
  ADD UNIQUE KEY `UKh0y6g5ihophql8qi53imkmut5` (`uuid`),
  ADD KEY `FKdsmvld5uqkt421se9qph8co0e` (`id_chat`),
  ADD KEY `FKsp432pndlhmxoinib1g0evcrd` (`id_tecnico`),
  ADD KEY `FKccrpu7i0j1b5r9tgnq97xq6b4` (`id_usuario`);

--
-- Indices de la tabla `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id_rating`),
  ADD UNIQUE KEY `UKsiuciuw7v2iwrhn0vk5vdk360` (`uuid`),
  ADD KEY `FKj4ctwpxjykxlpx1tc1p21sves` (`id_usuario_destinatario`),
  ADD KEY `FK8t5gwjwghuueca87ulwejr6p8` (`id_usuario_remitente`);

--
-- Indices de la tabla `rating_caracteristica`
--
ALTER TABLE `rating_caracteristica`
  ADD KEY `FK3ctusiegyd3bkvmd5b8sjf90` (`id_caracteristica`),
  ADD KEY `FK5po1jfnni5varu51d4uxsinmr` (`id_rating`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_role`),
  ADD UNIQUE KEY `UKgidd9huji2j14xop37v9dc7li` (`rol`);

--
-- Indices de la tabla `rubro`
--
ALTER TABLE `rubro`
  ADD PRIMARY KEY (`id_rubro`),
  ADD UNIQUE KEY `UK5yffd86m0hw8hi4kpf7sq40xm` (`uuid`);

--
-- Indices de la tabla `rubro_especialidad`
--
ALTER TABLE `rubro_especialidad`
  ADD KEY `FKp9y0ajqia6j400foiu2t1lnc0` (`id_especialidad`),
  ADD KEY `FKedxaioki15re2eih79kmp8qjj` (`id_rubro`);

--
-- Indices de la tabla `tecnico`
--
ALTER TABLE `tecnico`
  ADD PRIMARY KEY (`id_tecnico`),
  ADD UNIQUE KEY `UK3fngl95qr0xsjgeb5j3e291j4` (`uuid`),
  ADD UNIQUE KEY `UKkoxndpnthrvgvwuigmh9f467l` (`id_usuario`);

--
-- Indices de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD PRIMARY KEY (`id_trabajo`),
  ADD UNIQUE KEY `UKpg1p6dq23yabrwamch2ncyrfd` (`uuid`),
  ADD UNIQUE KEY `UKj4ty9v2iacd1be5c3j6gw2luh` (`id_presupuesto`);

--
-- Indices de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD PRIMARY KEY (`id_ubicacion`),
  ADD UNIQUE KEY `UKqwpyqibjgmaaav4a009cd5k0` (`uuid`),
  ADD KEY `FKdcu2t0v1s5qeyc4vp197ravvw` (`id_usuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UKma71x4n4tydibsd9qt0m71le7` (`dni`),
  ADD UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`),
  ADD UNIQUE KEY `UKl8q1tlaiyb03eemicl6qgvddu` (`uuid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `caracteristica`
--
ALTER TABLE `caracteristica`
  MODIFY `id_caracteristica` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `certificacion`
--
ALTER TABLE `certificacion`
  MODIFY `id_certificacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `chat`
--
ALTER TABLE `chat`
  MODIFY `id_chat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `credencial`
--
ALTER TABLE `credencial`
  MODIFY `credencial_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `denuncia`
--
ALTER TABLE `denuncia`
  MODIFY `id_denuncia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  MODIFY `id_especialidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT de la tabla `habilidad`
--
ALTER TABLE `habilidad`
  MODIFY `id_habilidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=231;

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id_mensaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `permit`
--
ALTER TABLE `permit`
  MODIFY `id_permit` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `portfolio`
--
ALTER TABLE `portfolio`
  MODIFY `id_portfolio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `presupuesto`
--
ALTER TABLE `presupuesto`
  MODIFY `id_presupuesto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rating`
--
ALTER TABLE `rating`
  MODIFY `id_rating` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `rubro`
--
ALTER TABLE `rubro`
  MODIFY `id_rubro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tecnico`
--
ALTER TABLE `tecnico`
  MODIFY `id_tecnico` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  MODIFY `id_trabajo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  MODIFY `id_ubicacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `certificacion`
--
ALTER TABLE `certificacion`
  ADD CONSTRAINT `FK7knd03pi88tosb3wicxncp81c` FOREIGN KEY (`id_admin_revisor`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKr55m7box7l2l866en7plruduk` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `FK1yd9hrld2mou4jdaiqliu88ik` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKmfhjx839tw7b2tby5garfv576` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `credencial`
--
ALTER TABLE `credencial`
  ADD CONSTRAINT `FK2n9ltmbqhg4w8481ofpdhjp4t` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `credencial_rol`
--
ALTER TABLE `credencial_rol`
  ADD CONSTRAINT `FKoceysl0epyj5k3i59s9h6yej3` FOREIGN KEY (`credencial_id`) REFERENCES `credencial` (`credencial_id`),
  ADD CONSTRAINT `FKpd4bekonnhadgw4hqjbf36jbx` FOREIGN KEY (`id_role`) REFERENCES `rol` (`id_role`);

--
-- Filtros para la tabla `denuncia`
--
ALTER TABLE `denuncia`
  ADD CONSTRAINT `FK4fc6ogyys8twtmu8uamo1ap16` FOREIGN KEY (`id_denunciado`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKedppucslrjcvkic9b9y8wc3pw` FOREIGN KEY (`administrador_encargado`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKpgm4jg3tw8vldkgfvik3i5d3p` FOREIGN KEY (`id_denunciante`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `especialidad_tecnico`
--
ALTER TABLE `especialidad_tecnico`
  ADD CONSTRAINT `FKh8c7bf8n16f69k0hya55orwg9` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`),
  ADD CONSTRAINT `FKt7tjjghke4p5v3nauq8hnwjyb` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`);

--
-- Filtros para la tabla `habilidad_especialidad`
--
ALTER TABLE `habilidad_especialidad`
  ADD CONSTRAINT `FKotnte27wg97k0mnmi1wsv3a7r` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`),
  ADD CONSTRAINT `FKpqylas7yu3b29b72h4tccwn6w` FOREIGN KEY (`id_habilidad`) REFERENCES `habilidad` (`id_habilidad`);

--
-- Filtros para la tabla `habilidad_tecnico`
--
ALTER TABLE `habilidad_tecnico`
  ADD CONSTRAINT `FK14s57rijv4u3rb3t7rfy3ot40` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`),
  ADD CONSTRAINT `FK8kacwu0r6ef4ebyv938dih1mu` FOREIGN KEY (`id_habilidad`) REFERENCES `habilidad` (`id_habilidad`);

--
-- Filtros para la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD CONSTRAINT `FKe19chdnxesv3itlf0v128hmrp` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id_chat`),
  ADD CONSTRAINT `FKmipwrttnexwj61hkitg4sdvr2` FOREIGN KEY (`id_sender`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `FK4kawmu0llvo61fbd082fygrql` FOREIGN KEY (`id_trabajo`) REFERENCES `trabajo` (`id_trabajo`);

--
-- Filtros para la tabla `portfolio`
--
ALTER TABLE `portfolio`
  ADD CONSTRAINT `FK8yo07coyy7nwt4opjvim8l1o1` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`),
  ADD CONSTRAINT `FKdtr49vtt968lp9uwgyblywbv7` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `presupuesto`
--
ALTER TABLE `presupuesto`
  ADD CONSTRAINT `FKccrpu7i0j1b5r9tgnq97xq6b4` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKdsmvld5uqkt421se9qph8co0e` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id_chat`),
  ADD CONSTRAINT `FKsp432pndlhmxoinib1g0evcrd` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `FK8t5gwjwghuueca87ulwejr6p8` FOREIGN KEY (`id_usuario_remitente`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKj4ctwpxjykxlpx1tc1p21sves` FOREIGN KEY (`id_usuario_destinatario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `rating_caracteristica`
--
ALTER TABLE `rating_caracteristica`
  ADD CONSTRAINT `FK3ctusiegyd3bkvmd5b8sjf90` FOREIGN KEY (`id_caracteristica`) REFERENCES `caracteristica` (`id_caracteristica`),
  ADD CONSTRAINT `FK5po1jfnni5varu51d4uxsinmr` FOREIGN KEY (`id_rating`) REFERENCES `rating` (`id_rating`);

--
-- Filtros para la tabla `rubro_especialidad`
--
ALTER TABLE `rubro_especialidad`
  ADD CONSTRAINT `FKedxaioki15re2eih79kmp8qjj` FOREIGN KEY (`id_rubro`) REFERENCES `rubro` (`id_rubro`),
  ADD CONSTRAINT `FKp9y0ajqia6j400foiu2t1lnc0` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`);

--
-- Filtros para la tabla `tecnico`
--
ALTER TABLE `tecnico`
  ADD CONSTRAINT `FKglsl21216xiqdl2rtw9nijyji` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD CONSTRAINT `FKfx52xtnnowpsg4bp7ohm98jxo` FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

--
-- Filtros para la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD CONSTRAINT `FKdcu2t0v1s5qeyc4vp197ravvw` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
