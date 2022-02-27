

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hoppin.AuthenticationGUI;
import hoppin.MySQLConnect;
import hoppin.ServeSingleton;


@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletOutputStream out;
	AuthenticationGUI gui;


    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		out = response.getOutputStream();
		gui = new AuthenticationGUI(out);
		gui.register();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		MySQLConnect db = new MySQLConnect();
		String name = request.getParameter("name");
		String user = request.getParameter("email");
		String passw = request.getParameter("password");
		if ( name == null || user == null || passw == null) {
			return;
		}
		
		db.register(name, user, passw);
		ServeSingleton save = ServeSingleton.getInstance();
		save.setValue(2);
		response.sendRedirect("/hoppin/Authentication");
		
	}

}
