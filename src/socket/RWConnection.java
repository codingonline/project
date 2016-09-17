package socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;


public class RWConnection {

	private static final int maxIdleTimeout = 30000;	//30s

	private static final Map<String, Queue<Session>> conn = new ConcurrentHashMap<String, Queue<Session>>();

	private static Session MONI_SESSION = null;

	public static void setMONI(Session session){
		MONI_SESSION = session;
		if(session!=null)
			notifyMONI();
	}

	public static Session getMONI(){
		return MONI_SESSION;
	}

	// 用户打开连接
	public static boolean put(String path, Session session){
		//if(conn.containsKey(path)){
		Queue<Session> sessions = getSessions(path, true);
		synchronized (sessions) {
			if(sessions.size()==0){
				session.setMaxIdleTimeout(maxIdleTimeout);
				sessions.add(session);
			}
			else if(!contains(sessions, session.getPathParameters().get("username"))){
				sessions.add(session);
			}else{
				// 队列中已存在session
				return false;
			}
		}
		return true;
	}

	// 用户的连接是否存在
	private static boolean contains(Queue<Session>sessions, String username){
		for(Session session : sessions){
			if(session.getPathParameters().get("username").equals(username)){
				return true;
			}
		}
		return false;
	}

	// 断开path路径下的第一个连接
	public static Session remove(String path){
		Queue<Session> sessions = getSessions(path, true);
		synchronized (sessions) {
			Session head = sessions.poll();
			Session nextSession = sessions.peek();
			if(nextSession==null){
				conn.remove(path);
			}else{
				nextSession.setMaxIdleTimeout(maxIdleTimeout);
			}
			return head;
		}
	}

	// 断开连接
	public static boolean remove(String path, Session session) {
		//if(conn.containsKey(path)){
		Queue<Session> sessions = getSessions(path, true);
		boolean r = false;
		synchronized (sessions) {
			for(Session s : sessions){
				if(s.getId()==session.getId()){
					r = sessions.remove(s);
					if(sessions.size()>0){
						sessions.peek().setMaxIdleTimeout(maxIdleTimeout);
					}
					break;
				}
			}
			if(sessions.size()==0){
				conn.remove(path);
			}
		}
		return r;
	}

	// 获取某一路径下的连接
	public static synchronized Queue<Session> getSessions(String path, boolean putIfAbsent){
		Queue<Session> sessions = conn.get(path);
		if(putIfAbsent&&sessions==null){
			// 用于加锁，synchronized(null)抛出异常，所以创建一个内容为空的队列，对该队列加锁，可以防止多个线程同时对key的映射队列操作
			// 如果putIfAbsent为false，则不创建对象，返回值为null，此时没有加锁的需求
			sessions = new LinkedList<Session>();
			conn.put(path, sessions);
		}
		return sessions;
	}

	// 把path下的连接的用户以json格式输出
	public static String getQueueJSON(String path){
		Queue<Session> sessions = getSessions(path, false);
		if(sessions!=null&&sessions.size()>0){
			JSONArray ret = new JSONArray();
			for(Session session : sessions){
				ret.add(session.getPathParameters().get("username"));
			}
			return ret.toJSONString();
		}
		return "[]"; //返回空JSON
	}

	// 记录日志
	public static void addLog(String path, String usersJSON){
		// TODO Auto-generated method stub
		try {
			RWLogJDBC.insert(path, usersJSON);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 监控页面显示
	public static void notifyMONI(){
		try {
			if(MONI_SESSION!=null&&MONI_SESSION.isOpen()){
				JSONObject object = new JSONObject();
				object.put("database", databaseMONI());
				object.put("memory", memoryMONI());
				MONI_SESSION.getBasicRemote().sendText(object.toJSONString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static String databaseMONI() throws Exception{
		List<RWLog> logs = RWLogJDBC.findCurrent();
		ObjectMapper mapper = new ObjectMapper();
		String retString = mapper.writeValueAsString(logs);
		return retString;
	}

	public static String memoryMONI(){
		JSONArray array = new JSONArray();
		for(Entry<String, Queue<Session>> entry : conn.entrySet()){
			JSONObject object = new JSONObject();
			String userString = getQueueJSON(entry.getKey());
			object.put("path", entry.getKey());
			object.put("users", userString);
			array.add(object);
		}
		return array.toJSONString();
	}

}
