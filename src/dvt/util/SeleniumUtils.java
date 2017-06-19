package dvt.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumUtils {
	public static void snapshot(WebDriver driver,WebElement wel,String outputPath){
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
        	Point p = wel.getLocation();
            int width = wel.getSize().getWidth();
            int higth = wel.getSize().getHeight();
            Rectangle rect = new Rectangle(width, higth);
            BufferedImage img = ImageIO.read(srcFile);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, higth);
            ImageIO.write(dest, "png", srcFile);
            Thread.sleep(1000);
            File fng = new File(outputPath);
            if(fng.exists()){
                fng.delete();
            }
            FileUtils.copyFile(srcFile,fng);
            
        } catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean doesWebElementExist(WebDriver driver, By selector){
		try {
			 driver.findElement(selector);
			 return true; 
		} catch (NoSuchElementException  e) {
			return false; 
		}
	}
	
	public static boolean doesWebElementExist(WebElement we, By selector){
		try {
			 we.findElement(selector);
			 return true; 
		} catch (NoSuchElementException  e) {
			return false; 
		}
	}
	
}
