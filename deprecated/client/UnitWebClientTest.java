package com.vitacard.finsvc.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

@SpringBootTest
@PropertySource("unit-apis.properties")
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
        System.out.println(requestBody.toString());
        mockServer
                .when(
                request()
                        .withMethod("POST")
                        .withPath(uri)
                        .withBody(requestBody.toString()),
                        Times.exactly(1)
        )
                .respond(
                        response()
                                .withStatusCode(201)
                                .withBody(requestBody.toString())
                );

        System.out.println(unitWebClient.post(uri, requestBody.toString()));
    }
}