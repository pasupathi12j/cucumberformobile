package com.qa.pages.securramvp;

import com.qa.pages.BasePage;
import com.qa.utils.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class LoginPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='button']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@resource-id='button']")
    protected WebElement getStartedButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=' Verify Your Mobile Number']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@resource-id='button']")
    protected WebElement verifyYourMobileNumberText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Mobile Number']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@resource-id='button']")
    protected WebElement mobileNumberfield;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Linked Mobile Number']/following-sibling::android.widget.EditText[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Linked Mobile Number\"]//following-sibling::XCUIElementTypeOther/XCUIElementTypeTextField")
    protected WebElement mobileNumber;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'successfully')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name, 'successfully')]")
    protected WebElement fetchSuccessMessage;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='You’re all set,ready to go!']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='You’re all set,ready to go!']")
    protected WebElement successMessage;


    @Override
    protected void waitForAppToLoad() {

    }

    public void clickOnGetStartedButton() {
        String parentAttribute = "";
        String attribute = "";

        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.widget.Button";
            attribute = "resource-id";
        } else if (getDriver() instanceof IOSDriver) {
            parentAttribute = "XCUIElementTypeButton";
            attribute = "name";
        }
        utils.waitForElement(getDriver(), "//" + parentAttribute + "[@" + attribute + "=\"button\"]", 60);
        click(getStartedButton, "Clicking on get started button");
    }

    public String verifyYourMobileNumberText() {
        String parentAttribute = "";
        String attribute = "";
        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.widget.TextView";
            attribute = "text";
        } else if (getDriver() instanceof IOSDriver) {
            attribute = "text";
        }
        utils.waitForElement(getDriver(), "//" + parentAttribute + "[@" + attribute + "=\" Verify Your Mobile Number\"]", 60);
        return getTextWithAttribute(verifyYourMobileNumberText, attribute);
    }

    public void enterMobileNumber() {
        String parentAttribute = "";
        String attribute = "";

        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.view.View";
            attribute = "content-desc";
        } else if (getDriver() instanceof IOSDriver) {
            parentAttribute = "XCUIElementTypeStaticText";
            attribute = "name";
        }
        utils.waitForElement(getDriver(), "//" + parentAttribute + "[@" + attribute + "='Mobile Number']", 90);
        clickAndSendKeys(mobileNumberfield, new TestUtils().getRandomNumber(5), "Entering the mobile number");

        if (getDriver() instanceof IOSDriver)
            getDriver().findElement(By.xpath("//XCUIElementTypeButton[@name=\"Done\"]")).click();
    }

    public LoginPage enterMobileNumber(String number) {
        String parentAttribute = "";
        String parentAttribute2 = "";
        String attribute = "";

        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.view.View";
            parentAttribute2 = "android.widget.EditText";
            attribute = "content-desc";
        } else if (getDriver() instanceof IOSDriver) {
            parentAttribute = "XCUIElementTypeStaticText";
            parentAttribute2 = "XCUIElementTypeTextField";
            attribute = "name";
        }
        if (getDriver() instanceof AndroidDriver)
            utils.waitForElement(getDriver(), "//" + parentAttribute + "[@" + attribute + "='Linked Mobile Number']/following-sibling::" + parentAttribute2 + "[1]", 90);
        else {
            utils.waitForElement(getDriver(), "//XCUIElementTypeStaticText[@name=\"Linked Mobile Number\"]//following-sibling::XCUIElementTypeOther/XCUIElementTypeTextField", 90);
            click(By.xpath("//XCUIElementTypeButton[@name=\"Done\"]"));
        }

        clickAndSendKeys(mobileNumber, number, "Linked MobileNumber entered as " + number);
        return this;
    }

    public String successfulMessage() {
        String parentAttribute = "";
        String attribute = "";

        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.view.View";
            attribute = "content-desc";
        } else if (getDriver() instanceof IOSDriver) {
            parentAttribute = "XCUIElementTypeStaticText";
            attribute = "name";
        }

        utils.waitForElement(getDriver(), "//" + parentAttribute + "[contains(@" + attribute + ", 'successfully')]", 60);
        return getTextWithAttribute(fetchSuccessMessage, attribute);
    }

    public boolean successMessage() {
        String parentAttribute = "";
        String attribute = "";

        if (getDriver() instanceof AndroidDriver) {
            parentAttribute = "android.view.View";
            attribute = "content-desc";
        } else if (getDriver() instanceof IOSDriver) {
            parentAttribute = "XCUIElementTypeOther";
            attribute = "name";
        }

        utils.waitForElement(getDriver(), "//" + parentAttribute + "[@" + attribute + "='You’re all set,ready to go!']", 90);
        return successMessage.isDisplayed();
    }

    public void clickOnEnter() {
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();
    }
}
