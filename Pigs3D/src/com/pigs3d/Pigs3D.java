package com.pigs3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

/**
 * @author FRANCESCO
 */
public class Pigs3D
{
	private final int numFaces = 6;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices = {
			// front
			-1.0f, -1.0f, 0.2f,//
			1.0f, -1.0f, 0.2f,//
			-1.0f, 1.0f, 0.2f,//
			1.0f, 1.0f, 0.2f,//
			// back
			1.0f, -1.0f, -0.2f,//
			-1.0f, -1.0f, -0.2f,//
			1.0f, 1.0f, -0.2f,//
			-1.0f, 1.0f, -0.2f,//
			// left
			-1.0f, -1.0f, -0.2f,//
			-1.0f, -1.0f, 0.2f,//
			-1.0f, 1.0f, -0.2f,//
			-1.0f, 1.0f, 0.2f,//
			// right
			1.0f, -1.0f, 0.2f,//
			1.0f, -1.0f, -0.2f,//
			1.0f, 1.0f, 0.2f,//
			1.0f, 1.0f, -0.2f,//
			// top
			-1.0f, 1.0f, 0.2f,//
			1.0f, 1.0f, 0.2f,//
			-1.0f, 1.0f, -0.2f,//
			1.0f, 1.0f, -0.2f,//
			// bottom
			1.0f, -1.0f, 0.2f,//
			-1.0f, -1.0f, 0.2f,//
			1.0f, -1.0f, -0.2f,//
			-1.0f, -1.0f, -0.2f,//
	};

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

	private final static int TEXTURE_FRONT = R.drawable.front;
	private final static int TEXTURE_BACK = R.drawable.back;

	private final Frame3D frame;

	public Pigs3D(Context context)
	{
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
		this.texBuffer = TextureUtils.initTextureBuffer(this.textureCoords);
		this.frame = new Frame3D(context);
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

	void loadTextures(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, context, new int[] {
				TEXTURE_FRONT, TEXTURE_BACK });
	}

	void destroy()
	{

	}

	void resume(Context context)
	{

	}
}