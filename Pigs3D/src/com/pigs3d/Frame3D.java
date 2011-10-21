package com.pigs3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * @author FRANCESCO
 */
public class Frame3D
{
	private final int numFaces = 6;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices = {
			// front
			-1.0f, -1.0f, 0.2f,//
			1.0f, -1.0f, 0.2f,//
			1.0f, 1.0f, 0.2f,//
			-1.0f, 1.0f, 0.2f,//
			// back
			1.0f, -1.0f, -0.2f,//
			-1.0f, -1.0f, -0.2f,//
			-1.0f, 1.0f, -0.2f,//
			1.0f, 1.0f, -0.2f,//
			// left
			-1.0f, -1.0f, -0.2f,//
			-1.0f, -1.0f, 0.2f,//
			-1.0f, 1.0f, 0.2f,//
			-1.0f, 1.0f, -0.2f,//
			// right
			1.0f, -1.0f, 0.2f,//
			1.0f, -1.0f, -0.2f,//
			1.0f, 1.0f, -0.2f,//
			1.0f, 1.0f, 0.2f,//
			// top
			-1.0f, 1.0f, 0.2f,//
			1.0f, 1.0f, 0.2f,//
			1.0f, 1.0f, -0.2f,//
			-1.0f, 1.0f, -0.2f,//
			// bottom
			1.0f, -1.0f, 0.2f,//
			-1.0f, -1.0f, 0.2f,//
			-1.0f, -1.0f, -0.2f,//
			1.0f, -1.0f, -0.2f,//
	};

	public Frame3D(Context context)
	{
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		this.vertexBuffer = vbb.asFloatBuffer();
		this.vertexBuffer.put(this.vertices);
		this.vertexBuffer.position(0);
	}

	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		for (int face = 0; face < this.numFaces; face++)
			gl.glDrawArrays(GL10.GL_LINE_LOOP, face * 4, 4);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

}