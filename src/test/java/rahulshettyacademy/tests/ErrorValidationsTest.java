package rahulshettyacademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
    public void LoginErrorValidation() throws IOException {

        landingPage = launchApplication();

        // Intentionally wrong password to trigger error message
        landingPage.loginApplication(
            "japendrareddy@gmail.com",
            "WrongPassword@123"
        );

        Assert.assertEquals(
            landingPage.getErrorMessage(),
            "Incorrect email or password."
        );
    }

    @Test(groups = { "ErrorHandling" })
    public void ProductErrorValidation() throws IOException {

        landingPage = launchApplication();

        String productName = "ZARA COAT 3";

        ProductCatalogue productCatalogue =
            landingPage.loginApplication(
                "japendrareddy@gmail.com",
                "Medway@2025"
            );

        productCatalogue.addProductToCart(productName);

        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertFalse(
            cartPage.VerifyProductDisplay("ZARA COAT 33")
        );
    }
}
