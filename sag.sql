DROP DATABASE IF EXISTS sag;

CREATE DATABASE IF NOT EXISTS sag;

USE sag;

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS catalogo(
	idCatalogo INT NOT NULL,
	idCategoria INT,
	nombre VARCHAR(50) NOT NULL,
	activo CHAR(255) NOT NULL,
	PRIMARY KEY (idCatalogo),
	FOREIGN KEY (idCategoria) REFERENCES catalogo(idCatalogo));

CREATE TABLE IF NOT EXISTS rancho(
	idRancho INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(200) NOT NULL,
	direccion VARCHAR(200) NOT NULL,
	nombreEncargado VARCHAR(200) NOT NULL,
	idEstatus INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS usuario(
	idUsuario INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(70) NOT NULL,
	apellidoPaterno VARCHAR(50) NOT NULL,
	apellidoMaterno VARCHAR(50),
	celular VARCHAR(10),
	usuario VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(1000) NOT NULL,
	idRol INT NOT NULL,
	idEstatus INT NOT NULL,
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idUsuario),
	FOREIGN KEY (idRol) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idEstatus) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS lote(
	idLote INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(100) NOT NULL,
	descripcion VARCHAR(500) NOT NULL,
	idEstatus INT NOT NULL,
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idLote),
	FOREIGN KEY (idEstatus) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS hato(
	idHato INT NOT NULL AUTO_INCREMENT,
	diio VARCHAR(106) NOT NULL UNIQUE,
	idRaza INT NOT NULL,
	idLote INT NOT NULL,
	sexo VARCHAR(1) NOT NULL,
	idEstatus INT NOT NULL,
	descripcion VARCHAR(250) NOT NULL,
	fechaBaja DATE,
	motivoBaja VARCHAR(500),
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idHato),
	FOREIGN KEY (idRaza) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idLote) REFERENCES lote(idLote),
	FOREIGN KEY (idEstatus) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS fotos(
	idFoto INT NOT NULL AUTO_INCREMENT,
	idHato INT NOT NULL,
	foto1 LONGBLOB NOT NULL,
	foto2 LONGBLOB,
	PRIMARY KEY (idFoto),
	FOREIGN KEY (idHato) REFERENCES hato(idHato));

CREATE TABLE IF NOT EXISTS cria(
	idCria INT NOT NULL AUTO_INCREMENT,
	idHatoMadre INT NOT NULL,
	sexo VARCHAR(1) NOT NULL,
	fechaNacimiento DATE NOT NULL,
	idRaza INT NOT NULL,
	idEstatus INT NOT NULL,
	observaciones VARCHAR(250) NOT NULL,
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idCria),
	FOREIGN KEY (idHatoMadre) REFERENCES hato(idHato),
	FOREIGN KEY (idRaza) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idEstatus) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS consultaMedica(
	idConsultaMedica INT NOT NULL AUTO_INCREMENT,
	idHato INT NOT NULL,
	nombreVeterinario VARCHAR(100) NOT NULL,
	fechaAtencion DATE NOT NULL,
	observaciones VARCHAR(500) NOT NULL,
	idMotivoAtencion INT NOT NULL,
	cancelado BOOLEAN NOT NULL DEFAULT false,
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idConsultaMedica),
	FOREIGN KEY (idHato) REFERENCES hato(idHato),
	FOREIGN KEY (idMotivoAtencion) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS traspaso(
	idTraspaso INT NOT NULL AUTO_INCREMENT,
	idLoteAnterior INT NOT NULL,
	idLoteDestino INT NOT NULL,
	cancelado BOOLEAN NOT NULL DEFAULT false,
	fechaCancelacion DATE,
	motivoCancelacion VARCHAR(300),
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idTraspaso),
	FOREIGN KEY (idLoteAnterior) REFERENCES lote(idLote),
	FOREIGN KEY (idLoteDestino) REFERENCES lote(idLote),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

CREATE TABLE IF NOT EXISTS hatosTraspaso(
	idHato INT NOT NULL,
	idTraspaso INT NOT NULL,
	PRIMARY KEY (idHato, idTraspaso),
	FOREIGN KEY (idHato) REFERENCES hato(idHato),
	FOREIGN KEY (idTraspaso) REFERENCES traspaso(idTraspaso));

CREATE TABLE IF NOT EXISTS movimiento(
	idMovimiento INT NOT NULL AUTO_INCREMENT,
	cantidadVenta DECIMAL(12,2) NOT NULL,
	tipo VARCHAR(50) NOT NULL,
	idConcepto INT NOT NULL,
	fecha DATE NOT NULL,
	observaciones VARCHAR(200) NOT NULL,
	cancelado CHAR(2) NOT NULL DEFAULT "No",
	motivoCancelacion VARCHAR(300),
	idRancho INT NOT NULL,
	fechaAlta DATE NOT NULL,
	idUsuarioAlta INT NOT NULL,
	fechaEdicion DATE,
	idUsuarioEditor INT,
	PRIMARY KEY (idMovimiento),
	FOREIGN KEY (idConcepto) REFERENCES catalogo(idCatalogo),
	FOREIGN KEY (idRancho) REFERENCES rancho(idRancho),
	FOREIGN KEY (idUsuarioAlta) REFERENCES usuario(idUsuario),
	FOREIGN KEY (idUsuarioEditor) REFERENCES usuario(idUsuario));

INSERT INTO rancho(nombre, direccion, nombreEncargado, idEstatus, fechaAlta, idUsuarioAlta) VALUES
("Reino de los Admins", "En tu corazón <3", "Admins", 101, "2023-04-22", 1),
("La Mancha", "Allá por Palmas de Abajo", "Yadelí", 101, "2023-05-11", 2);

INSERT INTO catalogo(idCatalogo, idCategoria, nombre, activo) VALUES
(1, NULL, "Estatus actividad","S"),
(2, NULL, "Rol de sistema", "S"),
(3, NULL, "Raza", "S"),
(4, NULL, "Movimiento", "S"),
(5, NULL, "Motivos de atención médica", "S"),
(101, 1, "Activo", "S"),
(102, 1, "Inactivo", "S"),
(103, 1, "Cancelado", "S"),
(201, 2, "Administrador", "S"),
(202, 2, "Vaquero", "S"),
(301, 3, "Shorthorn", "S"),
(302, 3, "Hereford", "S"),
(303, 3, "Limousin", "S"),
(304, 3, "Pardo", "S"),
(305, 3, "Nelore", "S"),
(306, 3, "Holando Argentino", "S"),
(307, 3, "Santa Gertrudis", "S"),
(401, 4, "Inversión en rancho", "S"),
(402, 4, "Pago Nóminas", "S"),
(403, 4, "Compra de productos", "S"),
(404, 4, "Venta de productos", "S"),
(501, 5, "Revisión rutinaria", "S"),
(502, 5, "Visita por enfermedad", "S"),
(503, 5, "Visita de emergencia", "S"),
(504, 5, "Vacunación", "S");

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, celular, usuario, contrasena, idRol, idEstatus, idRancho, fechaAlta, idUsuarioAlta) VALUES 
("Luis Enrique", "Zapata", "Lopez", "2281345788", "LuisZpt", "B5997B3AAA94FB20F581FFE549FE79F2F5F9DCF61BCE39B033A2A7556A82180633BE1320E64D3B4B03DA9B7A56BD5E8C5B51CF1C99C7F543AF36B0ED9E116D8A", 201, 101, 1, "2023-04-22", 1),
("Nadia Itzel", "Bravo", "Guevara", "2288511209", "nJinxx", "EA259C7DBC4C2C92E891C20A40006F5F54E4161514566503FFB41839BE29DC97CD2CEE3A3FEE2BA0942D068837A9CF9C9FFFD2C985D160AA2507E73AAD174F7C", 201, 101, 1, "2023-04-22", 1),
("Ingrid Yadelí", "López", "Aguilar", "2841001284", "Yayes", "EA259C7DBC4C2C92E891C20A40006F5F54E4161514566503FFB41839BE29DC97CD2CEE3A3FEE2BA0942D068837A9CF9C9FFFD2C985D160AA2507E73AAD174F7C", 201, 101, 2, "2023-05-14", 1);

INSERT INTO lote(nombre, descripcion, idEstatus, idRancho, fechaAlta, idUsuarioAlta) VALUES
("Crianza", "Hatos listos para criar", 101, 1, "2023-04-22", 1),
("Engorda", "Hatos que deben comer muuucho", 101, 1, "2023-04-22", 1),
("Cuarentena", "Hatos enfermos", 101, 1, "2023-04-22", 2),
("Pasteo", "Hatos que requieren pastear", 101, 1, "2023-04-22", 2),
("Reproducción", "Hatos candidatos para procrear", 101, 1, "2023-04-22", 2),
("Crianza", "Hatos listos para criar", 101, 2, "2023-05-14", 3),
("Venta", "Hatos para vender", 101, 2, "2023-05-14", 3),
("Cuarentena", "Hatos enfermos", 101, 2, "2023-05-14", 3),
("Pasteo", "Hatos que requieren pastear", 101, 2, "2023-05-14", 3),
("Reproducción", "Hatos candidatos para procrear", 101, 2, "2023-05-14", 3);

INSERT INTO hato(diio, idRaza, idLote, sexo, idEstatus, descripcion, idRancho, fechaAlta, idUsuarioAlta) VALUES
("Q02RE09AD10P2", 301, 2, "H", 101, "Vaca blanca de manchas negras", 1, "2023-04-22", 2),
("3QRFOIMG0E990", 302, 5, "M", 101, "Vaca negra de manchas blancas", 1, "2023-04-22", 1),
("98FWF2390D2C2", 302, 5, "H", 101, "Vaca café", 1, "2023-04-22", 1),
("23RDFG34G3FE2", 303, 4, "H", 101, "Vaca blanca con manchas color café", 1, "2023-04-22", 2),
("E4GH45H56J67J", 303, 4, "H", 101, "Vaca blanca", 1, "2023-04-22", 2),
("1290KH459N23N", 304, 5, "M", 101, "Vaca marrón con café", 1, "2023-04-22", 2),
("MOAG901R0WFE0", 304, 1, "H", 101, "Vaca pokémon", 1, "2023-04-22", 1),
("0919RM9923T89", 305, 4, "M", 101, "Vaca marrón, patas blancas", 1, "2023-04-22", 2),
("1290RMMXDQWQW", 305, 4, "M", 101, "Vaca blanca, cabeza marrón", 1, "2023-04-22", 2),
("3F0MOW3F2IG02", 306, 5, "H", 101, "Vaca parecida a la mascota de Alpura", 1, "2023-04-22", 1),
("1R2JMG0M43RES", 306, 5, "H", 101, "Vaca marrón con rayas blancas", 1, "2023-04-22", 1),
("34G033G0MSZFQ", 307, 2, "H", 101, "Vaca blanca con puntos marrones", 1, "2023-04-22", 1),
("1249GE99298FS", 307, 2, "H", 101, "Vaca marrón con manchas negras y blancas", 1, "2023-04-22", 2),
("124H8RGR923RA", 301, 2, "M", 101, "Vaca gris", 2, "2023-04-22", 3),
("DH45HNVZFZEW3", 301, 4, "M", 101, "Vaca pequeña color blanco", 2, "2023-04-22", 3),
("0ID12R3I91RGD", 303, 5, "H", 101, "Vaca blanca", 2, "2023-04-22", 3),
("21T38129NUIEN", 306, 1, "H", 101, "Cococabraaaa", 2, "2023-04-22", 3);

INSERT INTO cria(idHatoMadre, sexo, fechaNacimiento, idRaza, idEstatus, observaciones, idRancho, fechaAlta, idUsuarioAlta) VALUES
(1, "M", "2023-03-01", 307, 101, "Vaca negra con manchas blancas", 1, "2023-04-22", 1),
(5, "H", "2023-02-11", 307, 101, "Vaca negra", 1, "2023-04-22", 1),
(10, "H", "2023-01-22", 307, 101, "Vaca marron con manchas blancas", 1, "2023-04-22", 2),
(11, "M", "2023-02-07", 307, 101, "Vaca blanca con manchas marrones", 1, "2023-04-22", 2),
(17, "H", "2023-02-07", 307, 101, "Cococabraaaa JR", 2, "2023-04-22", 3);

INSERT INTO movimiento(cantidadVenta, tipo, idConcepto, fecha, observaciones, idRancho, fechaAlta, idUsuarioAlta) VALUES
(5000000, "Ingreso", 401, "2023-04-22", "Compramos un ranchooo", 1, "2023-04-22", 1),
(20000, "Egreso", 402,"2023-04-21", "Apenas lo compramos y ya les pagamos a los trabajadores :/", 1, "2023-04-23", 2),
(5000, "Ingreso", 404, "2023-04-23", "Venta de productos lácteos", 1, "2023-04-21", 2),
(5000000, "Ingreso", 401, "2023-04-20", "Ingreso inicial", 2, "2023-05-14", 3);

INSERT INTO consultamedica(idHato, nombreVeterinario, fechaAtencion, observaciones, idMotivoAtencion, idRancho, fechaAlta, idUsuarioAlta) VALUES
(1, "Paco", CURDATE(), "Estaba enfermita", 501, 1, "2023-06-10", 1);

INSERT INTO `sag`.`traspaso` (`idTraspaso`, `idLoteAnterior`, `idLoteDestino`, `idRancho`, `fechaAlta`, `idUsuarioAlta`) VALUES ('1', '1', '2', '1', '2023-06-10', '1');
INSERT INTO `sag`.`traspaso` (`idTraspaso`, `idLoteAnterior`, `idLoteDestino`, `idRancho`, `fechaAlta`, `idUsuarioAlta`) VALUES ('2', '3', '1', '1', '2023-06-10', '2');
INSERT INTO `sag`.`traspaso` (`idTraspaso`, `idLoteAnterior`, `idLoteDestino`, `idRancho`, `fechaAlta`, `idUsuarioAlta`) VALUES ('3', '4', '2', '1', '2023-06-10', '1');
INSERT INTO `sag`.`traspaso` (`idTraspaso`, `idLoteAnterior`, `idLoteDestino`, `idRancho`, `fechaAlta`, `idUsuarioAlta`) VALUES ('4', '2', '1', '2', '2023-06-10', '2');

SET FOREIGN_KEY_CHECKS=1;

-- ############################################################################################################################################## --

--      VISTAS                                                                                                                                    --

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW usuariosfullinfo AS
	SELECT
		u.idUsuario,
		u.nombre,
		u.apellidoPaterno,
		u.apellidoMaterno,
		u.celular,
		u.usuario,
		u.contrasena,
		u.idRol,
		cr.nombre AS rol,
		u.idEstatus,
		ce.nombre AS estatus,
		u.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(u.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		u.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(u.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		u.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM usuario u
		INNER JOIN catalogo cr ON u.idRol=cr.idCatalogo
		INNER JOIN catalogo ce ON u.idEstatus=ce.idCatalogo
		INNER JOIN rancho r ON u.idRancho=r.idRancho
		INNER JOIN usuario ua ON u.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON u.idUsuarioEditor=ue.idUsuario
	ORDER BY u.idUsuario;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW categoriasfullinfo AS
	SELECT
		c.idCatalogo AS idCategoria,
		c.nombre,
		c.activo
	FROM catalogo c
	WHERE ISNULL(c.idCategoria);

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW catalogosfullinfo AS
	SELECT
		c.idCatalogo,
		c.idCategoria,
		ca.nombre AS categoria,
		c.nombre,
		c.activo
	FROM catalogo c
		INNER JOIN catalogo ca ON c.idCategoria=ca.idCatalogo
	WHERE NOT ISNULL(c.idCategoria);

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW ranchosfullinfo AS
	SELECT
		r.idRancho,
		r.nombre,
		r.direccion,
		r.nombreEncargado,
		r.idEstatus,
		c.nombre AS estatus,
		DATE_FORMAT(r.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		r.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(r.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		r.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM rancho r
		INNER JOIN catalogo c ON r.idEstatus=c.idCatalogo
		INNER JOIN usuario ua ON r.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON r.idUsuarioEditor=ue.idUsuario
	ORDER BY r.idRancho;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW lotesfullinfo AS
	SELECT
		l.idLote,
		l.nombre,
		l.descripcion,
		l.idEstatus,
		ce.nombre AS estatus,
		l.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(l.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		l.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(l.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		l.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM lote l
		INNER JOIN catalogo ce ON l.idEstatus=ce.idCatalogo
		INNER JOIN rancho r ON l.idRancho=r.idRancho
		INNER JOIN usuario ua ON l.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON l.idUsuarioEditor=ue.idUsuario
	ORDER BY l.idLote;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW hatosfullinfo AS
	SELECT
		h.idHato,
		h.diio,
		h.idRaza,
		cr.nombre AS raza,
		h.idLote,
		l.nombre AS lote,
		h.sexo,
		h.idEstatus,
		ce.nombre AS estatus,
		h.descripcion,
		DATE_FORMAT(h.fechaBaja, "%d-%m-%Y") AS fechaBaja,
		h.motivoBaja,
		h.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(h.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		h.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(h.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		h.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM hato h
		INNER JOIN catalogo cr ON h.idRaza=cr.idCatalogo
		INNER JOIN lote l ON h.idLote=l.idLote
		INNER JOIN catalogo ce ON h.idEstatus=ce.idCatalogo
		INNER JOIN rancho r ON h.idRancho=r.idRancho
		INNER JOIN usuario ua ON h.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON h.idUsuarioEditor=ue.idUsuario
	ORDER BY h.idHato;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW criasfullinfo AS
	SELECT
		c.idCria,
		c.idHatoMadre,
		c.sexo,
		DATE_FORMAT(c.fechaNacimiento, "%d-%m-%Y") AS fechaNacimiento,
		c.idRaza,
		cr.nombre AS raza,
		c.idEstatus,
		ce.nombre AS estatus,
		c.observaciones,
		c.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(c.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		c.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(c.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		c.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM cria c
		INNER JOIN catalogo ce ON c.idEstatus=ce.idCatalogo
		INNER JOIN catalogo cr ON c.idRaza=cr.idCatalogo
		INNER JOIN rancho r ON c.idRancho=r.idRancho
		INNER JOIN usuario ua ON c.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON c.idUsuarioEditor=ue.idUsuario
	ORDER BY c.idCria;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW consultasmedicasfullinfo AS
	SELECT
		cm.idConsultaMedica,
		cm.idHato,
		cm.nombreVeterinario,
		DATE_FORMAT(cm.fechaAtencion, "%d-%m-%Y") AS fechaAtencion,
		cm.observaciones,
		cm.idMotivoAtencion,
		ca.nombre AS motivoAtencion,
		cm.cancelado,
		cm.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(cm.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		cm.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(cm.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		cm.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM consultaMedica cm
		INNER JOIN rancho r ON cm.idRancho=r.idRancho
		INNER JOIN catalogo ca ON cm.idMotivoAtencion=ca.idCatalogo
		INNER JOIN usuario ua ON cm.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON cm.idUsuarioEditor=ue.idUsuario
	ORDER BY cm.idConsultaMedica;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW movimientosfullinfo AS
	SELECT
		m.idMovimiento,
		m.cantidadVenta,
		m.tipo,
		m.idConcepto,
		c.nombre AS concepto,
		DATE_FORMAT(m.fecha, "%d-%m-%Y") AS fecha,
		m.observaciones,
		m.cancelado,
		m.motivoCancelacion,
		m.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(m.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		m.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(m.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		m.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM movimiento m
		INNER JOIN catalogo c ON m.idConcepto=c.idCatalogo
		INNER JOIN rancho r ON m.idRancho=r.idRancho
		INNER JOIN usuario ua ON m.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON m.idUsuarioEditor=ue.idUsuario
	ORDER BY m.fecha DESC;

-- ############################################################################################################################################## --

CREATE OR REPLACE VIEW traspasosfullinfo AS
	SELECT
		t.idTraspaso,
    	t.idLoteAnterior,
    	la.nombre AS loteAnterior,
    	t.idLoteDestino,
    	ld.nombre AS loteDestino,
    	t.cancelado,
    	DATE_FORMAT(t.fechaCancelacion, "%d-%m-%Y") AS fechaCancelacion,
    	t.motivoCancelacion,
    	t.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(t.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		t.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(t.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		t.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor

	FROM traspaso t
		INNER JOIN lote la ON t.idLoteAnterior=la.idLote
		INNER JOIN lote ld ON t.idLoteDestino=ld.idLote
		INNER JOIN rancho r ON t.idRancho=r.idRancho
		INNER JOIN usuario ua ON t.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON t.idUsuarioEditor=ue.idUsuario;

-- ############################################################################################################################################## --

--      PROCEDIMIENTOS ALMACENADOS                                                                                                                --

-- ############################################################################################################################################## --

DELIMITER $$

CREATE PROCEDURE sp_login(
	IN usuario VARCHAR(50),
	IN contrasena VARCHAR(1000))
BEGIN
	SELECT * FROM usuariosfullinfo u
	WHERE u.usuario=usuario AND u.contrasena=contrasena;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllUsuarios()
BEGIN
	SELECT * FROM usuariosfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getUsuariosByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM usuariosfullinfo u WHERE u.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarUsuarios(IN idRancho INT, IN busqueda VARCHAR(70))
BEGIN
	SELECT * FROM usuariosfullinfo u WHERE (u.idUsuario=busqueda OR LOCATE(busqueda, CONCAT(u.nombre, " ", u.apellidoPaterno, " ", u.apellidoMaterno))) AND u.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarUsuario(
	IN nombre VARCHAR(70),
	IN apellidoPaterno VARCHAR(50),
	IN apellidoMaterno VARCHAR(50),
	IN celular VARCHAR(10),
	IN usuario VARCHAR(50),
	IN contrasena VARCHAR(1000),
	IN idRol INT,
	IN idEstatus INT,
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, celular, usuario, contrasena, idRol, idEstatus, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(nombre, apellidoPaterno, apellidoMaterno, celular, usuario, contrasena, idRol, idEstatus, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarUsuario(
	IN idUsuario INT,
	IN nombre VARCHAR(70),
	IN apellidoPaterno VARCHAR(50),
	IN apellidoMaterno VARCHAR(50),
	IN celular VARCHAR(10),
	IN usuario VARCHAR(50),
	IN idRol INT,
	IN idEstatus INT,
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE usuario u
	SET u.nombre=nombre, u.apellidoPaterno=apellidoPaterno, u.apellidoMaterno=apellidoMaterno, u.celular=celular, u.usuario=usuario, u.idRol=idRol, u.idEstatus=idEstatus, u.idRancho=idRancho, u.fechaEdicion=CURDATE(), u.idUsuarioEditor=idUsuarioEditor
	WHERE u.idUsuario=idUsuario;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_cambiarContrasenaUsuario(
	IN idUsuario INT,
	IN contrasena VARCHAR(1000),
	IN idUsuarioEditor INT)
BEGIN
	UPDATE usuario u
	SET u.contrasena=contrasena, u.fechaEdicion=CURDATE(), u.idUsuarioEditor=idUsuarioEditor
	WHERE u.idUsuario=idUsuario;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusUsuario(
	IN idUsuario INT,
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE usuario u
	SET u.idEstatus=idEstatus, u.fechaEdicion=CURDATE(), u.idUsuarioEditor=idUsuarioEditor
	WHERE u.idUsuario=idUsuario;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllCategorias()
BEGIN
	SELECT * FROM categoriasfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarCategorias(IN busqueda VARCHAR(50))
BEGIN
	SELECT * FROM categoriasfullinfo c WHERE c.idCategoria=busqueda OR LOCATE(busqueda, c.nombre);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarCategoria(
	IN idCategoria INT,
	IN nombre VARCHAR(50),
	IN activo CHAR(255))
BEGIN
	INSERT INTO catalogo(idCatalogo, nombre, activo) VALUES(idCategoria, nombre, activo);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarCategoria(
	IN idCategoria INT,
	IN nombre VARCHAR(50),
	IN activo CHAR(255))
BEGIN
	UPDATE catalogo c
	SET c.idCatalogo=idCategoria, c.nombre=nombre, c.activo=activo
	WHERE c.idCatalogo=idCategoria;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusCategoria(
	IN idCategoria INT,
	IN activo CHAR(255))
BEGIN
	UPDATE catalogo c
	SET c.activo=activo
	WHERE c.idCatalogo=idCategoria;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getCatalogosByCategoria(IN id INT)
BEGIN
	SELECT * FROM catalogosfullinfo c WHERE c.idCategoria = id;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarCatalogo(
	IN idCatalogo INT,
	IN idCategoria INT,
	IN nombre VARCHAR(50),
	IN activo CHAR(255))
BEGIN
	INSERT INTO catalogo(idCatalogo, idCategoria, nombre, activo) VALUES(idCatalogo, idCategoria, nombre, activo);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarCatalogo(
	IN idCatalogo INT,
	IN idCategoria INT,
	IN nombre VARCHAR(50),
	IN activo CHAR(255))
BEGIN
	UPDATE catalogo c
	SET c.idCatalogo=idCatalogo, c.idCategoria=idCategoria, c.nombre=nombre, c.activo=activo
	WHERE c.idCatalogo=idCatalogo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusCatalogo(
	IN idCatalogo INT,
	IN activo CHAR(255))
BEGIN
	UPDATE catalogo c
	SET c.activo=activo
	WHERE c.idCatalogo=idCatalogo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getConceptosMovimientos()
BEGIN
	SELECT idCatalogo, nombre FROM catalogosfullinfo WHERE idCategoria=4 AND activo="S";
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getRolesUsuarios()
BEGIN
	SELECT idCatalogo, nombre FROM catalogosfullinfo WHERE idCategoria=2 AND activo="S";
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllRanchos()
BEGIN
	SELECT * FROM ranchosfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarRanchos(IN busqueda VARCHAR(200))
BEGIN
	SELECT * FROM ranchosfullinfo r WHERE r.idRancho=busqueda OR LOCATE(busqueda, r.nombre);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarRancho(
	IN nombre VARCHAR(200),
	IN direccion VARCHAR(200),
	IN nombreEncargado VARCHAR(200),
	IN idEstatus INT,
	IN idUsuarioAlta INT)
BEGIN
	DECLARE idNuevoRancho INT;

	INSERT INTO rancho(nombre, direccion, nombreEncargado, idEstatus, fechaAlta, idUsuarioAlta) VALUES
	(nombre, direccion, nombreEncargado, idEstatus, CURDATE(), idUsuarioAlta);

	SELECT r.idRancho INTO idNuevoRancho FROM rancho r WHERE r.nombre=nombre;

	INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, celular, usuario, contrasena, idRol, idEstatus, idRancho, fechaAlta, idUsuarioAlta) VALUES 
	("admin", "admin", "admin", "0000000000", "admin", "4367D7428E0D67677F09C37A936910F3BFA794B5410FB8510DC8A528F5370CA939CBFD9E4A6EA3F9C4161FDFA4FAE9C0F13A2F250EC54BAEC6053640F8C7EDC5", 201, 101, idNuevoRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarRancho(
	IN idRancho INT,
	IN nombre VARCHAR(200),
	IN direccion VARCHAR(200),
	IN nombreEncargado VARCHAR(200),
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE rancho r
	SET r.nombre=nombre, r.direccion=direccion, r.nombreEncargado=nombreEncargado, r.idEstatus=idEstatus, r.fechaEdicion=CURDATE(), r.idUsuarioEditor=idUsuarioEditor
	WHERE r.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusRancho(
	IN idRancho INT,
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE rancho r
	SET r.idEstatus=idEstatus, r.fechaEdicion=CURDATE(), r.idUsuarioEditor=idUsuarioEditor
	WHERE r.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllLotes()
BEGIN
	SELECT * FROM lotesfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getLotesByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM lotesfullinfo l WHERE l.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarLotes(IN idRancho INT, IN busqueda VARCHAR(100))
BEGIN
	SELECT * FROM lotesfullinfo l WHERE (l.idLote=busqueda OR LOCATE(busqueda, l.nombre)) AND l.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarLote(
	IN nombre VARCHAR(100),
	IN descripcion VARCHAR(500),
	IN idEstatus INT,
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO lote(nombre, descripcion, idEstatus, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(nombre, descripcion, idEstatus, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarLote(
	IN idLote INT,
	IN nombre VARCHAR(100),
	IN descripcion VARCHAR(500),
	IN idEstatus INT,
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE lote l
	SET l.nombre=nombre, l.descripcion=descripcion, l.idEstatus=idEstatus, l.idRancho=idRancho, l.fechaEdicion=CURDATE(), l.idUsuarioEditor=idUsuarioEditor
	WHERE l.idLote=idLote;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusLote(
	IN idLote INT,
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE lote l
	SET l.idEstatus=idEstatus, l.fechaEdicion=CURDATE(), l.idUsuarioEditor=idUsuarioEditor
	WHERE l.idLote=idLote;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllHatos()
BEGIN
	SELECT * FROM hatosfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getHatosByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM hatosfullinfo h WHERE h.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarHatos(IN idRancho INT, IN busqueda VARCHAR(106))
BEGIN
	SELECT * FROM hatosfullinfo h WHERE (h.idHato=busqueda OR LOCATE(busqueda, h.diio)) AND h.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarHato(
	IN diio VARCHAR(106),
	IN idRaza INT,
	IN idLote INT,
	IN sexo VARCHAR(1),
	IN idEstatus INT,
	IN descripcion VARCHAR(250),
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO hato(diio, idRaza, idLote, sexo, idEstatus, descripcion, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(diio, idRaza, idLote, sexo, idEstatus, descripcion, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarHato(
	IN idHato INT,
	IN diio VARCHAR(106),
	IN idRaza INT,
	IN idLote INT,
	IN sexo VARCHAR(1),
	IN idEstatus INT,
	IN descripcion VARCHAR(250),
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE hato h
	SET h.diio=diio, h.idRaza=idRaza, h.idLote=idLote, h.sexo=sexo, h.idEstatus=idEstatus, h.descripcion=descripcion, h.idRancho=idRancho, h.fechaEdicion=CURDATE(), h.idUsuarioEditor=idUsuarioEditor
	WHERE h.idHato=idHato;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusHato(
	IN idHato INT,
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE hato h
	SET h.idEstatus=idEstatus, h.fechaEdicion=CURDATE(), h.idUsuarioEditor=idUsuarioEditor
	WHERE h.idHato=idHato;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_bajaHato(
	IN idHato INT,
	IN fechaBaja DATE,
	IN motivoBaja VARCHAR(500),
	IN idUsuarioEditor INT)
BEGIN
	UPDATE hato h
	SET h.idEstatus=103, h.fechaBaja=fechaBaja, h.motivoBaja=motivoBaja, h.fechaEdicion=CURDATE(), h.idUsuarioEditor=idUsuarioEditor
	WHERE h.idHato=idHato;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getCriasByIdHatoMadre(IN idHatoMadre INT)
BEGIN
	SELECT * FROM criasfullinfo c WHERE c.idHatoMadre=idHatoMadre;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getCriasByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM criasfullinfo c WHERE c.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarCrias(IN idRancho INT, IN idCria INT)
BEGIN
	SELECT * FROM criasfullinfo c WHERE c.idCria=idCria AND c.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarCria(
	IN idHatoMadre INT,
	IN sexo VARCHAR(1),
	IN fechaNacimiento DATE,
	IN idRaza INT,
	IN idEstatus INT,
	IN observaciones VARCHAR(250),
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO cria(idHatoMadre, sexo, fechaNacimiento, idRaza, idEstatus, observaciones, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(idHatoMadre, sexo, fechaNacimiento, idRaza, idEstatus, observaciones, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarCria(
	IN idCria INT,
	IN idHatoMadre INT,
	IN sexo VARCHAR(1),
	IN fechaNacimiento DATE,
	IN idRaza INT,
	IN idEstatus INT,
	IN observaciones VARCHAR(250),
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE cria c
	SET c.idHatoMadre= idHatoMadre, c.sexo=sexo, c.fechaNacimiento=fechaNacimiento, c.idRaza=idRaza, c.idEstatus=idEstatus, c.observaciones=observaciones, c.idRancho=idRancho, c.fechaEdicion=CURDATE(), c.idUsuarioEditor=idUsuarioEditor
	WHERE c.idCria=idCria;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarEstatusCria(
	IN idCria INT,
	IN idEstatus INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE cria c
	SET c.idEstatus=idEstatus, c.fechaEdicion=CURDATE(), c.idUsuarioEditor=idUsuarioEditor
	WHERE c.idCria=idCria;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getConsultasMedicasByIdHato(IN idHato INT)
BEGIN
	SELECT * FROM consultasmedicasfullinfo c WHERE c.idHato=idHato;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getConsultasMedicasByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM consultasmedicasfullinfo c WHERE c.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarConsultasMedicas(IN idRancho INT, IN busqueda VARCHAR(30))
BEGIN
	SELECT * FROM consultasmedicasfullinfo c WHERE (c.idConsultaMedica=busqueda OR LOCATE(busqueda, c.motivoAtencion)) AND c.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarConsultaMedica(
	IN idHato INT,
	IN nombreVeterinario VARCHAR(100),
	IN fechaAtencion DATE,
	IN observaciones VARCHAR(500),
	IN idMotivoAtencion INT,
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO consultaMedica(idHato, nombreVeterinario, fechaAtencion, observaciones, idMotivoAtencion, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(idHato, nombreVeterinario, fechaAtencion, observaciones, idMotivoAtencion, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarConsultaMedica(
	IN idConsultaMedica INT,
	IN idHato INT,
	IN nombreVeterinario VARCHAR(100),
	IN fechaAtencion DATE,
	IN observaciones VARCHAR(500),
	IN idMotivoAtencion INT,
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE consultaMedica cm
	SET cm.idHato=idHato, cm.nombreVeterinario=nombreVeterinario, cm.fechaAtencion=fechaAtencion, cm.observaciones=observaciones, cm.idMotivoAtencion=idMotivoAtencion, cm.idRancho=idRancho, cm.fechaEdicion=CURDATE(), cm.idUsuarioEditor=idUsuarioEditor
	WHERE cm.idConsultaMedica=idConsultaMedica;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_cancelarConsultaMedica(
	IN idConsultaMedica INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE consultaMedica cm
	SET cm.cancelado=true, cm.idUsuarioEditor=idUsuarioEditor
	WHERE cm.idConsultaMedica=idConsultaMedica;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getAllMovimientos()
BEGIN
	SELECT * FROM movimientosfullinfo;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getMovimientosByIdRancho(IN idRancho INT)
BEGIN
	SELECT
		m.idMovimiento,
		m.cantidadVenta,
		m.tipo,
		m.idConcepto,
		c.nombre AS concepto,
		DATE_FORMAT(m.fecha, "%d-%m-%Y") AS fecha,
		m.observaciones,
		m.cancelado,
		m.motivoCancelacion,
		m.idRancho,
		r.nombre AS rancho,
		DATE_FORMAT(m.fechaAlta, "%d-%m-%Y") AS fechaAlta,
		m.idUsuarioAlta,
		CONCAT(ua.nombre, " ", ua.apellidoPaterno, " ", ua.apellidoMaterno) AS usuarioAlta,
		DATE_FORMAT(m.fechaEdicion, "%d-%m-%Y") AS fechaEdicion,
		m.idUsuarioEditor,
		CONCAT(ue.nombre, " ", ue.apellidoPaterno, " ", ue.apellidoMaterno) AS usuarioEditor
	FROM movimiento m
		INNER JOIN catalogo c ON m.idConcepto=c.idCatalogo
		INNER JOIN rancho r ON m.idRancho=r.idRancho
		INNER JOIN usuario ua ON m.idUsuarioAlta=ua.idUsuario
		LEFT JOIN usuario ue ON m.idUsuarioEditor=ue.idUsuario
	WHERE m.idRancho=idRancho
	ORDER BY m.fecha DESC;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarMovimientos(IN idRancho INT, IN busqueda VARCHAR(100))
BEGIN
	SELECT * FROM movimientosfullinfo m WHERE (m.idMovimiento=busqueda OR LOCATE(busqueda, m.concepto)) AND m.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarMovimiento(
	IN cantidadVenta DECIMAL(12,2),
	IN tipo VARCHAR(50),
	IN idConcepto INT,
	IN fecha DATE,
	IN observaciones VARCHAR(200),
	IN idRancho INT,
	IN idUsuarioAlta INT)
BEGIN
	INSERT INTO movimiento(cantidadVenta, tipo, idConcepto, fecha, observaciones, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(cantidadVenta, tipo, idConcepto, fecha, observaciones, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarMovimiento(
	IN idMovimiento INT,
	IN cantidadVenta DECIMAL(12,2),
	IN tipo VARCHAR(50),
	IN idConcepto INT,
	IN fecha DATE,
	IN observaciones VARCHAR(250),
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE movimiento m
	SET m.cantidadVenta=cantidadVenta, m.tipo=tipo, m.idConcepto=idConcepto, m.fecha=fecha, m.observaciones=observaciones, m.idRancho=idRancho, m.fechaEdicion=CURDATE(), m.idUsuarioEditor=idUsuarioEditor
	WHERE m.idMovimiento=idMovimiento;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_cancelarMovimiento(
	IN idMovimiento INT,
	IN motivoCancelacion VARCHAR(300),
	IN idUsuarioEditor INT)
BEGIN
	UPDATE movimiento m
	SET m.cancelado="Sí", m.motivoCancelacion=motivoCancelacion, m.fechaEdicion=CURDATE(), m.idUsuarioEditor=idUsuarioEditor
	WHERE m.idMovimiento=idMovimiento;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_buscarTraspasos(IN idRancho INT, IN busqueda VARCHAR(100))
BEGIN
	SELECT * FROM traspasosfullinfo t WHERE (t.idTraspaso=busqueda OR LOCATE(busqueda, t.loteAnterior) OR LOCATE(busqueda, t.loteDestino)) AND t.idRancho=idRancho;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_registrarTraspaso(
	IN idLoteAnterior INT,
	IN idLoteDestino INT,
	IN idRancho INT,
	IN idUsuarioAlta INT)	
BEGIN
	INSERT INTO traspaso(idLoteAnterior, idLoteDestino, idRancho, fechaAlta, idUsuarioAlta) VALUES
	(idLoteAnterior, idLoteDestino, idRancho, CURDATE(), idUsuarioAlta);
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_editarTraspaso(
	IN idTraspaso INT,
	IN idLoteAnterior INT,
	IN idLoteDestino INT,
	IN idRancho INT,
	IN idUsuarioEditor INT)
BEGIN
	UPDATE traspaso t
	SET t.idLoteAnterior=idLoteAnterior, t.idLoteDestino=idLoteDestino, t.idRancho=idRancho, t.fechaEdicion=CURDATE(), t.idUsuarioEditor=idUsuarioEditor
	WHERE t.idTraspaso=idTraspaso;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_cancelarTraspaso(
	IN idTraspaso INT,
	IN motivoCancelacion VARCHAR(300),
	IN idUsuarioEditor INT)
BEGIN
	UPDATE traspaso t
	SET t.cancelado=true, t.fechaCancelacion=CURDATE(), t.motivoCancelacion=motivoCancelacion, t.fechaEdicion=CURDATE(), t.idUsuarioEditor=idUsuarioEditor
	WHERE t.idTraspaso=idTraspaso;
END$$

-- ############################################################################################################################################## --

CREATE PROCEDURE sp_getTraspasosByIdRancho(IN idRancho INT)
BEGIN
	SELECT * FROM traspasosfullinfo t WHERE t.idRancho=idRancho;
END$$


DELIMITER ;





