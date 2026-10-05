package net.kallen.solaris.util.file;

import java.nio.ByteBuffer;

public record ImageData(ByteBuffer buffer, int width, int height) {}
