package com.pyramid;

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

	private final Pyramid pyramid;
	private final Cube cube;
	private final Cube1 cube1;
	private final Cube2 cube2;

	// Rotational angle in degree for pyramid
	private static float anglePyramid = 0;
	// Rotational angle in degree for cube
	private static float angleCube = 0;
	private static float speedPyramid = 2.0f; // Rotational speed for pyramid
	private static float speedCube = -1.5f; // Rotational speed for cube

	// Constructor with global application context
	public MyGLRenderer(Context context)
	{
		this.context = context;
		this.pyramid = new Pyramid();
		this.cube = new Cube();
		this.cube1 = new Cube1();
		this.cube2 = new Cube2();
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
		gl.glRotatef(anglePyramid, 0.1f, 1.0f, -0.1f); // Rotate
		this.pyramid.draw(gl); // Draw the pyramid

		gl.glLoadIdentity(); // Reset the mode-view matrix
		gl.glTranslatef(1.5f, 1.7f, -6.0f);
		gl.glScalef(0.4f, 0.4f, 0.4f); // Scale down
		// rotate about the axis (1,1,1)
		gl.glRotatef(angleCube, 1.0f, 1.0f, 1.0f);
		this.cube.draw(gl); // Draw the cube

		gl.glLoadIdentity(); // Reset the mode-view matrix
		gl.glTranslatef(1.5f, 0.0f, -6.0f);
		gl.glScalef(0.4f, 0.4f, 0.4f); // Scale down
		// rotate about the axis (1,1,1)
		gl.glRotatef(angleCube, -1.0f, -1.0f, -1.0f);
		this.cube1.draw(gl); // Draw the cube

		gl.glLoadIdentity(); // Reset the mode-view matrix
		gl.glTranslatef(1.5f, -1.7f, -6.0f);
		gl.glScalef(0.4f, 0.4f, 0.4f); // Scale down
		// rotate about the axis (1,1,1)
		gl.glRotatef(angleCube, -1.0f, 0.0f, -1.0f);
		this.cube2.draw(gl); // Draw the cube

		// Update the rotational angle after each refresh
		anglePyramid += speedPyramid;
		angleCube += speedCube;
	}
}