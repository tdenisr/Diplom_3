import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

public abstract class BaseTest {
    WebDriver driver;

    @Before
    public void setup() {
        driver = createWebDriver();
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        driver.quit();
    }
}