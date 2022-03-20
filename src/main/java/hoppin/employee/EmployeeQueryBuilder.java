package hoppin.employee;

import hoppin.util.QueryBuilder;

/**
 * 
 * Costruisce una stringa di id di impiegati da eliminare
 * utilizzata dal metodo {@link hoppin.employee.MySQLEmployee#deleteEmployee(java.util.List)}
 */
public class EmployeeQueryBuilder extends QueryBuilder {
	private static final String qistart = "( "; //Query Incognites Start
	private static final String qiend = " )"; //Query Incognites End
	private static final String strep =" ?"; //String To Repeat 
	
	public EmployeeQueryBuilder() {
		super();
	}
	
	/**
	 * 
	 * @param size cioè il size dell'arraylist in {@link hoppin.employee.EmployeeCache} che contiene gli id degli impiegati
	 * @return una stringa usata per la query sql
	 * ad esempio, se size = 4, allora restituisce (?, ?, ?, ?)
	 */
	public String makeQuery(int size) {
		
		sb.append(qistart);
		for (int i=0; i<size; i++) {
			sb.append(strep);
			if ( i+1 < size)
				sb.append(", ");
		}
		
		sb.append(qiend);
		query = sb.toString();
		
		return this.toString();
	}

}
