-- DROP TABLE `documenti`, `hotel`, `package`, `paymenttransaction`, `prenota`, `room`, `user`;


CREATE TABLE Package (
    name varchar(20) ,
    price float,
  description varchar(100)

);



CREATE TABLE PaymentTransaction (
  N_Card varchar(20) NOT NULL,
  Name varchar(20) ,
  LastName varchar(20) ,
  expiration date ,
  PRIMARY KEY (N_Card)
);



CREATE TABLE Hotel (
  Name varchar(20),
  Via varchar(255) NOT NULL,
	City varchar(255) NOT NULL,
	Postcode varchar(255),
  Stars int,
  Balance float ,
  description varchar(100),
  Primary Key (Name)
);



CREATE TABLE Reservation (

  id int NOT NULL AUTO_INCREMENT,

  Check_in date ,
  Check_out date ,
  Package varchar(20) ,
  Tassa_Soggiorno float,
  Name varchar(255),

  PRIMARY KEY (id)
);



CREATE TABLE Room (

  Number int ,
  Type varchar(20)
);




CREATE TABLE Customer (
  id int NOT NULL AUTO_INCREMENT,
  completeName varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  passw_hash varchar(255) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE Employee (
  id int NOT NULL AUTO_INCREMENT,
  completeName varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  passw_hash varchar(255) NOT NULL,
  accType varchar(255) NOT NULL,
  sid int,
  PRIMARY KEY (id)
);


CREATE TABLE Documents (
  type varchar(50) ,
  name varchar(50) ,
  LastName varchar(50) ,
  number varchar(20) NOT NULL,
  relase date ,
  expiration date ,
  PRIMARY KEY (number)
);

create table PriceList
  (
  type_room varchar(20),
  price float,

  -- chiave esterna
  Hotel varchar(20),

  CONSTRAINT FK_prezziario_hotel FOREIGN KEY (Hotel) REFERENCES Hotel (Name),

  CONSTRAINT PK_prezziario PRIMARY KEY(Hotel,type_room)
  );

create table Season
  (
    type varchar(20),
    start date,
    end date,
    PercentIncrease int,

    -- chiave esterna
    Hotel varchar(20),
    CONSTRAINT FK_stagione_hotel FOREIGN KEY(Hotel) REFERENCES Hotel (Name),
    CONSTRAINT PK_stagione PRIMARY Key(start,end,hotel)
  );

  create table SavedDocuments
    (
    numero_documento varchar(20),
    id_prenota int,
    CONSTRAINT fk_documentiMemorizzati_documenti FOREIGN KEY(numero_documento) REFERENCES Documents(number),
    CONSTRAINT fk_documentiMemorizzati_prenotazione FOREIGN KEY(id_prenota) REFERENCES Reservation(id)
    );






alter table Hotel add OwnerId int,
add CONSTRAINT FK_Hotel_Dipendente FOREIGN KEY(OwnerId) REFERENCES Employee (id);



alter table Room add Hotel varchar(20),
add CONSTRAINT PK_Room PRIMARY Key(Hotel,Number),
add CONSTRAINT FK_Hotel FOREIGN Key(Hotel) REFERENCES Hotel(Name);


alter table Reservation
add Number int,
add N_Card varchar(20) ,
add Hotel varchar(20),
add CONSTRAINT FK_prenota_pagamenti FOREIGN KEY (N_Card) REFERENCES PaymentTransaction (N_Card),
add CONSTRAINT FK_prenota_room FOREIGN KEY (Hotel,Number) REFERENCES Room (Hotel,Number);

alter table Reservation add DocumentNumber varchar(20),
add CONSTRAINT FK_prenota_documenti FOREIGN KEY(DocumentNumber) REFERENCES Documents (number);







Alter TABLE Package add Hotel varchar(20) ,
add CONSTRAINT PK_Package PRIMARY Key (Hotel,Name),
add CONSTRAINT FK_Package_Hotel FOREIGN KEY (Hotel) REFERENCES Hotel (Name);

alter table Package drop column description;
alter table Package add description text;


create table HotelImages (
	Hotel varchar(20),
    Image longblob NOT NULL,
    
    CONSTRAINT FK_hotelImages_hotel FOREIGN KEY (Hotel) REFERENCES Hotel (Name)
) 











/*
INSERT INTO `hotel` (`Name`, `Via`, `City`, `Postcode`, `Stars`, `Balance`, `description`, `OwnerId`) VALUES
('Hotel Belvedere ', 'Toledo,22', 'Napoli', '80234', 4, NULL, NULL, NULL),
('Hotel Hoppo', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL),
INSERT INTO `room` (`Number`, `Type`, `Hotel`) VALUES
(1, 'Singola', 'Hotel Belvedere '),
(2, 'Matrimoniale', 'Hotel Belvedere '),
(3, 'Tripla', 'Hotel Belvedere '),
(4, 'Quadrupla', 'Hotel Belvedere '),
(1, 'Singola', 'Hotel Hoppo '),
(2, 'Matrimoniale', 'Hotel Hoppo '),
(3, 'Tripla', 'Hotel Hoppo '),
(4, 'Quadrupla', 'Hotel Hoppo '),

INSERT INTO `Cliente` (`id`, `completeName`, `email`, `passw_hash`, `sid`) VALUES
(1, 'Mario Rossi', 'mario.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', NULL),
(4, 'Sara Rossi', 'sara.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', NULL),
(5, 'Sara Chiaglio', 'sara.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', NULL),
(6, 'Mia Chiaglio', 'mia.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', NULL);

INSERT INTO `Dipendente` (`id`, `completeName`, `email`, `passw_hash`, `accType`, `sid`) VALUES
(2, 'Mario Tortora', 'mario.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(3, 'Raffaele Tortora', 'raffaele.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL),
(7, 'Raffaella Vano', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(8, 'Raffaella Vart', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL);
*/
