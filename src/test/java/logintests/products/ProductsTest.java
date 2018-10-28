package logintests.products;

import java.io.IOException;
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

public class ProductsTest {
	Map<String, String> properties;
	WebDriver driver;
	String product_name;
	String product_price;
	
	@BeforeClass
	private void setUp() {
		//GetProperties getProperties=new GetProperties();
		properties=new HashMap<String, String>();
		properties = GetProperties.readProperties("login.properties");
		
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
	
	@Test
	public void testCreateProduct() throws InterruptedException {
		launchBrowser();
		testutils=new TestUtils(driver);
		testutils.login(properties.get("email"), properties.get("password"), properties.get("url"));
		testCreateProductWithBasicDetails_String();
		testCreateProductWithBasicDetails_alphanumeric_decimal();
		testCreateProductWithBasicDetails_specialchars();
		testCreateProductWithFullShippingDetails();
		testCreateProductWithShippingDetailsAndInventory();
		testEnabledisableProductpurchaseWhenOOS();
		testEnabledisableProductInStore();
		//testDeleteProduct();

	}
	
	
	private void testCreateProductWithBasicDetails_String() throws InterruptedException {
		product_name=RandomStringUtils.randomAlphabetic(5);
		product_price=RandomStringUtils.randomNumeric(3);
		testutils.addProductBasicDetails(product_name, product_price);
	}
	
	
	private void testCreateProductWithBasicDetails_alphanumeric_decimal() throws InterruptedException {
		product_name=RandomStringUtils.randomAlphanumeric(7);
		product_price=RandomStringUtils.randomNumeric(3)+"."+RandomStringUtils.randomNumeric(2);
		testutils.addProductBasicDetails(product_name, product_price);
	}
	
	private void testCreateProductWithBasicDetails_specialchars() throws InterruptedException {
		product_name=RandomStringUtils.randomAlphabetic(5)+"_*#@";
		product_price=RandomStringUtils.randomNumeric(3);
		testutils.addProductBasicDetails(product_name, product_price);
	}
	
	String product_oprice,product_desc,product_stock,product_category,length,height,width,weight;
	
	private void testCreateProductWithFullShippingDetails()throws InterruptedException {
		product_name=RandomStringUtils.randomAlphabetic(5)+"_*#@123";
		product_price=RandomStringUtils.randomNumeric(3);
		product_oprice=product_price;
		product_desc=RandomStringUtils.randomAlphabetic(25);
		product_stock=RandomStringUtils.randomNumeric(2);
		product_category=RandomStringUtils.randomAlphabetic(5);
		length=RandomStringUtils.randomNumeric(2);
		height=RandomStringUtils.randomNumeric(2);
		width=RandomStringUtils.randomNumeric(2);
		weight=RandomStringUtils.randomNumeric(2);
		testutils.addProductWithShippingDetails(product_name, product_oprice, product_price, product_desc, product_stock, product_category, length, height, width, weight);
	}
	
	String inventory,upc;
	private void testCreateProductWithShippingDetailsAndInventory() throws InterruptedException {
		product_name=RandomStringUtils.randomAlphabetic(5)+"_*#@123";
		product_price=RandomStringUtils.randomNumeric(3);
		product_oprice=product_price;
		product_desc=RandomStringUtils.randomAlphabetic(25);
		product_stock=RandomStringUtils.randomNumeric(2);
		product_category=RandomStringUtils.randomAlphabetic(5);
		length=RandomStringUtils.randomNumeric(2);
		height=RandomStringUtils.randomNumeric(2);
		width=RandomStringUtils.randomNumeric(2);
		weight=RandomStringUtils.randomNumeric(2);
		inventory=RandomStringUtils.randomAlphabetic(4);
		upc=RandomStringUtils.randomAlphabetic(4);
		testutils.addProductWithShippingDetailsAndInventory(product_name, product_oprice, product_price, product_desc, product_stock, product_category, length, height, width, weight, inventory, upc);		
	}
	
	private void testEnabledisableProductpurchaseWhenOOS() throws InterruptedException {
		testutils.enabledisableProductpurchaseWhenOOS(product_name);
		Thread.sleep(1000);
	}
	
	private void testEnabledisableProductInStore() throws InterruptedException {
		testutils.enabledisableProductInStore(product_name);
	}
	
	private void testDeleteProduct() throws InterruptedException {
		testutils.deleteProduct(product_name);
	}
	
}
