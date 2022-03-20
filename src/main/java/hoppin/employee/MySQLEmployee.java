package hoppin.employee;

import hoppin.util.sql.MySQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Effettua delle operazioni CRUD (Create, Read, Update, Delete) su database MYSQL
 * legate al sottosistema Employee di hoppin.
 * Si collega al database estendendo la superclasse {@link hoppin.util.sql.MySQLConnect}
 *
 */
public class MySQLEmployee extends MySQLConnect {
	int id = 0;
	
	public MySQLEmployee() {
		super();
	}
	
	/**
	 * 
	 * @param i id dell'utente attualmente autenticato che richiama i metodi in {@link hoppin.employee.EmployeeManagement}
	 */
	public MySQLEmployee(int i) {
		id=i;
	}

	/**
	 * 
	 * @param name dell'impiegato/a
	 * @param email dell'impiegato/a
	 * @param passw dell'impiegato/a
	 * @return {@code true} se l'impiegato viene aggiunto, {@code false} se almeno una stringa di input è vuota o null, oppure se c'è un errore
	 * @throws java.sql.SQLException
	 */
	public boolean addEmployee(String name, String email, String passw) {
		
		//Se l'input è null o vuoto, ritorna falso.
		boolean stringNULL = name == null || passw == null || email == null;
		boolean stringEmpty = name.equals("") || passw.equals("") || email.equals("");
		if ( stringNULL || stringEmpty) { 
			return false;
		}
		
		String query = "select max(id) as id from Employee";
		try {
			PreparedStatement pss = conn.prepareStatement(query);
			ResultSet rs = pss.executeQuery();
			int i=0;
			if ( rs != null) {
				rs.next();
				i = rs.getInt("id") + 1;
			}
			rs.close();
			query = "insert into Employee (id, completeName, email, passw_hash, accType, sid ) values (?,?,?,?,?,?)";
			
			PreparedStatement psi = conn.prepareStatement(query);
			
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
	
	/**
	 * 
	 * @return {@code null} se non ci sono impiegati o c'è stato un errore, 
	 * altrimenti restituisce un ArrayList di impiegati dell'Hotel in cui lavora il proprietario che richiama questo metodo
	 * @throws java.sql.SQLException
	 */
	public ArrayList<Employee> getEmployeeList(){
		
		ArrayList<Employee> al = null;
		
		try {
			al = new ArrayList<Employee>();
					
			String query = "select id,completeName,email from Employee where sid = ?";
			
			PreparedStatement pss = conn.prepareStatement(query);
			
			pss.setInt(1, this.id);
			ResultSet rs = pss.executeQuery();
			
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
	
	
	/**
	 * 
	 * @param ids una lista di interi cioè id di impiegati da eliminare
	 * @return {@code true} se l'impiegato viene eliminato, {@code false} se c'è un errore o la lista è vuota
	 * @throws java.sql.SQLException
	 */
	public boolean deleteEmployee(List<Integer> ids) {
		
		try {
			int size = ids.size();
			if ( size < 1 || ids == null) { //lista vuota
				return false;
			}
			
			EmployeeQueryBuilder eqb = new EmployeeQueryBuilder();
			
			String query = "delete from Employee where id IN " + eqb.makeQuery(size);

			PreparedStatement ps = conn.prepareStatement(query);
			
			for (int i=0; i<size;i++) {
				ps.setInt(i+1, ids.get(i));
			}
			boolean res = ps.execute(); //ps return true or false
			return res;
			
		} catch( SQLException e) {
			System.out.println(e);
		}
		
		return false;
		
	}
	
	
}
