package lk.dialog.psi.MifeSMS.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class LogRequestResponseFilter implements ClientHttpRequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LogRequestResponseFilter.class);

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        traceRequest(request, body);
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        traceResponse(clientHttpResponse);

        return clientHttpResponse;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.request URI : " + request.getURI());
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.request method : " + request.getMethod());
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.request body : " + getRequestBody(body));
    }

    private String getRequestBody(byte[] body) throws UnsupportedEncodingException {
        if (body != null && body.length > 0) {
            return (new String(body, "UTF-8"));
        } else {
            return null;
        }
    }


    private void traceResponse(ClientHttpResponse response) throws IOException {
        String body = getBodyString(response);
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.response status code: " + response.getStatusCode());
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.response status text: " + response.getStatusText());
        logger.info("lk.dialog.techcrm.TechCRMDiagnostic.response body : " + body);
    }

    private String getBodyString(ClientHttpResponse response) {
        try {
            if (response != null && response.getBody() != null) {// &&
                // isReadableResponse(lk.dialog.techcrm.TechCRMDiagnostic.response))
                // {
                StringBuilder inputStringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
                String line = bufferedReader.readLine();
                while (line != null) {
                    inputStringBuilder.append(line);
                    inputStringBuilder.append('\n');
                    line = bufferedReader.readLine();
                }
                return inputStringBuilder.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
