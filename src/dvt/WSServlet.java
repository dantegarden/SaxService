package dvt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Endpoint;

public class WSServlet extends GenericServlet {

	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);  
        System.out.println("准备启动WebService服务");  
        //发布一个WebService  4改95
        Endpoint.publish("http://localhost:9001/saxServer", new SaxServiceImpl());  
        System.out.println("已成功启动WebService服务"); 
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {  
        System.out.println("此Servlet不处理任何业务逻辑，仅仅用来发布一个Web服务");  
	}

}
