package com.vitacard.finsvc.domain.application.facet;

import com.vitacard.finsvc.client.UnitWebClient;
import com.vitacard.finsvc.commons.events.JustForwardDomainEventPublisher;
import com.vitacard.finsvc.domain.application.infrastructure.ApplicationDaoRepository;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateApplicationResponse;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateIndividualApplicationRequest;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateCustomerEvent;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateIndividualApplicationEvent;
import com.vitacard.finsvc.domain.application.model.IndividualApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unit.UnitResponse;

import static com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateIndividualApplicationEvent.createIndividualApplication;

@Service
public class CreateApplication {
    @Autowired
    private ApplicationDaoRepository applicationDao;
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private JustForwardDomainEventPublisher justForwardDomainEventPublisher;

    public void createApplication(CreateIndividualApplicationCommand createIndividualApplicationCommand) {
        UnitCreateIndividualApplicationRequest unitCreateIndividualApplicationRequest = new UnitCreateIndividualApplicationRequest(createIndividualApplicationCommand);
        UnitCreateApplicationResponse unitResponse = new UnitCreateApplicationResponse(
              new UnitResponse(unitWebClient.createIndividualApplication(unitCreateIndividualApplicationRequest)).getOnly()
        );

        CreateIndividualApplicationEvent createIndividualApplicationEvent = createIndividualApplication(createIndividualApplicationCommand, unitResponse);
        IndividualApplication individualApplication = applicationDao.publish(createIndividualApplicationEvent);

        CreateCustomerEvent createCustomerEvent = CreateCustomerEvent.createCustomer(individualApplication);
        justForwardDomainEventPublisher.publish(createCustomerEvent);
    }
}
