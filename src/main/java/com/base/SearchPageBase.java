package com.base;


import com.core.pages.SearchPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchPageBase {

    SearchPage searchPageCore;

    public SearchPageBase(WebDriver driver) {
        searchPageCore = new SearchPage(driver);
    }

    @Step("Selecting Filter: {0}")
    public void selectFilter(String filterName) {
        searchPageCore.selectFilter(filterName);
    }

    @Step("Sorting Results: {0}")
    public void sortBy(String option) {
        searchPageCore.sortBy(option);
    }

    @Step("Adding Products Under {0} and Listing It")
    public List<String> addProductsUnderThreshold(double priceThreshold) {
        return searchPageCore.addProductsUnderThreshold(priceThreshold);
    }

    public void focusOut() {
        searchPageCore.focusOut();
    }
}



