package com.fullcycle.utils.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Util {

    public static String toJson(Object object, boolean includeNull) {

        final var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        if (includeNull == false){
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        String jsonInString = "Error convert object to json. ";
        try {
            jsonInString = mapper.writeValueAsString(object);
        } catch (Exception e) {
            jsonInString += e.getMessage();
        }
        return jsonInString;
    }
}
