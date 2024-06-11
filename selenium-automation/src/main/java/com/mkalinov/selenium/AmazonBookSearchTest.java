package com.mkalinov.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AllureJunit5.class)
public class AmazonBookSearchTest {

  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    setupWebdriver();
  }

  @Step("Setup Webdriver")
  private void setupWebdriver() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    driver = new ChromeDriver(options);

    // TODO add implicit and explicit waits for web elements to appear before continuing to next step.
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

  }

  @AfterEach
  public void tearDown() {
    closeDriver();
  }

  @Step("Close Webdriver")
  private void closeDriver() {
    // TODO This isn't enough and sometimes the process hangs. More process handling should be done.
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  @Description("Search for a book on Amazon and verify the results")
  public void searchBookTest() {
    navigateToAmazon();
    searchForBook("Harry Potter and the Cursed Child");
    verifySearchResults();
    selectPaperbackEdition();
    verifyPaperbackDetails();
    markAsGiftAndAddToCart();
    verifyCartContents();
  }

  @Step("Navigate to Amazon UK")
  public void navigateToAmazon() {
    driver.get("https://www.amazon.co.uk/");
    assertThat(driver.getTitle()).contains("Amazon");
  }

  @Step("Search for book: {bookName}")
  public void searchForBook(String bookName) {
    WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
    searchBox.sendKeys(bookName);
    searchBox.submit();
  }

  @Step("Verify search results")
  public void verifySearchResults() {

    //TODO These should be extracted to meaningful methods and annotated with `@Step` appropriately

    WebElement firstResult = driver.findElement(By.cssSelector("[data-component-type='s-search-result']"));

    WebElement title = firstResult.findElement(By.cssSelector(".a-size-medium.a-color-base.a-text-normal"));
    assertThat(title.getText()).contains("Harry Potter and the Cursed Child");

    WebElement bookCover = firstResult.findElement(By.cssSelector(
      "[data-cel-widget=search_result_1] [data-cy=price-recipe]:first-child > div:first-child a"));
    assertThat(bookCover.getText()).contains("Paperback");

    // TODO possible additional checks: author, ratings, review snippet, delivery, buying choices, etc

    assertThat(firstResult.findElements(By.cssSelector(".a-size-medium.a-color-base.a-text-normal"))).isNotEmpty();
    assertThat(firstResult.findElements(By.cssSelector("[data-cel-widget=search_result_1] span.a-price"))).isNotEmpty();

    assertThat(driver.findElements(By.cssSelector("span.a-price"))).isNotEmpty();
  }

  @Step("Select paperback edition")
  public void selectPaperbackEdition() {
    WebElement firstResult = driver.findElement(By.cssSelector("[data-component-type='s-search-result']"));
    WebElement paperbackLink = firstResult.findElement(By.cssSelector(
      "[data-cel-widget=search_result_1] [data-cy=price-recipe]:first-child > div:first-child a"));
    paperbackLink.click();
  }

  @Step("Verify paperback details")
  public void verifyPaperbackDetails() {
    WebElement title = driver.findElement(By.id("productTitle"));
    assertThat(title.getText()).contains("Harry Potter and the Cursed Child");
  }

  @Step("Add book to basket and mark as gift")
  public void markAsGiftAndAddToCart() {
    WebElement giftOption = driver.findElement(By.cssSelector("[for='gift-wrap']"));
    giftOption.click();
    WebElement addToBasketButton = driver.findElement(By.id("add-to-cart-button"));
    addToBasketButton.click();
  }

  @Step("Verify cart contents")
  public void verifyCartContents() {
    navigateToBasket();

    step("Verify book title", () -> {
      final var basketTitle = driver.findElement(By.cssSelector(".sc-product-title"));
      assertThat(basketTitle.getText()).contains("Harry Potter and the Cursed Child");
    });

    step("Verify book cover", () -> {
      final var basketEdition = driver.findElement(By.xpath("//span[contains(text(),'Paperback')]"));
      assertThat(basketEdition.getText()).contains("Paperback");
    });

    step("Verify book price", () -> {
      final var basketPrice = driver.findElement(By.cssSelector(".sc-product-price"));
      assertThat(basketPrice.getText()).isNotEmpty();
    });

    step("Verify only one product present in cart", () -> {
      final var productsInCart = driver.findElements(By.cssSelector(".sc-list-item"));
      assertThat(productsInCart.size()).isOne();
    });

  }

  @Step("Navigate to Cart")
  private void navigateToBasket() {
    WebElement basketButton = driver.findElement(By.id("nav-cart"));
    basketButton.click();
  }
}