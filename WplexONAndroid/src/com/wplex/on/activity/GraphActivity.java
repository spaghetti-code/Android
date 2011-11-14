package com.wplex.on.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GraphActivity extends Activity
{
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		final TextView textview = new TextView(this);
		textview.setText("This is the Graph tab"); //$NON-NLS-1$
		setContentView(textview);
	}
}
