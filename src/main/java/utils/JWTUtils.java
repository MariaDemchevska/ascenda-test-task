package utils;

import com.google.gson.Gson;
import pojo.JWTPayload;

import java.util.Base64;

public class JWTUtils {

    public static String decodeJwtAndGetPayload(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(chunks[1]));
    }

    public static JWTPayload getJWTPayloadAsObj(String payload) {
        Gson gson = new Gson();
        return gson.fromJson(payload, JWTPayload.class);
    }
}
