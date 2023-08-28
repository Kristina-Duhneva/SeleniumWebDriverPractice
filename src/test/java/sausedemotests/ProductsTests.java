package sausedemotests;


import core.BaseTest;
import org.example.BrowserTypes;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static core.BaseBrowser.staticBrowser;

public class ProductsTests extends BaseTest {


    @BeforeAll
    public static void beforeAllTests() {
        driver = staticBrowser(BrowserTypes.CHROME_HEADLESS);

        // Configure wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Navigate to Google.com
        driver.get("https://www.saucedemo.com/");

        authenticateWithUser("standard_user", "secret_sauce");
    }

    @BeforeEach
    public void beforeEachTests() {
        WebElement backpack = getProductByTitle(backpackTitle);
        backpack.findElement(By.className("btn_inventory")).click();

        var tshirt = getProductByTitle(shirtTitle);
        tshirt.findElement(By.className("btn_inventory")).click();

        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart() {

        // Assert Items and Totals
        var items = driver.findElements(By.className("inventory_item_name"));

        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        Assertions.assertEquals(backpackTitle, items.get(0).getText(), "Item title not as expected");
        Assertions.assertEquals(shirtTitle, items.get(1).getText(), "Item title not as expected");

    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {

        // Assert Items and Totals
        driver.findElement(By.id("checkout")).click();

        // fill form
        fillShippingDetails("First name", "Last name", "Zip Code");

        driver.findElement(By.id("continue")).click();

        var items = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        var total = driver.findElement(By.className("summary_total_label")).getText();
        double expectedPrice = 29.99 + 15.99 + 3.68;
        String expectedTotal = String.format("Total: $%.2f", expectedPrice);

        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertEquals(backpackTitle, items.get(0).getText(), "Item title not as expected");
        Assertions.assertEquals(shirtTitle, items.get(1).getText(), "Item title not as expected");
        Assertions.assertEquals(expectedTotal, total, "Items total price not as expected");
        resetAppState();

    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {

        driver.findElement(By.id("checkout")).click();

        // fill form
        fillShippingDetails("First name", "Last name", "Zip code");

        driver.findElement(By.id("continue")).click();

        var items = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        driver.findElement(By.id("finish")).click();
        var finish = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));

        Assertions.assertEquals("Thank you for your order!", finish.getText(), "Search result not found");
        driver.findElement(By.className("shopping_cart_link")).click();

        var item = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertEquals(0, item.size(), "Shopping cart is not empty");


    }
}