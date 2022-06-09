CREATE IF NOT EXISTS DATABASE hoppin;
USE hoppin;
GRANT ALL ON hoppin.* to 'TuoNomeUtente'@'localhost';

DROP TABLE User;

CREATE TABLE IF NOT EXISTS User (
	id int NOT NULL,
	completeName varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	passw_hash varchar(255) NOT NULL,
	accType	varchar(255) NOT NULL,
	sid		int,
	PRIMARY KEY(id)
);

INSERT INTO User (id, completeName, email, passw_hash, accType)
VALUES (1,'Pinco Pallo','pinco@pallo','zinco','Owner');

INSERT INTO User (id, completeName, email, passw_hash, accType, sid)
VALUES (2, "Salvatore Salvia",  "salvo@salvia", "salvia", "Employee",1 );

select * from User where email='pinco@pallo' and passw_hash='zinco';
-- Query esempio per verificare utente

DROP TABLE Hotel;

CREATE TABLE IF NOT EXISTS Hotel (
	OwnerId int NOT NULL,
	Name varchar(255) NOT NULL,
	balance float not NULL,
	Via varchar(255) NOT NULL,
	City varchar(255) NOT NULL,
	Postcode varchar(255), 
	PRIMARY KEY (Name),
	FOREIGN KEY (OwnerId) REFERENCES User(id)
);

# Esempio di inserimento tabella Hotel:
INSERT INTO Hotel(OwnerId, Name, balance, Via, City, Postcode) 
VALUES (1, "Hotel Bello", 100000, "Via sul mare 44", "Posillia", "99999");

# Esempio di query per trovare un Hotel in base al nome del proprietario:
select * from Hotel where OwnerId=(select id from User where completeName='Pinco Pallo');


DROP TABLE Room;

CREATE TABLE IF NOT EXISTS Room(
	Num int NOT NULL,
	HotelName varchar(255) NOT NULL,
	CONSTRAINT PK_Room PRIMARY KEY(Num, HotelName),
	CONSTRAINT PK_Hotel_Name FOREIGN KEY (HotelName) REFERENCES Hotel(Name)
);

# Esempio popolamento tabella Room:

insert into Room(Num, HotelName) 
values (101, "Hotel Bello"), (102, "Hotel Bello"),
		(103, "Hotel Bello"), (104, "Hotel Bello"),
			(105, "Hotel Bello"), (106, "Hotel Bello");

DROP TABLE Reservation;

CREATE TABLE IF NOT EXISTS Reservation(
	Customer int,
	CustomerName varchar(255) NOT NULL,
	id int NOT NULL,
	HotelName varchar(255) NOT NULL,
	RoomNum int NOT NULL,
	PRIMARY KEY(id),
	CheckIn DATE NOT NULL,
	CheckOut DATE NOT NULL,
	Package varchar(255) NOT NULL,


	# Che poi in realtà, ce la possiamo risparmiare
	#Si potrebbe mettere Customer + room che insieme unici
	CONSTRAINT Customer FOREIGN KEY(Customer) REFERENCES User(id),
	CONSTRAINT HotelName FOREIGN KEY(HotelName) REFERENCES Hotel(name),
	CONSTRAINT RoomNum FOREIGN KEY(RoomNum) REFERENCES Room(Num)
);

-- Esempio popolamento Reservation

INSERT INTO User (id, completeName, email, passw_hash, accType)
VALUES (5, "Giovanni Muscolo","giovannimuscolo@libero.it", "strong", "Customer");

INSERT INTO Reservation (Customer, CustomerName, id, HotelName, RoomNum, CheckIn, CheckOut, Package) 
VALUES (5, "Giovanni Muscolo", 1, "Hotel Bello", "101", STR_TO_DATE("20-02-2022", "%d-%m-%Y"), STR_TO_DATE("24-02-2022", "%d-%m-%Y"), "Base" );

INSERT INTO Reservation (Customer, CustomerName, id, HotelName, RoomNum, CheckIn, CheckOut, Package)  VALUES 
(5, "Alessandro Galli", 3, "Hotel Bello", "104", STR_TO_DATE('20-02-2022', "%d-%m-%Y"), STR_TO_DATE("24-02-2022", "%d-%m-%Y"), "Base" );

-- Creazione tabella

create table Season   (  Type varchar(20), 
		       Start date, 
		       End date, 
		       PercentIncrease int, 
		       Hotel varchar(255),     
		       
		       CONSTRAINT FK_stagione_hotel FOREIGN KEY(Hotel) REFERENCES Hotel (Name),     
		       CONSTRAINT PK_stagione PRIMARY Key(start,end,hotel)   
		      );


INSERT INTO Season (Type, Start, End, PercentIncrease, Hotel) 
VALUES ('Bassa Stagione', '2022-06-1', '2022-06-30', 20, 'Hotel Bello'),
	('Media Stagione', '2022-07-01', '2022-07-31', 35, 'Hotel Bello'),
	('Alta Stagione', '2022-08-01', '2022-08-19', 50, 'Hotel Bello');




