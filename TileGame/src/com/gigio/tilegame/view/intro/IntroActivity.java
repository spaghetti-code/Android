package com.gigio.tilegame.view.intro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.gigio.tilegame.R;
import com.gigio.tilegame.game.GameHelper;
import com.gigio.tilegame.view.game.TileGameActivity;
import com.gigio.tilegame.view.options.OptionsActivity;

public class IntroActivity extends Activity
{
	private IntroView view;

	private static final int HELP_DIALOG = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.intro);

		this.view = (IntroView) findViewById(R.id.introView);

		// start button
		final ImageButton btnStart = (ImageButton) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final Intent intent = new Intent(IntroActivity.this,
						TileGameActivity.class);
				startActivity(intent);
			}
		});

		// help button
		final ImageButton btnHelp = (ImageButton) findViewById(R.id.btnHelp);
		btnHelp.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showDialog(HELP_DIALOG);
			}
		});

		// options button
		final ImageButton btnOptions = (ImageButton) findViewById(R.id.btnOptions);
		btnOptions.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final Intent intent = new Intent(IntroActivity.this,
						OptionsActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Dialog dialog = null;
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id)
		{
			case HELP_DIALOG:
				builder.setMessage(R.string.help).setPositiveButton("Ok",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{
								dialog.cancel();
							}
						});
				dialog = builder.create();
				break;
		}
		return dialog;
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

		// to stop the timer any time this activity is called
		GameHelper.getInstance().stopTimer(false);
	}
}
