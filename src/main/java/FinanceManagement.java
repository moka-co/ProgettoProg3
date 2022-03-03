

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import hoppin.CacheSingleton;
import hoppin.PriceList;
import hoppin.Season;

import hoppin.sql.MySQLFinance;

import hoppin.factory.AbstractFactory;
import hoppin.factory.FinanceFactory;

public class FinanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FinanceManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CacheSingleton cache = CacheSingleton.getInstance();
		cache.setRoomTypeId("");
		
		AbstractFactory factory = new FinanceFactory();
		int id = ((FinanceFactory) factory).makeCookieGetter(request).getIdbyCookies();
		
		
		MySQLFinance db = new MySQLFinance();
		ArrayList<Integer> params = db.getTotProfit(id); //params = parametri
		ArrayList<PriceList> pricelist = db.getPriceList(id);
		ArrayList<Season> seasonlist = db.getSeason(id);
		db.disconnect();
		
		HttpSession session=request.getSession();
		session.setAttribute("sumAllPrice", params.get(0));
		session.setAttribute("allReservation", params.get(1));
		session.setAttribute("pricelist", pricelist);
		session.setAttribute("seasonlist", seasonlist);
		
		db.disconnect();
		response.sendRedirect("/hoppin/FinanceManagement.jsp");
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLFinance db = new MySQLFinance();
		CacheSingleton cache = CacheSingleton.getInstance();
		
		AbstractFactory factory = new FinanceFactory();
		int id = ((FinanceFactory) factory).makeCookieGetter(request).getIdbyCookies();
		
		
		String arti= request.getParameter("AddRoomTypeId"); //arti = Add Room Type Id
		if ( arti != null) {
			cache.setRoomTypeId(arti);
		}
		
		String rrti= request.getParameter("RemoveRoomTypeId"); //arti = Add Room Type Id
		if ( rrti != null) {
			cache.setRoomTypeId("");
		}
		
		String npti = request.getParameter("NewPriceToIns");
		if ( npti != null && ! cache.getRoomTypeId().equals("")) {
			db.editPriceList(cache.getRoomTypeId(), Integer.valueOf(npti), id);
		}
		
		String eto = request.getParameter("ConfirmAddEtoPriceList");
		if ( eto != null) {
			int price = Integer.valueOf(request.getParameter("price"));
			String roomType = request.getParameter("roomType");
			
			db.addElementToPriceList(roomType, price, id);
			doGet(request, response);
		}
		
		String dr = request.getParameter("DeleteRoom");
		if ( dr != null && ! cache.getRoomTypeId().equals("")) {
			String roomType = cache.getRoomTypeId();
			db.deleteElementToPriceList(roomType, id);
		}
		
		String caes = request.getParameter("ConfirmAddEtoSeason");
		String eaes = request.getParameter("ConfirmEditEtoSeason");
		if ( caes != null || eaes != null) {
			String season = request.getParameter("season");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			int incr = 0;
			incr = Integer.valueOf( request.getParameter("price") );
			
			if ( caes != null) {
				db.addEtoSeason(id, season, startDate, endDate, incr);
			}else {
				db.editEtoSeason(id, cache.getEtoSeason(), season, startDate, endDate, incr);
			}
			response.sendRedirect("/hoppin/FinanceManagement");
			
		}
		
		String etos = request.getParameter("EtoSeason");
		if ( etos != null ) {
			cache.setEtoSeason(etos);
		}
		
		String detos = request.getParameter("DeleteEtoSeason");
		if ( detos != null) {
			String etoSeason = cache.getEtoSeason();
			db.removeEtoSeason(id, etoSeason);
		}
		
		
		db.disconnect();
		
	}

}
