package hoppin.packages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Interfaccia funzionale che implementa delle strategie usate nel metodo {@link hoppin.employe.PackagesManagement#doPost}
 */
interface PackagesStrategy {
	void run()  throws ServletException, IOException;
}

/**
 * 
 * Gestisce le richeste HTTP GET e POST ed effettua delle operazioni legate
 * al sottosistema PackagesManagement, in base al tipo di richiesta e ai suoi attributi
 * Usa {@link MySQLPackages}, {@link PackagesFactory}, {@link PackagesCache} 
 * e la Java Server Page <mono>PackagesManagement.jsp</mono>
 * 
 */
public class PackagesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PackagesManagement() {
        super();
    }

    /**
     * Imposta la lista di pacchetti di un Hotel
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PackagesCache.getInstance().setSelectedPackage(""); //imposta pacchetto selezionato a null
		//Serve sopratutto in caso un utente seleziona un pacchetto, poi ricarica la pagina e clicca su elimina pacchetto
		
		PackagesFactory factory = new PackagesFactory();
		MySQLPackages db = (MySQLPackages) factory.makeDatabaseConnect(request);
	
		HttpSession session = request.getSession();
		
		ArrayList<Package> packageList = db.getPackageList();
		
		session.setAttribute("PackageList", packageList);
		
		db.disconnect();
		
		response.sendRedirect("/hoppin/PackagesManagement.jsp");
		
	}

	/**
	 * @param request 
	 * @param response
	 * 
	 * Riceve degli attributi da @param request e in base a questi
	 * implementa in modo dinamico  {@link hoppin.employee.EmployeeStrategy#run()} e lo esegue.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PackagesCache cache = PackagesCache.getInstance();
		PackagesFactory factory = new PackagesFactory();
		
		MySQLPackages db = (MySQLPackages) factory.makeDatabaseConnect(request);
		
		PackagesStrategy strategy = () -> { }; //Inizializzazione della funzione lambda strategy
		
		//lista di keywords valide che corrispondono ad un azione da effettuare:
		List<String> words = Arrays.asList("ConfirmAddPackage", "SetValue", "DeletePackage", "ConfirmEditPackage");
		
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
		
		switch (switched) {
		
		case "ConfirmAddPackage" : {
			
			strategy = () -> {
				String Npack = request.getParameter("NPack");
				String DPack = request.getParameter("DPack");
				int PPack = Integer.valueOf(request.getParameter("PPack"));
				
				db.addPackage(Npack, DPack, PPack);
				
			};
			
			response.sendRedirect("/hoppin/PackagesManagement");
			break;
		}
		
		//Si attiva quando si clicca su un elemento della tabella
		case "SetValue" : {
			strategy = () -> cache.setSelectedPackage(param);
			break;
		}
		
		case "DeletePackage" : {
			strategy = () -> {	
				//Se c'è un pacchetto nella cache
				if ( cache.getSelectedPackage().equals("") == false ) {
					db.deletePackage(cache.getSelectedPackage());
				}
				cache.setSelectedPackage("");
				
			};
			
			break;
		}
		
		case "ConfirmEditPackage" : {
			strategy = () -> {
				String selpac = cache.getSelectedPackage();
				if ( selpac.equals("") == false ) {
					
					Package newPac= factory.makePackage(request);
					db.editPackage(newPac , cache.getSelectedPackage() );
				}
			};
			
			response.sendRedirect("/hoppin/PackagesManagement");
			break;
		}
		
		default: {
			doGet(request, response);
			break;
		}
		
		} //end switch
		
		strategy.run();
		db.disconnect();
		
	}	

}