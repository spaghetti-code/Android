package com.gigio.tilegame.view.game;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Touch listener for tile game.
 * 
 * @author Francesco Bertolino
 */
public class TileGameListener implements OnTouchListener
{
	/**
	 * Game renderer
	 */
	private final TileGameRenderer renderer;

	/**
	 * @param renderer
	 */
	public TileGameListener(final TileGameRenderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		final float x = event.getX();
		final float y = event.getY();

		// when screen is touched, simply udpates touch coords in renderer
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			this.renderer.setTouchCoords(x, y);
		}

		return true;
	}

}
