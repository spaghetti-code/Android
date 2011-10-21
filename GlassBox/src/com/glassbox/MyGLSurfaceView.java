package com.glassbox;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

/*
 * Custom GL view by extending GLSurfaceView so as
 * to override event handlers such as onKeyUp(), onTouchEvent()
 */
public class MyGLSurfaceView extends GLSurfaceView
{
	MyGLRenderer renderer; // Custom GL Renderer

	// For touch event
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320.0f;
	private float previousX;
	private float previousY;

	// Constructor - Allocate and set the renderer
	public MyGLSurfaceView(Context context)
	{
		super(context);
		this.renderer = new MyGLRenderer(context);
		this.setRenderer(this.renderer);
		// Request focus, otherwise key/button won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);
	}

	// Handler for key event
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent evt)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_LEFT: // Decrease Y-rotational speed
				this.renderer.speedY -= 0.1f;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT: // Increase Y-rotational speed
				this.renderer.speedY += 0.1f;
				break;
			case KeyEvent.KEYCODE_DPAD_UP: // Decrease X-rotational speed
				this.renderer.speedX -= 0.1f;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN: // Increase X-rotational speed
				this.renderer.speedX += 0.1f;
				break;
			case KeyEvent.KEYCODE_A: // Zoom out (decrease z)
				this.renderer.z -= 0.2f;
				break;
			case KeyEvent.KEYCODE_Z: // Zoom in (increase z)
				this.renderer.z += 0.2f;
				break;
			case KeyEvent.KEYCODE_L: // Toggle lighting on/off
				this.renderer.lightingEnabled = !this.renderer.lightingEnabled;
				break;
			case KeyEvent.KEYCODE_B: // Toggle Blending on/off
				this.renderer.blendingEnabled = !this.renderer.blendingEnabled;
				break;
			case KeyEvent.KEYCODE_Q: // Quit
				System.exit(0);
				break;
		}
		return true; // Event handled
	}

	// Handler for touch event
	@Override
	public boolean onTouchEvent(final MotionEvent evt)
	{
		final float currentX = evt.getX();
		final float currentY = evt.getY();
		float deltaX, deltaY;
		switch (evt.getAction())
		{
			case MotionEvent.ACTION_MOVE:
				// Modify rotational angles according to movement
				deltaX = currentX - this.previousX;
				deltaY = currentY - this.previousY;
				this.renderer.angleX += deltaY * this.TOUCH_SCALE_FACTOR;
				this.renderer.angleY += deltaX * this.TOUCH_SCALE_FACTOR;
		}
		// Save current x, y
		this.previousX = currentX;
		this.previousY = currentY;
		return true; // Event handled
	}
}