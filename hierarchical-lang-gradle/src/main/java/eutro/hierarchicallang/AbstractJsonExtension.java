package eutro.hierarchicallang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.gradle.api.Action;
import org.gradle.api.file.ContentFilterable;

import java.util.HashMap;

abstract class AbstractJsonExtension implements JsonExtension, JsonTransformer {
    @Override
    public ContentFilterable invoke(ContentFilterable filterable) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("transformer", this);
        return filterable.filter(properties, JsonFilterReader.class);
    }

    @Override
    public ContentFilterable invoke(ContentFilterable filterable, Action<GsonBuilder> builderAction) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        builderAction.execute(gsonBuilder);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("transformer", this);
        Gson gson = gsonBuilder.create();
        properties.put("inGson", gson);
        properties.put("outGson", gson);
        return filterable.filter(properties, JsonFilterReader.class);
    }
}
