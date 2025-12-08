package Utility;

import org.openqa.selenium.WebElement;

public class commonactions {
    public static void clickelement(WebElement element)
    {
        element.click();
    }
    public static void inputdata(WebElement element, String data)
    {
        element.sendKeys(data);
    }
}
