package hoppin.employee;

import hoppin.util.sql.MySQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLEmployee extends MySQLConnect {
	
	public MySQLEmployee() {
		super();
	}
	
	public boolean addEmployee(String name, String email, String passw, int id) {
		try {
			PreparedStatement pss = conn.prepareStatement("select max(id) as id from Employee");
			ResultSet rs = pss.executeQuery();
			int i=0;
			if ( rs != null) {
				rs.next();
				i = rs.getInt("id") + 1;
			}
			rs.close();
			PreparedStatement psi = conn.prepareStatement("insert into Employee (id, completeName, email, passw_hash, accType, sid ) values (?,?,?,?,?,?)");
			psi.setInt(1, i);
			psi.setString(2, name);
			psi.setString(3, email);
			psi.setString(4, passw);
			psi.setString(5, "Employee");
			psi.setInt(6, id);
			psi.execute();
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public ArrayList<Employee> getEmployeeList(int i){
		//ArrayList<String>[] al = null;
		
		ArrayList<Employee> al = null;
		
		try {
			PreparedStatement pss = conn.prepareStatement("select count(id) as max from Employee where sid = ?");
			pss.setInt(1, i);
			ResultSet rs = pss.executeQuery();
			rs.next();
			int max = rs.getInt("max");
			al = new ArrayList<Employee>();
			al.ensureCapacity(max);
			
			
			pss = conn.prepareStatement("select id,completeName,email from Employee where sid = ?");
			pss.setInt(1, i);
			rs = pss.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String completeName = rs.getString("completeName");
				Employee pnt = new Employee(id,email,completeName);
				al.add(pnt);
			}
			
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return al;
	}
	
	
	public boolean deleteEmployee(List<Integer> ids) {
		try {
			int size = ids.size();
			System.out.println("Size: " + size);
			if ( size < 1) {
				System.out.println("Lista vuota");
				return false;
			}
			
			
			StringBuilder sb = new StringBuilder();
			String qistart = "( "; //Query Incognites Start
			String qiend = " )"; //QUery Incognites End
			String strep =" ?"; //String To Repeat 
			sb.append(qistart);
			for (int i=0; i<size; i++) {
				sb.append(strep);
				if ( i+1 < size)
					sb.append(", ");
			}
			sb.append(qiend);
			
			String idsToDelete = sb.toString();
			System.out.println("Stringa costruita: " + idsToDelete);
			PreparedStatement ps = conn.prepareStatement("delete from Employee where id IN " + idsToDelete);
			
			for (int i=0; i<size;i++) {
				ps.setInt(i+1, ids.get(i));
			}
			boolean res = ps.execute(); //ps return true or false
			System.out.println("Query ritorna: " + res);
			return res;
			
		} catch( SQLException e) {
			System.out.println(e);
		}
		
		return false;
		
	}
	
	
}
