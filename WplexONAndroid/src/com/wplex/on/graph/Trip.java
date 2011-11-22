package com.wplex.on.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.utils.GeometryUtils;
import com.wplex.on.view.GraphRenderer;

public class Trip
{
	private FloatBuffer vertexBuffer;

	public void draw(final GL10 gl, final GraphRenderer renderer,
			final short startTime, final short endTime, final float y,
			final float[] rgb)
	{
		if (this.vertexBuffer == null)
			initVertexBuffer(gl, renderer, startTime, endTime, y);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	private void initVertexBuffer(final GL10 gl, final GraphRenderer renderer,
			final short startTime, final short endTime, final float y)
	{
		final float[] origin = GeometryUtils.convertScreenCoordsToWorldCoords(
				gl, convertMinutesToPixels(renderer, startTime), y,
				renderer.getWidth(), renderer.getHeight());
		final float[] destination = GeometryUtils
				.convertScreenCoordsToWorldCoords(gl,
						convertMinutesToPixels(renderer, endTime), y,
						renderer.getWidth(), renderer.getHeight());
		final float halfLength = (destination[0] - origin[0]) / 2f;
		final float[] vertices = new float[] {//
		origin[0], origin[1], 0.0f, // origem
				destination[0], destination[1], 0.0f, // destino
				origin[0] + halfLength, origin[1] - halfLength, 0.0f //vertice
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(vertices);
	}

	private float convertMinutesToPixels(final GraphRenderer renderer,
			final float min)
	{
		return min * (renderer.getWidth() / 1800f);
	}
}
