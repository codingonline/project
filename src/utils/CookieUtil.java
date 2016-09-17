package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	private HttpServletRequest request;  
	private HttpServletResponse response;  
	private int age = 60*60*24*7;  	//����cookie�����೤���ɾ��   7��֮��ɾ��
	public CookieUtil(HttpServletRequest request,  
			HttpServletResponse response) {  
		this.request = request;  
		this.response = response;   
	}  

	public void addCookie(String name, String value) {  
		Cookie cookies = new Cookie(name, value);  
		cookies.setPath("/");  
		cookies.setMaxAge(age);  
		response.addCookie(cookies);  
	}  
	public String getCookieValue(String cookieName) {  
		if (cookieName != null) {  
			Cookie cookie = getCookie(cookieName);  
			if(cookie!=null){  
				return cookie.getValue();  
			}  
		}  
		return "";  
	}  

	public Cookie getCookie(String cookieName){  
		Cookie[] cookies = request.getCookies();  
		Cookie cookie = null;  
		try {  
			if (cookies != null && cookies.length > 0) {  
				for (int i = 0; i < cookies.length; i++) {  
					cookie = cookies[i];  
					if (cookie.getName().equals(cookieName)) {  
						return cookie;  
					}  
				}  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return cookie;  
	}  

	public boolean deleteCookie(String cookieName) {  
		if (cookieName != null) {  
			Cookie cookie = getCookie(cookieName);  
			if(cookie!=null){  
				cookie.setMaxAge(0);//���0����˵������ɾ��  
				cookie.setPath("/");//��Ҫ©��  
				response.addCookie(cookie);  
				return true;  
			}  
		}  
		return false;  
	}  

	public boolean deleteAll(){
		Cookie[] c = request.getCookies();
		if (c != null && c.length > 0) {  
			for (int i = 0; i < c.length; i++) {  
				c[i].setMaxAge(0);//���0����˵������ɾ��  
				c[i].setPath("/");//��Ҫ©��  
				response.addCookie(c[i]);  
				return true;
			}
		}
		return false;
	}
}
