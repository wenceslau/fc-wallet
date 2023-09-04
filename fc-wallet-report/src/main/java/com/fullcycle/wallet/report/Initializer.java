package com.fullcycle.wallet.report;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class Initializer {

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) throws SQLException {
        System.out.println("applicationReadyEvent() - INIT");

        Connection conn = getConnection();

        dropTable(conn);

        createTable(conn);

        System.out.println("applicationReadyEvent() - END");
    }

    private static void dropTable(Connection conn) throws SQLException {

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.execute( "Drop table balances");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Drop table balances was not possible..: "+ e.getMessage());
        }
    }
    
    private static void createTable(Connection conn) throws SQLException {

        Statement stmt;

        stmt = conn.createStatement();
        stmt.execute( "Create table balances (id varchar(255) primary key , id_account varchar(255), balance float, created_at date, updated_at date)");
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
        System.out.println("applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println("applicationStartingEvent()");
    }

}
