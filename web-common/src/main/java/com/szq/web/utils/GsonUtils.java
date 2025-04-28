package com.szq.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtils {

    private static final Gson gson = new GsonBuilder().create();
    //实现json输出美观打印
    // private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson gsonFormat = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final JsonParser jsonParser = new JsonParser();

    private static final ObjectMapper mapper = new ObjectMapper();

    private GsonUtils() {
    }

    public static JsonElement bean2JsonTree(Object bean) {
        return gson.toJsonTree(bean);
    }

    public static <T> T jsonStr2Bean(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    public static String bean2JsonStr(Object bean) {
        return gson.toJson(bean);
    }

    public static String formatJsonStr(String jsonStr) {
        return gsonFormat.toJson(jsonParser.parse(jsonStr));
    }

    public static String beanToStr(Object bean) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            Log.sdk.error("beanToStr error", e);
            return "";
        }
    }


}