package com.gigio.tilegame.view.game;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.tilegame.R;
import com.gigio.tilegame.game.GameHelper;
import com.gigio.tilegame.graph.Tile3D;
import com.gigio.utils.BasicGLRenderer;
import com.gigio.utils.GeometryUtils;
import com.gigio.utils.ScreenUtils;

/**
 * OpenGL renderer for tile game.
 * 
 * @author Francesco Bertolino
 */
public class TileGameRenderer extends BasicGLRenderer
{
	/**
	 * Tiles collection
	 */
	private Tile3D[] tiles;

	/**
	 * Rotation agle for each tile
	 */
	private float[] angle;

	/**
	 * Rotation speed used when showing a number
	 */
	private final static float SHOW_ROTATION_SPEED = 10.0f;

	/**
	 * Rotation speed used when hiding a number
	 */
	private final static float HIDE_ROTATION_SPEED = 20.0f;

	/**
	 * Rotation speed
	 */
	private float speed = SHOW_ROTATION_SPEED;

	/**
	 * For each tile, tells the renderer if it's rotating front to back (showing number)
	 */
	private boolean[] rotateFrontToBack;

	/**
	 * For each tile, tells the renderer if it's rotating back to front (hiding number)
	 */
	private boolean[] rotateBackToFront;

	/**
	 * Screen coordinate X, when the screen is touched
	 */
	private float touchX = -1.0f;

	/**
	 * Screen coordinate Y, when the screen is touched
	 */
	private float touchY = -1.0f;

	/**
	 * Index of the touched tile
	 */
	private int hit = -1;

	/**
	 * True if game has to be reset
	 */
	private boolean resetGame = false;

	/**
	 * True if game has to be continued
	 */
	private boolean continueGame = false;

	/**
	 * @param context
	 */
	public TileGameRenderer(Context context)
	{
		super(context);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		super.onSurfaceChanged(gl, width, height);
		initTiles(gl);
		continueGame();
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		gl.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

		// translates out of the screen to show objects
		gl.glTranslatef(0.0f, 0.0f, -1.0f);

		if (this.resetGame)
		{
			initTiles(gl);
			this.resetGame = false;
		}

		if (GameHelper.getInstance().isGameStarted())
		{
			Tile3D tile;
			for (int i = 0; i < this.tiles.length; i++)
			{
				tile = this.tiles[i];

				// draws tile
				gl.glLoadIdentity();
				gl.glTranslatef(tile.getCenterX(), tile.getCenterY(), -1.0f);
				gl.glRotatef(this.angle[i], 0.0f, 1.0f, 0.0f);
				tile.draw(gl);
			}

			gl.glLoadIdentity();
			// manages touch
			if (this.touchX != -1.0f)
			{
				// if a tile has been touched, starts its rotation
				this.hit = hitTile(gl);
				if (this.hit != -1)
					startTileRotation(this.hit);
				this.touchX = -1.0f;
				this.touchY = -1.0f;
			}
			// checks sequence after every rotation
			// FIXME just needed once each time
			else if (!tilesRotating())
				if (!GameHelper.getInstance().assertSequence())
					hideNumbers();

			// updates variables used for tiles rotation
			updateRotationVars();
		}
	}

	/**
	 * Initializes tiles:
	 * - random value
	 * - size
	 * - position
	 * 
	 * @param gl
	 */
	private void initTiles(GL10 gl)
	{
		final int tilesNumber = GameHelper.getInstance().getDifficulty()
				.getTiles();

		this.tiles = new Tile3D[tilesNumber];

		// gets tiles size based on OpenGL viewport size
		final float openGLWidth = getMaxX(gl) - getMinX(gl);
		final float openGLHeight = getMaxY(gl) - getMinY(gl);
		final float halfSide = getHalfSide(openGLWidth);

		// gets tile position based on OpenGL viewport size
		final float[] centerX = getCenterX(openGLWidth);
		final float[] centerY = getCenterY(openGLHeight);

		// generates random values
		List<Integer> values = new ArrayList<Integer>();
		if (!this.continueGame)
		{
			while (values.size() < tilesNumber)
				values.add(Integer.valueOf(GameHelper.getInstance()
						.getRandomValue(values)));
			GameHelper.getInstance().setTileNumbers(values);
		} else
			values = GameHelper.getInstance().getTileNumbers();

		// creates tiles
		for (int i = 0; i < tilesNumber; i++)
			this.tiles[i] = new Tile3D(this.context, values.get(i), GameHelper
					.getInstance().getBackTextureResource(i + 1), GameHelper
					.getInstance().getNumberTextureResource(values.get(i)),
					R.drawable.white, centerX[i], centerY[i], halfSide);

		// loads textures
		gl.glEnable(GL10.GL_TEXTURE_2D);
		for (final Tile3D tile : this.tiles)
			tile.loadTexture(gl, this.context);
	}

	/**
	 * @param openGLWidth
	 * @return Size of a tile's half side
	 */
	private float getHalfSide(final float openGLWidth)
	{
		final GameHelper gameHelper = GameHelper.getInstance();
		switch (gameHelper.getDifficulty())
		{
			case BEGINNER:
				return ScreenUtils.isPortrait(this.context) ? openGLWidth / 4.5f
						: openGLWidth / 5.0f;
			case EASY:
			case NORMAL:
				return ScreenUtils.isPortrait(this.context) ? openGLWidth / 7.0f
						: openGLWidth / 7.5f;
			case EXPERT:
			case MASTER:
				return ScreenUtils.isPortrait(this.context) ? openGLWidth / 9.5f
						: openGLWidth / 11.5f;
		}
		return ScreenUtils.isPortrait(this.context) ? openGLWidth / 7.0f
				: openGLWidth / 7.5f;
	}

	/**
	 * @param openGLWidth
	 * @return Horizontal center coordinates for each tile
	 */
	private float[] getCenterX(final float openGLWidth)
	{
		final GameHelper gameHelper = GameHelper.getInstance();
		switch (gameHelper.getDifficulty())
		{
			case BEGINNER:
				final float centerBeginner[] = { -openGLWidth / 4.0f,
						openGLWidth / 4.0f, -openGLWidth / 4.0f,
						openGLWidth / 4.0f };
				return centerBeginner;
			case EASY:
				final float centerEasy[] = { -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f };
				return centerEasy;
			case NORMAL:
				final float centerNormal[] = { -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f };
				return centerNormal;
			case EXPERT:
				final float centerExpert[] = { -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f, 0.0f,
						openGLWidth / 3.0f };
				return centerExpert;
			case MASTER:
				final float centerMaster[] = { -openGLWidth / 3.0f,
						-openGLWidth / 9.0f, openGLWidth / 9.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f,
						-openGLWidth / 9.0f, openGLWidth / 9.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f,
						-openGLWidth / 9.0f, openGLWidth / 9.0f,
						openGLWidth / 3.0f, -openGLWidth / 3.0f,
						-openGLWidth / 9.0f, openGLWidth / 9.0f,
						openGLWidth / 3.0f };
				return centerMaster;
		}
		return null;
	}

	/**
	 * @param openGLHeight
	 * @return Vertical center coordinates for each tile
	 */
	private float[] getCenterY(final float openGLHeight)
	{
		final GameHelper gameHelper = GameHelper.getInstance();
		switch (gameHelper.getDifficulty())
		{
			case BEGINNER:
				final float centerBeginner[] = { openGLHeight / 4.0f,
						openGLHeight / 4.0f, -openGLHeight / 4.0f,
						-openGLHeight / 4.0f };
				return centerBeginner;
			case EASY:
				final float centerEasy[] = { openGLHeight / 4.0f,
						openGLHeight / 4.0f, openGLHeight / 4.0f,
						-openGLHeight / 4.0f, -openGLHeight / 4.0f,
						-openGLHeight / 4.0f };
				return centerEasy;
			case NORMAL:
				final float centerNormal[] = { openGLHeight / 3.0f,
						openGLHeight / 3.0f, openGLHeight / 3.0f, 0.0f, 0.0f,
						0.0f, -openGLHeight / 3.0f, -openGLHeight / 3.0f,
						-openGLHeight / 3.0f };
				return centerNormal;
			case EXPERT:
				final float centerExpert[] = { openGLHeight / 3.0f,
						openGLHeight / 3.0f, openGLHeight / 3.0f,
						openGLHeight / 9.0f, openGLHeight / 9.0f,
						openGLHeight / 9.0f, -openGLHeight / 9.0f,
						-openGLHeight / 9.0f, -openGLHeight / 9.0f,
						-openGLHeight / 3.0f, -openGLHeight / 3.0f,
						-openGLHeight / 3.0f };
				return centerExpert;
			case MASTER:
				final float centerMaster[] = { openGLHeight / 3.0f,
						openGLHeight / 3.0f, openGLHeight / 3.0f,
						openGLHeight / 3.0f, openGLHeight / 9.0f,
						openGLHeight / 9.0f, openGLHeight / 9.0f,
						openGLHeight / 9.0f, -openGLHeight / 9.0f,
						-openGLHeight / 9.0f, -openGLHeight / 9.0f,
						-openGLHeight / 9.0f, -openGLHeight / 3.0f,
						-openGLHeight / 3.0f, -openGLHeight / 3.0f,
						-openGLHeight / 3.0f };
				return centerMaster;
		}
		return null;
	}

	//	/**
	//	 * Draws OpenGL viewport border.
	//	 * 
	//	 * @param gl
	//	 */
	//	private void drawBorder(final GL10 gl)
	//	{
	//		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	//		new HorizontalLine().draw(gl, this, getMinY(gl), getMinX(gl) + 0.01f,
	//				getMaxX(gl));
	//		new HorizontalLine().draw(gl, this, getMaxY(gl) - 0.01f,
	//				getMinX(gl) + 0.01f, getMaxX(gl));
	//		new VerticalLine().draw(gl, this, getMinX(gl) + 0.01f, getMinY(gl),
	//				getMaxY(gl) - 0.01f);
	//		new VerticalLine().draw(gl, this, getMaxX(gl), getMinY(gl),
	//				getMaxY(gl) - 0.01f);
	//	}

	/**
	 * @param gl
	 * @return Index of touched tile, or -1
	 */
	private int hitTile(final GL10 gl)
	{
		// if another tile is rotating, blocks touch
		if (!tilesRotating())
		{
			// converts touch screen coords to OpenGL coords
			final float[] coords = GeometryUtils
					.convertScreenCoordsToWorldCoords(gl, this.touchX,
							this.touchY, this.w, this.h);
			final float worldX = coords[0] / Math.abs(coords[2]);
			final float worldY = -coords[1] / Math.abs(coords[2]);

			// asserts if a tile has been touched
			Tile3D tile;
			for (int i = 0; i < this.tiles.length; i++)
			{
				tile = this.tiles[i];
				if (tile.hit(worldX, worldY))
				{
					tile.setShowingNumber(true);
					// updates number sequence based on tile value
					GameHelper.getInstance().updateSequence(tile.getValue());
					this.speed = SHOW_ROTATION_SPEED;
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Updates variables used for tiles rotation.
	 */
	private void updateRotationVars()
	{
		final int i = getRotatingTile();
		if (i != -1)
		{
			if (this.rotateFrontToBack[i])
			{
				// rotation front to back stops at 180 degrees
				if (this.angle[i] == 180.0f)
					this.rotateFrontToBack[i] = false;
				else
					this.angle[i] += this.speed;
			} else if (this.rotateBackToFront[i])
			{
				// rotation back to front stops at 360 degrees, and resets to 0 degrees
				if (this.angle[i] == 360.0f)
				{
					this.angle[i] = 0.0f;
					this.rotateBackToFront[i] = false;
				} else
					this.angle[i] += this.speed;
			}
		}
	}

	/**
	 * Hides all numbers.
	 */
	private void hideNumbers()
	{
		// clears numbers sequence
		GameHelper.getInstance().clearSequence();

		// sets all involved tiles to rotate back to front
		for (int i = 0; i < this.tiles.length; i++)
			if (this.tiles[i].isShowingNumber())
			{
				this.tiles[i].setShowingNumber(false);
				this.rotateBackToFront[i] = true;
			}
		// uses faster rotation speed
		this.speed = HIDE_ROTATION_SPEED;
	}

	/**
	 * Sets a tile to rotate front to back.
	 * 
	 * @param i Tile index
	 */
	private void startTileRotation(final int i)
	{
		if (!tilesRotating())
			this.rotateFrontToBack[i] = true;
	}

	/**
	 * @return True if at least a tile is already rotating.
	 */
	private boolean tilesRotating()
	{
		for (int i = 0; i < GameHelper.getInstance().getDifficulty().getTiles(); i++)
			if (tileRotating(i))
				return true;
		return false;
	}

	/**
	 * @return Index of rotating tile, or -1
	 */
	private int getRotatingTile()
	{
		for (int i = 0; i < GameHelper.getInstance().getDifficulty().getTiles(); i++)
			if (tileRotating(i))
				return i;
		return -1;
	}

	/**
	 * @param i Tile index
	 * @return True if the informed tile is rotating 
	 */
	private boolean tileRotating(final int i)
	{
		return this.rotateFrontToBack[i] || this.rotateBackToFront[i];
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

	/**
	 * Called when the game has to start new.
	 */
	public void resetGame()
	{
		this.resetGame = true;
		resetRotationArrays();
	}

	/**
	 * Called when the game has to continue after a pause.
	 * (ex. when the screen changes orientation)
	 */
	public void continueGame()
	{
		resetRotationArrays();
		final int sequenceSize = GameHelper.getInstance().getSequence().size();
		for (int i = 0; i < GameHelper.getInstance().getDifficulty().getTiles(); i++)
			if (this.tiles[i].getValue() <= sequenceSize)
			{
				this.angle[i] = 180.0f;
				this.tiles[i].setShowingNumber(true);
			}
		this.continueGame = false;
	}

	/**
	 * @param continueGame
	 */
	public void setContinueGame(boolean continueGame)
	{
		this.continueGame = continueGame;
	}

	/**
	 * Resets the arrays used during the tiles rotation.
	 */
	private void resetRotationArrays()
	{
		this.angle = new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		this.rotateBackToFront = new boolean[] { false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false };
		this.rotateFrontToBack = new boolean[] { false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false };
	}
}
