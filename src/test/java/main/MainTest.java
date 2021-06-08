package main;

import model.DataID;
import service.BaseTest;

import java.io.Serializable;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.Test;
import service.ReadListID;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainTest extends BaseTest implements Serializable {
    ReadListID readListID = new ReadListID();
    List<DataID> list = new ArrayList<>();
    //    link file ex to pc
    String excelFilePath = "D:\\CRW\\Excel\\05282021L9.xlsx";


    public String takeElementSnapShot(WebElement webElement, String idImage) {
        //Create file path
        String screenshotName = idImage + ".png";
//        link chua image to pc
        String screenshotPath = "D:\\CRW\\Images\\05282021\\L9\\" + screenshotName;

        // Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webElement);
        // Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        // Move image file to new destination
        File DestFile = new File(screenshotPath);
        // Copy file at destination
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return screenshotPath;
    }

    @Test
    public void Test() throws Exception {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.get("https://client.crowdworks.kr/member/login");
//        form đăng nhập
        sendKeyMethod(By.xpath("//*[@name=\"email\"]"), "tien.vu@lqa.com.vn");
        sendKeyMethod(By.xpath("//*[@name=\"password\"]"), "NVgde2ZSMTW4swB");
        clickMethod(By.xpath("//*[@class=\"css-2jtqid\"]"));

//        go to project
        clickMethod(By.xpath("//*[@data-group-id=\"423\"]"));
        clickMethod(By.xpath("(//*[@data-project-id=\"7723\"])[1]"));
//        click project

        clickMethod(By.xpath("//*[@data-index=\"5\"]"));
        clickMethod(By.xpath("(//*[@class=\"css-jf130o\"])[1]"));


        String winHandleBefore = webDriver.getWindowHandle();
        // get to popup twitter handle
        Set<String> handles = webDriver.getWindowHandles();
        String PopupHandle = null;
        for (String h : handles) {
            if (!h.equals(winHandleBefore)) {
                PopupHandle = h;
            }
        }
// Switch back to original browser (first window)
        webDriver.switchTo().window(PopupHandle);


        String startDate = "2021-05-28";
        String endDate = "2021-05-28";
        int result = 200;
        int record = 81;

//        select Search period
        WebElement searchPeriodElt = webDriver.findElement(By.xpath("//*[@name=\"rangingField\"]"));
        Select select = new Select(searchPeriodElt);
        select.selectByVisibleText("Submit work date");

        // Start date
        WebElement startDateElt = webDriver.findElement(By.xpath("(//*[@placeholder=\"YYYY-MM-DD\"])[1]"));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('readonly','readonly')", startDateElt);
        new Actions(webDriver).click(startDateElt).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT).sendKeys(Keys.BACK_SPACE).sendKeys(startDate).perform();
        // end date
        WebElement endDateElt = webDriver.findElement(By.xpath("(//*[@placeholder=\"YYYY-MM-DD\"])[2]"));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('readonly','readonly')", endDateElt);
        new Actions(webDriver).click(endDateElt).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT).sendKeys(Keys.BACK_SPACE).sendKeys(endDate).perform();
        // click Search
        By clickButton = By.xpath("(//*[@type=\"button\"])[34]");

        // click Search
        webDriver.findElement(By.xpath("//*[@class=\"project-monitor-board css-486dx3\"]")).click();
        webDriver.findElement(By.xpath("//*[@class=\"css-1ypq19w\"]")).click();
        Thread.sleep(2000);
        if (isElementPresent(clickButton)) {
            for (int i = 1; i < record; i++) {
                clickMethod(By.xpath("(//*[@type=\"button\"])[34]"));
            }
        }
        Thread.sleep(40000);

        By byStatus = By.xpath("//*[@class=\"sample\"]/dd/em");
        By getLandmark = By.xpath("//*[@class=\"content-group  css-1rw3nf5\"]//li[2]/div/p");
        By byIframe = By.xpath("//iframe[@title=\"monitor\"]");

        int check = 0;
        for (int i = 0; i < 1000; i++) {
            try {
                String namClass = "";
                String status = "";
                String workName = "";
                String landmark = "";
                String id = "";
                List<WebElement> list_data = webDriver.findElements(By.xpath("//*[@data-id]"));
                List<WebElement> listStatus = webDriver.findElements(By.xpath("//*[@id=\"root\"]/div[1]/section/ul/li/div/ul/li[3]/div/section[2]/table/tbody/tr/td[2]"));
                List<WebElement> listWorkName = webDriver.findElements(By.xpath("//*[@id=\"root\"]/div[1]/section/ul/li/div/ul/li[3]/div/section[2]/table/tbody/tr/td[3]"));
                System.out.println("id = " + list_data.size());

                for (int j = 0, list_dataSize = list_data.size(); j < list_dataSize; j++) {
                    try {
                        WebElement dataIdButton = list_data.get(j);
//                click vao ID
//                    lay id
                        id = dataIdButton.getText();
                        status = listStatus.get(j).getText();
                        workName = listWorkName.get(j).getText();
                        dataIdButton.click();
//                chuyển iframe
                        if (isElementPresent(byIframe)) {
                            WebElement forIframe = new WebDriverWait(webDriver, 10)
                                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//iframe[@title=\"monitor\"]")));

                            webDriver.switchTo().frame(forIframe);
//                    get text landmark
                            WebElement clickZoom = new WebDriverWait(webDriver, 10)
                                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class=\"css-1s93314\"])[2]")));
                            clickZoom.click();
                            if (isElementPresent(getLandmark)) {
                                WebElement getTextLandmark = new WebDriverWait(webDriver, 10)
                                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"content-group  css-1rw3nf5\"]//li[2]/div/p")));
//                    chup hinh
                                WebElement imageBox = new WebDriverWait(webDriver, 10)
                                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"upper-canvas fabric-canvas\"]")));
                                if (isElementPresent(byStatus)) {
                                    WebElement statusClass = new WebDriverWait(webDriver, 10)
                                            .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"sample\"]/dd/em")));
                                    Thread.sleep(1000);
                                    namClass = statusClass.getText();
                                    landmark = getTextLandmark.getText();
                                    takeElementSnapShot(imageBox, id);
                                    webDriver.switchTo().defaultContent();
//                add vao list CSV
                                    DataID dataID = new DataID(id, namClass, status, workName, landmark);
                                    list.add(dataID);
                                    namClass = "";
                                    id = "";
                                    status = "";
                                    workName = "";
                                    landmark = "";
                                    check++;
                                    System.out.println("cố bản ghi : " + check);
                                    if (check == result) {
                                        return;
                                    }
                                } else {
                                    DataID dataID = new DataID(id, namClass, status, workName, landmark);
                                    list.add(dataID);
                                    webDriver.switchTo().defaultContent();
                                }
                            } else {
                                DataID dataID = new DataID(id, namClass, status, workName, landmark);
                                list.add(dataID);
                                webDriver.switchTo().defaultContent();
                            }
                        } else {
                            DataID dataID = new DataID(id, namClass, status, workName, landmark);
                            list.add(dataID);
                            webDriver.switchTo().defaultContent();
                        }
                    } catch (Exception e) {
                        System.out.println("lag");
                        DataID dataID = new DataID(id, namClass, status, workName, landmark);
                        list.add(dataID);
                        webDriver.switchTo().defaultContent();
                    }
                }
                clickMethod(By.xpath("(//*[@type=\"button\"])[34]"));
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("lỗi");
            }
        }
    }

    @AfterMethod
    public void afterMethod() throws Exception {
        readListID.writeExcel(list, excelFilePath);

        webDriver.close();
    }
}
