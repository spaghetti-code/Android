package com.gigio.tilegame.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		GameHelper.getInstance().setActivity(this);

		// when the start button is clicked, a new game starts
		final Button btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				GameHelper.getInstance().setGameStarted(true);
			}
		});

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
		final TextView lblWin = (TextView) this.findViewById(R.id.lblWin);
		lblWin.setText(R.string.game_started);
		lblWin.setTextColor(Color.LTGRAY);

		final TileGameView gameView = (TileGameView) findViewById(R.id.tileGameView1);
		gameView.getRenderer().resetGame();
	}

	/**
	 * Update the views when a game has been won.
	 */
	public void updateViewsOnGameWon()
	{
		final TextView lblWin = (TextView) this.findViewById(R.id.lblWin);
		lblWin.setText(R.string.win);
		lblWin.setTextColor(Color.GREEN);
	}
}