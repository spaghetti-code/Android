package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Curva do tunel.
 * 
 * @author Francesco Bertolino
 */
public class Bend
{
	private final Section section;

	private final int nSections;

	private final int direction;

	public Bend(final int nSections, final int direction, final int texture1,
			final int texture2)
	{
		this.section = new Section(texture1, texture2);
		this.nSections = nSections;
		this.direction = direction;
	}

	public void draw(GL10 gl)
	{
		for (int i = 1; i < this.nSections; i++)
		{
			gl.glTranslatef(0.0f, 0.0f, -1.7f);
			gl.glRotatef(2 * this.direction, 0.0f, 1.0f, 0.0f);
			this.section.draw(gl);
		}
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.section.loadTexture(gl, context);
	}
}
