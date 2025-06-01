package com.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddressPopup {
    //Adresss
    private static final By ADD_ADDRESS = By.xpath("//span[contains(@id,'add-new-address')]");
    private static final By FULL_NAME = By.xpath("//input[contains(@id,'address-ui-widgets-enterAddressFullName')]");
    private static final By MOBILE_NUMBER = By.xpath("//input[contains(@id,'address-ui-widgets-enterAddressPhoneNumber')]");
    private static final By STREET_NAME = By.xpath("//input[contains(@id,'address-ui-widgets-enterAddressLine1')]");
    private static final By BUILDING_NO = By.xpath("//input[contains(@id,'enter-building-name-or-number')]");
    private static final By CITY_AREA = By.xpath("//input[contains(@id,'address-ui-widgets-enterAddressCity')]");
    private static final By DISTRICT = By.xpath("//input[contains(@id,'address-ui-widgets-enterAddressDistrict')]");
    private static final By LANDMARK = By.xpath("//input[contains(@id,'address-ui-widgets-landmark')]");
    private static final By ADDRESS_TYPE_HOME = By.xpath("//input[contains(@name,'address-type-radio')]");
    private static final By DEFAULT_ADDRESS_CHECKBOX = By.xpath("//input[contains(@name,'address-ui-widgets-use-as-my-default')]");
    private static final By USE_THIS_ADDRESS_BUTTON = By.xpath("//input[contains(@data-testid,'bottom-continue-button')]");
    private static final By CLOSE_POPUP_BUTTON = By.xpath("//button[contains(@aria-label,'Close')]");

    private final WebDriver driver;
    private final WebDriverWait wait;


    public AddressPopup(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement getFullNameField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME));
    }

    private WebElement getMobileNumberField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MOBILE_NUMBER));
    }

    private WebElement getStreetNameField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(STREET_NAME));
    }

    private WebElement getBuildingNoField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BUILDING_NO));
    }

    private WebElement getCityAreaOptionXPath(String city) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'" + city + "')]")));
    }

    private WebElement getDistrictOptionXPath(String district) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'" + district + "')]")));
    }

    private WebElement getCityAreaField() {
        return wait.until(ExpectedConditions.elementToBeClickable(CITY_AREA));
    }

    private WebElement getDistrictField() {
        return wait.until(ExpectedConditions.elementToBeClickable(DISTRICT));
    }

    private WebElement getLandmarkField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LANDMARK));
    }

    private WebElement getHomeRadio() {
        return wait.until(ExpectedConditions.elementToBeClickable(ADDRESS_TYPE_HOME));
    }

    // 🔸 Address Methods

    public void setFullName(String name) {
        getFullNameField().clear();
        getFullNameField().sendKeys(name);
    }

    public void setMobileNumber(String number) {
        getMobileNumberField().clear();
        getMobileNumberField().sendKeys(number);
    }

    public void setStreetName(String street) {
        getStreetNameField().clear();
        getStreetNameField().sendKeys(street);
    }

    public void setBuildingNo(String building) {
        getBuildingNoField().clear();
        getBuildingNoField().sendKeys(building);
    }
    
    public void selectCityArea(String city) {
        getCityAreaField().click();
        getCityAreaField().clear();
        getCityAreaField().sendKeys(city);
        getCityAreaOptionXPath(city).click();
    }

    public void selectDistrict(String district) {
        getDistrictField().click();
        getDistrictField().clear();
        getDistrictField().sendKeys(district);
        getDistrictOptionXPath(district).click();
    }

    public void setLandmark(String landmark) {
        getLandmarkField().clear();
        getLandmarkField().sendKeys(landmark);
    }

    public void selectAddressTypeHome() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getHomeRadio());
    }

    public void setDefaultAddress(boolean setDefault) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(DEFAULT_ADDRESS_CHECKBOX));
        if (checkbox.isSelected() != setDefault) {
            checkbox.click();
        }
    }

    public void clickUseThisAddress() {
        wait.until(ExpectedConditions.elementToBeClickable(USE_THIS_ADDRESS_BUTTON)).click();
    }

    public void closePopup() {
        wait.until(ExpectedConditions.elementToBeClickable(CLOSE_POPUP_BUTTON)).click();
    }
}
