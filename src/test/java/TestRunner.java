import com.qa.engine.DriverManager;
import com.qa.engine.GlobalParams;
import com.qa.engine.ServerManager;
import io.appium.java_client.service.local.InvalidServerInstanceException;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;

@CucumberOptions(
        plugin = {"json:target/cucumber-results/output.json",
                "rerun:target/failedScenarios.txt"},
        features = {"target/classes/features"},
        glue = {"com.qa.steps"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());

    @BeforeClass(
            alwaysRun = true
    )
    @Override
    public void setUpClass(ITestContext context) {
        try {

            new File("target", "runLogs").mkdirs();
            new File("target", "screenRecording").mkdirs();

            GlobalParams params = new GlobalParams();
            params.initializeGlobalParams();

            ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                    + params.getDeviceName());

            new ServerManager().startServer();
            super.setUpClass(context);
        } catch (InvalidServerInstanceException e) {
            LOGGER.error("Error in server instance. " + e.getMessage());
            throw new InvalidServerInstanceException(e.getMessage());
        } catch (Throwable e) {
            LOGGER.error("Class setup failed with the following error\n", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        try {
            super.runScenario(pickleWrapper, featureWrapper);
        } catch (Throwable e) {
            LOGGER.error("Scenario failed with the following error\n", e);
            throw e;
        }
    }

    @AfterClass
    public static void quit() {
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
    }
}
