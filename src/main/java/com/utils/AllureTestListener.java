package com.utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureTestListener implements ITestListener {

  @Override
  public void onTestFailure(ITestResult result) {
    // Capture screenshot on failure
    WebDriver driver =
        Configurations.getDriver(); // Replace this with how you get your WebDriver instance
    if (driver != null) {
      byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      saveScreenshotPNG(screenshot);
    }
  }

  @Attachment(value = "Failure Screenshot", type = "image/png")
  public byte[] saveScreenshotPNG(byte[] screenshot) {
    return screenshot;
  }

  // Other TestNG listener methods (you can leave these empty if you don't need them)
  @Override
  public void onTestStart(ITestResult result) {}

  @Override
  public void onTestSuccess(ITestResult result) {}

  @Override
  public void onTestSkipped(ITestResult result) {}

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onStart(org.testng.ITestContext context) {}

  @Override
  public void onFinish(org.testng.ITestContext context) {}
}
