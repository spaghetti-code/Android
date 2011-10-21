package com.logo2d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

/**
 * @author FRANCESCO
 */
public class WplexLogo2D
{
	private final FloatBuffer vertexBuffer;
	private final float[] vertices = { -1.5f, -1.0f, 0.0f, // 0. left-bottom
			1.5f, -1.0f, 0.0f, // 1. right-bottom
			-1.5f, 1.0f, 0.0f, // 2. left-top
			1.5f, 1.0f, 0.0f // 3. right-top
	};

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

	private final static int FRONT_TEXTURE = R.drawable.logo;
	private final static int BACK_TEXTURE = R.drawable.ten_years;

	public WplexLogo2D(Context context)
	{
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
		this.texBuffer = TextureUtils.initTextureBuffer(this.texCoords);
	}

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

	public void loadTexture(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, context, new int[] {
				FRONT_TEXTURE, BACK_TEXTURE });
	}

	public void destroy()
	{

	}

	public void resume(Context context)
	{

	}
}