package com.fullcycle.wallet.core.usecases;

import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.ClientGateway;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientOutputDTO;

public class CreateClientUseCase {

    ClientGateway clientGateway;

    public CreateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public CreateClientOutputDTO execute(CreateClientInputDTO input) throws Exception {
        Client client = new Client(input.getName(), input.getEmail());

        clientGateway.save(client);

        CreateClientOutputDTO output = new CreateClientOutputDTO();
        output.setId(client.getId());
        output.setName(client.getName());
        output.setEmail(client.getEmail());
        output.setCreateAt(client.getCreatedAt());
        output.setUpdateAt(client.getUpdatedAt());

        clientGateway.commit();

        return output;
    }
}

