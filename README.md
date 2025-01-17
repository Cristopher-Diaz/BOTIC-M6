# Evaluación M6 Bootacmp Java

## Query Inicial para la Base de Datos

Antes de comenzar con la implementación del sistema, asegúrate de ejecutar las siguientes instrucciones SQL en tu base de datos para crear las tablas necesarias:

```sql
DROP DATABASE IF EXISTS m6_final_drilling;

CREATE DATABASE m6_final_drilling;

USE m6_final_drilling;

-- Tabla para usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabla para alumnos
CREATE TABLE alumnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para materias
CREATE TABLE materias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    alumno_id BIGINT,
    FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE SET NULL ON UPDATE CASCADE
);
```

