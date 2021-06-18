package eutro.hierarchicallang;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * The Hierarchical Lang Gradle plugin.
 * <p>
 * Adds the {@code expandJson} and {@code flattenJson extensions}.
 */
public class HierarchicalLangPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        ExtensionContainer extensions = target.getExtensions();
        extensions.create("expandJson", ExpandJsonExtension.class);
        extensions.create("flattenJson", FlattenJsonExtension.class);
    }
}
