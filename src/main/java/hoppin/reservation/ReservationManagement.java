package hoppin.reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Interfaccia funzionale che implementa delle strategie usate nel metodo {@link ReservationManagement#doPost}
 */
interface ReservationStrategy {
	void run() throws ServletException, IOException;
}

/**
 * 
 * Gestisce le richeste HTTP GET e POST ed effettua delle operazioni legate
 * al sottosistema ReservationManagement, in base al tipo di richiesta e ai suoi attributi
 * Usa {@link MySQLReservation}, {@link ReservationFactory}, {@link ReservationCache} 
 * e la Java Server Page <mono>PackagesManagement.jsp</mono>
 * 
 */
public class ReservationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        ReservationCache cache = ReservationCache.getInstance();
		cache.setSingleValue(-1);
		
        ArrayList<Reservation> rlist = null;
        
        ReservationFactory factory = new ReservationFactory();
		
		MySQLReservation db = (MySQLReservation) factory.makeDatabaseConnect(request);
		
		rlist = db.getReservationList();
		
		db.disconnect();
		HttpSession session=request.getSession();
		session.setAttribute("rlist",rlist);
		response.sendRedirect("/hoppin/ReservationManagement.jsp");
	}

	/**
	 * 
	 * Riceve degli attributi da @param request e in base a questi
	 * implementa in modo dinamico  {@link EmployeeStrategy#run()} e lo esegue.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session=request.getSession();

		ReservationFactory factory = new ReservationFactory();
		MySQLReservation db = (MySQLReservation) factory.makeDatabaseConnect(request);
		ReservationCache cache = ReservationCache.getInstance();
		
		ReservationStrategy strategy = () -> { }; //Inizializzazione della funzione lambda strategy
		
		//lista di keywords valide che corrispondono ad un azione da effettuare:
		List<String> words = Arrays.asList("AddValue", "ConfirmAddReservation", "DeleteReservation", "ConfirmEditReservation");
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
		String param = request.getParameter(switched);
		
		 /**
         * switched è una delle keywords valide che sono passate in input, oppure è una stringa vuota e 
         * viene usata nel costrutto switch.
         * param è il parametro della richiesta HTTP associato alla keyword passata.
         * 
         */
		switch ( switched ) {
		
		case "AddValue" : {
			strategy = () -> {
				int value = Integer.valueOf(param);
				cache.setSingleValue(value);
			};
			
			break;
		}
		
		case "ConfirmAddReservation" : {
			strategy = () -> {
			    Reservation res = factory.makeReservation(request);
				boolean result = db.addReservation(res);
					
				if ( result == true) {
					session.setAttribute("ResultAddEmployee","ok");
						
				}else {
					session.setAttribute("ResultAddEmployee","no");
				}
			};
			
			doGet(request,response);
			break;
		}
		
		case "DeleteReservation" : {
			strategy = () -> {
				db.deleteReservation(cache.getSingleValue());
				cache.setSingleValue(-1);
			};
			break;
		}
		
		case "ConfirmEditReservation" : {
			strategy = () -> {
				int value = cache.getSingleValue();
				if ( value > 0) {
					Reservation newReservation = factory.makeReservation(request, value);
					db.editReservation(newReservation);
				}
				
			};
			response.sendRedirect("/hoppin/ReservationManagement");
			break;
		}
		
		default : {
			doGet(request, response);
			break;
		}
		
		} //end switch
 		
		strategy.run();
		db.disconnect();
		
	} //end doPost

} 
