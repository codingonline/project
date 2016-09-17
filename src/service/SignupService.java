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
		
		// �ʼ�����
		StringBuffer sb=new StringBuffer("����������Ӽ����˺ţ�48Сʱ����Ч��������Ҫ����ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�</br>");  
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
						throw new Exception("�����벻��ȷ");
					}
				}else{
					throw new Exception("���������");
				}
			}else{
				throw new Exception("�����Ѽ�����¼");
			}
		}else{
			throw new Exception("������δע��");
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
