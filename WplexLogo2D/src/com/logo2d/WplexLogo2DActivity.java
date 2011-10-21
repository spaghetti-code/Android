package com.logo2d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class WplexLogo2DActivity extends Activity
{
	private GLSurfaceView glView; // Use GLSurfaceView
	private MyGLRenderer glRenderer;

	// Call back when the activity is started, to initialize the view
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.glView = new GLSurfaceView(this); // Allocate a GLSurfaceView
		this.glRenderer = new MyGLRenderer(this);
		this.glView.setRenderer(this.glRenderer); // Use a custom
													// renderer
		this.setContentView(this.glView); // This activity sets to GLSurfaceView
	}

	// Call back when the activity is going into the background
	@Override
	protected void onPause()
	{
		super.onPause();
		this.glRenderer.destroy();
		this.glView.onPause();
	}

	// Call back after onPause()
	@Override
	protected void onResume()
	{
		super.onResume();
		this.glRenderer.resume(this);
		this.glView.onResume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.glRenderer.destroy();
	}
}