package dvt.test;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;

import org.json.JSONObject;
import org.junit.Test;

import com.baidu.aip.util.Base64Util;
import com.google.common.collect.Maps;

import dvt.api.BaiduOcr;
import dvt.api.ChaoJiYing;
import dvt.util.Base64Utils;
import dvt.util.CommonHelper;
import dvt.util.HttpHelper;
import dvt.util.ImageHandleHelper;
import dvt.util.JsonUtils;
import dvt.util.TestImage;

public class DevTest {

	public final String username = "dantegarden";
	public final String password = "10121118Dante";
	public final String machineCode = "222";// 数字，随便写
	public final String codeType = "6004";// 写死6004

	public void test() {
		String timestamp = new Date().getTime() + "";
		String id = "";
		ChaoJiYing.GetScore(username, password);
		// ChaoJiYing.ReportError(username, password, machineCode, id);
	}
	
	@Test
	public void testPic() {

		String dirpath = "D:/QMES/761043617/FileRecv/tupian/";
		String filepath = dirpath + "Y8Y01]9RP(%K7[SWIZ9$38T.png";//"pu20170711170149.png";//"pu20170711170111.png";//"pu20170613173355.jpg";

		String api_id = "MNZaWLrXH0PiG4h4GyymzwSu";
		String secret_key = "KaTGGGSGh8sGirNTsaPb16dntx6dCt9X";

		Map<String, String> params = Maps.newHashMap();
		params.put("grant_type", "client_credentials");
		params.put("client_id", api_id);
		params.put("client_secret", secret_key);
		try {
			String accessjson = HttpHelper.startGet("https://aip.baidubce.com/oauth/2.0/token",params);
			JSONObject jsonObject = new JSONObject(accessjson);
			String access_token = jsonObject.getString("access_token");
			//String access_token = "24.fdcf3e5392d092c6625a1dfa7ed97f6f.2592000.1502265060.282335-9865827";

			byte[] fileBytes = FileUtils.readFileToByteArray(new File(filepath));
			String imgStr = URLEncoder.encode(Base64Util.encode(fileBytes), "UTF-8");
			Map<String, String> params2 = Maps.newHashMap();
			params2.put("image", imgStr);
			System.out.println(imgStr);

			String backjson = HttpHelper.startPost("https://aip.baidubce.com/rest/2.0/ocr/v1/general?access_token="+access_token, params2);
			System.out.println(backjson);
//			JSONObject jobj = new JSONObject(backjson);
//			String requestId = jobj.getJSONArray("result").getJSONObject(0).getString("request_id");
//
//			Map<String, String> params3 = Maps.newHashMap();
//			params3.put("request_id", requestId);
//			params3.put("result_type", "json");
//			backjson = HttpHelper.startPost("https://aip.baidubce.com/api/v1/solution/form_ocr/get_request_result?access_token="+access_token, params3);
//			System.out.println(backjson);
//			JSONObject jobj2 = new JSONObject(backjson);

		} catch (Exception e) {
			System.out.println("获取access_token失败");
			e.printStackTrace();
		}

		// Base64Util.encode(arg0)
	}

//	@Test
//	public void test3(){
//		String dirpath = "D:/QMES/761043617/FileRecv/tupian/";
//		myTest(dirpath);
//	}
//	
//	public void myTest(String filepath){
//		File sourceFile = new File(filepath);
//		List<File> sourcelist = Arrays.asList(sourceFile.listFiles());
//		if(sourcelist!=null && sourcelist.size()>0){
//			for (File curFile : sourcelist) {
//				if(curFile.isDirectory()){
//					myTest(curFile.getAbsolutePath());
//				}else{
//					System.out.println("加密："+curFile.getAbsolutePath());
//				}
//			}
//		}
//				
//	}
	
}
