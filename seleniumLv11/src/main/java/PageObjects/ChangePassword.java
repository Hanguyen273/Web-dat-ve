package PageObjects;

import Constant.Constant;
import org.openqa.selenium.By;

import static Constant.Constant.WEBDRIVER;

public class ChangePassword extends GeneralPage{

    private final By myChangePassword = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");

    public ChangePassword clickChangePassword() {
        WEBDRIVER.findElement(myChangePassword).click();
        return this;
    }

    public boolean isChangePasswordPageDisplayed() {
        return WEBDRIVER.getCurrentUrl().contains("ChangePassword");
    }

}
