package pageObject;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    WebDriver driver;
    public BasePage(WebDriver driver){
        this.driver = driver;
    }
    public abstract LoginPage clickLoginLink();

}
