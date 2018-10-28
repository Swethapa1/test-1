package utils;

import org.openqa.selenium.WebDriver;

import TemplateCategories.AllTemplate;
import TemplateCategories.ClothingTemplate;

public class ChooseCategory {
	WebDriver driver;

	public ChooseCategory(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void chooseCategoryMethod(String template_category) {
		AllTemplate blankTemplate = new AllTemplate(driver);
		switch(template_category.toLowerCase().replace(" ", "")) 
		{
			case "clothing": 
				blankTemplate.clickEditClothingTemplate(1);
				break;
			case "beautyandwellness":
				blankTemplate.clickEditBeautyAndWellness(1);
				break;
			case "decorandfurniture":
				blankTemplate.clickEditDecorAndFurnitureTemplate(1);
				break;
			case "education":
				blankTemplate.clickEditEducationTemplate(1);
				break;
			case "fashionaccessories":
				blankTemplate.clickEditFashionAccessoriesTemplate(1);
				break;
			case "groceries":
				blankTemplate.clickEditGroceriesTemplate(1);
				break;
			case "healthservices":
				blankTemplate.clickEditHealthServicesTemplate(1);
				break;
			case "interiordesign":
				blankTemplate.clickEditInteriorDesignTemplate(1);
				break;
			case "kitchenandhouseware":
				blankTemplate.clickEditKitchenAndHousewareTemplate(1);
				return;
			case "others":
				blankTemplate.clickEditToysTemplate();
				break;
			case "realestate":
				blankTemplate.clickEditRealEstateTemplate(1);
				break;
			case "weddingsandevents":
				blankTemplate.clickEditWeddingsAndEventsTemplate(1);
				break;
			case "blank":
				blankTemplate.clickEditBlankTemplate();
				break;
			default:
				blankTemplate.clickEditBlankTemplate();
				break;
			
		}
		
	}

}
