import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObject.*;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import static config.Constants.*;
import static org.junit.Assert.assertEquals;

public class LoginTest extends BaseTest {
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
        userName = user.getName();
        userEmail = user.getEmail();
        userPassword = user.getPassword();
        Allure.addAttachment("Имя", userName);
        Allure.addAttachment("Email", userEmail);
        Allure.addAttachment("Пароль", userPassword);
    }

    @Test
    @DisplayName("Вход пользователя с главной страницы через кнопку Личный кабинет")
    public void registerMainPageSuccess() {
        driver.get(MAIN_PAGE_URL);
        BasePage basePage = new MainPage(driver);
        LoginPage loginPage = basePage.clickLoginLink();
        ProfilePage profilePage = loginPage.loginFromLoginPage(user).getProfilePage();
        assertEquals("Автроирзация не выполнена", user.getEmail(), profilePage.getEmailFromField());
    }

    @Test
    @DisplayName("Вход пользователя по кнопке «Войти в аккаунт» на главной")
    public void registerAccountButtonSuccess() {
        driver.get(MAIN_PAGE_URL);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLogin();
        ProfilePage profilePage = loginPage.loginFromLoginPage(user).getProfilePage();
        assertEquals("Автроирзация не выполнена", user.getEmail(), profilePage.getEmailFromField());
    }

    @Test
    @DisplayName("Вход пользователя  через кнопку в форме регистрации")
    public void registerRegisterPageSuccess() {
        driver.get(REGISTER_PAGE_URL);
        BasePage basePage = new RegisterPage(driver);
        LoginPage loginPage = basePage.clickLoginLink();
        ProfilePage profilePage = loginPage.loginFromLoginPage(user).getProfilePage();
        assertEquals("Автроирзация не выполнена", user.getEmail(), profilePage.getEmailFromField());
    }

    @Test
    @DisplayName("Вход пользователя через кнопку в форме восстановления пароля")
    public void registerRecoverPasswordPageSuccess() {
        driver.get(FORGOT_PASSWORD_PAGE_URL);
        BasePage basePage = new ForgotPasswordPage(driver);
        LoginPage loginPage = basePage.clickLoginLink();
        ProfilePage profilePage = loginPage.loginFromLoginPage(user).getProfilePage();
        assertEquals("Автроирзация не выполнена", user.getEmail(), profilePage.getEmailFromField());
    }

    @After
    @Step("Удаление тестовго пользователя через API")
    public void deleteTestUser() {
        Response response = userClient.deleteUser(user);
        assertEquals("Ошибка при удалении пользователя", HttpStatus.SC_ACCEPTED, response.getStatusCode());
    }
}
