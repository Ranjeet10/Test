package com.envent.bottlesup.utils;

/**
 * Created by ronem on 4/20/18.
 */


import com.envent.bottlesup.mvp.model.mycart.RequestItem;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RequestItemSerializeAdapter implements JsonSerializer<RequestItem>, JsonDeserializer<RequestItem> {
    @Override
    public JsonElement serialize(RequestItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public RequestItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName("com.bidhee.bottlesup.mvp.model.mycart." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}