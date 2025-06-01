package com.core.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private static final By CART = By.id("nav-cart");
    private static final By CART_HEADER = By.xpath("//h2[contains(text(),'Shopping Cart')]");
    private static final By CART_ITEMS_TITLE = By.xpath("//div[contains(@data-csa-c-owner,'Cart') and contains(@data-itemtype,'active')]//h4//span[contains(@class,'a-truncate-full a-offscreen')]");
    private static final By DELETE = By.xpath("//input[@data-feature-id='item-delete-button']");
    private static final By CHECKOUT = By.xpath("//input[contains(@value,'checkout')]");
    private static final By AMAZON_PRIME_DISMISS = By.id("prime-decline-button");
    private static final By SECURED_CHECKOUT_HEADER = By.xpath("//a[contains(@id,'nav-checkout')]");
    //Address
    private static final By ADD_ADDRESS = By.xpath("//span[contains(@id,'add-new-address')]");


    private final WebDriver driver;
    private final WebDriverWait wait;


    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebElement getCart() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART));
    }

    public WebElement getPrimeDismissBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(AMAZON_PRIME_DISMISS));
    }

    public WebElement getCheckoutBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CHECKOUT));
    }

    public WebElement getAddAddressBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_ADDRESS));
    }

    public WebElement getCheckoutHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SECURED_CHECKOUT_HEADER));
    }

    public WebElement getCartHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART_HEADER));
    }

// Core Methods

    public void checkingCart() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getCart());
        getCartHeader();
    }

    public void proceedToCheckOut() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        getCheckoutBtn().click();
        getCheckoutHeader();
    }

    public void dismissPrime() {
        if (!driver.findElements(AMAZON_PRIME_DISMISS).isEmpty()) {
            driver.findElement(AMAZON_PRIME_DISMISS).click();
        } else {
            System.out.println("Prime window not found, skipping dismiss.");
        }
    }


    public void addNewAddress() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        getAddAddressBtn().click();
    }

    public List<String> getCartContent() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CART_ITEMS_TITLE));
        List<WebElement> cartItems = driver.findElements(CART_ITEMS_TITLE);
        List<String> cartTitles = new ArrayList<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement item : cartItems) {
//            js.executeScript("arguments[0].scrollIntoView(true);", item);  // Scroll to item
            String title = (String) js.executeScript("return arguments[0].textContent;", item);
//            String title = item.getAttribute("textContent").trim();
            cartTitles.add(title);
        }
        return cartTitles;
    }

    public void deleteAllCartItems() {
        wait.until(ExpectedConditions.visibilityOf(getCartHeader()));

        while (true) {
            try {
                By deleteButtonLocator = By.xpath("//input[@value='Delete' or contains(@aria-label,'حذف')]");
                WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteButtonLocator));
                deleteButton.click();
                wait.until(ExpectedConditions.stalenessOf(deleteButton));
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("All items deleted from the cart.");
                break;
            }
        }

        System.out.println("All items deleted from the cart.");
    }

    public boolean verifyCartContent(List<String> expectedTitles) {
        getCart().click();
        wait.until(ExpectedConditions.visibilityOf(getCartHeader()));
        List<WebElement> cartItems = driver.findElements(CART_ITEMS_TITLE);
        List<String> cartTitles = cartItems.stream()
                .map(item -> item.getAttribute("textContent").trim())
                .collect(Collectors.toList());
        return cartTitles.containsAll(expectedTitles);
    }

}
