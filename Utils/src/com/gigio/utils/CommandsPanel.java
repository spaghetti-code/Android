package com.gigio.utils;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class CommandsPanel extends GLSurfaceView
{
	private Renderer renderer;

	public CommandsPanel(Context context)
	{
		super(context);
		initView(context);
	}

	public CommandsPanel(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context)
	{
		this.setGLWrapper(new GLSurfaceView.GLWrapper()
		{
			@Override
			public GL wrap(GL gl)
			{
				return new MatrixTrackingGL(gl);
			}
		});

		this.renderer = new Renderer(context);
		setRenderer(this.renderer);
	}

	private class Renderer extends BasicGLRenderer
	{
		private Joystick joystickLandscape;
		private Joystick joystickPortrait;
		private boolean landscape;

		public Renderer(final Context context)
		{
			super(context);
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			super.onDrawFrame(gl);

			gl.glTranslatef(0.0f, 0.0f, -1.0f);
			final float[] coords = GeometryUtils
					.convertScreenCoordsToWorldCoords(gl, this.w / 2.0f,
							this.h / 2.0f, this.w, this.h);
			if (this.landscape)
				this.joystickLandscape.draw(gl, coords[0], coords[1]);
			else
				this.joystickPortrait.draw(gl, coords[0], coords[1]);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			super.onSurfaceChanged(gl, width, height);
			this.landscape = (width > height);
			if (this.landscape)
			{
				if (this.joystickLandscape == null)
					this.joystickLandscape = new Joystick(width, height);
			} else
			{
				if (this.joystickPortrait == null)
					this.joystickPortrait = new Joystick(width, height);
			}
		}
	}

}
