package com.vitacard.finsvc.domain.application.dao;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndividualApplicationRowMapper implements RowMapper<IndividualApplication> {
    @Override
    public IndividualApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new IndividualApplication()
                .setId(rs.getString("id"))
                .setFullName(new FullName()
                        .setFirst(rs.getString("first_name"))
                        .setLast(rs.getString("last_name")))
                .setAddress(new Address()
                        .setStreet(rs.getString("street"))
                        .setStreet2(rs.getString("street2"))
                        .setCity(rs.getString("city"))
                        .setState(rs.getString("state"))
                        .setCountry(rs.getString("country"))
                        .setPostalCode(rs.getString("postal_code")))
                .setPhone(new Phone().setNumber(rs.getString("phone")))
                .setDateOfBirth(rs.getTimestamp("date_of_birth"))
                .setEmail(rs.getString("email"));
    }
}
