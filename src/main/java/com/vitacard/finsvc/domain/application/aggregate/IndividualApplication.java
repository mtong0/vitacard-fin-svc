package com.vitacard.finsvc.domain.application.aggregate;

import com.vitacard.finsvc.domain.application.request.UnitCreateIndividualApplicationRequest;
import com.vitacard.finsvc.infrastructure.DocumentType;
import customer.InternalCreateCustomerRequestDto;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

public class IndividualApplication {
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

    public String getUnitRequest() {
        UnitCreateIndividualApplicationRequest unitCreateIndividualApplicationRequest = new UnitCreateIndividualApplicationRequest(this);
        return unitCreateIndividualApplicationRequest.value();
    }

    public String getId() {
        return id;
    }

    public IndividualApplication setId(String id) {
        this.id = id;
        return this;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public IndividualApplication setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public String getSsn() {
        return ssn;
    }

    public IndividualApplication setSsn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    public FullName getFullName() {
        return fullName;
    }

    public IndividualApplication setFullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public IndividualApplication setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public IndividualApplication setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public IndividualApplication setEmail(String email) {
        this.email = email;
        return this;
    }

    public Phone getPhone() {
        return phone;
    }

    public IndividualApplication setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public IndividualApplication setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public IndividualApplication setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean getArchived() {
        return archived;
    }

    public IndividualApplication setArchived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public InternalCreateCustomerRequestDto getCreateCustomerRequestDto() {
        return new InternalCreateCustomerRequestDto()
                .setFirstName(fullName.getFirst())
                .setLastName(fullName.getLast())
                .setSsn(ssn)
                .setDateOfBirth(dateOfBirth)
                .setStreet(address.getStreet())
                .setStreet2(address.getStreet2())
                .setCity(address.getCity())
                .setState(address.getState())
                .setCountry(address.getCountry())
                .setPostalCode(address.getPostalCode())
                .setPhone(phone.fullNumber())
                .setEmail(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IndividualApplication that = (IndividualApplication) o;

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
