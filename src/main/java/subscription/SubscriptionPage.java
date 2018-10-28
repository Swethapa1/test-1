package subscription;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SubscriptionPage {
	WebDriver driver;

	public SubscriptionPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	String fname="//input[@name='billing_form[first_name]']";
	String lname="//input[@name='billing_form[last_name]']";
	String mobile_number="//input[@name='billing_form[phone]']";
	String cc_no="//input[@id='credit-card-number']";
	String expiry_date="//input[@id='expiration']";
	String cvv="//input[@class='cvv']";
	String subscribe="//input[@name='commit']";
	
	public void addFirstName(String name) {
		driver.findElement(By.xpath(fname)).sendKeys(""+name);
	}
	
	public void addLastName(String name) {
		driver.findElement(By.xpath(lname)).sendKeys(""+name);
	}

	public void addMobileNumber(String num) {
		driver.findElement(By.xpath(mobile_number)).sendKeys(""+num);
	}
	
	public void addCreditCardNumber(String num) {
		driver.findElement(By.xpath(cc_no)).sendKeys(""+num);
	}
	
	public void addExpiryDate(String date) {
		driver.findElement(By.xpath(expiry_date)).sendKeys(""+date);
	}
	
	public void addCvv(String cvv1) {
		driver.findElement(By.xpath(cvv)).sendKeys(""+cvv1);
	}
	
	public void clickSubscribeNow() {
		driver.findElement(By.xpath(subscribe)).click();
	}
}
