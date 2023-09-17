package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static config.Constants.defaultTimeOut;

public class LoginPage {
    private final WebDriver driver;
    public LoginPage(WebDriver driver){
        this.driver = driver;
    }
    private final By userEmailField = By.xpath(".//div/label[text()='Email']/parent::div/input");
    private final By userPasswordField = By.xpath(".//div/label[text()='Пароль']/parent::div/input");
    private final By enterButton = By.xpath(".//button[text()= 'Войти']");
    @Step("Ожидаем когда страница авторизации зарузится")
    public void waitLoginPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(ExpectedConditions.visibilityOfElementLocated(enterButton)).isDisplayed();
    }
    @Step("Заполняем поле Email")
    public void setUserEmail(String email){
        putText(driver.findElement(userEmailField), email);
    }
    @Step("Заполняем поле пароль")
    public void setUserPassword(String password){
        putText(driver.findElement(userPasswordField), password);
    }

    private void putText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }
}
