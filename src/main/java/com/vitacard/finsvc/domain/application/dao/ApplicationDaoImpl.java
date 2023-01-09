package com.vitacard.finsvc.domain.application.dao;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@PropertySource("classpath:db/sql/application.xml")
@Repository
public class ApplicationDaoImpl implements ApplicationDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private Environment environment;

    private IndividualApplicationRowMapper individualApplicationRowMapper;

    public ApplicationDaoImpl() {
        individualApplicationRowMapper = new IndividualApplicationRowMapper();
    }
    @Override
    public IndividualApplication addApplication(IndividualApplication individualApplication) {
        String sql = environment.getProperty("addApplication");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", individualApplication.getId())
                .addValue("document_type_code", individualApplication.getDocumentType().getCode())
                .addValue("first_name", individualApplication.getFullName().getFirst())
                .addValue("last_name", individualApplication.getFullName().getLast())
                .addValue("ssn", individualApplication.getSsn())
                .addValue("street", individualApplication.getAddress().getStreet())
                .addValue("street2", individualApplication.getAddress().getStreet2())
                .addValue("city", individualApplication.getAddress().getCity())
                .addValue("state", individualApplication.getAddress().getState())
                .addValue("postal_code", individualApplication.getAddress().getPostalCode())
                .addValue("country", individualApplication.getAddress().getCountry())
                .addValue("date_of_birth", individualApplication.getDateOfBirth())
                .addValue("email", individualApplication.getEmail())
                .addValue("phone", individualApplication.getPhone().fullNumber())
                .addValue("status_code", individualApplication.getStatusCode())
                .addValue("message", individualApplication.getMessage())
                .addValue("archived", individualApplication.getArchived());

        assert sql != null;
        jdbcTemplate.update(sql, parameterSource);
        return getApplicationById(individualApplication.getId());
    }

    @Override
    public IndividualApplication getApplicationById(String id) {
        String sql = environment.getProperty("getApplicationById");
        assert sql != null;
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);

        IndividualApplication individualApplication = jdbcTemplate.queryForObject(sql, parameterSource, individualApplicationRowMapper);
        return individualApplication;
    }
}
