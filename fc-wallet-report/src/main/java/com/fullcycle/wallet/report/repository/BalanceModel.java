package com.fullcycle.wallet.report.repository;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "balances")
public class BalanceModel {

    @Id()
    @Column(name = "id")
    private String id;
    @Column(name = "id_account")
    private String idAccount;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public BalanceModel(String idAccount, Double balance) {
        this.id = UUID.randomUUID().toString();
        this.idAccount = idAccount;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public BalanceModel() {
    }

    public String getId() {
        return id;
    }

    public BalanceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public BalanceModel setIdAccount(String idAccount) {
        this.idAccount = idAccount;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public BalanceModel setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BalanceModel setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BalanceModel setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        BalanceModel that = (BalanceModel) object;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
