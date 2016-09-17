package socket;


public class RWMessage {
	
	private int code;
	private Long timestamp;
	private String content;
	
	public RWMessage(){}
	
	public RWMessage(int code, String content) {
		super();
		this.code = code;
		this.content = content;
		this.timestamp = ((Long)System.currentTimeMillis());
	}
	
	public RWMessage(int code, Long timestamp, String content) {
		super();
		this.code = code;
		this.timestamp = timestamp;
		this.content = content;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isNew(Object obj){
		String objString = obj.toString();
		String tString = timestamp.toString();
		if(tString.length()!=objString.length()){
			return tString.length()>objString.length()? true:false;
		}else{
			return tString.compareTo(objString)>0? true:false;
		}
	}
	
}
