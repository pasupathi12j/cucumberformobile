package com.qa.steps.securraMvp;

import com.qa.engine.DriverManager;
import com.qa.engine.ProjectBase;
import com.qa.pages.securramvp.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class loginUser extends ProjectBase {

    @Given("I have launched the mvp app")
    public void IHaveLaunchedTheMvpApp() {
        try {
            if (getDriver() != null)
                getDriver().quit();
            new DriverManager().initializeDriver(MVP);
            logInfo("MVP app is launched");
        } catch (Exception e) {
            logInfo("Error while launching the MVP app");
            throw new RuntimeException(e);
        }
    }

    @And("I click on the get started button")
    public void iClickOnTheGetStartedButton() {
        new LoginPage().clickOnGetStartedButton();
    }

    @Then("I navigate to Verify your Mobile Number screen")
    public void iNavigateToVerifyYourMobileNumberScreen() {
        String referenceMessage = new LoginPage().verifyYourMobileNumberText();
        logInfo(referenceMessage);
        Assert.assertTrue(referenceMessage.contains
                        (" Verify Your Mobile Number"),
                "Assertion on Verify your Mobile Number screen");
    }

}
