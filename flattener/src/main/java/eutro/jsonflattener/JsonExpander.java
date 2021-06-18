package eutro.jsonflattener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * A class that provides methods for expanding Gson Json Objects.
 */
public class JsonExpander {
    /**
     * Expand a Json Object, the opposite of flattening it.
     *
     * @param object The object to expand.
     * @return A new, expanded object.
     * @see JsonFlattener#flatten(JsonObject)
     */
    public static JsonObject expand(JsonObject object) {
        JsonObject expanded = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            JsonObject mapObj = expanded;
            String key = entry.getKey();
            if (key.indexOf('.') != -1) {
                String[] path = entry.getKey().split("\\.");
                for (int i = 0; i < path.length - 1; i++) {
                    mapObj = getObj(mapObj, path[i]);
                }
                key = path[path.length - 1];
            }
            JsonElement value = entry.getValue();
            JsonElement oldVal = mapObj.get(key);
            if (oldVal != null) {
                JsonArray array = new JsonArray();
                array.add(oldVal);
                array.add(value);
                value = array;
            }
            mapObj.add(key, value);
        }
        return expanded;
    }

    private static JsonObject getObj(JsonObject object, String key) {
        JsonObject mapObj;
        JsonElement mappedVal = object.get(key);
        if (mappedVal == null) {
            JsonElement value = mapObj = new JsonObject();
            object.add(key, value);
        } else if (mappedVal.isJsonObject()) {
            mapObj = mappedVal.getAsJsonObject();
        } else if (mappedVal.isJsonArray()) {
            JsonArray mappedArr = mappedVal.getAsJsonArray();
            JsonElement last;
            if (mappedArr.size() > 0 &&
                    (last = mappedArr.get(mappedArr.size() - 1))
                            .isJsonObject()) {
                mapObj = last.getAsJsonObject();
            } else {
                mappedArr.add(mapObj = new JsonObject());
            }
        } else {
            JsonArray newVal = new JsonArray();
            object.add(key, newVal);
            newVal.add(mappedVal);
            newVal.add(mapObj = new JsonObject());
        }
        return mapObj;
    }
}
