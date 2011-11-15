package com.wplex.on.model;

import com.wplex.on.util.TimeUtil;

public class Trip extends Base
{
	private static final long serialVersionUID = -3223398869529811151L;

	private final Itinerary itinerary;

	private final Short startTime;

	private final Short endTime;

	public Trip(Long id, Itinerary itinerary, Short startTime, Short endTime)
	{
		super(id, EKind.TRIP);
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

	@Override
	public boolean equals(Object o)
	{
		if (!((Base) o).getKind().equals(EKind.TRIP))
			return false;
		return super.equals(o);
	}
}
