package com.pigs3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Pigs3DActivity extends Activity
{
	private GLSurfaceView glView; // Use GLSurfaceView
	private MyGLRenderer glRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.glView = new GLSurfaceView(this); // Allocate a GLSurfaceView
		this.glRenderer = new MyGLRenderer(this);
		this.glView.setRenderer(this.glRenderer); // Use a custom
													// renderer
		this.setContentView(this.glView); // This activity sets to GLSurfaceView
	}

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