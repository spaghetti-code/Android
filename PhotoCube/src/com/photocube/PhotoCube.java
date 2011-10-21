package com.photocube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.gigio.utils.TextureUtils;

/*
 * A photo cube with 6 pictures (textures) on its 6 faces.
 */
public class PhotoCube
{
	private final FloatBuffer vertexBuffer; // Vertex Buffer
	private final FloatBuffer texBuffer; // Texture Coords Buffer

	private final int numFaces = 6;
	private final int[] imageFileIDs = { // Image file IDs
	R.drawable.summer, R.drawable.spring, R.drawable.winter, R.drawable.autumn,
			R.drawable.mountain, R.drawable.desert };
	private final int[] textureIDs = new int[this.numFaces];
	private final Bitmap[] bitmap = new Bitmap[this.numFaces];
	private final float cubeHalfSize = 1.2f;

	// Constructor - Set up the vertex buffer
	public PhotoCube(Context context)
	{
		// Allocate vertex buffer. An float has 4 bytes
		final ByteBuffer vbb = ByteBuffer
				.allocateDirect(12 * 4 * this.numFaces);
		vbb.order(ByteOrder.nativeOrder());
		this.vertexBuffer = vbb.asFloatBuffer();

		// Read images. Find the aspect ratio and adjust the vertices
		// accordingly.
		for (int face = 0; face < this.numFaces; face++)
		{
			this.bitmap[face] = TextureUtils.loadBitmap(context,
					this.imageFileIDs[face]);
			final int imgWidth = this.bitmap[face].getWidth();
			final int imgHeight = this.bitmap[face].getHeight();
			float faceWidth = 2.0f;
			float faceHeight = 2.0f;
			// Adjust for aspect ratio
			if (imgWidth > imgHeight)
			{
				faceHeight = faceHeight * imgHeight / imgWidth;
			} else
			{
				faceWidth = faceWidth * imgWidth / imgHeight;
			}
			final float faceLeft = -faceWidth / 2;
			final float faceRight = -faceLeft;
			final float faceTop = faceHeight / 2;
			final float faceBottom = -faceTop;

			// Define the vertices for this face
			final float[] vertices = { faceLeft, faceBottom, 0.0f, // 0.
																	// left-bottom-front
					faceRight, faceBottom, 0.0f, // 1. right-bottom-front
					faceLeft, faceTop, 0.0f, // 2. left-top-front
					faceRight, faceTop, 0.0f, // 3. right-top-front
			};
			this.vertexBuffer.put(vertices); // Populate
		}
		this.vertexBuffer.position(0); // Rewind

		// Allocate texture buffer. An float has 4 bytes. Repeat for 6 faces.
		final float[] texCoords = { 0.0f, 1.0f, // A. left-bottom
				1.0f, 1.0f, // B. right-bottom
				0.0f, 0.0f, // C. left-top
				1.0f, 0.0f // D. right-top
		};
		final ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4
				* this.numFaces);
		tbb.order(ByteOrder.nativeOrder());
		this.texBuffer = tbb.asFloatBuffer();
		for (int face = 0; face < this.numFaces; face++)
		{
			this.texBuffer.put(texCoords);
		}
		this.texBuffer.position(0); // Rewind
	}

	// Render the shape
	public void draw(GL10 gl)
	{
		gl.glFrontFace(GL10.GL_CCW);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texBuffer);

		// front
		gl.glPushMatrix();
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[0]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[1]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
		gl.glPopMatrix();

		// back
		gl.glPushMatrix();
		gl.glRotatef(180.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[2]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
		gl.glPopMatrix();

		// top
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 1f, 0f, 0f);
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[4]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
		gl.glPopMatrix();

		// bottom
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1f, 0f, 0f);
		gl.glTranslatef(0f, 0f, this.cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[5]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
		gl.glPopMatrix();

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	// Load images into 6 GL textures
	public void loadTexture(GL10 gl)
	{
		gl.glGenTextures(6, this.textureIDs, 0); // Generate texture-ID array
													// for 6 IDs

		// Generate OpenGL texture images
		for (int face = 0; face < this.numFaces; face++)
		{
			gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIDs[face]);

			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR_MIPMAP_NEAREST);
			if (gl instanceof GL11)
				gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP,
						GL11.GL_TRUE);

			// Build Texture from loaded bitmap for the currently-bind texture
			// ID
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, this.bitmap[face], 0);
			this.bitmap[face].recycle();
		}
	}
}