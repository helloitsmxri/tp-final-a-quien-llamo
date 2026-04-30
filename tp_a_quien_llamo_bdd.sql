DROP DATABASE a_quien_llamo;
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
fecha_registro DATE NOT NULL,
ultima_actividad TIMESTAMP,
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
id_tecnico INT,
num_matricula VARCHAR(100) NOT NULL,
ente_otorgorador VARCHAR(100) NOT NULL,
fecha_vencimiento DATE,
tipo_imagen VARCHAR(50) NOT NULL,
imagen_certificado MEDIUMBLOB NOT NULL,
FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)
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
nombre_especialidad VARCHAR(50)
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

INSERT INTO Especialidad (nombre_especialidad) 
VALUES ('Clases Particulares'), 
('Coaching y Desarrollo Personal'), 
('Idiomas'), 
('Instructores y Capacitadores'), 
('Baristería'), 
('Chef/Cocinero'), 
('Catering'), 
('Decoración de Eventos'), 
('Floristería'), 
('Fotografía y Video'), 
('Maquillaje'), 
('Música y Entretenimiento'), 
('Pastelería'), 
('Servicio de Mesa'), 
('Souvenir y Regalería'), 
('Panadería Artesanal'), 
('Viandas de Comida'), 
('Aire Acondicionado'), 
('Albañilería'), 
('Carpintería'), 
('Cerrajería'), 
('Electricidad'), 
('Fumigación y Control de Plagas'), 
('Gas'), 
('Impermeabilización'), 
('Interiorismo y Decoración'), 
('Jardinería y Paisajismo'), 
('Limpieza'), 
('Persianas y Cortinas'), 
('Pisos'), 
('Pintura'), 
('Plomería'), 
('Piscinas'), 
('Seguridad (Alarmas y Cámaras)'), 
('Techos'), 
('Toldos y Cerramientos'), 
('Vidriera'), 
('Zinguería'), 
('Adiestramiento'), 
('Paseos y Cuidado'), 
('Peluquería de Mascotas'), 
('Coaching y Bienestar'), 
('Depilación'), 
('Entrenamiento Personal'), 
('Estética y Cosmetología'), 
('Manicura y Pedicura'), 
('Masajes'), 
('Peluquería'), 
('Fletes'), 
('Mudanzas'), 
('Reparto y Logística'), 
('Programador'), 
('Diseñador Web y UX/UI'), 
('Soporte Técnico'), 
('Analista en Sistemas'), 
('Arquitectura'), 
('Construcción en Seco'), 
('Herrería y metalurgía'), 
('Diseño Gráfico'), 
('Ilustración y arte'), 
('Moda e indumentaria');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Muros'), ('Reparación de grietas'), ('Revoques'), ('Contrapiso'), ('Carpeta niveladora'), ('Cerámicos'), ('Porcelanato'), ('Piedra natural'), ('Asadores'), ('Tabiques'), ('Zócalos'), ('Pozos'), ('Frentes'), ('Microcemento'),
('Redes internas de gas'), ('Detección de fugas'), ('Gas natural'), ('Gas envasado'), ('Instalación de Calderas'), ('Estufas'), ('Habilitaciones de gas'), ('Conversión de combustible'),
('Pérdidas de agua'), ('Destapaciones'), ('Sanitarios'), ('Presión de agua'), ('Cloacas'), ('Conexión domiciliaria'), ('Griferías'), ('Colectores solares'),
('Chapa'), ('Tejas'), ('Membrana en rollo'), ('Filtraciones'), ('Techos verdes'), ('Policarbonato'), ('Zinc'), ('Cumbrera'), ('Aislación térmica'), ('Drenajes'),
('Tableros eléctricos'), ('Baja tensión'), ('Media tensión'), ('LED'), ('Automatización'), ('Domótica'), ('Cableado estructurado'), ('Puesta a tierra'), ('Solar fotovoltaica'), ('Detección de fallas');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Java'), ('Python'), ('JavaScript'), ('TypeScript'), ('C#'), ('Go'), ('Kotlin'), ('APIs REST'), ('GraphQL'), ('Microservicios'), ('Spring Boot'), ('React'), ('Angular'), ('Node.js'), ('Git'), ('CI/CD'),
('Machine learning'), ('Deep learning'), ('NLP'), ('Análisis de datos'), ('SQL avanzado'), ('Power BI'), ('Tableau'), ('Modelos predictivos'), ('Chatbots'),
('Wireframing'), ('Prototipado'), ('UX Research'), ('UI Design'), ('Figma'), ('Accesibilidad web'), ('Design System'),
('Identidad de marca'), ('Logotipos'), ('Manual de marca'), ('Packaging'), ('Flyer y folletería'), ('Infografías'), ('Motion graphics'), ('Señalética');

INSERT INTO Habilidad (nombre_habilidad) VALUES 
('Café de especialidad'), ('Latte art'), ('Espresso'), ('Cold brew'), ('Cata de café'),
('Vegano'), ('Asado y parrilla'), ('Finger food'), ('Comida Sin TACC'), ('Kosher'), ('Halal'), ('Meal prep'),
('Vegetariano'),
('Cena de autor'), ('Comida Internacional'), ('Cocina Saludable'),
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
('Obediencia básica'), ('Corrección de conducta'), ('Socialización de cachorros'),
('Paseos grupales'), ('Hospedaje de mascotas'), ('Cat sitting'), ('Transporte veterinario'),
('Baño y secado'), ('Corte de raza'), ('Deslanado'), ('Geriátricas (mascotas)'),
('Embalaje de mudanza'), ('Armado de muebles'), ('Seguro de cargas'), ('Transporte de obras de arte'),
('Reparto Last mile'), ('Logística en frío'), ('Trazabilidad de envíos'),
('Apertura de cerraduras'), ('Cajas de seguridad'), ('Cerraduras digitales'), ('Llave maestra');