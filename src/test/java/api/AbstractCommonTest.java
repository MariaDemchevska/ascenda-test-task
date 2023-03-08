package api;

import cfg.TestConfig;
import org.testng.annotations.BeforeSuite;
import steps.AuthApiSteps;
import utils.RestUtils;

public class AbstractCommonTest {
    protected RestUtils restUtils;
    protected AuthApiSteps authApiSteps;
    protected static TestConfig CONFIG = TestConfig.getConfig();

    @BeforeSuite(alwaysRun = true)
    public void init() {
        restUtils = new RestUtils();
        authApiSteps = new AuthApiSteps(restUtils);
    }
}
