package com.fullcycle.wallet.core.usecases;

import com.fullcycle.utils.events.Event;
import com.fullcycle.utils.events.EventDispatcher;
import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Transaction;
import com.fullcycle.wallet.core.gateway.AccountGateway;
import com.fullcycle.wallet.core.gateway.TransactionGateway;
import com.fullcycle.wallet.core.uow.UnitOfWork;
import com.fullcycle.wallet.core.usecases.dtos.BalanceUpdatedOutputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionOutputDTO;

public class CreateTransactionUseCase {

//    TransactionGateway transactionGateway;
//    AccountGateway accountGateway;
    EventDispatcher eventDispatcher;
    Event transactionCreated;
    Event balanceUpdated;


    UnitOfWork unitOfWork;

    public CreateTransactionUseCase(UnitOfWork unitOfWork,
                                    EventDispatcher eventDispatcher,
                                    Event transactionCreated,
                                    Event balanceUpdated) {
        this.unitOfWork = unitOfWork;
        this.eventDispatcher = eventDispatcher;
        this.transactionCreated = transactionCreated;
        this.balanceUpdated = balanceUpdated;
    }

    public CreateTransactionOutputDTO execute(CreateTransactionInputDTO input) throws Exception {

        CreateTransactionOutputDTO output = new CreateTransactionOutputDTO();
        BalanceUpdatedOutputDTO balanceOutput = new BalanceUpdatedOutputDTO();

        unitOfWork.doTransaction((x) -> {
            try {

                AccountGateway accountGateway = (AccountGateway) unitOfWork.getRepository("AccountDB");
                TransactionGateway transactionGateway = (TransactionGateway) unitOfWork.getRepository("TransactionDB");

                Account accountFrom = accountGateway.findById(input.getAccountIdFrom());
                if (accountFrom == null){
                    throw new RuntimeException("Account "+ input.getAccountIdFrom() + " does not exist");
                }
                Account accountTo = accountGateway.findById(input.getAccountIdTo());
                if (accountTo == null){
                    throw new RuntimeException("Account "+ input.getAccountIdTo() + " does not exist");
                }
                Transaction transaction = new Transaction(accountFrom, accountTo, input.getAmount());

                accountGateway.updateBalance(transaction.getAccountFrom());
                accountGateway.updateBalance(transaction.getAccountTo());
                transactionGateway.create(transaction);

                output.setId(transaction.getId());

                balanceOutput.setAccountIdFrom(accountFrom.getId());
                balanceOutput.setAccountIdTo(accountTo.getId());
                balanceOutput.setBalanceAccountFrom(accountFrom.getBalance());
                balanceOutput.setBalanceAccountTo(accountTo.getBalance());

            } catch (Exception e) {
                System.err.println("Error to save transaction..:"+ e.getMessage());
                throw new RuntimeException(e);
            }
            return null;
        });

        transactionCreated.setPayload(output);
        eventDispatcher.dispatch(transactionCreated);

        balanceUpdated.setPayload(balanceOutput);
        eventDispatcher.dispatch(balanceUpdated);

        return output;
    }
}

