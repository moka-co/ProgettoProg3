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
	PRIMARY KEY(id)
);

INSERT INTO User (id, completeName, email, passw_hash, AccType)
VALUES (1,'Pinco Pallo','pinco@pallo','zinco','Owner');

select * from User where email='pinco@pallo' and passw_hash='zinco';
-- Query esempio per verificare utente
