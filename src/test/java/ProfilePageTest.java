import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObject.BasePage;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.ProfilePage;
import utils.User;
import utils.UserClient;
import utils.UserCreds;
import utils.UserGenerator;

import static config.Constants.MAIN_PAGE_URL;
import static config.Constants.PROFILE_PAGE_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfilePageTest extends BaseTest {
    UserClient userClient = new UserClient();
    User user;
    private String userName;
    private String userEmail;
    private String userPassword;

    @Before
    @Step("Подготовка тестовых данных")
    public void init() {
        user = UserGenerator.getRandomUser();
        userClient.create(user);
        userClient.login(UserCreds.credsFrom(user));
        userName = user.getName();
        userEmail = user.getEmail();
        userPassword = user.getPassword();
        Allure.addAttachment("Имя", userName);
        Allure.addAttachment("Email", userEmail);
        Allure.addAttachment("Пароль", userPassword);
    }

    @Test
    @DisplayName("Переход по клику на Личный кабинет")
    public void clickAccountLinkSuccess() {
        driver.get(MAIN_PAGE_URL);
        BasePage basePage = new MainPage(driver);
        LoginPage loginPage = basePage.clickLoginLink();
        ProfilePage profilePage = loginPage.loginFromLoginPage(user).getProfilePage();
        assertEquals("Не произошел переход на страницу профиля",
                PROFILE_PAGE_URL,
                driver.getCurrentUrl());
        assertEquals("Переход в личный кабинет не выполнен", user.getEmail(), profilePage.getEmailFromField());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void registerAccountButtonSuccess() {
        driver.get(MAIN_PAGE_URL);
        BasePage basePage = new MainPage(driver);
        ProfilePage profilePage = basePage.clickLoginLink().loginFromLoginPage(user).getProfilePage();
        MainPage mainPage = profilePage.clickConstructorLink();
        assertEquals("Не произошел переход на страницу Конструктора",
                MAIN_PAGE_URL,
                driver.getCurrentUrl());
        assertTrue(mainPage.waitConstructorHeader());
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную страницу по клику на логотип Stellar Burgers.")
    public void clickLogoGetMainPage() {
        driver.get(MAIN_PAGE_URL);
        BasePage basePage = new MainPage(driver);
        ProfilePage profilePage = basePage.clickLoginLink().loginFromLoginPage(user).getProfilePage();
        MainPage mainPage = profilePage.clickLogoLink();
        assertEquals("Не произошел переход на страницу Конструктора",
                MAIN_PAGE_URL,
                driver.getCurrentUrl());
        assertTrue(mainPage.waitConstructorHeader());
    }

    @After
    @Step("Удаление тестовго пользователя через API")
    public void deleteTestUser() {
        Response response = userClient.deleteUser(user);
        assertEquals("Ошибка при удалении пользователя", HttpStatus.SC_ACCEPTED, response.getStatusCode());
    }
}
