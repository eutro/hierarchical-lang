package eutro.jsonflattener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFlattenerTest {
    public static final Gson GSON = new GsonBuilder().create();
    private final Map<String, JsonObject> cache = new HashMap<>();

    private JsonObject getGsonFromResource(String resource) {
        return cache.computeIfAbsent(resource, $ -> {
            InputStream stream = JsonFlattenerTest.class.getResourceAsStream(resource);
            assertNotNull(stream);
            try (Reader reader = new BufferedReader(new InputStreamReader(stream))) {
                return GSON.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void testFlattening() {
        JsonObject flat = getGsonFromResource("/flat.json");
        JsonObject hierarchical = getGsonFromResource("/hierarchical.json");
        assertEquals(flat, JsonFlattener.flatten(hierarchical));
    }

    @Test
    public void testAlreadyFlat() {
        JsonObject flat = getGsonFromResource("/flat.json");
        assertEquals(flat, JsonFlattener.flatten(flat));
    }

    @Test
    public void testEmpty() {
        assertEquals(new JsonObject(), JsonFlattener.flatten(new JsonObject()));
    }
}