package hoppin.finance;

public class Season {
	private String hotel;
	private String type;
	private String start;
	private String end;
	private int percIncrease; //Percentual Increase

	public Season(String Hotel, String Type, String Start, String End, int p) {
		this.hotel=Hotel;
		this.type=Type;
		this.start=Start;
		this.end=End;
		this.percIncrease=p;
		
	}
	
	public String getHotel() { return hotel; }
	public String getType() { return type; }
	public String getStart() { return start; }
	public String getEnd() { return end; }
	public int getPercIncrease() { return percIncrease; }
}
