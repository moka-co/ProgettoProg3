package hoppin.util.factory;

import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Interfaccia che viene utilizzata per implementare il design pattern AbstractFactory
 *
 */
public interface AbstractFactory {

	public abstract MySQLConnect makeDatabaseConnect(HttpServletRequest request);
}
