package api;

import enums.ResponseCode;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.JWTPayload;
import pojo.PostAuthResponse;
import utils.JWTUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthRequestTest extends AbstractCommonTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestTest.class);
    private static final String SCOPE_VALUE_VERIFY_REGEX_PATTERN = "(get:invoices\\Wget:clients)";
    private static final String EXPECTED_ISS_VALUE = "https://dev-5twd4ss9.auth0.com/";
    private static final String EXPECTED_AUD_VALUE = "https://test-data-api.com";

    @DataProvider(name = "client_data_and_expected_status_codes")
    public static Object[][] getData() {
        return new Object[][]{
                {CONFIG.baseUrl(), CONFIG.clientId(), CONFIG.clientSecret(), CONFIG.audience(), ResponseCode.OK},
                {null, null, null, null, ResponseCode.BAD_REQUEST}
        };
    }

    @Test(description = "Send valid /oauth/token request",
            dataProvider = "client_data_and_expected_status_codes")
    public void sendAuthRequestAndCheckStatusCodeEqualsExpectedTest(String baseUrl, String clientId, String clientSecret,
                                                                    String audience, ResponseCode expectedResponseCode) {
        LOGGER.info("Sending /oauth/token request");
        Response response = authApiSteps.sendAuthAndVerifyResponseCode(baseUrl, clientId,
                clientSecret, audience, expectedResponseCode);

        LOGGER.info("Asserting status code");
        Assert.assertEquals(response.code(), expectedResponseCode.getStatusCode(), "Asserting actual /oauth/token response equals expected");
    }


    @Test(description = "Send valid /oauth/token request and check token is valid")
    public void getAndCheckTokenIsValidTest() {
        LOGGER.info("Sending /oauth/token request");
        Response response = authApiSteps.sendAuthAndVerifyResponseCode(CONFIG.baseUrl(), CONFIG.clientId(),
                CONFIG.clientSecret(), CONFIG.audience(), ResponseCode.OK);

        LOGGER.info("Validating JWT token");
        PostAuthResponse postAuthResponse = authApiSteps.getResponseToAuthResponse(response);
        String tokenFromResponse = postAuthResponse.getToken();
        JWTPayload jwtPayload = JWTUtils.getJWTPayloadAsObj(JWTUtils.decodeJwtAndGetPayload(tokenFromResponse));
        Assert.assertEquals(jwtPayload.getIss(), EXPECTED_ISS_VALUE, "Decoded ISS value should be equal to: " + EXPECTED_ISS_VALUE);
        Assert.assertEquals(jwtPayload.getAud(), EXPECTED_AUD_VALUE, "Decoded AUD value should be equal to: " + EXPECTED_AUD_VALUE);
        Assert.assertTrue(verifyScopeContainsOnlyExpectedValue(SCOPE_VALUE_VERIFY_REGEX_PATTERN, jwtPayload.getScope()),
                "Decoded SCOPE value should match expected regex: " + SCOPE_VALUE_VERIFY_REGEX_PATTERN);
        Assert.assertEquals(postAuthResponse.getExpiresIn(), jwtPayload.getIat(),
                "Decoded IAT vaule should be equal to  \"expires_in\" in the response");
    }

    private boolean verifyScopeContainsOnlyExpectedValue(String expectedScopeValueRegex, String actualScopeValue) {
        Pattern p = Pattern.compile(expectedScopeValueRegex);
        Matcher m = p.matcher(actualScopeValue);
        return m.matches();
    }
}
