package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.entity.Transaction;
import com.fullcycle.wallet.core.gateway.TransactionGateway;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.UUID;

public class TransactionDB implements TransactionGateway {

    Connection db;

    public TransactionDB(Connection db) {
        this.db = db;
    }

    public Client get(UUID id) throws SQLException {

        PreparedStatement ps = db.prepareStatement("Select id, name, email From clients where id = ?");
        ps.setString(1, id.toString());
        ResultSet rs = ps.executeQuery();
        if ((rs.next())) {
            UUID uuid = UUID.fromString(rs.getString("id"));
            return new Client(uuid, rs.getString("name"), rs.getString("email"));
        }
        rs.close();

        return null;
    }

    public void create(Transaction transaction) throws SQLException {
        System.out.println("Saving " + transaction.getId());
        PreparedStatement ps = db.prepareStatement("INSERT INTO transactions (id, account_id_from, account_id_to, amount, created_at) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, transaction.getId().toString());
        ps.setString(2, transaction.getAccountFrom().getId().toString());
        ps.setString(3, transaction.getAccountTo().getId().toString());
        ps.setDouble(4, transaction.getAmount());
        ps.setTimestamp(5, Timestamp.from((transaction.getCreatedAt().toInstant(ZoneOffset.UTC))));
        ps.execute();
        ps.close();
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
