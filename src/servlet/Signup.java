package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;

import service.SignupService;
import utils.CookieUtil;

@WebServlet("/signup")
public class Signup extends HttpServlet {

	static Logger log = Logger.getLogger(Logging.class.getClass());   

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); 
		try{
			if("activate".equals(action)){
				String email = req.getParameter("email");
				String vcode = req.getParameter("validateCode");
				String redirect = req.getParameter("redirect");
				try {
					User u = SignupService.activate(email, vcode);		
					req.getSession().setAttribute("user", u);
					if(redirect!=null&&!"".equals(redirect)){
						resp.sendRedirect(redirect);
					}else{
						req.getRequestDispatcher("/pages/welcome.jsp").forward(req, resp);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
					out.write(e.getMessage());
				}finally{
					out.flush();  
					out.close();
				}
			}else if("isActivated".equals(action)){
				if(SignupService.isActivated(req.getParameter("email"))){
					out.print(true);
				}else{
					out.print(false);
				}
			}else if("isUsernameExisted".equals(action)){
				if(SignupService.isUsernameExisted(req.getParameter("username"))){
					out.print(true);
				}else{
					out.print(false);
				}
			}else{
				req.getRequestDispatcher("/pages/signup.jsp").forward(req, resp);
			}
		} catch(Exception e){
			out.print(e.getMessage());
		}

	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();  
		String action = req.getParameter("action");
		// ×¢²á²Ù×÷
		if("register".equals(action)){
			log.info("ÓÃ»§×¢²áÓÊÏä...");
			String email = req.getParameter("email");
			String pwd = req.getParameter("password");
			try {
				SignupService.register(email,req.getRequestURL());
				out.print("success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Failed to send Email.  To: " + email);
				out.print("error");
			}finally{
				out.flush();  
				out.close();
			}
		}else if("completeUserInfo".equals(action)){
			try {
				User u = SignupService.setUserInfo(req.getParameter("id"), req.getParameter("username"), req.getParameter("password"));
				req.getSession().setAttribute("user", u);
				CookieUtil cu = new CookieUtil(req, resp);
				cu.addCookie("token", u.getToken());
				resp.sendRedirect("/console");
			} catch (Exception e) {
				// TODO: handle exception
				req.getRequestDispatcher("/pages/welcome.jsp").forward(req, resp);
			}
			
		}
	}

}
