package com.gigio.tilegame.view.intro;

import javax.microedition.khronos.opengles.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.gigio.utils.MatrixTrackingGL;

public class IntroView extends GLSurfaceView
{
	private final IntroRenderer renderer;

	private final IntroListener listener;

	public IntroView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		// creates and sets OpenGL renderer
		this.renderer = new IntroRenderer(context);
		setRenderer(this.renderer);

		// creates and sets touch listener
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.listener = new IntroListener(this.renderer);
		this.setOnTouchListener(this.listener);

		// to allow screen coords/OpenGL coords conversion
		setGLWrapper(new GLSurfaceView.GLWrapper()
		{
			@Override
			public GL wrap(GL gl)
			{
				return new MatrixTrackingGL(gl);
			}
		});
	}

}
