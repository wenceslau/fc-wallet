package com.fullcycle.wallet.core.usecases.dtos;

import java.util.UUID;

public class BalanceUpdatedOutputDTO {
    UUID accountIdFrom;
    UUID accountIdTo;
    double balanceAccountFrom;
    double balanceAccountTo;

    public UUID getAccountIdFrom() {
        return accountIdFrom;
    }

    public BalanceUpdatedOutputDTO setAccountIdFrom(UUID accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
        return this;
    }

    public UUID getAccountIdTo() {
        return accountIdTo;
    }

    public BalanceUpdatedOutputDTO setAccountIdTo(UUID accountIdTo) {
        this.accountIdTo = accountIdTo;
        return this;
    }

    public double getBalanceAccountFrom() {
        return balanceAccountFrom;
    }

    public BalanceUpdatedOutputDTO setBalanceAccountFrom(double balanceAccountFrom) {
        this.balanceAccountFrom = balanceAccountFrom;
        return this;
    }

    public double getBalanceAccountTo() {
        return balanceAccountTo;
    }

    public BalanceUpdatedOutputDTO setBalanceAccountTo(double balanceAccountTo) {
        this.balanceAccountTo = balanceAccountTo;
        return this;
    }
}
