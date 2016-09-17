package service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import jdbc.UsrJDBC;
import model.User;
import utils.DateUtil;
import utils.MD5Util;
import utils.SendEmailUtil;


public class LoginService {

	public static model.User loginBaidu(com.baidu.api.domain.User loggedInUser) throws Exception{
		// TODO Auto-generated method stub
		//第一次登陆？
		model.User u0 = (model.User) UsrJDBC.findUserByProperty("uname_baidu='" + loggedInUser.getUname() + "'");
		Timestamp tt = DateUtil.getCurrentDate();
		if(u0 == null){
			u0 = new model.User();
			u0.setDateJoined(tt);
			u0.setRegisterTime(tt);
			u0.setUnameBaidu(loggedInUser.getUname());
			u0.setLastLogin(DateUtil.getCurrentDate());
			u0.setToken(MD5Util.getToken());
			u0.setIsRemembered(true);
			new UsrJDBC().insert(u0);
			u0 = (new UsrJDBC().findUserByProperty("uname_baidu='" + loggedInUser.getUname() + "'"));
		}else{
			u0.setUnameBaidu(loggedInUser.getUname());
			u0.setLastLogin(DateUtil.getCurrentDate());
			u0.setToken(MD5Util.getToken());
			u0.setIsRemembered(true);
			new UsrJDBC().update(u0);
		}			
		return u0;
	}

	public static User login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		User u = isRegistered(username);					
		
		if(u!=null&&u.getRegisterStatus()==true){
			if(u.getPassword().equals(MD5Util.encode2hex(password+MD5Util.SALT))){
				u.setLastLogin(DateUtil.getCurrentDate());
//				u.setToken(MD5Util.getToken());
				UsrJDBC.update(u);
				return u;
			}else{
				throw new Exception("密码不正确");
			}
		}else{
			throw new Exception("用户不存在");
		}
	}
	
	public static User forgetPwd(String username, String url) throws Exception{
		User user = isRegistered(username);
		if(user!=null&&user.getRegisterStatus()==true){
			user.setRegisterTime(DateUtil.getCurrentDate());
			user.setValidateCode(MD5Util.encode2hex(user.getEmail()+MD5Util.SALT));
			user.setIsRemembered(false);
			UsrJDBC.update(user);
			
			StringBuffer sb=new StringBuffer("您正在进行找回密码操作，如您未进行找回密码操作，请忽略这封邮件。</br>");  
			sb.append("点击下面链接找回密码。</br>");
	        sb.append("<a href=\"" + url + "?action=update-pwd&email=");  
	        sb.append(user.getEmail());   
	        sb.append("&validateCode=");   
	        sb.append(user.getValidateCode());  
	        sb.append("\">" + url + "?action=update-pwd&email=");   
	        sb.append(user.getEmail());  
	        sb.append("&validateCode=");  
	        sb.append(user.getValidateCode());  
	        sb.append("</a>");
	        
			SendEmailUtil.send(user.getEmail(), sb.toString());
		}
		return user;
	}
	
	public static User updatePwd(String email, String vcode) throws Exception{
		User u = (User) UsrJDBC.findUserByProperty("email='" + email + "'");
		if(u != null){
			if(u.getRegisterStatus() == true){
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
						throw new Exception("验证码不正确");
					}
				}else{
					throw new Exception("验证码过期");
				}
			}else{
				throw new Exception("账号未激活");
			}
		}else{
			throw new Exception("该邮箱未注册");
		}
	}
	
	public static User isRegistered(String username) throws Exception{
		User u = UsrJDBC.findUserByProperty("username='" + username + "'");	
		if(u==null) {
			u = UsrJDBC.findUserByProperty("email='" + username + "'");
		}
		return u;
	}

	public static void remember(User u) throws Exception {
		// TODO Auto-generated method stub				
		u.setIsRemembered(true);
		UsrJDBC.update(u);
	}
	
	public static User isRemembered(String token) throws Exception{
		User u = UsrJDBC.findUserByProperty("token='" + token + "'");
		if(u!=null&&u.getIsRemembered()){
			return u;
		}else{
			throw new Exception("failed");
		}
	}

}
