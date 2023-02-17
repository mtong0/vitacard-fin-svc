package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;

public interface ApplicationDao {
    IndividualApplicationEntity addApplication(IndividualApplicationEntity individualApplicationEntity);
    IndividualApplicationEntity getApplicationById(String id);
}
