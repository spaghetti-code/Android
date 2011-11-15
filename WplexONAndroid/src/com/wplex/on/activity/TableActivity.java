package com.wplex.on.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.wplex.on.R;
import com.wplex.on.model.Block;
import com.wplex.on.model.BlocksModel;
import com.wplex.on.model.Trip;

public class TableActivity extends Activity
{
	private BlocksModel blocksModel;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		this.blocksModel = (BlocksModel) getIntent().getExtras()
				.getSerializable("blocksModel");
		final List<Block> blocks = this.blocksModel.getBlocks();

		final TableLayout table = (TableLayout) findViewById(R.id.tableLayout1);

		//		final TableRow rowTitle = new TableRow(this);
		//		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		//		final TextView title = new TextView(this);
		//		title.setText(getString(R.string.table_title));
		//		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		//		title.setGravity(Gravity.CENTER);
		//		title.setTypeface(Typeface.SERIF, Typeface.BOLD);
		//		final TableRow.LayoutParams params = new TableRow.LayoutParams();
		//		params.span = 6;
		//		rowTitle.addView(title, params);
		//		table.addView(rowTitle);

		Long id;
		List<Trip> trips;
		for (final Block block : blocks)
		{
			id = block.getId();
			trips = block.getTrips();
			for (final Trip trip : trips)
			{

			}
		}
	}
}
