package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Constant.Constant;

import static Constant.Constant.WEBDRIVER;

public class RegisterPage extends GeneralPage {
    // Khai báo các locator cho các phần tử trên trang đăng ký
    private final By _txtEmail = By.id("email");
    private final By _txtPassword = By.id("password");
    private final By _txtConfirmPassword = By.id("confirmPassword");
    private final By _txtPassport = By.id("pid"); // Thêm locator cho passport
    private final By _btnRegister = By.xpath("//input[@value='Register']");
    private final By _lblErrorMessage = By.xpath("//p[@class='message error']");

    // Triển khai các phương thức để thao tác với các phần tử trên trang đăng ký
    public void enterEmail(String email) {
        WEBDRIVER.findElement(_txtEmail).sendKeys(email);
    }

    public void enterPassword(String password) {
        WEBDRIVER.findElement(_txtPassword).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WEBDRIVER.findElement(_txtConfirmPassword).sendKeys(confirmPassword);
    }

    public void enterPassport(String passport) { // Thêm phương thức nhập passport
        WebElement passportField = WEBDRIVER.findElement(_txtPassport);
       // Xóa trường passport trước khi nhập giá trị mới
        passportField.sendKeys(passport); // Nhập giá trị passport
    }

    public void clickRegisterButton() {
        WEBDRIVER.findElement(_btnRegister).click();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(_lblErrorMessage);
    }

    // Phương thức để thực hiện đăng ký với thông tin email, password, confirmPassword và passport
    public void register(String email, String password, String confirmPassword, String passport) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPassport(passport); // Thêm passport vào quá trình đăng ký
    }

    // Phương thức kiểm tra xem một phần tử có được hiển thị hay không
    private boolean isElementDisplayed(By locator) {
        try {
            return WEBDRIVER.findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }


}
