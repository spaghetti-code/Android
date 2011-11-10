package com.browsepicture.shapes;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;

/**
 * Textured cube shape.
 * 
 * @author Francesco Bertolino
 */
public class Cube extends Shape
{
	public Cube(final Bitmap bitmap)
	{
		super(bitmap, new float[] { // Vertices for a face at z=0
				-1.0f, -1.0f, 0.0f, // 0. left-bottom-front
						1.0f, -1.0f, 0.0f, // 1. right-bottom-front
						-1.0f, 1.0f, 0.0f, // 2. left-top-front
						1.0f, 1.0f, 0.0f // 3. right-top-front
				}, new float[] { // Texture coords for the above face
				0.0f, 1.0f, // A. left-bottom
						1.0f, 1.0f, // B. right-bottom
						0.0f, 0.0f, // C. left-top
						1.0f, 0.0f // D. right-top
				});
	}

	@Override
	protected void drawShape(GL10 gl)
	{
		// front
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// back
		gl.glPushMatrix();
		gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// top
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// bottom
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
	}

}