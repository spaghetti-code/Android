package com.tunnel;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class TunnelView extends GLSurfaceView
{
	private TunnelRenderer tunnelRenderer;
	private GestureDetector gestureDetector;

	public TunnelView(Context context)
	{
		super(context);
		initView(context);
	}

	public TunnelView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context)
	{
		this.tunnelRenderer = new TunnelRenderer(context);
		setRenderer(this.tunnelRenderer);
		this.gestureDetector = new GestureDetector(new GestureListener());

		this.requestFocus();
		this.setFocusableInTouchMode(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent evt)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				this.tunnelRenderer.forward();
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				this.tunnelRenderer.back();
				break;
			case KeyEvent.KEYCODE_Q:
				System.exit(0);
				break;
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent evt)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_Q:
				System.exit(0);
				break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return this.gestureDetector.onTouchEvent(event);
	}

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onDown(MotionEvent e)
		{
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			if (velocityY > 0)
				TunnelView.this.tunnelRenderer.back();
			else
				TunnelView.this.tunnelRenderer.forward();
			return true;
		}
	}
}
