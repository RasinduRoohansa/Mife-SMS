package lk.dialog.psi.MifeSMS.request;

import java.util.Map;

public class SmsRequest {
    private Map<String,Object> outboundSMSMessageRequest;

    public SmsRequest(Map<String, Object> outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }

    public Map<String, Object> getOutboundSMSMessageRequest() {
        return outboundSMSMessageRequest;
    }

    public void setOutboundSMSMessageRequest(Map<String, Object> outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "outboundSMSMessageRequest=" + outboundSMSMessageRequest +
                '}';
    }
}
