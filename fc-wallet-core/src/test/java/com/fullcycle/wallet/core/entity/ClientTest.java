package com.fullcycle.wallet.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    public void testCreateNewClient(){
        Client client = new Client("Jon Doe", "j@j.com");

        Assertions.assertEquals(client.getName(), "Jon Doe");
        Assertions.assertEquals(client.getEmail(), "j@j.com");

    }

    @Test
    public void testCreateNewClientWhenArgumentIsInvalid(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Client("", "");
        });

        String expectedMessage = "name is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(RuntimeException.class, () -> {
            new Client("Jon", "");
        });

        expectedMessage = "email is required";
        actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testUpdateClient(){
        Client client = new Client("Jon Doe", "j@j.com");
        client.update("Jon Updated", "j@j.com");

        Assertions.assertEquals(client.getName(), "Jon Updated");
        Assertions.assertEquals(client.getEmail(), "j@j.com");

    }

    @Test
    public void testUpdateClientWhenArgumentIsInvalid(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            Client c = new Client("Jon Doe", "j@j.com");
            c.update("","");
        });

        String expectedMessage = "name is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(RuntimeException.class, () -> {
            Client c = new Client("Jon Doe", "j@j.com");
            c.update("Jon","");

        });

        expectedMessage = "email is required";
        actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}