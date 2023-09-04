package com.fullcycle.wallet.core.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void testCreateAccount() {
        Client client = new Client("John Doe", "j@j");
        Account account = new Account(client);
        assertNotNull(account);
        assertEquals(client.getId(), account.getClient().getId());
    }

    @Test
    public void testCreateAccountWithNilClient() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Account(null);
        });

        assertNotNull(exception);
    }

    @Test
    public void testCreditAccount() {
        Client client = new Client("John Doe", "j@j");
        Account account = new Account(client);
        account.credit(100);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testDebitAccount() {
        Client client = new Client("John Doe", "j@j");
        Account account = new Account(client);
        account.credit(100);
        account.debit(50);
        assertEquals(50.0, account.getBalance());
    }

}