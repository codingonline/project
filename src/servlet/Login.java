package servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import service.LoginService;
import utils.CookieUtil;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;  

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		if("isRegistered".equals(action)){
			String username = req.getParameter("username");
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter(); 
			try{
				if((!"".equals(username)) && LoginService.isRegistered(username)==null){
					out.print("用户名错误，或用户未注册，点击<a href='/signup'>这里</a>完成注册");
					out.flush();
					out.close();
				}
			} catch(Exception e){
				e.printStackTrace();
				out.print(e.getMessage());
				out.flush();
				out.close();
			}
			
		}else if("update-pwd".equals(action)){
			String email = req.getParameter("email");
			String vcode = req.getParameter("validateCode");
			try {
				User user = LoginService.updatePwd(email, vcode);
				if(user!=null){
					req.getSession().setAttribute("user", user);
					resp.sendRedirect("/account");
				}else{
					PrintWriter out = resp.getWriter(); 
					out.print("user null");
					out.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				PrintWriter out = resp.getWriter(); 
				out.print(e.getMessage());
				out.close();
			}
		} else{
			req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String action = req.getParameter("action");
		JSONObject obj=new JSONObject();
		if("login".equals(action)){
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			try {
				User u = LoginService.login(username, password);
				if(req.getParameter("remember")!=null){
					LoginService.remember(u);
				}
				CookieUtil cu = new CookieUtil(req, resp);
				cu.addCookie("token", u.getToken());
				req.getSession().setAttribute("user", u);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter out = resp.getWriter(); 
				out.print(e.getMessage());
				out.close();
			}
		}else if ("forget-pwd".equals(action)) {
			String username = req.getParameter("uname");
			try {
				User user = LoginService.forgetPwd(username, req.getRequestURL().toString());
				if(user!=null){
					obj.put("status", "success");
					obj.put("email", user.getEmail());
				}else{
					obj.put("status", "error");
					obj.put("msg", "用户未注册");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				obj.put("status", "error");
			}
			resp.getWriter().print(obj.toString());
			resp.getWriter().close();
		}
		
	}

}
