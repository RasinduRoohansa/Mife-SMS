package lk.dialog.psi.MifeSMS.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "psi.sms")
@Configuration("SMS Properties")
public class MifeSmsProperties {
    private String smsURL;
    private String externalMifeURL;
    private String externalMifeAuth;

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
