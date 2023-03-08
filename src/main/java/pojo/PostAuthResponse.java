package pojo;

import com.google.gson.annotations.SerializedName;

public class PostAuthResponse {

    private String token;

    @SerializedName("expires_in")
    private String expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
