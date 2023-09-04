package com.fullcycle.utils.events;

import java.time.LocalDateTime;

public interface Event {

    String getName();

    LocalDateTime getDateTime();

    Object getPayload();

    void setPayload(Object paylod);
}
