package net.kallen.solaris.graphics;

import net.kallen.solaris.util.file.ImageLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final String path;
    private int textureID;
    private int width, height;
    private boolean mipmaps;

    /**
     * Load a texture from the classpath.
     *
     * @param path    classpath path (e.g. "/textures/grass.png")
     * @param mipmaps true for 3D/voxel rendering, false for 2D pixel art
     */
    public Texture(String path, boolean mipmaps) {
        this.path = path;
        this.mipmaps = mipmaps;
    }

    /** Convenience constructor — no mipmaps, suitable for 2D. */
    public Texture(String path) {
        this(path, false);
    }

    /**
     * Constructor for a pre-existing OpenGL texture (e.g. framebuffer attachments).
     */
    public Texture(int textureID, int width, int height) {
        this.path = null;
        this.textureID = textureID;
        this.width = width;
        this.height = height;
    }

    public void create() {
        if (path == null) return; // pre-built texture, nothing to load

        IntBuffer widthBuf  = MemoryUtil.memAllocInt(1);
        IntBuffer heightBuf = MemoryUtil.memAllocInt(1);

        ByteBuffer image = ImageLoader.loadTexture(path, widthBuf, heightBuf);
        if (image == null) {
            MemoryUtil.memFree(widthBuf);
            MemoryUtil.memFree(heightBuf);
            throw new RuntimeException("Texture failed to load: " + path);
        }

        try {
            width  = widthBuf.get(0);
            height = heightBuf.get(0);

            // Generate and bind texture
            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            // Filtering
            if (mipmaps) {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                        GL11.GL_NEAREST_MIPMAP_LINEAR);
            } else {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                        GL11.GL_NEAREST);
            }
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                    GL11.GL_NEAREST);

            // Wrapping
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                    GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_REPEAT);

            // Upload to GPU
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                    width, height, 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

            if (mipmaps) {
                GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        } finally {
            STBImage.stbi_image_free(image);
            MemoryUtil.memFree(widthBuf);
            MemoryUtil.memFree(heightBuf);
        }
    }

    /**
     * Binds this texture to the given texture unit.
     * Pass unit 0 for the default "tex" sampler in your shader.
     */
    public void bind(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void destroy() {
        GL11.glDeleteTextures(textureID);
    }

    public int getTextureID() { return textureID; }
    public int getWidth()     { return width; }
    public int getHeight()    { return height; }
}