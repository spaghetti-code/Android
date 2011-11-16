package com.wplex.on.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.wplex.on.R;
import com.wplex.on.model.BlockTableRowData;
import com.wplex.on.model.BlocksModel;

public class TableActivity extends Activity
{
	private BlocksModel blocksModel;

	private int orderByColumn = BlockTableRowData.BLOCK_ID_COLUMN;

	private boolean ascending = true;

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

		loadHeaders();
		loadTable();
	}

	/**
	 * FIXME seria legal criar uma ColumnHeaderView que extenda TextView e encapsule estes
	 * comportamentos...
	 */
	private void loadHeaders()
	{
		final TextView headerBlock = (TextView) findViewById(R.id.headerBlock);
		headerBlock.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.BLOCK_ID_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerBlock
						.setText(TableActivity.this.ascending ? getString(R.string.block_up)
								: getString(R.string.block_down));
				resetHeadersText();
				loadTable();
			}
		});

		final TextView headerLine = (TextView) findViewById(R.id.headerLine);
		headerLine.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.LINE_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerLine
						.setText(TableActivity.this.ascending ? getString(R.string.line_up)
								: getString(R.string.line_down));
				resetHeadersText();
				loadTable();
			}
		});

		final TextView headerKind = (TextView) findViewById(R.id.headerKind);
		headerKind.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.KIND_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerKind
						.setText(TableActivity.this.ascending ? getString(R.string.kind_up)
								: getString(R.string.kind_down));
				resetHeadersText();
				loadTable();
			}
		});

		final TextView headerDirection = (TextView) findViewById(R.id.headerDirection);
		headerDirection.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.DIRECTION_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerDirection
						.setText(TableActivity.this.ascending ? getString(R.string.direction_up)
								: getString(R.string.direction_down));
				resetHeadersText();
				loadTable();
			}
		});

		final TextView headerStartTime = (TextView) findViewById(R.id.headerStartTime);
		headerStartTime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.START_TIME_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerStartTime
						.setText(TableActivity.this.ascending ? getString(R.string.start_up)
								: getString(R.string.start_down));
				resetHeadersText();
				loadTable();
			}
		});

		final TextView headerEndTime = (TextView) findViewById(R.id.headerEndTime);
		headerEndTime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TableActivity.this.orderByColumn = BlockTableRowData.END_TIME_COLUMN;
				TableActivity.this.ascending = !TableActivity.this.ascending;
				headerEndTime
						.setText(TableActivity.this.ascending ? getString(R.string.end_up)
								: getString(R.string.end_down));
				resetHeadersText();
				loadTable();
			}
		});
	}

	private void resetHeadersText()
	{
		final TextView headerBlock = (TextView) findViewById(R.id.headerBlock);
		if (this.orderByColumn != BlockTableRowData.BLOCK_ID_COLUMN)
			headerBlock.setText(R.string.block);

		final TextView headerLine = (TextView) findViewById(R.id.headerLine);
		if (this.orderByColumn != BlockTableRowData.LINE_COLUMN)
			headerLine.setText(R.string.line);

		final TextView headerKind = (TextView) findViewById(R.id.headerKind);
		if (this.orderByColumn != BlockTableRowData.KIND_COLUMN)
			headerKind.setText(R.string.kind);

		final TextView headerDirection = (TextView) findViewById(R.id.headerDirection);
		if (this.orderByColumn != BlockTableRowData.DIRECTION_COLUMN)
			headerDirection.setText(R.string.direction);

		final TextView headerStartTime = (TextView) findViewById(R.id.headerStartTime);
		if (this.orderByColumn != BlockTableRowData.START_TIME_COLUMN)
			headerStartTime.setText(R.string.start);

		final TextView headerEndTime = (TextView) findViewById(R.id.headerEndTime);
		if (this.orderByColumn != BlockTableRowData.END_TIME_COLUMN)
			headerEndTime.setText(R.string.end);
	}

	private void loadTable()
	{
		final TableLayout table = (TableLayout) findViewById(R.id.tableLayoutContent);
		table.removeAllViews();

		final DisplayMetrics displayMetrics = getResources()
				.getDisplayMetrics();
		// TODO colocar este método de conversão DIP --> PIXELS no Utils
		final int w = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				displayMetrics.widthPixels < displayMetrics.heightPixels ? 75
						: 100, displayMetrics);
		final int h = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
		final LayoutParams params = new LayoutParams(w, h);

		TextView txtBlock;
		TextView line;
		TextView kind;
		TextView direction;
		TextView startTime;
		TextView endTime;
		TableRow row;
		final List<BlockTableRowData> blockTableRowData = this.blocksModel
				.getBlockTableRowData(this.orderByColumn, this.ascending);
		for (final BlockTableRowData data : blockTableRowData)
		{
			txtBlock = new TextView(this);
			txtBlock.setLayoutParams(params);
			line = new TextView(this);
			line.setLayoutParams(params);
			kind = new TextView(this);
			kind.setLayoutParams(params);
			direction = new TextView(this);
			direction.setLayoutParams(params);
			startTime = new TextView(this);
			startTime.setLayoutParams(params);
			endTime = new TextView(this);
			endTime.setLayoutParams(params);

			txtBlock.setText(String.valueOf(data.getBlockId()));
			line.setText(data.getLine());
			kind.setText(data.getItineraryKind());
			direction.setText(data.getDirection());
			startTime.setText(data.getStartTime());
			endTime.setText(data.getEndTime());

			row = new TableRow(this);
			row.addView(txtBlock);
			row.addView(line);
			row.addView(kind);
			row.addView(direction);
			row.addView(startTime);
			row.addView(endTime);
			table.addView(row);
		}
	}
}
