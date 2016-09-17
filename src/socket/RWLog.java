package socket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RWLog {

	private String users;
	private String path;
	private Timestamp timestamp;
	
	public RWLog(Timestamp timestamp, String path, String users) {
		super();
		this.users = users;
		this.path = path;
		this.timestamp = timestamp;
	}
	public RWLog(HashMap<String, Object> map) {
		this((Timestamp)map.get("action_time"), (String)map.get("path"), (String)map.get("users"));
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public static List<RWLog> getLog(ArrayList<HashMap<String, Object>> ul){
		List<RWLog> logs = new ArrayList<RWLog>();
		for(HashMap<String, Object> uu : ul){
			logs.add(new RWLog(uu));
		}
		return logs;
	}
	
}
