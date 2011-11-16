package com.wplex.on.model;

import com.wplex.on.util.TimeUtil;

public class Trip extends Base
{
	private static final long serialVersionUID = -3223398869529811151L;

	private final Long blockId;

	private final Itinerary itinerary;

	private final Short startTime;

	private final Short endTime;

	public Trip(final Long id, final Long blockId, final Itinerary itinerary,
			final Short startTime, final Short endTime)
	{
		super(id, EKind.TRIP);
		this.blockId = blockId;
		this.itinerary = itinerary;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getBlockId()
	{
		return this.blockId;
	}

	public Itinerary getItinerary()
	{
		return this.itinerary;
	}

	public Short getStartTime()
	{
		return this.startTime;
	}

	public Short getEndTime()
	{
		return this.endTime;
	}

	@Override
	public String toString()
	{
		return this.itinerary.toString() + " - " + getFormattedStartTime()
				+ "/" + getFormattedEndTime();
	}

	public String getFormattedEndTime()
	{
		return TimeUtil.convertTimeToString(this.endTime);
	}

	public String getFormattedStartTime()
	{
		return TimeUtil.convertTimeToString(this.startTime);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!((Base) o).getKind().equals(EKind.TRIP))
			return false;
		return super.equals(o);
	}
}
