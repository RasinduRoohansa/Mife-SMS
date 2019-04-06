package lk.dialog.psi.MifeSMS.api;

import io.swagger.annotations.ApiOperation;
import lk.dialog.psi.MifeSMS.service.MifeSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MifeSmsAPI {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MifeSmsService mifeSmsService;

    @ApiOperation(value = "Send SMS", nickname = "Send SMS", notes = "Send SMS")
    @RequestMapping(value = {"/send/{msisdn}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendOTP(@PathVariable("msisdn") String msisdn) {
        long start = System.currentTimeMillis();
        logger.info("Start Method sendOTP | msisdn={}", msisdn);
        try {
            mifeSmsService.sendClientSMS(msisdn);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
        }
}
