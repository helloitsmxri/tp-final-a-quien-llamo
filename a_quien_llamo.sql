-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-10-2026 a las 15:43:11
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credencial_rol`
--

CREATE TABLE `credencial_rol` (
  `credencial_id` int(11) NOT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
