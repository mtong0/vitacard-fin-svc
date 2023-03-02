package com.vitacard.finsvc.domain.application.model;

import com.vitacard.finsvc.commons.events.DomainEvent;
import com.vitacard.finsvc.configuration.SpringApplicationContext;
import com.vitacard.finsvc.domain.application.facet.CreateIndividualApplicationCommand;
import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;
import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntityFactory;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateApplicationResponse;
import customer.InternalCreateCustomerRequestDto;
import generalattributes.Address;
import generalattributes.Phone;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

public interface ApplicationEvent extends DomainEvent {
    @Value
    class CreateIndividualApplicationEvent implements ApplicationEvent {
        @NonNull IndividualApplicationEntity individualApplicationEntity;
        public static CreateIndividualApplicationEvent createIndividualApplication(
                CreateIndividualApplicationCommand createIndividualApplicationCommand,
                UnitCreateApplicationResponse unitResponse
        ) {
            String id = unitResponse.getId();
            ApplicationStatus status = ApplicationStatus.getByStatus(unitResponse.getStatus());
            String customerId = unitResponse.getCustomerId();
            IndividualApplicationEntityFactory individualApplicationEntityFactory
                    = SpringApplicationContext.getBean(IndividualApplicationEntityFactory.class);
            IndividualApplicationEntity individualApplication = individualApplicationEntityFactory.create(
                    id,
                    status.getCode(),
                    customerId,
                    createIndividualApplicationCommand
            );
            return new CreateIndividualApplicationEvent(individualApplication);
        }

        @Override
        public UUID getEventId() {
            return UUID.randomUUID();
        }
    }

    @Value
    class CreateCustomerEvent implements ApplicationEvent {
        @NonNull InternalCreateCustomerRequestDto internalCreateCustomerRequestDto;

        public static CreateCustomerEvent createCustomer(IndividualApplication individualApplication) {
            Address address = individualApplication.getAddress();
            Phone phone = individualApplication.getPhone();
            InternalCreateCustomerRequestDto internalCreateCustomerRequestDto = new InternalCreateCustomerRequestDto()
                    .setId(individualApplication.getCustomerId())
                    .setFirstName(individualApplication.getFullName().getFirst())
                    .setLastName(individualApplication.getFullName().getLast())
                    .setSsn(individualApplication.getSsn())
                    .setDateOfBirth(individualApplication.getDateOfBirth())
                    .setStreet(address.getStreet())
                    .setStreet2(address.getStreet2())
                    .setCity(address.getCity())
                    .setState(address.getState())
                    .setCountry(address.getCountry())
                    .setPostalCode(address.getPostalCode())
                    .setPhone(phone.fullNumber())
                    .setEmail(individualApplication.getEmail());
            return new CreateCustomerEvent(internalCreateCustomerRequestDto);
        }

        @Override
        public UUID getEventId() {
            return UUID.randomUUID();
        }
    }
}
