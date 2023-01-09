package com.vitacard.finsvc.svc;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

@Component
@PropertySource("classpath:unit-apis.properties")
public class UnitWebClient {
    @Autowired
    private Environment environment;
    @Value("${services.unit.baseUrl}")
    private String baseUrl;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private String token;
    private WebClient client;

    @PostConstruct
    public void setup() throws IOException {
        if (activeProfile.equals("dev")) {
            File file = ResourceUtils.getFile("classpath:certificates/unit-token");
            InputStream in = new FileInputStream(file);
            token = new Scanner(in).nextLine();
        }
        client = WebClient
                .builder()
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(token))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json")
                .baseUrl(baseUrl)
                .build();
    }

    public String post(String uri, String requestBody) {
        System.out.println(requestBody);
        return client
                .post()
                .uri(uri)
                .body(Mono.just(requestBody), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String createIndividualApplication(String requestBody) {
        String uri = environment.getProperty("api.unit.application.createIndividualApplication.uri");
        return post(uri, requestBody);
    }
}
