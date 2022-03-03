-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 23, 2022 alle 10:15
-- Versione del server: 10.4.22-MariaDB
-- Versione PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hoppin2.0`
--

--
-- Dump dei dati per la tabella `cliente`
--

INSERT INTO Customer (id, completeName, email, passw_hash) VALUES
(1, 'Mario Rossi', 'mario.rossi@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2'),
(4, 'Sara Rossi', 'sara.rossi@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2'),
(5, 'Sara Chiaglio', 'sara.chiaglio@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2'),
(6, 'Mia Chiaglio', 'mia.chiaglio@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2');

--
-- Dump dei dati per la tabella `dipendente`
--

INSERT INTO Employee (id, completeName, email, passw_hash, accType, sid) VALUES
(2, 'Mario Tortora', 'mario.tortora@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2', 'Employee', 3),
(3, 'Raffaele Tortora', 'raffaele.tortora@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2', 'Owner', NULL),
(7, 'Raffaella Vano', 'raffella.vano@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2', 'Employee', 8),
(8, 'Raffaella Vart', 'raffella.vano@gmail.it', '5160c4f3a3aeac992248ea1a191e10d2', 'Owner', NULL),
(9, 'Pinco Pallo', 'pinco@pallo', '4ae8cde26bdd8620847a388e968d0f43', 'Owner', NULL);

--
-- Dump dei dati per la tabella `hotel`
--

INSERT INTO Hotel (Name, Via, City, Postcode, Stars, Balance, description, OwnerId) VALUES
('Hotel Belvedere', 'Toledo,22', 'Napoli', '80234', 4, NULL, NULL, 9),
('Hotel Hoppo', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL);

--
-- Dump dei dati per la tabella `package`
--

INSERT INTO Package (name, price, description, Hotel) VALUES
('Base', NULL, NULL, 'Hotel Belvedere'),
('Gold', 50, NULL, 'Hotel Belvedere'),
('Silver', 35, NULL, 'Hotel Belvedere'),
('Base', NULL, NULL, 'Hotel Hoppo'),
('Gold', 50, NULL, 'Hotel Hoppo'),
('Silver', 35, NULL, 'Hotel Hoppo');

--
-- Dump dei dati per la tabella `prezziario`
--

INSERT INTO PriceList (Type_room, price, hotel) VALUES
('Matrimoniale', 100, 'Hotel Belvedere'),
('Quadrupla', 220, 'Hotel Belvedere'),
('Singola', 70, 'Hotel Belvedere'),
('Tripla', 160, 'Hotel Belvedere'),
('Matrimoniale', 100, 'Hotel Hoppo'),
('Quadrupla', 220, 'Hotel Hoppo'),
('Singola', 70, 'Hotel Hoppo'),
('Tripla', 160, 'Hotel Hoppo');

--
-- Dump dei dati per la tabella `room`
--

INSERT INTO Room (Number, Type, Hotel) VALUES
(1, 'Singola', 'Hotel Belvedere'),
(2, 'Matrimoniale', 'Hotel Belvedere'),
(3, 'Tripla', 'Hotel Belvedere'),
(4, 'Quadrupla', 'Hotel Belvedere'),
(1, 'Singola', 'Hotel Hoppo'),
(2, 'Matrimoniale', 'Hotel Hoppo'),
(3, 'Tripla', 'Hotel Hoppo'),
(4, 'Quadrupla', 'Hotel Hoppo');

--
-- Dump dei dati per la tabella `stagione`
--

INSERT INTO Season (type, start, end, aumento_percentuale, Hotel) VALUES
('Bassa stagione', '2022-06-01', '2022-06-30', 20, 'Hotel Belvedere'),
('Bassa stagione', '2022-06-01', '2022-06-30', 20, 'Hotel Hoppo'),
('Media Stagione', '2022-07-01', '2022-07-31', 35, 'Hotel Belvedere'),
('Media Stagione', '2022-07-01', '2022-07-31', 35, 'Hotel Hoppo'),
('Alta Stagione', '2022-08-01', '2022-08-19', 50, 'Hotel Belvedere'),
('Alta Stagione', '2022-08-01', '2022-08-19', 50, 'Hotel Hoppo'),
('Media Stagione', '2022-08-20', '2022-08-31', 35, 'Hotel Belvedere'),
('Media Stagione', '2022-08-20', '2022-08-31', 35, 'Hotel Hoppo'),
('Bassa stagione', '2022-09-01', '2022-09-30', 20, 'Hotel Belvedere'),
('Bassa stagione', '2022-09-01', '2022-09-30', 20, 'Hotel Hoppo');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
