package subscriptiontests.hk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.GetProperties;
import utils.Screenshot;
import utils.TestUtils;

public class SubscribeForSevenMonthPlanTest {

	Map<String, String> properties;
	WebDriver driver;
	
	@BeforeClass
	private void setUp() {
		//GetProperties getProperties=new GetProperties();
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
	
	TestUtils testUtils;String num;
	@Test
	public void testSubscribeForSevenMonthPlan_hk() throws InterruptedException {
		testUtils = new TestUtils(driver);
		testUtils.login(properties.get("email"), properties.get("password"), properties.get("url"));
		testUtils.enableSubscription(properties.get("region"), "Seven");
	}

}
