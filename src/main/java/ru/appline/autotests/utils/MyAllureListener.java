package ru.appline.autotests.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.junit4.AllureJunit4;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import static ru.appline.autotests.utils.DriverManager.getDriver;

public class MyAllureListener extends AllureJunit4 {

    @Override
    public void testFailure(Failure failure) {
        saveScreenshot();
        super.testFailure(failure);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
    }
    
}
