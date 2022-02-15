

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
		System.out.println(x);
		//Da rifattorizzare perché questa parte va messa in AuthenticationGUI
		switch(x) {
		case 0:
			gui.base();
			break;
		case 1:
			gui.badCredentials();
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
		
		String user = request.getParameter("email");
		String passw = request.getParameter("password");
		
		System.out.println(user);
		System.out.println(passw);
		
		response.setContentType("text/html");
		//response.sendRedirect("/hoppin/Authentication");
		
		if ( ! user.equals("") && ! passw.equals("")) {
			if (user.equals("pinco@pallo") ) {
				
				Cookie newCookie = new Cookie("pinco","pallo");
				newCookie.setMaxAge(0);
				
				String name="pinco";
				LocalDateTime dat = LocalDateTime.now();
				String value = "pallo";
				String comment= dat.toString();
				Cookie biscotto = new Cookie(name, value);
				String cookiePath = request.getContextPath();
				System.out.println(cookiePath);
				biscotto.setPath(cookiePath);
				biscotto.setComment(comment);
				biscotto.setDomain("aaaa");
				biscotto.setMaxAge(600);
				response.addCookie(biscotto);
				
				
				response.sendRedirect("/hoppin/goodPassword.html");
				
				System.out.println("A Cookie has been created successfully");
			}else {
				save.setValue(1);
				response.sendRedirect("/hoppin/Authentication");
			}
		}else {
			save.setValue(1);
			response.sendRedirect("/hoppin/Authentication");
		}
	
	}

}
