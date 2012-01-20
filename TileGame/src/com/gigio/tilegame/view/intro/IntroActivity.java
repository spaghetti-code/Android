package com.gigio.tilegame.view.intro;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gigio.tilegame.R;

public class IntroActivity extends Activity
{
	private IntroView view;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.intro);

		this.view = (IntroView) findViewById(R.id.introView);
	}

	// Call back when the activity is going into the background
	@Override
	protected void onPause()
	{
		super.onPause();
		this.view.onPause();
	}

	// Call back after onPause()
	@Override
	protected void onResume()
	{
		super.onResume();
		this.view.onResume();
	}
}
