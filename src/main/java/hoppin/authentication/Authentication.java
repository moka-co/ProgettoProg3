package hoppin.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


interface AuthStrategy {
	boolean run();
}

public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Authentication() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/hoppin/Index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String user = request.getParameter("email");
		String passw = request.getParameter("password");
		
		boolean stringNotNull = user != null && passw != null;
		boolean stringNotEmpty = ! user.equals("") && ! user.equals("");

		
		if ( stringNotNull && stringNotEmpty ) {
			
			//strategy verifica se i dati inseriti sono corretti
			//e solo in tal caso setta il cookie
			//poi chiude la connessione col database e ritorna
			
			AuthStrategy strategy = () -> {
				MySQLAuth db = new MySQLAuth();
				boolean auth = db.login(user, passw);
				
				if ( auth ) {
					AuthFactory factory = new AuthFactory();
					factory.makeCookieSetter(response,user);
				}
				
				db.disconnect();
				return auth;
			};
			
			if ( strategy.run() == true ) {
				response.sendRedirect("/hoppin/HotelInfoManagement");
			}else {
				System.out.println("Utente non trovato");
				response.sendRedirect("/hoppin/Authentication");
			}

			
		}
	
	}

}
