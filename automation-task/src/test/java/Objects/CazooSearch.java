package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CazooSearch {

    WebDriver driver;

    public CazooSearch(WebDriver driver) {
        this.driver = driver;


    }

    By searchBox = By.id("vrm");
    By acceptCookies = By.xpath("//span[text()='Accept All']/parent::*");
    By startValuation = By.xpath("//span[text()='Start valuation']/parent::*");


    public void searchCazoo(String registration) {

        driver.findElement(searchBox).sendKeys(registration);
        driver.findElement(acceptCookies).click();
        driver.findElement(startValuation).click();

    }

}