package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Client;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ClientDBTest {

    private Connection conn;

   void setupConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        conn.setAutoCommit(true);
        System.out.println("Opened database successfully");
        Statement stmt = conn.createStatement();
        stmt.execute( "Create table clients (id varchar(255), name varchar(255), email varchar(255), created_at date)");
        stmt.close();
    }

    void tearDownConnection() throws SQLException {
//        Statement stmt = conn.createStatement();
//        stmt.execute("DROP TABLE clients");
//        stmt.close();
        conn.close();
        conn = null;
    }

    @Test
    void save() throws Exception {
        setupConnection();
        assertDoesNotThrow(() -> {
            Client client = new Client("Test", "j@j");
            ClientDB clientDB = new ClientDB(conn);
            clientDB.save(client);
            tearDownConnection();
        });
    }

    @Test
    void get() throws Exception {
        setupConnection();
        Client client1 = new Client("John", "j@j.com");
        ClientDB clientDB = new ClientDB(conn);
        clientDB.save(client1);

        Client client2 = clientDB.get(client1.getId());
        tearDownConnection();

        assertEquals(client1.getId(), client2.getId());
        assertEquals(client1.getName(), client2.getName());
        assertEquals(client1.getEmail(), client2.getEmail());
    }
}