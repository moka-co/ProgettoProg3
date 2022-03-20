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


/**
 * Interfaccia funzionale che implementa delle strategie usate nel metodo {@link hoppin.employe.EmployeeManagement#doPost}
 */
interface EmployeeStrategy {
	void run();
}


/**
 * 
 * 
 * Gestisce le richeste HTTP GET e POST ed effettua delle operazioni legate
 * al sottosistema Employee, in base al tipo di richiesta e ai suoi attributi
 * Usa {@link hoppin.employee.MySQLEmployee}, {@link hoppin.employee.EmployeeFactory}, {@link hoppin.employee.EmployeeCache} 
 * e la Java Server Page <mono>EmployeeManagement.jsp</mono>
 * 
 */
@WebServlet("/EmployeeManagement")
public class EmployeeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeManagement() {
        super();
        
    }
    
    /**
     * Imposta come attributo della risposta HTTP, la lista di impiegati presente nel database per l'Hotel in cui lavora l'utente
     * e redireziona alla pagina <mono>EmployeeManagement.jsp</mono>
     */

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

	
	/**
	 * @param request 
	 * @param response
	 * 
	 * Riceve degli attributi da @param request e in base a questi
	 * implementa in modo dinamico  {@link hoppin.employee.EmployeeStrategy#run()} e lo esegue.
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        EmployeeFactory factory = new EmployeeFactory();
		
		MySQLEmployee db = (MySQLEmployee) factory.makeDatabaseConnect(request);
		
		EmployeeCache cache = EmployeeCache.getInstance();
		
		EmployeeStrategy strategy = () -> { }; //Inizializzazione della funzione lambda strategy
		
		//lista di keywords valide che corrispondono ad un azione da effettuare
		List<String> words = Arrays.asList("DeleteEmployee", "RemoveAccId", "AddAccId", "ConfirmAddEmployee" );
		
		String switched = "";
		for (String str : words) { 
			if ( request.getParameter(str) != null) {
				switched = str;
			} 
		}
		
        String param = request.getParameter(switched);
        
        /**
         * Abbiamo due variabili: param e switched.
         * switched è una delle keywords valide che sono passate in input, oppure è una stringa vuota.
         * 
         * switched viene utilizzato nel costrutto 
         * @{code switch(switched){
         * case "DeleteEmployee" : { //... };
         * }
         *  // ...
         * } 
         * 
         * param è il parametro della richiesta HTTP associato alla keyword passata.
         * 
         * 
         * Ad esempio, se la parola è "AddAccId", param potrà essere 1 o 2 o 3 o ... N e verrà utilizzato
         * dalla strategia implementata in modo dinamico per aggiungerlo alla cache
         */
        
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
