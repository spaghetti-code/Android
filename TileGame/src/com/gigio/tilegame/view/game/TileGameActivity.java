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

		GameHelper.getInstance().setActivity(this);
		GameHelper.getInstance().setGameStarted(true);

		//new Thread(this).start();
	}

	//	@Override
	//	public void run()
	//	{
	//		while (true)
	//		{
	//			runOnUiThread(new Runnable()
	//			{
	//				@Override
	//				public void run()
	//				{
	//					if (GameHelper.getInstance().isGameStarted())
	//					{
	//						final TextView lblWin = (TextView) TileGameActivity.this
	//								.findViewById(R.id.lblWin);
	//						lblWin.setText(R.string.game_started);
	//					} else if (GameHelper.getInstance().isGameWon())
	//					{
	//						final TextView lblWin = (TextView) TileGameActivity.this
	//								.findViewById(R.id.lblWin);
	//						lblWin.setText(R.string.win);
	//					}
	//				}
	//			});
	//		}
	//	}

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
	 * Update the views when a game has been won.
	 */
	public void updateViewsOnGameWon()
	{
		this.lblWin.setText(R.string.win);
		this.lblWin.setTextColor(Color.GREEN);
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