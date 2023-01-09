package com.vitacard.finsvc.domain.application.dao;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;

public interface ApplicationDao {
    IndividualApplication addApplication(IndividualApplication individualApplication);
    IndividualApplication getApplicationById(String id);
}
