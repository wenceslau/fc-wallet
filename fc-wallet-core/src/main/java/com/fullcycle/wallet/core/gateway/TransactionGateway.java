package com.fullcycle.wallet.core.gateway;

import com.fullcycle.wallet.core.entity.Transaction;

import java.sql.SQLException;

public interface TransactionGateway extends BaseDb {

    void create(Transaction transaction) throws Exception;
}
