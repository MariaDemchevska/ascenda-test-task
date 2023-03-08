package steps;

import com.google.gson.Gson;
import okhttp3.Response;
import utils.RestUtils;

import java.io.IOException;

public abstract class AbstractApiSteps {

    protected RestUtils restUtils;

    public AbstractApiSteps(RestUtils restUtils) {
        this.restUtils = restUtils;
    }

    public <T> T getResponseAsClassObg(String responseBody, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(responseBody, classOfT);
    }

    public void checkReceivedResponse(Response response, int expectedResponseCode) {
        if (response.code() != expectedResponseCode) {
            throw new RuntimeException("Response status code doesn't match expected!");
        }
    }

    public String getResponseBodyAsString(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get response body as string", e);
        }
    }

}
