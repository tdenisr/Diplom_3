import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import pageObject.MainPage;

import static config.Constants.MAIN_PAGE_URL;
import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {

    MainPage mainPage;

    @Before
    @Step("Открытие главной страницы")
    public void init() {
        driver.get(MAIN_PAGE_URL);
        mainPage = new MainPage(driver);
        mainPage.waitMainPage();
    }

    @Test
    @Description("Переход на раздел Булки")
    public void getBunsSectionSuccess() {
        int expectedLocation = mainPage.getIngredientsBlock();
        mainPage.clickBunsLink();
        assertEquals("Не произошла прокрутка до блока Булки", expectedLocation, mainPage.getBunsHeader());
    }

    @Test
    @Description("Переход на раздел Соусы")
    public void getSauceSectionSuccess() {
        int expectedLocation = mainPage.getIngredientsBlock();
        mainPage.clickSauceLink();
        assertEquals("Не произошла прокрутка до блока Соусы", expectedLocation, mainPage.getSauceHeader());
    }

    @Test
    @Description("Переход на раздел Начинки")
    public void getFillingSectionSuccess() {
        int expectedLocation = mainPage.getIngredientsBlock();
        mainPage.clickFillingLink();
        assertEquals("Не произошла прокрутка до блока Начинки", expectedLocation, mainPage.getFillingHeader());
    }

}
