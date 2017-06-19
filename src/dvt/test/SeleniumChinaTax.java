package dvt.test;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;

import dvt.api.ChaoJiYing;
import dvt.bo.YzmResult;
import dvt.util.Base64Utils;
import dvt.util.ImageHandleHelper;
import dvt.util.JsonUtils;
import dvt.util.SeleniumUtils;
import dvt.util.TestImage;

public class SeleniumChinaTax {

	public final String contextDir = System.getProperty("user.dir");
	public final String filepath = contextDir + "\\yzm_pic\\yzm.png"; // 验证码图片
	public final String filepath2 = contextDir + "\\yzm_pic\\yzm_ms.jpeg"; // 验证码要求
	public final String fileoutpath = contextDir + "\\yzm_pic\\union.jpeg"; // 拼好的验证码+要求

	public final String username = "dantegarden";
	public final String password = "10121118Dante";
	public final String machineCode = "222";// 数字，随便写
	public final String codeType = "6004";// 写死6004
	
/*
 *  普通发票
 * 	037021600111
	23596501
	20161114
	940253
*/	
	public final String _fpdm = "4403162130";
	public final String _fphm = "47894260";
	public final String _kprq = "20170315";
	public final String _kjje = "283.02";
	
	public final String tableid = _fpdm.length()==12?"tabPage-dzfp":"tabPage-zzszyfp" ;
	
	private Timer timer = new Timer();

	public synchronized void fillForm(WebDriver driver) {
		//清理验证码图片
		String[] paths = {filepath,filepath2,fileoutpath};
		for (String p : paths) {
			File curfile = new File(p);
			if(curfile.exists()){
				curfile.delete();
			}
		}
		
		
		driver.get("https://inv-veri.chinatax.gov.cn");
		WebElement fpdm = driver.findElement(By.id("fpdm"));
		fpdm.sendKeys(_fpdm);
		WebElement fphm = driver.findElement(By.id("fphm"));
		fphm.sendKeys(_fphm);
		WebElement kprq = driver.findElement(By.id("kprq"));
		kprq.sendKeys(_kprq);
		WebElement kjje = driver.findElement(By.id("kjje"));
		kjje.sendKeys(_kjje);
		// 等待验证码刷出来，最多3秒
		WebDriverWait wait = new WebDriverWait(driver, 3);
		boolean yzmready = wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				String pic_src = arg0.findElement(By.id("yzm_img")).getAttribute("src");
				return pic_src.startsWith("data:image/png;base64,");
			}
		});
		/*
		 * String pic_src =
		 * driver.findElement(By.id("yzm_img")).getAttribute("src");
		 * System.out.println(pic_src); boolean yzmready =
		 * pic_src.startsWith("data:image/png;base64,"); while(!yzmready){ try {
		 * Thread.sleep(200); } catch (InterruptedException e) {
		 * e.printStackTrace(); } }
		 */

		if (yzmready) {

			WebElement yzm = driver.findElement(By.id("yzm"));// 验证码输入框
			WebElement yzm_img = driver.findElement(By.id("yzm_img"));// 验证码base64字符串
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					SeleniumUtils.snapshot(driver, yzm_img, filepath);
				}
			}, 2000);
			while(true){
				try {
					Thread.sleep(1000);
					if(new File(filepath).exists()){break;}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			

			WebElement yzminfo = driver.findElement(By.id("yzminfo"));// 验证码提示
			String yzminfo_context = yzminfo.getText();
			// String yzm_base64 =
			// yzm_img.getAttribute("src").replaceAll("data:image/png;base64,",
			// "");
			// Base64Utils.GenerateImage(yzm_base64,filepath);//写到本地
			//TestImage.exportImg1_snapshot(yzminfo_context, filepath2);// 要求也写到本地
			ImageHandleHelper.overlapImage(filepath2, filepath, fileoutpath);// 拼接图片
			
			while(true){
				try {
					Thread.sleep(1000);
					if(new File(fileoutpath).exists()){break;}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//String yzm_res="{\"err_no\":0,\"err_str\":\"OK\",\"pic_id\":\"262128065301\",\"pic_str\":\"\u8fd9\",\"md5\":\"657907e379e81c494cb11ee1221150c3\",\"str_debug\":\"trans-message\"}";

			String yzm_res = ChaoJiYing.PostPic(username, password,machineCode, codeType, "0", "0", "trans-message",fileoutpath);
			System.out.println(yzm_res);
			YzmResult yzmres = JsonUtils.jsonToJavaBean(yzm_res,YzmResult.class);
			// json字符串转对象
			System.out.println(yzmres.getPic_str());
			yzm.sendKeys(yzmres.getPic_str());
			String picid = yzmres.getPic_id();
			System.out.println(picid);

			WebElement checkfp = driver.findElement(By.id("checkfp"));// 查验按钮

			if (checkfp.getAttribute("style").equals("display: inline-block;")) {
				checkfp.click();
				try {
					WebDriverWait tabwait = new WebDriverWait(driver, 3);
					WebElement tableDiv = tabwait.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver arg0) {
							return arg0.findElement(By.id(tableid));
						}
					});
					List<WebElement> hrList = tableDiv.findElements(By.cssSelector("table > tbody > tr > td > span")); // By.xpath("//table/tbody/tr/td/span")
					if (CollectionUtils.isNotEmpty(hrList)) {
						for (WebElement we : hrList) {
							System.out.println(we.getText());
						}
					}
				} catch (Exception e) {
					WebElement tableDiv = driver.findElement(By.id("popup_container"));// table
					String alert_msg = driver.findElement(By.id("popup_message")).getText();
					if (alert_msg.indexOf("验证码") > -1) {
						// 验证失败 发送错误报告
						ChaoJiYing.ReportError(username, password,machineCode, picid);
						this.fillForm(driver);
					} else {
						System.out.println(alert_msg);
					}
				}
				
			}
		}
	}

	@Test
	public void testMethod() {
//		System.setProperty("webdriver.firefox.bin",
//				"D:/Program Files (x86)/Mozilla Firefox_47.01/firefox.exe");
//		// System.setProperty("webdriver.gecko.driver",
//		// "E:/drivers/geckodriver.exe");
//		System.setProperty("webdriver.firefox.marionette",
//				"E:/drivers/geckodriver.exe");
		WebDriver driver = new HtmlUnitDriver(true);
		driver.manage().window().maximize();// 窗口最大化

		this.fillForm(driver);

		driver.quit();

	}

	/**
	 * 9 发票代码 1 10 发票号码 2 11 开票时间 3 12 校验码 4 13 机器编号 5 14 购买方 名称 6 15 纳税人识别号 7
	 * 16 地址、电话 8 17 开户行及账号 9 18 货物或应税劳务、服务名称 10 19 规格型号 11 20 单位 12 21 数量 13 22
	 * 单价 14 23 金额 15 24 税率 16 25 税额 17 26 金额(带单位) 18 27 税额(带单位) 19 28 价税合计大写 20
	 * 29 30 价税合计小写 22 31 销售方：名称 23 32 销售方：纳税人识别号 24 33 销售方：地址、电话：25 34
	 * 销售方：开户行及账号 26
	 * 
	 * ****/
}
