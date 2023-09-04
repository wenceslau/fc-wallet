package com.fullcycle.wallet.core.gateway;

import java.sql.SQLException;

public interface BaseDb {
    void commit() throws SQLException;
    void rollback() throws SQLException;
}
