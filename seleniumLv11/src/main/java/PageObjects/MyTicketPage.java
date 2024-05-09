package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Constant.Constant;

import static Constant.Constant.WEBDRIVER;

public class MyTicketPage extends GeneralPage {
    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");

    public MyTicketPage clickMyTicketTab() {
        WEBDRIVER.findElement(myTicketTab).click();
        return this;
    }

    public boolean isMyTicketPageDisplayed() {
        return WEBDRIVER.getCurrentUrl().contains("ManageTicket");
    }
}
