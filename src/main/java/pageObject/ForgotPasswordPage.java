package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static config.Constants.defaultTimeOut;

public class ForgotPasswordPage extends BasePage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By recoverPasswordButton = By.xpath(".//button[text()= 'Восстановить']");

    @Step("Ожидание загрузки страцницы восстановления пароля")
    public void waitForgotPasswordPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(ExpectedConditions.visibilityOfElementLocated(recoverPasswordButton)).isDisplayed();
    }

    @Step("Клик по ссылке Войти")
    public LoginPage clickLoginLink() {
        waitForgotPasswordPage();
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }
}
