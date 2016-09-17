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

	// �û�������
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
				// �������Ѵ���session
				return false;
			}
		}
		return true;
	}

	// �û��������Ƿ����
	private static boolean contains(Queue<Session>sessions, String username){
		for(Session session : sessions){
			if(session.getPathParameters().get("username").equals(username)){
				return true;
			}
		}
		return false;
	}

	// �Ͽ�path·���µĵ�һ������
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

	// �Ͽ�����
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

	// ��ȡĳһ·���µ�����
	public static synchronized Queue<Session> getSessions(String path, boolean putIfAbsent){
		Queue<Session> sessions = conn.get(path);
		if(putIfAbsent&&sessions==null){
			// ���ڼ�����synchronized(null)�׳��쳣�����Դ���һ������Ϊ�յĶ��У��Ըö��м��������Է�ֹ����߳�ͬʱ��key��ӳ����в���
			// ���putIfAbsentΪfalse���򲻴������󣬷���ֵΪnull����ʱû�м���������
			sessions = new LinkedList<Session>();
			conn.put(path, sessions);
		}
		return sessions;
	}

	// ��path�µ����ӵ��û���json��ʽ���
	public static String getQueueJSON(String path){
		Queue<Session> sessions = getSessions(path, false);
		if(sessions!=null&&sessions.size()>0){
			JSONArray ret = new JSONArray();
			for(Session session : sessions){
				ret.add(session.getPathParameters().get("username"));
			}
			return ret.toJSONString();
		}
		return "[]"; //���ؿ�JSON
	}

	// ��¼��־
	public static void addLog(String path, String usersJSON){
		// TODO Auto-generated method stub
		try {
			RWLogJDBC.insert(path, usersJSON);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���ҳ����ʾ
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
