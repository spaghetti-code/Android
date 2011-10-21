package com.logo2d;

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
	Context context;

	WplexLogo2D logo;

	private float logoAngle = 0.0f;
	private final float logoSpeed = -1.5f;

	public MyGLRenderer(Context context)
	{
		this.context = context;
		this.logo = new WplexLogo2D(context);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		this.logo.loadTexture(gl, this.context);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if (height == 0)
			height = 1; // To prevent divide by zero
		final float aspect = (float) width / height;

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.logoAngle, 0.0f, 1.0f, 0.0f);
		this.logo.draw(gl, true);

		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glRotatef(this.logoAngle + 180, 0.0f, 1.0f, 0.0f);
		this.logo.draw(gl, false);

		this.logoAngle += this.logoSpeed;
	}

	public void destroy()
	{
		this.logo.destroy();
	}

	public void resume(Context context)
	{
		this.logo.resume(context);
	}
}