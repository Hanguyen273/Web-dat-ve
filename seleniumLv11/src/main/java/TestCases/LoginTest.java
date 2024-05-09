package TestCases;

import PageObjects.*;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Constant.Constant;

import java.util.List;

import static Constant.Constant.*;
import static Constant.Constant.WEBDRIVER;

public class LoginTest {
    @BeforeMethod
    public void beforeTest(){
        System.out.println("Pre-condition");
//        System.setProperty("webdriver.chrome.driver",Utilities.getProjectPath()
//                + "\\Executables\\chromedriver.exe");
        WEBDRIVER = new ChromeDriver();
        WEBDRIVER.manage().window().maximize();


    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("Post-condition");
        //Constant.WEBDRIVER.quit();
    }
    @Test
    public void TC01(){
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();;
        String actualMsg = loginPage.login(USERNAME, PASSWORD).getWelcomeMessage();
        String expectMsg = "Welcome " + USERNAME;
        Assert.assertEquals(actualMsg,expectMsg,"Welcome message is not displayed as expected");
    }
    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", PASSWORD);
        //Thực hiện thao tác đăng nhập với tên người dùng rỗng và mật khẩu được lấy từ hằng số Constant.PASSWORD

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText(); //Lấy thông báo lỗi thực tế xuất hiện sau khi thử đăng nhập.
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC03(){
        System.out.println("User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login(USERNAME, " ");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message 'There was a problem with your login and/or errors exist in your form.' is displayed");

    }
    @Test
    public void TC04(){
        System.out.println("Login page displays when un-logged User clicks on 'Book ticket' tab");
        HomePage homePage= new HomePage();
        homePage.open();
        LoginPage loginPage =homePage.gotoLoginPage();
        WebElement changePasswordLink = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']"));
        changePasswordLink.click();
        WebElement loginForm = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']"));
        Assert.assertTrue(loginForm.isDisplayed(), "Login page did not display when un-logged user clicked on 'Book ticket' tab");
    }
    @Test
    public void TC05() {
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        for (int i = 0; i < 4; i++) {
            loginPage.login(USERNAME, "invalidPassword");
        }
        loginPage.login(USERNAME, "invalidPassword");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC06() {
        System.out.println("TC06 - Additional pages display once user logged in");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoLoginPage();

        // Step 3: "Login with valid account"
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify "My ticket", "Change password", and "Logout" tabs are displayed
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordPageDisplayed(), "Change password tab is not displayed");
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Logout tab is not displayed");

        // Click "My ticket" tab and verify redirection
        MyTicketPage myTicketPage = homePage.clickMyTicketTab();
        Assert.assertTrue(myTicketPage.isMyTicketPageDisplayed(), "User is not redirected to My ticket page");

        // Click "Change password" tab and verify redirection
        ChangePassword changePasswordPage = homePage.clickChangePassword();
        Assert.assertTrue(changePasswordPage.isChangePasswordPageDisplayed(), "User is not redirected to Change password page");
    }
    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");
        HomePage homePage = new HomePage();
        homePage.open();

        // Bước 1: Click on "Register" tab
        WebElement registerTab = WEBDRIVER.findElement(By.linkText("Register"));
        registerTab.click();

        JavascriptExecutor js = (JavascriptExecutor) WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Bước 2: Nhập thông tin hợp lệ vào tất cả các trường
        WebElement emailField = WEBDRIVER.findElement(By.id("email"));
        WebElement passwordField = WEBDRIVER.findElement(By.id("password"));
        WebElement confirmPasswordField = WEBDRIVER.findElement(By.id("confirmPassword"));
        WebElement passportField = WEBDRIVER.findElement(By.id("pid"));
        // Điền thông tin email, mật khẩu và xác nhận mật khẩu vào các trường
        String email = "nguyenlinh3@gmail.com";
        String password = "2345678910";
        String passportNumber = "044303000857";
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        passportField.sendKeys(passportNumber);

        // Bước 3: Click on "Register" button
        WebElement registerButton = WEBDRIVER.findElement(By.xpath("//input[@value='Register']"));
        registerButton.click();

        // Kiểm tra xem thông báo đã xuất hiện hay không
        WebElement successMessage = null;
        try {
            successMessage = WEBDRIVER.findElement(By.xpath("//div[@class='success-message']"));
            // Nếu thông báo xuất hiện, so sánh với thông báo mong đợi
            String expectedMessage = "Thank you for registering your account";
            Assert.assertEquals(successMessage.getText(), expectedMessage);
            System.out.println("Thực tế: " + successMessage.getText());
            System.out.println("Mong đợi: " + expectedMessage);
        } catch (NoSuchElementException e) {
            // Nếu không có thông báo xuất hiện, đánh dấu là thất bại
            System.out.println("Failed to create new account. No success message found.");
            Assert.fail("Failed to create new account. No success message found.");
        }
    }
    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");

        // Giả sử bạn đã có một trang đăng nhập và một tài khoản chưa được kích hoạt
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Nhập thông tin tài khoản chưa được kích hoạt
        String username = "example@gmail.com";
        String password = "password123";
        loginPage.login(username, password);

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");

    }
    @Test
    public void TC09(){
        System.out.println("TC09 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage= homePage.gotoLoginPage();
        loginPage.login(USERNAME, PASSWORD);
        // 3. Click on "Change Password" tab
        WebElement changePasswordLink = Constant.WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']"));
        changePasswordLink.click();
        // 4. Enter valid value into all fields
        WebElement currentPasswordInput = Constant.WEBDRIVER.findElement(By.id("currentPassword"));
        currentPasswordInput.sendKeys(PASSWORD);

        WebElement newPasswordInput = Constant.WEBDRIVER.findElement(By.id("newPassword"));
        newPasswordInput.sendKeys("123456789");

        WebElement confirmPasswordInput = Constant.WEBDRIVER.findElement(By.id("confirmPassword"));
        confirmPasswordInput.sendKeys("123456789");

        // 5. Click on "Change Password" button
        WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.cssSelector("input[value='Change Password']"));
        changePasswordButton.click();

        // Kiểm tra xem thông báo "Your password has been updated" xuất hiện hay không
        String actualMessage = Constant.WEBDRIVER.findElement(By.xpath("//p[@class='message success']")).getText();

        String expectedMessage = "Your password has been updated!";
        Assert.assertEquals(actualMessage, expectedMessage, "Message 'Your password has been updated' does not appear.");
    }
    @Test
    public void TC10() {
        System.out.println("User can't create account with \"Confirm password\" is not the same with \"Password\"");
        System.out.println("TC10 - User can't create account with 'Confirm password' is not the same with 'Password'");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // Step 3: Enter valid information except "Confirm password" is not the same with "Password"
        String email = "test@example.com";
        String password = "password";
        String confirmPassword = "notthesamepassword";
        String passport = "123456789";
        registerPage.register(email, password, confirmPassword, passport);

        // Step 4: Click on "Register" button
        registerPage.clickRegisterButton();

        // Verify message "There're errors in the form. Please correct the errors and try again." appears
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message is not displayed as expected");
    }
    @Test
    public void TC11() {
        System.out.println("TC10 - User can't create account while password and PID fields are empty");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // Step 3: Enter valid information except "Confirm password" is not the same with "Password"
        String email = "test@example.com";
        String password = "";
        String confirmPassword = "";
        String passport = "";
        registerPage.register(email, password, confirmPassword, passport);

        // Step 4: Click on "Register" button
        registerPage.clickRegisterButton();

        String actualErrorMessage = String.valueOf(registerPage.isErrorMessageDisplayed());
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message is not as expected");
        // Kiểm tra xem thông báo lỗi "Invalid password length." hiển thị gần trường password hay không
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Password error message is not displayed");

        // Kiểm tra xem thông báo lỗi "Invalid ID length." hiển thị gần trường PID hay không
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "PID error message is not displayed");
    }
    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Navigate to QA Railway Login page
        WebElement forgotPasswordLink = Constant.WEBDRIVER.findElement(By.linkText("Forgot Password page"));
        forgotPasswordLink.click();

        // Enter the email address of the created account
        WebElement emailField = Constant.WEBDRIVER.findElement(By.id("email"));
        emailField.sendKeys(USERNAME);

        // Click on "Send Instructions" button
        WebElement sendInstructionsButton = Constant.WEBDRIVER.findElement(By.cssSelector("input[type='submit'][value='Send Instructions']"));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", sendInstructionsButton);
        sendInstructionsButton.click();
    }
    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Navigate to QA Railway Login page
        WebElement forgotPasswordLink = WEBDRIVER.findElement(By.linkText("Forgot Password page"));
        forgotPasswordLink.click();

        // Enter the email address of the created account
        WebElement emailField = WEBDRIVER.findElement(By.id("email"));
        emailField.sendKeys(USERNAME);
        WebElement sendInstructionsButton = WEBDRIVER.findElement(By.xpath("//input[@value='Send Instructions']"));
        ((JavascriptExecutor) WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", sendInstructionsButton);
        // Click on "Send Instructions" button
        sendInstructionsButton.click();}
    @Test
    public void TC14() {
        System.out.println("User can book 1 ticket at a time");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with a valid account
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(USERNAME, PASSWORD);

        // Step 3: Click on "Book ticket" tab
        BookTicketPage bookTicketPage = homePage.clickBookTicketTab();

        // Step 4: Chọn ngày khởi hành bất kỳ từ danh sách (ví dụ: 5 ngày sau)
        bookTicketPage.selectDepartDate(5);

        // Step 5: Chọn "Sài Gòn" cho "Depart from" và "Nha Trang" cho "Arrive at"
        bookTicketPage.selectDepartFrom("Sài Gòn");
        bookTicketPage.selectArriveAt("Nha Trang");

        // Step 6: Chọn "Soft bed with air conditioner" cho "Seat type"
        bookTicketPage.selectSeatType("Soft bed with air conditioner");

        // Step 7: Chọn "1" cho "Ticket amount"
        bookTicketPage.selectTicketAmount("1");

        // Step 8: Nhấp vào nút "Book ticket"
        bookTicketPage.clickBookTicketButton();

        // Xác nhận hành vi mong đợi: Hiển thị thông báo "Ticket booked successfully!"
        Assert.assertTrue(bookTicketPage.isTicketBookedSuccessfullyDisplayed(), "Ticket booked successfully message is not displayed");

        // Xác nhận thông tin vé hiển thị chính xác và in ra kết quả
        boolean ticketDetailsMatch = bookTicketPage.verifyTicketDetails("Sài Gòn", "Nha Trang", "Soft bed with air conditioner", bookTicketPage.getDepartDate(), "1");
        System.out.println("Depart Station: " + bookTicketPage.getDepartStation());
        System.out.println("Arrive Station: " + bookTicketPage.getArriveStation());
        System.out.println("Seat Type: " + bookTicketPage.getSeatType());
        System.out.println("Depart Date: " + bookTicketPage.getDepartDate());
        System.out.println("Amount: " + bookTicketPage.getAmount());
        Assert.assertTrue(ticketDetailsMatch, "Ticket details do not match.");
    }
    @Test
    public void TC15() {
        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        boolean timeTable = homePage.clickTimeTableTab();
    }
    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        BookTicketPage bookTicketPage = homePage.clickBookTicketTab();

        // Step 4: Chọn ngày khởi hành bất kỳ từ danh sách (ví dụ: 5 ngày sau)
        bookTicketPage.selectDepartDate(5);

        // Step 5: Chọn "Sài Gòn" cho "Depart from" và "Nha Trang" cho "Arrive at"
        bookTicketPage.selectDepartFrom("Quảng Ngãi");
        bookTicketPage.selectArriveAt("Nha Trang");

        // Step 6: Chọn "Soft bed with air conditioner" cho "Seat type"
        bookTicketPage.selectSeatType("Soft seat");

        // Step 7: Chọn "1" cho "Ticket amount"
        bookTicketPage.selectTicketAmount("1");

        // Step 8: Nhấp vào nút "Book ticket"
        bookTicketPage.clickBookTicketButton();
        // Step 4: Click on "My ticket" tab
        // Step 4: Click on "My ticket" tab
        // Step 4: Click on "My ticket" tab
        WebElement myTicketTab = WEBDRIVER.findElement(By.linkText("My ticket"));
        myTicketTab.click();

        // Locate and click on the cancel buttons for each ticket
        List<WebElement> cancelButtons = Constant.WEBDRIVER.findElements(By.xpath("//input[@value='Cancel']"));
        if (!cancelButtons.isEmpty()) {
            WebElement cancelButton = cancelButtons.get(0); // Select the first cancel button
            // Extract ID of the ticket
            String onClickValue = cancelButton.getAttribute("onclick");
            String idString = onClickValue.split("\\(")[1].split("\\)")[0];
            int id = Integer.parseInt(idString);

            // Scroll to the cancel button and click
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
            cancelButton.click();

            // Handle the confirmation alert
            try {
                Alert alert = Constant.WEBDRIVER.switchTo().alert();
                alert.accept(); // Accept the alert
            } catch (NoAlertPresentException e) {
                // No alert present, continue execution
            }
            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Validate if the ticket is no longer visible on the "My ticket" page
            boolean isTicketCancelled = !WEBDRIVER.getPageSource().contains(Integer.toString(id));
            if (isTicketCancelled) {
                System.out.println("Ticket with ID " + id + " has been successfully cancelled.");
            } else {
                System.out.println("Ticket cancellation failed for ID " + id);
            }
        } else {
            System.out.println("No tickets available to cancel.");
        }
    }
}

