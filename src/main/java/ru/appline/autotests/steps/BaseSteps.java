package ru.appline.autotests.steps;

import org.junit.After;
import org.junit.Before;

import static ru.appline.autotests.utils.DriverManager.getDriver;

public class BaseSteps {

    public static void initDriver(){
        getDriver();
    }

    public static void tearDown() throws Exception {
        getDriver().quit();
    }
}
