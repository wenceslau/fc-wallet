package com.fullcycle.wallet.core.database;

import com.fullcycle.wallet.core.entity.Client;
import com.fullcycle.wallet.core.gateway.ClientGateway;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.UUID;

public class ClientDB implements ClientGateway {

    Connection db;

    public ClientDB(Connection db) {
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

    public void save(Client client) throws SQLException {
        System.out.println("Saving...");
        PreparedStatement ps = db.prepareStatement("Insert into clients (id, name, email, created_at) values (?,?,?,?)");
        ps.setString(1, client.getId().toString());
        ps.setString(2, client.getName());
        ps.setString(3, client.getEmail());
        ps.setTimestamp(4, Timestamp.from((client.getCreatedAt().toInstant(ZoneOffset.UTC))));
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
