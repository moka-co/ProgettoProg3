package hoppin.util.factory;

import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

public interface AbstractFactory {

	public abstract MySQLConnect makeDatabaseConnect(HttpServletRequest request);
}
