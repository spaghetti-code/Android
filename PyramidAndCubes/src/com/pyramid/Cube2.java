package com.pyramid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/*
 * Define the vertices for a representative face.
 * Render the cube by translating and rotating the face.
 */
public class Cube2
{
	private final FloatBuffer vertexBuffer; // Buffer for vertex-array

	private final float[] vertices = { // Vertices for a face at z=0
	-1.0f, -1.0f, 0.0f, // 0. left-bottom-front
			1.0f, -1.0f, 0.0f, // 1. right-bottom-front
			-1.0f, 1.0f, 0.0f, // 2. left-top-front
			1.0f, 1.0f, 0.0f // 3. right-top-front
	};

	private final float[][] colors = { // Colors of the 6 faces
	{ 1.0f, 0.5f, 0.0f, 1.0f }, // 0. orange
			{ 1.0f, 0.0f, 1.0f, 1.0f }, // 1. violet
			{ 0.0f, 1.0f, 0.0f, 1.0f }, // 2. green
			{ 0.0f, 0.0f, 1.0f, 1.0f }, // 3. blue
			{ 1.0f, 0.0f, 0.0f, 1.0f }, // 4. red
			{ 1.0f, 1.0f, 0.0f, 1.0f } // 5. yellow
	};

	// Constructor - Set up the buffers
	public Cube2()
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

		// front
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[0][0], this.colors[0][1], this.colors[0][2],
				this.colors[0][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[1][0], this.colors[1][1], this.colors[1][2],
				this.colors[1][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// back
		gl.glPushMatrix();
		gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[2][0], this.colors[2][1], this.colors[2][2],
				this.colors[2][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[3][0], this.colors[3][1], this.colors[3][2],
				this.colors[3][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// top
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[4][0], this.colors[4][1], this.colors[4][2],
				this.colors[4][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// bottom
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glColor4f(this.colors[5][0], this.colors[5][1], this.colors[5][2],
				this.colors[5][3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}