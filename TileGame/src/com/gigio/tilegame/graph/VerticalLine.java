package com.gigio.tilegame.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.tilegame.view.game.TileGameRenderer;
import com.gigio.utils.GeometryUtils;

/**
 * Simple vertical line.
 * 
 * @author Francesco Bertolino
 */
public class VerticalLine
{
	private FloatBuffer vertexBuffer;

	/**
	 * @param gl
	 * @param renderer
	 * @param x
	 * @param minY
	 * @param maxY
	 */
	public void draw(final GL10 gl, final TileGameRenderer renderer,
			final float x, final float minY, final float maxY)
	{
		if (this.vertexBuffer == null)
			initVertexBuffer(gl, renderer, x, minY, maxY);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glDrawArrays(GL10.GL_LINES, 0, 2);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	/**
	 * @param gl
	 * @param renderer
	 * @param x
	 * @param minY
	 * @param maxY
	 */
	private void initVertexBuffer(final GL10 gl,
			final TileGameRenderer renderer, final float x, final float minY,
			final float maxY)
	{
		final float[] vertices = new float[] {//
		x, minY, 0.0f, // origem
				x, maxY, 0.0f, // destino
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(vertices);
	}
}
