

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

/**
 * Servlet implementation class DebugCookies
 */
@WebServlet("/DebugCookies")
public class DebugCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DebugCookies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ServletOutputStream out = response.getOutputStream();
		Cookie [] cookies = request.getCookies();
		if ( cookies != null) {
			for (Cookie aCookie : cookies) {
				String name = aCookie.getName();
				String domain = aCookie.getDomain();
				String value = aCookie.getValue();
				int MaxAge = aCookie.getMaxAge();
				String comment = aCookie.getComment();
			
				out.println("Name: " + name + " domain: " + domain + " value: " + value + " max age: " + MaxAge + " comment " + comment);
			}
		}else {
			out.println("Cookies are null!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
