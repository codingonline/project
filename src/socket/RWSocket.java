package socket;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint("/rwsocket/{username}")
public class RWSocket {

	private static final ObjectMapper mapper = new ObjectMapper();
	private Map<String, Object> userProperties = null;
	private Queue<Session> sessions = null;
	private String path = null;

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username){
		path = session.getRequestParameterMap().get("filepath").get(0);
		userProperties = session.getUserProperties();
		userProperties.put("timestamp", 0);
		userProperties.put("username", username);
		if("rwmonitor".equals(username)&&"rwmonitor".equals(path)){
			// �洢�������
			RWConnection.setMONI(session);
		} else if(RWConnection.put(path, session)){
			// �洢�û�����
			sessions = RWConnection.getSessions(path, false);
			String usersJSON = RWConnection.getQueueJSON(path);
			RWMessage msg = new RWMessage(201, usersJSON);
			broadcast(sessions, msg);
			System.out.println(usersJSON);
			RWConnection.addLog(path, usersJSON);
			RWConnection.notifyMONI();
		}else{
			System.out.println(username+"@"+path+" already in exist!");
		}

	}

	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose(Session session, @PathParam("username") String username){
		if("rwmonitor".equals(username)&&"rwmonitor".equals(path)){
			RWConnection.setMONI(null);
		}else{
			RWConnection.remove(path, session);
			Queue<Session> sessions = RWConnection.getSessions(path, false);
			String usersJSON = RWConnection.getQueueJSON(path);
			RWMessage msg = new RWMessage(201, usersJSON);
			RWConnection.addLog(path, usersJSON);
			RWConnection.notifyMONI();
			broadcast(sessions, msg);
		}
	}

	/**
	 * �յ��ͻ�����Ϣ����õķ���
	 * @param message �ͻ��˷��͹�������Ϣ
	 * @param session ��ѡ�Ĳ���
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			RWMessage msg = mapper.readValue(message, RWMessage.class);
			if(msg.getCode()==102 && msg.isNew(userProperties.get("timestamp"))){
				// д�߸��±༭
				userProperties.put("timestamp", msg.getTimestamp());	// ����ʱ���
				msg.setCode(202);
				broadcast(sessions, msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��������ʱ����
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("��������");
		error.printStackTrace();
	}

	private void broadcast(Queue<Session>sessions, RWMessage msg) {
		if(sessions!=null){
			try {
				String msgString = mapper.writeValueAsString(msg);
				if(msg.getCode()==201){
					// ���и���
					for (Session session : sessions) {
						session.getBasicRemote().sendText(msgString);
					}
				}else if (msg.getCode()==202) {
					// �༭����
					Iterator<Session> itr = sessions.iterator();
					itr.next();
					while (itr.hasNext()) {
						Session session = itr.next();
						session.getBasicRemote().sendText(msgString);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}