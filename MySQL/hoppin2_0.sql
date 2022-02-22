-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 22, 2022 alle 13:13
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

-- --------------------------------------------------------

--
-- Struttura della tabella `documenti`
--

CREATE TABLE `documenti` (
  `type` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `number` varchar(20) NOT NULL,
  `relase` date DEFAULT NULL,
  `expiration` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `hotel`
--

CREATE TABLE `hotel` (
  `Name` varchar(20) NOT NULL,
  `Via` varchar(255) NOT NULL,
  `City` varchar(255) NOT NULL,
  `Postcode` varchar(255) DEFAULT NULL,
  `Stars` int(11) DEFAULT NULL,
  `Balance` float DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `OwnerId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `hotel`
--

INSERT INTO `hotel` (`Name`, `Via`, `City`, `Postcode`, `Stars`, `Balance`, `description`, `OwnerId`) VALUES
('Hotel Belvedere ', 'Toledo,22', 'Napoli', '80234', 4, NULL, NULL, NULL),
('Hotel Hoppo', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL),
('Hotel Quattro Stagio', 'Gramsci, 33', 'Roma', '1234', 5, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `package`
--

CREATE TABLE `package` (
  `name` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `Hotel` varchar(20) NOT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `package`
--

INSERT INTO `package` (`name`, `description`, `Hotel`, `price`) VALUES
('Base', NULL, 'Hotel Belvedere ', NULL),
('Gold', NULL, 'Hotel Belvedere ', 50),
('Silver', NULL, 'Hotel Belvedere ', 30),
('Base', NULL, 'Hotel Hoppo', NULL),
('Gold', NULL, 'Hotel Hoppo', 50),
('Silver', NULL, 'Hotel Hoppo', 30),
('Base', NULL, 'Hotel Quattro Stagio', NULL),
('Gold', NULL, 'Hotel Quattro Stagio', 50),
('Silver', NULL, 'Hotel Quattro Stagio', 50);

-- --------------------------------------------------------

--
-- Struttura della tabella `paymenttransaction`
--

CREATE TABLE `paymenttransaction` (
  `N_Card` varchar(20) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `LastName` varchar(20) DEFAULT NULL,
  `expiration` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `prenota`
--

CREATE TABLE `prenota` (
  `id` int(11) NOT NULL,
  `Check_in` date DEFAULT NULL,
  `Check_out` date DEFAULT NULL,
  `Package` varchar(20) DEFAULT NULL,
  `Tassa_Soggiorno` float DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `Number` int(11) DEFAULT NULL,
  `N_Card` varchar(20) DEFAULT NULL,
  `Hotel` varchar(20) DEFAULT NULL,
  `numero_documento` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `room`
--

CREATE TABLE `room` (
  `Number` int(11) NOT NULL,
  `Type` varchar(20) DEFAULT NULL,
  `Hotel` varchar(20) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `room`
--

INSERT INTO `room` (`Number`, `Type`, `Hotel`, `price`) VALUES
(1, 'Singola', 'Hotel Belvedere ', 70),
(2, 'Matrimoniale', 'Hotel Belvedere ', 100),
(3, 'Tripla', 'Hotel Belvedere ', 160),
(4, 'Quadrupla', 'Hotel Belvedere ', 220),
(1, 'Singola', 'Hotel Hoppo ', 70),
(2, 'Matrimoniale', 'Hotel Hoppo ', 100),
(3, 'Tripla', 'Hotel Hoppo ', 160),
(4, 'Quadrupla', 'Hotel Hoppo ', 220),
(1, 'Singola', 'Hotel Quattro Stagio', 70),
(2, 'Matrimoniale', 'Hotel Quattro Stagio', 100),
(3, 'Tripla', 'Hotel Quattro Stagio', 160),
(4, 'Quadrupla', 'Hotel Quattro Stagio', 220);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `completeName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `passw_hash` varchar(255) NOT NULL,
  `accType` varchar(255) NOT NULL,
  `sid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`id`, `completeName`, `email`, `passw_hash`, `accType`, `sid`) VALUES
(1, 'Mario Rossi', 'mario.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(2, 'Mario Tortora', 'mario.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(3, 'Raffaele Tortora', 'raffaele.tortora@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL),
(4, 'Sara Rossi', 'sara.rossi@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(5, 'Sara Chiaglio', 'sara.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(6, 'Mia Chiaglio', 'mia.chiaglio@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Cliente', NULL),
(7, 'Raffaella Vano', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Dipendente', NULL),
(8, 'Raffaella Vart', 'raffella.vano@gmail.it', '06db9ac82dfc4c9146de64b669a675a3', 'Proprietario', NULL);

--
-- Trigger `users`
--
DELIMITER $$
CREATE TRIGGER `Encypt_password` BEFORE INSERT ON `users` FOR EACH ROW BEGIN
set new.passw_hash=md5(new.passw_hash);
END
$$
DELIMITER ;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `documenti`
--
ALTER TABLE `documenti`
  ADD PRIMARY KEY (`number`);

--
-- Indici per le tabelle `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`Name`),
  ADD KEY `FK_Hotel_Users` (`OwnerId`);

--
-- Indici per le tabelle `package`
--
ALTER TABLE `package`
  ADD PRIMARY KEY (`Hotel`,`name`);

--
-- Indici per le tabelle `paymenttransaction`
--
ALTER TABLE `paymenttransaction`
  ADD PRIMARY KEY (`N_Card`);

--
-- Indici per le tabelle `prenota`
--
ALTER TABLE `prenota`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_prenota_pagamenti` (`N_Card`),
  ADD KEY `FK_prenota_room` (`Hotel`,`Number`),
  ADD KEY `FK_prenota_user` (`id_user`),
  ADD KEY `FK_prenota_documenti` (`numero_documento`);

--
-- Indici per le tabelle `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`Hotel`,`Number`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `prenota`
--
ALTER TABLE `prenota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `hotel`
--
ALTER TABLE `hotel`
  ADD CONSTRAINT `FK_Hotel_Users` FOREIGN KEY (`OwnerId`) REFERENCES `users` (`id`);

--
-- Limiti per la tabella `package`
--
ALTER TABLE `package`
  ADD CONSTRAINT `FK_Package_Hotel` FOREIGN KEY (`Hotel`) REFERENCES `hotel` (`Name`);

--
-- Limiti per la tabella `prenota`
--
ALTER TABLE `prenota`
  ADD CONSTRAINT `FK_prenota_documenti` FOREIGN KEY (`numero_documento`) REFERENCES `documenti` (`number`),
  ADD CONSTRAINT `FK_prenota_pagamenti` FOREIGN KEY (`N_Card`) REFERENCES `paymenttransaction` (`N_Card`),
  ADD CONSTRAINT `FK_prenota_room` FOREIGN KEY (`Hotel`,`Number`) REFERENCES `room` (`Hotel`, `Number`),
  ADD CONSTRAINT `FK_prenota_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Limiti per la tabella `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK_Hotel` FOREIGN KEY (`Hotel`) REFERENCES `hotel` (`Name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
