package main.java.net.kallen.solaris.util.file;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageLoader {

    /**
     * Loads an image from the classpath and returns the decoded pixel data.
     * The caller is responsible for freeing the returned ByteBuffer via STBImage.stbi_image_free().
     *
     * @param path     classpath path to the image (e.g. "/textures/grass.png")
     * @param width    single-element buffer that receives the image width
     * @param height   single-element buffer that receives the image height
     * @return decoded RGBA pixel data, or null on failure
     */
    public static ByteBuffer loadTexture(String path, IntBuffer width, IntBuffer height) {
        // Read raw bytes from classpath
        byte[] imageData;
        try (InputStream is = ImageLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("ImageLoader: Resource not found: " + path);
                return null;
            }
            imageData = is.readAllBytes();
        } catch (Exception e) {
            System.err.println("ImageLoader: Failed to read resource: " + path);
            e.printStackTrace();
            return null;
        }

        // Copy into native memory for STBImage
        ByteBuffer rawBuffer = MemoryUtil.memAlloc(imageData.length);
        try {
            rawBuffer.put(imageData).flip();

            IntBuffer channels = MemoryUtil.memAllocInt(1);
            try {
                STBImage.stbi_set_flip_vertically_on_load(true);
                ByteBuffer image = STBImage.stbi_load_from_memory(rawBuffer, width, height, channels, 4);

                if (image == null) {
                    System.err.println("ImageLoader: STBImage failed on " + path
                            + " — " + STBImage.stbi_failure_reason());
                    return null;
                }

                return image; // caller must call stbi_image_free()
            } finally {
                MemoryUtil.memFree(channels);
            }
        } finally {
            MemoryUtil.memFree(rawBuffer);
        }
    }

    /**
     * Loads a square texture and warns if dimensions don't match the expected size.
     * Useful for sprite sheets and texture atlases.
     *
     * @param path         classpath path to the image
     * @param expectedSize expected width and height in pixels
     * @param width        single-element buffer that receives the actual width
     * @param height       single-element buffer that receives the actual height
     * @return decoded RGBA pixel data, or null on failure
     */
    public static ByteBuffer loadTexture(String path, int expectedSize,
                                         IntBuffer width, IntBuffer height) {
        ByteBuffer image = loadTexture(path, width, height);
        if (image != null) {
            int w = width.get(0);
            int h = height.get(0);
            if (w != expectedSize || h != expectedSize) {
                System.err.println("ImageLoader: Warning — " + path
                        + " is " + w + "x" + h
                        + ", expected " + expectedSize + "x" + expectedSize);
            }
        }
        return image;
    }
}