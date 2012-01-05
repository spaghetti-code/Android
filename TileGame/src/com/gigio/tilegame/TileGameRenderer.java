package com.gigio.tilegame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.BasicGLRenderer;
import com.gigio.utils.GeometryUtils;

public class TileGameRenderer extends BasicGLRenderer
{
	private final Tile tile;

	private float angle = 0.0f;

	private final static float ROTATION_SPEED = 10.0f;

	private boolean rotateFrontToBack = false;

	private boolean rotateBackToFront = false;

	private boolean rotating = false;

	private float touchX = -1.0f;

	private float touchY = -1.0f;

	public TileGameRenderer(Context context)
	{
		super(context);
		this.tile = new Tile(context);
		this.tile.setCenterX(0.0f);
		this.tile.setCenterY(0.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		this.tile.loadTexture(gl, this.context);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		gl.glTranslatef(this.tile.getCenterX(), this.tile.getCenterY(), -1.0f);
		gl.glRotatef(this.angle, 0.0f, 1.0f, 0.0f);
		this.tile.draw(gl, true);

		gl.glLoadIdentity();
		gl.glTranslatef(this.tile.getCenterX(), this.tile.getCenterY(), -1.0f);
		gl.glRotatef(this.angle + 180, 0.0f, 1.0f, 0.0f);
		this.tile.draw(gl, false);

		gl.glLoadIdentity();
		if (this.touchX != -1.0f)
		{
			if (hitTile(gl))
				startTileRotation();
			this.touchX = -1.0f;
			this.touchY = -1.0f;
		}

		rotateTile();
	}

	private boolean hitTile(final GL10 gl)
	{
		final float[] coords = GeometryUtils.convertScreenCoordsToWorldCoords(
				gl, this.touchX, this.touchY, this.w, this.h);
		final float screenX = coords[0] / Math.abs(coords[2]);
		final float screenY = -coords[1] / Math.abs(coords[2]);
		return this.tile.hit(screenX, screenY);
	}

	private void rotateTile()
	{
		if (this.rotateFrontToBack)
		{
			if (this.angle == 180.0f)
			{
				this.rotateFrontToBack = false;
				this.rotating = false;
			} else
				this.angle += ROTATION_SPEED;
		} else if (this.rotateBackToFront)
		{
			if (this.angle == 360.0f)
			{
				this.angle = 0.0f;
				this.rotateBackToFront = false;
				this.rotating = false;
			} else
				this.angle += ROTATION_SPEED;
		}
	}

	private void startTileRotation()
	{
		if (!this.rotating)
		{
			this.rotating = true;
			if (this.angle == 0.0f)
				this.rotateFrontToBack = true;
			else
				this.rotateBackToFront = true;
		}
	}

	void setTouchCoords(final float x, final float y)
	{
		this.touchX = x;
		this.touchY = y;
	}
}
