package Tests;

import Objects.CazooSearch;
import Objects.CazooSearchResults;
;

import Tasks.CazooSearchTasks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Test_Methods_Cazoo {

    private static String currentDirectory = new File("").getAbsolutePath();


    WebDriver driver;

    @DataProvider(name = "registrations")
    public Iterator<Object[]> registrationProvider() throws IOException {
        String filename = currentDirectory + "/src/test/Resources/input";
        CazooSearchTasks m = new CazooSearchTasks();
        List<String> registrations = m.matchRegistrations(filename);
        Collection<Object[]> data = new ArrayList<Object[]>();
        registrations.forEach(item -> data.add(new Object[]{item}));
        return data.iterator();
    }


    @BeforeMethod
    public void beforeMethod() {

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.cazoo.co.uk/value-my-car/");


    }

    @Test(enabled = true, dataProvider = "registrations")
    public void searchTest(String Data) throws IOException, InterruptedException {


        String inputRegistration = Data.replaceAll("\\s+", "");
        CazooSearch page = new CazooSearch(driver);
        CazooSearchTasks m = new CazooSearchTasks();
        CazooSearchResults resultsPage = new CazooSearchResults(driver);
        page.searchCazoo(Data);


        String results = resultsPage.getResultsText();


        results = results.replace("Make/model: ", "");

        int index = m.indexOf("\\s+", results, 2);

        String inputMake = results.substring(0, index);
        String inputModel = results.substring(index + 1);

        String assertionLine = m.readOutputFile(currentDirectory + "/src/test/Resources/output", Data);

        // to do improvement string cleansing in a separate method

        String output[] = assertionLine.split("[,]", 0);
        String outputRegistration = output[0];
        String outputMake = output[1];
        String outputModel = output[2];

        Assert.assertEquals(inputRegistration, outputRegistration);
        Assert.assertEquals(inputMake, outputMake);
        Assert.assertEquals(inputModel, outputModel);
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();

    }

}
