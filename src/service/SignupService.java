package service;

import java.util.Calendar;
import java.util.Date;

import jdbc.UsrJDBC;
import model.User;
import utils.DateUtil;
import utils.MD5Util;
import utils.SendEmailUtil;

public class SignupService {

	public SignupService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void register(String addr, StringBuffer url) throws Exception{
		User u = new User();
		u.setEmail(addr);
		u.setRegisterStatus(false);
		u.setRegisterTime(DateUtil.getCurrentDate());
		u.setValidateCode(MD5Util.encode2hex(addr+MD5Util.SALT));
		u.setIsRemembered(false);
		u.setToken(MD5Util.getToken());
		
		UsrJDBC.insert(u);
		
		// 邮件内容
		StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时内有效，否则需要重新注册账号，链接只能使用一次，请尽快激活！</br>");  
        sb.append("<a href=\"" + url + "?action=activate&email=");  
        sb.append(addr);   
        sb.append("&validateCode=");   
        sb.append(u.getValidateCode());  
        sb.append("\">" + url + "?action=activate&email=");   
        sb.append(addr);  
        sb.append("&validateCode=");  
        sb.append(u.getValidateCode());  
        sb.append("</a>");
        
		SendEmailUtil.send(addr, sb.toString());
		
	}

	public static User activate(String email, String vcode) throws Exception {
		// TODO Auto-generated method stub
		User u = (User) UsrJDBC.findUserByProperty("email='" + email + "'");
		if(u != null){
			if(u.getRegisterStatus() == false){
				Date currentTime = new Date();
				Calendar cl = Calendar.getInstance();
				cl.setTime(u.getRegisterTime());
				cl.add(Calendar.DATE , 2);
				if(currentTime.before(cl.getTime())){
					if(vcode.equals(u.getValidateCode())){
						u.setRegisterStatus(true);
						UsrJDBC.update(u);
						return u;
					}else{
						throw new Exception("激活码不正确");
					}
				}else{
					throw new Exception("激活码过期");
				}
			}else{
				throw new Exception("邮箱已激活，请登录");
			}
		}else{
			throw new Exception("该邮箱未注册");
		}
	}

	public static boolean isActivated(String parameter) throws Exception {
		// TODO Auto-generated method stub
		User u = UsrJDBC.findUserByProperty("email='" + parameter + "'");
		if(u!=null){
			if(u.getRegisterStatus()==true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static User setUserInfo(String id, String username, String password) throws Exception{
		User u = UsrJDBC.findUserByProperty("id='" + id + "'");
		u.setRegisterStatus(true);
		u.setIsRemembered(true);
		u.setDateJoined(DateUtil.getCurrentDate());
		u.setUsername(username);
		u.setPassword(MD5Util.encode2hex(password+MD5Util.SALT));
		u.setLastLogin(DateUtil.getCurrentDate());
		UsrJDBC.update(u);
		return u;
	}

	public static boolean isUsernameExisted(String parameter) throws Exception {
		// TODO Auto-generated method stub
		User u = UsrJDBC.findUserByProperty("username='" + parameter + "'");
		if(u!=null){
			if(u.getRegisterStatus()==true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
