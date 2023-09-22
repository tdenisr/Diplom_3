package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.User;

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
    private final By bunsHeader = By.xpath(".//h2[text()='Булки']");
    private final By bunsLink = By.xpath(".//span[text()='Булки']/parent::div");
    private final By sauceHeader = By.xpath(".//h2[text()='Соусы']");
    private final By sauceLink = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingHeader = By.xpath(".//h2[text()='Начинки']");
    private final By fillingLink = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By ingredientsBlock = By.xpath(".//h2[text()='Начинки']/parent::div");

    @Step("Ожидание загрузки страницы")
    public void waitMainPage() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(loginButton)).isDisplayed();
    }

    @Step("Ожидание появление заголовка страницы")
    public boolean waitConstructorHeader() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(constructorHeader)).isDisplayed();
        return true;
    }

    @Step("Ожидание загрузки главной страницы для авторизованного пользователя")
    public void waitMainPageAfterLogin() {
        new WebDriverWait(driver, defaultTimeOut)
                .until(visibilityOfElementLocated(takeOrderButton)).isDisplayed();
    }

    @Step("Ожидаем скроллинга на странице")
    public void waitScrollingToSection(WebElement element) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new WebDriverWait(driver, defaultTimeOut)
                .until(driver1 -> element.getAttribute("class").contains("current"));
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

    @Step("Авторизоваться на сайте")
    public MainPage loginMainPage(User user) {
        waitMainPage();
        LoginPage loginPage = clickLogin();
        return loginPage.loginFromLoginPage(user);
    }

    @Step("Переход в личный кабинет авторизованного пользователя")
    public ProfilePage getProfilePage() {
        waitMainPageAfterLogin();
        driver.findElement(accountLink).click();
        return new ProfilePage(driver);
    }

    @Step("Получаем координаты начала блока с ингредиентами")
    public int getIngredientsBlock() {
        return Integer.valueOf(driver.findElement(ingredientsBlock).getLocation().getY());
    }

    @Step("Получаем координаты заголовка Булки")
    public int getBunsHeader() {
        return Integer.valueOf(driver.findElement(bunsHeader).getLocation().getY());
    }

    @Step("Кликаем по заголовку Булки")
    public MainPage clickBunsLink() {
        waitMainPage();
        WebElement element = driver.findElement(bunsLink);
        WebElement sauceElement = driver.findElement(sauceLink);
        sauceElement.click(); // для первоначльной прокрутки на другую секцию
        waitScrollingToSection(sauceElement);
        element.click();
        waitScrollingToSection(element);
        return new MainPage(driver);
    }

    @Step("Получаем координаты заголовка Соусы")
    public int getSauceHeader() {
        return Integer.valueOf(driver.findElement(sauceHeader).getLocation().getY());
    }

    @Step("Кликаем по заголовку Соусы")
    public MainPage clickSauceLink() {
        waitMainPage();
        WebElement element = driver.findElement(sauceLink);
        element.click();
        waitScrollingToSection(element);
        return new MainPage(driver);
    }

    @Step("Получаем координаты заголовка Начинки")
    public int getFillingHeader() {
        return Integer.valueOf(driver.findElement(fillingHeader).getLocation().getY());
    }

    @Step("Кликаем по заголовку Начинки")
    public MainPage clickFillingLink() {
        waitMainPage();
        WebElement element = driver.findElement(fillingLink);
        element.click();
        waitScrollingToSection(element);
        return new MainPage(driver);
    }

}
