package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class UserDbRepositoryImpl implements UserDbRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int create(User user) {
        String sql = "INSERT INTO public.user (name, phone, user_type) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, new String [] {"id"});
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getPhone());
                    if (user.getUserType() == UserType.CUSTOMER) {
                        statement.setString(3, "customer");
                    } else {
                        statement.setString(3, "merchant");
                    }
                    return statement;
                }

            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
