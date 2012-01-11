package com.gigio.tilegame.graph;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 3D frame to draw borders around a box
 * 
 * @author Francesco Bertolino
 */
public class Frame3D
{
	private final int numFaces = 6;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices;

	public Frame3D(final float halfSide)
	{
		this.vertices = new float[] {
				// front
				-halfSide, -halfSide, 0.0f,//
				halfSide, -halfSide, 0.0f,//
				halfSide, halfSide, 0.0f,//
				-halfSide, halfSide, 0.0f,//
				// back
				halfSide, -halfSide, -0.02f,//
				-halfSide, -halfSide, -0.02f,//
				-halfSide, halfSide, -0.02f,//
				halfSide, halfSide, -0.02f,//
				// left
				-halfSide, -halfSide, -0.02f,//
				-halfSide, -halfSide, 0.0f,//
				-halfSide, halfSide, 0.0f,//
				-halfSide, halfSide, -0.02f,//
				// right
				halfSide, -halfSide, 0.0f,//
				halfSide, -halfSide, -0.02f,//
				halfSide, halfSide, -0.02f,//
				halfSide, halfSide, 0.0f,//
				// top
				-halfSide, halfSide, 0.0f,//
				halfSide, halfSide, 0.0f,//
				halfSide, halfSide, -0.02f,//
				-halfSide, halfSide, -0.02f,//
				// bottom
				halfSide, -halfSide, 0.0f,//
				-halfSide, -halfSide, 0.0f,//
				-halfSide, -halfSide, -0.02f,//
				halfSide, -halfSide, -0.02f,//
		};

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

		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		for (int face = 0; face < this.numFaces; face++)
			gl.glDrawArrays(GL10.GL_LINE_LOOP, face * 4, 4);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

}