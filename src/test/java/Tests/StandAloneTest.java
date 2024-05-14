package Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.LandingPage;

import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new comments are added
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		landingPage.goTo();
		driver.findElement(By.id("userEmail")).sendKeys("oscar@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Aa_12345");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(
				product-> product.findElement(By.cssSelector(".mb-3 h5")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cart .cartWrap h3"));
		
		boolean findProduct = cartProducts.stream().anyMatch(
				p-> p.getText().equals(productName));
		
		Assert.assertTrue(findProduct);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("india");
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		
		driver.findElement(By.xpath("//a[contains(@class,'action__submit')]")).click();
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("hero-primary")));
		
		String textOrder = driver.findElement(By.cssSelector(".hero-primary")).getText();
		
		Assert.assertEquals(textOrder, "THANKYOU FOR THE ORDER.");
		
		driver.quit();
		
		/*WebElement productFound = null;
		for(WebElement product: products) {
			if(product.findElement(By.cssSelector(".mb-3 h5")).getText().equalsIgnoreCase("ZARA COAT 3")) {
				productFound = product;
			}
		}
		System.out.println(productFound.findElement(By.cssSelector(".mb-3 h5")).getText());*/
	}

}
