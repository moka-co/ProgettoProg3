

import jakarta.servlet.http.Cookie;
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
		boolean flag = false;
		
		Cookie [] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie aCookie : cookies) {
				String comp = aCookie.getName();
				if ( comp.equals("id")) {
					flag = true;
					chain.doFilter(request, response);
				}
			}
		}
		if ( flag == false ) { // https://stackoverflow.com/questions/18211497/servlet-cannot-forward-after-response-has-been-committed
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
			
		}
		
	}

}
