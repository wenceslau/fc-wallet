package com.fullcycle.wallet.core.handler;

import com.fullcycle.utils.utils.Util;
import com.fullcycle.utils.events.Event;
import com.fullcycle.utils.events.EventHandler;
import org.springframework.kafka.core.KafkaTemplate;

public class TransactionCreatedKafkaHandler implements EventHandler {

    private KafkaTemplate<String, String> kafkaTemplate;

    public TransactionCreatedKafkaHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String id() {
        return "TransactionCreatedKafkaHandler";
    }

    @Override
    public void handle(Event event) {
        String message =  Util.toJson(event, true);
        System.out.println("Chamando kafka.... message " + message);
        kafkaTemplate.send("transactions", message);
    }
}
