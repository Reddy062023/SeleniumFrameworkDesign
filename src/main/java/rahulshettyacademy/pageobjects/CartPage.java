package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;

	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;

	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProducts;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean VerifyProductDisplay(String productName) {
		return cartProducts.stream()
				.anyMatch(product -> product.getText().equalsIgnoreCase(productName));
	}

	public CheckoutPage goToCheckout() {

		// wait + scroll + safe click
		waitForWebElementToAppear(checkoutEle);
		scrollToElement(checkoutEle);
		clickElement(checkoutEle);

		return new CheckoutPage(driver);
	}
}
