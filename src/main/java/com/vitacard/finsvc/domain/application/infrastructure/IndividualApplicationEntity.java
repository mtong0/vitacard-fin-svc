package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.domain.application.model.DocumentType;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PACKAGE)
public class IndividualApplicationEntity {
    private static final String applicationType = "IndividualApplication";
    private String id;
    private DocumentType documentType;
    private String ssn;
    private FullName fullName;
    private Address address;
    private Timestamp dateOfBirth;
    private String email;
    private Phone phone;
    private int statusCode;
    private String message;
    private boolean archived;
    private String customerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IndividualApplicationEntity that = (IndividualApplicationEntity) o;

        return new EqualsBuilder().append(statusCode, that.statusCode).append(archived, that.archived).append(id, that.id).append(documentType, that.documentType).append(ssn, that.ssn).append(fullName, that.fullName).append(address, that.address).append(dateOfBirth, that.dateOfBirth).append(email, that.email).append(phone, that.phone).append(message, that.message).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(documentType).append(ssn).append(fullName).append(address).append(dateOfBirth).append(email).append(phone).append(statusCode).append(message).append(archived).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("documentType", documentType)
                .append("ssn", ssn)
                .append("fullName", fullName)
                .append("address", address)
                .append("dateOfBirth", dateOfBirth)
                .append("email", email)
                .append("phone", phone)
                .append("statusCode", statusCode)
                .append("message", message)
                .append("archived", archived)
                .toString();
    }
}
