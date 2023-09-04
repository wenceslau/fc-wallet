package com.fullcycle.wallet.core.uow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UnitOfWorkImpl implements UnitOfWork {

    private Connection db;
    private Connection tx;
    private Map<String, Function<Connection, Object>> repositories;

    public UnitOfWorkImpl(Connection db) {
        this.db = db;
        this.repositories = new HashMap<>();
    }

    public void register(String name, Function<Connection, Object> fc) {
        repositories.put(name, fc);
    }

    public Object getRepository(String name) {
        if (tx == null) {
            tx = db;
        }
        Function<Connection, Object> fc = repositories.get(name);
        return fc.apply(tx);
    }

    public void doTransaction(Function<UnitOfWork, Void> fn) throws Exception {
        if (tx != null) {
            throw new Exception("transaction already started");
        }
        try {
            fn.apply(this);
            commitOrRollback();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    public void commitOrRollback() throws SQLException {
        if (tx == null) {
            throw new SQLException("No active transaction");
        }
        try {
            tx.commit();
        } catch (SQLException e) {
            tx.rollback();
            throw e;
        } finally {
            tx = null;
        }
    }

    public void rollback() throws SQLException {
        if (tx == null) {
            throw new SQLException("No active transaction");
        }
        try {
            tx.rollback();
        } finally {
            tx = null;
        }
    }

    public void unregister(String name) {
        repositories.remove(name);
    }
}

