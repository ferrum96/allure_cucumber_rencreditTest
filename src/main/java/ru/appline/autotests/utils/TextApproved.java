package ru.appline.autotests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class TextApproved implements ExpectedCondition<Boolean> {
    String expectedText;
    WebElement element;

    public TextApproved(String expectedText, WebElement element)
    {
        this.expectedText = expectedText;
        this.element = element;
    }

    public Boolean apply(WebDriver driver)
    {
        Boolean isTextFieldCorrect = element.getAttribute("value").replaceAll("[^\\d.]", "").equals(expectedText);
        if(!isTextFieldCorrect)
        {
            element.clear();
            element.sendKeys(expectedText);
        }
        return isTextFieldCorrect;
    }
}
