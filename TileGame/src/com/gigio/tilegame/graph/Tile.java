package com.gigio.tilegame.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

/**
 * Tile graphic object.
 * 
 * @author Francesco Bertolino
 */
public class Tile
{
	/**
	 * Center coords
	 */
	private final float centerX;
	private final float centerY;

	/**
	 * Halfsize of a tile side
	 */
	private final float halfSide;

	/**
	 * Vertex vars
	 */
	private final FloatBuffer vertexBuffer;
	private final float[] vertices;

	/**
	 * Texture vars
	 */
	private final FloatBuffer texBuffer;
	int[] textureIDs;
	float[] texCoords = { 0.0f, 1.0f, // A. left-bottom
			1.0f, 1.0f, // B. right-bottom
			0.0f, 0.0f, // C. left-top
			1.0f, 0.0f, // D. right-top
			0.0f, 1.0f, // A. left-bottom
			1.0f, 1.0f, // B. right-bottom
			0.0f, 0.0f, // C. left-top
			1.0f, 0.0f // D. right-top
	};

	/**
	 * Front and back texture resources
	 */
	private final int frontTexture;
	private final int backTexture;

	/**
	 * Numeric value
	 */
	private final int value;

	/**
	 * True if tile is showing the the hidden number
	 */
	private boolean showingNumber = false;

	/**
	 * @param context
	 * @param value Numeric value
	 * @param frontTexture Front texture resource
	 * @param backTexture Back texture resource
	 * @param centerX Center horizontal coord
	 * @param centerY Center vertical coord
	 * @param halfSide Halfsize of a tile side
	 */
	public Tile(final Context context, final int value, final int frontTexture,
			final int backTexture, final float centerX, final float centerY,
			final float halfSide)
	{
		this.value = value;

		this.frontTexture = frontTexture;
		this.backTexture = backTexture;
		this.centerX = centerX;
		this.centerY = centerY;

		this.halfSide = halfSide;
		this.vertices = new float[] { -halfSide, -halfSide, 0.0f, // 0. left-bottom
				halfSide, -halfSide, 0.0f, // 1. right-bottom
				-halfSide, halfSide, 0.0f, // 2. left-top
				halfSide, halfSide, 0.0f // 3. right-top
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
		this.texBuffer = TextureUtils.initTextureBuffer(this.texCoords);
	}

	/**
	 * @param gl
	 * @param front
	 */
	public void draw(GL10 gl, boolean front)
	{
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texBuffer);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[front ? 0 : 1]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	/**
	 * @param gl
	 * @param context
	 */
	public void loadTexture(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, context, new int[] {
				this.frontTexture, this.backTexture });
	}

	/**
	 * @param x OpenGL touch coord
	 * @param y OpenGL touch coord
	 * @return True if the tile has been touched, and it's not already
	 * showing the hidden number
	 */
	public boolean hit(final float x, final float y)
	{
		return !isShowingNumber() && x >= this.centerX - this.halfSide
				&& x <= this.centerX + this.halfSide
				&& y >= this.centerY - this.halfSide
				&& y <= this.centerY + this.halfSide;
	}

	/**
	 * @return centerX
	 */
	public float getCenterX()
	{
		return this.centerX;
	}

	/**
	 * @return centerY
	 */
	public float getCenterY()
	{
		return this.centerY;
	}

	/**
	 * @return Numeric value
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * @return showingNumber
	 */
	public boolean isShowingNumber()
	{
		return this.showingNumber;
	}

	/**
	 * @param showingNumber
	 */
	public void setShowingNumber(boolean showingNumber)
	{
		this.showingNumber = showingNumber;
	}
}
