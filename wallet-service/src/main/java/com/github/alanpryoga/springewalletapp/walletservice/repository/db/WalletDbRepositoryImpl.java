package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WalletDbRepositoryImpl implements WalletDbRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Database query for get wallet balance info by given user id.
     *
     * @param userId user id
     * @return
     */
    @Override
    public int balance(int userId) {
        String sql = "SELECT * FROM public.wallet WHERE user_id = ? LIMIT 1";

        try {
            Wallet wallet = jdbcTemplate.queryForObject(sql, new Object[]{userId}, new WalletRowMapper());

            return wallet.getBalance();
        } catch (Exception e) {
            return 0;
        }
    }

}

class WalletRowMapper implements RowMapper<Wallet>
{
    /**
     * Mapping result set into wallet info.
     *
     * @param rs result set
     * @param rowNum row num
     * @return
     * @throws SQLException
     */
    @Override
    public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Wallet(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("balance")
        );
    }

}