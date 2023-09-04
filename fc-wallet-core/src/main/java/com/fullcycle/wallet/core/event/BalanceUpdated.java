package com.fullcycle.wallet.core.event;


import com.fullcycle.utils.events.Event;

import java.time.LocalDateTime;

public class BalanceUpdated implements Event {

    private Object paylod;

    @Override
    public String getName() {
        return "BalanceUpdated";
    }

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public Object getPayload() {
        return paylod;
    }

    @Override
    public void setPayload(Object paylod) {
        this.paylod = paylod;
    }
}
