package hoppin.employee;

import jakarta.servlet.ServletException;
import hoppin.util.factory.AbstractFactory;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hoppin.util.CacheSingleton;


public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int sem = 0; //Variabile temporanea che va sostituita con una soluzione migliore
	//In pratica, serve per non far visualizzare la scritta in rosso una volta che ricarichi la pagina
	//Dopo aver inserito un nuovo utente.
       
    public EmployeeManagement() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println(request.getParameter("AddEmployee"));
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        request.getSession().removeAttribute("ResultAddEmployee");
        
		HttpSession session=request.getSession();
        sem++;
        if ( sem % 2 == 0 ) {
        	session.setAttribute("ResultAddEmployee","x");
        	
        }
        
		MySQLEmployee db = new MySQLEmployee();

		//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication
		AbstractFactory factory = new EmployeeFactory();
		int i = ((EmployeeFactory) factory).makeCookieGetter(request).getIdbyCookies();
		
		//Va ripensato anche la responsabilita' di db.getEmployeeList, 
		//Infatti secondo me non dovrebbe essere lei a costruire ArrayList<Employee>
		ArrayList<Employee> elist = db.getEmployeeList(i);
		db.disconnect();
		
		session.setAttribute("elist",elist);
		session.setAttribute("show", "questo");

		response.sendRedirect("/hoppin/EmployeeManagement.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        CacheSingleton cache = CacheSingleton.getInstance();
        
        AbstractFactory factory = new EmployeeFactory();
		int i = ((EmployeeFactory) factory).makeCookieGetter(request)
											.getIdbyCookies();
		
		if ( request.getParameter("DeleteEmployee") != null) {
			MySQLEmployee db = new MySQLEmployee();
			cache = CacheSingleton.getInstance();
			List<Integer> li = cache.getList();
			if ( li != null)
				db.deleteEmployee(li);
			else
				System.out.println("li e' null!");
			db.disconnect();
		}
		
		String rai= request.getParameter("RemoveAccId");
		if ( rai != null) {
			int x = Integer.valueOf(rai);

			cache = CacheSingleton.getInstance();

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

			cache = CacheSingleton.getInstance();
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
			MySQLEmployee db = new MySQLEmployee();
			ArrayList<Employee> elist = db.getEmployeeList(i);
			
			
			session.setAttribute("elist",elist);
			session.setAttribute("show","quello");
			response.sendRedirect("/hoppin/EmployeeManagement.jsp");
		}
		
		
		if ( request.getParameter("ConfirmAddEmployee") != null) {
			String user = request.getParameter("email");
			String passw = request.getParameter("password");
			String nome = request.getParameter("nome");
			
			
			if ( user != null && passw != null && nome != null) {
				MySQLEmployee db = new MySQLEmployee();
				boolean res = db.addEmployee(nome, user, passw, i);
				
				if ( res == true) {
					session.setAttribute("ResultAddEmployee","ok");
					
				}else {
					session.setAttribute("ResultAddEmployee","no");
				}
				response.sendRedirect("/hoppin/EmployeeManagement");
			}
			
		}
	}

}
