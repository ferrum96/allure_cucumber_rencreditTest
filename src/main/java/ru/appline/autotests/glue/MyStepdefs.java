package ru.appline.autotests.glue;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import io.cucumber.datatable.DataTable;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.appline.autotests.steps.ContributionsSteps;
import ru.appline.autotests.steps.MainSteps;
import java.util.Map;

import static ru.appline.autotests.steps.BaseSteps.initDriver;
import static ru.appline.autotests.steps.BaseSteps.tearDown;
import static ru.appline.autotests.utils.DriverManager.getDriver;

public class MyStepdefs {

        public MainSteps mainSteps = new MainSteps();
        public ContributionsSteps contributionsSteps = new ContributionsSteps();

        @Когда("^выбрано меню \"(.+)\" и подменю \"(.+)\"$")
        public void выбрано_меню(String menuItem,String subMenuItem){
            mainSteps.stepSelectMenu(menuItem,subMenuItem);
        }

        @Когда("^выбран срок вклада \"(.+)\"$")
        public void выбран_срок_вклада(String term){
        contributionsSteps.selectTerm(term);
    }

        @Когда("^заполняются поля:$")
        public void заполняются_поля(DataTable fields){
            Map<String,String> dataMap = fields.asMap(String.class, String.class);
            dataMap.forEach((field, value) -> { contributionsSteps.fillField(field, value); });
        }

        @Когда("^выполнено нажатие на кнопку \"(.+)\"$")
        public void нажатие_на_кнопку(String name) throws InterruptedException {
            contributionsSteps.clickElement(name);
        }

        @Тогда("^значение поля \"(.+)\" равно \"(.+)\"$")
        public void значения_поля(String field, String value) {
            contributionsSteps.checkCalcValue(field, value);
        }

        @Before
        public void before(){
            initDriver();
        }

        @Attachment(value = "Page screenshot", type = "image/png")
        public static byte[] saveScreenshot() {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        }

        @After
        public void after(Scenario sc) throws Exception {
        if(sc.isFailed()) saveScreenshot();
        tearDown();
        }

}

