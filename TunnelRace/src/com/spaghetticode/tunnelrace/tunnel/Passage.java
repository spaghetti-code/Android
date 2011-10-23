package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Passagem do tunel, constituida de uma sequência de
 * seções.
 * 
 * @author Francesco Bertolino
 */
public class Passage
{
	private final Section section;

	private final int nSections;

	public Passage(final int sections, final int texture1, final int texture2)
	{
		this.nSections = sections;
		this.section = new Section(texture1, texture2);
	}

	public void draw(GL10 gl)
	{
		for (int i = 0; i < this.nSections; i++)
		{
			gl.glTranslatef(0.0f, 0.0f, -2.0f);
			this.section.draw(gl);
		}
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.section.loadTexture(gl, context);
	}
}
