package com.pyramid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube
{
	private final FloatBuffer vertexBuffer; // Buffer for vertex-array
	private final int numFaces = 6;

	private final float[][] colors = { // Colors of the 6 faces
	{ 1.0f, 0.5f, 0.0f, 1.0f }, // 0. orange
			{ 1.0f, 0.0f, 1.0f, 1.0f }, // 1. violet
			{ 0.0f, 1.0f, 0.0f, 1.0f }, // 2. green
			{ 0.0f, 0.0f, 1.0f, 1.0f }, // 3. blue
			{ 1.0f, 0.0f, 0.0f, 1.0f }, // 4. red
			{ 1.0f, 1.0f, 0.0f, 1.0f } // 5. yellow
	};

	private final float[] vertices = { // Vertices of the 6 faces
	// FRONT
			-1.0f, -1.0f, 1.0f, // 0. left-bottom-front
			1.0f, -1.0f, 1.0f, // 1. right-bottom-front
			-1.0f, 1.0f, 1.0f, // 2. left-top-front
			1.0f, 1.0f, 1.0f, // 3. right-top-front
			// BACK
			1.0f, -1.0f, -1.0f, // 6. right-bottom-back
			-1.0f, -1.0f, -1.0f, // 4. left-bottom-back
			1.0f, 1.0f, -1.0f, // 7. right-top-back
			-1.0f, 1.0f, -1.0f, // 5. left-top-back
			// LEFT
			-1.0f, -1.0f, -1.0f, // 4. left-bottom-back
			-1.0f, -1.0f, 1.0f, // 0. left-bottom-front
			-1.0f, 1.0f, -1.0f, // 5. left-top-back
			-1.0f, 1.0f, 1.0f, // 2. left-top-front
			// RIGHT
			1.0f, -1.0f, 1.0f, // 1. right-bottom-front
			1.0f, -1.0f, -1.0f, // 6. right-bottom-back
			1.0f, 1.0f, 1.0f, // 3. right-top-front
			1.0f, 1.0f, -1.0f, // 7. right-top-back
			// TOP
			-1.0f, 1.0f, 1.0f, // 2. left-top-front
			1.0f, 1.0f, 1.0f, // 3. right-top-front
			-1.0f, 1.0f, -1.0f, // 5. left-top-back
			1.0f, 1.0f, -1.0f, // 7. right-top-back
			// BOTTOM
			-1.0f, -1.0f, -1.0f, // 4. left-bottom-back
			1.0f, -1.0f, -1.0f, // 6. right-bottom-back
			-1.0f, -1.0f, 1.0f, // 0. left-bottom-front
			1.0f, -1.0f, 1.0f // 1. right-bottom-front
	};

	// Constructor - Set up the buffers
	public Cube()
	{
		// Setup vertex-array buffer. Vertices in float. An float has 4 bytes
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		this.vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		this.vertexBuffer.put(this.vertices); // Copy data into buffer
		this.vertexBuffer.position(0); // Rewind
	}

	// Draw the shape
	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
										// orientation
		gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
		gl.glCullFace(GL10.GL_BACK); // Cull the back face (don't display)

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		// Render all the faces
		for (int face = 0; face < this.numFaces; face++)
		{
			// Set the color for each of the faces
			gl.glColor4f(this.colors[face][0], this.colors[face][1],
					this.colors[face][2], this.colors[face][3]);
			// Draw the primitive from the vertex-array directly
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}