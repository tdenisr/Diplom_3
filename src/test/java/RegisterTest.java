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

import static config.Constants.LOGIN_PAGE_URL;
import static config.Constants.REGISTER_PAGE_URL;
import static org.junit.Assert.assertEquals;

public class RegisterTest extends BaseTest {
    UserClient userClient = new UserClient();
    User user;
    private String userName;
    private String userEmail;
    private String userPassword;
    private RegisterPage registerPage;
    boolean userCreationFlag = true;

    @Before
    @Step("Подготовка тестовых данных")
    public void init() {
        user = UserGenerator.getRandomUser();
        driver.get(REGISTER_PAGE_URL);
        registerPage = new RegisterPage(driver);

        userName = user.getName();
        userEmail = user.getEmail();
        userPassword = user.getPassword();
        Allure.addAttachment("Имя", userName);
        Allure.addAttachment("Email", userEmail);
        Allure.addAttachment("Пароль", userPassword);
    }

    @Test
    @DisplayName("Тест успешной регистарции пользователя")
    public void registerNewUserSuccess() {
        //Заполняем поля и кликаем кнопку регистрации
        LoginPage loginPage = registerPage.fillRegistrationForm(user);
        //Ожидаем загрузки страницы входа
        loginPage.waitLoginPage();
        //Проверяем, что произошел переход на страницу входа
        checkRegistrationFormLoaded();
    }

    @Step("Проверка успешного перехода на форму входа")
    private void checkRegistrationFormLoaded() {
        assertEquals("Не произошел переход на форму входа",
                LOGIN_PAGE_URL,
                driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Проверка регистрации с коротким паролем. Минимальная длинна пароля 6 символов")
    public void registerNewUserWithShortPasswordFail() {
        String expectedMessage = "Некорректный пароль";
        String shortPassword = UserGenerator.getRandomPassword(0, 5);
        registerPage.setUserName(userName);
        registerPage.setUserEmail(userEmail);
        registerPage.setUserPassword(shortPassword);
        userCreationFlag = false;
        String actualMessage = registerPage.getIncorrectPasswordMessageText();
        assertEquals("Не прошла провекра сообщения об ошибке при коротком пароле",
                expectedMessage,
                actualMessage);
    }

    @After
    @Step("Удаление тестовго пользователя через API")
    public void deleteTestUser() {
        if (userCreationFlag) {
            Response response = userClient.deleteUser(user);
            assertEquals("Ошибка при удалении пользователя", HttpStatus.SC_ACCEPTED, response.getStatusCode());
        }
    }
}
