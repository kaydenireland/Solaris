package main.java.net.kallen.solaris.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {
    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        InputStream stream = FileLoader.class.getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Couldn't find the file at " + path);
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }

    public static String getFileSafeName(String desiredFileName)
    {
        String safe = desiredFileName.replaceAll("[\\\\/:*?\"<>|\\x00-\\x1F;]", "_");
        return safe.substring(0, Math.min(255, safe.length()));
    }
}