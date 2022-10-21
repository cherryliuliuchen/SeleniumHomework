package com.tesing.web;

import com.tesing.DriverSelf.GoogleDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class WebKeyword {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void openBrowser(String browserType){
        switch (browserType) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "DriverExe\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", "DriverExe\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "DriverExe\\msedgeDriver.exe");
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                GoogleDriver gg=new GoogleDriver("DriverExe\\\\chromedriver.exe");
                driver=gg.getDriver();
        }
        setBrowserSize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public void setBrowserSize(){
        Point point=new Point(200,20);
        driver.manage().window().setPosition(point);
        Dimension range=new Dimension(1622,1000);
        driver.manage().window().setSize(range);
    }

    public void visitWeb(String url){
        System.out.println("We are visiting "+url);
        driver.get(url);
    }

    public void input(String xpath,String content){
        WebElement element = driver.findElement(By.xpath(xpath));
        element.clear();
        element.sendKeys(content);
    }

    public void click(String xpath){
        try{
            driver.findElement(By.xpath(xpath)).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void switchFrame(String locator){
        try {
            if (locator.startsWith("//")) {
                WebElement iframe = driver.findElement(By.xpath(locator));
                driver.switchTo().frame(iframe);

            } else {
                driver.switchTo().frame(locator);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void halt(String second){
        try {

            float time = Float.parseFloat(second);
            int millis = (int) (time * 1000);
            Thread.sleep(millis);
        } catch (Exception e){
            System.out.println("There are some problems on waiting "+e.fillInStackTrace());
        }

    }

    public void select(String xpath,String value){
        try {
            Select select = new Select(driver.findElement(By.xpath(xpath)));
            if (value.startsWith("{value}")) {
                select.selectByValue(value.substring(7));
                System.out.println(value.substring(7));
            } else if (value.startsWith("{text}")) {
                select.selectByVisibleText(value.substring(6));
            } else {
                select.selectByVisibleText(value);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Positioning the select element for operation, failed");
        }
    }

    public void switchTop(){
       try {
           driver.switchTo().defaultContent();
       } catch (Exception e){
           e.printStackTrace();
           System.out.println("Switch failed");
       }
    }

    public boolean assertEleText(String locator, String expctedResult){
        try{
            WebElement ele;
            if(locator.startsWith("/")){
                ele=driver.findElement(By.xpath(locator));
            }else if(locator.startsWith("#")||locator.startsWith(".")||locator.contains("[")){
                ele=driver.findElement(By.cssSelector(locator));
            }else{
                ele=driver.findElement(By.id(locator));
            }
            if(ele.getText().contains(expctedResult)){
                System.out.println("Test successfully");
                return true;
            }else{
                System.out.println("Test failed");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed, location failed");
            return false;
        }
    }

    public void closeBrowser(){
        driver.quit();
    }
}
