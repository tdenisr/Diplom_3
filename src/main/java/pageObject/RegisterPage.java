package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.User;

import static config.Constants.defaultTimeOut;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    //Поле имя
    private final By userNameField = By.xpath(".//div/label[text()='Имя']/parent::div/input");
    private final By userEmailField = By.xpath(".//div/label[text()='Email']/parent::div/input");
    private final By userPasswordField = By.xpath(".//div/label[text()='Пароль']/parent::div/input");
    private final By registerButton = By.xpath(".//button[text()= 'Зарегистрироваться']");
    private final By incorrectPasswordMessage = By.xpath(".//p[starts-with(@class, 'input__error')]");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    @Step("Ожидаем когда страница регистрации зарузится")
    public void waitRegisterPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(registerButton)).isDisplayed();
    }

    @Step("Заполняем поле Имя")
    public void setUserName(String userName) {
        putText(driver.findElement(userNameField), userName);
    }

    @Step("Заполняем поле Email")
    public void setUserEmail(String email) {
        putText(driver.findElement(userEmailField), email);
    }

    @Step("Заполняем поле пароль")
    public void setUserPassword(String password) {
        putText(driver.findElement(userPasswordField), password);
    }

    @Step("Нажимаем кнопку Зарегистрироваться")
    public LoginPage clickRegisterButton() {
        driver.findElement(registerButton).click();
        return new LoginPage(driver);
    }

    @Step("Получение сообщения о некорректном пароле")
    public String getIncorrectPasswordMessageText() {
        driver.findElement(registerButton).click();
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(incorrectPasswordMessage)).isDisplayed();
        return driver.findElement(incorrectPasswordMessage).getText();
    }

    @Step("Заполняем страницу регистрации и жмем зарегистрироваться")
    public LoginPage fillRegistrationForm(User user) {
        waitRegisterPage();
        //Заполняем поля для регистрации
        setUserEmail(user.getEmail());
        setUserName(user.getName());
        setUserPassword(user.getPassword());
        return clickRegisterButton();
    }

    @Step("Клик по ссылке Войти")
    public LoginPage clickLoginLink() {
        waitRegisterPage();
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }

    private void putText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }


}
