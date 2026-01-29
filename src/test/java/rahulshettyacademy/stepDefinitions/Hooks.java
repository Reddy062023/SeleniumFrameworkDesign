package rahulshettyacademy.stepDefinitions;

import io.cucumber.java.After;
import rahulshettyacademy.TestComponents.BaseTest;

public class Hooks extends BaseTest {

    @After
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();   // important for parallel execution
        }
    }
}
