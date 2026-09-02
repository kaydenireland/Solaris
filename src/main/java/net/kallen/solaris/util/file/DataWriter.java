package net.kallen.solaris.util.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataWriter {
    private final Gson gson;
    private final Path outputFolder;

    public DataWriter(Path outputFolder) {
        // Configure Gson for human-readable output
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        this.outputFolder = outputFolder;
    }

    /**
     * Serializes an object to JSON and saves it to a specific sub-path.
     * @param subPath e.g., "moves/ember.json"
     * @param data The object to serialize
     */
    public void write(String subPath, Object data) {
        Path filePath = outputFolder.resolve(subPath);
        try {
            // Ensure the parent directories (like /moves/) exist
            Files.createDirectories(filePath.getParent());

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                gson.toJson(data, writer);
                System.out.println("Generated: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to write data to " + filePath);
            e.printStackTrace();
        }
    }
}