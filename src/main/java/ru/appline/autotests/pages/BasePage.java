package ru.appline.autotests.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.autotests.utils.TextApproved;

import static ru.appline.autotests.utils.DriverManager.getDriver;

public class BasePage {

    public static WebDriverWait wait = new WebDriverWait(getDriver(),10);

    BasePage() {
        PageFactory.initElements(getDriver(), this);
    }


    public void fillField(WebElement field, String value){
        waitVisibilityOfElement(field);
        waitClickableOfElement(field);
        field.click();
        field.clear();
        field.sendKeys(value);
        wait.until(new TextApproved(value,field));
    }

    public static WebElement waitVisibilityOfElement(WebElement element){
        wait = new WebDriverWait(getDriver(),100);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitClickableOfElement(WebElement element){
        wait = new WebDriverWait(getDriver(),10);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waittextToBePresentInElement(WebElement element, String text){
       try {
           wait.until(ExpectedConditions.textToBePresentInElement(element,text));
           return true;
       }catch (Exception e){
           return false;
       }

    }

}
