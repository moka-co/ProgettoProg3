

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	private FilterConfig filterConfig = null;
	 
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//String ipAddress = req.getRemoteAddr(); 
		//System.out.println("IP Address " +ipAddress + ", Time is " 
							//+ new Date().toString());

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
			//res.sendRedirect("/hoppin/DebugCookies");
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}
	
	public void setFilterConfig(FilterConfig filterConfig) {
	        this.filterConfig = filterConfig;
	}

}
