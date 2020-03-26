package ru.appline.autotests.glue;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static ru.appline.autotests.steps.BaseSteps.openBrowser;
import static ru.appline.autotests.steps.BaseSteps.tearDown;

public class Hooks {

    @Before
    public static void before(){
        openBrowser();
    }

    @After
    public static void after() throws Exception {
        tearDown();
    }

}
