package socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jdbc.MySQL;

public class RWLogJDBC {

	public static List<RWLog> findAll() throws Exception{
		String sqlString = "select * from rwlog";
		return find(sqlString);
	}
	
	public static List<RWLog> findCurrent() throws Exception{
		String sqlString = "select action_time, path, users from rwlog log where action_time>=all (select action_time from rwlog where path=log.path) and length(users)>2";
		return find(sqlString);
	}
	
	public static List<RWLog> find(String sqlString) throws Exception{
		MySQL mySQL = new MySQL();
		ArrayList<HashMap<String, Object>> list = mySQL.execute(sqlString);
		if(list==null) return null;
		return RWLog.getLog(list);
	}
	
	public static void insert(String path, String usersJSON) throws Exception{
		String sqlString = "insert into rwlog(path, users) value ('" + path + "', '" 
							+ usersJSON + "')";
		new MySQL().execute(sqlString);
	}
}
