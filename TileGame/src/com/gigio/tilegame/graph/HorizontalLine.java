package com.gigio.tilegame.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.gigio.tilegame.view.game.TileGameRenderer;
import com.gigio.utils.GeometryUtils;

/**
 * Simple horizontal line.
 * 
 * @author Francesco Bertolino
 */
public class HorizontalLine
{
	private FloatBuffer vertexBuffer;

	/**
	 * @param gl
	 * @param renderer
	 * @param y
	 * @param minX
	 * @param maxX
	 */
	public void draw(final GL10 gl, final TileGameRenderer renderer,
			final float y, final float minX, final float maxX)
	{
		if (this.vertexBuffer == null)
			initVertexBuffer(gl, renderer, y, minX, maxX);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glDrawArrays(GL10.GL_LINES, 0, 2);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	/**
	 * @param gl
	 * @param renderer
	 * @param y
	 * @param minX
	 * @param maxX
	 */
	private void initVertexBuffer(final GL10 gl,
			final TileGameRenderer renderer, final float y, final float minX,
			final float maxX)
	{
		final float[] vertices = new float[] {//
		minX, y, 0.0f, // origem
				maxX, y, 0.0f, // destino
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(vertices);
	}
}
