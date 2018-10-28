package aboutus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AboutUsPage {
	WebDriver driver;

	public AboutUsPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	private String iframe_element="//iframe[@title='Page Editor']";
	private String about_us_id="element-nhwbx-block-1-h-1";
	private String save_btn="//div[contains(@class,'save-btn')]";
	
	Actions actions;
	WebElement element;
	
	public void switchToIframe() {
		driver.switchTo().frame(driver.findElement(By.xpath(iframe_element)));
	}
	
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}
	
	
	public void addAboutUs(String text) {
		element = driver.findElement(By.xpath("//section[@id='nhwbx']/div/div[contains(@class,'element-block')]/h1"));
		actions=new Actions(driver);
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id(about_us_id)).click();
		driver.findElement(By.id(about_us_id)).clear();
		driver.findElement(By.id(about_us_id)).sendKeys(""+text);
		
	}
	
	public void clickSave() {
		driver.findElement(By.xpath(save_btn)).click();
	}

}
