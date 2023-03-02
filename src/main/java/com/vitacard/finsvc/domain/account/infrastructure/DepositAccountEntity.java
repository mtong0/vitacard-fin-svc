package com.vitacard.finsvc.domain.account.infrastructure;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Getter
public class DepositAccountEntity extends AccountEntity {
    private final String id;
    private final int depositProductCode;
    private final String routingNumber;
    private final String accountNumber;

    @Builder(builderMethodName = "depositAccountEntityBuilder")
    public DepositAccountEntity (
            String id,
            int statusCode,
            String currency,
            long balance,
            long hold,
            long available,
            String customerId,
            Timestamp createdAt,
            int depositProductCode,
            String routingNumber,
            String accountNumber,
            String type
    ) {
        super(id, statusCode, currency, balance, hold, available, customerId, createdAt, type);
        this.id = id;
        this.depositProductCode = depositProductCode;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("depositProductCode", depositProductCode)
                .append("routingNumber", routingNumber)
                .append("accountNumber", accountNumber)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DepositAccountEntity that = (DepositAccountEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(depositProductCode, that.depositProductCode)
                .append(routingNumber, that.routingNumber)
                .append(accountNumber, that.accountNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(depositProductCode)
                .append(routingNumber)
                .append(accountNumber)
                .toHashCode();
    }
}
