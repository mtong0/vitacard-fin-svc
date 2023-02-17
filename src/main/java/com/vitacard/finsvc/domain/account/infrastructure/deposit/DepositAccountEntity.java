package com.vitacard.finsvc.domain.account.infrastructure.deposit;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
@Builder
@Getter
public class DepositAccountEntity {
    private String id;
    private int depositProductCode;
    private String routingNumber;
    private String accountNumber;

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
