package com.rotatetriangle;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class HelloOpenGLES10 extends Activity
{

	private GLSurfaceView mGLView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		this.mGLView = new HelloOpenGLES10SurfaceView(this);
		setContentView(this.mGLView);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// The following call pauses the rendering thread.
		// If your OpenGL application is memory intensive,
		// you should consider de-allocating objects that
		// consume significant memory here.
		this.mGLView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// The following call resumes a paused rendering thread.
		// If you de-allocated graphic objects for onPause()
		// this is a good place to re-allocate them.
		this.mGLView.onResume();
		Toast.makeText(this, "Rotate me with your finger!", Toast.LENGTH_LONG)
				.show();
	}
}

class HelloOpenGLES10SurfaceView extends GLSurfaceView
{
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private final HelloOpenGLES10Renderer mRenderer;
	private float mPreviousX;
	private float mPreviousY;

	public HelloOpenGLES10SurfaceView(Context context)
	{
		super(context);

		// set the mRenderer member
		this.mRenderer = new HelloOpenGLES10Renderer();
		setRenderer(this.mRenderer);

		// Render the view only when there is a change
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.

		final float x = e.getX();
		final float y = e.getY();

		switch (e.getAction())
		{
			case MotionEvent.ACTION_MOVE:

				float dx = x - this.mPreviousX;
				float dy = y - this.mPreviousY;

				// reverse direction of rotation above the mid-line
				if (y > getHeight() / 2)
				{
					dx = dx * -1;
				}

				// reverse direction of rotation to left of the mid-line
				if (x < getWidth() / 2)
				{
					dy = dy * -1;
				}

				this.mRenderer.mAngle += (dx + dy) * this.TOUCH_SCALE_FACTOR;
				requestRender();
		}

		this.mPreviousX = x;
		this.mPreviousY = y;
		return true;
	}
}