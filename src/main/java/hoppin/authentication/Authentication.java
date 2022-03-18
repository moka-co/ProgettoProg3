package hoppin.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 * Implementa il design pattern Strategy, utilizzando
 * un interfaccia funzionale
 *
 */

interface AuthStrategy {
	boolean run();
}

/**
 * 
 * Gestisce le richieste HTTP GET e POST nel sottosistema di autenticazione
 * Usa le classi {@link AuthFactory}, {@link MySQLAuth} e la Java Server Page <mono>index.jsp</mono>
 * Redireziona a {@link hoppin.hotelinfo.HotelInfoManagement} oppure {@link Authentication}
 * 
 * Definisce dinamicamente <mono>AuthStrategy</mono> utilizzando funzioni lambda e funzioni anonime
 */

@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Authentication() {
        super();
    }


    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/hoppin/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String user = request.getParameter("email");
		String passw = request.getParameter("password");
		
		boolean stringNotNull = user != null && passw != null;
		boolean stringNotEmpty = ! user.equals("") && ! user.equals("");

		
		if ( stringNotNull && stringNotEmpty ) { //Se user e password non sono ne vuote ne null
			
			//Verifica se i dati inseriti sono corretti e in tal caso imposta un cookie
			
			AuthStrategy strategy = () -> {
				AuthFactory factory = new AuthFactory();
				MySQLAuth db = (MySQLAuth) factory.makeDatabaseConnect(request);
				boolean auth = db.login(user, passw);
				
				if ( auth ) {
					factory.makeCookieSetter(response,user);
				}
				
				db.disconnect();
				return auth;
			};
			
			if ( strategy.run() == true ) { //Login effettuato con successo
				response.sendRedirect("/hoppin/HotelInfoManagement");
			
			}else { //Rimanda ad Authentication
				response.sendRedirect("/hoppin/Authentication");
			}

			
		} // fine if
	
	} // fine doPost

} // fine classe
