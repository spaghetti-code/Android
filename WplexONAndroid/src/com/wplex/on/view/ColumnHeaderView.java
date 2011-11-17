package com.wplex.on.view;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.wplex.on.activity.TableActivity;

public class ColumnHeaderView extends TextView
{
	private final int column;

	private final int defaultId;

	public ColumnHeaderView(final TableActivity activity,
			final LayoutParams params, final int column, final int defaultId,
			final int upId, final int downId)
	{
		super(activity);
		this.column = column;
		this.defaultId = defaultId;
		setText(activity.getString(defaultId));
		setLayoutParams(params);
		setTypeface(Typeface.SERIF, Typeface.BOLD);
		setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				activity.setOrderByColumn(column);
				activity.setAscending(!activity.isAscending());
				setText(activity.isAscending() ? activity.getString(upId)
						: activity.getString(downId));
				activity.resetHeadersText();
				activity.loadTable();
			}
		});
		activity.registerForContextMenu(this);
	}

	public int getColumn()
	{
		return this.column;
	}

	public int getDefaultId()
	{
		return this.defaultId;
	}

}
