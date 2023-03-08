package utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class RestUtils {

    private OkHttpClient okHttpClient;
    protected static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);
    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    public static final String SCHEME = "https";

    public RestUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(20))
                .writeTimeout(Duration.ofSeconds(20))
                .build();
    }

    public Request postRequest(HttpUrl requestUrl, RequestBody requestBody) {
        LOGGER.info("Sending post request");
        return new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build();
    }

    public Response send(Request request) {
        Response response;
        try {
            response = okHttpClient
                    .newCall(request)
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to make HTTP call with the following request: " + request, e);
        }
        return response;
    }
}
