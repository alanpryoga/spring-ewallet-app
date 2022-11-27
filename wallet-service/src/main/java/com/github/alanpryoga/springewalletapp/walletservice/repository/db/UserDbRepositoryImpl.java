package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDbRepositoryImpl implements UserDbRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Database query for get list of user by given user type.
     *
     * @param userType user type
     * @return
     */
    @Override
    public List<User> list(UserType userType) {
        String userTypeStr = "";
        if (userType == UserType.MERCHANT) {
            userTypeStr = "merchant";
        } else {
            userTypeStr = "customer";
        }

        String sql = "SELECT * FROM public.user WHERE user_type = '" + userTypeStr + "'";

        try {
            return jdbcTemplate.query(sql, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Database query for get detail user by given user id.
     *
     * @param userId user id
     * @return
     */
    @Override
    public User detail(int userId) {
        String sql = "SELECT * FROM public.user WHERE id = ? LIMIT 1";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Database query for create new user by given user info.
     *
     * @param user user info
     * @return
     */
    @Override
    public int create(User user) {
        String sql = "INSERT INTO public.user (name, phone, user_type) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(sql, new String [] {"id"});
                statement.setString(1, user.getName());
                statement.setString(2, user.getPhone());
                if (user.getUserType() == UserType.CUSTOMER) {
                    statement.setString(3, "customer");
                } else {
                    statement.setString(3, "merchant");
                }
                return statement;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

}

class UserRowMapper implements RowMapper<User>
{

    /**
     * Mapping result set into user info.
     *
     * @param rs result set
     * @param rowNum row num
     * @return
     * @throws SQLException
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserType userType = UserType.CUSTOMER;
        String userTypeStr = rs.getString("user_type");
        if (userTypeStr.equals("merchant")) {
            userType = UserType.MERCHANT;
        }

        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("phone"),
                userType
        );
    }

}