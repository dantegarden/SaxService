package dvt.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumHello {
	public static void main(String[] args) {
		//�����������û��Ĭ�ϰ�װ��C�̣���Ҫ�ƶ���·��
        System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox_47.01/firefox.exe"); 
        //System.setProperty("webdriver.gecko.driver", "E:/drivers/geckodriver.exe");
        System.setProperty("webdriver.firefox.marionette","E:/drivers/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        
        driver.get("https://www.baidu.com");
        WebElement searchField = driver.findElement(By.id("kw"));
        //searchField.sendKeys("1212");
        driver.findElement(By.id("su")).click();
        driver.quit();
	}
}
