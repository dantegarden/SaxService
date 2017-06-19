package dvt.test;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import dvt.api.ChaoJiYing;
import dvt.util.CommonHelper;
import dvt.util.ImageHandleHelper;
import dvt.util.TestImage;

public class DevTest {
	
	public final String username = "dantegarden";
	public final String password = "10121118Dante";
	public final String machineCode = "222";//数字，随便写
	public final String codeType = "6004";//写死6004
	
	public void test(){
		String timestamp = new Date().getTime()+"";
		String id = "";
		ChaoJiYing.GetScore(username, password);
		//ChaoJiYing.ReportError(username, password, machineCode, id);
	}
	
	@Test
	public void testPic(){
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("aa", 1);
		map.put("bb", 2);
		map.put("cc", 3);
		map.put("aa", 11);
		for (String key : map.keySet()) {
			System.out.println(map.get(key));
		}
		/*String yzm = "E://Program Files//apache-tomcat-8.0.41//webapps//SaxService//yzm_pic//yzm_1497254193796.png";
		String yzmyq = "E://Program Files//apache-tomcat-8.0.41//webapps//SaxService//yzm_pic//yzm_ms_1497254193796.jpeg";
		String white = "E://Program Files//apache-tomcat-8.0.41//webapps//SaxService//yzm_pic//white.jpeg";
		String out = "E://Program Files//apache-tomcat-8.0.41//webapps//SaxService//yzm_pic//outFile.jpeg";
		TestImage.createWhitePic(white);*/
		//ImageHandleHelper.overlapImage(white, yzmyq, out, "1");
		//ImageHandleHelper.overlapImage(out, yzm, out, "2");
	}
}
