package ru.appline.autotests.steps;

import io.qameta.allure.Step;
import ru.appline.autotests.pages.MainPage;

public class MainSteps extends BaseSteps {

    MainPage mainPage = new MainPage();

    @Step("Выбран пункт {menuItem} - подменю {subMenuItem}")
    public void stepSelectMenu(String menuItem, String subMenuItem){
        mainPage.selectMainMenuItem(menuItem);
        mainPage.selectMainSubMenuItem(subMenuItem);
    }


}
