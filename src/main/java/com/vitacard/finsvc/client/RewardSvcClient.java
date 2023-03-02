package com.vitacard.finsvc.client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import transactionDto.InternalTransactionDto;

import java.io.IOException;

@Component
@PropertySource("classpath:reward-svc-apis.properties")
public class RewardSvcClient {
    @Autowired
    private Environment environment;
    @Value("${services.reward-svc.baseUrl}")
    private String baseUrl;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private WebClient client;

    @PostConstruct
    private void setup() throws IOException {
        client = WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .baseUrl(baseUrl)
                .build();
    }

    private String post(String uri, String requestBody) {
        return client
                .post()
                .uri(uri)
                .body(Mono.just(requestBody), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String postTransaction(InternalTransactionDto internalTransactionDto) {
        String uri = environment.getProperty("api.reward-svc.transaction.post.uri");
        return post(uri, internalTransactionDto.toString());
    }
}
