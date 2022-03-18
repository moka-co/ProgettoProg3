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


interface FinanceStrategy {
	void run() throws ServletException, IOException;
}


public class FinanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FinanceManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FinanceCache cache = FinanceCache.getInstance();
		cache.setRoomTypeId("");
		
		FinanceFactory factory = new FinanceFactory();
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		
		
		MySQLFinance db = new MySQLFinance(id);
		ArrayList<Integer> params = db.getTotProfit(); //params = parametri
		ArrayList<PriceList> pricelist = db.getPriceList();
		ArrayList<Season> seasonlist = db.getSeason();
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
		
		
		FinanceCache cache = FinanceCache.getInstance();
		
		FinanceFactory factory = new FinanceFactory();
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		MySQLFinance db = new MySQLFinance(id);
		
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
					db.editPriceList(RoomTypeId, newPriceToInsert);
				}
        	};
        	
        	break;
        }
        
        
        case "ConfirmAddEtoPriceList" : {
        	
        	strategy = () -> {
        		int price = Integer.valueOf(request.getParameter("price"));
    			String roomType = request.getParameter("roomType");
    			
    			db.addElementToPriceList(roomType, price);
        	};
        	
        	
        	response.sendRedirect("/hoppin/FinanceManagement");
        	break;
        	
        }
        
        case "DeleteRoom" :  {
        	System.out.println("elimina");
        	strategy = () -> {
        		if ( cache.getRoomTypeId().equals("") == false ) { //C'è qualcosa nella cache
					String roomType = cache.getRoomTypeId();
					db.deleteElementToPriceList(roomType);
				}
        	};
        	break;
        	
        }
        
        case "ConfirmAddEtoSeason" : {
        	
        	strategy = () -> {
				String season = request.getParameter("season");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				int incr = 0;
				incr = Integer.valueOf( request.getParameter("price") );
				
				db.addEtoSeason(season, startDate, endDate, incr);
        	};
        	response.sendRedirect("/hoppin/FinanceManagement");
        	break;
        }
        case "ConfirmEditEtoSeason" : {
        	
        	strategy = () -> {
				String season = request.getParameter("season");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				int incr = 0;
				incr = Integer.valueOf( request.getParameter("price") );
				
				db.editEtoSeason(cache.getEtoSeason(), season, startDate, endDate, incr);
				doGet(request,response);
        	};
        	
        	break;
        }
        
        case "EtoSeason" : {
        	strategy = () -> cache.setEtoSeason(param);
        	break;
        }
        
        case "DeleteEtoSeason" : {
        	strategy = () -> db.removeEtoSeason( cache.getEtoSeason());
        	break;
        }
        
        
        default: {
        	doGet(request,response);
        	break;
        }
        
        }
        
        
        strategy.run();
		db.disconnect();
		
	}

}
