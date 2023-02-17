//package com.vitacard.finsvc.domain.application.aggregate;
//
//import com.vitacard.finsvc.domain.application.infrastructure.ApplicationFactory;
//import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ApplicationFactoryTest {
//    @Autowired
//    private ApplicationFactory applicationFactory;
//
//    @Test
//    void constructFromUnitResponse() {
//        String unitResponseString = "{\n" +
//                "  \"data\": {\n" +
//                "    \"type\": \"individualApplicationEntity\",\n" +
//                "    \"id\": \"53\",\n" +
//                "    \"attributes\": {\n" +
//                "      \"createdAt\": \"2020-01-14T14:05:04.718Z\",\n" +
//                "      \"fullName\": {\n" +
//                "        \"first\": \"Peter\",\n" +
//                "        \"last\": \"Parker\"\n" +
//                "      },\n" +
//                "      \"ssn\": \"721074426\",\n" +
//                "      \"address\": {\n" +
//                "        \"street\": \"20 Ingram St\",\n" +
//                "        \"street2\": null,\n" +
//                "        \"city\": \"Forest Hills\",\n" +
//                "        \"state\": \"NY\",\n" +
//                "        \"postalCode\": \"11375\",\n" +
//                "        \"country\": \"US\"\n" +
//                "      },\n" +
//                "      \"dateOfBirth\": \"2001-08-10\",\n" +
//                "      \"email\": \"peter@oscorp.com\",\n" +
//                "      \"phone\": {\n" +
//                "        \"countryCode\": \"1\",\n" +
//                "        \"number\": \"5555555555\"\n" +
//                "      },\n" +
//                "      \"status\": \"Approved\",\n" +
//                "      \"ip\": \"127.0.0.2\",\n" +
//                "      \"soleProprietorship\": true,\n" +
//                "      \"ein\": \"123456789\",\n" +
//                "      \"dba\": \"Piedpiper Inc\",\n" +
//                "      \"tags\": {\n" +
//                "        \"userId\": \"106a75e9-de77-4e25-9561-faffe59d7814\"\n" +
//                "      },\n" +
//                "      \"archived\": false\n" +
//                "    },\n" +
//                "    \"relationships\": {\n" +
//                "      \"customer\": {\n" +
//                "        \"data\": {\n" +
//                "          \"type\": \"customer\",\n" +
//                "          \"id\": \"1\"\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"documents\": {\n" +
//                "        \"data\": [\n" +
//                "          {\n" +
//                "            \"type\": \"document\",\n" +
//                "            \"id\": \"1\"\n" +
//                "          },\n" +
//                "          {\n" +
//                "            \"type\": \"document\",\n" +
//                "            \"id\": \"2\"\n" +
//                "          }\n" +
//                "        ]\n" +
//                "      }\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"included\": [\n" +
//                "    {\n" +
//                "      \"type\": \"document\",\n" +
//                "      \"id\": \"1\",\n" +
//                "      \"attributes\": {\n" +
//                "        \"documentType\": \"AddressVerification\",\n" +
//                "        \"status\": \"Required\",\n" +
//                "        \"name\": \"Peter Parker\",\n" +
//                "        \"description\": \"Please provide a document to verify your address. Document may be a utility bill, bank statement, lease agreement or current pay stub.\",\n" +
//                "        \"address\": {\n" +
//                "          \"street\": \"20 Ingram St\",\n" +
//                "          \"street2\": null,\n" +
//                "          \"city\": \"Forest Hills\",\n" +
//                "          \"state\": \"NY\",\n" +
//                "          \"postalCode\": \"11375\",\n" +
//                "          \"country\": \"US\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"type\": \"document\",\n" +
//                "      \"id\": \"2\",\n" +
//                "      \"attributes\": {\n" +
//                "        \"documentType\": \"IdDocument\",\n" +
//                "        \"status\": \"Required\",\n" +
//                "        \"name\": \"Peter Parker\",\n" +
//                "        \"description\": \"Please provide a copy of your unexpired government issued photo ID which would include Drivers License or State ID.\",\n" +
//                "        \"dateOfBirth\": \"2001-08-10\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//        IndividualApplicationEntity individualApplicationEntity = new IndividualApplicationEntity();
//        individualApplicationEntity = applicationFactory.create(individualApplicationEntity, unitResponseString);
//        assertEquals(individualApplicationEntity.getRelationType(), "customer");
//    }
//}