package input;

import java.io.InputStream;
import java.util.Properties;

public class PropertyFileHandler {
	
	private String propertyFileName;
	private String region;
	private String url;
	private String input_excel_file;
	private String browser;
	private String email;
	private String password;
	

	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInput_excel_file() {
		return input_excel_file;
	}
	public void setInput_excel_file(String input_excel_file) {
		this.input_excel_file = input_excel_file;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public PropertyFileHandler(String propertyFileName) {
		super();
		this.propertyFileName = propertyFileName;
	}
	
	public void readPropertiesFile()
	{
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			input = getClass().getClassLoader().getResourceAsStream(this.propertyFileName);
			if(input == null)
			{
				System.out.println("Unable to open properties file");
				return;
			}
			
			prop.load(input);
			
			//Set the region
			String temp=prop.getProperty("region");
			if(temp.equals("")) 
			{
				System.out.println("Region field is empty. Setting to default region: IN");
				setRegion("IN");
			}
			else
			{
				System.out.println("Region: "+temp);
				setRegion(temp);
			}
			
			//Set the url
			temp=prop.getProperty("url");
			if(temp.equals("")) 
			{
				System.out.println("url field is empty. Setting to default pro");
				setUrl("https://myshopmatic.com");
			}
			else
			{
				System.out.println("URL: "+temp);
				setUrl(temp);
			}
			
			//Set the browser
			temp=prop.getProperty("browser");
			if(temp.equals("")) 
			{
				System.out.println("browser field is empty. Setting to default chrome");
				System.out.println("Browser: chrome");
				setBrowser("chrome");
			}
			else
			{
				System.out.println("Browser: "+temp);
				setBrowser(temp);
			}
			
			this.input_excel_file = prop.getProperty("input_file");
			this.email=prop.getProperty("email");
			this.password=prop.getProperty("password");
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}
	finally
	{
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
	}}	
	
}
