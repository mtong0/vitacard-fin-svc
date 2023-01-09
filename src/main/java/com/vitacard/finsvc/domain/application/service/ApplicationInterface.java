package com.vitacard.finsvc.domain.application.service;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import com.vitacard.finsvc.domain.application.request.IndividualApplicationRequest;

public interface ApplicationInterface {
    IndividualApplication createApplication(IndividualApplicationRequest individualApplicationRequest);
}
