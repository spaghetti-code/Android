package com.wplex.on.view;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.wplex.on.activity.TableActivity;
import com.wplex.on.model.EBlockTableColumns;

public class ColumnHeaderView extends TextView
{
	private final EBlockTableColumns column;

	public ColumnHeaderView(final TableActivity activity,
			final LayoutParams params, final EBlockTableColumns column)
	{
		super(activity);
		this.column = column;
		setText(activity.getString(column.getDefaultId()));
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
				setText(activity.isAscending() ? activity.getString(column
						.getUpId()) : activity.getString(column.getDownId()));
				activity.resetHeadersText();
				activity.loadTable();
			}
		});
		activity.registerForContextMenu(this);
	}

	public EBlockTableColumns getColumn()
	{
		return this.column;
	}

}
