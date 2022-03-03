

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hoppin.sql.MySQLAuth;
import hoppin.factory.AbstractFactory;
import hoppin.factory.AuthFactory;


@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Authentication() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/hoppin/Index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		response.setContentType("text/html");
		
		if ( request.getParameter("register") != null ) {
			//response.sendRedirect("/hoppin/Register");
			RequestDispatcher rd = request.getRequestDispatcher("/Register");
			rd.forward(request, response);
			return;
		}
		
		String user = request.getParameter("email");
		String passw = request.getParameter("password");

		//MySQLConnect db = new MySQLConnect();
			
		//_________________________________________
		
		if ( ! user.equals("") && ! passw.equals("")) {
			MySQLAuth db = new MySQLAuth();
			boolean auth = db.login(user, passw);
			if ( auth == true) {
				
				AbstractFactory factory = new AuthFactory();
				((AuthFactory) factory).makeCookieSetter(response,user);
				
				response.sendRedirect("/hoppin/HomePage.jsp");
				
			}else {
				System.out.println("Utente non trovato");
				response.sendRedirect("/hoppin/Authentication");
			}
			db.disconnect();
		}else {
			response.sendRedirect("/hoppin/Authentication");
		}
	
	}

}
