package lk.dialog.psi.MifeSMS.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "psi.techcrm")
@Configuration("Diagnostic Properties")
public class MifeSmsProperties {
    private String privateKey;
    private String redirectUrl;
    private String iv;
    private String smsURL;
    private String externalMifeURL;
    private String externalMifeAuth;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSmsURL() {
        return smsURL;
    }

    public void setSmsURL(String smsURL) {
        this.smsURL = smsURL;
    }

    public String getExternalMifeURL() {
        return externalMifeURL;
    }

    public void setExternalMifeURL(String externalMifeURL) {
        this.externalMifeURL = externalMifeURL;
    }

    public String getExternalMifeAuth() {
        return externalMifeAuth;
    }

    public void setExternalMifeAuth(String externalMifeAuth) {
        this.externalMifeAuth = externalMifeAuth;
    }
}
