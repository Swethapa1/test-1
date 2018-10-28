package shippingtests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pagebuilder.PageBuilderPage;
import utils.GetProperties;
import utils.Screenshot;
import utils.TestUtils;

public class EnableAramexInternationalTest {
	Map<String, String> properties;
	WebDriver driver;
	
	@BeforeClass
	private void setUp() {
		properties=new HashMap<String, String>();
		properties = GetProperties.readProperties("login.properties");
		launchBrowser();
	}
	
	@AfterClass(alwaysRun = true)
	private void testmethod() throws IOException {
		Screenshot scr=new Screenshot(driver);
		scr.getScreenshot();
		driver.quit();
	}
	
	private void launchBrowser() {
		ChromeOptions chromeOptions = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver");
//		chromeOptions.addArguments("--headless");
//		chromeOptions.addArguments("--disable-gpu");
//		chromeOptions.addArguments("--disable-extensions");
//		chromeOptions.addArguments("--no-sandbox");
		driver = new ChromeDriver(chromeOptions);
		driver.navigate().to(properties.get("url"));
	}
	
	TestUtils testutils;
	
	@Test
	public void testEnableAramexInternational() throws InterruptedException {
		testutils= new TestUtils(driver);
		testutils.login(properties.get("email"), properties.get("password"), properties.get("url"));
		
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		pb.clickShipping();
		Thread.sleep(2000);
		testutils.enableAramex("international");
	}
}
