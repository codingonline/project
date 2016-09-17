package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.LoginService;
import utils.CookieUtil;

@WebServlet("/index")
public class Index extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		if("autoLogin".equals(action)){
			CookieUtil cu = new CookieUtil(req, resp);
			String token = cu.getCookieValue("token");
			PrintWriter out = resp.getWriter();
			try {
				User ur = LoginService.isRemembered(token);
				HttpSession s = req.getSession();				
				s.setAttribute("user", ur);
				out.print("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			} finally {
				out.flush();
				out.close();
			}

		}else{
			req.getRequestDispatcher("/pages/index.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
