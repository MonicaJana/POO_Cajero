CREATE DATABASE CAJERO;
USE CAJERO;

CREATE TABLE usuarios (
 id INT AUTO_INCREMENT PRIMARY KEY,
 nombreUsuario VARCHAR(100) unique not null,
 contraseña VARCHAR(64) not null,
 saldoActual double not null
);

CREATE TABLE transacciones (
    numtransaccion INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) not null,
    monto double not null,
    fecha datetime,
    FKusuario int not null
);
ALTER TABLE transacciones AUTO_INCREMENT = 100;
drop table usuarios;
INSERT INTO usuarios (nombreUsuario, contraseña, saldoActual)
VALUES
('Monica Jana', sha2('moni123'), 500),
('Scarlett Luna', sha2('scarlett123') ,700);

SELECT * FROM usuarios;

INSERT INTO transacciones(tipo,monto,fecha,FKusuario)
VALUES
('retiro',100,'2024/02/13',1);

SELECT *FROM transacciones;

