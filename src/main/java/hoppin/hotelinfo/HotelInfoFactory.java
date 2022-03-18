package hoppin.hotelinfo;
import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

public class HotelInfoFactory implements CookieFactory {
	public HotelInfoFactory() {
		super();
	}
	
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLHotelInfo(i);
		
		return db;
		
	}

	public HotelInfo makeHotelInfo(HttpServletRequest request) {
		HotelInfoBuilder hib = new HotelInfoBuilder(request);
		int id = makeCookieGetter(request).getIdbyCookies();
		MySQLHotelInfo db = new MySQLHotelInfo();
		hib.name( db.getHotelNameById(id)  );
		db.disconnect();
		
		return hib.toHotelInfo();
	}
}
