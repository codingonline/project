package service.login.baidu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.LoginService;
import utils.CookieUtil;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;

/**
 * Servlet implementation class CallBackServlet
 */
@WebServlet("/callback")
public class CallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String clientId = BaiduAppConstant.CLIENTID;

	private String redirectUri = BaiduAppConstant.REDIRECTURI;

	private String clientSecret = BaiduAppConstant.CLIENTSECRET;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CallBackServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BaiduStore store = new BaiduCookieStore(clientId, request, response);
		Baidu baidu = null;
		User loggedInUser = null;
		model.User u = null;
		try {
			baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
			loggedInUser = baidu.getLoggedInUser();
			if(loggedInUser==null||loggedInUser.getUname()==null){
				response.sendRedirect("/oauthBaidu");
				return;
			}
			u = LoginService.loginBaidu(loggedInUser);
			CookieUtil cu = new CookieUtil(request, response);
			cu.addCookie("token", u.getToken());
		} catch (BaiduApiException e) {
			e.printStackTrace();
		} catch (BaiduOAuthException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("user", u);
		if(u.getUsername().equals("")||u.getUsername()==null){
			request.getRequestDispatcher("/pages/welcome.jsp").forward(request, response);
		}else{
			response.sendRedirect("/console");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
