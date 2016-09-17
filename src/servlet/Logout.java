package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.LogoutService;
import utils.CookieUtil;

@WebServlet("/logout")
public class Logout extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession s = req.getSession();
		User u = (User) s.getAttribute("user");
		try {
			CookieUtil cu = new CookieUtil(req, resp);
			LogoutService.logout(u);
			cu.deleteCookie("token");
			s.invalidate();
			resp.sendRedirect("/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(e.getMessage());
			out.flush();
			out.close();
		}
	}

}
