package com.fullcycle.wallet.report.consumer;

import com.fullcycle.wallet.report.repository.BalanceModel;
import com.fullcycle.wallet.report.repository.BalanceRepository;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class WalletConsumer {

    private final BalanceRepository balanceRepository;

    public WalletConsumer(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @KafkaListener(topics = "balances", groupId = "balance")
    public void listenBalance(String message) {

        System.out.println("Message received...." + message);
        JSONObject object = new JSONObject(message);
        object = object.getJSONObject("payload");
        String accountIdFrom = object.getString("accountIdFrom");
        Double balanceAccountFrom = object.getDouble("balanceAccountFrom");
        String accountIdTo = object.getString("accountIdTo");
        Double balanceAccountTo = object.getDouble("balanceAccountTo");

        updateOrCreateBalance(accountIdFrom, balanceAccountFrom);
        updateOrCreateBalance(accountIdTo, balanceAccountTo);

        System.out.println("Received Message in topic balance: " + message);

    }

    private void updateOrCreateBalance(String accountIdFrom, Double balanceAccountFrom) {
        BalanceModel balanceModel;
        Optional<BalanceModel> optionalFrom = balanceRepository.findByIdAccount(accountIdFrom);
        if (optionalFrom.isPresent()){
            balanceModel = optionalFrom.get();
            balanceModel.setBalance(balanceAccountFrom)
                    .setUpdatedAt(LocalDateTime.now());
        }else {
            balanceModel = new BalanceModel(accountIdFrom, balanceAccountFrom);
        }
        balanceRepository.save(balanceModel);
    }

}
