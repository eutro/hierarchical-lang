package eutro.hierarchicallang;

import com.google.gson.JsonObject;

/**
 * Transforms a JsonObject to another.
 */
public interface JsonTransformer {
    /**
     * Transform the given JsonObject to another.
     *
     * @param object The object to transform.
     * @return The transformed object.
     */
    JsonObject transform(JsonObject object);
}
