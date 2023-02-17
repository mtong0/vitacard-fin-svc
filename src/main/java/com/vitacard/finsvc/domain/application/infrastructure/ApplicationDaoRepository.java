package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.domain.application.model.ApplicationEvent;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateIndividualApplicationEvent;
import com.vitacard.finsvc.domain.application.model.IndividualApplication;
import com.vitacard.finsvc.domain.application.model.DocumentType;
import com.vitacard.finsvc.domain.application.model.IndividualApplicationFactory;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
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

@PropertySource("classpath:db/sql/application.xml")
@Repository
public class ApplicationDaoRepository implements ApplicationDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private IndividualApplicationFactory individualApplicationFactory;

    private final IndividualApplicationRowMapper individualApplicationRowMapper;

    public ApplicationDaoRepository() {
        individualApplicationRowMapper = new IndividualApplicationRowMapper();
    }
    @Override
    public IndividualApplicationEntity addApplication(IndividualApplicationEntity individualApplicationEntity) {
        String sql = environment.getProperty("addApplication");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", individualApplicationEntity.getId())
                .addValue("document_type_code", individualApplicationEntity.getDocumentType().getCode())
                .addValue("first_name", individualApplicationEntity.getFullName().getFirst())
                .addValue("last_name", individualApplicationEntity.getFullName().getLast())
                .addValue("ssn", individualApplicationEntity.getSsn())
                .addValue("street", individualApplicationEntity.getAddress().getStreet())
                .addValue("street2", individualApplicationEntity.getAddress().getStreet2())
                .addValue("city", individualApplicationEntity.getAddress().getCity())
                .addValue("state", individualApplicationEntity.getAddress().getState())
                .addValue("postal_code", individualApplicationEntity.getAddress().getPostalCode())
                .addValue("country", individualApplicationEntity.getAddress().getCountry())
                .addValue("date_of_birth", individualApplicationEntity.getDateOfBirth())
                .addValue("email", individualApplicationEntity.getEmail())
                .addValue("phone", individualApplicationEntity.getPhone().getNumber())
                .addValue("status_code", individualApplicationEntity.getStatusCode())
                .addValue("message", individualApplicationEntity.getMessage())
                .addValue("archived", individualApplicationEntity.isArchived())
                .addValue("customer_id", individualApplicationEntity.getCustomerId());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);
        return getApplicationById(individualApplicationEntity.getId());
    }

    @Override
    public IndividualApplicationEntity getApplicationById(String id) {
        String sql = environment.getProperty("getApplicationById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);

        IndividualApplicationEntity individualApplicationEntity = jdbcTemplate.queryForObject(sql, parameterSource, individualApplicationRowMapper);
        return individualApplicationEntity;
    }

    static class IndividualApplicationRowMapper implements RowMapper<IndividualApplicationEntity> {
        @Override
        public IndividualApplicationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return IndividualApplicationEntity.builder()
                    .id(rs.getString("id"))
                    .ssn(rs.getString("ssn"))
                    .documentType(DocumentType.getByCode(rs.getInt("document_type_code")))
                    .fullName(new FullName()
                            .setFirst(rs.getString("first_name"))
                            .setLast(rs.getString("last_name")))
                    .address(new Address()
                            .setStreet(rs.getString("street"))
                            .setStreet2(rs.getString("street2"))
                            .setCity(rs.getString("city"))
                            .setState(rs.getString("state"))
                            .setCountry(rs.getString("country"))
                            .setPostalCode(rs.getString("postal_code")))
                    .phone(new Phone().setNumber(rs.getString("phone")))
                    .dateOfBirth(rs.getTimestamp("date_of_birth"))
                    .email(rs.getString("email"))
                    .statusCode(rs.getInt("status_code"))
                    .customerId(rs.getString("customer_id"))
                    .build();
        }
    }

    public IndividualApplication publish(ApplicationEvent domainEvent) {
        IndividualApplication result = Match(domainEvent).of(
                Case($(instanceOf(CreateIndividualApplicationEvent.class)), this::createNewIndividualApplication));

        return result;
    }

    private IndividualApplication createNewIndividualApplication(CreateIndividualApplicationEvent createIndividualApplicationEvent) {
        IndividualApplicationEntity individualApplicationEntity = createIndividualApplicationEvent.getIndividualApplicationEntity();
        IndividualApplicationEntity addedIndividualApplicationEntity = addApplication(individualApplicationEntity);
        return individualApplicationFactory.create(addedIndividualApplicationEntity);
    }
}
