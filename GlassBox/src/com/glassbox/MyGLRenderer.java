package com.glassbox;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
	private final Context context; // Application context needed to read image
	private final TextureCube cube;

	// For controlling cube's z-position, x and y angles and speeds
	float angleX = 0;
	float angleY = 0;
	float speedX = 0;
	float speedY = 0;
	float z = -6.0f;

	// Lighting
	boolean lightingEnabled = true; // Is lighting on?
	private final float[] lightAmbient = { 1.0f, 0.0f, 0.0f, 1.0f };
	private final float[] lightDiffuse = { 0.0f, 1.0f, 0.0f, 1.0f };
	private final float[] lightPosition = { 0.0f, 0.0f, 2.0f, 1.0f };

	// Blending
	boolean blendingEnabled = true; // Is blending on? deveria...

	// Constructor
	public MyGLRenderer(Context context)
	{
		this.context = context; // Get the application context (NEW)
		this.cube = new TextureCube();
	}

	// Call back when the surface is first created or re-created.
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set color's clear-value to
													// black
		gl.glClearDepthf(1.0f); // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables depth-buffer for hidden
											// surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL); // The type of depth testing to do
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); // nice
																		// perspective
																		// view
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
										// performance

		// Setup Texture, each time the surface is created (NEW)
		this.cube.loadTexture(gl, this.context); // Load image into Texture
													// (NEW)
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable texture (NEW)

		// Setup lighting GL_LIGHT1 with ambient and diffuse lights
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, this.lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, this.lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, this.lightPosition, 0);
		gl.glEnable(GL10.GL_LIGHT1); // Enable Light 1
		gl.glEnable(GL10.GL_LIGHT0); // Enable the default Light 0

		// Setup Blending
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full brightness, 50% alpha
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Select blending
														// function
	}

	// Call back after onSurfaceCreated() or whenever the window's size changes.
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if (height == 0)
			height = 1; // To prevent divide by zero
		final float aspect = (float) width / height;

		// Set the viewport (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix
		// Use perspective projection
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset
	}

	// Call back to draw the current frame.
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// Clear color and depth buffers
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Enable lighting?
		if (this.lightingEnabled)
		{
			gl.glEnable(GL10.GL_LIGHTING);
		} else
		{
			gl.glDisable(GL10.GL_LIGHTING);
		}

		// Blending Enabled?
		if (this.blendingEnabled)
		{
			gl.glEnable(GL10.GL_BLEND); // Turn blending on
			gl.glDisable(GL10.GL_DEPTH_TEST); // Turn depth testing off

		} else
		{
			gl.glDisable(GL10.GL_BLEND); // Turn blending off
			gl.glEnable(GL10.GL_DEPTH_TEST); // Turn depth testing on
		}

		// ----- Render the Cube -----
		gl.glLoadIdentity(); // Reset the current model-view matrix
		gl.glTranslatef(0.0f, 0.0f, this.z); // Translate into the screen
		gl.glRotatef(this.angleX, 1.0f, 0.0f, 0.0f); // Rotate
		gl.glRotatef(this.angleY, 0.0f, 1.0f, 0.0f); // Rotate
		this.cube.draw(gl);

		// Update the rotational angle after each refresh
		this.angleX += this.speedX;
		this.angleY += this.speedY;
	}
}