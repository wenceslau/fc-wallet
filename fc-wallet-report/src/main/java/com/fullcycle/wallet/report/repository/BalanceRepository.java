package com.fullcycle.wallet.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BalanceRepository extends JpaRepository<BalanceModel, Long> {

    Optional<BalanceModel> findByIdAccount(String idAccount);

}
