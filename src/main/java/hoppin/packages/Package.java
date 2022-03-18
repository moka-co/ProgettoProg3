package hoppin.packages;

public class Package {

	public String name;
	public String description;
	public String Hotel;
	public int price;
	
	public Package(String name, String description, int price) {
		super();
		this.name=name;
		this.description=description;
		this.price=price;
	}
	
	public Package(String name, String description, String Hotel, int price) {
		super();
		this.name=name;
		this.description=description;
		this.Hotel=Hotel;
		this.price=price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getHotel() {
		return Hotel;
	}
	
	public int getPrice() {
		return price;
	}
	
}