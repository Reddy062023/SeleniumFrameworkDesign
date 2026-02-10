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

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        // ✅ Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        try {
            // Navigate to site
            driver.get("https://rahulshettyacademy.com/client");

            // Login
            driver.findElement(By.id("userEmail")).sendKeys("japendras06@gmail.com");
            driver.findElement(By.id("userPassword")).sendKeys("Medway@2025");
            driver.findElement(By.id("login")).click();

            // Wait for products to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

            // Select the product dynamically
            List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

            WebElement prod = products.stream()
                    .filter(p -> p.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

            // Scroll to product and click Add to Cart
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", prod);
            WebElement addToCartBtn = prod.findElement(By.cssSelector(".card-body button:last-of-type"));
            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
            js.executeScript("arguments[0].click();", addToCartBtn);

            // Wait for toast to disappear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

            // Go to cart
            WebElement cartButton = driver.findElement(By.cssSelector("[routerlink*='cart']"));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", cartButton);
            wait.until(ExpectedConditions.elementToBeClickable(cartButton));
            js.executeScript("arguments[0].click();", cartButton);

            // Verify product in cart
            List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
            boolean match = cartProducts.stream()
                    .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
            Assert.assertTrue(match, "Product not found in cart: " + productName);

            // Proceed to Checkout
            WebElement checkoutBtn = driver.findElement(By.cssSelector(".totalRow button"));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);
            wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
            js.executeScript("arguments[0].click();", checkoutBtn);

            // Select country
            WebElement countryInput = driver.findElement(By.cssSelector("[placeholder='Select Country']"));
            actions.sendKeys(countryInput, "india").build().perform();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
            driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

            // Submit order
            WebElement submitBtn = driver.findElement(By.cssSelector(".action__submit"));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", submitBtn);
            wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
            js.executeScript("arguments[0].click();", submitBtn);

            // ✅ Wait for confirmation message
            WebElement confirmElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary"))
            );

            String confirmMessage = confirmElement.getText().trim();
            System.out.println("Order confirmation message: " + confirmMessage);

            // Verify
            Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        } finally {
            // Close browser
            driver.quit();
        }
    }
}
