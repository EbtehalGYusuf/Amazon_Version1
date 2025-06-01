import com.base.*;
import com.utils.Configurations;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Amazon")
@Feature("Amazon Purchase")
@Listeners({AllureTestNg.class, com.utils.AllureTestListener.class})
public class AmazonPurchase extends Configurations {
    MainPageBase mainPage;
    SearchPageBase searchPage;
    SignInPageBase signInPage;
    CartPageBase cartPage;
    AddressPageBase address;

    @BeforeMethod
    public void amazonPurchaseBeforeMethod() {
        WebDriver driver = Configurations.getDriver();  // Retrieve the initialized driver
        mainPage = new MainPageBase(driver);
        searchPage = new SearchPageBase(driver);
        signInPage = new SignInPageBase(driver);
        cartPage = new CartPageBase(driver);
        address = new AddressPageBase(driver);
    }

    @Test(enabled = true)

    @Description("Purchase video games less than 15k")
    public void case01_SearchFilterSelectSpecificVideo()
            throws InterruptedException {
        System.out.println(
                "Thread ID: " + Thread.currentThread().getId() + " | Driver: " + getDriver());
        signInPage.signIn("1201049324", "TestAutomation13");
        mainPage.selectCategory("Shop by Category", "Video Games");
        mainPage.selectSubCategory("Video Games", "All Video Games");
        searchPage.selectFilter("Free Shipping");
        searchPage.selectFilter("New");
        searchPage.sortBy("Price: High to Low");
        List<String> purchasedProducts = searchPage.addProductsUnderThreshold(15000);
        Thread.sleep(2000);
        cartPage.checkingCart();
        Thread.sleep(2000);
        System.out.println("purchasedProducts" + purchasedProducts);
        System.out.println("cartProducts" + cartPage.getCartContent());
        Assert.assertEquals(cartPage.getCartContent().size(), purchasedProducts.size());
        Assert.assertTrue(cartPage.getCartContent().containsAll(purchasedProducts));
        cartPage.proceedToCheckOut();
        cartPage.dismissPrimeButton();
        cartPage.addNewAddress();
        address.addNewAddress("Ebtehal Gamal", "0123456789", "Salah Salem", "5A", "New Cairo", "10 (1st Settlement)");
    }

    @AfterMethod
    public void amazonPurchaseAfterMethod() {
        cartPage.checkingCart();
        cartPage.deleteAllCart(); //Cleaning Cart to rerun case
        mainPage.deleteAddresses();  //Clearing addresses
    }

}