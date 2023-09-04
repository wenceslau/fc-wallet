package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Account;
import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.AccountGateway;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.UUID;

public class AccountDB implements AccountGateway {

    Connection db;

    public AccountDB(Connection db) {
        this.db = db;
    }

    public Account findById(UUID id) throws SQLException {

        Account account;
        Client client;

        PreparedStatement ps = db.prepareStatement("Select a.id, a.client_id, a.balance, a.created_at, c.id, c.name, c.email, c.created_at " +
                "FROM accounts a " +
                "INNER JOIN clients c ON a.client_id = c.id " +
                "WHERE a.id = ?");
        ps.setString(1, id.toString());
        ResultSet rs = ps.executeQuery();
        if ((rs.next())) {
            UUID idClient = UUID.fromString(rs.getString("client_id"));
            client =  new Client(idClient, rs.getString("name"), rs.getString("email"));
            UUID idAccount = UUID.fromString(rs.getString("id"));
            account = new Account(idAccount, client, rs.getDouble("balance"));
            return account;
        }

        return null;
    }

    public void save(Account account) throws SQLException {
        System.out.println("Saving account id " + account.getId());
        PreparedStatement ps = db.prepareStatement("INSERT INTO accounts (id, client_id, balance, created_at) VALUES (?, ?, ?, ?)");
        ps.setString(1, account.getId().toString());
        ps.setString(2, account.getClientId().toString());
        ps.setDouble(3, account.getBalance());
        ps.setTimestamp(4, Timestamp.from((account.getCreatedAt().toInstant(ZoneOffset.UTC))));
        ps.execute();
    }

    @Override
    public void updateBalance(Account account) throws SQLException {
        System.out.println("Update account id " + account.getId());
        PreparedStatement ps = db.prepareStatement("UPDATE accounts set balance = ? WHERE id = ? ");
        ps.setDouble(1, account.getBalance());
        ps.setString(2, account.getId().toString());
        ps.execute();
    }

    @Override
    public void commit() throws SQLException {
        db.commit();
    }

    @Override
    public void rollback() throws SQLException {
        db.rollback();
    }
}
