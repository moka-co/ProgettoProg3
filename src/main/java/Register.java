

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hoppin.sql.MySQLAuth;

//Classe da rifare

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletOutputStream out;


    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		out = response.getOutputStream();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		MySQLAuth db = new MySQLAuth();
		String name = request.getParameter("name");
		String user = request.getParameter("email");
		String passw = request.getParameter("password");
		if ( name == null || user == null || passw == null) {
			return;
		}
		
		db.register(name, user, passw);
		response.sendRedirect("/hoppin/Authentication");
		
	}

}
