package com.pyramid;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Our OpenGL program's main activity
 */
public class Android3DActivity extends Activity
{

	private GLSurfaceView glView; // Use GLSurfaceView

	// Call back when the activity is started, to initialize the view
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.glView = new GLSurfaceView(this); // Allocate a GLSurfaceView
		this.glView.setRenderer(new MyGLRenderer(this)); // Use a custom
															// renderer
		this.setContentView(this.glView); // This activity sets to GLSurfaceView
	}

	// Call back when the activity is going into the background
	@Override
	protected void onPause()
	{
		super.onPause();
		this.glView.onPause();
	}

	// Call back after onPause()
	@Override
	protected void onResume()
	{
		super.onResume();
		this.glView.onResume();
	}
}