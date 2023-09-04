package com.fullcycle.wallet.core.web;

import com.fullcycle.wallet.core.usecases.CreateClientUseCase;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientInputDTO;
import com.fullcycle.wallet.core.usecases.dtos.CreateClientOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;

    public ClientController(CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody CreateClientInputDTO input){
        try {
            System.out.println(input.getName());
            System.out.println(input.getEmail());
            CreateClientOutputDTO output = createClientUseCase.execute(input);
            return ResponseEntity.status(HttpStatus.OK).body(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
