package PageObjects;
import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import static Constant.Constant.WEBDRIVER;

public class HomePage extends GeneralPage {
    public HomePage open() {
        WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }
    public MyTicketPage clickMyTicketTab() {
        By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
        WEBDRIVER.findElement(myTicketTab).click();
        return new MyTicketPage();
    }

    public ChangePassword clickChangePassword() {
        By myChangePassword = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
        WEBDRIVER.findElement(myChangePassword).click();
        return new ChangePassword();
    }
    public LoginPage clickLogoutTab(By logoutTab) {
        WEBDRIVER.findElement(logoutTab).click();
        return new LoginPage();
    }

    private boolean isElementDisplayed(By elementLocator) {
        try {
            return WEBDRIVER.findElement(elementLocator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    public RegisterPage gotoRegisterPage() {
        By registerTab = By.xpath("//a[@href='/Account/Register.cshtml']");
        WEBDRIVER.findElement(registerTab).click();
        return new RegisterPage();
    }

    public BookTicketPage clickBookTicketTab() {
        WebElement bookTicketTab = WEBDRIVER.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"));
        bookTicketTab.click();
        return new BookTicketPage();
    }

    public boolean isMyTicketTabDisplayed() {
        By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
        return WEBDRIVER.findElement(myTicketTab).isDisplayed();
    }

    public boolean isChangePasswordPageDisplayed() {
        By myChangePassword = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
        return WEBDRIVER.findElement(myChangePassword).isDisplayed();}

    public boolean isLogoutTabDisplayed() {
        By logoutTab = By.xpath("//a[@href='/Account/Logout']");;
        return WEBDRIVER.findElement(logoutTab).isDisplayed();
    }

    public boolean clickTimeTableTab() {
        By myTimeTable = By.xpath("//a[@href='/Page/Contact.cshtml']");
        return WEBDRIVER.findElement(myTimeTable).isDisplayed();
    }
}