package com.fullcycle.utils.events;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventDispatcherImplTest {

    Event event1;
    Event event2;
    Event event3;
    EventHandler handler1;
    EventHandler handler2;
    EventHandler handler3;
    EventDispatcher eventDispatcher;

    @BeforeEach
    void setUp() {
        event1 = new Event() {
            @Override
            public String getName() {
                return "Event Test 1";
            }

            @Override
            public LocalDateTime getDateTime() {
                return LocalDateTime.now();
            }

            @Override
            public Object getPayload() {
                return "{test: 'test1'}";
            }

            @Override
            public void setPayload(Object paylod) {

            }
        };
        event2 = new Event() {
            @Override
            public String getName() {
                return "Event Test 2";
            }

            @Override
            public LocalDateTime getDateTime() {
                return LocalDateTime.now();
            }

            @Override
            public Object getPayload() {
                return "{test: 'test2'}";
            }

            @Override
            public void setPayload(Object paylod) {

            }
        };
        event3 = new Event() {
            @Override
            public String getName() {
                return "Event Test 3";
            }

            @Override
            public LocalDateTime getDateTime() {
                return LocalDateTime.now();
            }

            @Override
            public Object getPayload() {
                return "{test: 'test3'}";
            }

            @Override
            public void setPayload(Object paylod) {

            }
        };

        handler1 = new EventHandler() {

            @Override
            public String id() {
                return "1";
            }

            @Override
            public void handle(Event event) {
                System.out.println("handling event " + event.getName() + LocalTime.now());
            }

            @Override
            public int hashCode() {
                return id().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Handler)
                    return ((EventHandler)obj).id().equals(id());
                return false;
            }
        } ;
        handler2 = new EventHandler() {

            @Override
            public String id() {
                return "3";
            }

            @Override
            public void handle(Event event) {
                System.out.println("handling event " + event.getName() + LocalTime.now());
            }

            @Override
            public int hashCode() {
                return id().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Handler)
                    return ((EventHandler)obj).id().equals(id());
                return false;
            }
        } ;
        handler3 =new EventHandler() {

            @Override
            public String id() {
                return "3";
            }

            @Override
            public void handle(Event event) {
                System.out.println("handling event " + event.getName() + LocalTime.now());
            }

            @Override
            public int hashCode() {
                return id().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Handler)
                    return ((EventHandler)obj).id().equals(id());
                return false;
            }
        } ;

        eventDispatcher = new EventDispatcherImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        eventDispatcher.clear();
    }

    @Test
    void register() throws Exception {

        eventDispatcher.register(event1.getName(), handler1);
        eventDispatcher.register(event1.getName(), handler2);

        assertEquals(2, eventDispatcher.sizeHandlers(event1.getName()));

        assertEquals(true, eventDispatcher.has(event1.getName(), handler1));
        assertEquals(true, eventDispatcher.has(event1.getName(), handler2));

    }

    @Test
    void register_duplicate() throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            eventDispatcher.register(event1.getName(), handler1);
            eventDispatcher.register(event1.getName(), handler1);
        });

        assertEquals(exception.getMessage(), "event already register");
    }

    @Test
    void dispach() throws Exception {

        EventHandler handlerMock = mock(EventHandler.class);
        EventHandler handlerMock2 = mock(EventHandler.class);

        eventDispatcher.register(event1.getName(), handler2);
        eventDispatcher.register(event1.getName(), handler1);
        eventDispatcher.dispatch(event1);

        Thread.sleep(1000);

       // verify(handlerMock, times(1)).handle(event1);
       // verify(handlerMock2, times(1)).handle(event1);

    }

    @Test
    void remove() throws Exception {

        eventDispatcher.register(event1.getName(), handler1);
        eventDispatcher.register(event1.getName(), handler2);

        assertEquals(2, eventDispatcher.sizeHandlers(event1.getName()));

        eventDispatcher.remove(event1.getName(), handler1);

        assertEquals(1, eventDispatcher.sizeHandlers(event1.getName()));

        assertEquals(1, eventDispatcher.sizeEvents());


    }

    @Test
    void has() throws Exception {
        eventDispatcher.register(event1.getName(), handler1);

        assertTrue(eventDispatcher.has(event1.getName(), handler1));
    }

    @Test
    void clear() throws Exception {
        eventDispatcher.register(event1.getName(), handler1);
        eventDispatcher.register(event2.getName(), handler2);

        assertEquals(1, eventDispatcher.sizeHandlers(event1.getName()));
        assertEquals(1, eventDispatcher.sizeHandlers(event2.getName()));

        eventDispatcher.clear();

        assertEquals(0, eventDispatcher.sizeEvents());
    }
}