package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Seção transversal do tunel.
 * 
 * TODO testar desempenho redesenhando várias vezes um único elemento.
 * 
 * @author Francesco Bertolino
 */
public class Section
{
	private final BaseElement[] elements = new BaseElement[8];

	public Section(int texture1, int texture2)
	{
		boolean even = true;
		for (int i = 0; i < this.elements.length; i++)
		{
			this.elements[i] = new BaseElement(even ? texture1 : texture2);
			even = !even;
		}
	}

	public void draw(GL10 gl)
	{
		gl.glPushMatrix();

		gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
		this.elements[0].draw(gl);

		for (int i = 1; i < this.elements.length; i++)
			drawNextElement(gl, i);

		//		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		//		gl.glRotatef(-45, 0.0f, 1.0f, 0.0f);
		//		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		//		this.elements[1].draw(gl);
		//
		//		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		//		gl.glRotatef(-45, 0.0f, 1.0f, 0.0f);
		//		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		//		this.elements[2].draw(gl);

		gl.glPopMatrix();
	}

	private void drawNextElement(GL10 gl, int index)
	{
		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		gl.glRotatef(-45, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		this.elements[index].draw(gl);
	}

	public void loadTexture(GL10 gl, Context context)
	{
		for (int i = 0; i < this.elements.length; i++)
			this.elements[i].loadTexture(gl, context);
	}
}
