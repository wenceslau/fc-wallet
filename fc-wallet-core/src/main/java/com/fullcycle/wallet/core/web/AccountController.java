package com.fullcycle.wallet.core.web;

import com.fullcycle.wallet.core.usecases.CreateAccountUseCase;
import com.fullcycle.wallet.core.usecases.CreateClientUseCase;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateAccountOutputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountInputDTO input){
        try {
            System.out.println(input.getClientId());
            CreateAccountOutputDTO output = createAccountUseCase.execute(input);
            return ResponseEntity.status(HttpStatus.OK).body(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
