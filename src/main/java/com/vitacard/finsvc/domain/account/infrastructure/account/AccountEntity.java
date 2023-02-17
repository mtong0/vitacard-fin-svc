package com.vitacard.finsvc.domain.account.infrastructure.account;

import com.vitacard.finsvc.domain.account.model.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class AccountEntity {
    private String id;
    private int statusCode;
    private String currency;
    private long balance;
    private long hold;
    private long available;
    private String customerId;
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity account = (AccountEntity) o;

        return new EqualsBuilder().append(statusCode, account.statusCode).append(balance, account.balance).append(hold, account.hold).append(available, account.available).append(id, account.id).append(currency, account.currency).append(customerId, account.customerId).append(createdAt, account.createdAt).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(statusCode).append(currency).append(balance).append(hold).append(available).append(customerId).append(createdAt).toHashCode();
    }
}
