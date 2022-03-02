

import hoppin.factory.AuthFactory;
import hoppin.factory.AbstractFactory;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	 

    public AuthFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		// pass the request along the filter chain

		AbstractFactory factory = new AuthFactory();
		int id = ((AuthFactory) factory).makeCookieGetter(req).getIdbyCookies();
		if ( id == -1) { //Cookie non presente o scaduto, rimanda a Index.html
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
			// https://stackoverflow.com/questions/18211497/servlet-cannot-forward-after-response-has-been-committed
		}else {
			chain.doFilter(request, response);
		}

		
	}

}
