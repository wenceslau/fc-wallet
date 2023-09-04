package com.fullcycle.wallet.core.cmd;

import com.fullcycle.utils.events.Event;
import com.fullcycle.utils.events.EventDispatcher;
import com.fullcycle.utils.events.EventDispatcherImpl;
import com.fullcycle.utils.events.EventHandler;
import com.fullcycle.wallet.core.database.AccountDB;
import com.fullcycle.wallet.core.database.ClientDB;
import com.fullcycle.wallet.core.database.TransactionDB;
import com.fullcycle.wallet.core.event.BalanceUpdated;
import com.fullcycle.wallet.core.event.TransactionCreated;
import com.fullcycle.wallet.core.handler.BalanceUpdatedKafkaHandler;
import com.fullcycle.wallet.core.handler.TransactionCreatedKafkaHandler;
import com.fullcycle.wallet.core.uow.UnitOfWork;
import com.fullcycle.wallet.core.uow.UnitOfWorkImpl;
import com.fullcycle.wallet.core.usecases.CreateAccountUseCase;
import com.fullcycle.wallet.core.usecases.CreateClientUseCase;
import com.fullcycle.wallet.core.usecases.CreateTransactionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class SpringBean {

    @Bean
    public CreateClientUseCase createClientUseCase(Connection conn) throws SQLException {

        ClientDB clientDB = new ClientDB(conn);

        CreateClientUseCase createClientUseCase = new CreateClientUseCase(clientDB);

        return createClientUseCase;
    }

    @Bean
    public CreateAccountUseCase createAccountUseCase(Connection conn) throws SQLException {

        ClientDB clientDB = new ClientDB(conn);
        AccountDB accountDB = new AccountDB(conn);

        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountDB, clientDB);

        return createAccountUseCase;
    }

    @Bean
    public CreateTransactionUseCase createTransactionUseCase(UnitOfWork unitOfWorkImpl, KafkaTemplate<String, String> kafkaTemplate) throws Exception {

        EventDispatcher eventDispatcher = new EventDispatcherImpl();
        EventHandler createdKafkaHandler = new TransactionCreatedKafkaHandler(kafkaTemplate);
        EventHandler updatedKafkaHandler = new BalanceUpdatedKafkaHandler(kafkaTemplate);

        eventDispatcher.register("TransactionCreated", createdKafkaHandler);
        eventDispatcher.register("BalanceUpdated", updatedKafkaHandler);

        Event transactionCreated = new TransactionCreated();
        Event balanceUpdated = new BalanceUpdated();

        CreateTransactionUseCase createTransactionUseCase = new CreateTransactionUseCase(unitOfWorkImpl, eventDispatcher, transactionCreated, balanceUpdated);

        return createTransactionUseCase;
    }

    @Bean
    public UnitOfWork unitOfWork(Connection conn) throws SQLException {

        UnitOfWork uow = new UnitOfWorkImpl(conn);
        //Two ways of pass functions to a method
        uow.register("AccountDB", tx -> new AccountDB(conn));
        uow.register("TransactionDB", new Function<Connection, Object>() {
            public Object apply(Connection tx) {
                return new TransactionDB(conn);
            }
        });
        System.out.println("UnitOf Work Created");
        return uow;
    }

    @Bean
    public Connection connection() throws SQLException {

        String connectionUrl = "jdbc:mysql://db-mysql-wallet:3306/wallet?serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(connectionUrl, "root", "r00t");
        conn.setAutoCommit(false);
        System.out.println("Opened database successfully");

        return conn;
    }

}
