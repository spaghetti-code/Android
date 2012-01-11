package com.gigio.tilegame.graph;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

/**
 * 3D tile graphic object.
 * 
 * @author Francesco Bertolino
 */
public class Tile3D
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

	private final int numFaces = 6;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices;

	private final FloatBuffer texBuffer;
	int[] textureIDs;
	float[] textureCoords = new float[] { 0.0f, 1.0f, // A. left-bottom
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

	private final Frame3D frame;

	public Tile3D(final Context context, final int value,
			final int frontTexture, final int backTexture, final float centerX,
			final float centerY, final float halfSide)
	{
		this.value = value;

		this.frontTexture = frontTexture;
		this.backTexture = backTexture;
		this.centerX = centerX;
		this.centerY = centerY;

		this.halfSide = halfSide;
		this.vertices = new float[] {
				// front
				-halfSide, -halfSide, 0.0f,//
				halfSide, -halfSide, 0.0f,//
				-halfSide, halfSide, 0.0f,//
				halfSide, halfSide, 0.0f,//
				// back
				halfSide, -halfSide, -0.02f,//
				-halfSide, -halfSide, -0.02f,//
				halfSide, halfSide, -0.02f,//
				-halfSide, halfSide, -0.02f,//
				// left
				-halfSide, -halfSide, -0.02f,//
				-halfSide, -halfSide, 0.0f,//
				-halfSide, halfSide, -0.02f,//
				-halfSide, halfSide, 0.0f,//
				// right
				halfSide, -halfSide, 0.0f,//
				halfSide, -halfSide, -0.02f,//
				halfSide, halfSide, 0.0f,//
				halfSide, halfSide, -0.02f,//
				// top
				-halfSide, halfSide, 0.0f,//
				halfSide, halfSide, 0.0f,//
				-halfSide, halfSide, -0.02f,//
				halfSide, halfSide, -0.02f,//
				// bottom
				halfSide, -halfSide, 0.0f,//
				-halfSide, -halfSide, 0.0f,//
				halfSide, -halfSide, -0.02f,//
				-halfSide, -halfSide, -0.02f,//
		};
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
		this.texBuffer = TextureUtils.initTextureBuffer(this.textureCoords);

		this.frame = new Frame3D(halfSide);
	}

	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texBuffer);

		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		for (int face = 0; face < this.numFaces; face++)
		{
			// aplica textures somente na face anterior e na posterior,
			// que são as primeiras duas definidas
			switch (face)
			{
				case 0:
					gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[0]);
					break;
				case 1:
					gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[1]);
					break;
				default:
					gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
		}

		this.frame.draw(gl);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

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