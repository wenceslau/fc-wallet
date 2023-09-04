package com.fullcycle.utils.events;

public interface EventDispatcher {

    void register(String eventName,EventHandler handler) throws Exception;

    void dispatch(Event event) throws  Exception;

    void remove(String eventName, EventHandler handler) throws  Exception;

    boolean has(String eventName, EventHandler eventHandler) throws Exception;

    void clear() throws  Exception;

    int sizeHandlers(String eventName);

    int sizeEvents();

}
