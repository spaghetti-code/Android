package com.wplex.on.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;

import com.wplex.on.comparator.TripBlockIdComparator;
import com.wplex.on.comparator.TripDirectionComparator;
import com.wplex.on.comparator.TripEndTimeComparator;
import com.wplex.on.comparator.TripKindComparator;
import com.wplex.on.comparator.TripLineComparator;
import com.wplex.on.comparator.TripStartTimeComparator;

public class BlocksModel implements Serializable
{
	private static final long serialVersionUID = 2582297848075962641L;

	private final List<Itinerary> itineraries = new ArrayList<Itinerary>();

	private final List<Block> blocks = new ArrayList<Block>();

	private final List<BlockTableRowData> blockTableRowData = new ArrayList<BlockTableRowData>();

	public BlocksModel(final XmlResourceParser itineraryParser,
			final XmlResourceParser blockParser)
	{
		parseItineraries(itineraryParser);
		parseBlocks(blockParser);
	}

	public void parseItineraries(final XmlResourceParser itineraryParser)
	{
		Long id = null;
		String line = null;
		String kind = null;
		String direction = null;
		float red = 1.0f;
		float green = 1.0f;
		float blue = 1.0f;
		boolean parseLine = false;
		boolean parseKind = false;
		boolean parseDirection = false;
		String text;
		String name;
		try
		{
			while (itineraryParser.getEventType() != XmlResourceParser.END_DOCUMENT)
			{
				if (itineraryParser.getEventType() == XmlResourceParser.START_TAG)
				{
					name = itineraryParser.getName();
					if (name.equals("itinerary"))
					{
						id = Long.valueOf(itineraryParser.getAttributeIntValue(
								null, "id", 0));
						red = itineraryParser.getAttributeFloatValue(null,
								"red", 1.0f);
						green = itineraryParser.getAttributeFloatValue(null,
								"green", 1.0f);
						blue = itineraryParser.getAttributeFloatValue(null,
								"blue", 1.0f);
					} else if (name.equals("line"))
						parseLine = true;
					else if (name.equals("kind"))
						parseKind = true;
					else if (name.equals("direction"))
						parseDirection = true;
				} else if (itineraryParser.getEventType() == XmlResourceParser.END_TAG)
				{
					name = itineraryParser.getName();
					if (name.equals("itinerary"))
					{
						this.itineraries.add(new Itinerary(id, line, kind,
								direction, red, green, blue));
						id = null;
						line = null;
						kind = null;
						direction = null;
					} else if (name.equals("line"))
						parseLine = false;
					else if (name.equals("kind"))
						parseKind = false;
					else if (name.equals("direction"))
						parseDirection = false;
				} else if (itineraryParser.getEventType() == XmlResourceParser.TEXT)
				{
					text = itineraryParser.getText();
					if (parseLine)
						line = text;
					else if (parseKind)
						kind = text;
					else if (parseDirection)
						direction = text;
				}
				itineraryParser.next();
			}
			itineraryParser.close();
		} catch (final XmlPullParserException xppe)
		{
			Log.e("XML", "Bad file format");
			xppe.toString();
		} catch (final IOException ioe)
		{
			Log.e("XML", "Unable to read resource file");
			ioe.printStackTrace();
		}
	}

	private void parseBlocks(XmlResourceParser blockParser)
	{
		Long blockId = null;
		Long tripId = null;
		Long itineraryId = null;
		Short startTime = null;
		Short endTime = null;
		Block block = null;
		Trip trip = null;
		Itinerary itinerary = null;
		String text;
		String name;
		boolean parseStartTime = false;
		boolean parseEndTime = false;
		try
		{
			while (blockParser.getEventType() != XmlResourceParser.END_DOCUMENT)
			{
				if (blockParser.getEventType() == XmlResourceParser.START_TAG)
				{
					name = blockParser.getName();
					if (name.equals("block"))
					{
						blockId = Long.valueOf(blockParser
								.getAttributeIntValue(null, "id", 0));
						block = new Block(blockId);
					} else if (name.equals("trip"))
					{
						tripId = Long.valueOf(blockParser.getAttributeIntValue(
								null, "id", 0));
						itineraryId = Long.valueOf(blockParser
								.getAttributeIntValue(null, "itinerary", 0));
						itinerary = getItinerary(itineraryId);
					} else if (name.equals("startTime"))
						parseStartTime = true;
					else if (name.equals("endTime"))
						parseEndTime = true;
				} else if (blockParser.getEventType() == XmlResourceParser.END_TAG)
				{
					name = blockParser.getName();
					if (name.equals("block"))
					{
						this.blocks.add(block);
						blockId = null;
						tripId = null;
						itineraryId = null;
						startTime = null;
						endTime = null;
						block = null;
						trip = null;
						itinerary = null;
					} else if (name.equals("trip"))
					{
						trip = new Trip(tripId, blockId, itinerary, startTime,
								endTime);
						block.addTrip(trip);
					} else if (name.equals("startTime"))
						parseStartTime = false;
					else if (name.equals("endTime"))
						parseEndTime = false;
				} else if (blockParser.getEventType() == XmlResourceParser.TEXT)
				{
					text = blockParser.getText();
					if (parseStartTime)
						startTime = Short.valueOf(text);
					else if (parseEndTime)
						endTime = Short.valueOf(text);
				}
				blockParser.next();
			}
			blockParser.close();
		} catch (final XmlPullParserException xppe)
		{
			Log.e("XML", "Bad file format");
			xppe.toString();
		} catch (final IOException ioe)
		{
			Log.e("XML", "Unable to read resource file");
			ioe.printStackTrace();
		}
	}

	public List<BlockTableRowData> getBlockTableRowData(
			final EBlockTableColumns orderByColumn, final boolean ascending)
	{
		final List<Trip> trips = new ArrayList<Trip>();
		for (final Block block : this.blocks)
			trips.addAll(block.getTrips());
		switch (orderByColumn)
		{
			case BLOCK:
				Collections.sort(trips, new TripBlockIdComparator());
				break;
			case LINE:
				Collections.sort(trips, new TripLineComparator());
				break;
			case KIND:
				Collections.sort(trips, new TripKindComparator());
				break;
			case DIRECTION:
				Collections.sort(trips, new TripDirectionComparator());
				break;
			case START_TIME:
				Collections.sort(trips, new TripStartTimeComparator());
				break;
			case END_TIME:
				Collections.sort(trips, new TripEndTimeComparator());
				break;
			default:
				Collections.sort(trips, new TripBlockIdComparator());
				break;
		}
		this.blockTableRowData.clear();
		if (!ascending)
			Collections.reverse(trips);
		final BlockTableFiltersMap filtersMap = BlockTableFiltersMap
				.getInstance();
		for (final Trip trip : trips)
		{
			if (filtersMap.passAllFilters(trip))
				this.blockTableRowData.add(new BlockTableRowData(String
						.valueOf(trip.getBlockId()), trip.getItinerary()
						.getLine(), trip.getItinerary().getItineraryKind(),
						trip.getItinerary().getDirection(), trip
								.getFormattedStartTime(), trip
								.getFormattedEndTime()));
		}
		return this.blockTableRowData;
	}

	public String[] getFilterItems(final String allString,
			final EBlockTableColumns column)
	{
		final List<String> items = new ArrayList<String>();

		switch (column)
		{
			case BLOCK:
				for (final Block block : this.blocks)
					items.add(String.valueOf(block.getId()));
				break;
			case LINE:
				for (final Itinerary itinerary : this.itineraries)
					if (!items.contains(itinerary.getLine()))
						items.add(itinerary.getLine());
				break;
			case KIND:
				for (final Itinerary itinerary : this.itineraries)
					if (!items.contains(itinerary.getItineraryKind()))
						items.add(itinerary.getItineraryKind());
				break;
			case DIRECTION:
				for (final Itinerary itinerary : this.itineraries)
					if (!items.contains(itinerary.getDirection()))
						items.add(itinerary.getDirection());
				break;
		}
		Collections.sort(items);
		items.add(0, allString);

		final String[] result = new String[items.size()];
		return items.toArray(result);
	}

	private Itinerary getItinerary(final Long id)
	{
		for (final Itinerary local : this.itineraries)
			if (local.getId().equals(id))
				return local;
		return null;
	}

	private Block getBlock(final Long id)
	{
		for (final Block local : this.blocks)
			if (local.getId().equals(id))
				return local;
		return null;
	}

	public List<Itinerary> getItineraries()
	{
		return this.itineraries;
	}

	public List<Block> getBlocks()
	{
		return this.blocks;
	}

}
