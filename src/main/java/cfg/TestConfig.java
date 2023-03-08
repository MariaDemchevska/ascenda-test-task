package cfg;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigCache;

@Config.LoadPolicy(Config.LoadType.MERGE)
public interface TestConfig extends Config {

    static TestConfig getConfig() {
        return ConfigCache.getOrCreate(TestConfig.class, System.getProperties());
    }

    @Key("CLIENT_ID")
        //assuming we are passing these credentials during test execution from Jenkins Credentials
    String clientId();

    @Key("CLIENT_SECRET")
        //assuming we are passing these credentials during test execution from Jenkins Credentials
    String clientSecret();

    @Key("BASE_URL") //Can be different accoring to ENV and can be stored in properties file for each env
    @DefaultValue("dev-5twd4ss9.auth0.com")
    String baseUrl();

    @Key("AUDIENCE") //Can be different accoring to ENV and can be stored in properties file for each env
    @DefaultValue("https://test-data-api.com/")
    String audience();
}
