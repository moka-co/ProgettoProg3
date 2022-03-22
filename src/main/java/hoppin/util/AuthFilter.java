package hoppin.util;

import hoppin.authentication.AuthFactory;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 
 * Filtro Servlet che rimanda a <mono>index.jsp</mono> se non c'è alcun cookie con un id valido
 *
 */
public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	 

    public AuthFilter() {
        super();
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request; //cast di ServletRequest a HttpServletRequest

		AuthFactory factory = new AuthFactory();
		int id = factory.makeCookieGetter(req).getIdbyCookies();
		if ( id == -1) { //Cookie non presente o scaduto, rimanda a index.html
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
			// vedi https://stackoverflow.com/questions/18211497/servlet-cannot-forward-after-response-has-been-committed
		}else {
			chain.doFilter(request, response);
		}

		
	}

}
