package com.wplex.on.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.utils.GeometryUtils;
import com.wplex.on.view.GraphRenderer;

public class Line
{
	private FloatBuffer vertexBuffer;

	public void draw(final GL10 gl, final GraphRenderer renderer, final float x)
	{
		if (this.vertexBuffer == null)
			initVertexBuffer(gl, renderer, x);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glDrawArrays(GL10.GL_LINES, 0, 2);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	private void initVertexBuffer(final GL10 gl, final GraphRenderer renderer,
			final float x)
	{
		final float[] origin = GeometryUtils.convertScreenCoordsToWorldCoords(
				gl, x, 0, renderer.getWidth(), renderer.getHeight());
		final float[] destination = GeometryUtils
				.convertScreenCoordsToWorldCoords(gl, x, renderer.getHeight(),
						renderer.getWidth(), renderer.getHeight());
		final float[] vertices = new float[] {//
		origin[0], origin[1], 0.0f, // origem
				destination[0], destination[1], 0.0f, // destino
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(vertices);
	}
}
