package com.browsepicture.renderers;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.browsepicture.shapes.Square;
import com.gigio.utils.BasicGLRenderer;

/**
 * Square shape renderer
 */
public class SquareRenderer extends BasicGLRenderer implements IPictureRenderer
{
	private final Square square;
	private float angle = 0.0f;
	private final float speed = -1.5f;

	/**
	 * @param context
	 * @param bitmap
	 */
	public SquareRenderer(Context context, final Bitmap bitmap)
	{
		super(context);
		this.square = new Square(bitmap);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		this.square.loadTexture(gl, this.context);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.angle, 0.0f, 1.0f, 0.0f);
		this.square.draw(gl);

		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.angle + 180, 0.0f, 1.0f, 0.0f);
		this.square.draw(gl);

		this.angle += this.speed;
	}
}