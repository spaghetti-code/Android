package com.browsepicture.renderers;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.browsepicture.shapes.Cube;
import com.gigio.utils.BasicGLRenderer;

/**
 * Cube shape renderer.
 */
public class CubeRenderer extends BasicGLRenderer implements IPictureRenderer
{
	private final Cube cube;
	private float angle = 0.0f;
	private final float speed = -1.5f;

	/**
	 * @param context
	 * @param bitmap
	 */
	public CubeRenderer(Context context, final Bitmap bitmap)
	{
		super(context);
		this.cube = new Cube(bitmap);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		this.cube.loadTexture(gl, this.context);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.angle, 0.5f, 1.0f, -0.8f);
		this.cube.draw(gl);

		this.angle += this.speed;
	}
}