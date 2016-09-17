package filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebFilter(urlPatterns={"/console", "/account"})
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest) arg0;  
        HttpServletResponse resp = (HttpServletResponse) arg1;   
        HttpSession session = req.getSession();   
        User user = (User) session.getAttribute("user");
//		if(user==null){
//			CookieUtil cu = new CookieUtil(req, resp);
//			String token = cu.getCookieValue("token");
//			try {
//				User ur = LoginService.isRemembered(token);
//				HttpSession s = req.getSession();				
//				s.setAttribute("user", ur);
//				user = (User) session.getAttribute("user");
//			}
//			catch (Exception e){
//				e.printStackTrace();
//			}
//		}
		if(user==null){
			resp.sendRedirect("/login");
		}
		else if(user.getUsername().length()==0){
			req.getRequestDispatcher("/pages/welcome.jsp").forward(req, resp);
		}
		else {
			arg2.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
