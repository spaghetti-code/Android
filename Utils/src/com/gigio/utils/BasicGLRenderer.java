package com.gigio.utils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class BasicGLRenderer implements GLSurfaceView.Renderer
{
	protected final Context context;

	protected int w;

	protected int h;

	public BasicGLRenderer(final Context context)
	{
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if (height == 0)
			height = 1; // To prevent divide by zero
		final float aspect = (float) width / height;

		this.w = width;
		this.h = height;

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
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
	}

	/**
	 * @param gl
	 * @return Coordenada X OpenGL correspondente à extremidade esquerda da tela
	 */
	protected float getMinX(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, 0,
				this.w, this.h))[0];
	}

	/**
	 * @param gl
	 * @return Coordenada X OpenGL correspondente à extremidade direita da tela
	 */
	protected float getMaxX(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, this.w, 0,
				this.w, this.h))[0];
	}

	/**
	 * @param gl
	 * @return Coordenada Y OpenGL correspondente à extremidade inferior da tela
	 */
	protected float getMinY(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, 0,
				this.w, this.h))[1];
	}

	/**
	 * @param gl
	 * @return Coordenada Y OpenGL correspondente à extremidade superior da tela
	 */
	protected float getMaxY(GL10 gl)
	{
		return (GeometryUtils.convertScreenCoordsToWorldCoords(gl, 0, this.h,
				this.w, this.h))[1];
	}
}
