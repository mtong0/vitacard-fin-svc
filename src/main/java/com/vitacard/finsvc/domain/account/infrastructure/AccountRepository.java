package com.vitacard.finsvc.domain.account.infrastructure;

import com.vitacard.finsvc.configuration.SpringApplicationContext;
import com.vitacard.finsvc.domain.account.model.Account;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.AccountTransactionEvent;
import com.vitacard.finsvc.domain.account.model.AccountFactory;
import com.vitacard.finsvc.domain.account.model.DepositAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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
@DependsOn("springApplicationContext")
@PropertySource("classpath:db/sql/account.xml")
public class AccountRepository implements IAccountRepository {
    @Autowired
    private Environment environment;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private final AccountEntityRowMapper accountEntityRowMapper;
    private final DepositAccountEntityRowMapper depositAccountEntityRowMapper;
    private final AccountDomainModelMapper accountDomainModelMapper;

    public AccountRepository() {
        accountEntityRowMapper = new AccountEntityRowMapper();
        accountDomainModelMapper = new AccountDomainModelMapper();
        depositAccountEntityRowMapper = new DepositAccountEntityRowMapper();
    }

    public Account addAccount(DepositAccount depositAccount) {
        DepositAccountEntity depositAccountEntity = accountDomainModelMapper.map(depositAccount);
        return addAccount(depositAccountEntity);
    }

    public Account getAccountById(String id) {
        String sql = environment.getProperty("getAccountById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        AccountEntity accountEntity = jdbcTemplate.queryForObject(sql, parameterSource, accountEntityRowMapper);
        return accountDomainModelMapper.map(accountEntity);
    }
    public Account publish(AccountEvent event) {
        Account result = Match(event).of(
                Case($(instanceOf(AccountTransactionEvent.class)), this::processTransaction));

        return result;
    }

    private Account processTransaction(AccountTransactionEvent accountTransactionEvent) {
        AccountEntity accountEntity = getAccountEntityById(accountTransactionEvent.accountId());
        accountEntity.setAvailable(accountTransactionEvent.updatedAvailable());
        accountEntity.setBalance(accountTransactionEvent.updatedBalance());
        AccountEntity updatedAccountEntity = updateAccountEntity(accountEntity);
        return accountDomainModelMapper.map(updatedAccountEntity);
    }
    private DepositAccount getDepositAccountById(String id) {
        String sql = environment.getProperty("getDepositAccountById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        DepositAccountEntity depositAccountEntity = jdbcTemplate.queryForObject(sql, parameterSource, depositAccountEntityRowMapper);
        return accountDomainModelMapper.map(depositAccountEntity);
    }

    private void addAccount(AccountEntity accountEntity) {
        String sql = environment.getProperty("addAccount");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", accountEntity.getId())
                .addValue("created_at", accountEntity.getCreatedAt())
                .addValue("status_code", accountEntity.getStatusCode())
                .addValue("currency", accountEntity.getCurrency())
                .addValue("balance", accountEntity.getBalance())
                .addValue("hold", accountEntity.getHold())
                .addValue("available", accountEntity.getAvailable())
                .addValue("customer_id", accountEntity.getCustomerId())
                .addValue("type", accountEntity.getType());
        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);

        getAccountById(accountEntity.getId());
    }

    private DepositAccount addAccount(DepositAccountEntity depositAccountEntity) {
        String sql = environment.getProperty("addDepositAccount");
        addAccount((AccountEntity) depositAccountEntity);

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", depositAccountEntity.getId())
                .addValue("deposit_product_code", depositAccountEntity.getDepositProductCode())
                .addValue("routing_number", depositAccountEntity.getRoutingNumber())
                .addValue("account_number", depositAccountEntity.getAccountNumber());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);

        return getDepositAccountById(depositAccountEntity.getId());
    }

    private AccountEntity updateAccountEntity(AccountEntity accountEntity) {
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

    private AccountEntity getAccountEntityById(String id) {
        String sql = environment.getProperty("getAccountById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, accountEntityRowMapper);
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
                .type(rs.getString("type"))
                .build();
    }
}

class DepositAccountEntityRowMapper implements RowMapper<DepositAccountEntity> {
    @Override
    public DepositAccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DepositAccountEntity.depositAccountEntityBuilder()
                .id(rs.getString("id"))
                .createdAt(rs.getTimestamp("created_at"))
                .statusCode(rs.getInt("status_code"))
                .currency(rs.getString("currency"))
                .balance(rs.getLong("balance"))
                .hold(rs.getLong("hold"))
                .available(rs.getLong("available"))
                .customerId(rs.getString("customer_id"))
                .type(rs.getString("type"))
                .depositProductCode(rs.getInt("deposit_product_code"))
                .routingNumber(rs.getString("routing_number"))
                .accountNumber(rs.getString("account_number"))
                .build();
    }
}

class AccountDomainModelMapper {
    private final AccountFactory accountFactory;

    AccountDomainModelMapper() {
        accountFactory = SpringApplicationContext.getBean(AccountFactory.class);
    }

    Account map(AccountEntity accountEntity) {
        return accountFactory.create(accountEntity);
    }

    DepositAccount map(DepositAccountEntity depositAccountEntity) {
        return accountFactory.create(depositAccountEntity);
    }

    DepositAccountEntity map(DepositAccount depositAccount) {
        return DepositAccountEntity.depositAccountEntityBuilder()
                .id(depositAccount.getId())
                .createdAt(depositAccount.getCreatedAt())
                .statusCode(depositAccount.getStatus().getCode())
                .currency(depositAccount.getCurrency())
                .balance(depositAccount.getBalance())
                .hold(depositAccount.getHold())
                .available(depositAccount.getAvailable())
                .customerId(depositAccount.getCustomerId())
                .type(depositAccount.getType())
                .accountNumber(depositAccount.getAccountNumber())
                .routingNumber(depositAccount.getRoutingNumber())
                .depositProductCode(depositAccount.getDepositProduct().getCode())
                .build();
    }
}



