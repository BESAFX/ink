package com.besafx.app.ink.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class JsonConverter {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object o) {
        try {
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Object> Optional<T> toOptionalObject(String jsonInString, Class<T> type) {
        return Optional.ofNullable(toObject(jsonInString, type));
    }

    public static <T extends Object> T toObject(String jsonInString, Class<T> type) {
        try {
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return type.cast(mapper.readValue(jsonInString, type));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
