package com.vitacard.finsvc.domain.application.service;

import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;

public interface ApplicationInterface {
    IndividualApplicationEntity createApplication(IndividualApplicationEntity individualApplicationEntity);
}
