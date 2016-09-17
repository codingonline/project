package service;

import jdbc.UsrJDBC;
import model.User;

public class LogoutService {

	public static void logout(User u) throws Exception{
		u.setIsRemembered(false);
		UsrJDBC.update(u);
	}
}
