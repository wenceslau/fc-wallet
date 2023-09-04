package com.fullcycle.wallet.core;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class Initializer {

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) throws SQLException {
        System.out.println(LocalDateTime.now() + " applicationReadyEvent() - INIT");

        Connection conn = getConnection();

        dropTables(conn);

        createTables(conn);

        UUID clientId = createClient(conn,"Jonh Doe", "jonh@fc.com");
        UUID accountId = UUID.fromString("af0a5741-2d2d-42f6-b35c-73fd156063c3");
        createAccount(conn, accountId, clientId, 100.00);

        UUID clientId2 = createClient(conn,"Jane Doe", "jane@fc.com");
        UUID accountId2 = UUID.fromString("18cd7316-d3f1-4842-b4fc-2f5d16333254");
        createAccount(conn,accountId2, clientId2, 400.00);

        //createTransaction(conn, accountId, accountId2, 20);

        System.out.println(LocalDateTime.now() + " applicationReadyEvent() - END");
    }

    private static void createTransaction(Connection conn, UUID accountId, UUID accountId2, double amount) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (id, account_id_from, account_id_to, amount, created_at) VALUES (?, ?, ?, ?, ?)");
        UUID id = UUID.randomUUID();
        ps.setString(1, id.toString());
        ps.setString(2, accountId.toString());
        ps.setString(3, accountId2.toString());
        ps.setDouble(4, amount);
        ps.setTimestamp(5, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        ps.execute();
        ps.close();
    }

    private UUID createAccount(Connection conn, UUID id, UUID clientId, double balance) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts (id, client_id, balance, created_at) VALUES (?, ?, ?, ?)");

        ps.setString(1, id.toString());
        ps.setString(2, clientId.toString());
        ps.setDouble(3, balance);
        ps.setTimestamp(4, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        ps.execute();
        ps.close();
        return id;
    }

    private UUID createClient(Connection conn, String name, String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("Insert into clients (id, name, email, created_at) values (?,?,?,?)");
        UUID id = UUID.randomUUID();
        ps.setString(1, id.toString());
        ps.setString(2, name );
        ps.setString(3, email);
        ps.setTimestamp(4, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        ps.execute();
        ps.close();
        return id;
    }

    private static void dropTables(Connection conn) throws SQLException {

        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.execute( "Drop table clients");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Drop table clients was not possible..: "+ e.getMessage());
        }

        try {
            stmt = conn.createStatement();
            stmt.execute( "Drop table accounts");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Drop table accounts was not possible..: "+ e.getMessage());
        }

        try {
            stmt = conn.createStatement();
            stmt.execute( "Drop table transactions");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Drop table accounts was not possible..: "+ e.getMessage());
        }
    }

    private static void createTables(Connection conn) throws SQLException {

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

    private static Connection getConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://db-mysql-wallet:3306/wallet?serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(connectionUrl, "root", "r00t");
        conn.setAutoCommit(true);
        System.out.println("Opened database successfully");
        return conn;
    }

    @EventListener
    private void applicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println(LocalDateTime.now() + " applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println(LocalDateTime.now() + " applicationStartingEvent()");
    }

}
