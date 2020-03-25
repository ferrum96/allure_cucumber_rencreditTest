import org.junit.*;
import ru.appline.autotests.steps.BaseSteps;
import ru.appline.autotests.steps.ContributionsSteps;
import ru.appline.autotests.steps.MainSteps;

public class RencreditTest2 {

    public MainSteps mainPageSteps = new MainSteps();
    public ContributionsSteps contributionsSteps = new ContributionsSteps();

    @BeforeClass
    public static void before(){
        BaseSteps.initDriver();
    }

    @Test
    public void allureRencreditTest2(){

        mainPageSteps
                .stepSelectMenu("Вклады","рубли");
        contributionsSteps
                .selectTerm("9 месяцев")
                .fillField("Сумма вклада","500000")
                .fillField("Ежемесячное пополнение","70000")
                .clickElement("Ежемесячная капитализация")
                .checkCalcValue("К снятию", "717 675,35");
    }

    @AfterClass
    public static void after() throws Exception {
        BaseSteps.tearDown();
    }
}
