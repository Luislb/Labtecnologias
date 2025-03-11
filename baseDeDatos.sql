-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: trolley.proxy.rlwy.net    Database: universidad
-- ------------------------------------------------------
-- Server version	9.2.0

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

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursos` (
  `ID` double NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Programa` varchar(100) NOT NULL,
  `Activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1,'Calculo diferencia','Ingeniería de Sistemas',1),(2,'algebra lineal','Ingeniería de Sistemas',1),(3,'Matematicas Especiales','Ingeniería de Sistemas',1),(4,'educación infantil','Licenciatura en Educación Infantil',1),(5,'POO','Ingeniería de Sistemas',1),(6,'algebra lineal','Ingeniería de Sistemas',1),(7,'algebra lineal 2','Ingeniería de Sistemas',1),(10,'Electricidad y magnetismo','Ingeniería de Sistemas',1);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `decano`
--

DROP TABLE IF EXISTS `decano`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `decano` (
  `ID` double NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decano`
--

LOCK TABLES `decano` WRITE;
/*!40000 ALTER TABLE `decano` DISABLE KEYS */;
INSERT INTO `decano` VALUES (1,'Daniel','Romero','dromero@unillanos.edu.co'),(2,'Jose','Vargas','jvargas@unillanos.edu.co'),(3,'Manuel','Rodriguez','mrodriguez@unillanos.edu.co'),(4,'Luis','Muños','lmuños@unillanos.edu.co'),(5,'Diana','Lopez','dlopez@unillanos.edu.co');
/*!40000 ALTER TABLE `decano` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiantes`
--

DROP TABLE IF EXISTS `estudiantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiantes` (
  `ID` int NOT NULL,
  `nombres` varchar(50) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `codigo` double DEFAULT NULL,
  `programa` varchar(100) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT NULL,
  `promedio` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiantes`
--

LOCK TABLES `estudiantes` WRITE;
/*!40000 ALTER TABLE `estudiantes` DISABLE KEYS */;
INSERT INTO `estudiantes` VALUES (1,'luis','blanco','lbs@unillanos.edu.co',160004632,'Ingeniería de Sistemas',1,4),(2,'Brayan','Vargas','bVargas@unillanos.edu.co',17800012,'Licenciatura en Educación Fisica y Deportes',1,4),(3,'Vivian','Prado','vPrado@unillanos.edu.co',160004612,'Licenciatura en Educación Fisica y Deportes',1,4),(4,'mayckoll','vargas','Mvargas@unillanos.edu.co',160004612,'Ingeniería de Sistemas',1,3.6),(5,'pedro','pe','pedrope@unillanos.edu.co',160004603,'Ingeniería de Sistemas',0,5),(6,'Devanny','Vargas','dVargas@unillanos.edu.co',17800025,'Licenciatura en Educación Fisica y Deportes',1,3.5),(7,'fasdfsf','vargas','angiep@unillanos.edu.co',1.29492392e16,'Ingeniería de Sistemas',1,3.8),(8,'carlos','muños','cm@unillanos.edu.co',160004423,'Ingeniería de Sistemas',1,4),(9,'qsfc','wqedq','asmasm@unillano.edu.co',123123,'Licenciatura en Educación Infantil',1,4);
/*!40000 ALTER TABLE `estudiantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiantes_cursos`
--

DROP TABLE IF EXISTS `estudiantes_cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiantes_cursos` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `estudiante_id` int DEFAULT NULL,
  `curso_id` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `estudiante_id` (`estudiante_id`),
  KEY `fk_cursoE` (`curso_id`),
  CONSTRAINT `estudiantes_cursos_ibfk_1` FOREIGN KEY (`estudiante_id`) REFERENCES `estudiantes` (`ID`),
  CONSTRAINT `fk_cursoE` FOREIGN KEY (`curso_id`) REFERENCES `cursos` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiantes_cursos`
--

LOCK TABLES `estudiantes_cursos` WRITE;
/*!40000 ALTER TABLE `estudiantes_cursos` DISABLE KEYS */;
INSERT INTO `estudiantes_cursos` VALUES (1,1,1),(3,5,2),(4,4,1),(5,1,3),(6,4,3),(7,5,3),(10,4,4),(11,1,4),(12,4,5),(13,5,5),(14,2,3),(15,2,7);
/*!40000 ALTER TABLE `estudiantes_cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultades`
--

DROP TABLE IF EXISTS `facultades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facultades` (
  `ID` double NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `decano_id` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `decano_id` (`decano_id`),
  CONSTRAINT `facultades_ibfk_1` FOREIGN KEY (`decano_id`) REFERENCES `decano` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultades`
--

LOCK TABLES `facultades` WRITE;
/*!40000 ALTER TABLE `facultades` DISABLE KEYS */;
INSERT INTO `facultades` VALUES (1,'FCBI',1),(2,'FCARN',2),(3,'FCS',3),(4,'FCE',4),(5,'FCHE',5);
/*!40000 ALTER TABLE `facultades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesores`
--

DROP TABLE IF EXISTS `profesores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesores` (
  `ID` double NOT NULL,
  `Nombres` varchar(100) NOT NULL,
  `Apellidos` varchar(100) NOT NULL,
  `Email` varchar(150) NOT NULL,
  `TipoContrato` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesores`
--

LOCK TABLES `profesores` WRITE;
/*!40000 ALTER TABLE `profesores` DISABLE KEYS */;
INSERT INTO `profesores` VALUES (1,'Ana','Gil','aGil@unillanos.edu.co','tiempo completo'),(2,'jose','Barreto','jose@unillanos.edu.co','tiempo completo '),(3,'Manuel','Lopez','mLopez@unillanos.edu.co','indefinido'),(4,'Laura','Garcia','jGarcia@unillanos.edu.co','Indefinido'),(5,'luis','soy','lbs@unillanos.edu.co','tiempo completo'),(6,'luis4','soy','lbs3@unillanos.edu.co','tiempo completo'),(7,'felipe','agudelo','ajhsdausnd@asdasd','definido'),(8,'Carlos','Peña','Peñita@unillanos.edu.co','definido'),(10,'Diana','Perez','dPerez@unillanos.edu.co','tiempo completo ');
/*!40000 ALTER TABLE `profesores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesores_cursos`
--

DROP TABLE IF EXISTS `profesores_cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesores_cursos` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `profesor_id` double DEFAULT NULL,
  `curso_id` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `profesor_id` (`profesor_id`),
  KEY `fk_curso` (`curso_id`),
  CONSTRAINT `fk_curso` FOREIGN KEY (`curso_id`) REFERENCES `cursos` (`ID`),
  CONSTRAINT `profesores_cursos_ibfk_1` FOREIGN KEY (`profesor_id`) REFERENCES `profesores` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesores_cursos`
--

LOCK TABLES `profesores_cursos` WRITE;
/*!40000 ALTER TABLE `profesores_cursos` DISABLE KEYS */;
INSERT INTO `profesores_cursos` VALUES (3,5,2),(5,2,1),(8,2,3),(9,8,4),(10,8,5),(11,5,7);
/*!40000 ALTER TABLE `profesores_cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programas`
--

DROP TABLE IF EXISTS `programas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programas` (
  `ID` double NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `duracion` double NOT NULL,
  `fecha_inicio` date NOT NULL,
  `facultad_id` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `facultad_id` (`facultad_id`),
  CONSTRAINT `programas_ibfk_1` FOREIGN KEY (`facultad_id`) REFERENCES `facultades` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programas`
--

LOCK TABLES `programas` WRITE;
/*!40000 ALTER TABLE `programas` DISABLE KEYS */;
INSERT INTO `programas` VALUES (1,'Ingeniería de Sistemas',5,'2020-01-15',1),(2,'Ingeniería Ambiental',5,'2020-04-20',1),(3,'Ingeniería de Procesos',5,'2020-04-20',1),(4,'Ingeniería Ambiental',5,'2020-04-20',1),(5,'Ingeniería Electrica',5,'2020-04-20',1),(6,'Biología',5,'2020-04-20',1),(7,'Ingeniería Agroindustrial',5,'2021-01-20',2),(8,'Ingeniería Agronómica',5,'2021-01-20',2),(9,'Ingeniería Forestal',5,'2021-01-20',2),(10,'Medicina Veterinaria y Zootecnia',5,'2021-01-20',2),(11,'Enfermería',4,'2020-12-11',3),(12,'Fisioterapia',3,'2020-12-11',3),(13,'Fonoaudiología',3,'2020-12-11',3),(14,'Tecnologia en Regencia de Farmacia',2,'2020-12-11',3),(15,'Administración de Empresas',5,'2019-11-11',4),(16,'Contaduría Publica',5,'2019-11-11',4),(17,'Economía',5,'2019-11-11',4),(18,'Mercadeo',5,'2019-11-11',4),(19,'Licenciatura en Educación Campesina-Rural',5,'2020-09-10',5),(20,'Licenciatura en Educación Fisica y Deportes',5,'2020-09-10',5),(21,'Licenciatura en Educación Infantil',5,'2020-09-10',5),(22,'Licenciatura en Español e Ingles',5,'2020-09-10',5),(23,'Licenciatura en Matemáticas',5,'2020-09-10',5),(24,'Sociologia',4,'2020-09-10',5);
/*!40000 ALTER TABLE `programas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10 23:41:40
