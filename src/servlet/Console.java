package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.User;
import service.ConsoleService;
import utils.CookieUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/console")
public class Console extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User u = (User) req.getSession().getAttribute("user");
		String action = req.getParameter("action");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		try {
			if("developers".equals(action)){
				String ownnerName = req.getParameter("ownerName");
				String appName = req.getParameter("appName");
				List<App> apps = ConsoleService.getAppUsers(ownnerName, appName);
				ObjectMapper mapper = new ObjectMapper();  	
				out.print(mapper.writeValueAsString(apps));
				out.flush();
				out.close();
			}else if("apps".equals(action)){
				ObjectMapper mapper = new ObjectMapper();  	
				List<App> apps = ConsoleService.getApps(u);
				out.print(mapper.writeValueAsString(apps));
				out.flush();
				out.close();
			} else{
				
				if(u!=null&&u.getId()!=null){
					req.setAttribute("apps", ConsoleService.getApps(u));
					req.getRequestDispatcher("/pages/console.jsp").forward(req, resp);
				}else{
					resp.sendRedirect("/");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			out.print(e.getMessage());
			out.flush();
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User u = (User) req.getSession().getAttribute("user");
		String action = req.getParameter("action");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();    
		
		String username = req.getParameter("username");
		CookieUtil cu = new CookieUtil(req, resp);
		String token = cu.getCookieValue("token");
		if(ConsoleService.checkUser(username, token)){
			if("delete".equals(action)){
				String appname = req.getParameter("appname");
				ConsoleService.deleteApp(username, appname, token);
			}else if("create".equals(action)){
				String appname = req.getParameter("appname");
				String apptype = req.getParameter("apptype");
				App app = ConsoleService.createApp(username, appname, apptype);
	            mapper.writeValue(out, app);   
			} else if("import-bae".equals(action)){
				String url = req.getParameter("url");
				String domain = req.getParameter("domain");
				String appname = req.getParameter("appname");
				String apptype = req.getParameter("apptype");
				String paasname = req.getParameter("paasname");
				App app = ConsoleService.importApp(username, appname, apptype, url, domain, paasname);
	            mapper.writeValue(out, app);   
			} else if("addeveloper".equals(action)){
				String developerName = req.getParameter("developer_name");
				String right = req.getParameter("rights");
				String appName = req.getParameter("appname");
				App app = null;
				try {
					app = ConsoleService.addeveloper(developerName, right, username, appName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mapper.writeValue(out, app);
			} else if ("checkDeveloper".equals(action)) {
				String developerName = req.getParameter("developer_name");
				String appName = req.getParameter("appname");
				try {
					if(ConsoleService.checkAddeveloper(developerName, username, appName)){
						out.print("success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					out.print(e.getMessage());
					out.flush();
					out.close();
				}
			}
		}
	}

}
