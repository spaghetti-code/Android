package com.wplex.on.model;

import java.util.ArrayList;
import java.util.List;

public class Block extends Base
{
	private final List<Trip> trips = new ArrayList<Trip>();

	public Block(final Long id)
	{
		super(id);
	}

	public List<Trip> getTrips()
	{
		return this.trips;
	}

	public void addTrip(final Trip trip)
	{
		Trip localTrip;
		int index = -1;
		for (int i = this.trips.size() - 1; i >= 0; i--)
		{
			localTrip = this.trips.get(i);
			if (localTrip.getEndTime() <= trip.getStartTime())
			{
				index = i + 1;
				break;
			}
		}
		if (index == -1)
			this.trips.add(0, trip);
		else if (index >= this.trips.size() - 1)
			this.trips.add(trip);
		else
			this.trips.add(index, trip);
	}

	public void removeTrip(final Trip trip)
	{
		this.trips.remove(trip);
	}

	@Override
	public String toString()
	{
		return "" + getId();
	}
}
