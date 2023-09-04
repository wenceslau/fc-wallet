package com.fullcycle.utils.events;

public interface EventHandler {

    String id();

    void handle(Event event);

}
