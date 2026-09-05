package net.kallen.solaris.graphics.shader;

import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.util.file.FileLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class Shader {

    private int programID;
    private final String vertexFile, fragmentFile;
    private int vertexID, fragmentID;

    private final Map<String, Integer> uniformCache = new HashMap<>();

    protected Shader(String vPath, String fPath) {
        vertexFile = FileLoader.loadAsString(vPath);
        fragmentFile = FileLoader.loadAsString(fPath);
    }

    public void create() {
        programID = GL20.glCreateProgram();

        // Vertex Shader
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexID, vertexFile);
        GL20.glCompileShader(vertexID);

        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Vertex shader compilation Failed: " + GL20.glGetShaderInfoLog(vertexID));
        }

        // Fragment Shader
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentID, fragmentFile);
        GL20.glCompileShader(fragmentID);

        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String log = GL20.glGetShaderInfoLog(fragmentID);
            throw new RuntimeException("Fragment shader compilation failed:\n" + log);
        }

        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        bindAttributes();

        GL20.glLinkProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            String log = GL20.glGetShaderInfoLog(programID);
            throw new RuntimeException("Shader program linking failed:\n" + log);
        }

        GL20.glValidateProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
        }

    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int index, String name) {
        GL20.glBindAttribLocation(programID, index, name);
    }


    protected int getUniformLocation(String name) {
        if (uniformCache.containsKey(name)) {
            return uniformCache.get(name);
        }
        int location = GL20.glGetUniformLocation(programID, name);
        uniformCache.put(name, location);
        return location;
    }

    protected void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    protected void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    protected void setUniform(String name, boolean value) {
        GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    protected void setUniform(String name, Vector2 value) {
        GL20.glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    protected void setUniform(String name, Vector3 value) {
        GL20.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    protected void setUniform(String name, Matrix4 value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4.SIZE * Matrix4.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
        MemoryUtil.memFree(matrix);
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void destroy() {
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

        GL20.glDeleteProgram(programID);
    }

}