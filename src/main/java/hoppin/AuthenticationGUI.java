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
	
	// Da rifattorizzare perché c'è ridondanza
	public void base() throws IOException {
		String nw = System.getProperty("line.separator");
		printer.println("<html>" + "<h2>Autenticazione</h2>" +  nw + "<br>" + nw + "<p>" + nw
				+ "<form action=\"./Authentication\" method=\"post\">" + nw + "Email <input type=\"text\" name=\"email\"></input>" + nw +
				"<br>" + nw + "Password <input type=\"password\" name=\"password\"></input>" + nw + "<br> <br>" + nw + 
				"<input type=\"submit\" value=\"Accedi\"></input>" + nw + 
				"<button name=\"register\" value=\"register\">Registrati</button>" + 
				"</form>" + "<br>" + "</html>"
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
	
	public void register() throws IOException { // Da rifattorizzare perché c'è ridondanza
		String nw = System.getProperty("line.separator");
		printer.println("<html>" + "<h2>Registrazione</h2>" +  nw + "<br>" + nw + 
				"<p>" + nw
				+ "<form action=\"./Register\" method=\"post\">" + nw + 
				"Nome e Cognome <input type=\"text\" name=\"name\"></input>" + nw + "<br>" + nw + 
				"Email <input type=\"text\" name=\"email\"></input>" + nw + "<br>" + nw + 
				"Password <input type=\"password\" name=\"password\"></input>" + nw + "<br> <br>" + nw + 
				"<input type=\"submit\" value=\"Conferma\"></input>" + nw + "</form>" + "<br>" + nw + "<br> <br>" + 
				
				"</html>" 
				);
	}
	
	public void registerSuccess() throws IOException {
		String nw = System.getProperty("line.separator");
		printer.println("<html>" + "<h2>Login</h2>" +  nw + "<br>" + nw + "<p>" + nw
				+ "<form action=\"./Authentication\" method=\"post\">" + nw + "Email <input type=\"text\" name=\"email\"></input>" + nw +
				"<br>" + nw + "Password <input type=\"password\" name=\"password\"></input>" + nw + "<br> <br>" + nw + 
				"<input type=\"submit\" value=\"Accedi\"></input>" + nw + 
				"<button name=\"register\" value=\"register\">Registrati</button>" + 
				"</form>" + "<br>" + 
				
				"<p style=\"color:red\">Registrazione effettuata con successo, ora puoi effettuare l'accesso</p>" + 
				
				"</html>"
				);

	}

}
