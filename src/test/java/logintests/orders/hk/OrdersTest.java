package logintests.orders.hk;

import java.io.IOException;
import java.util.ArrayList;
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

public class OrdersTest {
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
	
	public String cust_email=RandomStringUtils.randomAlphabetic(6)+"@"+RandomStringUtils.randomAlphabetic(4)+".com";
	public String cust_fname=RandomStringUtils.randomAlphabetic(6);
	public String cust_lname=RandomStringUtils.randomAlphabetic(4);
	public String cust_street=RandomStringUtils.randomAlphanumeric(20);
	
	public String cust_state="Kwun Tong";
	public String cust_pincode="";
	public String cust_phone=RandomStringUtils.randomNumeric(8);
	public String district="Admiralty";
	Boolean SaveInfoFuturePurposes= false;
	String shipping_method="standard_shipping",pmt_mode="cod";
	String state="Southern", po_num="", country="";
	String order_id="";
	
	@Test
	public void testCreateOrder() throws InterruptedException {
		testutils= new TestUtils(driver);
		testutils.login(properties.get("email"), properties.get("password"), properties.get("url"));
		testutils.viewSite();
		order_id=testutils.createOrder(cust_email, cust_fname, cust_lname, cust_street, cust_pincode, cust_phone, SaveInfoFuturePurposes, shipping_method, pmt_mode, district, state, po_num, country);
		Thread.sleep(2000);
	}
	
	@Test(dependsOnMethods= {"testCreateOrder"})
	public void testOrderMarkAsDelivered() throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    Thread.sleep(2000);
		testutils.markAsADeliveredForOrder(order_id);
	}
	
	@Test(dependsOnMethods= {"testOrderMarkAsDelivered"})
	public void testPrintInvoice() throws InterruptedException {
		testutils.printInvoiceForOrder(order_id);
	}
	
}
