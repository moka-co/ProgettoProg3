package hoppin.hotelinfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Costruisce la classe {@link hoppin.hotelinfo.HotelInfo}
 *
 *	@see {@link hoppin.hotelinfo.HotelInfo} 
 * 	@see {@link hoppin.hotelinfo.HotelInfoFactory}
 */
public class HotelInfoBuilder {
	HotelInfo hotel = new HotelInfo();
	
	/**
	 * Imposta dei parametri di HotelInfo
	 * @param rs è il	 risultato di una query SQL
	 * @throws SQLException
	 */
	public HotelInfoBuilder(ResultSet rs) throws SQLException {
		this.name(rs.getString("Name"));
		this.via(rs.getString("Via"));
		this.city(rs.getString("City"));
		this.postcode(rs.getString("Postcode"));
		this.stars(rs.getInt("Stars"));
		this.description(rs.getString("description"));
	}
	
	/**
	 * Imposta dei parametri di HotelInfo
	 * @param request una richiesta HTTP che contiene parametri i cui valori servono per costruire la classe HotelInfo
	 */
	public HotelInfoBuilder(HttpServletRequest request) {
		this.via(request.getParameter("via"));
		this.city(request.getParameter("city"));
		this.postcode(request.getParameter("postcode"));
		this.description(request.getParameter("description"));
		
		
		if ( request.getParameter("stars") != null) {
			this.stars( Integer.valueOf(request.getParameter("stars")) );
		}else {
			this.stars(0);
		}
	}
	
	/**
	 * 
	 * @return un istanza della classe HotelInfo con i parametri impostati dal costruttore
	 */
	public HotelInfo toHotelInfo() {
		return hotel;
	}
	
	/**
	 * Imposta il nome dell'Hotel
	 * @param name
	 */
	public void name(String name) {
		hotel.setName(name);
		
	}
	/**
	 * Imposta la via dell'Hotel
	 * @param via
	 */
	public void via(String via) {
		hotel.setVia(via);
	}
	
	/**
	 * Imposta la città dell'Hotel
	 * @param city
	 */
	public void city(String city) {
		hotel.setCity(city);
	}
	
	/**
	 * Imposta il codice postale dell'Hotel
	 * @param postcode
	 */
	public void postcode(String postcode) {
		hotel.setPostcode(postcode);
	}
	
	/**
	 * Imposta le stelle dell'Hotel
	 * @param stars
	 */
	public void stars(int stars) {
		hotel.setStars(stars);
	}
	
	/**
	 * Imposta la descrizione dell'Hotel
	 * @param des
	 */
	public void description(String des) {
		hotel.setDescription(des);
	}
	
}
