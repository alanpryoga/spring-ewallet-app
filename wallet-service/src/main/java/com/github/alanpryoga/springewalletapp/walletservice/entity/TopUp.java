package com.github.alanpryoga.springewalletapp.walletservice.entity;

public class TopUp {

    private int amount;

    private int userId;

    public TopUp(int amount, int userId) {
        this.amount = amount;
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
