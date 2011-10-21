package com.spaghetticode.tunnelrace.tunnel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.utils.GeometryUtils;

/**
 * A nave espacial.
 * 
 * @author Francesco Bertolino
 */
public class Ship
{
	private final FloatBuffer vertexBuffer;
	private final float[] vertices = { // 5 vertices of the pyramid in (x,y,z)
	-0.5f, -0.5f, -0.25f, // 0. left-bottom-back
			0.5f, -0.5f, -0.25f, // 1. right-bottom-back
			0.5f, -0.5f, 0.25f, // 2. right-bottom-front
			-0.5f, -0.5f, 0.25f, // 3. left-bottom-front
			0.0f, 0.75f, 0.0f // 4. top
	};
	private final ByteBuffer indexBuffer;
	private final byte[] indices = { // Vertex indices of the 4 Triangles
	2, 4, 3, // front face (CCW)
			1, 4, 2, // right face
			0, 4, 1, // back face
			4, 0, 3 // left face
	};
	private final FloatBuffer colorBuffer; // Buffer for color-array
	private final float[] colors = { // Colors of the 5 vertices in RGBA
	0.0f, 0.0f, 1.0f, 1.0f, // 0. blue
			0.0f, 1.0f, 0.0f, 1.0f, // 1. green
			0.0f, 0.0f, 1.0f, 1.0f, // 2. blue
			0.0f, 1.0f, 0.0f, 1.0f, // 3. green
			1.0f, 0.0f, 0.0f, 1.0f // 4. red
	};

	public Ship()
	{
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);

		final ByteBuffer cbb = ByteBuffer
				.allocateDirect(this.colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		this.colorBuffer = cbb.asFloatBuffer();
		this.colorBuffer.put(this.colors);
		this.colorBuffer.position(0);

		// Setup index-array buffer. Indices in byte.
		this.indexBuffer = ByteBuffer.allocateDirect(this.indices.length);
		this.indexBuffer.put(this.indices);
		this.indexBuffer.position(0);
	}

	public void draw(GL10 gl)
	{
		//		gl.glFrontFace(GL10.GL_CCW);
		//		gl.glEnable(GL10.GL_CULL_FACE);
		//		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, this.indices.length,
				GL10.GL_UNSIGNED_BYTE, this.indexBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		//		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
