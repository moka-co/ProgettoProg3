

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hoppin.CacheSingleton;
import hoppin.Employee;
import hoppin.MySQLConnect;

/**
 * Servlet implementation class EmployeeManagement
 */
@WebServlet("/EmployeeManagement")
public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int sem = 0; //Variabile temporanea che va sostituita con una soluzione migliore
	//In pratica, serve per non far visualizzare la scritta in rosso una volta che ricarichi la pagina
	//Dopo aver inserito un nuovo utente.
       
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
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
		HttpSession session=request.getSession();
        sem++;
        if ( sem % 2 == 0 ) {
        	session.setAttribute("ResultAddEmployee","x");
        	
        }
        
        
		int i = 0;
		ArrayList<Employee> elist = null;
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
		
		session.setAttribute("elist",elist);
		session.setAttribute("show", "questo");
		//this.getServletContext().getRequestDispatcher("/hoppin/EmployeeManagement.jsp").forward(request, response);
		db.disconnect();
		response.sendRedirect("/hoppin/EmployeeManagement.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
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
		
		if ( request.getParameter("DeleteEmployee") != null) {
			MySQLConnect db = new MySQLConnect();
			System.out.println("Ho ricevuto il messaggio!");
			CacheSingleton cache = CacheSingleton.getInstance();
			List<Integer> li = cache.getList();
			if ( li != null)
				db.deleteEmployee(li);
			else
				System.out.println("li è null!");
			db.disconnect();
		}
		
		String rai= request.getParameter("RemoveAccId");
		if ( rai != null) {
			int x = Integer.valueOf(rai);

			CacheSingleton cache = CacheSingleton.getInstance();

			if ( x!= 0)
				cache.removeValue(x);
			
			List<Integer> li = cache.getList();
			System.out.println("Elementi rimanenti nella lista: ");
			for (int k=0; k<li.size(); k++) {
				System.out.print(" " + li.get(k) + " ");
			}
			System.out.println("");
		}
		
		String aai= request.getParameter("AddAccId");
		if ( aai != null) {
			int x = Integer.valueOf(aai);

			CacheSingleton cache = CacheSingleton.getInstance();
			if ( x != 0)
				cache.setValue(x);
			
			List<Integer> li = cache.getList();
			System.out.println("Elementi nella lista: ");
			for (int k=0; k<li.size(); k++) {
				System.out.print(" " + li.get(k)+  " ");
			}
			System.out.println("");

		}
		
		if ( request.getParameter("AddEmployee") != null) {
			ArrayList<Employee> elist = null;
			MySQLConnect db = new MySQLConnect();


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
