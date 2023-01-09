package com.vitacard.finsvc.svc;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

@SpringBootTest
@PropertySource("classpath:unit-apis.properties")
class UnitWebClientTest {
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private Environment environment;

    private static ClientAndServer mockServer;
    @BeforeAll
    public static void startMockServer() {
        mockServer = startClientAndServer(1080);
    }
    @AfterAll
    public static void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void createIndividualApplication() throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("data", new JSONObject()
                        .put("type", "individualApplication")
                        .put("attributes", new JSONObject()
                                .put("ssn", "123456789")
                                .put("fullName", new JSONObject()
                                        .put("first", "Customer First Name")
                                        .put("last", "Customer Last Name"))
                                .put("dateOfBirth", "2001-08-10")
                                .put("address", new JSONObject()
                                        .put("street", "20 Ingram St")
                                        .put("city", "Forest Hills")
                                        .put("state", "CA")
                                        .put("postalCode", "11375")
                                        .put("country", "US"))
                                .put("email", "test@spiffymaildomain.test")
                                .put("phone", new JSONObject()
                                        .put("countryCode", "1")
                                        .put("number", "5555555555"))));
        String uri = environment.getProperty("api.unit.application.createIndividualApplication.uri");

        new MockServerClient("localhost", 1080)
                .when(
                request()
                        .withMethod("POST")
                        .withPath(uri)
                        .withBody(requestBody.toString())
        )
                .respond(
                        response()
                                .withStatusCode(201)
                                .withBody(requestBody.toString())
                );
        unitWebClient.createIndividualApplication(requestBody.toString());

        new MockServerClient("localhost", 1080).verify(
                request()
                        .withMethod("POST")
                        .withPath(uri)
                        .withBody(exact(requestBody.toString())),
                VerificationTimes.exactly(1)
        );
    }
}