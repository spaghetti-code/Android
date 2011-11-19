package com.wplex.on.model;

import com.wplex.on.R;

public enum EBlockTableColumns
{
	BLOCK(R.string.block, R.string.block_up, R.string.block_down), LINE(
			R.string.line, R.string.line_up, R.string.line_down), KIND(
			R.string.kind, R.string.kind_up, R.string.kind_down), DIRECTION(
			R.string.direction, R.string.direction_up, R.string.direction_down), START_TIME(
			R.string.start, R.string.start_up, R.string.start_down), END_TIME(
			R.string.end, R.string.end_up, R.string.end_down);

	private final int defaultId;

	private final int upId;

	private final int downId;

	private EBlockTableColumns(int defaultId, int upId, int downId)
	{
		this.defaultId = defaultId;
		this.upId = upId;
		this.downId = downId;
	}

	public int getDefaultId()
	{
		return this.defaultId;
	}

	public int getUpId()
	{
		return this.upId;
	}

	public int getDownId()
	{
		return this.downId;
	}

}
