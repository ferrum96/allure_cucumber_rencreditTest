package ru.appline.autotests.steps;

import ru.appline.autotests.utils.DriverManager;

import static ru.appline.autotests.utils.DriverManager.getDriver;
import static ru.appline.autotests.utils.DriverManager.initDriver;

public class BaseSteps {

    public static void openBrowser(){
        initDriver();
    }

    public static void tearDown() throws Exception {
        getDriver().close();
    }
}
