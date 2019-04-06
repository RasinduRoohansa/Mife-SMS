package lk.dialog.psi.MifeSMS.service;

import lk.dialog.psi.MifeSMS.properties.MifeSmsProperties;
import lk.dialog.psi.MifeSMS.response.MifeResponse;
import lk.dialog.psi.MifeSMS.request.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class MifeSmsService {

    private final Logger logger= LoggerFactory.getLogger(MifeSmsService.class);
    @Autowired
    private RestTemplate externlMifeTemplate;
    @Autowired
    private MifeSmsProperties mifeSmsProperties;

    private MifeResponse getSandBoxAuthToken(){
      try {
          RestTemplate restTemplate=new RestTemplate();
          HttpHeaders headers = new HttpHeaders();
          headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
          headers.add("Accept", MediaType.APPLICATION_JSON.toString());
          headers.add("Authorization"," Basic "+ mifeSmsProperties.getExternalMifeAuth());
          MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
          requestBody.add("grant_type","client_credentials");
          HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
          ResponseEntity<MifeResponse> responseEntity = restTemplate.exchange(mifeSmsProperties.getExternalMifeURL(), HttpMethod.POST, formEntity, MifeResponse.class);
          return responseEntity.getBody();
      }catch (Exception e){
          logger.error("Error on Sandbox Auth Token",e);
          return null;
      }
    }

    public void sendClientSMS(String msisdn) {
        long start = System.currentTimeMillis();
        logger.info("Start Method sendClientSMS | msisdn={}",msisdn);
        try {
            Map<String, String> receiptRequest = new HashMap<>();
            receiptRequest.put("notifyURL", null);
            receiptRequest.put("callbackData", "some-data-useful-to-the-requester");

            String message = "Testing SMS";
            Map<String, String> outboundSMSTextMessage = new HashMap<>();

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("address", new String[]{"tel:+94" +msisdn.substring(1)});
            requestMap.put("senderAddress", "tel:7555");
            outboundSMSTextMessage.put("message", message);
            requestMap.put("outboundSMSTextMessage", outboundSMSTextMessage);
            requestMap.put("clientCorrelator", "123456");
            requestMap.put("receiptRequest", receiptRequest);
            requestMap.put("senderName", "7555");

            SmsRequest smsRequest = new SmsRequest(requestMap);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + getSandBoxAuthToken().getAccess_token());

            HttpEntity<SmsRequest> httpEntity = new HttpEntity<>(smsRequest, httpHeaders);
            externlMifeTemplate.exchange(mifeSmsProperties.getSmsURL(), HttpMethod.POST, httpEntity, Object.class);


        } catch (HttpClientErrorException errorException) {
            logger.error("Error on SMS Request", errorException.getResponseBodyAsString());
        }
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - start);
        logger.info("End Method sendClientSMS | duration={}:{}:{}:{}", duration.toHours(), duration.toMinutes(), duration.getSeconds(), duration.toMillis());
    }
}
