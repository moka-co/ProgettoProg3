package hoppin.hotelinfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;

public class HotelInfoBuilder {
	HotelInfo hotel = new HotelInfo();
	
	public HotelInfoBuilder(ResultSet rs) throws SQLException {
		this.name(rs.getString("Name"))
			.via(rs.getString("Via"))
			.city(rs.getString("City"))
			.postcode(rs.getString("Postcode"))
			.stars(rs.getInt("Stars"))
			.description(rs.getString("description"));
	}
	
	public HotelInfoBuilder(HttpServletRequest request) {
		this.via(request.getParameter("via"))
			.city(request.getParameter("city"))
			.postcode(request.getParameter("postcode"))
			.description(request.getParameter("description"));
		
		
		if ( request.getParameter("stars") != null) {
			stars( Integer.valueOf(request.getParameter("stars")) );
		}else {
			stars(0);
		}
	}
	
	public HotelInfo toHotelInfo() {
		return hotel;
	}
	
	public HotelInfoBuilder name(String name) {
		hotel.setName(name);
		return this;
		
	}
	
	public HotelInfoBuilder via(String via) {
		hotel.setVia(via);
		return this;
	}
	
	public HotelInfoBuilder city(String city) {
		hotel.setCity(city);
		return this;
	}
	
	public HotelInfoBuilder postcode(String postcode) {
		hotel.setPostcode(postcode);
		return this;
	}
	
	public HotelInfoBuilder stars(int stars) {
		hotel.setStars(stars);
		return this;
	}
	
	public HotelInfoBuilder description(String des) {
		hotel.setDescription(des);
		return this;
	}
	
}
