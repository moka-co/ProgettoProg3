package hoppin.hotelinfo;
import hoppin.util.factory.CookieFactory;
import jakarta.servlet.http.HttpServletRequest;

public class HotelInfoFactory implements CookieFactory {
	public HotelInfoFactory() {
		super();
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
