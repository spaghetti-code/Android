package com.gigio.tilegame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.BasicGLRenderer;
import com.gigio.utils.GeometryUtils;

public class TileGameRenderer extends BasicGLRenderer
{
	private final Tile[] tiles;

	private float angle = 0.0f;

	private final static float ROTATION_SPEED = 10.0f;

	private boolean rotateFrontToBack = false;

	private boolean rotateBackToFront = false;

	private boolean rotating = false;

	private float touchX = -1.0f;

	private float touchY = -1.0f;

	private int hit = -1;

	public TileGameRenderer(Context context)
	{
		super(context);
		this.tiles = new Tile[9];
		this.tiles[0] = new Tile(context, R.drawable.front, R.drawable.one,
				-0.3f, 0.25f);
		this.tiles[1] = new Tile(context, R.drawable.front, R.drawable.two,
				0.0f, 0.25f);
		this.tiles[2] = new Tile(context, R.drawable.front, R.drawable.three,
				0.3f, 0.25f);
		this.tiles[3] = new Tile(context, R.drawable.front, R.drawable.four,
				-0.3f, 0.0f);
		this.tiles[4] = new Tile(context, R.drawable.front, R.drawable.five,
				0.0f, 0.0f);
		this.tiles[5] = new Tile(context, R.drawable.front, R.drawable.six,
				0.3f, 0.0f);
		this.tiles[6] = new Tile(context, R.drawable.front, R.drawable.seven,
				-0.3f, -0.25f);
		this.tiles[7] = new Tile(context, R.drawable.front, R.drawable.eight,
				0.0f, -0.25f);
		this.tiles[8] = new Tile(context, R.drawable.front, R.drawable.nine,
				0.3f, -0.25f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		for (final Tile tile : this.tiles)
			tile.loadTexture(gl, this.context);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);
		gl.glTranslatef(0.0f, 0.0f, -1.0f);
		drawBorder(gl);

		Tile tile;
		for (int i = 0; i < this.tiles.length; i++)
		{
			tile = this.tiles[i];

			gl.glLoadIdentity();
			gl.glTranslatef(tile.getCenterX(), tile.getCenterY(), -1.0f);
			gl.glRotatef(this.hit == i ? this.angle : 0.0f, 0.0f, 1.0f, 0.0f);
			tile.draw(gl, true);

			gl.glLoadIdentity();
			gl.glTranslatef(tile.getCenterX(), tile.getCenterY(), -1.0f);
			gl.glRotatef(this.hit == i ? this.angle + 180 : 180, 0.0f, 1.0f,
					0.0f);
			tile.draw(gl, false);
		}

		gl.glLoadIdentity();
		if (this.touchX != -1.0f)
		{
			this.hit = hitTile(gl);
			if (this.hit != -1)
				startTileRotation();
			this.touchX = -1.0f;
			this.touchY = -1.0f;
		}

		rotateTile();
	}

	private void drawBorder(final GL10 gl)
	{
		new HorizontalLine().draw(gl, this, getMinY(gl), getMinX(gl) + 0.01f,
				getMaxX(gl));
		new HorizontalLine().draw(gl, this, getMaxY(gl) - 0.01f,
				getMinX(gl) + 0.01f, getMaxX(gl));
		new VerticalLine().draw(gl, this, getMinX(gl) + 0.01f, getMinY(gl),
				getMaxY(gl) - 0.01f);
		new VerticalLine().draw(gl, this, getMaxX(gl), getMinY(gl),
				getMaxY(gl) - 0.01f);
	}

	private float getMinX(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, 0,
				this.w, this.h))[0];
	}

	private float getMaxX(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, this.w, 0,
				this.w, this.h))[0];
	}

	private float getMinY(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, 0,
				this.w, this.h))[1];
	}

	private float getMaxY(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, this.h,
				this.w, this.h))[1];
	}

	/**
	 * @param gl
	 * @return
	 */
	private int hitTile(final GL10 gl)
	{
		final float[] coords = GeometryUtils.convertScreenCoordsToWorldCoords(
				gl, this.touchX, this.touchY, this.w, this.h);
		final float screenX = coords[0] / Math.abs(coords[2]);
		final float screenY = -coords[1] / Math.abs(coords[2]);
		Tile tile;
		for (int i = 0; i < this.tiles.length; i++)
		{
			tile = this.tiles[i];
			if (tile.hit(screenX, screenY))
				return i;
		}
		return -1;
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
