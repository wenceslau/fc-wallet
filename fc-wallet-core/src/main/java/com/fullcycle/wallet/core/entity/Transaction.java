package com.fullcycle.wallet.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private Account accountFrom;
    private Account accountTo;
    private double amount;
    private LocalDateTime createdAt;

    public Transaction(Account accountFrom, Account accountTo, double amount) {
        this.id = UUID.randomUUID();
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();

        validate();
        commit();
    }

    private void commit(){
        accountFrom.debit(amount);
        accountTo.credit(amount);
    }

    private void validate(){
        if (amount <= 0){
            throw new RuntimeException("amount must be greater than zero");
        }

        if (accountFrom.getBalance() < amount){
            throw new RuntimeException("insufficient funds");
        }
    }

    public UUID getId() {
        return id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
