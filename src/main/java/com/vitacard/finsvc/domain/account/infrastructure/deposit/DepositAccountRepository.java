package com.vitacard.finsvc.domain.account.infrastructure.deposit;

import com.vitacard.finsvc.commons.SpringApplicationContext;
import com.vitacard.finsvc.domain.account.infrastructure.account.AccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.CreateDepositAccountEvent;
import com.vitacard.finsvc.domain.account.model.DepositAccount;
import com.vitacard.finsvc.domain.account.model.DepositAccountFactory;
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
@PropertySource("classpath:db/sql/deposit_account.xml")
public class DepositAccountRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private AccountRepository accountRepository;
    private final DepositAccountEntityRowMapper depositAccountEntityRowMapper;
    private final DepositAccountDomainModelMapper depositAccountDomainModelMapper;

    public DepositAccountRepository() {
        depositAccountEntityRowMapper = new DepositAccountEntityRowMapper();
        depositAccountDomainModelMapper = new DepositAccountDomainModelMapper();
    }

    public DepositAccountEntity addDepositAccountEntity(DepositAccountEntity depositAccountEntity) {
        String sql = environment.getProperty("addDepositAccount");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", depositAccountEntity.getId())
                .addValue("deposit_product_code", depositAccountEntity.getDepositProductCode())
                .addValue("routing_number", depositAccountEntity.getRoutingNumber())
                .addValue("account_number", depositAccountEntity.getAccountNumber());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);
        return getDepositAccountEntityById(depositAccountEntity.getId());
    }

    public DepositAccountEntity getDepositAccountEntityById(String id) {
        String sql = environment.getProperty("getDepositAccountById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);

        DepositAccountEntity depositAccountEntity = jdbcTemplate.queryForObject(sql, parameterSource, depositAccountEntityRowMapper);
        return depositAccountEntity;
    }

    public DepositAccount publish(AccountEvent event) {
        DepositAccount result = Match(event).of(
                Case($(instanceOf(CreateDepositAccountEvent.class)), this::createNewDepositAccount));

        return result;
    }

    public DepositAccount createNewDepositAccount(CreateDepositAccountEvent createDepositAccountEvent) {
        DepositAccountEntity addedDepositAccountEntity = addDepositAccountEntity(createDepositAccountEvent.getDepositAccountEntity());

        return depositAccountDomainModelMapper.map(addedDepositAccountEntity);
    }
}

@AllArgsConstructor
class DepositAccountDomainModelMapper {
    private DepositAccountFactory depositAccountFactory;

    DepositAccountDomainModelMapper() {
        depositAccountFactory = SpringApplicationContext.getDepositAccountFactory();
    }

    DepositAccount map(DepositAccountEntity depositAccountEntity) {
        return depositAccountFactory.create(depositAccountEntity);
    }
}

class DepositAccountEntityRowMapper implements RowMapper<DepositAccountEntity> {
    @Override
    public DepositAccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DepositAccountEntity.builder()
                .id(rs.getString("id"))
                .depositProductCode(rs.getInt("deposit_product_code"))
                .routingNumber(rs.getString("routing_number"))
                .accountNumber(rs.getString("account_number"))
                .build();
    }
}
