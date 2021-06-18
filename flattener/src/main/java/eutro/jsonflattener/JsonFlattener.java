package eutro.jsonflattener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * A class that provides methods for flattening Gson Json Objects.
 */
public class JsonFlattener {
    /**
     * Flattens a Json Object.
     * <p>
     * The following conversions are made:
     *
     * <ul>
     *
     * <li> Nested keys are replaced by the full path in the containing object, separated by '.'s:
     * <pre>{@code
     * {
     *     "a": {
     *         "b": "ab",
     *         "c": "ac"
     *     }
     * }
     * }</pre>
     * to
     * <pre>{@code
     * {
     *     "a.b": "ab",
     *     "a.c": "ac"
     * }
     * }</pre>
     * </li>
     * <li> Arrays are treated as though the key were repeated:
     * <pre>{@code
     * {
     *     "a": [
     *         "a",
     *         {
     *             "b": "ab"
     *         },
     *         {
     *             "c": "ac"
     *         }
     *     ]
     * }
     * }</pre>
     * to
     * <pre>{@code
     * {
     *     "a": "a",
     *     "a.b": "ab",
     *     "a.c": "ac"
     * }
     * }</pre>
     * </li>
     * </ul>
     *
     * @param object The object to flatten.
     * @return A new, flattened Json Object.
     */
    public static JsonObject flatten(JsonObject object) {
        JsonObject flat = new JsonObject();
        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            JsonElement value = entry.getValue();
            if (value.isJsonObject() || value.isJsonArray()) {
                addEntry(flat, buf, entry);
            } else {
                // avoid unnecessary string copying
                flat.add(entry.getKey(), value);
            }
        }
        return flat;
    }

    private static void addEntry(JsonObject flat, StringBuffer buf, Map.Entry<String, JsonElement> entry) {
        int len = buf.length();
        buf.append(entry.getKey());
        addValue(flat, buf, entry.getValue());
        buf.setLength(len);
    }

    private static void addValue(JsonObject flat, StringBuffer buf, JsonElement value) {
        if (value.isJsonObject()) {
            int oldLen = buf.length();
            buf.append('.');
            for (Map.Entry<String, JsonElement> entry : value.getAsJsonObject().entrySet()) {
                addEntry(flat, buf, entry);
            }
            buf.setLength(oldLen);
        } else if (value.isJsonArray()) {
            for (JsonElement el : value.getAsJsonArray()) {
                addValue(flat, buf, el);
            }
        } else {
            flat.add(buf.toString(), value);
        }
    }
}
