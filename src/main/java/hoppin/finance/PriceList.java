package hoppin.finance;

public class PriceList {
	public String roomType;
	public int price;

	public PriceList(String type, int price) {
		super();
		this.roomType=type;
		this.price=price;
		
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getRoomType() {
		return roomType;
	}

}
