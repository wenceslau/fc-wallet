package com.fullcycle.wallet.core.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    public void testCreateTransaction() {
        Client client1 = new Client("John Doe", "j@j");
        Account account1 = new Account(client1);
        Client client2 = new Client("John Doe 2", "j@j2");
        Account account2 = new Account(client2);
        account1.credit(1000);
        account2.credit(1000);

        new Transaction(account1, account2, 100);
        assertEquals(1100.0, account2.getBalance(), 0.0);
        assertEquals(900.0, account1.getBalance(), 0.0);
    }

    @Test
    public void testCreateTransactionWithInsufficientBalance() {
        Client client1 = new Client("John Doe", "j@j");
        Account account1 = new Account(client1);
        Client client2 = new Client("John Doe 2", "j@j2");
        Account account2 = new Account(client2);
        account1.credit(1000);
        account2.credit(1000);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Transaction(account1, account2, 2000);
        });
        assertNotNull(exception);
        assertEquals("insufficient funds", exception.getMessage());
        assertEquals(1000.0, account2.getBalance(), 0.0);
        assertEquals(1000.0, account1.getBalance(), 0.0);
    }
}
