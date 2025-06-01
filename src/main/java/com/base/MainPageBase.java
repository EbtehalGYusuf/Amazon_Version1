package com.base;

import com.core.MainPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class MainPageBase {
    MainPage mainPageCore;

    public MainPageBase(WebDriver driver) {
        mainPageCore = new MainPage(driver);
    }

    @Step("Select language: {language}")
    public void selectLanguage(String language) {
        mainPageCore.selectLanguage(language);
    }

    @Step("Select category: Section='{section}', Category='{category}'")
    public void selectCategory(String section, String category) {
        mainPageCore.selectCategory(section, category);
    }

    @Step("Select subcategory: Category='{categoryName}', Subcategory='{subCategoryName}'")
    public void selectSubCategory(String categoryName, String subCategoryName) {
        mainPageCore.selectSubCategory(categoryName, subCategoryName);
    }

    @Step("Addresses Deletion")
    public void deleteAddresses() {
        mainPageCore.deleteAddresses();
    }

}
