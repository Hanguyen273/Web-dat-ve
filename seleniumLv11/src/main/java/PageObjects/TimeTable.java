package PageObjects;

import org.openqa.selenium.By;

import static Constant.Constant.WEBDRIVER;

public class TimeTable extends GeneralPage {
    private final By myTimeTable = By.xpath("//a[@href='/Page/Contact.cshtml']");
    public TimeTable clickTimeTable() {
        WEBDRIVER.findElement(myTimeTable).click();
        return this;
    }
}
