package com.wplex.on.graph;

import javax.microedition.khronos.opengles.GL10;

import com.wplex.on.view.GraphRenderer;

public class BackGround
{
	public void draw(final GL10 gl, final GraphRenderer renderer)
	{
		gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		for (int min = 0; min <= 1800; min += 60)
			new Line()
					.draw(gl, renderer, convertMinutesToPixels(renderer, min));
	}

	private float convertMinutesToPixels(final GraphRenderer renderer,
			final float min)
	{
		return min * (renderer.getWidth() / 1800f);
	}
}
