package com.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage {
    private static final By SIGNINBTN = By.id("nav-link-accountList");
    private static final By EMAILINPUT = By.id("ap_email_login");
    private static final By PASSWORDINPUT = By.id("ap_password");
    private static final By CONTINUEBTN = By.id("continue");
    private static final By SIGNINBUTTON = By.id("signInSubmit");
    private static final By PROBLEMWARNING = By.xpath("//h4[contains(text(),'There was a problem')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement getSignInBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNINBTN));
    }

    private WebElement getEmailInput() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(EMAILINPUT));
    }

    private WebElement getPasswordInput() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORDINPUT));
    }

    private WebElement getContinueBtn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CONTINUEBTN));
    }

    private WebElement getSignInButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNINBUTTON));
    }

    public void signIn(String email, String password) {
        try {
            getSignInBtn().click();
            getEmailInput().sendKeys(email);
            getContinueBtn().click();
            getPasswordInput().sendKeys(password);
            getSignInButton().click();
        } catch (NoSuchElementException e) {
            System.out.println("invalid credential");
        }
    }
}
