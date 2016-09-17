package servlet;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;
@WebServlet(name="init",loadOnStartup=1,description="init", urlPatterns="/log")
public class Logging extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		Properties prop = new Properties();

		prop.setProperty("log4j.rootLogger", "DEBUG, CONSOLE");
		prop.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{HH:mm:ss,SSS} [%t] %-5p %C{1} : %m%n");

		PropertyConfigurator.configure(prop);
	}

}
