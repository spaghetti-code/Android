package com.wplex.on.model;

import com.wplex.on.util.TimeUtil;

public class Trip extends Base
{
	private final Itinerary itinerary;

	private final Short startTime;

	private final Short endTime;

	public Trip(Long id, Itinerary itinerary, Short startTime, Short endTime)
	{
		super(id);
		this.itinerary = itinerary;
		this.startTime = startTime;
		this.endTime = endTime;
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
		return this.itinerary.toString() + " - "
				+ TimeUtil.convertTimeToString(this.startTime) + "/"
				+ TimeUtil.convertTimeToString(this.endTime);
	}
}
