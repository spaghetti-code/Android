package com.coordsconverter;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.utils.GeometryUtils;

public class Point
{
	final float vertices[] = { 0.0f, 0.0f, -1.0f };
	private final FloatBuffer vertexBuffer;

	public Point()
	{
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
	}

	/**
	 * This function draws our square on screen.
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl, float x, float y)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glPointSize(10.0f);

		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0.0f);
		gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
		gl.glPopMatrix();

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
