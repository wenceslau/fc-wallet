package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AccountDBTest {


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

        Account account = new Account(client);
        AccountDB accountDB = new AccountDB(conn);
        accountDB.save(account);

        tearDownConnection();
    }

    @Test
    void findById() throws Exception {

        setupConnection();

        Client client = new Client("Test", "j@j");
        ClientDB clientDB = new ClientDB(conn);
        clientDB.save(client);

        Account account = new Account(client);
        AccountDB db = new AccountDB(conn);
        db.save(account);

        Account accountDB = db.findById(account.getId());

        assertEquals(account.getId(), accountDB.getId());
        assertEquals(account.getClientId(), accountDB.getClientId());
        assertEquals(account.getBalance(), accountDB.getBalance());
        assertEquals(account.getClient().getId(), accountDB.getClient().getId());
        assertEquals(account.getClient().getName(), accountDB.getClient().getName());
        assertEquals(account.getClient().getEmail(), accountDB.getClient().getEmail());
    }

}