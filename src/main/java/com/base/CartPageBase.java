package com.base;

import com.core.pages.CartPage;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPageBase {
    CartPage cartPageCore;

    public CartPageBase(WebDriver driver) {
        cartPageCore = new CartPage(driver);
    }

    public void checkingCart() {
        cartPageCore.checkingCart();
    }

    public List<String> getCartContent() {
        return cartPageCore.getCartContent();
    }

    public boolean verifyCartContent(List<String> expectedTitles) {
        return cartPageCore.verifyCartContent(expectedTitles);
    }

    public void deleteAllCart() {
        cartPageCore.deleteAllCartItems();
    }

    public void proceedToCheckOut() {
        cartPageCore.proceedToCheckOut();
    }

    public void dismissPrimeButton() {
        cartPageCore.dismissPrime();
    }

    public void addNewAddress() {
        cartPageCore.addNewAddress();
    }
}
