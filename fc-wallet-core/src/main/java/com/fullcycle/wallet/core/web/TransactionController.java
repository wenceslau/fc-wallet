package com.fullcycle.wallet.core.web;

import com.fullcycle.wallet.core.usecases.CreateAccountUseCase;
import com.fullcycle.wallet.core.usecases.CreateTransactionUseCase;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountOutputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateTransactionOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionInputDTO input){
        try {
            System.out.println("Ammount " + input.getAmount());
            CreateTransactionOutputDTO output = createTransactionUseCase.execute(input);
            return ResponseEntity.status(HttpStatus.OK).body(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
