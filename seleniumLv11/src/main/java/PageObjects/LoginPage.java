package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static Constant.Constant.WEBDRIVER;

public class LoginPage extends GeneralPage{
    private static final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");
    public static WebElement getTxtUsername(String username){return WEBDRIVER.findElement(_txtUsername);}

    public WebElement getTxtPassword(String password){
        return WEBDRIVER.findElement(_txtPassword);
    }
    public WebElement getBtnLogin(){
        return WEBDRIVER.findElement(_btnLogin);
    }
    public WebElement getLblLoginErrorMsg(){
        return WEBDRIVER.findElement(_lblLoginErrorMsg);
    }
    public HomePage login(String username, String password)
    {
        this.getTxtUsername(username).sendKeys(username);
        this.getTxtPassword(password).sendKeys(password);
        scrollToElement(getBtnLogin());
        this.getBtnLogin().click();
        return new HomePage();
    }
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) WEBDRIVER;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }




}
