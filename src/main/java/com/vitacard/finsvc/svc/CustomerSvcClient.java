package com.vitacard.finsvc.svc;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;

@Component
@PropertySource("classpath:customer-svc-apis.properties")
public class CustomerSvcClient {
    @Autowired
    private Environment environment;
    @Value("${services.customer-svc.baseUrl}")
    private String baseUrl;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private WebClient client;

    @PostConstruct
    public void setup() throws IOException {
        client = WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .baseUrl(baseUrl)
                .build();
    }

    public String post(String uri, String requestBody) {
        return client
                .post()
                .uri(uri)
                .body(Mono.just(requestBody), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String addCustomer(String requestBody) {
        String uri = environment.getProperty("api.customer-svc.customer.add.uri");
        return post(uri, requestBody);
    }
}
