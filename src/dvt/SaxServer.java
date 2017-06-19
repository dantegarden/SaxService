package dvt;

import javax.xml.ws.Endpoint;

public class SaxServer {
	public static void main(String[] args) {
		Endpoint.publish("http://101.201.75.114:9001/saxServer", new SaxServiceImpl());
		System.out.println("webserver started");
	}
}
