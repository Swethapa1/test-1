import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Navigation.Navigation;

import domain.DomainNamePage;
import edittemplate.PageBuilderPage;
import products.Products;
import publish.ReadyToPublishPage;
import SignUp.BusinessInfo;
import SignUp.SignUp;
import SignUp.SignUpWindow;
import aboutus.AboutUsPage;
import input.InputDocumentReader;
import input.JsonClass;
import input.PropertyFileHandler;
import payments.PaymentOptionsPage;
import sample.SignUpProperties;
import termsandconditions.TnCPage;
import utils.BusinessInfoDetails;
import utils.ChooseCategory;
import utils.CompanyAuthenticationDetails;

public class SignUpTest {
	
	SignUpProperties s; 
	PropertyFileHandler propertyHandler;
	String inputFileName,region,browser,url,email,password;
	int no_of_rows=0;
	
	private ArrayList<String> currentHeaders;
	private ArrayList<ArrayList<String>> currentSheetData;
	private static InputDocumentReader bookReader;
	
	 FileInputStream input=null;
     XSSFWorkbook  wb=null;
     XSSFSheet sheet=null;
     
	
	@BeforeClass
	@Parameters({"propertyFile"})
	public void setup(String propertyFileName) throws IOException {
		propertyHandler = new PropertyFileHandler(propertyFileName);
		System.out.println("Attempting to read property file");
		propertyHandler = new PropertyFileHandler(propertyFileName);
		propertyHandler.readPropertiesFile();
		region=propertyHandler.getRegion();
		browser=propertyHandler.getBrowser();
		url=propertyHandler.getUrl();
		email=propertyHandler.getEmail();
		password=propertyHandler.getPassword();
		inputFileName=propertyHandler.getInput_excel_file();
		
		try {
   		 	input = new FileInputStream(inputFileName);
   		 	wb = new XSSFWorkbook(input);
   		 	sheet = wb.getSheetAt(0);
   	 	}catch(Exception e) {
   		 e.printStackTrace();
   	    }
		bookReader = new InputDocumentReader();
		bookReader.setInputFileName(inputFileName);
	}
	
	WebDriver driver;
	JSONObject j1;
	
	@Test(dataProvider="sheetData")
	public void testMethod(ArrayList<String> headers, ArrayList<String> values) throws InterruptedException{
		ChromeOptions chromeOptions = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver");
		driver=new ChromeDriver();
		//driver.navigate().to(url);
		driver.get(url);
		Thread.sleep(5000);
		
		JsonClass j = new JsonClass();
		j1=new JSONObject();
		j1 = j.JsonObjectConstructor(headers, values);
		
		signup();
		setBusinessInfo();
		if(driver.findElements(By.xpath("//span[contains(@class,'manange-drop-list-products')]")).size()!=0) 
		{
			addFirstProduct();
		}else {
			driver.findElement(By.xpath("//div[contains(@class,'SkipThisButton')]/div")).click();
			Navigation.clickNextEditSite(driver);
			Navigation.clickNextEditSite(driver);
			Thread.sleep(3000);
		}
		addCustomDomain();
		if(!j1.getString("dom_pmt_offline_instructions").isEmpty() ||!j1.getString("intl_pmt_offline_instructions").isEmpty())
			enablePayments();
		publishSite();	
		Thread.sleep(2000);
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		Thread.sleep(2000);
		if(!j1.getString("about_us").isEmpty())
				addAboutUs();
		if(!j1.getString("shipping_policy").isEmpty() || !j1.getString("shipping_policy_details").isEmpty() || !j1.getString("refund_policy_details").isEmpty() || !j1.getString("refund_policy").isEmpty())
			addTnC();
	}
	
	private void addTnC() throws InterruptedException {
		// TODO Auto-generated method stub
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		Thread.sleep(1000);
		pb.clickEditSite();
		Thread.sleep(1000);
		pb.clickPageDropDown();
		pb.clickEditShippingReturns();
		Actions action = new Actions(driver);
		action.moveByOffset(120, 300).click().perform();
		Thread.sleep(2000);
		
		TnCPage tnCPage=new TnCPage(driver);
		tnCPage.switchToIframe();
		
		if(!j1.getString("shipping_policy").isEmpty())
			tnCPage.addShippingPolicyTitle(j1.getString("shipping_policy"));
		if(!j1.getString("shipping_policy_details").isEmpty())
			tnCPage.addShippingPolicyDetails(j1.getString("shipping_policy_details"));
		if(!j1.getString("refund_policy").isEmpty())
			tnCPage.addRefundPolicyTitle(j1.getString("refund_policy"));
		if(!j1.getString("refund_policy_details").isEmpty())
			tnCPage.addRefundPolicyDetails(j1.getString("refund_policy_details"));
		
		tnCPage.switchToDefaultContent();
		tnCPage.clickSave();
	}

	private void addAboutUs() throws InterruptedException{
		// TODO Auto-generated method stub
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		Thread.sleep(1000);
		pb.clickEditSite();
		Thread.sleep(1000);
		pb.clickPageDropDown();
		pb.clickEditAboutUsDropDown();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		action.moveByOffset(120, 300).click().perform();
		Thread.sleep(2000);
		
		AboutUsPage aboutUsPage=new AboutUsPage(driver);
		aboutUsPage.switchToIframe();
		aboutUsPage.addAboutUs(j1.getString("about_us"));
		Thread.sleep(2000);
		
		aboutUsPage.switchToDefaultContent();
		aboutUsPage.clickSave();
	}

	private void enablePayments() throws InterruptedException {
		// TODO Auto-generated method stub
		PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage(driver);
		if(!j1.getString("dom_pmt_offline_instructions").isEmpty()) {
		paymentOptionsPage.addOfflineInstructionForDomesticPayment(j1.getString("dom_pmt_offline_instructions"));
		paymentOptionsPage.clickSaveAndEnablePayment_Domestic();
		Thread.sleep(5000);
		}
		if(!j1.getString("intl_pmt_offline_instructions").isEmpty()) {
			paymentOptionsPage.addOfflineInstructionForInternationalPayment(j1.getString("intl_pmt_offline_instructions"));
			paymentOptionsPage.clickSaveAndEnablePayment_International();
			Thread.sleep(5000);
		}
		Navigation.clickNext(driver);
		Thread.sleep(5000);
		Navigation.clickNext(driver);
		Thread.sleep(5000);
		System.out.println("----------------- Payment Details -----------------------");
		System.out.println("Offline Domestic payment instructions: "+j1.getString("dom_pmt_offline_instructions")+"\nOffline International payment instructions: "+j1.getString("intl_pmt_offline_instructions"));
	}

	private void publishSite() {
		// TODO Auto-generated method stub
		ReadyToPublishPage readyToPublishPage = new ReadyToPublishPage(driver);
		readyToPublishPage.clickPublishSite();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		System.out.println("Created a shop with domain: "+driver.getCurrentUrl().toString()+"-------------------------------\n*******************************************************");
	}

	private void addCustomDomain() throws InterruptedException{
		// TODO Auto-generated method stub
		DomainNamePage domainNamePage = new DomainNamePage(driver);
		Thread.sleep(3000);
		if(j1.getString("domain_name").isEmpty())
			domainNamePage.confirmDomainName();
		else {
			domainNamePage.addDomainName(j1.getString("domain_name").toLowerCase());
			Thread.sleep(3000);
			domainNamePage.confirmDomainName();
		}
		Thread.sleep(3000);
	}

	private void addFirstProduct() throws InterruptedException{
		// TODO Auto-generated method stub
		Products products = new Products(driver);
		products.clickProductsOption();
		Thread.sleep(3000);
		products.addProductsName(j1.getString("product_name"));
		products.addOriginalPrice(j1.getString("product_original_price"));
		products.addSellingPrice(j1.getString("product_selling_price"));
		products.addTotalStockQty(j1.getString("product_stock"));
		products.addCategory(j1.getString("product_category"));
		Thread.sleep(3000);
		products.clickSave();
		Thread.sleep(3000);
		products.clickSkipEditing();
		Thread.sleep(3000);
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+j1.getString("product_name")+"\nProduct Category: "+j1.getString("product_category")+"\nOriginal price: "+j1.getString("product_original_price")+"\nSelling Price: "+j1.getString("product_selling_price")+"Stock: "+j1.getString("product_stock") );
	}

	private void setBusinessInfo()throws InterruptedException {
		// TODO Auto-generated method stub
		//set Business info
			BusinessInfoDetails businessInfoDetails=new BusinessInfoDetails();
			businessInfoDetails.setBusinessInfoDetails(j1.getString("first_name"), j1.getString("last_name"), j1.getString("store_name"), j1.getString("street"), j1.getString("city"), j1.getString("district"), j1.getString("state"), j1.getString("pincode"), j1.getString("po_number"));
			businessInfoDetails.printBusinessInfoDetails();
			
		// add basic details	
		BusinessInfo businessInfo= new BusinessInfo(driver);
		businessInfo.addFirstName(businessInfoDetails.getFname());
		businessInfo.addLastName(businessInfoDetails.getLname());
		businessInfo.addStoreName(businessInfoDetails.getStoreName());
		businessInfo.clickNext();
		Thread.sleep(5000);
		// more details
		businessInfo.clickBusinessAddressSearchButton();
		Thread.sleep(1000);
		
		switch(region) {
			case "in":
				businessInfo.addBusinessStreet(businessInfoDetails.getStreet());
				businessInfo.addBusinessCity(businessInfoDetails.getCity());
				businessInfo.addBusinessState(businessInfoDetails.getState());
				businessInfo.addBusiness_Pincode(businessInfoDetails.getPincode());
				businessInfo.clickBusinessFormAddressNextButton();
				break;
				
			case "hk":
				businessInfo.addBusinessDistrict(businessInfoDetails.getDistrict());
				businessInfo.clickBusinessFormAddressNextButton_sg();
				break;
				
			case "sg":
				businessInfo.addBusinessStreet(businessInfoDetails.getStreet());
				businessInfo.addBusiness_Pincode(businessInfoDetails.getPincode());
				businessInfo.clickBusinessFormAddressNextButton_sg();
				break;
				
			case "ni":
				businessInfo.addBusinessStreet(businessInfoDetails.getStreet());
				businessInfo.addBusinessCity(businessInfoDetails.getCity());
				businessInfo.addBusinessPONumber(businessInfoDetails.getPonumber());
				businessInfo.clickBusinessFormAddressNextButton();
		}
		Thread.sleep(5000);
		
	}

	private void signup() throws InterruptedException{
		//Signup page 
				SignUp s = new SignUp(driver);
				s.clickSignUpGetStarted();
				Thread.sleep(5000);
				if(url.contentEquals("https://shpmtc.me") || url.contentEquals("https://myshopmatic.com")) {
					if(region.contentEquals("hk") || region.contentEquals("sg")) {
						String currentUrl=driver.getCurrentUrl();
						currentUrl = currentUrl.substring(0, currentUrl.length()-2);
						currentUrl = currentUrl+region;
						driver.navigate().to(currentUrl);
						Thread.sleep(5000);
					}
					
				}
				
						
				//choose category
				ChooseCategory chooseCategory=new ChooseCategory(driver);
				chooseCategory.chooseCategoryMethod(j1.getString("template_category"));
				
				//set company authentication details
				CompanyAuthenticationDetails companyAuthenticationDetails=new CompanyAuthenticationDetails();
				companyAuthenticationDetails.setCompanyAuthenticationDetails(j1.getString("email"),j1.getString("password") , j1.getString("phone"));
				companyAuthenticationDetails.printCompanyAuthenticationDetails();
				//Sign up with company authentication details
				SignUpWindow signUpWindow= new SignUpWindow(driver);
				signUpWindow.addEmail(companyAuthenticationDetails.getEmail());
				signUpWindow.addPassword(companyAuthenticationDetails.getPassword());
				signUpWindow.addPhone(companyAuthenticationDetails.getPhone());
				signUpWindow.addEnvPassCode();
				signUpWindow.clickSubmit();
				Thread.sleep(5000);
				
	}
	
	@DataProvider(name = "sheetData")
	public Object[][] dataProviderMethod() throws Exception
	{
		ArrayList<Object[]> testCases = new ArrayList<Object[]>();
		currentHeaders = bookReader.getSheetHeaders(sheet);
		currentSheetData = bookReader.readSheet(sheet);
		
		for(int rowIndex = 1; rowIndex < currentSheetData.size(); rowIndex++)
		{
			Object[] testcase = new Object[2];
			testcase[0] = currentHeaders;
			testcase[1] = currentSheetData.get(rowIndex);
			
			testCases.add(testcase);
		}

		Object[][] testcasesAsArray = testCases.toArray(new Object[testCases.size()][5]);
		return testcasesAsArray;
	}
	

}
