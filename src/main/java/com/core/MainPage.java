package com.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private static final By ARABICLANG = By.xpath("//a[contains(@href,'switch-lang=ar')]");
    private static final By ENGLISHLANG = By.xpath("//a[contains(@href,'switch-lang=en')]");
    private static final By LANGUAGE = By.xpath("//a[contains(@href,'topnav_lang')]");
    private static final By ALLMENUBTN = By.xpath("//a[@aria-label='Open All Categories Menu']");
    private static final By LEFTSIDEMENU = By.xpath("//div[@data-menu-id='1']");
    private static final By SEEALLCATEGORIES = By.xpath("//a[@aria-label='See All Categories']");
    private static final By BACKTOMAINMENU = By.xpath("//a[@aria-label='Back to main menu']");
    private static final By SERACHLEFTPANEL = By.xpath("//div[contains(@class,'search-refinements-leftnav')]");
    private static final By MAIN_MENU = By.id("nav-link-accountList");
    private static final By Your_ADDRESSES = By.id("nav_prefetch_youraddresses");
    private static final By ADDRESS_DELETE = By.xpath("//a[contains(@id,'address-delete')]");
    private static final By SUBMIT_DELETE = By.xpath("//span[contains(@id,'deleteAddress')]//input[@type='submit']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement getLanguage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LANGUAGE));
    }

    private WebElement getArabicLang() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARABICLANG));
    }

    private WebElement getEnglishLang() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ENGLISHLANG));
    }

    private WebElement getAllMenuBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ALLMENUBTN));
    }

    private WebElement getLeftSideMenu() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LEFTSIDEMENU));
    }

    private WebElement getSeeAllCategories() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SEEALLCATEGORIES));
    }

    public List<WebElement> getCategoriesInSubCategory(String categoryName) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//section[contains(@aria-labelledby,'" + categoryName + "')]//ul//li//a")));
    }

    public void selectLanguage(String language) {
        getLanguage().click();
        if (language.equalsIgnoreCase("arabic")) {
            getArabicLang().click();
        } else {
            getEnglishLang().click();
        }
    }

    public void selectCategory(String section, String category) {
        getAllMenuBtn().click();  //Opening Left Menu
        getLeftSideMenu().isDisplayed();
        getSeeAllCategories().click();
        List<WebElement> categories = driver.findElements(By.xpath("//section[contains(@aria-labelledby,'" + section + "')]//ul//li//a//div"));
        String Stringsss = categories.get(14).getText();
        for (WebElement cat : categories) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cat);
            if (cat.getText().trim().equalsIgnoreCase(category)) {
                cat.click();
                wait.until(ExpectedConditions.visibilityOfAllElements(getCategoriesInSubCategory(category)));
                break;
            }
        }
    }

    public void waitForPageLoading() {
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void selectSubCategory(String categoryName, String subCategoryName) {
        boolean found = false;
        List<WebElement> subCategories = getCategoriesInSubCategory(categoryName);
        for (WebElement subCategory : subCategories) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subCategory);

            String text = subCategory.getText().trim();
            System.out.println("hi" + text);
            if (text.equalsIgnoreCase(subCategoryName)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subCategory);
                System.out.println("Clicked subCategory: " + subCategoryName);
                wait.until(ExpectedConditions.visibilityOfElementLocated(SERACHLEFTPANEL));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Category not found: " + subCategoryName);
        }
    }

    public void hoverOverMainMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mainMenu = wait.until(ExpectedConditions.presenceOfElementLocated(MAIN_MENU));

        // Create Actions instance
        Actions actions = new Actions(driver);

        // Hover over the main menu
        actions.moveToElement(mainMenu).perform();
    }

    public void deleteAddresses() {
        try {
            // Hover over main menu to open dropdown
            hoverOverMainMenu();

            // Click Your Addresses in the dropdown
            driver.findElement(Your_ADDRESSES).click();

            // Delete addresses
            List<WebElement> deleteButtons = driver.findElements(ADDRESS_DELETE);
            for (WebElement deleteButton : deleteButtons) {
                deleteButton.click();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Error deleting addresses: " + e.getMessage());
        }
    }
}
