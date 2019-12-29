package com.timw.iprwc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JacksonService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(List list) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static String toJson(Optional optional) {
        String jsonString = null;

        if (!optional.isPresent()) return "";

        try {
            jsonString = mapper.writeValueAsString(optional.get());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static Object fromJson(String jsonString, Class model) {
        Object o = null;
        try {
            o = mapper.readValue(jsonString, model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return o;
    }


}