package hoppin.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hoppin.util.CacheSingleton;


interface EmployeeStrategy {
	void run();
}

public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeManagement() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
		HttpSession session=request.getSession();
        
        
		MySQLEmployee db = new MySQLEmployee();

		//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication
		EmployeeFactory factory = new EmployeeFactory();
		int i = factory.makeCookieGetter(request).getIdbyCookies();
		
		//Va ripensato anche la responsabilita' di db.getEmployeeList, 
		//Infatti secondo me non dovrebbe essere lei a costruire ArrayList<Employee>
		ArrayList<Employee> elist = db.getEmployeeList(i);
		db.disconnect();
		
		session.setAttribute("elist",elist);

		response.sendRedirect("/hoppin/EmployeeManagement.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        EmployeeFactory factory = new EmployeeFactory();
		int i = factory.makeCookieGetter(request).getIdbyCookies();
		
		MySQLEmployee db = new MySQLEmployee();
		
		CacheSingleton cache = CacheSingleton.getInstance();
		
		EmployeeStrategy strategy = () -> { };
		
		
		List<String> words = Arrays.asList("DeleteEmployee", "RemoveAccId", "AddAccId", "ConfirmAddEmployee" );
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
        String param = request.getParameter(switched);
        
		switch(switched) {
		case "DeleteEmployee" : {
			strategy = () -> {
				db.deleteEmployee(cache.getList());
			};
		
			
			break;
		}
								
		case "RemoveAccId" : {
			strategy = () -> {
				cache.removeValue( Integer.valueOf( param )  );			
			};
		
			
			break;
		}
								
		case "AddAccId": {
			strategy = () -> {
				int value = 0;
				try{
					value = Integer.valueOf( param );
					
				} catch ( NumberFormatException e) {
					System.out.println( param );
				}
				
				cache.setValue( value );
			};
				
			
			break;
		}
								
		case "ConfirmAddEmployee":{
			strategy = () -> {
				String user = request.getParameter("email");
				String passw = request.getParameter("password");
				String nome = request.getParameter("nome");

				db.addEmployee(nome, user, passw, i);
			
			};
		
			
		
			response.sendRedirect("/hoppin/EmployeeManagement");
			break;
			
		}
		
		default: {
			response.sendRedirect("/hoppin/EmployeeManagement");
			break;
		}
				
		}
		
		strategy.run();
		

		/*
		if ( request.getParameter("DeleteEmployee") != null) {
			
			EmployeeStrategy strategy = () -> {
				db.deleteEmployee(cache.getList());
				
			};
			
			strategy.run();		
		}
		
		String rai= request.getParameter("RemoveAccId");
		if ( rai != null) {
			
			EmployeeStrategy strategy = () -> {
				cache.removeValue( Integer.valueOf(rai)  );			
			};
			
			strategy.run();
			
		}
		
		String aai= request.getParameter("AddAccId");
		if ( aai != null) {
			
			EmployeeStrategy strategy = () -> {
				cache.setValue( Integer.valueOf(aai) );
			};
			
			strategy.run();
			
		}
		
		if ( request.getParameter("ConfirmAddEmployee") != null) {
			
			EmployeeStrategy strategy = () -> {

				String user = request.getParameter("email");
				String passw = request.getParameter("password");
				String nome = request.getParameter("nome");

				
				db.addEmployee(nome, user, passw, i);
				
			};
			
			strategy.run();
			
			response.sendRedirect("/hoppin/EmployeeManagement");
			
		}
		
		*/
		
		db.disconnect();
	}

}
