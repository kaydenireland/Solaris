package net.kallen.solaris.graphics.render;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.shader.SkyboxShader;
import net.kallen.solaris.util.file.ImageLoader;
import net.kallen.solaris.util.file.ModelLoader;
import net.kallen.solaris.util.file.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

public class SkyboxRenderer {
    private final SkyboxShader shader;
    private int vao;
    private final int vertexCount;
    private int textureID;

    private static final float SIZE = 500f;

    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private final static String[] TEXTURE_LOCATIONS = {
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "right").toImagePath(),
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "left").toImagePath(),
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "top").toImagePath(),
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "bottom").toImagePath(),
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "back").toImagePath(),
            ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "front").toImagePath()
    };

    public SkyboxRenderer() {
        this(new SkyboxShader());
    }

    public SkyboxRenderer(SkyboxShader shader) {
        this.shader = shader;
        this.vertexCount = VERTICES.length / 3;
    }

    public void create() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(VERTICES.length);
        buffer.put(VERTICES);
        buffer.flip();

        shader.create();
        textureID = ImageLoader.loadCubeMap(TEXTURE_LOCATIONS);
        vao = ModelLoader.createVAO();
        ModelLoader.storeData(
                buffer,
                0,
                3
        );
        GL30.glBindVertexArray(0);
    }

    public void destroy() {
        shader.destroy();
        GL30.glDeleteVertexArrays(vao);
        GL11.glDeleteTextures(textureID);
    }

    public void render(RenderContext context) {
        shader.bind();
        shader.loadProjectionMatrix(context.getProjectionMatrix());
        shader.loadViewMatrix(context.getViewMatrix());

        GL11.glDepthMask(false);
        GL30.glBindVertexArray(vao);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        GL30.glBindVertexArray(0);
        GL11.glDepthMask(true);

        shader.unbind();
    }
}
