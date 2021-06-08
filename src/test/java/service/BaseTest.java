package service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.Serializable;

public class BaseTest implements Serializable {
    public WebDriver webDriver;

    public void clickMethod(By by) {
        webDriver.findElement(by).click();
    }

    public void sendKeyMethod(By by, String key) {
        webDriver.findElement(by).sendKeys(key);
    }

    public void clearMethod(By by) {
        webDriver.findElement(by).clear();
    }


    public boolean isElementPresent(By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            return false ;
        }
    }
}
