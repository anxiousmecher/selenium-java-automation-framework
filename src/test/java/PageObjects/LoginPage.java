package PageObjects;

import org.openqa.selenium.By;

public class LoginPage {
    public static By loginpageheader=By.xpath("//div[contains(@class,'login_logo') and (text()='Swag Labs')]");
    public static By loginpage_username_textbox=By.id("user-name");
    public static By loginpage_password_textbox=By.id("password");
    public static By loginpage_loginbutton=By.xpath("//input[contains(@class,'submit-button btn_action')]");

}
