package com.base;

import com.core.pages.AddressPopup;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class AddressPageBase {
    AddressPopup address;

    public AddressPageBase(WebDriver driver) {
        address = new AddressPopup(driver);
    }


    @Step("Set Full Name: {name}")
    public void setFullName(String name) {
        address.setFullName(name);
    }

    @Step("Set Mobile Number: {number}")
    public void setMobileNumber(String number) {
        address.setMobileNumber(number);
    }

    @Step("Set Street Name: {street}")
    public void setStreetName(String street) {
        address.setStreetName(street);
    }

    @Step("Set Building No: {building}")
    public void setBuildingNo(String building) {
        address.setBuildingNo(building);
    }

    @Step("Select City Area: {city}")
    public void selectCityArea(String city) {
        address.selectCityArea(city);
    }

    @Step("Select District: {district}")
    public void selectDistrict(String district) {
        address.selectDistrict(district);
    }

    @Step("Set Landmark: {landmark}")
    public void setLandmark(String landmark) {
        address.setLandmark(landmark);
    }

    @Step("Select Address Type: Home")
    public void selectAddressTypeHome() {
        address.selectAddressTypeHome();
    }

    @Step("Set Default Address: {setDefault}")
    public void setDefaultAddress(boolean setDefault) {
        address.setDefaultAddress(setDefault);
    }

    @Step("Click 'Use This Address' Button")
    public void clickUseThisAddress() {
        address.clickUseThisAddress();
    }

    @Step("Close Add Address Popup")
    public void closePopup() {
        address.closePopup();
    }

    @Step("Add New Address with provided details")
    public void addNewAddress(String fullName, String mobileNumber, String street, String building, String city, String district) {
        address.setFullName(fullName);
        address.setMobileNumber(mobileNumber);
        address.setStreetName(street);
        address.setBuildingNo(building);
        address.selectCityArea(city);
        address.selectDistrict(district);
        address.selectAddressTypeHome();
        address.clickUseThisAddress();
    }
}
