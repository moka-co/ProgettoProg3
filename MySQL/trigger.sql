-- Incollare i due trigger separatemente 
DELIMITER |
create TRIGGER Encypt_password
BEFORE INSERT on cliente
FOR EACH ROW
BEGIN
set new.passw_hash=md5(new.passw_hash);
END

| DELIMITER


DELIMITER |
create TRIGGER Encypt_password
BEFORE INSERT on dipendente
FOR EACH ROW
BEGIN
set new.passw_hash=md5(new.passw_hash);
END

| DELIMITER
