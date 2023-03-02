package com.vitacard.finsvc.domain.transaction.infrasturcture;

import com.vitacard.finsvc.configuration.SpringApplicationContext;
import com.vitacard.finsvc.domain.transaction.model.CardTransaction;
import com.vitacard.finsvc.domain.transaction.model.ITransactionFactory;
import com.vitacard.finsvc.domain.transaction.model.Transaction;
import com.vitacard.finsvc.domain.transaction.model.TransactionFactory;

import static io.vavr.API.Case;
import static io.vavr.API.Match;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import static io.vavr.API.$;

@PropertySource("classpath:db/sql/transaction.xml")
@Repository
public class TransactionRepository implements ITransactionRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private TransactionDomainModelMapper transactionDomainModelMapper;
    private final CardTransactionEntityRowMapper cardTransactionEntityRowMapper = new CardTransactionEntityRowMapper();


    public Transaction addTransaction(CardTransaction cardTransaction) {
        CardTransactionEntity cardTransactionEntity = transactionDomainModelMapper.map(cardTransaction);
        return addTransaction(cardTransactionEntity);
    }

    public Transaction getTransactionById(String id, String type) {
        Transaction transaction = Match(type).of(
                Case($("cardTransaction"), ()-> getCardTransactionById(id))
        );
        return transaction;
    }
    private void addTransaction(TransactionEntity transactionEntity) {
        String sql = environment.getProperty("addTransaction");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", transactionEntity.getId())
                .addValue("amount", transactionEntity.getAmount())
                .addValue("balance", transactionEntity.getBalance())
                .addValue("available", transactionEntity.getAvailable())
                .addValue("summary", transactionEntity.getSummary())
                .addValue("created_at", transactionEntity.getCreatedAt())
                .addValue("direction", transactionEntity.getDirection())
                .addValue("type", transactionEntity.getType());
        assert sql != null;
        jdbcTemplate.update(sql, sqlParameterSource);
    }
    private CardTransaction addTransaction (CardTransactionEntity cardTransactionEntity) {
        addTransaction((TransactionEntity) cardTransactionEntity);
        String sql = environment.getProperty("addCardTransaction");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", cardTransactionEntity.getId())
                .addValue("card_last_4_digits", cardTransactionEntity.getCardLast4Digits())
                .addValue("merchant_type", cardTransactionEntity.getMerchantType())
                .addValue("merchant_category", cardTransactionEntity.getMerchantCategory())
                .addValue("merchant_name", cardTransactionEntity.getMerchantName());
        assert sql != null;
        jdbcTemplate.update(sql, sqlParameterSource);
        return getCardTransactionById(cardTransactionEntity.getId());
    }

    private CardTransaction getCardTransactionById(String id) {
        String sql = environment.getProperty("getCardTransactionById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);

        CardTransactionEntity cardTransactionEntity = jdbcTemplate.queryForObject(sql, parameterSource, cardTransactionEntityRowMapper);
        return transactionDomainModelMapper.map(cardTransactionEntity);
    }
}

class CardTransactionEntityRowMapper implements RowMapper<CardTransactionEntity> {
    @Override
    public CardTransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CardTransactionEntity.builder()
                .id(rs.getString("id"))
                .createdAt(rs.getTimestamp("created_at"))
                .amount(rs.getLong("amount"))
                .balance(rs.getLong("balance"))
                .available(rs.getLong("available"))
                .type(rs.getString("type"))
                .cardLast4Digits(rs.getString("card_last_4_digits"))
                .merchantCategory(rs.getString("merchant_category"))
                .merchantName(rs.getString("merchant_name"))
                .merchantType(rs.getString("merchant_type"))
                .build();
    }
}

@Component
class TransactionDomainModelMapper {
    @Autowired
    private ITransactionFactory transactionFactory;
    CardTransaction map(CardTransactionEntity transactionEntity) {
        return transactionFactory.create(transactionEntity);
    }

    CardTransactionEntity map(CardTransaction cardTransaction) {
        return CardTransactionEntity.builder()
                .id(cardTransaction.getId())
                .createdAt(cardTransaction.getCreatedAt())
                .available(cardTransaction.getAvailable())
                .balance(cardTransaction.getBalance())
                .amount(cardTransaction.getAmount())
                .type(cardTransaction.getType())
                .direction(cardTransaction.getDirection())
                .summary(cardTransaction.getSummary())
                .merchantType(cardTransaction.getMerchantType())
                .cardLast4Digits(cardTransaction.getCardLast4Digits())
                .merchantName(cardTransaction.getMerchantName())
                .merchantCategory(cardTransaction.getMerchantCategory())
                .build();
    }
}