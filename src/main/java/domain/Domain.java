package domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Domain {
	WebDriver driver;

	public Domain(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	private String connectCustomDomain="//a[text()='Connect a custom domain']";
	private String buyNewDomain="//a[text()='Buy a new domain']";
	
	private String godaddy_radio_btn="//input[@id='connect-godaddy-domain-radio-input']";
	private String others_radio_btn="//div[@class='section']/input[@id='connect-custon-domain-radio-input']";
	
	private String input_godaddy="//input[@id='connect-godaddy-domain-radio-input']/following-sibling::form/div/input[@name='domain']";
	private String input_others="//input[@id='connect-custon-domain-radio-input']/following-sibling::form/div/input[@name='domain']";

	private String connect_godaddy="//input[@id='connect-godaddy-domain-radio-input']/following-sibling::form/div[@class='submit']/input";
	private String connect_others="//input[@id='connect-custon-domain-radio-input']/following-sibling::form/div[@class='submit']/input";
	
	private String domain_settings="//a[text()='Go to Domain settings']";
	private String disconnect="//a[text()='Disconnect']";
	private String connect_another_domain="//a[text()='Connect another domain']";
	private String primary_domain="//div[@class='primary-domain-selection']/select";
	private String save="//div[@class='primary-domain-selection']/input";
	
	//Buy domain
	private String domain_text="//input[@class='input-group-field']";
	private String search="//div[@class='submit']";
	
	public void addDomain(String dom) {
		driver.findElement(By.xpath(domain_text)).sendKeys(""+dom);
	}
	
	public void clickSearch() {
		driver.findElement(By.xpath(search)).click();
	}
	
	public void clickSave() {
		driver.findElement(By.xpath(save)).click();
	}
	
	public void changePrimaryDomain(String domain_name) {
		Select domain=new Select(driver.findElement(By.xpath(primary_domain)));
		domain.selectByValue(domain_name);
	}
	
	public void clickConnectAnotherCustomDomain() {
		driver.findElement(By.xpath(connect_another_domain)).click();
	}
	
	public void clickDisconnect() {
		driver.findElement(By.xpath(disconnect)).click();
	}
	
	public void clickGoToDomainSettings() {
		driver.findElement(By.xpath(domain_settings)).click();
	}
	
	public void clickConnectCustomDomain() {
		driver.findElement(By.xpath(connectCustomDomain)).click();
	}
	
	public void clickBuyNewDomain() {
		driver.findElement(By.xpath(buyNewDomain)).click();
	}
	
	public void clickGodaddy() {
		driver.findElement(By.xpath(godaddy_radio_btn)).click();
	}
	
	public void clickOthers() {
		driver.findElement(By.xpath(others_radio_btn)).click();
	}
	
	public void clickConnectGodaddy() {
		driver.findElement(By.xpath(connect_godaddy)).click();
	}
	
	public void clickConnectOthers() {
		driver.findElement(By.xpath(connect_others)).click();
	}
	
	public void addGodaddyDomain(String addr) {
		driver.findElement(By.xpath(input_godaddy)).sendKeys(""+addr);
	}
	
	public void addCustomDomain(String addr) {
		driver.findElement(By.xpath(input_others)).click();
		driver.findElement(By.xpath(input_others)).sendKeys(""+addr);
	}
	
	
}
