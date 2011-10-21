package com.trianglesquare;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * OpenGL Custom renderer used with GLSurfaceView
 */
public class MyGLRenderer implements GLSurfaceView.Renderer
{
	Context context; // Application's context

	Triangle triangle;
	Square square;

	// Rotational angle and speed
	private float angleTriangle = 0.0f;
	private float angleQuad = 0.0f;
	private final float speedTriangle = 0.5f;
	private final float speedQuad = -0.7f;

	// Constructor with global application context
	public MyGLRenderer(Context context)
	{
		this.context = context;
		this.triangle = new Triangle();
		this.square = new Square();
	}

	// Call back when the surface is first created or re-created
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

		// You OpenGL|ES initialization code here
		// ......
	}

	// Call back after onSurfaceCreated() or whenever the window's size changes
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

		// You OpenGL|ES display re-sizing code here
		// ......
	}

	// Call back to draw the current frame.
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// Clear color and depth buffers using clear-value set earlier
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// You OpenGL|ES rendering code here
		gl.glLoadIdentity(); // Reset model-view matrix

		// Translate left and into the screen
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		// Rotate the triangle about the y-axis
		gl.glRotatef(this.angleTriangle, 0.0f, 1.0f, 0.0f);
		this.triangle.draw(gl); // Draw triangle

		gl.glLoadIdentity(); // Reset the mode-view matrix
		gl.glTranslatef(1.5f, 0.0f, -6.0f);
		// Rotate the square about the x-axis
		gl.glRotatef(this.angleQuad, 1.0f, 0.0f, 0.0f);
		this.square.draw(gl); // Draw square

		// Update the rotational angle after each refresh
		this.angleTriangle += this.speedTriangle;
		this.angleQuad += this.speedQuad;
	}
}