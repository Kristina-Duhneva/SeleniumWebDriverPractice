package core;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public String backpackTitle = "Sauce Labs Backpack";
    public String shirtTitle = "Sauce Labs Bolt T-Shirt";
    protected final By hamburgerMenu = new By.ByXPath("//button[@id='react-burger-menu-btn']");
    protected final By resetAppButton = new By.ByXPath("//a[@id='reset_sidebar_link']");
    protected final By closeMenu = new By.ById("react-burger-cross-btn");

    @AfterEach
    public void afterTest() {
        // close driver
        driver.close();
    }

    protected static void authenticateWithUser(String username, String pass) {
        WebElement usernameInput = driver.findElement(By.xpath("//input[@data-test='username']"));
        usernameInput.sendKeys(username);

        WebElement password = driver.findElement(By.xpath("//input[@data-test='password']"));
        password.sendKeys(pass);

        WebElement loginButton = driver.findElement(By.xpath("//input[@data-test='login-button']"));
        loginButton.click();

        WebElement inventoryPageTitle = driver.findElement(By.xpath("//div[@class='app_logo']"));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
    }

    protected WebElement getProductByTitle(String title) {
        return driver.findElement(By.xpath(String.format("//div[@class='inventory_item' and descendant::div[text()='%s']]", title)));
    }

    protected static void fillShippingDetails(String firstName, String lastName, String zip) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zip);
    }

    public void resetAppState() {
        driver.findElement(hamburgerMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(resetAppButton)).click();
        driver.findElement(closeMenu).click();
    }

}