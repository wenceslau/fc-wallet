package com.fullcycle.wallet.core.gateway;

import com.fullcycle.wallet.core.entity.Client;

import java.sql.SQLException;
import java.util.UUID;

public interface ClientGateway extends BaseDb {

    Client get(UUID id) throws Exception;
    void save(Client client) throws Exception;
}

