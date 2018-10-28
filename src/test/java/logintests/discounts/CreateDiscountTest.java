package logintests.discounts;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.GetProperties;
import utils.Screenshot;
import utils.TestUtils;

public class CreateDiscountTest {

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
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--no-sandbox");
		driver = new ChromeDriver(chromeOptions);
		driver.navigate().to(properties.get("url"));
	}
	
	TestUtils testutils;
	String type, name, code, amount, sdate, edate;
	@Test
	public void testCreateFlatDiscount() throws InterruptedException {
		testutils= new TestUtils(driver);
		testutils.login(properties.get("email"), properties.get("password"), properties.get("url"));
		String type="", name=RandomStringUtils.randomAlphabetic(5), code=RandomStringUtils.randomAlphabetic(5), amount=RandomStringUtils.randomNumeric(3), sdate=date(), edate=date();
		testutils.addDiscount(type, name, code, amount, sdate, edate);
	}
	
	private String date() {
	    SimpleDateFormat formDate = new SimpleDateFormat("dd MM yyyy");
	    String strDate = formDate.format(new Date());
	    return strDate;
	}
	
	//free shipping discount
	@Test
	public void testCreateFreeShippingDiscount() throws InterruptedException {
		String type="free shipping discount", name=RandomStringUtils.randomAlphabetic(5), code=RandomStringUtils.randomAlphabetic(5), amount=RandomStringUtils.randomNumeric(3), sdate=date(), edate=date();
		testutils.addDiscount(type, name, code, amount, sdate, edate);
	}
	
	@Test
	public void testCreatePercentageDiscount() throws InterruptedException {
		String type="percentage discount", name=RandomStringUtils.randomAlphabetic(5), code=RandomStringUtils.randomAlphabetic(5), amount=RandomStringUtils.randomNumeric(1), sdate=date(), edate=date();
		testutils.addDiscount(type, name, code, amount, sdate, edate);
	}
}
