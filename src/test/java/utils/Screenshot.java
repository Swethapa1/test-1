package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	WebDriver driver;
	
	public Screenshot(WebDriver driver) 
	{
		super();
		this.driver = driver;
	}

	public void getScreenshot() throws IOException
	{
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File dstFile = new File("./screenshots/screenshot_"+dateFormat.format(date)+".jpg");
			System.out.println("Screenshot file: "+dstFile.getAbsolutePath());
			FileUtils.copyFile(srcFile, dstFile);
	}

}
