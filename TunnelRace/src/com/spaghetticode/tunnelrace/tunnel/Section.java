package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Seção transversal do tunel.
 * 
 * @author Francesco Bertolino
 */
public class Section
{
	/**
	 * Os dois paineis que, altrenados com textures diferentes, 
	 * constituem a seção.
	 */
	private final BaseElement[] elements = new BaseElement[2];

	public Section(int texture1, int texture2)
	{
		this.elements[0] = new BaseElement(texture1);
		this.elements[1] = new BaseElement(texture2);
	}

	public void draw(GL10 gl)
	{
		gl.glPushMatrix();

		gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);

		this.elements[0].draw(gl);
		for (int i = 1; i < 8; i++)
			drawNextElement(gl, (i + 2) % 2);

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
