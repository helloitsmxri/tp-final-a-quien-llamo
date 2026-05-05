DROP DATABASE IF EXISTS a_quien_llamo;
CREATE DATABASE a_quien_llamo;
USE a_quien_llamo;

CREATE TABLE Usuario (
id_usuario INT AUTO_INCREMENT PRIMARY KEY,
tipo_imagen VARCHAR(50) NOT NULL,
foto MEDIUMBLOB,
dni VARCHAR(8) NOT NULL,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL,
clave VARCHAR(100) NOT NULL,
telefono VARCHAR(50) NOT NULL,
fecha_nacimiento DATE NOT NULL,
fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
ultima_actividad TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
sobre_mi TEXT NOT NULL
);

CREATE UNIQUE INDEX idx_dni ON Usuario(dni);

CREATE TABLE Ubicacion (
id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
id_usuario INT,
codigo_postal VARCHAR(10) NOT NULL,
provincia VARCHAR(50) NOT NULL,
ciudad VARCHAR(50) NOT NULL,
calle VARCHAR(50) NOT NULL,
numero INT,
piso INT,
numero_piso INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Tecnico (
id_tecnico INT AUTO_INCREMENT PRIMARY KEY,
id_usuario INT,
cuit VARCHAR(20) NOT NULL,
descripcion_trabajo TEXT,
proyectos TEXT,
FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Certificacion (
id_certificacion INT AUTO_INCREMENT PRIMARY KEY,
id_tecnico INT NOT NULL,
num_matricula VARCHAR(100) NOT NULL,
ente_otorgorador VARCHAR(100) NOT NULL,
fecha_vencimiento DATE,
tipo_imagen VARCHAR(50) NOT NULL,
imagen_certificado MEDIUMBLOB NOT NULL,
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)
);

CREATE TABLE Portfolio (
id_portfolio INT AUTO_INCREMENT PRIMARY KEY,
id_tecnico INT NOT NULL,
id_especialidad INT NOT NULL,
notas_aspirante TEXT, -- breve explicación del técnico sobre su experiencia
enlace_externo VARCHAR(255),
tipo_archivo VARCHAR(100),
archivo_adjunto MEDIUMBLOB,
estado_verificacion ENUM ('Pendiente', 'Rechazado', 'Aprobado') DEFAULT 'Pendiente',
notas_admin TEXT, -- lo usamos para anotar y le decimos al user xq lo rechazamos
fecha_entrega TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico) ON DELETE CASCADE,
FOREIGN KEY (id_especialidad) REFERENCES Especialidad(id_especialidad)
);

CREATE TABLE Chat (
id_chat INT AUTO_INCREMENT PRIMARY KEY,
id_usuario INT,
id_tecnico INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)
);

CREATE UNIQUE INDEX idx_user_tech ON Chat(id_usuario, id_tecnico);

CREATE TABLE Mensaje(
id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
id_chat INT,
mensaje TEXT NOT NULL,
FOREIGN KEY (id_chat) REFERENCES Chat(id_chat)
);

CREATE TABLE Presupuesto (
id_presupuesto INT AUTO_INCREMENT PRIMARY KEY,
id_usuario INT,
id_tecnico INT,
precio_estimado DECIMAL (10,2) NOT NULL,
descripcion_presupuesto TEXT NOT NULL,
FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario),
FOREIGN KEY(id_tecnico) REFERENCES Tecnico(id_tecnico)
);

CREATE TABLE Trabajo (
id_trabajo INT AUTO_INCREMENT PRIMARY KEY,
id_presupuesto INT,
descripcion_trabajo TEXT NOT NULL,
fecha_estimada_inicio DATE NOT NULL,
fecha_estimada_fin DATE NOT NULL,
estado_trabajo ENUM('Pendiente', 'Finalizado', 'Cancelado', 'En proceso') NOT NULL,
FOREIGN KEY (id_presupuesto) REFERENCES Presupuesto(id_presupuesto)
);

CREATE TABLE Pago (
id_pago INT PRIMARY KEY AUTO_INCREMENT,
id_trabajo INT,
metodo_pago ENUM('Transferencia', 'Efectivo', 'Debito', 'Credito') NOT NULL,
fecha_pago DATE,
estado ENUM('Confirmado', 'Rechazado', 'Pendiente de revision'),
FOREIGN KEY (id_trabajo) REFERENCES Trabajo(id_trabajo)
);

CREATE TABLE Rating (
id_rating INT AUTO_INCREMENT PRIMARY KEY,
id_usuario_remitente INT,
id_usuario_destinatario INT,
valoracion INT NOT NULL,
descripcion TEXT,
tipo_foto VARCHAR(50),
foto MEDIUMBLOB,
FOREIGN KEY (id_usuario_remitente) REFERENCES Usuario(id_usuario),
FOREIGN KEY (id_usuario_destinatario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Caracteristica (
id_caracteristica INT AUTO_INCREMENT PRIMARY KEY,
valor_adjetivo VARCHAR(50)
);

CREATE TABLE Rating_Caracteristica (
id_rating INT, 
id_caracteristica INT,
PRIMARY KEY(id_rating, id_caracteristica),
FOREIGN KEY(id_rating) REFERENCES Rating(id_rating),
FOREIGN KEY(id_caracteristica) REFERENCES Caracteristica(id_caracteristica)
);

CREATE TABLE Habilidad (
id_habilidad INT AUTO_INCREMENT PRIMARY KEY,
nombre_habilidad VARCHAR(50)
);

CREATE TABLE Especialidad (
id_especialidad INT AUTO_INCREMENT PRIMARY KEY,
nombre_especialidad VARCHAR(50),
tipo_validacion ENUM('Certificado', 'Portfolio', 'Ninguna') DEFAULT 'Ninguna'
);

CREATE TABLE Habilidad_Especialidad (
id_habilidad INT,
id_especialidad INT,
PRIMARY KEY(id_habilidad, id_especialidad),
FOREIGN KEY (id_habilidad) REFERENCES Habilidad(id_habilidad),
FOREIGN KEY (id_especialidad) REFERENCES Especialidad (id_especialidad)
);

CREATE TABLE Habilidad_Tecnico (
id_tecnico INT,
id_habilidad INT,
PRIMARY KEY (id_tecnico, id_habilidad),
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico),
FOREIGN KEY (id_habilidad) REFERENCES Habilidad(id_habilidad) 
);

CREATE TABLE Especialidad_Tecnico (
id_tecnico INT,
id_especialidad INT,
PRIMARY KEY (id_tecnico, id_especialidad),
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico),
FOREIGN KEY (id_especialidad) REFERENCES Especialidad(id_especialidad)
);

CREATE TABLE Rubro (
id_rubro INT AUTO_INCREMENT PRIMARY KEY,
nombre_rubro VARCHAR(50)
);

CREATE TABLE Rubro_Especialidad (
id_especialidad INT,
id_rubro INT,
PRIMARY KEY(id_especialidad, id_rubro),
FOREIGN KEY (id_especialidad) REFERENCES Especialidad (id_especialidad),
FOREIGN KEY(id_rubro) REFERENCES Rubro (id_rubro)
);

CREATE TABLE Administrador (
id_admin INT AUTO_INCREMENT PRIMARY KEY,
nombre_usuario VARCHAR(50) NOT NULL,
clave VARCHAR(20) NOT NULL
);

CREATE TABLE Denuncia (
id_denuncia INT AUTO_INCREMENT PRIMARY KEY,
administrador_encargado INT,
nombre_denunciante VARCHAR(50),
apellido_denunciante VARCHAR(50),
dni_denunciante VARCHAR(8),
telefono_denunciante VARCHAR(20),
nombre_denunciado VARCHAR(50),
apellido_denunciado VARCHAR(50),
dni_denunciado VARCHAR(8),
telefono_denunciado VARCHAR(20),
estado_denuncia ENUM('Comprobado','En proceso','Pendiente') NOT NULL,
motivo_denuncia TEXT NOT NULL,
tipo_foto VARCHAR(50),
foto MEDIUMBLOB,
fecha_denuncia TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
FOREIGN KEY (administrador_encargado) REFERENCES Administrador (id_admin)
);

INSERT INTO Rubro(nombre_rubro)
VALUES ('Hogar'), 
('Construcción'), 
('Mascotas'), 
('Eventos'), 
('Belleza y Salud'), 
('Transporte'), 
('Arte y Diseño'), 
('Tecnología'), 
('Educación'), 
('Gastronomía');

INSERT INTO Especialidad (nombre_especialidad, tipo_validacion) VALUES 
('Apoyo Escolar', 'Certificado'),
('Coaching y Desarrollo Personal', 'Certificado'),
('Idiomas', 'Certificado'),
('Capacitación Profesional', 'Certificado'),
('Baristería', 'Certificado'),
('Gastronomía y Cocina', 'Certificado'),
('Catering', 'Porfolio'),
('Decoración de Eventos', 'Portfolio'),
('Floristería', 'Ninguna'),
('Fotografía', 'Portfolio'),
('Video', 'Portfolio'),
('Maquillaje', 'Portfolio'),
('Música y Entretenimiento', 'Ninguna'),
('Pastelería', 'Portfolio'),
('Servicio de Mesa', 'Ninguna'),
('Souvenirs y Regalería', 'Ninguna'),
('Panadería Artesanal', 'Certificado'),
('Viandas y Viandas Saludables', 'Ninguna'),
('Climatización y Aire Acondicionado', 'Certificado'),
('Albañilería', 'Ninguna'),
('Carpintería', 'Certificado'),
('Cerrajería', 'Certificado'),
('Electricidad', 'Certificado'),
('Fumigación y Control de Plagas', 'Certificado'),
('Instalaciones de Gas', 'Certificado'),
('Impermeabilización', 'Certificado'),
('Interiorismo y Decoración', 'Portfolio'),
('Jardinería y Paisajismo', 'Portfolio'),
('Servicios de Limpieza', 'Ninguna'),
('Persianas y Cortinas', 'Ninguna'),
('Instalación de Pisos', 'Ninguna'),
('Pintura', 'Ninguna'),
('Plomería' ,'Certificado'),
('Mantenimiento de Piscinas', 'Ninguna'),
('Sistemas de Seguridad', 'Certificado'),
('Techos','Ninguna'),
('Toldos y Cerramientos', 'Ninguna'),
('Vidriería', 'Ninguna'),
('Zinguería', 'Ninguna') ,
('Adiestramiento Canino', 'Certificado'),
('Paseo y Cuidado de Mascotas', 'Ninguna'),
('Estética y Peluquería Canina' ,'Ninguna'),
('Depilación', 'Certificado'),
('Entrenamiento Personal', 'Certificado'),
('Estética y Cosmetología', 'Certificado'),
('Manicura y Pedicuría', 'Certificado'),
('Masoterapia y Masajes', 'Certificado'),
('Peluquería', 'Certificado'),
('Fletes', 'Ninguna'),
('Mudanzas', 'Ninguna'),
('Logística y Reparto', 'Ninguna'),
('Programación y Desarrollo','Portfolio'),
('Diseño Web y UX/UI', 'Portfolio'),
('Soporte Técnico', 'Certificado'),
('Análisis de Sistemas', 'Certificado'),
('Arquitectura', 'Certificado'),
('Construcción en Seco', 'Ninguna'),
('Herrería y Metalurgia', 'Certificado'),
('Diseño Gráfico', 'Portfolio'),
('Ilustración y Arte', 'Portfolio'),
('Moda e Indumentaria', 'Portfolio');

Select * from Especialidad;
-- HABILIDADES

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Muros'), ('Reparación de grietas'), ('Revoques'), ('Contrapiso'), ('Carpeta niveladora'), ('Cerámicos'), ('Porcelanato'), ('Piedra natural'), ('Asadores'), ('Tabiques'), ('Zócalos'), ('Pozos'), ('Frentes'), ('Microcemento'),
('Redes internas de gas'), ('Detección de fugas'), ('Gas natural'), ('Gas envasado'), ('Instalación de Calderas'), ('Estufas'), ('Habilitaciones de gas'), ('Conversión de combustible'),
('Pérdidas de agua'), ('Destapaciones'), ('Sanitarios'), ('Presión de agua'), ('Cloacas'), ('Conexión domiciliaria'), ('Griferías'), ('Colectores solares'),
('Chapa'), ('Tejas'), ('Membrana en rollo'), ('Filtraciones'), ('Techos verdes'), ('Policarbonato'), ('Zinc'), ('Cumbrera'), ('Aislación térmica'), ('Drenajes'),
('Tableros eléctricos'), ('Baja tensión'), ('Media tensión'), ('LED'), ('Automatización'), ('Domótica'), ('Cableado estructurado'), ('Puesta a tierra'), ('Solar fotovoltaica'), ('Detección de fallas'),
('Semanal'),('Post-Obra'), ('Diaria');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Java'), ('Python'), ('JavaScript'), ('TypeScript'), ('C#'), ('Go'), ('Kotlin'), ('APIs REST'), ('GraphQL'), ('Microservicios'), ('Spring Boot'), ('React'), ('Angular'), ('Node.js'), ('Git'), ('CI/CD'),
('Machine learning'), ('Deep learning'), ('NLP'), ('Análisis de datos'), ('SQL avanzado'), ('Power BI'), ('Tableau'), ('Modelos predictivos'), ('Chatbots'),
('Wireframing'), ('Prototipado'), ('UX Research'), ('UI Design'), ('Figma'), ('Accesibilidad web'), ('Design System'),
('Identidad de marca'), ('Logotipos'), ('Manual de marca'), ('Packaging'), ('Flyer y folletería'), ('Infografías'), ('Motion graphics'), ('Señalética'), ('Godot'), ('Desarrollo de videojuegos');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Café de especialidad'), ('Latte art'), ('Espresso'), ('Cold brew'), ('Cata de café'),
('Vegano'), ('Asado y parrilla'), ('Finger food'), ('Comida Sin TACC'), ('Kosher'), ('Halal'), ('Meal prep'),
('Vegetariano'), ('Cena de autor'), ('Comida Internacional'), ('Cocina Saludable'),
('Masa madre'), ('Pan de campo'), ('Focaccia'), ('Brioche'), ('Facturas'),
('Tortas de boda'), ('Cupcakes'), ('Macarons'), ('Cheesecake'), ('Cake design'),
('Mozos'), ('Maitre'), ('Sommelier'), ('Barman'), ('Coctelería'), ('Protocolo de mesa'),
('Iluminación ambiental'), ('Globología'), ('Centros de mesa'), ('Arcos florales'), ('Photo wall');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Limpiezas faciales'), ('Microdermoabrasión'), ('Peeling químico'), ('Radiofrecuencia'), ('Cavitación'), ('Micropigmentación'), ('Microblading'), ('Lifting de pestañas'),
('Manicura Rusa'), ('Uñas esculpidas'), ('Nail art'), ('Polygel'), ('Pedicuria spa'),
('Masaje Deportivo'), ('Masaje Descontracturante'), ('Reflexología'), ('Drenaje linfático'), ('Aromaterapia'),
('Corte de cabello'), ('Coloración'), ('Mechas Balayage'), ('Alisado permanente'), ('Barbería'), ('Tratamientos capilares'),
('Matemática'), ('Física'), ('Química'), ('Biología'), ('Literatura'), ('Economía'), ('Derecho'), ('Ingreso universitario'),
('IELTS/TOEFL'), ('Traducción técnica'), ('Interpretación'), ('Conversación de idiomas');

INSERT INTO Habilidad (nombre_habilidad) VALUES
('Bodas'), ('15 años'), ('Producto'), ('Corporativa'), ('Mascotas'), ('Video de bodas'),
('Drone'),('Videoclip'),('Deportes'),('Edición'),('Retratos'),('Artística'),
('Streaming'),('Coberturas'),('Aérea'),('Recitales'),('Culinaria'),('Lightroom'),
('Premiere Pro'),('Photoshop'),('DaVinci Resolve'),('After Effects'),('Fotografía nocturna'),
('Fotografía de eventos'),('Fotografía de moda'),('Fotografía inmobiliaria'),('Fotografía gastronómica'),
('Fotografía documental'),('Fotoperiodismo'),('Color grading'),
('Iluminación'),('Manejo de cámara'),('Dirección de fotografía'),
('Edición de sonido'),('Motion graphics'),('Sesiones exteriores'),
('Sesiones de estudio'),('Cobertura de shows'),('Video publicitario'),('Contenido para marcas');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Obediencia básica'), ('Corrección de conducta'), ('Socialización de cachorros'),
('Paseos grupales'), ('Hospedaje de mascotas'), ('Cat sitting'), ('Transporte veterinario'),
('Baño y secado'), ('Corte de raza'), ('Deslanado'), ('Geriátricas (mascotas)'),
('Embalaje de mudanza'), ('Armado de muebles'), ('Seguro de cargas'), ('Transporte de obras de arte'),
('Reparto Last mile'), ('Logística en frío'), ('Trazabilidad de envíos'),
('Apertura de cerraduras'), ('Cajas de seguridad'), ('Cerraduras digitales'), ('Llave maestra');

INSERT INTO Rubro_Especialidad(id_especialidad, id_rubro)
VALUES
-- RUBRO 1: HOGAR
(10, 1), (11, 1), (19, 1), (20, 1), (21, 1), (22, 1), (23, 1), (24, 1), (25, 1),
(26, 1), (27, 1), (28, 1), (29, 1), (30, 1), (31, 1), (32, 1), (33, 1), (34, 1), (35, 1),
(36, 1), (37, 1), (38, 1), (39, 1), (45, 1), (49, 1), (50, 1), (51, 1), (56, 1), (57, 1), (58, 1),

-- RUBRO 2: CONSTRUCCIÓN
(19, 2), (20, 2), (21, 2), (22, 2), (23, 2), (24, 2), (25, 2), (26, 2), (27, 2), 
(28, 2), (29, 2), (30, 2), (31, 2), (32, 2), (33, 2), (34, 2), (35, 2), (36, 2), 
(37, 2), (38, 2), (39, 2), (49, 2), (50, 2), (51, 2), (56, 2), (57, 2), (58, 2),

-- RUBRO 3: MASCOTAS
(40, 3), (41, 3), (42, 3),

-- RUBRO 4: EVENTOS
(5, 4), (6, 4), (7, 4), (8, 4), (9, 4), (10, 4), (11, 4), (12, 4), (13, 4), (15, 4),
(16, 4), (29, 4), (45, 4), (46, 4), (48, 4), (61, 4),

-- RUBRO 5: BELLEZA Y SALUD
(2, 5), (12, 5), (43, 5), (44, 5), (45, 5), (46, 5), (47, 5), (48, 5),

-- RUBRO 6: TRANSPORTE
(49, 6), (50, 6), (51, 6),

-- RUBRO 7: ARTE Y DISEÑO
(10, 7), (11, 7), (13, 7), (16, 7), (21, 7), (26, 7), (27, 7), (53, 7), (56, 7), 
(58, 7), (59, 7), (60, 7), (61, 7),

-- RUBRO 8: TECNOLOGÍA
(35, 8), (52, 8), (53, 8), (54, 8), (55, 8),

-- RUBRO 9: EDUCACIÓN
(1, 9), (2, 9), (3, 9), (4, 9), (13, 9), (52, 9), (60, 9),

-- RUBRO 10: GASTRONOMÍA
(5, 10), (6, 10), (7, 10), (14, 10), (17, 10), (18, 10);

-- 1. TECNOLOGÍA, DISEÑO Y ARTE
-- Programación y Desarrollo (ID 52) + Videojuegos y Análisis
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(52, 54), (52, 55), (52, 56), (52, 57), (52, 58), (52, 59), (52, 60), -- Lenguajes
(52, 61), (52, 62), (52, 63), (52, 64), (52, 65), (52, 66), (52, 67), (52, 68), (52, 69), -- Frameworks/DevOps
(52, 74), (52, 78), (52, 94), (52, 95); -- SQL, Chatbots, Godot, Videojuegos

-- Diseño Web y UX/UI (ID 53) + Herramientas de Diseño
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(53, 56), (53, 57), -- JS/TS para Front
(53, 79), (53, 80), (53, 81), (53, 82), (53, 83), (53, 84), (53, 85), -- UX/UI
(53, 210), (53, 230); -- Photoshop y Contenido marcas

-- Análisis de Sistemas (ID 55) + Datos e IA
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(55, 67), (55, 70), (55, 71), (55, 72), (55, 73), (55, 74), (55, 75), (55, 76), (55, 77);

-- Diseño Gráfico (ID 59) + Ilustración (ID 60) + Fotografía (ID 10)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(59, 83), (59, 86), (59, 87), (59, 88), (59, 89), (59, 90), (59, 91), (59, 92), (59, 93), -- Branding
(59, 210), (59, 212), (59, 225), -- Photoshop, After Effects, Motion
(60, 83), (60, 94), (60, 95), (60, 202), (60, 210), -- Arte, Godot, Photoshop
(61, 83), (61, 89), (61, 215); -- Moda + Branding + Fotos de moda

-- Fotografía (ID 10) y Video (ID 11)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(10, 191), (10, 192), (10, 193), (10, 194), (10, 195), (10, 201), (10, 208), (10, 210), (10, 213), (10, 214), (10, 215), (10, 216), (10, 217), (10, 221), (10, 222), (10, 226), (10, 227),
(11, 196), (11, 197), (11, 198), (11, 199), (11, 200), (11, 203), (11, 204), (11, 205), (11, 206), (11, 209), (11, 211), (11, 212), (11, 220), (11, 221), (11, 222), (11, 223), (11, 224), (11, 225), (11, 229);

-- 2. GASTRONOMÍA Y EVENTOS
-- Especialidades 6, 7, 14, 16, 17 comparten etiquetas de salud
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(6, 101), (6, 104), (6, 108), (6, 111), (6, 107), (6, 109), (6, 110), -- Gastronomía
(7, 101), (7, 102), (7, 103), (7, 104), (7, 105), (7, 106), -- Catering
(14, 112), (14, 117), (14, 118), (14, 119), (14, 120), (14, 121), -- Pastelería
(16, 109), (16, 112), (16, 113), (16, 114), (16, 115), (16, 116), -- Panadería
(17, 101), (17, 104), (17, 107), (17, 108), (17, 111), -- Viandas
(15, 122), (15, 123), (15, 124), (15, 125), (15, 126), (15, 127); -- Servicio de mesa


-- 3. HOGAR Y CONSTRUCCIÓN (Habilidades Cruzadas)
-- Albañilería (ID 20) e Instalación de Pisos (ID 31) y Construcción Seco (ID 57)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(20, 1), (20, 2), (20, 3), (20, 4), (20, 5), (20, 9), (20, 10), (20, 12), (20, 13), (20, 14),
(31, 6), (31, 7), (31, 8), (31, 11), (31, 14), -- Microcemento (14) compartido
(57, 10), (57, 39), (57, 40), (57, 181); -- Tabiques y muebles

-- Electricidad (ID 23) y Sistemas de Seguridad (ID 35)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(23, 41), (23, 42), (23, 43), (23, 44), (23, 45), (23, 46), (23, 48), (23, 49), (23, 50),
(35, 45), (35, 46), (35, 47), (35, 48), (35, 50); -- Automatización y Domótica compartida

-- Plomería (ID 33) y Gas (ID 25)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(33, 23), (33, 24), (33, 25), (33, 26), (33, 27), (33, 28), (33, 29), (33, 30),
(25, 15), (25, 16), (25, 17), (25, 18), (25, 19), (25, 20), (25, 21), (25, 22);

-- Limpieza (ID 29)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(29, 51), (29, 52), (29, 53);

-- 4. SALUD, BELLEZA Y EDUCACIÓN
-- Estética (ID 45) y Peluquería (ID 48)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(45, 133), (45, 134), (45, 135), (45, 136), (45, 137), (45, 138), (45, 139), (45, 140),
(48, 151), (48, 152), (48, 153), (48, 154), (48, 155), (48, 156),
(46, 141), (46, 142), (46, 143), (46, 144), (46, 145); -- Manicura

-- Educación e Idiomas (1, 3)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(1, 157), (1, 158), (1, 159), (1, 160), (1, 161), (1, 162), (1, 163), (1, 164),
(3, 165), (3, 166), (3, 167), (3, 168);

-- Mascotas (40, 41, 42)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(40, 169), (40, 170), (40, 171),
(41, 172), (41, 173), (41, 174), (41, 175),
(42, 176), (42, 177), (42, 178), (42, 179);


-- 5. LOGÍSTICA Y CERRAJERÍA
-- Fletes y Mudanzas (49, 50)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(49, 180), (49, 182), (49, 184),
(50, 180), (50, 181), (50, 182), (50, 183),
(51, 184), (51, 185), (51, 186);

-- Cerrajería (ID 22)
INSERT INTO Habilidad_Especialidad (id_especialidad, id_habilidad) VALUES 
(22, 187), (22, 188), (22, 189), (22, 190);


