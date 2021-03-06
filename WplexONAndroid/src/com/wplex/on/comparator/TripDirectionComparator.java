package com.wplex.on.comparator;

import java.util.Comparator;

import com.wplex.on.model.Trip;

public class TripDirectionComparator implements Comparator<Trip>
{

	@Override
	public int compare(Trip trip1, Trip trip2)
	{
		return trip1.getItinerary().getDirection()
				.compareTo(trip2.getItinerary().getDirection());
	}

}
