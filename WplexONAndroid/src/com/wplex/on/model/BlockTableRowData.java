package com.wplex.on.model;

public class BlockTableRowData
{
	private final String blockId;

	private final String line;

	private final String itineraryKind;

	private final String direction;

	private final String startTime;

	private final String endTime;

	public BlockTableRowData(String blockId, String line, String itineraryKind,
			String direction, String startTime, String endTime)
	{
		super();
		this.blockId = blockId;
		this.line = line;
		this.itineraryKind = itineraryKind;
		this.direction = direction;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getBlockId()
	{
		return this.blockId;
	}

	public String getLine()
	{
		return this.line;
	}

	public String getItineraryKind()
	{
		return this.itineraryKind;
	}

	public String getDirection()
	{
		return this.direction;
	}

	public String getStartTime()
	{
		return this.startTime;
	}

	public String getEndTime()
	{
		return this.endTime;
	}

}
