package hoppin.employee;

public class Employee {
	public Integer id;
	public String email;
	public String completeName;
	public Employee(int id, String email, String name) {
		super();
		this.id=id;
		this.email=email;
		this.completeName=name;
	}
	
	public int getId() {
		return id;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public String getCompleteName() {
		return completeName;
	}
}
