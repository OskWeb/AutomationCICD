package Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.sun.net.httpserver.Authenticator.Retry;

import org.openqa.selenium.By;
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
import rahulshettyacademy.ProductCatalogue;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestComponents.BaseTest;

public class ErrorValidationsTest extends BaseTest {
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer=TestComponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3";
		landingPage.loginApplication("oscar@gmail.com", "Aa_12345");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
		
	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("oscar@gmail.com", "Aa_12345");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
		
	}

}
