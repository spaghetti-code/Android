package com.wplex.on.activity;

import android.app.Activity;
import android.os.Bundle;

import com.wplex.on.model.BlocksModel;
import com.wplex.on.view.GraphView;

public class GraphActivity extends Activity
{
	private BlocksModel blocksModel;
	private GraphView view;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.blocksModel = (BlocksModel) getIntent().getExtras()
				.getSerializable("blocksModel");
		this.view = new GraphView(this, this.blocksModel);

		setContentView(this.view);
	}
}
