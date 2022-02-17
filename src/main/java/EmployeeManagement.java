

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hoppin.MySQLConnect;

/**
 * Servlet implementation class EmployeeManagement
 */
@WebServlet("/EmployeeManagement")
public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeManagement() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println(request.getParameter("AddEmployee"));
		int i = 0;
		List<String> elist = new ArrayList<String>();
		MySQLConnect db = new MySQLConnect();
		Cookie [] cookies = request.getCookies(); 

		//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication

		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				i = Integer.valueOf(value);
			}
		}

		elist = db.getEmployeeList(i);
		
		HttpSession session=request.getSession();
		session.setAttribute("elist",elist);
		session.setAttribute("show", "questo");
		//this.getServletContext().getRequestDispatcher("/hoppin/EmployeeManagement.jsp").forward(request, response);
		response.sendRedirect("/hoppin/EmployeeManagement.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int i=0;
		HttpSession session=request.getSession();
		Cookie [] cookies = request.getCookies();
		if ( cookies == null) {
			return;
		}
		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				i = Integer.valueOf(value);
			}
		}
		
		if ( request.getParameter("AddEmployee") != null) {
			List<String> elist = new ArrayList<String>();
			MySQLConnect db = new MySQLConnect();

			//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication

			elist = db.getEmployeeList(i);
			
			
			session.setAttribute("elist",elist);
			session.setAttribute("show","quello");
			response.sendRedirect("/hoppin/EmployeeManagement.jsp");
		}
		
		
		if ( request.getParameter("ConfirmAddEmployee") != null) {
			System.out.println(request.getParameter("ConfirmAddEmployee"));
			String user = request.getParameter("email");
			String passw = request.getParameter("password");
			String nome = request.getParameter("nome");
			
			
			if ( user != null && passw != null && nome != null) {
				MySQLConnect db = new MySQLConnect();
				boolean res = db.addEmployee(nome, user, passw, i);
				
				if ( res == true) {
					session.setAttribute("ResultAddEmployee","ok");
				}else {
					session.setAttribute("ResultAddEmployee","no");
				}
			}
			
			//response.sendRedirect("/hoppin/EmployeeManagement.jsp");
			doGet(request, response);
		}
		//doGet(request, response);
	}

}
