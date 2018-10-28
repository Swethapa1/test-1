package termsandconditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TnCPage {
	WebDriver driver;

	public TnCPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	private String iframe_element="//iframe[@title='Page Editor']";
	private String refund_policy_title="element-vgwbx-block-1-h-1";
	private String refund_policy_details="element-vgwbx-block-1-p-1";
	private String shipping_policy_title="element-ugwbx-block-1-h-1";
	private String shipping_policy_details="element-ugwbx-block-1-p-1";
	private String save_btn="//div[contains(@class,'save-btn')]";
	
	
	Actions actions;
	WebElement element;
	
	public void switchToIframe() {
		driver.switchTo().frame(driver.findElement(By.xpath(iframe_element)));
	}
	
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}
	
	public void addShippingPolicyTitle(String text) {
		actions=new Actions(driver);
		element=driver.findElement(By.id(shipping_policy_title));
		driver.findElement(By.id(shipping_policy_title)).click();
		driver.findElement(By.id(shipping_policy_title)).clear();
		driver.findElement(By.id(shipping_policy_title)).sendKeys(""+text);
	}
	
	public void addShippingPolicyDetails(String text) {
		actions=new Actions(driver);
		element=driver.findElement(By.id(shipping_policy_details));
		driver.findElement(By.id(shipping_policy_details)).click();
		driver.findElement(By.id(shipping_policy_details)).clear();
		driver.findElement(By.id(shipping_policy_details)).sendKeys(""+text);
	}
	
	public void addRefundPolicyTitle(String text) {
		actions=new Actions(driver);
		element=driver.findElement(By.id(refund_policy_title));
		driver.findElement(By.id(refund_policy_title)).click();
		driver.findElement(By.id(refund_policy_title)).clear();
		driver.findElement(By.id(refund_policy_title)).sendKeys(""+text);
	}
	public void addRefundPolicyDetails(String text) {
		actions=new Actions(driver);
		element=driver.findElement(By.id(refund_policy_details));
		driver.findElement(By.id(refund_policy_details)).click();
		driver.findElement(By.id(refund_policy_details)).clear();
		driver.findElement(By.id(refund_policy_details)).sendKeys(""+text);
	}
	
	public void clickSave() {
		driver.findElement(By.xpath(save_btn)).click();
	}

}
