package pojo;

import com.google.gson.annotations.SerializedName;

public class JWTPayload {

    @SerializedName("ISS")
    private String iss;

    private String aud;

    private String scope;

    private String iat;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String exp) {
        this.iat = exp;
    }
}
