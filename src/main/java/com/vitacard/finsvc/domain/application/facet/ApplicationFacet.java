package com.vitacard.finsvc.domain.application.facet;

import com.vitacard.finsvc.client.CustomerSvcClient;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateCustomerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Service
public class ApplicationFacet {
    @Autowired
    private CustomerSvcClient customerSvcClient;

    public String handle(ApplicationEvent event) {
        return Match(event).of(
                Case($(instanceOf(CreateCustomerEvent.class)), this::handle));
    }

    public String handle(CreateCustomerEvent createCustomerEvent) {
        customerSvcClient.addCustomer(createCustomerEvent.getInternalCreateCustomerRequestDto());
        return "";
    }
}
