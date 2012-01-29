package com.gigio.tilegame.game;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.gigio.tilegame.R;
import com.gigio.tilegame.view.game.TileGameActivity;
import com.gigio.tilegame.view.options.Difficulty;

/**
 * Tile game helper class.
 * 
 * @author Francesco Bertolino
 */
public class GameHelper
{
	/**
	 * Singleton
	 */
	private static GameHelper instance;

	/**
	 * Sequence of chosen numbers
	 */
	private final List<Integer> sequence;

	/**
	 * Sequence of tile numbers
	 */
	private List<Integer> tileNumbers;

	/**
	 * True if a new game has been started
	 */
	private boolean gameStarted = false;

	/**
	 * True if the game has been won
	 */
	private boolean gameWon = false;

	/**
	 * Game activity
	 */
	private TileGameActivity activity;

	/**
	 * Current number of tries
	 */
	private int tries = 1;

	/**
	 * Game difficulty level:
	 * - beginner = 4
	 * - easy = 6
	 * - normal = 9
	 * - expert = 12
	 * - master = 16 
	 */
	private Difficulty difficulty = Difficulty.NORMAL;

	/**
	 * Constant: game started
	 */
	public static final int GAME_STARTED = 0;

	/**
	 * Constant: game won
	 */
	public static final int GAME_WON = 1;

	/**
	 * Constant: add try
	 */
	public static final int ADD_TRY = 2;

	/**
	 * Default constructor
	 */
	private GameHelper()
	{
		this.sequence = new ArrayList<Integer>();
	}

	/**
	 * @return singleton
	 */
	public static GameHelper getInstance()
	{
		if (instance == null)
			instance = new GameHelper();
		return instance;
	}

	/**
	 * @return True if the number sequence is correct
	 */
	public boolean assertSequence()
	{
		for (int i = 0; i < this.sequence.size(); i++)
			if (!this.sequence.get(i).equals(Integer.valueOf(i + 1)))
			{
				this.handler.sendEmptyMessage(ADD_TRY);
				return false;
			}
		if (this.sequence.size() == this.difficulty.getTiles())
			setGameWon(true);
		return true;
	}

	/**
	 * Adds a value to number sequence.
	 * 
	 * @param value
	 */
	public void updateSequence(final int value)
	{
		this.sequence.add(value);
	}

	/**
	 * Clears number sequence.
	 */
	public void clearSequence()
	{
		this.sequence.clear();
	}

	/**
	 * @param value
	 * @return Texture resource corresponding to informed numeric value
	 */
	public int getNumberTextureResource(final int value)
	{
		int resource = R.drawable.t1;
		switch (value)
		{
			case 1:
				resource = R.drawable.t1;
				break;
			case 2:
				resource = R.drawable.t2;
				break;
			case 3:
				resource = R.drawable.t3;
				break;
			case 4:
				resource = R.drawable.t4;
				break;
			case 5:
				resource = R.drawable.t5;
				break;
			case 6:
				resource = R.drawable.t6;
				break;
			case 7:
				resource = R.drawable.t7;
				break;
			case 8:
				resource = R.drawable.t8;
				break;
			case 9:
				resource = R.drawable.t9;
				break;
			case 10:
				resource = R.drawable.t10;
				break;
			case 11:
				resource = R.drawable.t11;
				break;
			case 12:
				resource = R.drawable.t12;
				break;
			case 13:
				resource = R.drawable.t13;
				break;
			case 14:
				resource = R.drawable.t14;
				break;
			case 15:
				resource = R.drawable.t15;
				break;
			case 16:
				resource = R.drawable.t16;
				break;
		}
		return resource;
	}

	/**
	 * @param value
	 * @return Back resource corresponding to informed numeric value
	 */
	public int getBackTextureResource(final int value)
	{
		int resource = R.drawable.back_neutra;
		if (this.difficulty.equals(Difficulty.BEGINNER))
		{
			switch (value)
			{
				case 1:
					resource = R.drawable.back_1;
					break;
				case 2:
					resource = R.drawable.back_3;
					break;
				case 3:
					resource = R.drawable.back_7;
					break;
				case 4:
					resource = R.drawable.back_9;
					break;
			}
		} else if (this.difficulty.equals(Difficulty.EASY))
		{
			switch (value)
			{
				case 1:
					resource = R.drawable.back_1;
					break;
				case 2:
					resource = R.drawable.back_2;
					break;
				case 3:
					resource = R.drawable.back_3;
					break;
				case 4:
					resource = R.drawable.back_7;
					break;
				case 5:
					resource = R.drawable.back_8;
					break;
				case 6:
					resource = R.drawable.back_9;
					break;
			}
		} else if (this.difficulty.equals(Difficulty.NORMAL))
		{
			switch (value)
			{
				case 1:
					resource = R.drawable.back_1;
					break;
				case 2:
					resource = R.drawable.back_2;
					break;
				case 3:
					resource = R.drawable.back_3;
					break;
				case 4:
					resource = R.drawable.back_4;
					break;
				case 5:
					resource = R.drawable.back_5;
					break;
				case 6:
					resource = R.drawable.back_6;
					break;
				case 7:
					resource = R.drawable.back_7;
					break;
				case 8:
					resource = R.drawable.back_8;
					break;
				case 9:
					resource = R.drawable.back_9;
					break;
			}
		} else if (this.difficulty.equals(Difficulty.EXPERT))
		{
			switch (value)
			{
				case 1:
					resource = R.drawable.back_neutra;
					break;
				case 2:
					resource = R.drawable.back_neutra;
					break;
				case 3:
					resource = R.drawable.back_neutra;
					break;
				case 4:
					resource = R.drawable.back_1;
					break;
				case 5:
					resource = R.drawable.back_2;
					break;
				case 6:
					resource = R.drawable.back_3;
					break;
				case 7:
					resource = R.drawable.back_7;
					break;
				case 8:
					resource = R.drawable.back_8;
					break;
				case 9:
					resource = R.drawable.back_9;
					break;
				case 10:
					resource = R.drawable.back_neutra;
					break;
				case 11:
					resource = R.drawable.back_neutra;
					break;
				case 12:
					resource = R.drawable.back_neutra;
					break;
			}
		} else if (this.difficulty.equals(Difficulty.MASTER))
		{
			switch (value)
			{
				case 1:
					resource = R.drawable.back_neutra;
					break;
				case 2:
					resource = R.drawable.back_neutra;
					break;
				case 3:
					resource = R.drawable.back_neutra;
					break;
				case 4:
					resource = R.drawable.back_neutra;
					break;
				case 5:
					resource = R.drawable.back_neutra;
					break;
				case 6:
					resource = R.drawable.back_1;
					break;
				case 7:
					resource = R.drawable.back_3;
					break;
				case 8:
					resource = R.drawable.back_neutra;
					break;
				case 9:
					resource = R.drawable.back_neutra;
					break;
				case 10:
					resource = R.drawable.back_7;
					break;
				case 11:
					resource = R.drawable.back_9;
					break;
				case 12:
					resource = R.drawable.back_neutra;
					break;
				case 13:
					resource = R.drawable.back_neutra;
					break;
				case 14:
					resource = R.drawable.back_neutra;
					break;
				case 15:
					resource = R.drawable.back_neutra;
					break;
				case 16:
					resource = R.drawable.back_neutra;
					break;
			}
		}

		return resource;
	}

	/**
	 * @param alreadySelected List of already selected numeric values
	 * @return Random numeric value between 1 and max
	 */
	public int getRandomValue(final List<Integer> alreadySelected)
	{
		final int min = 1;
		final int max = this.difficulty.getTiles();
		final int value = min + (int) (Math.random() * ((max - min) + 1));
		if (!alreadySelected.contains(Integer.valueOf(value)))
			return value;
		return getRandomValue(alreadySelected);
	}

	/**
	 * Used to handle view components update, because a Handler runs
	 * on the UI thread and thus avoids the error:
	 * "Only the original thread that created a view hierarchy can touch its views."
	 */
	private final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case GAME_STARTED:
					GameHelper.this.activity.updateViewsOnGameStarted();
					break;
				case GAME_WON:
					GameHelper.this.activity.updateViewsOnGameWon();
					break;
				case ADD_TRY:
					GameHelper.this.tries++;
					GameHelper.this.activity
							.updateTriesNumber(GameHelper.this.tries);
					break;
			}
		}
	};

	/**
	 * @return gameStarted
	 */
	public boolean isGameStarted()
	{
		return this.gameStarted;
	}

	/**
	 * @param gameStarted
	 */
	public void setGameStarted(boolean gameStarted)
	{
		this.gameStarted = gameStarted;
		if (gameStarted)
		{
			clearSequence();
			this.tries = 1;
			this.handler.sendEmptyMessage(GAME_STARTED);
			startTimer();
		}
	}

	/**
	 * @return gameWon
	 */
	public boolean isGameWon()
	{
		return this.gameWon;
	}

	/**
	 * @param gameWon
	 */
	public void setGameWon(boolean gameWon)
	{
		this.gameWon = gameWon;
		if (gameWon)
		{
			this.tries = 1;
			this.handler.sendEmptyMessage(GAME_WON);
			stopTimer(false);
		}
	}

	/**
	 * @param activity
	 */
	public void setActivity(TileGameActivity activity)
	{
		this.activity = activity;
	}

	/**
	 * @return sequence
	 */
	public List<Integer> getSequence()
	{
		return this.sequence;
	}

	/**
	 * @return difficulty
	 */
	public Difficulty getDifficulty()
	{
		return this.difficulty;
	}

	/**
	 * @param difficulty
	 */
	public void setDifficulty(Difficulty difficulty)
	{
		this.difficulty = difficulty;
	}

	/**
	 * @return tileNumbers
	 */
	public List<Integer> getTileNumbers()
	{
		return this.tileNumbers;
	}

	/**
	 * @param tileNumbers
	 */
	public void setTileNumbers(List<Integer> tileNumbers)
	{
		this.tileNumbers = tileNumbers;
	}

	/////////////////////// TIMER

	/**
	 * Handler for the timer task (runs on main thread)
	 */
	private final Handler timerHandler = new Handler();

	/**
	 * Start time for counting
	 */
	private long startTime;

	/**
	 * Elapsed time
	 */
	private long elapsedTime;

	/**
	 * Time elapsed when the game is paused
	 */
	private long elapsedTimeAtPause;

	/**
	 * Starts the timer
	 */
	public void startTimer()
	{
		this.startTime = System.currentTimeMillis();
		// removes any existing callbacks from the message queue, to be sure
		this.timerHandler.removeCallbacks(this.timerTask);
		// tells the handler to run the task once 100ms from now
		this.timerHandler.postDelayed(this.timerTask, 100);
	}

	/**
	 * Stops the timer
	 * 
	 * @param pause True if it's just a pause
	 */
	public void stopTimer(final boolean pause)
	{
		// removes any callbacks
		this.timerHandler.removeCallbacks(this.timerTask);
		if (pause)
			this.elapsedTimeAtPause = this.elapsedTime;
		else
			this.elapsedTimeAtPause = 0;
	}

	/**
	 * Timer task
	 */
	private final Runnable timerTask = new Runnable()
	{
		@Override
		public void run()
		{
			final long now = System.currentTimeMillis();
			GameHelper.this.elapsedTime = (now - GameHelper.this.startTime)
					+ GameHelper.this.elapsedTimeAtPause;
			int seconds = (int) (GameHelper.this.elapsedTime / 1000);
			final int minutes = seconds / 60;
			seconds = seconds % 60;

			// adjusts to mm:ss format and updates interface
			final String min = (minutes < 10 ? "0" + minutes : "" + minutes);
			final String sec = (seconds < 10 ? "0" + seconds : "" + seconds);
			GameHelper.this.activity.updateTimer(min + ":" + sec);

			// sets next callback on the message queue, 100ms from now
			GameHelper.this.timerHandler.postDelayed(this, 100);
		}
	};
}
