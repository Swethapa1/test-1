import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.GetProperties;

public class TestRemoteDriver {
	Map<String, String> properties;
	RemoteWebDriver driver;
	
	@BeforeClass
	private void setUp() {
		properties=new HashMap<String, String>();
		properties = GetProperties.readProperties("login.properties");
		launchBrowser();
	}
	
	private void launchBrowser() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setPlatform(Platform.LINUX);
	    capabilities.setBrowserName(properties.get("url"));
	    ChromeOptions chromeOptions = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/linux/chromedriver");
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--no-sandbox");
	    driver = new ChromeDriver(chromeOptions);
	    driver.navigate().to(properties.get("url"));
	}
	
	@Test
	public void testSeo() throws InterruptedException {
		System.out.println("Test method");
	}

}
