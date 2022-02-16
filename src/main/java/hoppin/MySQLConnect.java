package hoppin;
import java.sql.*;

public class MySQLConnect { //Forse questo dovrebbe diventare Singleton
	Connection conn = null;
	private String passw = "anoncorno";
	private String user ="kurush";

	public MySQLConnect(){
		
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoppin", this.user, this.passw); //Establishing connection
			System.out.println("Connected With the database successfully");
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("Error while connecting to the database");
		}
	}
	
	public boolean login(String user, String passw) {
		try {
			PreparedStatement ps = conn.prepareStatement("select id from User where email = ? and passw_hash = ?");
			ps.setString(1, user);
			ps.setString(2, passw);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id"));
			}
			rs.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return false;
		}
	}
}
