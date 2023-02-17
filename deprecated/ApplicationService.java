package com.vitacard.finsvc.domain.application.service;

import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntityFactory;
import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;
import com.vitacard.finsvc.domain.application.infrastructure.ApplicationDao;
import com.vitacard.finsvc.client.CustomerService;
import com.vitacard.finsvc.client.UnitWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements ApplicationInterface {
    @Autowired
    private IndividualApplicationEntityFactory individualApplicationEntityFactory;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private UnitWebService unitWebService;
    @Autowired
    private CustomerService customerService;

    @Override
    public IndividualApplicationEntity createApplication(IndividualApplicationEntity individualApplicationEntity) {
        individualApplicationEntity = unitWebService.createIndividualApplication(individualApplicationEntity);
        individualApplicationEntity = applicationDao.addApplication(individualApplicationEntity);
        customerService.addCustomer(individualApplicationEntity);
        return individualApplicationEntity;
    }
}
