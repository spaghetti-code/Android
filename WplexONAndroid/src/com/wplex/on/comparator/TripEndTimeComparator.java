package com.wplex.on.comparator;

import java.util.Comparator;

import com.wplex.on.model.Trip;

public class TripEndTimeComparator implements Comparator<Trip>
{

	@Override
	public int compare(Trip trip1, Trip trip2)
	{
		return trip1.getEndTime().compareTo(trip2.getEndTime());
	}

}
