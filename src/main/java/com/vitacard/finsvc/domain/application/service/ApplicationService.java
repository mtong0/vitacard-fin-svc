package com.vitacard.finsvc.domain.application.service;

import com.vitacard.finsvc.domain.application.aggregate.ApplicationFactory;
import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import com.vitacard.finsvc.domain.application.dao.ApplicationDao;
import com.vitacard.finsvc.domain.application.request.IndividualApplicationRequest;
import com.vitacard.finsvc.svc.CustomerSvcClient;
import com.vitacard.finsvc.svc.UnitWebClient;
import customer.InternalCreateCustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JSONStringConverter;

@Service
public class ApplicationService implements ApplicationInterface{
    @Autowired
    private ApplicationFactory applicationFactory;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private CustomerSvcClient customerSvcClient;

    @Override
    public IndividualApplication createApplication(IndividualApplicationRequest individualApplicationRequest) {
        IndividualApplication individualApplication = applicationFactory.constructFromInternalIndividualApplicationRequest(individualApplicationRequest);

        String unitRequest = individualApplication.getUnitRequest();
        String unitResponse = unitWebClient.createIndividualApplication(unitRequest);

        individualApplication = applicationFactory.constructFromUnitResponse(individualApplication, unitResponse);
        applicationDao.addApplication(individualApplication);

        InternalCreateCustomerRequestDto internalCreateCustomerRequestDto = individualApplication.getCreateCustomerRequestDto();
        customerSvcClient.addCustomer(JSONStringConverter.toJSONString(internalCreateCustomerRequestDto));

        return individualApplication;
    }
}
