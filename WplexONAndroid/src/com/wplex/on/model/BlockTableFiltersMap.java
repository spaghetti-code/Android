package com.wplex.on.model;

import java.util.HashMap;
import java.util.Map;

public class BlockTableFiltersMap
{
	private final Map<EBlockTableColumns, Object> filters = new HashMap<EBlockTableColumns, Object>(
			4);

	private static BlockTableFiltersMap instance;

	private BlockTableFiltersMap()
	{
		resetFilters();
	}

	public static BlockTableFiltersMap getInstance()
	{
		if (instance == null)
			instance = new BlockTableFiltersMap();
		return instance;
	}

	public void resetFilters()
	{
		this.filters.clear();
		this.filters.put(EBlockTableColumns.BLOCK, null);
		this.filters.put(EBlockTableColumns.LINE, null);
		this.filters.put(EBlockTableColumns.KIND, null);
		this.filters.put(EBlockTableColumns.DIRECTION, null);
	}

	public boolean passAllFilters(final Trip trip)
	{
		return passFilterBlock(trip) && passFilterLine(trip)
				&& passFilterKind(trip) && passFilterDirection(trip);
	}

	public boolean passFilterBlock(final Trip trip)
	{
		return getFilter(EBlockTableColumns.BLOCK) == null
				|| getFilter(EBlockTableColumns.BLOCK)
						.equals(trip.getBlockId());
	}

	public boolean passFilterLine(final Trip trip)
	{
		return getFilter(EBlockTableColumns.LINE) == null
				|| getFilter(EBlockTableColumns.LINE).equals(
						trip.getItinerary().getLine());
	}

	public boolean passFilterKind(final Trip trip)
	{
		return getFilter(EBlockTableColumns.KIND) == null
				|| getFilter(EBlockTableColumns.KIND).equals(
						trip.getItinerary().getItineraryKind());
	}

	public boolean passFilterDirection(final Trip trip)
	{
		return getFilter(EBlockTableColumns.DIRECTION) == null
				|| getFilter(EBlockTableColumns.DIRECTION).equals(
						trip.getItinerary().getDirection());
	}

	public void setFilter(final EBlockTableColumns column, final Object value)
	{
		this.filters.put(column, value);
	}

	public Object getFilter(final EBlockTableColumns column)
	{
		return this.filters.get(column);
	}
}
