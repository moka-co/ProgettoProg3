

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import hoppin.*;

/**
 * Servlet implementation class Authentication
 */

@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletOutputStream out;
	AuthenticationGUI gui;
	ServeSingleton save;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
        save = ServeSingleton.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		
		out = response.getOutputStream();
		gui = new AuthenticationGUI(out);
		
		int x = save.getValue();
		//System.out.println(x);
		//Da rifattorizzare perché questa parte va messa in AuthenticationGUI
		switch(x) {
		case 0:
			gui.base();
			break;
		case 1:
			gui.badCredentials();
			break;		
		case 2:
			gui.registerSuccess();
			break;
		}
		save.setValue(0);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		//System.out.println(user);
		//System.out.println(passw);
		
		
		//_________________MYSQL________________________
		//MySQLConnect db = new MySQLConnect();
		MySQLConnect db = new MySQLConnect();
			
		//_________________________________________
		
		if ( ! user.equals("") && ! passw.equals("")) {
			boolean auth = db.login(user, passw);
			if ( auth == true) {
				String [] splitd = user.split("@");
				
				// Penso bisognerebbe creare una nuova classe Cookie e rifattorizzare questa parte
				Cookie biscotto = new Cookie(splitd[0], splitd[1]);
				biscotto.setMaxAge(0);
				
				String comment= LocalDateTime.now().toString();
				splitd = user.split("@");
				biscotto = new Cookie(splitd[0], splitd[1]);
				String cookiePath = request.getContextPath();
				biscotto.setPath(cookiePath);
				biscotto.setComment(comment);
				biscotto.setDomain("aaaa");
				biscotto.setMaxAge(600);
				response.addCookie(biscotto);
				
				int id = db.getId(user);
				
				Cookie dataCookie = new Cookie("id",Integer.toString(id));
				dataCookie.setMaxAge(600);
				dataCookie.setDomain("aaaa");
				response.addCookie(dataCookie);
				
				response.sendRedirect("/hoppin/HomePage.jsp");
				
			}else {
				System.out.println("Utente non trovato");
				save.setValue(1);
				response.sendRedirect("/hoppin/Authentication");
			}
		}else {
			save.setValue(1);
			response.sendRedirect("/hoppin/Authentication");
		}
	
	}

}
