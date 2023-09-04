package com.fullcycle.wallet.core.usecases;

import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.AccountGateway;
import com.fullcycle.wallet.core.gateway.ClientGateway;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAccountUseCaseTest {

    @Mock
    ClientGateway clientGateway;

    @Mock
    AccountGateway accountGateway;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() throws Exception {

        //Mocks
        Client client = new Client("John", "j@j");
        when(clientGateway.get(client.getId())).thenReturn(client);
        doNothing().when(accountGateway).save(any(Account.class));

        //Object that is being tested
        CreateAccountInputDTO input = new CreateAccountInputDTO();
        input.setClientId(client.getId().toString());
        CreateAccountUseCase uc = new CreateAccountUseCase(accountGateway, clientGateway);
        CreateAccountOutputDTO output = uc.execute(input);

        //Assertions
        assertNotNull(output);
        assertNotNull(output.getId());
        //check if the method save was called
        verify(accountGateway).save(any(Account.class));
        //check if the method save was called only once
        verify(accountGateway, times(1)).save(any(Account.class));
        verify(clientGateway, times(1)).get(any(UUID.class));


    }
}