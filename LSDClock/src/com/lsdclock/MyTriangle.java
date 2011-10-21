package com.lsdclock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class MyTriangle
{
	private final float[] vertexArray = new float[] { -1.0f, -1.0f, 0.0f, 1.0f,
			-1.0f, 0.0f, 0.0f, 1.0f, 0.0f };

	private final short[] indexArray = new short[] { 0, 1, 2 };

	float[] colorArray = { 1f, 0f, 0f, 1f, // vertex 0 red
			0f, 1f, 0f, 1f, // vertex 1 green
			0f, 0f, 1f, 1f, // vertex 2 blue
	};

	private final FloatBuffer vertexBuffer;

	private final ShortBuffer indexBuffer;

	private final FloatBuffer colorBuffer;

	public MyTriangle()
	{
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertexArray.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		this.vertexBuffer = vbb.asFloatBuffer();
		this.vertexBuffer.put(this.vertexArray);
		this.vertexBuffer.position(0);

		final ByteBuffer ibb = ByteBuffer
				.allocateDirect(this.indexArray.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		this.indexBuffer = ibb.asShortBuffer();
		this.indexBuffer.put(this.indexArray);
		this.indexBuffer.position(0);

		final ByteBuffer cbb = ByteBuffer
				.allocateDirect(this.colorArray.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		this.colorBuffer = cbb.asFloatBuffer();
		this.colorBuffer.put(this.colorArray);
		this.colorBuffer.position(0);
	}

	/**
	 * @param gl
	 */
	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, this.indexArray.length,
				GL10.GL_UNSIGNED_SHORT, this.indexBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
