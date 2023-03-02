package com.vitacard.finsvc.client;

import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountRequest;
import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.commons.unit.UnitCreateRewardRequest;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateIndividualApplicationRequest;
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
import unit.UnitResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Component
@PropertySource("classpath:unit-apis.properties")
public class UnitWebClient {
    @Value("${services.unit.baseUrl}")
    private String baseUrl;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private String token;
    private WebClient client;

    @Autowired
    private Environment environment;

    @PostConstruct
    private void setup() throws IOException {
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

    private String post(String uri, String requestBody) {
        return client
                .post()
                .uri(uri)
                .body(Mono.just(requestBody), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String get(String uri) {
        String response = client
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }

    public String createIndividualApplication(UnitCreateIndividualApplicationRequest unitCreateIndividualApplicationRequest) {
        String uri = environment.getProperty("api.unit.application.createIndividualApplication.uri");
        String unitRequest = unitCreateIndividualApplicationRequest.value();
        String unitResponse = post(uri, unitRequest);
        return unitResponse;
    }

    public UnitCreateDepositAccountResponse createDepositAccount(String depositProduct, String relationType, String relationId) {
        String uri = environment.getProperty("api.unit.account.createDepositAccount.uri");
        UnitCreateDepositAccountRequest unitCreateDepositAccountRequest
                = new UnitCreateDepositAccountRequest(depositProduct, relationType, relationId);
        String response = post(uri, unitCreateDepositAccountRequest.value());
        return new UnitCreateDepositAccountResponse(new UnitResponse(response).getOnly());
    }
    public UnitCreateDepositAccountResponse createDepositAccount(UnitCreateDepositAccountRequest unitCreateDepositAccountRequest) {
        String uri = environment.getProperty("api.unit.account.createDepositAccount.uri");
        String response = post(uri, unitCreateDepositAccountRequest.value());
        return new UnitCreateDepositAccountResponse(new UnitResponse(response).getOnly());
    }

    public String getTransactionById(String accountId, String transactionId) {
        String uri = environment.getProperty("api.unit.account.getTransactionById.uri");
        assert uri != null;
        uri = uri.replaceAll("\\{accountId}", accountId).replaceAll("\\{transactionId}", transactionId);
        String finalUri = uri;
        return get(finalUri);
    }

    public String createReward(UnitCreateRewardRequest unitCreateRewardRequest) {
        String uri = environment.getProperty("api.unit.account.createReward.uri");
        assert uri != null;
        return post(uri, unitCreateRewardRequest.value());
    }
}
