package eutro.hierarchicallang;

import com.google.gson.JsonObject;
import eutro.jsonflattener.JsonFlattener;

/**
 * A {@link JsonExtension} that flattens nested paths to dot-separated keys.
 */
public class FlattenJsonExtension extends AbstractJsonExtension {
    @Override
    public JsonObject transform(JsonObject object) {
        return JsonFlattener.flatten(object);
    }
}
