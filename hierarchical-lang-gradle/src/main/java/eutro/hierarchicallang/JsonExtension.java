package eutro.hierarchicallang;

import com.google.gson.GsonBuilder;
import org.gradle.api.Action;
import org.gradle.api.file.ContentFilterable;

/**
 * An extension provided by the Hierarchical Lang plugin.
 */
public interface JsonExtension {
    /**
     * Filter some Json resource.
     *
     * @param filterable The resource to filter.
     * @return The parameter.
     */
    ContentFilterable invoke(ContentFilterable filterable);

    /**
     * Filter some Json resource, configuring the Gson that will be used.
     *
     * @param filterable The resource to filter.
     * @param builderAction The action to configure the builder with.
     * @return The parameter.
     */
    ContentFilterable invoke(ContentFilterable filterable, Action<GsonBuilder> builderAction);
}
