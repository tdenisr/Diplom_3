import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObject.LoginPage;
import pageObject.RegisterPage;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import static config.Constants.*;
import static org.junit.Assert.assertEquals;

public class RegisterTest extends BaseTest {
    UserClient userClient = new UserClient();
    User user;
    private String userName;
    private String userEmail;
    private String userPassword;
    private RegisterPage registerPage;
    @Before
    @Step("Подготовка тестовых данных")
    public void init(){
        user = UserGenerator.getRandomUser();
        userName = user.getName();
        userEmail = user.getEmail();
        userPassword = user.getPassword();
        driver.get(REGISTER_PAGE_URL);
        registerPage = new RegisterPage(driver);

        Allure.addAttachment("Имя", userName);
        Allure.addAttachment("Email", userEmail);
        Allure.addAttachment("Пароль", userPassword);
    }

    @Test
    @DisplayName("Тест успешной регистарции пользователя")
    public void registerNewUserSuccess() {
        //Ожидаем загрузки страницы
        registerPage.waitRegisterPage();
        //Заполняем поля для регистрации
        registerPage.setUserEmail(userEmail);
        registerPage.setUserName(userName);
        registerPage.setUserPassword(userPassword);
        //Кликаем кнопку регистрации
        LoginPage loginPage = registerPage.clickRegisterButton();
        //Ожидаем загрузки страницы входа
        loginPage.waitLoginPage();
        //Проверяем, что произошел переход на страницу входа
        checkRegistrationFormLoaded();
    }
    @Step("Проверка успешного перехода на форму входа")
    private void checkRegistrationFormLoaded() {
        assertEquals("Не произошел переход на форму входа",
                driver.getCurrentUrl(),
                LOGIN_PAGE_URL);
    }
    @After
    @Step("Удаление тестовго пользователя через API")
    public void deleteTestUser() {
        Response response = userClient.deleteUser(user);
        assertEquals("Ошибка при удалении пользователя", HttpStatus.SC_ACCEPTED, response.getStatusCode());
    }
}
