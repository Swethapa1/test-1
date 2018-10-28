package orders;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Orders {
	WebDriver driver;
	
	public Orders(WebDriver driver) {
		this.driver=driver;
	}
	private String search_text="//input[@placeholder='Name, email or order #']";

	private String allow_customer_order_note="//div[@class='order-note']";
	private String order_tab_allorders="//div[contains(@class,'regional-filter-button')][1]";
	private String order_tab_domesticorders="//div[contains(@class,'regional-filter-button')][2]";
	private String order_tab_internationalorders="//div[contains(@class,'regional-filter-button')][3]";
	
	private String shippment_status_All="//div[text()='All status']";
	private String shippment_status_Unshipped="//div[text()='Unshipped']";
	private String shippment_status_AwaitingPickup="//div[text()='Awaiting pickup']";
	
	private String order_id_row="//a[text()='";
	public void clickOrderId(String order_id) {
		driver.findElement(By.xpath("//a[text()='"+order_id+"']")).click();	
	}
	public void selectOrderCheckBox(String order_id) {
		driver.findElement(By.xpath(order_id_row+order_id+"']/parent::div/preceding-sibling::div")).click();
	}
	
	public void searchOrder(String text) {
		driver.findElement(By.xpath(search_text)).sendKeys(""+text);
		driver.findElement(By.xpath(search_text)).sendKeys(Keys.ENTER);
	}
	
	private String getNoOrdersText="//div[@class='no-order-wrapper']/h2";
	
	public String getNoOrdersText() {
		return driver.findElement(By.xpath(getNoOrdersText)).getText();
	}
	
	private String order_dropdown_1="//div[@class='rt-tr-group']/div/div[2]/a[text()='";
	private String order_dropdown_2="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/span";
	
	public void selectActionsForOrder(String order_id) {		
		driver.findElement(By.xpath(order_dropdown_1+order_id+order_dropdown_2)).click();
	}
	
	private String cancel_order_1="//div[@class='rt-tr-group']/div/div[2]/a[text()='";
	private String cancel_order_2="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/li[@class='actions-dropdown-post-link-item ' and text()='Cancel']";
	
	public void clickCancelOrder(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+cancel_order_2)).click();
	}
	
	private String print_invoice="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/li/a[text()='Print invoice']";
	
	public void clickPrintInvoiceForOrder(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+print_invoice)).click();
	}
	
	private String schedule_pickup="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/div/li[text()='Schedule pickup']";
	
	public void clickSchedulePickupForOrder(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+schedule_pickup)).click();
	}
	
	private String mark_as_delivered="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/li[text()='Mark as delivered']";
	
	public void clickMarkAsDeliveredForOrder(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+mark_as_delivered)).click();
	}
	
	private String refund="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/li/a[text()='Refund']";
	
	public void clickRefundForOrder(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+refund)).click();
	}
	
	private String confirm_button="//button[@class='confirm-btn']";
	
	public void clickConfirm() {
		driver.findElement(By.xpath(confirm_button)).click();
	}

	public void clickOrderNote() {
		driver.findElement(By.xpath(allow_customer_order_note)).click();
	}
	
	public void clickOrderTabs_AllOrders() {
		
				driver.findElement(By.xpath(order_tab_allorders)).click();
	}
	
	public void clickOrderTabs_DomesticOrders() {
		
				driver.findElement(By.xpath(order_tab_domesticorders)).click();
	}
	
	public void clickOrderTabs_InternationalOrders() {
		
				driver.findElement(By.xpath(order_tab_internationalorders)).click();
	}
	
	public void clickShippmentStatus_AwaitingPickUp() {
		
				driver.findElement(By.xpath(shippment_status_AwaitingPickup)).click();
	}
	public void clickShippmentStatus_Unshipped() {
		
				driver.findElement(By.xpath(shippment_status_Unshipped)).click();
	}
	public void clickShippmentStatus_All() {
		
				driver.findElement(By.xpath(shippment_status_All)).click();
	}
	
	private String msg="//p[@class='message']";
	
	public String getMessage() {
		return driver.findElement(By.xpath(msg)).getText();
	}
	
	private String shipViaShipyaari="']/parent::div/following-sibling::div[7]/div[@class='order-actions-dropdown']/div/div/div/ul/div/li[text()='Schedule pickup via Shipyaari']";

	public void clickShipViaShipyaari(String order_id) {
		driver.findElement(By.xpath(cancel_order_1+order_id+shipViaShipyaari)).click();
	}
}
