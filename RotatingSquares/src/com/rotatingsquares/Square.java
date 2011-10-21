package com.rotatingsquares;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square
{
	// Our vertices.
	private final float vertices[] = { -1.0f, 1.0f, 0.0f, // 0, Top Left
			-1.0f, -1.0f, 0.0f, // 1, Bottom Left
			1.0f, -1.0f, 0.0f, // 2, Bottom Right
			1.0f, 1.0f, 0.0f, // 3, Top Right
	};

	// The order we like to connect them.
	private final short[] indices = { 0, 1, 2, 0, 2, 3 };

	// Our vertex buffer.
	private final FloatBuffer vertexBuffer;

	// Our index buffer.
	private final ShortBuffer indexBuffer;

	public Square()
	{
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		this.vertexBuffer = vbb.asFloatBuffer();
		this.vertexBuffer.put(this.vertices);
		this.vertexBuffer.position(0);

		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		final ByteBuffer ibb = ByteBuffer
				.allocateDirect(this.indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		this.indexBuffer = ibb.asShortBuffer();
		this.indexBuffer.put(this.indices);
		this.indexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl)
	{
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, // OpenGL docs
				this.vertexBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, this.indices.length,// OpenGL docs
				GL10.GL_UNSIGNED_SHORT, this.indexBuffer);

		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
	}

}