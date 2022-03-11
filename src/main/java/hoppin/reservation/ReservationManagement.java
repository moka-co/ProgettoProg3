package hoppin.reservation;

import java.io.IOException;
import java.util.ArrayList;

import hoppin.util.CacheSingleton;

import hoppin.util.factory.AbstractFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


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
		CacheSingleton cache = CacheSingleton.getInstance();
		
		if ( request.getParameter("AddValue") != null) { //Si attiva quando si clicca su un elemento della tabella
			int value = Integer.valueOf(request.getParameter("AddValue"));
			cache.setSingleValue(value);
			
		}
		
		if ( request.getParameter("ConfirmAddReservation") != null) { //Si attiva quando si preme il bottone che conferma l'inserimento dati
			ReservationBuilder rb = new ReservationBuilder(request);
			Reservation res = rb.getReservation();
			
			/*
			String name = request.getParameter("name");
			String room = request.getParameter("room");
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String pckg = request.getParameter("package");
			if (pckg == null) {
				pckg = "Base";
			}
			
			if ( name == null || room == null || checkin == null || checkout == null) {
				System.out.println("Error");
				doGet(request,response);
				return;
			} CONTROLLARE SE HO MESSO PURE LE PARTI RIGA DA 74 a 77 */ 
			
			AbstractFactory factory = new ReservationFactory();
		    int id = ((ReservationFactory) factory).makeCookieGetter(request).getIdbyCookies();
			MySQLReservation db = new MySQLReservation();
			boolean result = db.addReservation(id, res);
			db.disconnect();
				
			if ( result == true) {
				session.setAttribute("ResultAddEmployee","ok");
					
			}else {
				session.setAttribute("ResultAddEmployee","no");
			}
			

			//response.sendRedirect("/hoppin/EmployeeManagement.jsp");
			doGet(request, response);
		}
		
		if ( request.getParameter("DeleteReservation") != null) {
			MySQLReservation db = new MySQLReservation();
			db.deleteReservation(cache.getSingleValue());
			db.disconnect();
			cache.setSingleValue(-1);
		}
		
		if ( request.getParameter("ConfirmEditReservation") != null ) {
			
			MySQLReservation db = new MySQLReservation();
			
			String name = request.getParameter("name");
			String room = request.getParameter("room");
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String pckg = request.getParameter("package");
			Reservation newReservation = new Reservation(name, cache.getSingleValue(), room, checkin, checkout, pckg);
			db.editReservation(newReservation);
			db.disconnect();
			
			doGet(request,response);
		}
	}

}
