package com.gigio.tilegame.view.game;

import javax.microedition.khronos.opengles.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.gigio.utils.MatrixTrackingGL;

/**
 * Tile game OpenGL view.
 * 
 * @author Francesco Bertolino
 */
public class TileGameView extends GLSurfaceView
{
	/**
	 * OpenGL renderer
	 */
	private TileGameRenderer renderer;

	/**
	 * Touch listener
	 */
	private TileGameListener listener;

	/**
	 * @param context
	 */
	public TileGameView(final Context context)
	{
		super(context);
	}

	/**
	 * Layout XML needs this constructor.
	 * 
	 * @param context
	 * @param attrs
	 */
	public TileGameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		// creates and sets OpenGL renderer
		this.renderer = new TileGameRenderer(context);
		setRenderer(this.renderer);

		// creates and sets touch listener
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.listener = new TileGameListener(this.renderer);
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

	/**
	 * @return renderer
	 */
	public TileGameRenderer getRenderer()
	{
		return this.renderer;
	}
}
