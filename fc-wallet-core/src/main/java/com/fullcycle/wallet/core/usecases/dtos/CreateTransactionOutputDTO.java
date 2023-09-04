package com.fullcycle.wallet.core.usecases.dtos;

import java.util.UUID;

public class CreateTransactionOutputDTO {
    UUID id;

    public UUID getId() {
        return id;
    }

    public CreateTransactionOutputDTO setId(UUID id) {
        this.id = id;
        return this;
    }
}
