package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Parameters;

import input.PropertyFileHandler;

public class GetProperties {
	
	 static Map<String, String> props;
	 static PropertyFileHandler propertyHandler;
	
	@Parameters({"propertyFile"})
	public static  Map<String, String> readProperties(String propertyFileName){
		System.out.println("Attempting to read property file");
		propertyHandler = new PropertyFileHandler(propertyFileName);
		propertyHandler.readPropertiesFile();
		props = new HashMap<String, String>();
		
		props.put("region", propertyHandler.getRegion());
		props.put("browser", propertyHandler.getBrowser());
		props.put("url", propertyHandler.getUrl());
		props.put("email", propertyHandler.getEmail());
		props.put("password",propertyHandler.getPassword());
		
		return props;
	}

}
