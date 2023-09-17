package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static config.Constants.defaultTimeOut;

public class RegisterPage {
    private final WebDriver driver;
    public RegisterPage(WebDriver driver){
        this.driver = driver;
    }
    //Поле имя
    private final By userNameField = By.xpath(".//div/label[text()='Имя']/parent::div/input");
    private final By userEmailField = By.xpath(".//div/label[text()='Email']/parent::div/input");
    private final By userPasswordField = By.xpath(".//div/label[text()='Пароль']/parent::div/input");
    private final By registerButton = By.xpath(".//button[text()= 'Зарегистрироваться']");
    @Step("Ожидаем когда страница регистрации зарузится")
    public void waitRegisterPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton)).isDisplayed();
    }

    @Step("Заполняем поле Имя")
    public void setUserName(String userName){
        putText(driver.findElement(userNameField), userName);
    }
    @Step("Заполняем поле Email")
    public void setUserEmail(String email){
        putText(driver.findElement(userEmailField), email);
    }
    @Step("Заполняем поле пароль")
    public void setUserPassword(String password){
        putText(driver.findElement(userPasswordField), password);
    }
    @Step("Нажимаем кнопку Зарегистрироваться")
    public LoginPage clickRegisterButton(){
        driver.findElement(registerButton).click();
        return new LoginPage(driver);
    }

    private void putText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }


}
