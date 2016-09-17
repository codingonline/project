package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;



public class MySQL {
	
//	private static final Logger log = LoggerFactory.getLogger(UserJDBC.class);
	
	private Connection connection;
	
	public MySQL() {
		initConnection();
	}

	private void initConnection() {
		try {
//			log.debug("opening database connection");
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://rdsj7nhfyy0syt1fw980.mysql.rds.aliyuncs.com:3306/pop2016",
					"useradmin", "useradmin");
//			log.debug("database connection opened");
		}
		catch (Exception e) {
			connection=null;
		}
	}

	public ArrayList<HashMap<String, Object>> execute(String sql) throws Exception {
		String _sql=new String(sql);
		System.out.println(_sql);
		try {
			if (connection==null || connection.isClosed())
				initConnection();
			if (connection==null)
				return null;
			Statement statement=connection.createStatement();
			ArrayList<HashMap<String, Object>> arrayList=new ArrayList<>();
			if (_sql.toLowerCase(Locale.getDefault()).startsWith("select")) {
//				log.debug("executeQuery");
				ResultSet resultSet=statement.executeQuery(_sql);
				if (resultSet==null)
					return null;
				ResultSetMetaData metaData=resultSet.getMetaData();
				int colnums=metaData.getColumnCount();
				
				while (resultSet.next()) {
					HashMap<String, Object> map=new HashMap<>();
					for (int i=1;i<=colnums;i++) {
						String name=metaData.getColumnName(i);
						Object value=resultSet.getObject(i);
						map.put(name, value);
					}
					arrayList.add(map);
				}
				return arrayList.size()==0? null: arrayList;

			}
			else {
//				log.debug("executeUpdate");
				statement.executeUpdate(_sql);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("wrong sql: "  + _sql);
		} finally {
			close();
		}
	}


	public void close() {
		try {
//			log.debug("closing database connection");
			connection.close();
//			log.debug("database connection closed");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			connection=null;
		}
	}

public static String convertStr(Object str){
		if(str==null) return "''";
		else return "'" + str.toString().replaceAll(".*([';]+|(--)+).*", " ") + "'";
	}
}
