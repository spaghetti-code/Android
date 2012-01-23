package com.gigio.tilegame.view.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gigio.tilegame.R;
import com.gigio.tilegame.game.GameHelper;

/**
 * Tile game activity.
 * 
 * @author Francesco Bertolino
 */
public class TileGameActivity extends Activity
{
	private TileGameView view;

	private TextView lblWin;

	private TextView lblTries;

	private TextView lblTimer;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game);

		this.view = (TileGameView) findViewById(R.id.tileGameView1);
		this.lblWin = (TextView) this.findViewById(R.id.lblWin);
		this.lblTries = (TextView) this.findViewById(R.id.lblTriesValue);
		this.lblTimer = (TextView) this.findViewById(R.id.lblTimeValue);

		GameHelper.getInstance().setActivity(this);

		if (savedInstanceState != null)
		{
			this.lblTries.setText(savedInstanceState.getCharSequence("tries"));
			this.lblTimer.setText(savedInstanceState.getCharSequence("timer"));
			this.view.continueGame();
		} else
		{
			this.view.resetGame();
			GameHelper.getInstance().setGameStarted(true);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putCharSequence("tries", this.lblTries.getText());
		outState.putCharSequence("timer", this.lblTimer.getText());
	}

	/**
	 * Update the views when a game has been started.
	 */
	public void updateViewsOnGameStarted()
	{
		this.lblWin.setText(R.string.game_started);
		this.lblWin.setTextColor(Color.LTGRAY);

		this.view.getRenderer().resetGame();
	}

	/**
	 * Updates the views when a game has been won.
	 */
	public void updateViewsOnGameWon()
	{
		this.lblWin.setText(R.string.win);
		this.lblWin.setTextColor(Color.GREEN);
	}

	/**
	 * Updates the numer of tries on screen.
	 * 
	 * @param tries
	 */
	public void updateTriesNumber(final int tries)
	{
		this.lblTries.setText(String.valueOf(tries));
	}

	/**
	 * Updates the timer value on screen.
	 * 
	 * @param value
	 */
	public void updateTimer(final String value)
	{
		this.lblTimer.setText(value);
	}

	// Call back when the activity is going into the background
	@Override
	protected void onPause()
	{
		super.onPause();
		this.view.onPause();
		GameHelper.getInstance().stopTimer(true);
	}

	// Call back after onPause()
	@Override
	protected void onResume()
	{
		super.onResume();
		this.view.onResume();
		GameHelper.getInstance().startTimer();
	}
}