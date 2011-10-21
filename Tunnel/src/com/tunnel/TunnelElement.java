package com.tunnel;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

public class TunnelElement
{
	private int textureResource;
	private float[] textureCoords;
	private FloatBuffer textureBuffer;
	int[] textureIDs;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices = {//
	-1.0f, -1.0f, 0.0f, //
			1.0f, -1.0f, 0.0f, //
			-1.0f, 1.0f, 0.0f, //
			1.0f, 1.0f, 0.0f //
	};

	public TunnelElement(int textureResource, float[] textureCoord)
	{
		initTexture(textureResource, textureCoord);
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
	}

	private void initTexture(int textureResource, float[] textureCoord)
	{
		this.textureResource = textureResource;
		if (textureCoord != null)
			this.textureCoords = textureCoord;
		else
			this.textureCoords = new float[] { 0.0f, 1.0f, // A. left-bottom
					1.0f, 1.0f, // B. right-bottom
					0.0f, 0.0f, // C. left-top
					1.0f, 0.0f // D. right-top
			};
		this.textureBuffer = TextureUtils.initTextureBuffer(this.textureCoords);
	}

	public void draw(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[0]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, context,
				new int[] { this.textureResource });
	}

}
