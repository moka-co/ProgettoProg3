-- DROP TABLE `documenti`, `hotel`, `package`, `paymenttransaction`, `prenota`, `room`, `user`;


CREATE TABLE package (
    name varchar(20) ,
    price float,
  description varchar(100)

);



CREATE TABLE paymenttransaction (
  N_Card varchar(20) NOT NULL,
  Name varchar(20) ,
  LastName varchar(20) ,
  expiration date ,
  PRIMARY KEY (N_Card)
);



CREATE TABLE hotel (
  Name varchar(20),
  Via varchar(255) NOT NULL,
	City varchar(255) NOT NULL,
	Postcode varchar(255),
  Stars int,
  Balance float ,
  description varchar(100),
  Primary Key (Name)
);



CREATE TABLE prenota (

  id int NOT NULL AUTO_INCREMENT,

  Check_in date ,
  Check_out date ,
  Package varchar(20) ,
  Tassa_Soggiorno float,

  PRIMARY KEY (id)
);



CREATE TABLE room (

  Number int ,
  Type varchar(20)
);




CREATE TABLE Users (
  id int NOT NULL AUTO_INCREMENT,
  completeName varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  passw_hash varchar(255) NOT NULL,
  accType varchar(255) NOT NULL,
  sid int,
  PRIMARY KEY (id)
);



CREATE TABLE documenti (
  type varchar(50) ,
  name varchar(50) ,
  LastName varchar(50) ,
  number varchar(20) NOT NULL,
  relase date ,
  expiration date ,
  PRIMARY KEY (number)
);

create table prezziario
  (type_room varchar(20),
  price float,

  -- chiave esterna
  hotel varchar(20),

  CONSTRAINT FK_prezziario_hotel FOREIGN KEY (hotel) REFERENCES hotel (name),

  CONSTRAINT PK_prezziario PRIMARY KEY(hotel,type_room)
  );

create table stagione
  (
    type varchar(20),
    start date,
    end date,
    aumento_percentuale int,

    -- chiave esterna
    Hotel varchar(20),
    CONSTRAINT FK_stagione_prezziario FOREIGN KEY(hotel) REFERENCES hotel (name)
  );

  create table documenti_memorizzati
    (
    numero_documento varchar(20),
    id_prenota int,
    CONSTRAINT fk_documentiMemorizzati_documenti FOREIGN KEY(numero_documento) REFERENCES documenti(number),
    CONSTRAINT fk_documentiMemorizzati_prenotazione FOREIGN KEY(id_prenota) REFERENCES prenota(id)
    );






alter table hotel add OwnerId int,
add CONSTRAINT FK_Hotel_Users FOREIGN KEY(OwnerId) REFERENCES users (id);



alter table room add Hotel varchar(20),
add CONSTRAINT PK_Room PRIMARY Key(Hotel,Number),
add CONSTRAINT FK_Hotel FOREIGN Key(Hotel) REFERENCES Hotel(Name);


alter table prenota add id_user int NOT NULL,
add Number int,
add N_Card varchar(20) ,
add Hotel varchar(20),
add CONSTRAINT FK_prenota_pagamenti FOREIGN KEY (N_Card) REFERENCES paymenttransaction (N_Card),
add CONSTRAINT FK_prenota_room FOREIGN KEY (Hotel,Number) REFERENCES room (Hotel,Number),
add CONSTRAINT FK_prenota_user FOREIGN KEY (id_user) REFERENCES users (id);

alter table prenota add numero_documento varchar(20),
add CONSTRAINT FK_prenota_documenti FOREIGN KEY(numero_documento) REFERENCES documenti (number);







Alter TABLE Package add Hotel varchar(20) ,
add CONSTRAINT PK_Package PRIMARY Key (Hotel,Name),
add CONSTRAINT FK_Package_Hotel FOREIGN KEY (Hotel) REFERENCES hotel (Name);










DELIMITER |
create TRIGGER Encypt_password
BEFORE INSERT on users
FOR EACH ROW
BEGIN
set new.passw_hash=md5(new.passw_hash);
END

| DELIMITER






INSERT INTO `hotel` (`Name`, `Via`, `City`, `Postcode`, `Stars`, `Balance`, `description`, `OwnerId`) VALUES
('Hotel Belvedere ', 'Toledo,22', 'Napoli', '80234', 4, NULL, NULL, NULL),
('Hotel Hoppo', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL),
('Hotel Quattro Stagio', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL);
INSERT INTO `room` (`Number`, `Type`, `Hotel`) VALUES
(1, 'Singola', 'Hotel Belvedere '),
(2, 'Matrimoniale', 'Hotel Belvedere '),
(3, 'Tripla', 'Hotel Belvedere '),
(4, 'Quadrupla', 'Hotel Belvedere '),
(1, 'Singola', 'Hotel Hoppo '),
(2, 'Matrimoniale', 'Hotel Hoppo '),
(3, 'Tripla', 'Hotel Hoppo '),
(4, 'Quadrupla', 'Hotel Hoppo '),
(1, 'Singola', 'Hotel Quattro Stagio'),
(2, 'Matrimoniale', 'Hotel Quattro Stagio'),
(3, 'Tripla', 'Hotel Quattro Stagio'),
(4, 'Quadrupla', 'Hotel Quattro Stagio');

INSERT INTO `users` (`id`, `completeName`, `email`, `passw_hash`, `accType`, `sid`) VALUES
(1, 'Mario Rossi', 'mario.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(2, 'Mario Tortora', 'mario.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(3, 'Raffaele Tortora', 'raffaele.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL),
(4, 'Sara Rossi', 'sara.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(5, 'Sara Chiaglio', 'sara.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(6, 'Mia Chiaglio', 'mia.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(7, 'Raffaella Vano', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(8, 'Raffaella Vart', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL);
