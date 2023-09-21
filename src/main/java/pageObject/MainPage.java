package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static config.Constants.defaultTimeOut;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By takeOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By accountLink = By.xpath(".//p[text()='Личный Кабинет']/parent::a");
    private final By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");

    @Step("Ожидание загрузки страницы")
    public void waitMainPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(loginButton)).isDisplayed();
    }
    @Step("Ожидание появление заголовка страницы")
    public boolean waitConstructorHeader(){
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(constructorHeader)).isDisplayed();
        return true;
    }

    @Step("Ожидание загрузки главной страницы для авторизованного пользователя")
    public void waitMainPageAfterLogin() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(takeOrderButton)).isDisplayed();
    }

    @Step("Прокрутка и клик по кнопке Войти в аккаунт")
    public LoginPage clickLogin() {
        waitMainPage();
        WebElement element = driver.findElement(loginButton);
        //Прокручиваем страницу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
        return new LoginPage(driver);
    }

    @Step("Клик по Личному кабинету")
    public LoginPage clickLoginLink() {
        waitMainPage();
        driver.findElement(accountLink).click();
        return new LoginPage(driver);
    }

    @Step("Переход в личный кабинет авторизованного пользователя")
    public ProfilePage getProfilePage() {
        waitMainPageAfterLogin();
        driver.findElement(accountLink).click();
        return new ProfilePage(driver);
    }
}
