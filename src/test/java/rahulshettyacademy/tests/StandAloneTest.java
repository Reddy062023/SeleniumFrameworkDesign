package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        // ✅ Automatically set up the correct ChromeDriver for your installed Chrome
        WebDriverManager.chromedriver().setup();

        // Chrome options to improve stability
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://rahulshettyacademy.com/client");

        LandingPage landingPage = new LandingPage(driver);

        // Login
        driver.findElement(By.id("userEmail")).sendKeys("japendras06@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Medway@2025");
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Select the product
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        WebElement prod = products.stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        // Scroll to product and click Add to Cart using JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", prod);
        WebElement addToCartBtn = prod.findElement(By.cssSelector(".card-body button:last-of-type"));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);

        // Wait for toast notification & animation to disappear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // Scroll to Cart and click safely
        WebElement cartButton = driver.findElement(By.cssSelector("[routerlink*='cart']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cartButton);
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartButton);

        // Verify product in cart
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match, "Product not found in cart: " + productName);

        // Proceed to Checkout
        WebElement checkoutBtn = driver.findElement(By.cssSelector(".totalRow button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);

        // Select Country
        WebElement countryInput = driver.findElement(By.cssSelector("[placeholder='Select Country']"));
        Actions a = new Actions(driver);
        a.sendKeys(countryInput, "india").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

        // Submit order
        WebElement submitBtn = driver.findElement(By.cssSelector(".action__submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submitBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        // Verify confirmation
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        driver.quit();
    }
}
