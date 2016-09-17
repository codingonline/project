package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditorService {


	private static final String URL = "http://123.57.2.1:8000/";


	public static String createProject(String username, String projectName, String type) throws IOException {	
		//System.out.println("packageExplorer?operation=createProject&token=" + token + "&projectName=" + projectName);
		return post(URL+"projectapi", "operation=createProject&username=" + username + "&projectName=" + projectName + "&projectType=" + type);
	}
	
	public static String deleteProject(String username, String projectName, String type) throws IOException {
		//System.out.println(URL+"projectapi/?operation=removeProject&token=" + token + "&projectName=" + projectName);
		return post(URL+"projectapi", "operation=removeProject&username=" + username + "&projectName=" + projectName);
	}

	public static String post(String path, String output) throws IOException {
		URL url = new URL(path);
		HttpURLConnection connection= (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");

		out.write(output);       
		out.flush();
		out.close();

		String strLine = "";
		String strResponse = "";
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while((strLine = reader.readLine()) != null)       {
			strResponse += strLine;
		}

		output = strResponse;
		return output;
	}

}
