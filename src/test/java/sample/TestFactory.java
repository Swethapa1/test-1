package sample;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

import input.PropertyFileHandler;

public class TestFactory {
	PropertyFileHandler propertyHandler;
	String inputFileName,region,browser,url,email,password;
	Iterator<XSSFSheet> sheetIterator;
	
	@Factory
	@Parameters({"propertyFile"})
	public Object[] factoryMethod(String propertyFileName)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		FileInputStream input = null;
		try
		{
			//Try reading properties file
			System.out.println("Attempting to read property file");
			propertyHandler = new PropertyFileHandler(propertyFileName);
			propertyHandler.readPropertiesFile();
			region=propertyHandler.getRegion();
			browser=propertyHandler.getBrowser();
			url=propertyHandler.getUrl();
			email=propertyHandler.getEmail();
			password=propertyHandler.getPassword();
			inputFileName=propertyHandler.getInput_excel_file();
			
			System.out.println("----------");
			URL url1 = this.getClass().getClassLoader().getSystemResource(inputFileName);
			input = new FileInputStream(url1.getPath());
			
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			sheetIterator = workbook.iterator();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//Try closing resources
			if(input != null)
			{
				try
				{
					input.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		while(sheetIterator.hasNext())
		{
			XSSFSheet currentSheet = sheetIterator.next();
			String currentSheetName = currentSheet.getSheetName();
			//list.add(new SignUp(currentSheet,currentSheetName));
		}
		
		Object[] array = list.toArray(new Object[list.size()]);
		return array;
		
	}
}
