package com.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

// Before and After Method is included here
public class Configurations {

    // ThreadLocal for WebDriver to ensure each thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // A method to get the WebDriver instance for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"browser", "url"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(
            @Optional("chrome") String browser, @Optional("https://www.amazon.eg/") String url) {
        // Initialize WebDriver using DriverFactory and set it to the current thread
        driver.set(DriverFactory.initDriver(browser));
        // Navigate to the URL
        driver.get().get(url);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Quit the WebDriver for the current thread
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Remove the WebDriver from the current thread
        }
    }
}
