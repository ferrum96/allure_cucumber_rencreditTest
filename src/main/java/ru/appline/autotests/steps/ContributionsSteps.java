package ru.appline.autotests.steps;

import io.qameta.allure.Step;
import ru.appline.autotests.pages.ContributionsPage;

import static org.junit.Assert.assertTrue;
import static ru.appline.autotests.pages.BasePage.waittextToBePresentInElement;

public class ContributionsSteps extends BaseSteps {

    public ContributionsPage contributionsPage = new ContributionsPage();

    @Step("Выбран срок вклада {0}")
    public ContributionsSteps selectTerm(String term){
        contributionsPage.selectTerm(term);
        return this;
    }

    @Step("Поле {0} заполняется значением {1}")
    public ContributionsSteps fillField(String field, String value){
        contributionsPage
                .scrollToElement(ContributionsPage.form,65)
                .fillField(field, value);
        return this;
    }

    @Step("Выполнено нажатие на кнопку {0}")
    public ContributionsSteps clickElement(String name){
        contributionsPage.clickOnButton(name);
        return this;
    }

    @Step("Поле {0} заполнено значением {1}")
    public ContributionsSteps checkCalcValue(String field, String value) {
        if (waittextToBePresentInElement(ContributionsPage.withdraw, value) == false){
            String actual = ContributionsPage.withdraw.getText();
            assertTrue(String.format("Значение поля [%s] равно [%s]. Ожидалось - [%s]", field, actual, value),
                    actual.equals(value));
        }
        return this;
    }

}
