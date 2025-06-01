package com.base;

import com.core.pages.SignInPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class SignInPageBase {
    SignInPage signInPageCore;

    public SignInPageBase(WebDriver driver) {
        signInPageCore = new SignInPage(driver);
    }

    @Step("Signing in to amazon.com.eg using : {0} {1}")
    public void signIn(String email, String password) {
        signInPageCore.signIn(email, password);
    }
}
