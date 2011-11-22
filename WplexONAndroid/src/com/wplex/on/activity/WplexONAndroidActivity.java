package com.wplex.on.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

import com.wplex.on.R;
import com.wplex.on.model.BlocksModel;

public class WplexONAndroidActivity extends TabActivity
{
	private BlocksModel blocksModel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		if (getLastNonConfigurationInstance() != null)
			this.blocksModel = (BlocksModel) getLastNonConfigurationInstance();
		else
			this.blocksModel = new BlocksModel(getResources().getXml(
					R.xml.itineraries), getResources().getXml(R.xml.blocks));

		final Resources res = getResources(); // Resource object to get Drawables
		final TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, GraphActivity.class);
		final Bundle bundle = new Bundle();
		bundle.putSerializable("blocksModel", this.blocksModel);
		intent.putExtras(bundle);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("graph")
				.setIndicator(getString(R.string.graph),
						res.getDrawable(R.drawable.tab_graph))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, TableActivity.class);
		intent.putExtras(bundle);

		spec = tabHost
				.newTabSpec("table")
				.setIndicator(getString(R.string.table),
						res.getDrawable(R.drawable.tab_table))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);
	}

	@Override
	public Object onRetainNonConfigurationInstance()
	{
		if (this.blocksModel != null)
			return this.blocksModel;
		return super.onRetainNonConfigurationInstance();
	}

}