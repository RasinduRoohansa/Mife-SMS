package lk.dialog.psi.MifeSMS.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.dialog.psi.MifeSMS.util.LogRequestResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@EnableSwagger2
public class MifeSmsApiConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger  logger= LoggerFactory.getLogger(MifeSmsApiConfig.class);

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("PSI-Tech CRM- Diagnostic")
                .useDefaultResponseMessages(false).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("PSI Tech CRM Diagnostic")
                .description("Tech CRM Diagnostic Rest API Documentation")
                .termsOfServiceUrl("")
                .contact(new Contact("Dialog PSI", "www.dialog.lk", "PSI@dialog.lk"))
                .license("Apache License Version 2.0").version("0.0.3")
                .licenseUrl("").build();
    }

    public RestTemplate restTemplateGenerator(){
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
        // set up a buffering lk.dialog.techcrm.TechCRMDiagnostic.request factory, so lk.dialog.techcrm.TechCRMDiagnostic.response body is always buffered
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(requestFactory);
        requestFactory.setOutputStreaming(false);
        rest.setRequestFactory(bufferingClientHttpRequestFactory);

        // add the interceptor that will handle logging
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new LogRequestResponseFilter());
        rest.setInterceptors(interceptors);
        return rest;
    }

    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(myObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper myObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public RestTemplate externlMifeTemplate(){
        return restTemplateGenerator();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("!!!! Application Started!!!!!");
    }
}
