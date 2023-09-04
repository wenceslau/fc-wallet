package com.fullcycle.wallet.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Account {

    private UUID id;
    private Client client;
    private UUID clientId;
    private double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account(Client client) {
        this(UUID.randomUUID(), client);
    }

    public Account(UUID id, Client client) {
        if (client == null){
            throw new RuntimeException("account is required");
        }
        this.client = client;
        this.clientId = client.getId();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        this.id = id;
    }

    public Account(UUID id, Client client, double balance) {
        if (client == null){
            throw new RuntimeException("account is required");
        }
        this.client = client;
        this.clientId = client.getId();
        this.balance = balance;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        this.id = id;
    }

    public void credit(double amount){
        this.balance += amount;
        updatedAt = LocalDateTime.now();
    }

    public void debit(double amount){
        this.balance -= amount;
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Account setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

}
