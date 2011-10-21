package com.crate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.TextureUtils;
import com.crate.R;

/*
 * Define the vertices for a representative face.
 * Render the cube by translating and rotating the face.
 */
public class TextureCube
{
	private final FloatBuffer vertexBuffer; // Buffer for vertex-array
	private final FloatBuffer texBuffer; // Buffer for texture-coords-array

	private final float[] vertices = { // Vertices for a face at z=0
	-1.0f, -1.0f, 0.0f, // 0. left-bottom-front
			1.0f, -1.0f, 0.0f, // 1. right-bottom-front
			-1.0f, 1.0f, 0.0f, // 2. left-top-front
			1.0f, 1.0f, 0.0f // 3. right-top-front
	};

	float[] texCoords = { // Texture coords for the above face
	0.0f, 1.0f, // A. left-bottom
			1.0f, 1.0f, // B. right-bottom
			0.0f, 0.0f, // C. left-top
			1.0f, 0.0f // D. right-top
	};
	int[] textureIDs = new int[1]; // Array for 1 texture-ID

	// Constructor - Set up the buffers
	public TextureCube()
	{
		// Setup vertex-array buffer. Vertices in float. An float has 4 bytes
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(this.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		this.vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		this.vertexBuffer.put(this.vertices); // Copy data into buffer
		this.vertexBuffer.position(0); // Rewind

		// Setup texture-coords-array buffer, in float. An float has 4 bytes
		final ByteBuffer tbb = ByteBuffer
				.allocateDirect(this.texCoords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		this.texBuffer = tbb.asFloatBuffer();
		this.texBuffer.put(this.texCoords);
		this.texBuffer.position(0);
	}

	// Draw the shape
	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
										// orientation
		gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
		gl.glCullFace(GL10.GL_BACK); // Cull the back face (don't display)

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

		// Enable texture-coords-array
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		// Define texture-coords buffer
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texBuffer);

		// front
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// back
		gl.glPushMatrix();
		gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// top
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// bottom
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// Disable texture-coords-array
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	// Load an image into GL texture
	public void loadTexture(GL10 gl, Context context)
	{
		TextureUtils.loadTextures(gl, context, new int[] { R.drawable.crate });
	}
}