package com.fullcycle.wallet.core.usecases.dtos;

import java.util.UUID;

public class CreateAccountInputDTO {
    String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
