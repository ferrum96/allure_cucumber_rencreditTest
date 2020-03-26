package ru.appline.autotests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver driver;

    private DriverManager(){}

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static TestProperties props = TestProperties.getInstance();

    public static void initDriver(){

        switch ("chrome"){
            case "firefox":
                System.setProperty("webdriver.gecko.driver", props.getProperty("firefoxdriver"));
                driver = new FirefoxDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", props.getProperty("chromedriver"));
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty("timeout.global")), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty("timeout.pageLoad")), TimeUnit.SECONDS);
        driver.get(props.getProperty("url"));
    }
}
