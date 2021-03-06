package com.spaghetticode.tunnelrace.tunnel;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.GeometryUtils;
import com.gigio.utils.TextureUtils;

/**
 * Elemento base para constru��o de uma se��o do tunel. 
 * 
 * @author Francesco Bertolino
 */
public class BaseElement
{
	private int textureResource;
	private final float[] textureCoords = new float[] { 0.0f, 1.0f, // A. left-bottom
			1.0f, 1.0f, // B. right-bottom
			0.0f, 0.0f, // C. left-top
			1.0f, 0.0f // D. right-top
	};
	private FloatBuffer textureBuffer;
	int[] textureIDs;

	private final FloatBuffer vertexBuffer;
	private final float[] vertices = {//
	-1.0f, -1.0f, 0.0f, //
			1.0f, -1.0f, 0.0f, //
			-1.0f, 1.0f, 0.0f, //
			1.0f, 1.0f, 0.0f //
	};

	public BaseElement(int textureResource)
	{
		initTexture(textureResource);
		this.vertexBuffer = GeometryUtils.initVertexBuffer(this.vertices);
	}

	private void initTexture(int textureResource)
	{
		this.textureResource = textureResource;
		this.textureBuffer = TextureUtils.initTextureBuffer(this.textureCoords);
	}

	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[0]);

		// ap�s profiling, o uso de glDrawArrays em vez que glDrawElements
		// resultou bem melhor
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.textureIDs = TextureUtils.loadTextures(gl, context,
				new int[] { this.textureResource });
	}

}
