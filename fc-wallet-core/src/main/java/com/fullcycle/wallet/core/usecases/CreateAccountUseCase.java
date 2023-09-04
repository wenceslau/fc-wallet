package com.fullcycle.wallet.core.usecases;

import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.AccountGateway;
import com.fullcycle.wallet.core.gateway.ClientGateway;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountOutputDTO;

import java.util.UUID;

public class CreateAccountUseCase {

    AccountGateway accountGateway;
    ClientGateway clientGateway;

    public CreateAccountUseCase(AccountGateway accountGateway, ClientGateway clientGateway) {
        this.accountGateway = accountGateway;
        this.clientGateway = clientGateway;
    }

    public CreateAccountOutputDTO execute(CreateAccountInputDTO input) throws Exception {

        Client client = clientGateway.get(UUID.fromString(input.getClientId()));
        Account account = new Account(client);
        accountGateway.save(account);

        CreateAccountOutputDTO output = new CreateAccountOutputDTO();
        output.setId(account.getId());

        accountGateway.commit();

        return output;
    }

}


