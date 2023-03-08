package steps;

import enums.ResponseCode;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import pojo.PostAuthResponse;
import utils.RestUtils;

import static utils.RestUtils.MEDIA_TYPE_JSON;
import static utils.RestUtils.SCHEME;

public class AuthApiSteps extends AbstractApiSteps {

    private static final String AUTH_REQUEST_PATH = "/oauth/token";

    public AuthApiSteps(RestUtils restUtils) {
        super(restUtils);
    }

    /**
     * PATH: /oauth/token
     */
    public Response sendPostAuth(String baseUrl, String clientId, String clientSecret, String audience) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(baseUrl)
                .addPathSegments(AUTH_REQUEST_PATH)
                .build();

        String jsonBody = new JSONObject()
                .put("grant_type", "client_credentials")
                .put("client_id", clientId)
                .put("client_secret", clientSecret)
                .put("audience", audience)
                .toString();

        RequestBody requestBody = RequestBody.create(jsonBody, MEDIA_TYPE_JSON);

        Request request = restUtils.postRequest(httpUrl, requestBody);
        return restUtils.send(request);
    }

    public Response sendAuthAndVerifyResponseCode(String baseUrl, String clientId, String clientSecret, String audience, ResponseCode expectedResponseCode) {
        Response response = sendPostAuth(baseUrl, clientId, clientSecret, audience);
        checkReceivedResponse(response, expectedResponseCode.getStatusCode());
        return response;
    }

    public PostAuthResponse getResponseToAuthResponse(Response response) {
        return getResponseAsClassObg(getResponseBodyAsString(response), PostAuthResponse.class);
    }

}
