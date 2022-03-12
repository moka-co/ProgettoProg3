package hoppin.reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hoppin.util.CacheSingleton;

import hoppin.util.factory.AbstractFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

interface ReservationStrategy {
	void run() throws ServletException, IOException;
}


public class ReservationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        CacheSingleton cache = CacheSingleton.getInstance();
		cache.setSingleValue(-1);
		
        ArrayList<Reservation> rlist = null;
        
        AbstractFactory factory = new ReservationFactory();
        int id = ((ReservationFactory) factory).makeCookieGetter(request).getIdbyCookies();
		
		//________________
		
		MySQLReservation db = new MySQLReservation();
		
		rlist = db.getReservationList(id);
		
		db.disconnect();
		HttpSession session=request.getSession();
		session.setAttribute("rlist",rlist);
		response.sendRedirect("/hoppin/ReservationManagement.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session=request.getSession();
		MySQLReservation db = new MySQLReservation();
		ReservationFactory factory = new ReservationFactory();
		CacheSingleton cache = CacheSingleton.getInstance();
		
		ReservationStrategy strategy = () -> { };
		
		List<String> words = Arrays.asList("AddValue", "ConfirmAddReservation", "DeleteReservation", "ConfirmEditReservation");
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		String param = request.getParameter(switched);
		
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
			    int id = factory.makeCookieGetter(request).getIdbyCookies();
			    Reservation res = factory.makeReservation(request);
				boolean result = db.addReservation(id, res);
					
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
			doGet(request,response);
			break;
		}
		
		default : {
			doGet(request, response);
			break;
		}
		}
		
		strategy.run();
		db.disconnect();
		
		/*
		
		if ( request.getParameter("AddValue") != null) { //Si attiva quando si clicca su un elemento della tabella
			ReservationStrategy strategy = () -> {
				int value = Integer.valueOf(request.getParameter("AddValue"));
				cache.setSingleValue(value);
			};
			
			strategy.run();
			
		}
		
		if ( request.getParameter("ConfirmAddReservation") != null) { //Si attiva quando si preme il bottone che conferma l'inserimento dati
			
			ReservationStrategy strategy = () -> {
				ReservationFactory factory = new ReservationFactory();
			    int id = factory.makeCookieGetter(request).getIdbyCookies();
			    Reservation res = factory.makeReservation(request);
				MySQLReservation db = new MySQLReservation();
				boolean result = db.addReservation(id, res);
				db.disconnect();
					
				if ( result == true) {
					session.setAttribute("ResultAddEmployee","ok");
						
				}else {
					session.setAttribute("ResultAddEmployee","no");
				}
			};
			
			strategy.run();
			
			doGet(request, response);
		}
		
		if ( request.getParameter("DeleteReservation") != null) {
			
			ReservationStrategy strategy = () -> {
				MySQLReservation db = new MySQLReservation();
				db.deleteReservation(cache.getSingleValue());
				db.disconnect();
				cache.setSingleValue(-1);
			};
			
			strategy.run();
			
		}
		
		if ( request.getParameter("ConfirmEditReservation") != null ) {
			
			ReservationStrategy strategy = () -> {
				MySQLReservation db = new MySQLReservation();
				
				int value = cache.getSingleValue();
				if ( value > 0) {
					ReservationFactory factory = new ReservationFactory();
					Reservation newReservation = factory.makeReservation(request, value);
					db.editReservation(newReservation);
					
				}
				
				db.disconnect();
			};
			
			strategy.run();
			
			
			doGet(request,response);
		}
		
		*/
	}

}
