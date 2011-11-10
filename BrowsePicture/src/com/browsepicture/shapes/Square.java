package com.browsepicture.shapes;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;

/**
 * Textured square shape.
 * 
 * @author FRANCESCO
 */
public class Square extends Shape
{
	/**
	 * @param bitmap
	 */
	public Square(final Bitmap bitmap)
	{
		super(bitmap, new float[] { -1.0f, -1.0f, 0.0f, // 0. left-bottom
				1.0f, -1.0f, 0.0f, // 1. right-bottom
				-1.0f, 1.0f, 0.0f, // 2. left-top
				1.0f, 1.0f, 0.0f // 3. right-top
				}, new float[] { 0.0f, 1.0f, // A. left-bottom
						1.0f, 1.0f, // B. right-bottom
						0.0f, 0.0f, // C. left-top
						1.0f, 0.0f, // D. right-top
				});
	}

	@Override
	protected void drawShape(GL10 gl)
	{
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);
	}
}