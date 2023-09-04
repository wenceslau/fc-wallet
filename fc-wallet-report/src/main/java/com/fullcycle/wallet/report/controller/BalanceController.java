package com.fullcycle.wallet.report.controller;

import com.fullcycle.wallet.report.repository.BalanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceRepository balanceRepository;

    public BalanceController(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<?> balance(@PathVariable String account_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(balanceRepository.findByIdAccount(account_id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
