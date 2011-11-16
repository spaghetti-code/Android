package com.wplex.on.comparator;

import java.util.Comparator;

import com.wplex.on.model.Trip;

public class TripKindComparator implements Comparator<Trip>
{

	@Override
	public int compare(Trip trip1, Trip trip2)
	{
		return trip1.getItinerary().getItineraryKind()
				.compareTo(trip2.getItinerary().getItineraryKind());
	}

}
