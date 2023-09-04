package com.fullcycle.wallet.core.usecases;

import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.ClientGateway;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CreateClientUseCaseTest {

    @Mock
    ClientGateway clientGateway;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() throws Exception {

        //Mocks
        doNothing().when(clientGateway).save(any(Client.class));

        //Testing
        CreateClientUseCase uc = new CreateClientUseCase(clientGateway);
        CreateClientInputDTO input = new CreateClientInputDTO();
        input.setName("John Doe");
        input.setEmail("j@j");
        CreateClientOutputDTO output = uc.execute(input);

        //Assertions
        assertNotNull(output);
        assertNotNull(output.getId());
        assertEquals("John Doe", output.getName());
        assertEquals("j@j", output.getEmail());
        //check if the method save was called
        verify(clientGateway).save(any(Client.class));
        //check if the method save was called only once
        verify(clientGateway, times(1)).save(any(Client.class));

    }
}