package hoppin;

public class HotelInfo {
	private String name;
	
	private String via;
	private String city;
	private String postcode;
	
	private int stars;
	
	private String description;
	
	public HotelInfo() {
		super();
	}
	
	
	public String getName() { return name; }
	public String getVia() { return via; }
	public String getCity() { return city; }
	public String getPostcode() { return postcode; }
	public int getStars() { return stars; }
	public String getDescription() { return description; }
	
	public void setName(String name) { this.name = name; }
	public void setVia(String via) { this.via = via; }
	public void setCity(String city) { this.city = city; }
	public void setPostcode(String postcode) { this.postcode = postcode; }
	public void setStars(int s) { stars=s; }
	public void setDescription(String des) { description = des; }
	
}
