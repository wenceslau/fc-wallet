package com.fullcycle.wallet.core.usecases.dtos;

import java.util.UUID;

public class CreateAccountOutputDTO {
    UUID id;

    public UUID getId() {
        return id;
    }

    public CreateAccountOutputDTO setId(UUID id) {
        this.id = id;
        return this;
    }
}
