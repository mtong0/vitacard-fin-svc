package com.vitacard.finsvc.domain.account.infrastructure.account;

import com.vitacard.finsvc.commons.SpringApplicationContext;
import com.vitacard.finsvc.commons.unitevents.UnitTransactionCreatedEvent;
import com.vitacard.finsvc.domain.account.model.Account;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.CreateDepositAccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.TransactionEvent;
import com.vitacard.finsvc.domain.account.model.AccountFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Repository
@PropertySource("classpath:db/sql/account.xml")
public class AccountRepository {
    @Autowired
    private Environment environment;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private final AccountEntityRowMapper accountEntityRowMapper;
    private final AccountDomainModelMapper accountDomainModelMapper;

    public AccountRepository() {
        accountEntityRowMapper = new AccountEntityRowMapper();
        accountDomainModelMapper = new AccountDomainModelMapper();
    }

    public AccountEntity addAccountEntity(AccountEntity accountEntity) {
        String sql = environment.getProperty("addAccount");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", accountEntity.getId())
                .addValue("created_at", accountEntity.getCreatedAt())
                .addValue("status_code", accountEntity.getStatusCode())
                .addValue("currency", accountEntity.getCurrency())
                .addValue("balance", accountEntity.getBalance())
                .addValue("hold", accountEntity.getHold())
                .addValue("available", accountEntity.getAvailable())
                .addValue("customer_id", accountEntity.getCustomerId());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);
        return getAccountEntityById(accountEntity.getId());
    }

    public AccountEntity updateAccountEntity(AccountEntity accountEntity) {
        String sql = environment.getProperty("updateAccountById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", accountEntity.getId())
                .addValue("status_code", accountEntity.getStatusCode())
                .addValue("currency", accountEntity.getCurrency())
                .addValue("balance", accountEntity.getBalance())
                .addValue("hold", accountEntity.getHold())
                .addValue("available", accountEntity.getAvailable());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);
        return getAccountEntityById(accountEntity.getId());
    }

    public AccountEntity getAccountEntityById(String id) {
        String sql = environment.getProperty("getAccountById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);

        AccountEntity accountEntity = jdbcTemplate.queryForObject(sql, parameterSource, accountEntityRowMapper);
        return accountEntity;
    }

    public Account publish(AccountEvent event) {
        Account result = Match(event).of(
                Case($(instanceOf(CreateDepositAccountEvent.class)), this::createNewAccount),
                Case($(instanceOf(TransactionEvent.class)), this::processTransaction));

        return result;
    }

    public Account createNewAccount(CreateDepositAccountEvent createDepositAccountEvent) {
        AccountEntity addedAccountEntity = addAccountEntity(createDepositAccountEvent.getAccountEntity());

        return accountDomainModelMapper.map(addedAccountEntity);
    }

    public Account processTransaction(TransactionEvent transactionEvent) {
        AccountEntity accountEntity = getAccountEntityById(transactionEvent.getAccountId());
        accountEntity.setAvailable(transactionEvent.getUpdatedAvailable());
        accountEntity.setBalance(transactionEvent.getUpdatedBalance());
        AccountEntity updatedAccountEntity = updateAccountEntity(accountEntity);
        return accountDomainModelMapper.map(updatedAccountEntity);
    }
}

class AccountEntityRowMapper implements RowMapper<AccountEntity> {
    @Override
    public AccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AccountEntity.builder()
                .id(rs.getString("id"))
                .createdAt(rs.getTimestamp("created_at"))
                .statusCode(rs.getInt("status_code"))
                .currency(rs.getString("currency"))
                .balance(rs.getLong("balance"))
                .hold(rs.getLong("hold"))
                .available(rs.getLong("available"))
                .customerId(rs.getString("customer_id"))
                .build();
    }
}
@AllArgsConstructor
class AccountDomainModelMapper {
    private AccountFactory accountFactory;

    AccountDomainModelMapper() {
        accountFactory = SpringApplicationContext.getAccountFactory();
    }

    Account map(AccountEntity accountEntity) {
        return accountFactory.create(accountEntity);
    }
}

