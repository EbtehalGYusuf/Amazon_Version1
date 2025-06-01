package com.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class SearchPage {
    private static final By RESULTS_HEADER = By.xpath("//h2[text()='Results']");
    private static final By SORTBY = By.xpath("//select[contains(@id,'s-result-sort-select')]");
    private static final By SORTBY_CONTENT = By.xpath("//div[contains(@class,'popover')]/ul//li[contains(@aria-labelledby,'sort-select')]//a[contains(@id,'result-sort')]");
    private static final By FILTERS = By.xpath("//div[contains(@id,'s-refinements')]//ul//li//a/span");
    private static final By PRODUCT_ITEMS = By.xpath("//div[@data-component-type='s-search-result']");
    private static final By ADDTOCART = By.xpath(".//button[@name='submit.addToCart']");
    private static final By DECREMENTITEM = By.xpath(".//button[@aria-label='Decrease quantity by one']");
    private static final By NEXTPAGE = By.xpath("//a[contains(@aria-label,'Go to next page')]");
    private static final By PAGINATION = By.xpath("//span[contains(@class,'pagination-strip')]//ul//li//a[contains(@class,'pagination')]");
    private static final By WARNING = By.xpath("//div[contains(@aria-label,'Sorry, there was a problem')]");
    private static final By CLOSE = By.xpath("//button[contains(@aria-label,'Close')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement getResultsHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(RESULTS_HEADER));
    }

    private WebElement getSortBy() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SORTBY));
    }

    private List<WebElement> getSortByOptions() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SORTBY_CONTENT));
    }

    private List<WebElement> getFilters() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(FILTERS));
    }

    private List<WebElement> getProductItems() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_ITEMS));
    }

    private WebElement getNextPageBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NEXTPAGE));
    }

    private WebElement getPageNumberBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAGINATION));
    }

    public void selectFilter(String filterName) {
        boolean found = false;
        for (WebElement filter : getFilters()) {
            String text = filter.getText().trim();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filter);
            if (text.equalsIgnoreCase(filterName)) {
                filter.click();
                System.out.println("Selected: " + filter);
                wait.until(ExpectedConditions.visibilityOf(getResultsHeader())); //Wait till Results Header appears
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Filter not found: " + filterName);
        }
    }

    public int currentPageNumber() {
        try {
            String text = getPageNumberBtn().getText().trim();
            int pageNumber = Integer.parseInt(text);
            return pageNumber;
        } catch (Exception e) {
            System.out.println("Failed to get current page number: " + e.getMessage());
            return -1;  // Or return 1 as a default
        }
    }

    public void sortBy(String option) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getSortBy());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getSortBy());
        for (WebElement item : getSortByOptions()) {
            if (item.getText().equalsIgnoreCase(option)) {
                System.out.println("sortby" + item);
                wait.until(ExpectedConditions.elementToBeClickable(item)).click();

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);

                wait.until(ExpectedConditions.visibilityOf(getResultsHeader()));
                break;
            }
        }
    }

    public void focusOut() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getResultsHeader());
        getResultsHeader().click();
    }

    public List<String> addProductsUnderThreshold(double priceThreshold) {
        List<String> addedProductTitles = new ArrayList<>();
        Set<String> seenProducts = new HashSet<>();

        while (true) {
            System.out.println("Searching products on page " + currentPageNumber() + "...");
            List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[@data-component-type='s-search-result']")));

            List<WebElement> matchingProducts = new ArrayList<>();

            for (WebElement product : products) {
                try {
                    String title = getProductTitle(product);
                    if (seenProducts.contains(title)) continue;

                    String priceText = product.findElement(By.xpath(".//span[@class='a-price-whole']")).getText();
                    double price = Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));

                    if (price < priceThreshold) {
                        matchingProducts.add(product);
                        Thread.sleep(1000);

                        seenProducts.add(title);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping product: " + e.getMessage());
                }
            }

            if (!matchingProducts.isEmpty()) {
                System.out.println("Found matching products. Adding all and stopping pagination.");
                for (WebElement product : matchingProducts) {
                    try {
                        String title = getProductTitle(product);
                        System.out.println("Adding: " + title);
                        product.findElement(ADDTOCART).click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(DECREMENTITEM));
                        addedProductTitles.add(title);
                    } catch (Exception e) {
                        System.out.println("Failed to add product: " + e.getMessage());
                    }
                }
                break; // Stop pagination after processing this page
            }
            // Move to next page if available
            if (!goToNextPage(products)) {
                System.out.println("No more pages. Ending search.");
                break;
            }
        }
        System.out.println("Total products added: " + addedProductTitles.size());
        System.out.println("Added products: " + addedProductTitles);
        return addedProductTitles;
    }

    private String getProductTitle(WebElement product) {
        try {
            String title = product.findElement(By.xpath(".//h2[contains(@class,'a-size-mini') or contains(@aria-label,'')]")).getText().trim();
            if (title.isEmpty()) {
                title = product.findElement(By.xpath(".//h2[contains(@class,'a-size-mini') or contains(@aria-label,'')]")).getAttribute("aria-label").trim();
            }
            return title;
        } catch (Exception e) {
            return "Unknown Product";
        }
    }

    public void dismissAddToCartErrorIfPresent() {
        try {
            WebElement errorCloseButton = driver.findElement(By.xpath("//button[contains(@aria-label,'Close')]"));
            if (errorCloseButton.isDisplayed()) {
                errorCloseButton.click();
                System.out.println("Dismissed Add to Cart error popup.");
            }
        } catch (NoSuchElementException e) {
        }
    }

    private boolean goToNextPage(List<WebElement> products) {
        try {
            WebElement nextPage = getNextPageBtn();
            if (nextPage.isDisplayed()) {
                nextPage.click();
                wait.until(ExpectedConditions.stalenessOf(products.get(0)));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Next page not found or error: " + e.getMessage());
        }
        return false;
    }


}
