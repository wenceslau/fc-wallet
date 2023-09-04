package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class TransactionDBTest {

    private Connection conn;

    void setupConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        conn.setAutoCommit(false);
        System.out.println("Opened database successfully");
        Statement stmt;

        stmt = conn.createStatement();
        stmt.execute( "Create table clients (id varchar(255), name varchar(255), email varchar(255), created_at date)");
        stmt.close();

        stmt = conn.createStatement();
        stmt.execute( "Create table accounts (id varchar(255), client_id varchar(255), balance float, created_at date)");
        stmt.close();

        stmt = conn.createStatement();
        stmt.execute( "Create table transactions (id varchar(255), account_id_from varchar(255), account_id_to varchar(255), amount int, created_at date)");
        stmt.close();
    }

    void tearDownConnection() throws SQLException {

        conn.close();
        conn = null;
    }



    @Test
    void save() throws Exception {
        setupConnection();

        Client client = new Client("Test", "j@j");
        ClientDB clientDB = new ClientDB(conn);
        clientDB.save(client);

        Account accountFrom = new Account(client);
        accountFrom.credit(2000);
        AccountDB accountDB = new AccountDB(conn);
        accountDB.save(accountFrom);

        Client client2 = new Client("Test", "j@j");
        clientDB.save(client);

        Account accountTo = new Account(client2);
        accountTo.credit(2000);
        accountDB.save(accountTo);

        Transaction transaction = new Transaction(accountFrom, accountTo, 100);
        TransactionDB transactionDB = new TransactionDB(conn);
        transactionDB.create(transaction);
        tearDownConnection();
    }


}