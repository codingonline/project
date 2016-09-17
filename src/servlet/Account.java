package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.UsrJDBC;
import model.User;

import org.json.simple.JSONObject;

import utils.DateUtil;
import utils.MD5Util;
import utils.SendEmailUtil;

@WebServlet("/account")
public class Account extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		req.getRequestDispatcher("/pages/account.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		JSONObject obj=new JSONObject();
		User user = (User) req.getSession().getAttribute("user");
		if("save-bae".equals(action)){
			String baeuname = req.getParameter("bae-uname");
			String baepwd = req.getParameter("bae-pwd1");
			user.setUnameBaidu(baeuname);
			user.setPwdBaidu(baepwd);
			try {
				UsrJDBC.update(user);
				req.getSession().setAttribute("user", user);
				obj.put("status", "success");
				obj.put("unamebaidu", user.getUnameBaidu());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				obj.put("status", "error");
			}
		}else if("save-sae".equals(action)){
			String saeuname = req.getParameter("sae-uname");
			String saepwd = req.getParameter("sae-pwd1");
			
			user.setUnameSae(saeuname);
			user.setPwdSae(saepwd);
			try {
				UsrJDBC.update(user);
				req.getSession().setAttribute("user", user);
				obj.put("status", "success");
				obj.put("unamesae", user.getUnameSae());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				obj.put("status", "error");
			}
		}else if ("save-email".equals(action)) {
			String newEmail = req.getParameter("new-email");
			user.setEmail(newEmail);
			user.setRegisterStatus(false);
			user.setValidateCode(MD5Util.encode2hex(newEmail+MD5Util.SALT));
			user.setRegisterTime(DateUtil.getCurrentDate());
			try {
				StringBuffer url = req.getRequestURL();
				int len = url.length();
				url.replace(len-7, len, "signup");
				StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时内有效，否则需要重新注册账号，链接只能使用一次，请尽快激活！</br>");  
		        sb.append("<a href=\"" + url + "?action=activate&email=");  
		        sb.append(newEmail);   
		        sb.append("&validateCode=");   
		        sb.append(user.getValidateCode());  
		        sb.append("&redirect=/account");  
		        sb.append("\">" + url + "?action=activate&email=");   
		        sb.append(newEmail);  
		        sb.append("&validateCode=");  
		        sb.append(user.getValidateCode());  
		        sb.append("&redirect=/account");  
		        sb.append("</a>");
		        
				SendEmailUtil.send(newEmail, sb.toString());
				UsrJDBC.update(user);
				req.getSession().setAttribute("user", user);
				obj.put("status", "success");
				obj.put("email", newEmail);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				obj.put("status", "error");
			}
			
		}else if ("save-pwd".equals(action)) {
			String pwd = req.getParameter("pwd1");
			user.setPassword(MD5Util.encode2hex(pwd+MD5Util.SALT));
			try {
				UsrJDBC.update(user);
				req.getSession().setAttribute("user", user);
				obj.put("status", "success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				obj.put("status", "error");
			}
		}
		resp.getWriter().print(obj.toString());
		resp.getWriter().close();
	}
	
}
