package com.gigio.tilegame.view.intro;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;

import com.gigio.tilegame.graph.IntroCube;
import com.gigio.tilegame.view.game.TileGameActivity;
import com.gigio.utils.BasicGLRenderer;
import com.gigio.utils.GeometryUtils;

public class IntroRenderer extends BasicGLRenderer
{
	private final IntroCube cube;

	private float angleX = 0;
	private float angleY = 0;
	private final float speedX = 1.0f;
	private final float speedY = -1.0f;

	/**
	 * Screen coordinate X, when the screen is touched
	 */
	private float touchX = -1.0f;

	/**
	 * Screen coordinate Y, when the screen is touched
	 */
	private float touchY = -1.0f;

	public IntroRenderer(Context context)
	{
		super(context);
		this.cube = new IntroCube();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);

		this.cube.loadTexture(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		if (this.touchX != -1.0f)
			checkTouch(gl);

		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(this.angleY, 0.0f, 1.0f, 0.0f);
		this.cube.draw(gl);

		this.angleX += this.speedX;
		this.angleY += this.speedY;
	}

	private void checkTouch(GL10 gl)
	{
		// converts touch screen coords to OpenGL coords
		final float[] coords = GeometryUtils.convertScreenCoordsToWorldCoords(
				gl, this.touchX, this.touchY, this.w, this.h);
		final float worldX = coords[0] / Math.abs(coords[2]);
		final float worldY = -coords[1] / Math.abs(coords[2]);

		if (this.cube.hit(worldX, worldY))
		{
			this.touchX = -1.0f;
			this.touchY = -1.0f;
			startGame();
		}
	}

	/**
	 * Updates touch coords when the screen is touched.
	 * 
	 * @param x
	 * @param y
	 */
	void setTouchCoords(final float x, final float y)
	{
		this.touchX = x;
		this.touchY = y;
	}

	private void startGame()
	{
		final Intent intent = new Intent(this.context, TileGameActivity.class);
		this.context.startActivity(intent);
	}
}
