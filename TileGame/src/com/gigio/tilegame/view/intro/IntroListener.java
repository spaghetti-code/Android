package com.gigio.tilegame.view.intro;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class IntroListener implements OnTouchListener
{
	private final IntroRenderer renderer;

	/**
	 * @param renderer
	 */
	public IntroListener(final IntroRenderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
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
