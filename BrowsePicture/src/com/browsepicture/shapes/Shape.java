package com.browsepicture.shapes;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

public abstract class Shape
{
	protected final FloatBuffer vertexBuffer;
	protected final float[] vertices;

	protected final FloatBuffer texBuffer;
	protected int[] textureIDs;
	protected final float[] texCoords;

	protected final Bitmap bitmap;

	public Shape(final Bitmap bitmap, final float[] vertices,
			final float[] texCoords)
	{
		this.bitmap = bitmap;
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
		this.texBuffer = TextureUtils.initTextureBuffer(this.texCoords);
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, this.bitmap);
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

		drawShape(gl);

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	protected abstract void drawShape(GL10 gl);
}
