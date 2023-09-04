package com.fullcycle.utils.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcherImpl implements EventDispatcher {

    private Map<String, List<EventHandler>> events = new HashMap<>();

    @Override
    public void register(String eventName, EventHandler handler) throws Exception {
        if (has(eventName, handler)) {
            throw new Exception("event already register");
        }
        if (events.containsKey(eventName)) {
            events.get(eventName).add(handler);
        }else{
            List<EventHandler> eventHandlers = new ArrayList<>();
            eventHandlers.add(handler);
            events.put(eventName, eventHandlers);
        }
    }

    @Override
    public void dispatch(Event event) throws Exception {
        if (events.containsKey(event.getName())) {
            for (EventHandler eventHandler : events.get(event.getName())) {
                Thread t = new Thread(() -> eventHandler.handle(event));
                t.start();
            }
        }
    }

    @Override
    public void remove(String eventName, EventHandler handler) throws Exception {
        int pos = exist(eventName, handler);
        if (pos > -1) {
            events.get(eventName).remove(pos);
        }
    }

    @Override
    public boolean has(String eventName, EventHandler handler) throws Exception {
        return exist(eventName, handler) != -1;
    }

    @Override
    public void clear() throws Exception {
        for (String key : events.keySet()) {
            events.get(key).clear();
        }
        events.clear();
    }

    @Override
    public int sizeHandlers(String eventName) {
        if (events.containsKey(eventName)){
            return events.get(eventName).size();
        }
        return 0;
    }

    @Override
    public int sizeEvents() {
        return events.size();
    }

    private int exist(String eventName, EventHandler handler){
        if (events.containsKey(eventName)) {
            List<EventHandler> lst = events.get(eventName);
            for (int i = 0; i < lst.size(); i++) {
                EventHandler eh = lst.get(i);
                if (eh.id().equals(handler.id())) {
                    return i;
                }
            }
        }
        return -1;
    }
}
