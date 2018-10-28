package utils;

import java.util.ArrayList;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;

import login.LoginPage;
import profile.MyProfile;
import Navigation.Navigation;
import payments.NeoPage;
import SignUp.BusinessInfo;
import SignUp.SignUp;
import SignUp.SignUpWindow;
import Website.HomePage;
import Website.ProductShippingDetails;
import Website.WebPayments;
import customers.Customers;
import domain.Domain;
import domain.DomainNamePage;
import marketing.Discounts;
import marketing.Marketplaces;
import marketing.Seo;
import marketing.ShoppingChannels;
import orders.Orders;
import pagebuilder.PageBuilderPage;
import payments.CitrusPage;
import payments.PaymentOptionsPage;
import payments.PaytmPage;
import products.Products;
import publish.ReadyToPublishPage;
import setup.Store;
import setup.TaxesPage;
import shipping.Aramex;
import shipping.ShippingOptionsPage;
import shipping.Shipyaari;
import subscription.AesubscriptionPage;
import subscription.HksubscriptionPage;
import subscription.InsubscriptionPage;
import subscription.SgsubscriptionPage;
import subscription.SubscriptionPage;

public class TestUtils {
	
	WebDriver driver;
	
	
	public TestUtils(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void viewSite() throws InterruptedException{
		ReadyToPublishPage r = new ReadyToPublishPage(driver);
		r.clickViewSite();
		Thread.sleep(5000);
	}

	public void login(String email, String password, String baseurl) throws InterruptedException {
		LoginPage loginPage =  new LoginPage(driver);
		loginPage.addEmail(email.toLowerCase());
		loginPage.addPassword(password);
		
		if(baseurl.contains("shpmtc") || baseurl.contains("shopmakerapp") || baseurl.contains("beta"))
			loginPage.addEnvPassCode();
		loginPage.clickLogin();
		Thread.sleep(2000);
		Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Failed to login");
		System.out.println("\n Logged in. Login Details - email: "+email+" password: "+password);	
	}
	
	public void signup(String region, String url, String template_category) throws InterruptedException
	{
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
				chooseCategory.chooseCategoryMethod(template_category);
				
				String email="testshop_"+region+RandomStringUtils.randomAlphanumeric(5)+"@testshop.com";
				String password="Abcd1234";
				String phone="";
				switch(region.toLowerCase())
				{
					case "in": 
						phone="1111111111";
						break;
					case "hk":
						phone="12345678";
						break;
					case "sg":
						phone="12345678";
						break;
					case "ni":
						phone="123456789";
						break;
				}				
				//set company authentication details
				CompanyAuthenticationDetails companyAuthenticationDetails=new CompanyAuthenticationDetails();
				companyAuthenticationDetails.setCompanyAuthenticationDetails(email,password , phone);
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

	public void setBusinessInfo(String region)throws InterruptedException {
		// TODO Auto-generated method stub
		String fname=RandomStringUtils.randomAlphabetic(6),lname=RandomStringUtils.randomAlphabetic(4),store_name=RandomStringUtils.randomAlphabetic(6);
		String street=RandomStringUtils.randomAlphabetic(6), city=RandomStringUtils.randomAlphabetic(6),district=RandomStringUtils.randomAlphabetic(6);
		String state=RandomStringUtils.randomAlphabetic(6);
		String po_number="1234";
		String pincode="581115";
		
		//set Business info
			BusinessInfoDetails businessInfoDetails=new BusinessInfoDetails();
			businessInfoDetails.setBusinessInfoDetails(fname, lname, store_name, street, city, district, state, pincode, po_number);
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

	public void addFirstProduct(String pname,String o_price,String s_price,String desc,String stock,String category) throws InterruptedException{
		// TODO Auto-generated method stub
		Products products = new Products(driver);
		products.clickProductsOption();
		Thread.sleep(3000);
		products.addProductsName(pname);
		products.addProductDescription(desc);
		products.addOriginalPrice(o_price);
		products.addSellingPrice(s_price);
		products.addTotalStockQty(stock);
		products.addCategory(category);
		Thread.sleep(3000);
		products.clickSave();
		Thread.sleep(3000);
		products.clickSkipEditing();
		Thread.sleep(3000);
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+pname+"\nProduct Description:"+desc+"\nProduct Category: "+category+"\nOriginal price: "+o_price+"\nSelling Price: "+s_price+"Stock: "+stock );
		System.out.println("\n---------------------------------------------");
	}
	
	public void addProductWithDetails(String pname,String o_price,String s_price,String desc,String stock,String category)throws InterruptedException{
		// TODO Auto-generated method stub
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickProducts();
		Thread.sleep(2000);
		
		Products products = new Products(driver);
		products.clickAddProduct();		
		Thread.sleep(3000);
		products.addProductsName(pname);
		products.addProductDescription(desc);
		products.addOriginalPrice(o_price);
		products.addSellingPrice(s_price);
		products.addTotalStockQty(stock);
		products.addCategory(category);
		Thread.sleep(3000);
		products.clickSave();
		Thread.sleep(3000);
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+pname+"\nProduct Description:"+desc+"\nProduct Category: "+category+"\nOriginal price: "+o_price+"\nSelling Price: "+s_price+"Stock: "+stock );
		System.out.println("\n---------------------------------------------");
	}

	public void addProductWithShippingDetails(String pname,String o_price,String s_price,String desc,String stock,String category, String length, String height, String width,String totalWeight) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickProducts();
		Thread.sleep(2000);
		
		Products products = new Products(driver);
		products.clickAddProduct();		
		Thread.sleep(3000);
		products.addProductsName(pname);
		products.addProductDescription(desc);
		products.addOriginalPrice(o_price);
		products.addSellingPrice(s_price);
		products.addTotalStockQty(stock);
		products.addCategory(category);
		Thread.sleep(3000);
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+pname+"\nProduct Description:"+desc+"\nProduct Category: "+category+"\nOriginal price: "+o_price+"\nSelling Price: "+s_price+"Stock: "+stock );
		products.clickMoreSettings();
		Thread.sleep(1000);
		products.addLength(length);
		products.addHeight(height);
		products.addWidth(width);
		products.addTotalWeight(totalWeight);
		
		products.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(products.isProductPresent(pname), "Product is not present in the list");
		System.out.println("\n Length: "+length+"\n Height: "+height+"\nWidth: "+width+"\nTotal Weight: "+totalWeight);
		System.out.println("\n---------------------------------------------");
	}
	
	public void addProductWithShippingDetailsAndInventory(String pname,String o_price,String s_price,String desc,String stock,String category, String length, String height, String width,String totalWeight,String inventorySKU, String upc) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickProducts();
		Thread.sleep(2000);
		
		Products products = new Products(driver);
		products.clickAddProduct();		
		Thread.sleep(3000);
		products.addProductsName(pname);
		products.addProductDescription(desc);
		products.addOriginalPrice(o_price);
		products.addSellingPrice(s_price);
		products.addTotalStockQty(stock);
		products.addCategory(category);
		Thread.sleep(3000);
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+pname+"\nProduct Description:"+desc+"\nProduct Category: "+category+"\nOriginal price: "+o_price+"\nSelling Price: "+s_price+"Stock: "+stock );
		products.clickMoreSettings();
		Thread.sleep(1000);
		products.addLength(length);
		products.addHeight(height);
		products.addWidth(width);
		products.addTotalWeight(totalWeight);
		products.addInventorySKU(inventorySKU);
		products.addUpc(upc);
		
		products.clickSave();
		Thread.sleep(5000);
		Assert.assertTrue(products.isProductPresent(pname), "Product is not present in the list");
		System.out.println("\nInventory SKU: "+inventorySKU+"\nUPC: "+upc);
		System.out.println("\n---------------------------------------------");
	}
	
	public void addProductBasicDetails(String pname,String s_price) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickProducts();
		Thread.sleep(2000);
		
		Products products = new Products(driver);
		products.clickAddProduct();
		
		Thread.sleep(3000);
		products.addProductsName(pname);
		products.addSellingPrice(s_price);
		Thread.sleep(3000);
		products.clickSave();
		Thread.sleep(5000);
		Assert.assertTrue(products.isProductPresent(pname), "Product is not present in the list");
		System.out.println("----------------Product Details----------------");
		System.out.println("Product Name: "+pname+"\nSelling Price: "+s_price);
		System.out.println("\n---------------------------------------------");
	}

	public void enabledisableProductInStore(String pname) throws InterruptedException {
		Products products = new Products(driver);
		
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.clickProducts();
		Thread.sleep(1000);
		products.clickAllProduct();
		products.selectProduct(pname);
		Thread.sleep(2000);
		products.clickMoreSettings();
		Thread.sleep(1000);
		products.enableShowPrdInStore();
		products.clickSave();
	}
	
	public void enabledisableProductpurchaseWhenOOS(String pname) throws InterruptedException {
		Products products = new Products(driver);
		
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.clickProducts();
		Thread.sleep(1000);
		products.clickAllProduct();
		Thread.sleep(2000);
		products.selectProduct(pname);
		Thread.sleep(2000);
		products.clickMoreSettings();
		Thread.sleep(1000);
		products.enableAllowPurchaseOOS();
		products.clickSave();
	}
	
	public void printListOfProducts()throws InterruptedException {
		Products products = new Products(driver);
		
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.clickProducts();
		Thread.sleep(5000);
		ArrayList<String> listOfProducts=products.listOfProducts();
		System.out.println("\n---------------- List of Products --------------------\n");
		for(int i=0;i<listOfProducts.size();i++) {
			System.out.println(listOfProducts.get(i).toString());
		}
		System.out.println("\n-------------------------------------------------------\n");
	}
	
	public void deleteProduct(String pname)throws InterruptedException{
		Products products = new Products(driver);
		
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.clickProducts();
		Thread.sleep(1000);
		products.clickAllProduct();
		products.selectProduct(pname);
		Thread.sleep(2000);
		products.deleteProduct(pname);
		Thread.sleep(3000);
		Assert.assertFalse(products.isProductPresent(pname));		
	}
	
	public void addCustomDomain() throws InterruptedException{
		// TODO Auto-generated method stub
		DomainNamePage domainNamePage = new DomainNamePage(driver);
		domainNamePage.confirmDomainName();
		Thread.sleep(3000);
	}

	public void publishSite() {
		// TODO Auto-generated method stub
		ReadyToPublishPage readyToPublishPage = new ReadyToPublishPage(driver);
		readyToPublishPage.clickPublishSite();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		System.out.println("Created a shop with domain: "+driver.getCurrentUrl().toString()+"-------------------------------\n*******************************************************");
	}

	public void enableOfflinePayment(String instructions, String mode) throws InterruptedException {
		// TODO Auto-generated method stub
		PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage(driver);
		if(mode.equalsIgnoreCase("domestic")) {
		paymentOptionsPage.addOfflineInstructionForDomesticPayment(instructions);
		paymentOptionsPage.clickSaveAndEnablePayment_Domestic();
		Thread.sleep(5000);
		}
		else
		{
			paymentOptionsPage.addOfflineInstructionForInternationalPayment(instructions);
			paymentOptionsPage.clickSaveAndEnablePayment_International();
			Thread.sleep(5000);
		}
		Navigation.clickNext(driver);
		Thread.sleep(5000);
		Navigation.clickNext(driver);
		Thread.sleep(5000);
		}

	public void enableCOD() throws InterruptedException {
		PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage(driver);
		paymentOptionsPage.enableCODPayment();
	    Thread.sleep(5000);
		
	}

	public void enableCitrusPay()throws InterruptedException {
		PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage(driver);
		paymentOptionsPage.enableCitrusPayment();
		
		CitrusPage  citrusPage= new CitrusPage(driver);
		citrusPage.clickAccount();
		Thread.sleep(5000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	    citrusPage.addChkoutURL();
	    citrusPage.addAccessKey();
	    citrusPage.addSecretKey();
	    citrusPage.clickSave();
	    Thread.sleep(5000);
	}

	public void enableNeo()throws InterruptedException {
		PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage(driver);
		paymentOptionsPage.enableNeoPayment();
		
		NeoPage neoPage=new NeoPage(driver);
		neoPage.clickMerchantId();
		neoPage.clickSecretKey();
		neoPage.clickSubmit();
		Thread.sleep(10000);
	}

	public void enablePaytm()throws InterruptedException{
		PaytmPage paytmpage= new PaytmPage(driver);
		paytmpage.clickEnable();
		Thread.sleep(2000);
		paytmpage.addMerchantId();
		paytmpage.addIndustryTypeId();
		paytmpage.addMerchantKey();
		paytmpage.addMerchantWebsite();
		paytmpage.clickSave();
		Thread.sleep(3000);
	}

	public void disablePaytm() {
		PaytmPage paytmpage= new PaytmPage(driver);
		paytmpage.clickDisable();
	}

	public void enableAramex(String mode) throws InterruptedException {
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		if(mode.equalsIgnoreCase("domestic"))
			shippingOptionsPage.clickAramex_Domestic();
		else
		{
			shippingOptionsPage.clickInternatioanlShippingTab();
			shippingOptionsPage.clickAramex_International();
		}
		 Thread.sleep(3000);
		 
		 Aramex aramex=new Aramex(driver);
		 aramex.clickAccount();
		 Thread.sleep(3000);
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		 driver.switchTo().window(tabs.get(1));
		 driver.close();
		 driver.switchTo().window(tabs.get(0));
		 aramex.addUsername();
		 aramex.addPassword();
		 aramex.addAccountNumber();
		 aramex.addAccountPin();
		 aramex.selectAramexEntity();
		 aramex.clickUpdate();
		 Thread.sleep(5000);
		 Assert.assertTrue(shippingOptionsPage.getMessage().contains("saved"), "Failed to enable aramex");
	}

	public void enableEasyShip(String mode) throws InterruptedException {
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		if(mode.equalsIgnoreCase("domestic"))
			shippingOptionsPage.clickEasyShip_Domestic();
		else
			shippingOptionsPage.clickInternatioanlShippingTab();
			shippingOptionsPage.clickEasyShip_International();
		 Thread.sleep(3000);
		 Assert.assertTrue(shippingOptionsPage.getMessage().contains("saved"), "Failed to enable easyship");
	}
	
	public void enableShipyaari(String mode) throws InterruptedException {
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		shippingOptionsPage.clickShipyaari_Domestic();
		Thread.sleep(3000);
		
		Shipyaari shipyaari=new Shipyaari(driver);
		shipyaari.clickAccount();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		 driver.switchTo().window(tabs.get(1));
		 driver.close();
		 driver.switchTo().window(tabs.get(0));
		shipyaari.addClientId();
		shipyaari.addUsername();
		shipyaari.clickUpdate();
		Thread.sleep(3000);	
		Assert.assertTrue(shippingOptionsPage.getMessage().contains("saved"), "Failed to enable shipyaari");
	}
	
	public void enableSelfCollect(String details)throws InterruptedException{
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		shippingOptionsPage.addSelfCollectDetails(details);
		shippingOptionsPage.clickSave();
		shippingOptionsPage.clickEnableSelfCollect();
		Thread.sleep(2000);
		Assert.assertTrue(shippingOptionsPage.getMessage().contains("saved"), "Failed to enable self collect");
	}
	
	public void enableOwnShipping(String mode)throws InterruptedException{
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		if(mode.equalsIgnoreCase("domestic"))
			shippingOptionsPage.clickOwnShipping_Domestic();
		else {
			shippingOptionsPage.clickInternatioanlShippingTab();
			shippingOptionsPage.clickOwnShipping_International();}
		shippingOptionsPage.clickConfirm();
		Thread.sleep(2000);
		Assert.assertTrue(shippingOptionsPage.getMessage().contains("saved"), "Failed to enable own shipping");

	}

	public void enableFlatShippingFeePerOrder(String mode, String fee)throws InterruptedException{
		ShippingOptionsPage shippingOptionsPage= new ShippingOptionsPage(driver);
		if(mode.equalsIgnoreCase("domestic"))
			shippingOptionsPage.addFlatShippingFeeCharges_domestic(fee);
		else {
			shippingOptionsPage.clickInternatioanlShippingTab();
			shippingOptionsPage.addFlatShippingFeeCharges_international(fee);
		}
		shippingOptionsPage.clickSave();		
	}

	public void addSeoHomePage() throws InterruptedException{
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectMarketing();
		Thread.sleep(1000);
		pageBuilderPage.clickSEO();
		Thread.sleep(1000);
		Seo seo = new Seo(driver);
		
		seo.selectTitleAndMetaDescriptionForPagesDropdown("FAQ");
		Thread.sleep(1000);
		seo.addTitleAndMetaDescriptionForPagesDescription("Pages Description");
		seo.addTitleAndMetaDescriptionForPagesTitle("Title");
		seo.clickSaveTitleAndMetaDescriptionForPages();
		Thread.sleep(2000);
		AssertJUnit.assertTrue(seo.getMessage().contentEquals("Title and description save successfully."));
	}

	public void addDiscount(String type,String name, String code,String amount, String sdate, String edate)throws InterruptedException{
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		Thread.sleep(2000);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectMarketing();
		pageBuilderPage.clickDiscounts();
		
		Discounts discounts = new Discounts(driver);
		discounts.clickAddDiscount();
		discounts.addDiscountName(name);
		discounts.addDiscountCode(code);
		
		if(type.equalsIgnoreCase("free shipping discount")) {
			discounts.clickradioButtonFreeShippingDiscount();
			discounts.addminOrderDiscount(amount);
		}
		else if(type.equalsIgnoreCase("percentage discount")) {
			discounts.clickradioButtonPercentageDiscount();
			discounts.addFlatDiscountPercentage(amount);
		}
		else 
		{
			discounts.addFlatDiscountAmount(amount);
		}
		discounts.addDiscountStartDate(sdate);
		discounts.addDiscountEndDate(edate);
		discounts.clickAddADiscountButton();
		Thread.sleep(2000);
		AssertJUnit.assertTrue(discounts.checkDiscountCreated().contentEquals("You have successfully created Discount"+" "+name+"."));
		
	}

	public void enableFaceBook() throws InterruptedException{
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectMarketing();
		Thread.sleep(1000);
		pageBuilderPage.clickShoppingChannels();
		Thread.sleep(1000);
		
		ShoppingChannels shoppingChannels= new ShoppingChannels(driver);
		shoppingChannels.clickEnableFaceBook();
		Thread.sleep(3000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
		Assert.assertTrue(driver.getCurrentUrl().contains("facebook"), "Facebook page is not opening");
	}

	public void enableMarketPlace(String name, String region)throws InterruptedException{
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectMarketing();
		Thread.sleep(1000);
		pageBuilderPage.clickMarketPlaces();
		Thread.sleep(1000);
		
		Marketplaces marketplaces=new Marketplaces(driver);
		switch(name.toLowerCase())
		{
			case "ebay":
				if(region.equals("in"))
					marketplaces.clickEnableMarketPlaces_ebay_in();
				else
					marketplaces.clickEnableMarketPlaces_ebay_us();
				System.out.println(driver.getCurrentUrl());
				Assert.assertTrue(driver.getCurrentUrl().contains("ebay"), "Unable to enable ebay marketplace");
				break;
		
			case "amazon":
				if(region.equals("in"))
					marketplaces.clickEnableMarketPlaces_amazon_in();
				else
					marketplaces.clickEnableMarketPlaces_amazon_us();
				Assert.assertTrue(driver.getCurrentUrl().contains("amazon"), "Unable to enable amazon marketplace");
				break;
				
			case "e-lala":
				marketplaces.clickEnableMarketPlaces_elala();
				Assert.assertTrue(driver.getCurrentUrl().contains("elala"), "Unable to enable elala");
				break;
				
			case "lazada":
				marketplaces.clickEnableMarketPlaces_lazada();
				Assert.assertTrue(driver.getCurrentUrl().contains("lazada"), "Unable to enable lazada");
				break;
				
			case "qoo10":
				marketplaces.clickEnableMarketPlaces_qoo10();
				Assert.assertEquals(driver.getCurrentUrl().contains("qoo10"), "Unable to enable qoo10");
				break;
		}
	}

	public void enableTaxes(String tax, String perc, String num, Boolean registered) throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.selectSetUp();
		Thread.sleep(1000);
		pageBuilderPage.clickTaxes();
		Thread.sleep(2000);
		
		TaxesPage taxes=new TaxesPage(driver);
		
		switch(tax.toLowerCase()) 
		{
			case "gst": 
				if(registered) 
				{
					taxes.clickGSTRegisteredYesCheckbox();
					taxes.addGstNum(num);
					if(!perc.contains(""))
						taxes.addGstRate(perc);
				}	
				else
					taxes.clickGSTRegisteredNoCheckbox();
				taxes.clickSaveChanges();
				Assert.assertEquals(taxes.getMessage(), "Your changes have been saved.");
				break;
				
			case "vat":
				break;
		}
	}

	public void enableStore(String option) throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectSetUp();
		Thread.sleep(1000);
		pageBuilderPage.clickStore();
		Thread.sleep(1000);
		
		Store store=new Store(driver);
		
		switch(option.toLowerCase()) 
		{
			case "product":
				store.clickProduct();
				store.clickEnableCommerceWithSelectedOption();
				break;
				
			case "product and payment":
				store.clickProductAndPayment();
				store.clickEnableCommerceWithSelectedOption();
				break;
				
			case "product,payment and shipping":
				store.clickProductPaymentAndShipping();
				store.clickEnableCommerceWithSelectedOption();
				break;
		}
		Thread.sleep(5000);
	}

	public void markAsADeliveredForOrder(String order_id) throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickOrders();
		
		Orders orders=new Orders(driver);
		orders.selectActionsForOrder(order_id);
		orders.clickMarkAsDeliveredForOrder(order_id);
		orders.clickConfirm();
		Thread.sleep(5000);
		AssertJUnit.assertTrue(orders.getMessage().contentEquals("Order #"+order_id+" is marked as delivered"));
	}
	
	public void printInvoiceForOrder(String order_id)throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickOrders();
		
		Orders orders=new Orders(driver);
		orders.selectActionsForOrder(order_id);
		orders.clickPrintInvoiceForOrder(order_id);
		Thread.sleep(5000);
	}
	
	public void refundOrder(String order_id)throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickOrders();
		
		Orders orders=new Orders(driver);
		orders.selectActionsForOrder(order_id);
		orders.clickRefundForOrder(order_id);
	}
	
	public void cancelOrder(String order_id)throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		Thread.sleep(200);
		pageBuilderPage.clickOrders();
		Thread.sleep(2000);
		
		Orders orders=new Orders(driver);
		orders.selectActionsForOrder(order_id);
		orders.clickCancelOrder(order_id);
		orders.clickConfirm();
		Thread.sleep(5000);
		AssertJUnit.assertTrue(orders.getMessage().contentEquals("Order #"+order_id+" is cancelled"));
		System.out.println("Cancelled order: "+order_id+"\n");
	}

	ProductShippingDetails productShippingDetails ;
	public String createOrder(String cust_email,String cust_fname,String cust_lname, String cust_street, String cust_pincode, String cust_phone, Boolean SaveInfoFuturePurposes,String shipping_method,String pmt_mode, String district,String state,String po_num, String country) throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
		HomePage homePage=new HomePage(driver);
	    homePage.clickAllProducts();
	    homePage.clickSelectProduct(1);	    
	    Thread.sleep(3000);
	    return addProductShippingDetails(cust_email,  cust_fname,  cust_lname,   cust_street,   cust_pincode,   cust_phone,SaveInfoFuturePurposes,  shipping_method,  pmt_mode, district, state, po_num,country);
	}
	
	public String addProductShippingDetails(String cust_email,String cust_fname,String cust_lname, String cust_street, String cust_pincode, String cust_phone, Boolean SaveInfoFuturePurposes,String shipping_method,String pmt_mode, String district, String state, String po_num,String country)throws InterruptedException {
		productShippingDetails = new ProductShippingDetails(driver);
	    productShippingDetails.clickAddToCart();
	    Thread.sleep(3000);
	    productShippingDetails.clickCheckout();
	    productShippingDetails.addEmailToCart(cust_email);
	    productShippingDetails.clickContinue();
	    productShippingDetails.addFirstName(cust_fname);
	    productShippingDetails.addLastName(cust_lname);
	    productShippingDetails.addStreetAddr(cust_street);
	    
	    if(!country.isEmpty())
	    	productShippingDetails.addCountry(country);
	    if(!cust_pincode.isEmpty())
	    	productShippingDetails.addPostCode(cust_pincode);
	    if(!district.isEmpty())
	    	productShippingDetails.addDistrict(district);
	    if(!state.isEmpty())
	    	productShippingDetails.addState(state);
	    if(!po_num.isEmpty())
	    	productShippingDetails.addPostCode(po_num);
	    productShippingDetails.addPhone(cust_phone);
	    Thread.sleep(2000);
	    
	    if(SaveInfoFuturePurposes) {
	    	productShippingDetails.clickSaveInfoFuturePurposes();
		    Thread.sleep(2000);
		    productShippingDetails.addPassword("Tester123");
		    System.out.println("\n-------------------------- Customer Login info -----------------------");
		    System.out.println("\n Customer Login Id: "+cust_email+"\n Customer Login password: "+"Tester123");
		    System.out.println("\n-----------------------------------------------------------------------\n");
	    }
	    productShippingDetails.clickShippingContinue();
	    Thread.sleep(2000);
	    productShippingDetails.selectShippingMethod(shipping_method);
	    productShippingDetails.clickShippingContinue();
	    Thread.sleep(2000);
	    
	    WebPayments webPayments=new WebPayments(driver);
	    switch(pmt_mode.toLowerCase()) {
	    	case "cod":
	    		webPayments.choosePaymentMethod("CASH ON DELIVERY");
	    	    Thread.sleep(3000);
	    	    webPayments.clickPlaceOrder();
	    	    Thread.sleep(5000);
	    	    break;
	    	    
	    }
	    return productShippingDetails.getOrderID();
	}

	public void enableAllowCustomerOrderNote()throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		Thread.sleep(200);
		pageBuilderPage.clickOrders();
		Thread.sleep(2000);
		
		Orders orders=new Orders(driver);
		orders.clickOrderNote();
	}
	
	public void findOrder(String text)throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		Thread.sleep(200);
		pageBuilderPage.clickOrders();
		Thread.sleep(2000);
		
		Orders orders=new Orders(driver);
		orders.searchOrder(text);
		Assert.assertFalse(orders.getNoOrdersText().contains("You have no orders yet."),"Order not found");
	}
	
	public void findDomesticOrder(String text)throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		Thread.sleep(200);
		pageBuilderPage.clickOrders();
		Thread.sleep(2000);
		
		Orders orders=new Orders(driver);
		orders.clickOrderTabs_DomesticOrders();
		Thread.sleep(200);
		orders.searchOrder(text);
		Assert.assertFalse(orders.getNoOrdersText().contains("You have no orders yet."),"Order not found");
	}
	
	public void findInternationalOrder(String text)throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		Thread.sleep(200);
		pageBuilderPage.clickOrders();
		Thread.sleep(2000);
		
		Orders orders=new Orders(driver);
		orders.clickOrderTabs_InternationalOrders();
		Thread.sleep(200);
		orders.searchOrder(text);
		Assert.assertFalse(orders.getNoOrdersText().contains("You have no orders yet."),"Order not found");
	}

	public void checkCustomerWithSearchFunctionality(String name, Boolean optedMarketing) throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickCustomers();
		Thread.sleep(2000);
		
		Customers cust=new Customers(driver);
		if(optedMarketing)
			cust.clickOptedMarketing();
		Assert.assertTrue(cust.findCustomer(name),"Customer not found");
	}
	
	public void checkCustomerWithoutSearchFunctionality(String name,Boolean optedMarketing) throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickCustomers();
		Thread.sleep(2000);
		
		Customers cust=new Customers(driver);
		if(optedMarketing)
			cust.clickOptedMarketing();
		Assert.assertTrue(cust.findCustomer(name),"Customer not found");
	}
	
	public void findCustomer(String text)throws InterruptedException {
		PageBuilderPage pb=new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.clickCustomers();
		Thread.sleep(2000);
		
		Customers cust=new Customers(driver);
		cust.searchCustomer(text);
		cust.clickSearchButton();
		Thread.sleep(1000);
		Assert.assertTrue(cust.findCustomer(text),"Customer not found");
	}

	public void addSeo(String text)throws InterruptedException {
		PageBuilderPage pageBuilderPage = new PageBuilderPage(driver);
		pageBuilderPage.clickDashboard();
		pageBuilderPage.selectMarketing();
		Thread.sleep(1000);
		pageBuilderPage.clickSEO();
		Thread.sleep(1000);
		Seo seo = new Seo(driver);
		
		seo.selectTitleAndMetaDescriptionForPagesDropdown("FAQ");
		Thread.sleep(1000);
		seo.addTitleAndMetaDescriptionForPagesDescription("Pages Description");
		seo.addTitleAndMetaDescriptionForPagesTitle("Title");
		seo.clickSaveTitleAndMetaDescriptionForPages();
		Thread.sleep(2000);
		AssertJUnit.assertTrue(seo.getMessage().contentEquals("Title and description save successfully."));
	}

	public String buyNewDomain()throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickBuyNewDomain();
		String dom=RandomStringUtils.randomAlphabetic(5)+".com";
		domain.addDomain(dom);
		domain.clickSearch();
		return dom;
	}
	
	public void changePrimaryDomain(String dom)throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.changePrimaryDomain(dom);
		domain.clickSave();
	}
	
	public String connectToACustomDomain() throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickConnectCustomDomain();
		Thread.sleep(1000);
		domain.clickOthers();
		Thread.sleep(2000);
		String dom=RandomStringUtils.randomAlphabetic(5)+".com";
		domain.addCustomDomain(dom);
		System.out.println("\nDomain name: "+dom);
		domain.clickConnectOthers();
		return dom;
	}
	
	public void disconnectCustomDomain()throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickDisconnect();
	}

	public String connectToAGoDaddyCustomDomain() throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickConnectCustomDomain();
		//domain.clickGodaddy();
		String dom=RandomStringUtils.randomAlphabetic(5)+".com";
		domain.addCustomDomain(dom);
		System.out.println("\nDomain name: "+dom);
		domain.clickConnectGodaddy();
		return dom;
	}
	
	public void disconnectGodaddyCustomDomain()throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickDisconnect();
	}

	public String connectToAnotherCustomDomain() throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectSetUp();
		Thread.sleep(2000);
		pb.clickDomain();
		Thread.sleep(2000);
		
		Domain domain= new Domain(driver);
		domain.clickConnectAnotherCustomDomain();
		
		domain.clickConnectCustomDomain();
		domain.clickOthers();
		String dom=RandomStringUtils.randomAlphabetic(5)+".com";
		domain.addCustomDomain(dom);
		System.out.println("\nAnother Domain name:"+dom);
		domain.clickConnectOthers();
		
		return dom;
	}

	public void changeCompanyAddress(String caddrs) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickMyProfile();
		Thread.sleep(2000);
		
		MyProfile myProfile=new MyProfile(driver);
		myProfile.addCompanyAddress(caddrs);
		myProfile.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(myProfile.getMessage().contains("Saved!"), "Changes are not saved");
	}

	public void changeCompanyName(String cname) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickMyProfile();
		Thread.sleep(2000);
		
		MyProfile myProfile=new MyProfile(driver);
		myProfile.addCompanyName(cname);
		myProfile.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(myProfile.getMessage().contains("Saved!"), "Changes are not saved");
	
	}

	public void changeName(String fname,String lname) throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickMyProfile();
		Thread.sleep(2000);
		
		MyProfile myProfile=new MyProfile(driver);
		myProfile.addFirstName(fname);
		myProfile.addLastName(lname);
		myProfile.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(myProfile.getMessage().contains("Saved!"), "Changes are not saved");
	}

	public void changeNotificationEmail(String email)throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickMyProfile();
		Thread.sleep(2000);
		
		MyProfile myProfile=new MyProfile(driver);
		myProfile.addNotificationEmail(email);
		myProfile.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(myProfile.getMessage().contains("Saved!"), "Changes are not saved");
	}

	public void changePhoneNumber(String phno)throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickMyProfile();
		Thread.sleep(2000);
		
		MyProfile myProfile=new MyProfile(driver);
		myProfile.addPhoneNumber(phno);
		myProfile.clickSave();
		Thread.sleep(2000);
		Assert.assertTrue(myProfile.getMessage().contains("Saved!"), "Changes are not saved");
	}

	public void enableSubscription(String region, String plan)throws InterruptedException {
		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		pb.selectProfileAndBilling();
		Thread.sleep(2000);
		pb.clickBillingAccount();
		
		AesubscriptionPage aesubscriptionPage;
		InsubscriptionPage insubscriptionPage;
		SgsubscriptionPage sgsubscriptionPage;
		HksubscriptionPage hksubscriptionPage;
		
		switch(region.toLowerCase()) {
			case "ae":
				aesubscriptionPage=new AesubscriptionPage(driver);
				Assert.assertNotNull(aesubscriptionPage.getOneMonthPlanBillAmount());
				switch(plan.toLowerCase()) {
					case "one":
						aesubscriptionPage.chooseOneMonthPlan();
						break;
					case "six":
						aesubscriptionPage.chooseSixMonthPlan();
						break;
					case "twelve":
						aesubscriptionPage.chooseTwelveMonthPlan();
						break;
				}
				subscribe();
				break;
			
			case "in":
				insubscriptionPage=new InsubscriptionPage(driver);
				switch(plan.toLowerCase()) {
					case "three":
						Assert.assertNotNull(insubscriptionPage.getPrice_3());
						Assert.assertNotNull(insubscriptionPage.getPriceCurrency_3());
						Assert.assertNotNull(insubscriptionPage.getPricePlanInfo_3());
						Assert.assertNotNull(insubscriptionPage.getTotalPrice_3());
						insubscriptionPage.choosePlan_3();
						break;
					case "seven":
						//System.out.println(insubscriptionPage.getPrice_7());
//						Assert.assertNotNull(insubscriptionPage.getPrice_7());
//						Assert.assertNotNull(insubscriptionPage.getPriceCurrency_7());
//						Assert.assertNotNull(insubscriptionPage.getPricePlanInfo_7());
//						Assert.assertNotNull(insubscriptionPage.getTotalPrice_7());
						insubscriptionPage.choosePlan_7();
						break;
					case "fifteen":
						Assert.assertNotNull(insubscriptionPage.getPrice_15());
						Assert.assertNotNull(insubscriptionPage.getPriceCurrency_15());
						Assert.assertNotNull(insubscriptionPage.getPricePlanInfo_15());
						Assert.assertNotNull(insubscriptionPage.getTotalPrice_15());
						insubscriptionPage.choosePlan_15();
						break;
				}
				subscribe();
				break;
				
			case "hk":
				hksubscriptionPage=new HksubscriptionPage(driver);
				switch(plan.toLowerCase()) {
				case "one":
					Assert.assertNotNull(hksubscriptionPage.getPrice_1());
					Assert.assertNotNull(hksubscriptionPage.getPriceCurrency_1());
					Assert.assertNotNull(hksubscriptionPage.getPricePlanInfo_1());
					Assert.assertNotNull(hksubscriptionPage.getTotalPrice_1());
					hksubscriptionPage.choosePlan_1();
					break;
				case "seven":
					Assert.assertNotNull(hksubscriptionPage.getPrice_7());
					Assert.assertNotNull(hksubscriptionPage.getPriceCurrency_7());
					Assert.assertNotNull(hksubscriptionPage.getPricePlanInfo_7());
					Assert.assertNotNull(hksubscriptionPage.getTotalPrice_7());
					hksubscriptionPage.choosePlan_7();
					break;
				case "sixteen":
					Assert.assertNotNull(hksubscriptionPage.getPrice_16());
					Assert.assertNotNull(hksubscriptionPage.getPriceCurrency_16());
					Assert.assertNotNull(hksubscriptionPage.getPricePlanInfo_16());
					Assert.assertNotNull(hksubscriptionPage.getTotalPrice_16());
					hksubscriptionPage.choosePlan_16();
					break;
				}
			  subscribe();
			  break;
			  
			case "sg":
				sgsubscriptionPage=new SgsubscriptionPage(driver);
				switch(plan.toLowerCase()) {
				case "one":
					Assert.assertNotNull(sgsubscriptionPage.getPrice_1());
					Assert.assertNotNull(sgsubscriptionPage.getPriceCurrency_1());
					Assert.assertNotNull(sgsubscriptionPage.getPricePlanInfo_1());
					Assert.assertNotNull(sgsubscriptionPage.getTotalPrice_1());
					sgsubscriptionPage.choosePlan_1();
					break;
				case "seven":
					Assert.assertNotNull(sgsubscriptionPage.getPrice_7());
					Assert.assertNotNull(sgsubscriptionPage.getPriceCurrency_7());
					Assert.assertNotNull(sgsubscriptionPage.getPricePlanInfo_7());
					Assert.assertNotNull(sgsubscriptionPage.getTotalPrice_7());
					sgsubscriptionPage.choosePlan_7();
					break;
				case "sixteen":
					Assert.assertNotNull(sgsubscriptionPage.getPrice_16());
					Assert.assertNotNull(sgsubscriptionPage.getPriceCurrency_16());
					Assert.assertNotNull(sgsubscriptionPage.getPricePlanInfo_16());
					Assert.assertNotNull(sgsubscriptionPage.getTotalPrice_16());
					sgsubscriptionPage.choosePlan_16();
					break;
			}
			subscribe();
			break;
		}
		
	}
	
	private void subscribe()throws InterruptedException {
		SubscriptionPage spage=new SubscriptionPage(driver);
		spage.addFirstName(RandomStringUtils.randomAlphabetic(5));
		spage.addLastName(RandomStringUtils.randomAlphabetic(5));
		spage.addCreditCardNumber("4111111111111111");
		spage.addExpiryDate("12/12/2022");
		spage.addCvv(RandomStringUtils.randomAlphabetic(3));
		spage.clickSubscribeNow();
	}
}
