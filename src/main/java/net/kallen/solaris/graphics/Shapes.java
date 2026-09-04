package net.kallen.solaris.graphics;

import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;

public final class Shapes {

    // Pre-built meshes
    public static final Mesh TRIANGLE = new Mesh(Positions.TRIANGLE, Faces.TRIANGLE);
    public static final Mesh SQUARE = new Mesh(Positions.SQUARE, Faces.RECTANGLE);
    public static final Mesh CUBE = new Mesh(Positions.CUBE, Faces.CUBE);
    public static final Mesh TRIANGLE_PYRAMID = new Mesh(Positions.TRIANGLE_PYRAMID, Faces.TRIANGLE_PYRAMID);
    public static final Mesh SQUARE_PYRAMID = new Mesh(Positions.SQUARE_PYRAMID,   Faces.SQUARE_PYRAMID);

    // Factory Methods

    /**
     * Creates a flat circle centered at the origin on the XY plane.
     *
     * @param radius   radius of the circle
     * @param segments number of segments around the circumference
     */
    public static Mesh circle(float radius, int segments) {
        if (segments < 3) {
            throw new IllegalArgumentException("Circle requires at least 3 segments");
        }

        Vertex[] vertices = new Vertex[segments + 1];
        int[] indices = new int[segments * 3];

        // Center
        vertices[0] = new Vertex(
                new Vector3(0, 0, 0),
                new Vector3(0, 0, 1),
                new Vector2(0.5f, 0.5f)
        );

        // Circumference
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;

            float x = radius * (float) Math.cos(angle);
            float y = radius * (float) Math.sin(angle);

            float u = 0.5f + x / (2.0f * radius);
            float v = 0.5f + y / (2.0f * radius);

            vertices[i + 1] = new Vertex(
                    new Vector3(x, y, 0),
                    new Vector3(0, 0, 1),
                    new Vector2(u, v)
            );
        }

        // Triangle fan
        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;

            indices[i * 3]     = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = next + 1;
        }

        return new Mesh(vertices, indices);
    }


    /**
     * Creates a sphere centered at the origin.
     *
     * @param radius   radius of the circle
     * @param segments number of segments around the circumference
     * @param rings number of rings around the circumference
     */
    public static Mesh sphere(float radius, int segments, int rings) {
        if (segments < 3) {
            throw new IllegalArgumentException("Sphere requires at least 3 segments");
        }

        if (rings < 2) {
            throw new IllegalArgumentException("Sphere requires at least 2 rings");
        }

        Vertex[] vertices = new Vertex[(rings + 1) * (segments + 1)];
        int[] indices = new int[rings * segments * 6];

        // Vertices
        for (int ring = 0; ring <= rings; ring++) {

            double phi = Math.PI * ring / rings;

            float y = radius * (float) Math.cos(phi);
            float ringRadius = radius * (float) Math.sin(phi);

            float v = (float) ring / rings;

            for (int segment = 0; segment <= segments; segment++) {

                double theta = 2.0 * Math.PI * segment / segments;

                float x = ringRadius * (float) Math.cos(theta);
                float z = ringRadius * (float) Math.sin(theta);

                float u = (float) segment / segments;

                int index = ring * (segments + 1) + segment;

                Vector3 position = new Vector3(x, y, z);
                Vector3 normal = position.normalize();

                vertices[index] = new Vertex(
                        position,
                        normal,
                        new Vector2(u, v)
                );
            }
        }

        // Indices
        int index = 0;

        for (int ring = 0; ring < rings; ring++) {
            for (int segment = 0; segment < segments; segment++) {

                int current = ring * (segments + 1) + segment;
                int next = current + segments + 1;

                indices[index++] = current;
                indices[index++] = next;
                indices[index++] = current + 1;

                indices[index++] = current + 1;
                indices[index++] = next;
                indices[index++] = next + 1;
            }
        }

        return new Mesh(vertices, indices);
    }

    /**
     * Creates a cylinder centered at the origin with its axis along Y.
     *
     * The total height extends from -height/2 to +height/2.
     *
     * @param radius   radius of the cylinder
     * @param height   total height
     * @param segments number of segments around the circumference
     */
    public static Mesh cylinder(float radius, float height, int segments) { // TODO: Normals

        if (segments < 3) {
            throw new IllegalArgumentException(
                    "Cylinder requires at least 3 segments"
            );
        }

        int ringSize = segments + 1;

        int bottomCenter = 0;
        int bottomRing = 1;

        int topCenter = bottomRing + ringSize;
        int topRing = topCenter + 1;

        int vertexCount = topRing + ringSize;

        Vertex[] vertices = new Vertex[vertexCount];

        float bottomY = -height / 2.0f;
        float topY = height / 2.0f;

        // --------------------------------------------------------
        // Bottom center
        // --------------------------------------------------------

        vertices[bottomCenter] = new Vertex(
                new Vector3(0, bottomY, 0),
                new Vector3(0, -1, 0),
                new Vector2(0.5f, 0.5f)
        );

        // --------------------------------------------------------
        // Bottom ring
        // --------------------------------------------------------

        for (int i = 0; i <= segments; i++) {

            double angle = 2.0 * Math.PI * i / segments;

            float x = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);

            float u = 0.5f + x / (2.0f * radius);
            float v = 0.5f + z / (2.0f * radius);

            vertices[bottomRing + i] = new Vertex(
                    new Vector3(x, bottomY, z),
                    new Vector3(0, -1, 0),
                    new Vector2(u, v)
            );
        }

        // --------------------------------------------------------
        // Top center
        // --------------------------------------------------------

        vertices[topCenter] = new Vertex(
                new Vector3(0, topY, 0),
                new Vector3(0, 1, 0),
                new Vector2(0.5f, 0.5f)
        );

        // --------------------------------------------------------
        // Top ring
        // --------------------------------------------------------

        for (int i = 0; i <= segments; i++) {

            double angle = 2.0 * Math.PI * i / segments;

            float x = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);

            float u = 0.5f + x / (2.0f * radius);
            float v = 0.5f + z / (2.0f * radius);

            vertices[topRing + i] = new Vertex(
                    new Vector3(x, topY, z),
                    new Vector3(0, 1, 0),
                    new Vector2(u, v)
            );
        }

        /*
         * Indices:
         *
         * bottom cap
         * top cap
         * sides
         */

        int indexCount = segments * 3 * 4;
        int[] indices = new int[indexCount];

        int index = 0;

        // --------------------------------------------------------
        // Bottom cap
        // --------------------------------------------------------

        for (int i = 0; i < segments; i++) {

            indices[index++] = bottomCenter;
            indices[index++] = bottomRing + i + 1;
            indices[index++] = bottomRing + i;
        }

        // --------------------------------------------------------
        // Top cap
        // --------------------------------------------------------

        for (int i = 0; i < segments; i++) {

            indices[index++] = topCenter;
            indices[index++] = topRing + i;
            indices[index++] = topRing + i + 1;
        }

        // --------------------------------------------------------
        // Sides
        // --------------------------------------------------------

        for (int i = 0; i < segments; i++) {

            int bottom = bottomRing + i;
            int bottomNext = bottomRing + i + 1;

            int top = topRing + i;
            int topNext = topRing + i + 1;

            // First triangle
            indices[index++] = bottom;
            indices[index++] = bottomNext;
            indices[index++] = top;

            // Second triangle
            indices[index++] = bottomNext;
            indices[index++] = topNext;
            indices[index++] = top;
        }

        return new Mesh(vertices, indices);
    }

    /**
     * Creates a cone centered around the origin with its axis along Y.
     *
     * The base is at -height/2 and the tip is at +height/2.
     *
     * NOTE: like cylinder(), the base ring vertices are shared between the
     * flat base cap and the slanted side surface, so they can only carry one
     * normal each. They're given the base's flat-down normal (matching the
     * cylinder precedent); the side surface's own slanted normal is a
     * follow-up, not computed here yet.
     */
    public static Mesh cone(float radius, float height, int segments) { // TODO: Normals

        if (segments < 3) {
            throw new IllegalArgumentException(
                    "Cone requires at least 3 segments"
            );
        }

        int ringSize = segments + 1;

        int center = 0;
        int ring = 1;
        int tip = ring + ringSize;

        Vertex[] vertices = new Vertex[tip + 1];

        float baseY = -height / 2.0f;
        float tipY = height / 2.0f;

        // Base center
        vertices[center] = new Vertex(
                new Vector3(0, baseY, 0),
                new Vector3(0, -1, 0),
                new Vector2(0.5f, 0.5f)
        );

        // Base ring
        for (int i = 0; i <= segments; i++) {

            double angle = 2.0 * Math.PI * i / segments;

            float x = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);

            float u = 0.5f + x / (2.0f * radius);
            float v = 0.5f + z / (2.0f * radius);

            vertices[ring + i] = new Vertex(
                    new Vector3(x, baseY, z),
                    new Vector3(0, -1, 0),
                    new Vector2(u, v)
            );
        }

        // Tip
        vertices[tip] = new Vertex(
                new Vector3(0, tipY, 0),
                new Vector3(0, 1, 0),
                new Vector2(0.5f, 1.0f)
        );

        /*
         * Base:
         *   segments triangles
         *
         * Sides:
         *   segments triangles
         */

        int[] indices = new int[segments * 6];

        int index = 0;

        // Base
        for (int i = 0; i < segments; i++) {

            indices[index++] = center;
            indices[index++] = ring + i + 1;
            indices[index++] = ring + i;
        }

        // Sides
        for (int i = 0; i < segments; i++) {

            indices[index++] = ring + i;
            indices[index++] = ring + i + 1;
            indices[index++] = tip;
        }

        return new Mesh(vertices, indices);
    }

    /**
     * Creates a capsule centered at the origin with its axis along Y.
     *
     * height is the TOTAL height of the capsule.
     *
     * @param radius   radius of the capsule
     * @param height   total height
     * @param segments radial resolution
     * @param rings    number of rings per hemisphere
     */
    public static Mesh capsule(float radius, float height, int segments, int rings) {

        if (segments < 3) {
            throw new IllegalArgumentException(
                    "Capsule requires at least 3 segments"
            );
        }

        if (rings < 1) {
            throw new IllegalArgumentException(
                    "Capsule requires at least 1 ring"
            );
        }

        if (height < radius * 2) {
            throw new IllegalArgumentException(
                    "Capsule height must be at least twice the radius"
            );
        }

        float cylinderHeight = height - radius * 2.0f;

        int totalRings = rings * 2 + 1;

        int ringSize = segments + 1;

        Vertex[] vertices = new Vertex[(totalRings + 1) * ringSize];

        int vertexIndex = 0;

        for (int ring = 0; ring <= totalRings; ring++) {

            float y;
            float ringRadius;
            double angle;

            if (ring <= rings) {

                // Bottom hemisphere
                angle = -Math.PI / 2.0 + (Math.PI / 2.0) * ring / rings;

                ringRadius = radius * (float) Math.cos(angle);

                y = -cylinderHeight / 2.0f + radius * (float) Math.sin(angle);

            } else {

                // Top hemisphere
                int topRing = ring - rings;

                angle = (Math.PI / 2.0) * topRing / rings;

                ringRadius = radius * (float) Math.cos(angle);

                y = cylinderHeight / 2.0f + radius * (float) Math.sin(angle);
            }

            // The point on each hemisphere ring lies on a sphere of radius
            // `radius` centered on that hemisphere's pole, at the same
            // (angle, theta) used to place the point itself — so the outward
            // normal is just (cos(angle)*cosTheta, sin(angle), cos(angle)*sinTheta).
            float cosAngle = (float) Math.cos(angle);
            float sinAngle = (float) Math.sin(angle);

            for (int segment = 0; segment <= segments; segment++) {

                double theta = 2.0 * Math.PI * segment / segments;
                float cosTheta = (float) Math.cos(theta);
                float sinTheta = (float) Math.sin(theta);

                float x = ringRadius * cosTheta;
                float z = ringRadius * sinTheta;
                float u = (float) segment / segments;
                float v = (float) ring / totalRings;

                Vector3 normal = new Vector3(cosAngle * cosTheta, sinAngle, cosAngle * sinTheta);

                vertices[vertexIndex++] =
                        new Vertex(
                                new Vector3(x, y, z),
                                normal,
                                new Vector2(u, v)
                        );
            }
        }

        /*
         * Connect every pair of rings.
         */

        int[] indices = new int[totalRings * segments * 6];
        int index = 0;

        for (int ring = 0; ring < totalRings; ring++) {

            int current = ring * ringSize;
            int next = (ring + 1) * ringSize;

            for (int segment = 0; segment < segments; segment++) {

                indices[index++] = current + segment;
                indices[index++] = next + segment;
                indices[index++] = current + segment + 1;
                indices[index++] = current + segment + 1;
                indices[index++] = next + segment;
                indices[index++] = next + segment + 1;
            }
        }

        return new Mesh(vertices, indices);
    }

    /**
     * Creates a torus centered at the origin around the Y axis.
     *
     * @param majorRadius radius from the center of the torus
     *                    to the center of the tube
     * @param minorRadius radius of the tube
     * @param majorSegments resolution around the torus
     * @param minorSegments resolution around the tube
     */
    public static Mesh torus(float majorRadius, float minorRadius, int majorSegments,int minorSegments) {

        if (majorSegments < 3) {
            throw new IllegalArgumentException(
                    "Torus requires at least 3 major segments"
            );
        }

        if (minorSegments < 3) {
            throw new IllegalArgumentException(
                    "Torus requires at least 3 minor segments"
            );
        }

        int ringSize = minorSegments + 1;

        Vertex[] vertices = new Vertex[(majorSegments + 1) * ringSize];
        int vertexIndex = 0;
        for (int major = 0; major <= majorSegments; major++) {
            double theta = 2.0 * Math.PI * major / majorSegments;
            float cosTheta = (float) Math.cos(theta);
            float sinTheta = (float) Math.sin(theta);

            for (int minor = 0; minor <= minorSegments; minor++) {

                double phi = 2.0 * Math.PI * minor / minorSegments;
                float cosPhi = (float) Math.cos(phi);
                float sinPhi = (float) Math.sin(phi);
                float ringRadius = majorRadius + minorRadius * cosPhi;

                float x = ringRadius * cosTheta;
                float y = minorRadius * sinPhi;
                float z = ringRadius * sinTheta;

                float u = (float) major / majorSegments;
                float v = (float) minor / minorSegments;

                // The tube's cross-section circle at this theta is centered on
                // (majorRadius*cosTheta, 0, majorRadius*sinTheta); the outward
                // normal is the direction from that center to this point, which
                // simplifies to exactly (cosPhi*cosTheta, sinPhi, cosPhi*sinTheta).
                Vector3 normal = new Vector3(cosPhi * cosTheta, sinPhi, cosPhi * sinTheta);

                vertices[vertexIndex++] =
                        new Vertex(
                                new Vector3(x, y, z),
                                normal,
                                new Vector2(u, v)
                        );
            }
        }

        int[] indices = new int[majorSegments * minorSegments * 6];
        int index = 0;
        for (int major = 0; major < majorSegments; major++) {

            int current = major * ringSize;
            int next = (major + 1) * ringSize;
            for (int minor = 0; minor < minorSegments; minor++) {

                indices[index++] = current + minor;
                indices[index++] = next + minor;
                indices[index++] = current + minor + 1;
                indices[index++] = current + minor + 1;
                indices[index++] = next + minor;
                indices[index++] = next + minor + 1;
            }
        }

        return new Mesh(vertices, indices);
    }

    /**
     * Creates a circle using the default resolution.
     */
    public static Mesh circle(float radius) {
        return circle(radius, 32);
    }

    /**
     * Creates a sphere using the default resolution.
     */
    public static Mesh sphere(float radius) {
        return sphere(radius, 32, 16);
    }

    /**
     * Creates a cylinder using the default resolution.
     */
    public static Mesh cylinder(float radius, float height) {
        return cylinder(radius, height, 32);
    }

    /**
     * Creates a cone using the default resolution.
     */
    public static Mesh cone(float radius, float height) {
        return cone(radius, height, 32);
    }

    /**
     * Creates a capsule using the default resolution.
     */
    public static Mesh capsule(float radius, float height) {
        return capsule(radius, height, 32, 8);
    }

    /**
     * Creates a torus using the default resolution.
     */
    public static Mesh torus(float majorRadius, float minorRadius) {
        return torus(majorRadius, minorRadius, 32, 16);
    }


    // Transformations — return new Mesh with modified vertices

    /**
     * Shifts all vertices by the given offset.
     * Use this to reposition a shape's geometry itself rather than its transform matrix.
     *
     * Example: Shapes.offset(Shapes.CUBE, new Vector3(1, 0, 0))
     */
    public static Mesh offset(Mesh mesh, Vector3 offset) {
        Vertex[] original = mesh.getVertices();
        Vertex[] shifted  = new Vertex[original.length];

        for (int i = 0; i < original.length; i++) {
            Vector3 pos = original[i].getPosition();
            shifted[i] = new Vertex(
                    new Vector3(
                            pos.x + offset.x,
                            pos.y + offset.y,
                            pos.z + offset.z
                    ),
                    original[i].getNormal(),
                    original[i].getTexturePos()
            );
        }

        return new Mesh(shifted, mesh.getIndices(), mesh.getTexture());
    }

    /**
     * Shifts all vertices by the given scalar offset.
     * Use this to reposition a shape's geometry itself rather than its transform matrix.
     *
     * Example: Shapes.offset(Shapes.CUBE, 1f)
     */
    public static Mesh offset(Mesh mesh, float offset) {
        return offset(mesh, new Vector3(offset, offset, offset));
    }

    /**
     * Scales all vertices by the given scalar vector.
     * Use this to bake a size into the geometry itself rather than the transform matrix.
     *
     * Example: Shapes.scale(Shapes.CUBE, new Vector3(2, 1, 2)) — wide flat cube
     */
    public static Mesh scale(Mesh mesh, Vector3 scalar) {
        Vertex[] original = mesh.getVertices();
        Vertex[] scaled   = new Vertex[original.length];

        for (int i = 0; i < original.length; i++) {
            Vector3 pos = original[i].getPosition();
            scaled[i] = new Vertex(
                    new Vector3(
                            pos.x * scalar.x,
                            pos.y * scalar.y,
                            pos.z * scalar.z
                    ),
                    original[i].getNormal(),
                    original[i].getTexturePos()
            );
        }

        return new Mesh(scaled, mesh.getIndices(), mesh.getTexture());
    }

    /**
     * Scales all vertices uniformly by a single float.
     *
     * Example: Shapes.scale(Shapes.CUBE, 0.5f) — half size cube
     */
    public static Mesh scale(Mesh mesh, float scalar) {
        return scale(mesh, new Vector3(scalar, scalar, scalar));
    }

    /**
     * Applies both offset and scale in one call.
     * Scale is applied first, then offset.
     *
     * Example: Shapes.transform(Shapes.CUBE, new Vector3(1, 0, 0), new Vector3(2, 2, 2))
     */
    public static Mesh transform(Mesh mesh, Vector3 offset, Vector3 scalar) {
        return offset(scale(mesh, scalar), offset);
    }

    /**
     * Applies a texture to a shape, returning a new Mesh with that texture assigned.
     *
     * Example: Shapes.withTexture(Shapes.CUBE, grassTexture)
     */
    public static Mesh withTexture(Mesh mesh, Texture texture) {
        return new Mesh(mesh.getVertices(), mesh.getIndices(), texture);
    }
}