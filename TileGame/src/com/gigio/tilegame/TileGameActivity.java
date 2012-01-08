package com.gigio.tilegame;

import android.app.Activity;
import android.os.Bundle;

public class TileGameActivity extends Activity
{

	//private TileGameView view;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.view = new TileGameView(this);
		setContentView(R.layout.main);
	}
}