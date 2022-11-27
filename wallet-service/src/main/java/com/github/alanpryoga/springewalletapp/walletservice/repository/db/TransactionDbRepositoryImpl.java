package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class TransactionDbRepositoryImpl implements TransactionDbRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void topUp(int amount, int userId) throws Exception {
        String sqlGetWalletIdByUserId = "SELECT * FROM public.wallet WHERE user_id = ? LIMIT 1";
        String sqlCreateWallet = "INSERT INTO public.wallet (user_id) VALUES (?)";
        String sqlCreateTransaction = "INSERT INTO public.transaction (wallet_id, amount, description, transaction_type) VALUES (?, ?, ?, ?)";
        String sqlUpdateWallet = "UPDATE wallet SET balance = balance + ? WHERE id = ?";

        transactionTemplate.execute(new TransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                // Get user wallet
                int walletId = 0;
                try {
                    Wallet wallet = jdbcTemplate.queryForObject(sqlGetWalletIdByUserId, new Object[]{userId}, new WalletRowMapper());
                    walletId = wallet.getId();
                } catch (Exception e) {
                    walletId = 0;
                }

                // If wallet does not exist, create
                if (walletId == 0) {
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update(con -> {
                        PreparedStatement statement = con.prepareStatement(sqlCreateWallet, new String [] {"id"});
                        statement.setInt(1, userId);
                        return statement;
                    }, keyHolder);

                    walletId = Objects.requireNonNull(keyHolder.getKey()).intValue();
                }

                // Create transaction (credit)
                jdbcTemplate.update(sqlCreateTransaction, walletId, amount, "topup", "credit");

                // Update wallet
                jdbcTemplate.update(sqlUpdateWallet, amount, walletId);

                return null;
            }

        });
    }

    @Override
    public void payment(int amount, int customerId, int merchantId) throws Exception {
        String sqlGetWalletIdByUserId = "SELECT * FROM public.wallet WHERE user_id = ? LIMIT 1";
        String sqlCreateWallet = "INSERT INTO public.wallet (user_id) VALUES (?)";
        String sqlCreateTransaction = "INSERT INTO public.transaction (wallet_id, amount, description, transaction_type) VALUES (?, ?, ?, ?)";
        String sqlUpdateWalletAdd = "UPDATE wallet SET balance = balance + ? WHERE id = ?";
        String sqlUpdateWalletSub = "UPDATE wallet SET balance = balance - ? WHERE id = ?";

        transactionTemplate.execute(new TransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                // Get customer wallet
                int customerWalletId = 0;
                try {
                    Wallet customerWallet = jdbcTemplate.queryForObject(sqlGetWalletIdByUserId, new Object[]{customerId}, new WalletRowMapper());
                    customerWalletId = customerWallet.getId();
                } catch (Exception e) {
                    customerWalletId = 0;
                }

                // If customer wallet does not exist, create
                if (customerWalletId == 0) {
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update(con -> {
                        PreparedStatement statement = con.prepareStatement(sqlCreateWallet, new String [] {"id"});
                        statement.setInt(1, customerId);
                        return statement;
                    }, keyHolder);

                    customerWalletId = Objects.requireNonNull(keyHolder.getKey()).intValue();
                }

                // Get merchant wallet
                int merchantWalletId = 0;
                try {
                    Wallet merchantWallet = jdbcTemplate.queryForObject(sqlGetWalletIdByUserId, new Object[]{merchantId}, new WalletRowMapper());
                    merchantWalletId = merchantWallet.getId();
                } catch (Exception e) {
                    merchantWalletId = 0;
                }

                // If merchant wallet does not exist, create
                if (merchantWalletId == 0) {
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update(con -> {
                        PreparedStatement statement = con.prepareStatement(sqlCreateWallet, new String [] {"id"});
                        statement.setInt(1, merchantId);
                        return statement;
                    }, keyHolder);

                    merchantWalletId = Objects.requireNonNull(keyHolder.getKey()).intValue();
                }

                // Create transaction for customer (debit)
                jdbcTemplate.update(sqlCreateTransaction, customerWalletId, amount, "payment", "debit");

                // Create transaction for merchant (credit)
                jdbcTemplate.update(sqlCreateTransaction, merchantWalletId, amount, "payment", "credit");

                // Update wallet for customer (sub)
                jdbcTemplate.update(sqlUpdateWalletSub, amount, customerWalletId);

                // Update wallet for merchant (add)
                jdbcTemplate.update(sqlUpdateWalletAdd, amount, merchantWalletId);

                return null;
            }

        });
    }

}
