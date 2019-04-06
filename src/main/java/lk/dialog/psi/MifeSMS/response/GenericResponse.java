package lk.dialog.psi.MifeSMS.response;

public class GenericResponse {
    private String response;
    private int responseCode;
    private String sessionId;
    private String tbToken;
    private String deviceOs;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTbToken() {
        return tbToken;
    }

    public void setTbToken(String tbToken) {
        this.tbToken = tbToken;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "response='" + response + '\'' +
                ", responseCode=" + responseCode +
                ", sessionId='" + sessionId + '\'' +
                ", tbToken='" + tbToken + '\'' +
                ", deviceOs='" + deviceOs + '\'' +
                '}';
    }
}
