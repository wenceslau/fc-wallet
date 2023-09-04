package com.fullcycle.wallet.core.usecases.dtos;

import java.util.UUID;

public class CreateTransactionInputDTO {
    UUID accountIdFrom;
    UUID accountIdTo;
    float amount;

    public UUID getAccountIdFrom() {
        return accountIdFrom;
    }

    public CreateTransactionInputDTO setAccountIdFrom(UUID accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
        return this;
    }

    public UUID getAccountIdTo() {
        return accountIdTo;
    }

    public CreateTransactionInputDTO setAccountIdTo(UUID accountIdTo) {
        this.accountIdTo = accountIdTo;
        return this;
    }

    public float getAmount() {
        return amount;
    }

    public CreateTransactionInputDTO setAmount(float amount) {
        this.amount = amount;
        return this;
    }
}
