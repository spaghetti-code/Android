package com.trianglesquare;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/*
 * A triangle with 3 vertices. Each vertex has its own color.
 */
public class Triangle
{
	private final FloatBuffer vertexBuffer; // Buffer for vertex-array
	private final FloatBuffer colorBuffer; // Buffer for color-array
	private final ByteBuffer indexBuffer; // Buffer for index-array

	private final float[] vertices = { // Vertices of the triangle
	0.0f, 1.0f, 0.0f, // 0. top
			-1.0f, -1.0f, 0.0f, // 1. left-bottom
			1.0f, -1.0f, 0.0f // 2. right-bottom
	};
	private final byte[] indices = { 0, 1, 2 }; // Indices to above vertices (in
												// CCW)
	private final float[] colors = { // Colors for the vertices
	1.0f, 0.0f, 0.0f, 1.0f, // Red
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 0.0f, 1.0f, 1.0f // Blue
	};

	// Constructor - Setup the data-array buffers
	public Triangle()
	{
		// Setup vertex-array buffer. Vertices in float. A float has 4 bytes.
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		this.vertexBuffer = vbb.asFloatBuffer(); // Convert byte buffer to float
		this.vertexBuffer.put(this.vertices); // Copy data into buffer
		this.vertexBuffer.position(0); // Rewind

		// Setup index-array buffer. Indices in byte.
		this.indexBuffer = ByteBuffer.allocateDirect(this.indices.length);
		this.indexBuffer.put(this.indices);
		this.indexBuffer.position(0);

		// Setup color-array buffer. Colors in float. A float has 4 bytes
		final ByteBuffer cbb = ByteBuffer
				.allocateDirect(this.colors.length * 4);
		cbb.order(ByteOrder.nativeOrder()); // Use native byte order
		this.colorBuffer = cbb.asFloatBuffer(); // Convert byte buffer to float
		this.colorBuffer.put(this.colors); // Copy data into buffer
		this.colorBuffer.position(0); // Rewind
	}

	// Render this shape
	public void draw(GL10 gl)
	{
		// Enable vertex-array and define the buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
		// gl*Pointer(int size, int type, int stride, Buffer pointer)
		// size: number of coordinates per vertex (must be 2, 3, or 4).
		// type: data type of vertex coordinate, GL_BYTE, GL_SHORT, GL_FIXED, or
		// GL_FLOAT
		// stride: the byte offset between consecutive vertices. 0 for tightly
		// packed.

		gl.glEnableClientState(GL10.GL_COLOR_ARRAY); // Enable color-array
		// Define color-array buffer
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);

		// Draw the primitives via index-array
		gl.glDrawElements(GL10.GL_TRIANGLES, this.indices.length,
				GL10.GL_UNSIGNED_BYTE, this.indexBuffer);
		// glDrawElements(int mode, int count, int type, Buffer indices)
		// mode: GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES,
		// GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, or GL_TRIANGLES
		// count: the number of elements to be rendered.
		// type: data-type of indices (must be GL_UNSIGNED_BYTE or
		// GL_UNSIGNED_SHORT).
		// indices: pointer to the index array.

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY); // Disable color-array
	}
}