import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.ProfilePage;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import static config.Constants.MAIN_PAGE_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogOutTest extends BaseTest {
    UserClient userClient = new UserClient();
    ProfilePage profilePage;
    User user;

    @Before
    @Step("Подготовка тестовых данных")
    public void init() {
        user = UserGenerator.getRandomUser();
        userClient.create(user);
        driver.get(MAIN_PAGE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.loginMainPage(user);
        profilePage = mainPage.getProfilePage();
    }

    @Test
    @Description("Выход по кнопке «Выйти» в личном кабинете.")
    public void logOutTestSuccess() {
        LoginPage loginPage = profilePage.clickExitButton();
        loginPage.waitLoginPage();
        assertTrue("Выход из личного кабинета не выполнен", loginPage.isLoginPage());
    }

    @After
    @Step("Удаление тестовго пользователя через API")
    public void deleteTestUser() {
        Response response = userClient.deleteUser(user);
        assertEquals("Ошибка при удалении пользователя", HttpStatus.SC_ACCEPTED, response.getStatusCode());
    }

}
