package net.kallen.solaris.util.file;

import java.util.Objects;

public class ResourceLocation {

    private final String namespace;
    private final String path;

    public static final String DEFAULT_NAMESPACE = "solaris";

    public static final String ICONS = "icons";
    public static final String SHADERS = "shaders";
    public static final String TEXTURES = "textures";
    public static final String MODELS = "models";

    public static final String DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "default").toImagePath();

    private ResourceLocation(String namespace, String path) {
        this.namespace = namespace.toLowerCase();
        this.path = path.toLowerCase();
        validate();
    }

    private void validate() {
        if (namespace.isEmpty()) {
            throw new IllegalArgumentException("Namespace cannot be empty");
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        if (!isValidString(namespace)) {
            throw new IllegalArgumentException("Invalid namespace: " + namespace);
        }
        if (!isValidString(path)) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
    }

    private boolean isValidString(String str) {
        return str.matches("[a-z0-9_\\-./]+");
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    // namespace:path
    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    public String toFilePath() {
        return "/" + namespace + "/" + path;
    }

    public String toFilePath(String extension) {
        return "/" + namespace + "/" + path + extension;
    }

    public String toSystemFilePath(String extension) {
        return "src/main/resources/" + namespace + "/" + path + extension;
    }

    public String toImagePath() {
        return "/" + namespace + "/" + path + ".png";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResourceLocation)) return false;
        ResourceLocation other = (ResourceLocation) obj;
        return namespace.equals(other.namespace) && path.equals(other.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    // Factory methods
    public static ResourceLocation fromNamespace(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation fromNamespaceAndDirectory(String namespace, String directory, String item) {
        return new ResourceLocation(namespace, directory + "/" + item);
    }

    // Parse from "namespace:path" or just "path"
    public static ResourceLocation parse(String location) {
        String[] parts = location.split(":", 2);
        if (parts.length == 2) {
            return new ResourceLocation(parts[0], parts[1]);
        } else {
            return fromNamespace(DEFAULT_NAMESPACE, parts[0]);
        }
    }

    // Create a new ResourceLocation with a different path but same namespace
    public ResourceLocation withPath(String newPath) {
        return new ResourceLocation(this.namespace, newPath);
    }

    // Create a new ResourceLocation with a different namespace but same path
    public ResourceLocation withNamespace(String newNamespace) {
        return new ResourceLocation(newNamespace, this.path);
    }
}