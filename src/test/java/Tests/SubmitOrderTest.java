package Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.CartPage;
import rahulshettyacademy.CheckoutPage;
import rahulshettyacademy.ConfirmationPage;
import rahulshettyacademy.LandingPage;
import rahulshettyacademy.OrderPage;
import rahulshettyacademy.ProductCatalogue;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest{
	
	String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkout = cartPage.goToCheckout();
		checkout.selectCountry("india");
		ConfirmationPage confirmationPage = checkout.submitOrder();
		String textOrder = confirmationPage.getConfirmationMessage();
		Assert.assertEquals(textOrder, "THANKYOU FOR THE ORDER.");
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() {
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("oscar@gmail.com", "Aa_12345");
		OrderPage ordersPage =  productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	//Extent Reports -
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
	
//	HashMap<String, String> map = new HashMap<String,String>();
//	map.put("email", "oscar@gmail.com");
//	map.put("password", "Aa_12345");
//	map.put("product", "ZARA COAT 3");
	
//	HashMap<String, String> map1 = new HashMap<String,String>();
//	map1.put("email", "anshika@gmail.com");
//	map1.put("password", "Iamking@000");
//	map1.put("product", "ADIDAS ORIGINAL");

}
