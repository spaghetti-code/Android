package com.gigio.tilegame.game;

import java.util.ArrayList;
import java.util.List;

import com.gigio.tilegame.R;

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
	 * Default constructor
	 */
	private GameHelper()
	{
		this.sequence = new ArrayList<Integer>(9);
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
		final boolean result = true;
		for (int i = 0; i < this.sequence.size(); i++)
			if (!this.sequence.get(i).equals(Integer.valueOf(i + 1)))
				return false;
		return result;
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
		int resource = R.drawable.zero;
		switch (value)
		{
			case 1:
				resource = R.drawable.one;
				break;
			case 2:
				resource = R.drawable.two;
				break;
			case 3:
				resource = R.drawable.three;
				break;
			case 4:
				resource = R.drawable.four;
				break;
			case 5:
				resource = R.drawable.five;
				break;
			case 6:
				resource = R.drawable.six;
				break;
			case 7:
				resource = R.drawable.seven;
				break;
			case 8:
				resource = R.drawable.eight;
				break;
			case 9:
				resource = R.drawable.nine;
				break;
		}
		return resource;
	}

	/**
	 * @param alreadySelected List of already selected numeric values
	 * @return Random numeric value between 1 and 9
	 */
	public int getRandomValue(final List<Integer> alreadySelected)
	{
		final int min = 1;
		final int max = 9;
		final int value = min + (int) (Math.random() * ((max - min) + 1));
		if (!alreadySelected.contains(Integer.valueOf(value)))
			return value;
		return getRandomValue(alreadySelected);
	}
}
