package service;

import java.util.List;

import jdbc.AppJDBC;
import jdbc.UsrJDBC;
import model.App;
import model.User;


public class ConsoleService {

	public static List<App> getApps(User u) throws Exception{
		List<App> apps = AppJDBC.findAppsByProperty("user_name='" + u.getUsername() + "'");
		return apps;
	}

	public static boolean checkUser(String username, String token){
		User user = null;
		try {
			user = UsrJDBC.findUserByProperty("username='" + username + "' and token='" + token + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user!=null?true:false;
	}

	public static void deleteApp(String username, String appname, String token) {
		// TODO Auto-generated method stub
		String type;
		try {
			type = AppJDBC.findAppByToken(token, appname).getAppType();
			EditorService.deleteProject(username, appname, type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static App createApp(String username, String appname, String apptype) {
		// TODO Auto-generated method stub
		App app = new App();
		app.setAppName(appname);
		app.setAppType(apptype);
		app.setUserName(username);
		app.setOwnerName(username);
		try {
			//String token = UsrJDBC.findUserByProperty("username='" + username + "'").getToken();
			String retString = EditorService.createProject(username, appname, apptype);
			app = AppJDBC.findAppByProperty("user_name='" + username + "' and app_name='" + appname + "'");
			System.out.println(retString);
			return app;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static App importApp(String username, String appname, String apptype, String url, String domain, String paasname) {
		// TODO Auto-generated method stub
		App app = new App();
		app.setAppName(appname);
		app.setAppType(apptype);
		app.setUserName(username);
		app.setOwnerName(username);
		if("bae".equals(paasname)){
			app.setPaasName("bae");
			if(url.matches("^https://svn\\.duapp\\.com/\\w+")){
				app.setSvnUrl(url);
			}else if(url.matches("^https://git\\.duapp\\.com/\\w+")){
				app.setGitUrl(url);
			}
			if(checkBAEdomain(domain)){
				app.setDomain(domain);
			}
		}
		try {
			AppJDBC.insert(app);
			app = AppJDBC.findAppByProperty("user_name='" + username + "' and app_name='" + appname + "'");
			return app;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static boolean checkBAEurl(String url){
		return url.matches("^https://svn\\.duapp\\.com/\\w+")||url.matches("^https://git\\.duapp\\.com/\\w+")==true? true:false;
	}

	public static boolean checkBAEdomain(String domain){
		return domain.matches("^http://\\w+\\.duapp\\.com/$")==true? true:false;
	}

	public static List<App> getAppUsers(String ownnerName, String appName) throws Exception {
		// TODO Auto-generated method stub
		List<App> apps = AppJDBC.findAppsByProperty("owner_name='"+ownnerName+"' and app_name='"+ appName+"' and user_name!='" + ownnerName + "'");
		return apps;
	}

	public static App addeveloper(String developerName, String right,
			String username, String appName) throws Exception {
		// TODO Auto-generated method stub
		if(checkAddeveloper(developerName, username, appName)==true){
			App app = AppJDBC.findAppByProperty("user_name='"+username+"' and app_name='" + appName + "'");
			App app0 = new App();
			app0.setAppName(appName);
			app0.setAppType(app.getAppType());
			app0.setDomain(app.getDomain());
			app0.setGitUrl(app.getGitUrl());
			app0.setImportUrl(app.getImportUrl());
			app0.setOwnerName(app.getOwnerName());
			app0.setPaasName(app0.getPaasName());
			app0.setSvnUrl(app0.getSvnUrl());
			app0.setUserName(developerName);
			if("write".equals(right)){
				app0.setWrite(true);
			}else if("read".equals(right)){
				app0.setWrite(false);
			}
			AppJDBC.insert(app0);
			return app0;
		}
		return null;
	}

	public static boolean checkAddeveloper(String developerName,
			String username, String appName) throws Exception{
		if(UsrJDBC.findUserByProperty("username='"+developerName+"'")!=null){
			if(AppJDBC.findAppByProperty("user_name='"+developerName+"' and app_name='" + appName + "'")==null){
				if(AppJDBC.findAppByProperty("user_name='"+username+"' and app_name='" + appName + "'")!=null){
					return true;
				}else{
					throw new Exception("wrong parameter");
				}
			}else{
				throw new Exception("该用户已加入开发");
			}
		}else{
			throw new Exception("该用户不存在");
		}
	}

}
