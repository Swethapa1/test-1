import java.awt.MouseInfo;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import edittemplate.PageBuilderPage;
import login.LoginPage;



public class Cords {

    public static void main(String[] args) throws InterruptedException {

        //get cords of mouse code, outputs to console every 1/2 second
        //make sure to import and include the "throws in the main method"

        
        /*TimeUnit.SECONDS.sleep(1/2);
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        System.out.println("X:" + mouseX);
        System.out.println("Y:" + mouseY);
        //make sure to import 
        */
        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver");
		WebDriver driver=new ChromeDriver();
		driver.navigate().to("https://shpmtc.me/login");
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		
		LoginPage loginPage =  new LoginPage(driver);
		loginPage.addEmail("testshop_01oct2018_a4@testshop.com");
		loginPage.addPassword("Tester123*");
		loginPage.addEnvPassCode();
		loginPage.clickLogin();
		Thread.sleep(5000);

		PageBuilderPage pb = new PageBuilderPage(driver);
		pb.clickDashboard();
		Thread.sleep(1000);
		pb.clickEditSite();
		Thread.sleep(1000);
		pb.clickPageDropDown();
		Actions action = new Actions(driver);
	/*	pb.clickEditAboutUsDropDown();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		action.moveByOffset(120, 300).click().perform();
		Thread.sleep(2000);
		
		int size = driver.findElements(By.tagName("iframe")).size();
		System.out.println(size);
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Page Editor']")));
		Thread.sleep(2000);
		
		WebElement element = driver.findElement(By.xpath("//section[@id='nhwbx']/div/div[contains(@class,'element-block')]/h1"));
		Actions actions=new Actions(driver);
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id("element-nhwbx-block-1-h-1")).click();
		driver.findElement(By.id("element-nhwbx-block-1-h-1")).clear();
		driver.findElement(By.id("element-nhwbx-block-1-h-1")).sendKeys("ABC");
		Thread.sleep(2000);
		
		action.moveByOffset(220, 300).click().perform();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//div[contains(@class,'save-btn')]")).click();
		Thread.sleep(2000);
		
		pb.clickPageDropDown();*/
		pb.clickEditShippingReturns();
		action.moveByOffset(120, 300).click().perform();
		Thread.sleep(2000);
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Page Editor']")));
		Thread.sleep(2000);
		Actions actions=new Actions(driver);
		WebElement element=driver.findElement(By.id("element-ugwbx-block-1-h-1"));
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id("element-ugwbx-block-1-h-1")).click();
		driver.findElement(By.id("element-ugwbx-block-1-h-1")).clear();
		driver.findElement(By.id("element-ugwbx-block-1-h-1")).sendKeys("shipping policy");
		
		
		element=driver.findElement(By.id("element-ugwbx-block-1-p-1"));
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id("element-ugwbx-block-1-p-1")).click();
		driver.findElement(By.id("element-ugwbx-block-1-p-1")).clear();
		driver.findElement(By.id("element-ugwbx-block-1-p-1")).sendKeys("shipping policy details");
		
		
		//Return policy
		
		element=driver.findElement(By.id("element-vgwbx-block-1-h-1"));
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id("element-vgwbx-block-1-h-1")).click();
		driver.findElement(By.id("element-vgwbx-block-1-h-1")).clear();
		driver.findElement(By.id("element-vgwbx-block-1-h-1")).sendKeys("Refund policy");
		
		
		element=driver.findElement(By.id("element-vgwbx-block-1-p-1"));
		actions.moveToElement(element).click().click().build().perform();
		driver.findElement(By.id("element-vgwbx-block-1-p-1")).click();
		driver.findElement(By.id("element-vgwbx-block-1-p-1")).clear();
		driver.findElement(By.id("element-vgwbx-block-1-p-1")).sendKeys("refund policy details");
		
		//Delete - //span[@data-tip='Delete']
		
		
		
		
    }

}