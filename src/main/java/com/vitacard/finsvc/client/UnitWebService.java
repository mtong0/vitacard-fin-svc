package com.vitacard.finsvc.client;

import com.vitacard.finsvc.domain.account.dtos.UnitCreateDepositAccountRequest;
import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntityFactory;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateIndividualApplicationRequest;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:unit-apis.properties")
public class UnitWebService {
    @Autowired
    private Environment environment;
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private IndividualApplicationEntityFactory individualApplicationEntityFactory;

    public String createIndividualApplication(UnitCreateIndividualApplicationRequest unitCreateIndividualApplicationRequest) {
        String uri = environment.getProperty("api.unit.application.createIndividualApplication.uri");
        String unitRequest = unitCreateIndividualApplicationRequest.value();
        System.out.println(unitRequest);
        String unitResponse = unitWebClient.post(uri, unitRequest);
        return unitResponse;
    }

    public String createDepositAccount(UnitCreateDepositAccountRequest unitCreateDepositAccountRequest) {
        String uri = environment.getProperty("api.unit.account.createDepositAccount.uri");
        return Try.of(()-> {
            String unitResponse = unitWebClient.post(uri, unitCreateDepositAccountRequest.value());
            return unitResponse;
        }).getOrElseThrow(()->{
            return new RuntimeException();
        });
    }
}
