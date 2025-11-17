drop database if exists viajesbus;
create database viajesbus;
use viajesbus;


CREATE TABLE IF NOT EXISTS viajes (
    codigo INT PRIMARY KEY AUTO_INCREMENT,
    destino varchar(50) NOT NULL,
    plazas_disponibles INT NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes(
	codigo int primary key auto_increment
);

CREATE TABLE IF NOT EXISTS reservas (
    numero_reserva INT PRIMARY KEY AUTO_INCREMENT,
    codigo_viaje INT NOT NULL,
    codigo_cliente INT NOT NULL,
    plazas_reservadas INT NOT NULL,
    estado varchar(1) DEFAULT 'A',
    FOREIGN KEY (codigo_viaje) REFERENCES viajes(codigo),
    foreign key (codigo_cliente) references clientes(codigo)
);

DELIMITER $$
CREATE TRIGGER liberar_plazas_canceladas
AFTER UPDATE ON reservas
FOR EACH ROW
BEGIN
-- Comprueba si el estado de la reserva ha cambiado de 'A' (Activa) a 'C' (Cancelada)
IF NEW.estado = 'C' AND OLD.estado = 'A' THEN
-- Actualiza la tabla 'viajes': incrementa las plazas libres
-- Se usa OLD.plazas porque son las plazas que estaban reservadas antes de la cancelación.
-- Se usa OLD.numviaje para identificar el viaje al que pertenece la reserva.
UPDATE viajes
SET plazas_disponibles = plazas_disponibles + OLD.plazas_reservadas
WHERE codigo = OLD.codigo_viaje;
END IF;
END$$
DELIMITER ;

select * from viajes;

insert into viajes(destino,plazas_disponibles) values ('Malaga',100) ;
insert into clientes(codigo) values (5) ;
insert into reservas(numero_reserva,codigo_viaje,codigo_cliente,plazas_reservadas,estado) values (1,1,7,10,'A') ;
insert into reservas(numero_reserva,codigo_viaje,codigo_cliente,plazas_reservadas,estado) values (2,1,5,10,'A') ;
select * from reservas;

UPDATE reservas SET estado = 'C' WHERE codigo_cliente = 5 AND codigo_viaje = 1;

