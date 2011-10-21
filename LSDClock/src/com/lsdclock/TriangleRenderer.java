package com.lsdclock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class TriangleRenderer implements Renderer
{
	private final MyTriangle triangle = new MyTriangle();

	private float angle;

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -10.0f);

		// triangulo grande
		gl.glPushMatrix();
		gl.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef(0.0f, -1.0f, 0.0f);
		this.triangle.draw(gl);
		gl.glPopMatrix();

		// triangulo pequeno
		gl.glPushMatrix();
		gl.glRotatef(180 + this.angle, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef(0.0f, 2.5f, 0.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		this.triangle.draw(gl);
		gl.glPopMatrix();

		this.angle++;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);// OpenGL docs.
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
		// Reset the projection matrix
		gl.glLoadIdentity();// OpenGL docs.
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
		// Reset the modelview matrix
		gl.glLoadIdentity();// OpenGL docs.
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // OpenGL docs.
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);// OpenGL docs.
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
				GL10.GL_NICEST);
	}

}
