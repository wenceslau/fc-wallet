package com.fullcycle.wallet.core.uow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public interface UnitOfWork {
    void register(String name, Function<Connection, Object> fc);

    Object getRepository(String name) throws Exception;

    void doTransaction(Function<UnitOfWork, Void> fn) throws Exception;

    void commitOrRollback() throws Exception;

    void rollback() throws Exception;

    void unregister(String name);
}
