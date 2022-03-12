package hoppin.finance;

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

interface FinanceStrategy {
	void run() throws ServletException, IOException;
}


public class FinanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FinanceManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CacheSingleton cache = CacheSingleton.getInstance();
		cache.setRoomTypeId("");
		
		FinanceFactory factory = new FinanceFactory();
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		
		
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
		
		FinanceFactory factory = new FinanceFactory();
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		
		//Strategy initialization
		FinanceStrategy strategy = () -> {
			return;
		};
		
		List<String> words = Arrays.asList("AddRoomTypeId", "RemoveRoomTypeId", "NewPriceToIns", "ConfirmAddEtoPriceList",
				"DeleteRoom", "ConfirmAddEtoSeason", "ConfirmEditEtoSeason", "EtoSeason", "DeleteEtoSeason" );
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
        String param = request.getParameter(switched);

        switch( switched ) {

        case "AddRoomTypeId" : {
        	strategy = () -> cache.setRoomTypeId(param);
        	
        	break;
        }
        case "RemoveRoomTypeId" : {
        	strategy = () -> cache.setRoomTypeId("");
        	break;
        }
        
        case "NewPriceToIns" : {
        	strategy = () -> {
        		if ( cache.getRoomTypeId().equals("") == false ) {
					int newPriceToInsert = Integer.valueOf(param);
					String RoomTypeId = cache.getRoomTypeId();
					db.editPriceList(RoomTypeId, newPriceToInsert, id);
				}
        	};
        	
        	break;
        }
        
        
        case "ConfirmAddEtoPriceList" : {
        	
        	strategy = () -> {
        		int price = Integer.valueOf(request.getParameter("price"));
    			String roomType = request.getParameter("roomType");
    			
    			db.addElementToPriceList(roomType, price, id);
        	};
        	
        	
        	doGet(request, response);
        	break;
        	
        }
        
        case "DeleteRoom" :  {
        	strategy = () -> {
        		if ( cache.getRoomTypeId().equals("") == false ) { //C'è qualcosa nella cache
					String roomType = cache.getRoomTypeId();
					db.deleteElementToPriceList(roomType, id);
				}
        	};
        	
        }
        
        case "ConfirmAddEtoSeason" : {
        	
        	strategy = () -> {
				String season = request.getParameter("season");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				int incr = 0;
				incr = Integer.valueOf( request.getParameter("price") );
				
				db.addEtoSeason(id, season, startDate, endDate, incr);
        	};
        
        	break;
        }
        case "ConfirmEditEtoSeason" : {
        	
        	strategy = () -> {
				String season = request.getParameter("season");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				int incr = 0;
				incr = Integer.valueOf( request.getParameter("price") );
				
				db.editEtoSeason(id, cache.getEtoSeason(), season, startDate, endDate, incr);
				doGet(request,response);
        	};
        	
        	break;
        }
        
        case "EtoSeason" : {
        	strategy = () -> cache.setEtoSeason(param);
        	break;
        }
        
        case "DeleteEtoSeason" : {
        	strategy = () -> db.removeEtoSeason(id, cache.getEtoSeason());
        	break;
        }
        
        
        default: {
        	doGet(request,response);
        	break;
        }
        
        }
        
        
        strategy.run();
		
		
        /*
		String arti= request.getParameter("AddRoomTypeId"); //arti = Add Room Type Id
		if ( arti != null) {
			cache.setRoomTypeId(arti);
		}
		
		String rrti= request.getParameter("RemoveRoomTypeId"); //arti = Add Room Type Id
		if ( rrti != null) {
			cache.setRoomTypeId("");
		}
		
		String npti = request.getParameter("NewPriceToIns");
		if ( npti != null ) {
			
			FinanceStrategy strategy = () -> {
				if ( cache.getRoomTypeId().equals("") == false ) {
					int newPriceToInsert = Integer.valueOf(npti);
					String RoomTypeId = cache.getRoomTypeId();
					db.editPriceList(RoomTypeId, newPriceToInsert, id);
				}
			};
			
			strategy.run();
			
		}
		
		String eto = request.getParameter("ConfirmAddEtoPriceList");
		if ( eto != null) {
			int price = Integer.valueOf(request.getParameter("price"));
			String roomType = request.getParameter("roomType");
			
			db.addElementToPriceList(roomType, price, id);
			doGet(request, response);
		}
		
		String dr = request.getParameter("DeleteRoom");
		if ( dr != null ) {
			
			FinanceStrategy strategy = () -> {
				if ( cache.getRoomTypeId().equals("") == false ) { //C'è qualcosa nella cache
					String roomType = cache.getRoomTypeId();
					db.deleteElementToPriceList(roomType, id);
				}
			};
			
			strategy.run();
			
		}
		
		String caes = request.getParameter("ConfirmAddEtoSeason");
		String eaes = request.getParameter("ConfirmEditEtoSeason");
		if ( caes != null || eaes != null) {
			
			FinanceStrategy strategy = () -> {
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
				
				
			};
			strategy.run();
			
			response.sendRedirect("/hoppin/FinanceManagement");
			
		}
		
		String etos = request.getParameter("EtoSeason");
		if ( etos != null ) {
			cache.setEtoSeason(etos);
		}
		
		String detos = request.getParameter("DeleteEtoSeason");
		if ( detos != null) {
			db.removeEtoSeason(id, cache.getEtoSeason());
		}
		
		*/
		db.disconnect();
		
	}

}
