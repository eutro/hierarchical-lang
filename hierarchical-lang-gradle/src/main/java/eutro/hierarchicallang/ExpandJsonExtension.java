package eutro.hierarchicallang;

import com.google.gson.JsonObject;
import eutro.jsonflattener.JsonExpander;

/**
 * A {@link JsonExtension} that expands dot-separated keys to nested paths.
 */
public class ExpandJsonExtension extends AbstractJsonExtension {
    @Override
    public JsonObject transform(JsonObject object) {
        return JsonExpander.expand(object);
    }
}
