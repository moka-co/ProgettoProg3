package hoppin;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;

public class AuthenticationGUI {
	public AuthenticationGUI id = null;
	public ServletOutputStream printer;
	
	public AuthenticationGUI(ServletOutputStream out) {
		printer = out;
		id = this;
	}
	
	public AuthenticationGUI getAuthGUI() {
		return this;
	}
	
	
	public void base() throws IOException {
		String nw = System.getProperty("line.separator");
		printer.println("<html>" + "<h2>Autenticazione</h2>" +  nw + "<br>" + nw + "<p>" + nw
				+ "<form action=\"./Authentication\" method=\"post\">" + nw + "Email <input type=\"text\" name=\"email\"></input>" + nw +
				"<br>" + nw + "Password <input type=\"password\" name=\"password\"></input>" + nw + "<br> <br>" + nw + 
				"<input type=\"submit\" value=\"Accedi\"></input>" + nw + "</form>" + "<br>" + "</html>"
				);

	}
	
	public void badCredentials() throws IOException { // Da rifattorizzare perché c'è ridondanza
		String nw = System.getProperty("line.separator");
		printer.println("<html>" + "<h2>Autenticazione</h2>" +  nw + "<br>" + nw + "<p>" + nw
				+ "<form action=\"./Authentication\" method=\"post\">" + nw + "Email <input type=\"text\" name=\"email\"></input>" + nw +
				"<br>" + nw + "Password <input type=\"password\" name=\"password\"></input>" + nw + "<br> <br>" + nw + 
				"<input type=\"submit\" value=\"Accedi\"></input>" + nw + "</form>" + "<br>" + nw + "<br> <br>" + 
				"<p style=\"color:red\">Credenziali errate, ritenta</p>" + "</html>"
				);
	}
	
	public void error() throws IOException {
		printer.println("<p>Non è stato trovato nessun utente associato con questa combinazione di email e password</p>");
	}
	
	public void insertedValues(String user, String passw) throws IOException {
		printer.println("<p>Inseriti i valori: " + user + " e " + passw);
		printer.println("</p>");
	}

}
