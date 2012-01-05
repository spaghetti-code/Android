package com.gigio.tilegame;

import javax.microedition.khronos.opengles.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.gigio.utils.MatrixTrackingGL;

public class TileGameView extends GLSurfaceView
{
	private final TileGameRenderer renderer;

	private final TileGameListener listener;

	public TileGameView(Context context)
	{
		super(context);

		this.renderer = new TileGameRenderer(context);
		setRenderer(this.renderer);

		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.listener = new TileGameListener(this.renderer);
		this.setOnTouchListener(this.listener);

		// para permitir a conversão de coordenadas tela/mundo
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
