package net.kallen.solaris.util.file;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageLoader {

    public static ImageData loadTexture(String path, boolean flipVertically) {
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

            IntBuffer width = MemoryUtil.memAllocInt(1);
            IntBuffer height = MemoryUtil.memAllocInt(1);
            IntBuffer channels = MemoryUtil.memAllocInt(1);
            try {
                STBImage.stbi_set_flip_vertically_on_load(flipVertically);
                ByteBuffer image = STBImage.stbi_load_from_memory(rawBuffer, width, height, channels, 4);

                if (image == null) {
                    System.err.println("ImageLoader: STBImage failed on " + path
                            + " — " + STBImage.stbi_failure_reason());
                    return null;
                }

                return new ImageData(image, width.get(0), height.get(0)); // caller must call stbi_image_free()
            } finally {
                MemoryUtil.memFree(channels);
            }
        } finally {
            MemoryUtil.memFree(rawBuffer);
        }
    }


    public static int loadCubeMap(String[] textureLocations) {
        int texID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < textureLocations.length; i++) {
            ImageData image = ImageLoader.loadTexture(textureLocations[i], false);
            GL11.glTexImage2D(
                    GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA,
                    image.width(), image.height(),
                    0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                    image.buffer()
            );
            STBImage.stbi_image_free(image.buffer());
        }
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_WRAP_R, GL12.GL_CLAMP_TO_EDGE);


        return texID;
    }
}
