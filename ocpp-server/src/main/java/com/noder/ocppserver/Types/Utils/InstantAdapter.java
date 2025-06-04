package com.noder.ocppserver.Types.Utils;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helper class to serlialize Instant (datetime) objects to JSON
 * It just converts the Instant object to a string and returns it as a JsonPrimitive 
 */
public class InstantAdapter implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
