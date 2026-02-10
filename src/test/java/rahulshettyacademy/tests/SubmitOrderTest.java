package rahulshettyacademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

    @Test
    public void submitOrderTest() throws IOException {

        String productName = "ZARA COAT 3";

        // Login
        ProductCatalogue productCatalogue =
                landingPage.loginApplication("japendras06@gmail.com", "Medway@2025");

        // Add product to cart
        productCatalogue.addProductToCart(productName);

        // Go to cart and validate
        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertTrue(
                cartPage.VerifyProductDisplay(productName),
                "Product not found in cart"
        );

        // Checkout
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");

        // Submit order
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();

        // Verify confirmation
        Assert.assertEquals(
                confirmationPage.getConfirmationMessage(),
                "THANKYOU FOR THE ORDER."
        );
    }
}
