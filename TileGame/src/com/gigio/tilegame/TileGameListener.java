package com.gigio.tilegame;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TileGameListener implements OnTouchListener
{
	private final TileGameRenderer renderer;

	public TileGameListener(final TileGameRenderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		final float x = event.getX();
		final float y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			this.renderer.setTouchCoords(x, y);
		}

		return true;
	}

}
