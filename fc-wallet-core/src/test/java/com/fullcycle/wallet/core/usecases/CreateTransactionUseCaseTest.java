package com.fullcycle.wallet.core.usecases;

import com.fullcycle.utils.events.Event;
import com.fullcycle.utils.events.EventDispatcher;
import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.event.BalanceUpdated;
import com.fullcycle.wallet.core.event.TransactionCreated;
import com.fullcycle.wallet.core.uow.UnitOfWork;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateTransactionUseCaseTest {

    @Mock
    UnitOfWork unitOfWork;

    @Mock
    EventDispatcher eventDispatcher;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() throws Exception {

        //Mocks
        Client client1 = new Client("John1", "j1@j");
        Client client2 = new Client("John2", "j2@j");

        Account account1 = new Account(client1);
        account1.credit(2000);
        Account account2 = new Account(client2);
        account2.credit(3000);

        //Object that is being tested
        CreateTransactionInputDTO input = new CreateTransactionInputDTO();
        input.setAmount(2000);
        input.setAccountIdFrom(account1.getId());
        input.setAccountIdTo(account2.getId());

        Event event = new TransactionCreated();
        Event balanceUpdated = new BalanceUpdated();

        CreateTransactionUseCase uc = new CreateTransactionUseCase(unitOfWork, eventDispatcher, event, balanceUpdated);
        CreateTransactionOutputDTO output = uc.execute(input);

        //Assertions
        assertNotNull(output);

        //check if the method save was called
        verify(unitOfWork).doTransaction(any(Function.class));
        verify(unitOfWork, times(1)).doTransaction(any(Function.class));

    }
}