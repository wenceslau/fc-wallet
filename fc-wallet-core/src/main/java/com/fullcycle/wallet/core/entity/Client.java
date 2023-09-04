package com.fullcycle.wallet.core.entity;

import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private UUID id;
    private String name;
    private String email;
    private List<Account> accounts = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Client(String name, String email) {
        this(UUID.randomUUID(), name, email);
    }

    public Client(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        this.validade();
    }

    private void validade(){
        if (Strings.isEmpty(name)){
            throw new RuntimeException("name is required");
        }
        if (Strings.isEmpty(email)){
            throw new RuntimeException("email is required");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void update(String name, String email) {

        this.name = name;
        this.email = email;

        validade();
    }

    public void addAccount(Account account){
        if (account.getClient().getId().equals(id)){
            throw new RuntimeException("Account does not belong to the client");
        }

        accounts.add(account);
    }
}
