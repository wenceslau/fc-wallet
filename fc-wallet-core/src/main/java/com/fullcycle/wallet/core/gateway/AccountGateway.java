package com.fullcycle.wallet.core.gateway;

import com.fullcycle.wallet.core.entity.Account;

import java.sql.SQLException;
import java.util.UUID;

public interface AccountGateway extends  BaseDb {

    Account findById(UUID id) throws Exception;
    void save(Account account) throws Exception;
    void updateBalance(Account a) throws SQLException;

}
