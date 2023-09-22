package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static config.Constants.defaultTimeOut;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ProfilePage {
    WebDriver driver;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By nameField = By.xpath(".//input[contains(@name, 'Name')]");
    private final By emailField = By.xpath(".//input[contains(@name, 'name') and contains(@type, 'text')]");
    private final By passwordField = By.xpath(".//input[contains(@name, 'name') and contains(@type, 'password')]");
    private final By saveButton = By.xpath(".//button[text()= 'Сохранить']");
    private final By exitButton = By.xpath(".//button[text()= 'Выход']");
    private final By constructorLink = By.xpath(".//p[text()='Конструктор']/parent::a");
    private final By logoLink = By.xpath(".//div[starts-with(@class,'AppHeader_header__logo')]/a");
    ;

    @Step("Ожидание загрузки страницы профиля")
    public void waitProfilePage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(saveButton)).isDisplayed();
    }

    @Step("Получение содержимого из поля Имя")
    public String getNameFromField() {
        waitProfilePage();
        return driver.findElement(nameField).getAttribute("value");
    }

    @Step("Получение содержимого из поля Email")
    public String getEmailFromField() {
        waitProfilePage();
        return driver.findElement(emailField).getAttribute("value");
    }

    @Step("Получение содержимого из поля c паролем")
    public String getPasswordFromField() {
        waitProfilePage();
        return driver.findElement(passwordField).getAttribute("value");
    }

    @Step("Переход по ссылке Конструктор")
    public MainPage clickConstructorLink() {
        waitProfilePage();
        driver.findElement(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("Клик по логотипу сайта")
    public MainPage clickLogoLink() {
        waitProfilePage();
        driver.findElement(logoLink).click();
        return new MainPage(driver);
    }

    @Step("Клик по кнопке Выход")
    public LoginPage clickExitButton() {
        waitProfilePage();
        driver.findElement(exitButton).click();
        return new LoginPage(driver);
    }

}
