package hoppin.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


interface EmployeeStrategy {
	void run();
}

@WebServlet("/EmployeeManagement")
public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeManagement() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
		HttpSession session=request.getSession();

		EmployeeFactory factory = new EmployeeFactory();
		
		MySQLEmployee db = (MySQLEmployee) factory.makeDatabaseConnect(request);
		ArrayList<Employee> elist = db.getEmployeeList();
		db.disconnect();
		
		session.setAttribute("elist",elist);

		response.sendRedirect("/hoppin/EmployeeManagement.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        EmployeeFactory factory = new EmployeeFactory();
		
		MySQLEmployee db = (MySQLEmployee) factory.makeDatabaseConnect(request);
		
		EmployeeCache cache = EmployeeCache.getInstance();
		
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

				db.addEmployee(nome, user, passw);
			
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
		db.disconnect();
	}

}
