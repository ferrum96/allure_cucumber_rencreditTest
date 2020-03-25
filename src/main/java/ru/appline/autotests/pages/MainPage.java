package ru.appline.autotests.pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static ru.appline.autotests.utils.DriverManager.getDriver;

public class MainPage extends BasePage{

    @FindBy(xpath = "//div/div[@class='service__title-text']")
    List<WebElement> mainMenuItem;

    @FindBy(xpath = "//span/span[contains(@class,'currency')]")
    List<WebElement> mainSubMenu;

    public MainPage(){PageFactory.initElements(getDriver(),this);}

    public void selectMainMenuItem(String menuItem){

        for (WebElement element: mainMenuItem){
            if(element.getText().equalsIgnoreCase(menuItem)){
                BasePage.waitVisibilityOfElement(element);
                Actions actions = new Actions(getDriver());
                actions.moveToElement(element).click().build().perform();
                return;
            }
        }
        Assert.fail("Нет такого меню " + menuItem);
    }

    public void selectMainSubMenuItem(String subMenuItem){
        for (WebElement element: mainSubMenu){
            if(element.getText().equalsIgnoreCase(subMenuItem)){
                BasePage.waitVisibilityOfElement(element).click();
                return;
            }
        }
        Assert.fail("Нет такого меню " + subMenuItem);
    }

}
