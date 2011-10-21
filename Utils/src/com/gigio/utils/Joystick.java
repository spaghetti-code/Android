package com.gigio.utils;

import javax.microedition.khronos.opengles.GL10;

public class Joystick
{
	private final Square base;
	private final Square stick;

	public Joystick(final float viewWidth, final float viewHeight)
	{
		this.base = new Square(viewWidth / 2000.0f, new float[] { 1.0f, 1.0f,
				1.0f, 1.0f });
		this.stick = new Square(viewWidth / 3000.0f, new float[] { 0.0f, 0.0f,
				1.0f, 1.0f });
	}

	public void draw(GL10 gl, float x, float y)
	{
		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0.0f);
		this.base.draw(gl);
		this.stick.draw(gl);
		gl.glPopMatrix();
	}
}
