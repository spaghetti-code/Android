package com.wplex.on.test;

import java.util.List;

import android.content.res.XmlResourceParser;
import android.test.InstrumentationTestCase;

import com.wplex.on.model.Block;
import com.wplex.on.model.BlocksModel;
import com.wplex.on.model.Itinerary;
import com.wplex.on.model.Trip;
import com.wplex.on.util.TimeUtil;

public class TestLoadBlocksModel extends InstrumentationTestCase
{
	private XmlResourceParser itineraryParser;

	private XmlResourceParser blockParser;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		this.itineraryParser = getInstrumentation().getContext().getResources()
				.getXml(R.xml.itineraries);
		this.blockParser = getInstrumentation().getContext().getResources()
				.getXml(R.xml.blocks);
	}

	public void testPreconditions()
	{
		assertNotNull(this.itineraryParser);
		assertNotNull(this.blockParser);
	}

	public void testLoadBlocksModel()
	{
		final BlocksModel blocksModel = new BlocksModel(this.itineraryParser,
				this.blockParser);

		final List<Itinerary> itineraries = blocksModel.getItineraries();
		assertNotNull(itineraries);
		assertEquals(2, itineraries.size());
		final Itinerary itinerary0 = itineraries.get(0);
		assertItinerary(itinerary0, Long.valueOf(0), "210", "Normal", "Ida");
		final Itinerary itinerary1 = itineraries.get(1);
		assertItinerary(itinerary1, Long.valueOf(1), "210", "Normal", "Volta");

		final List<Block> blocks = blocksModel.getBlocks();
		assertNotNull(blocks);
		assertEquals(1, blocks.size());
		final Block block = blocks.get(0);
		assertNotNull(block);
		assertEquals(Long.valueOf(0), block.getId());
		final List<Trip> trips = block.getTrips();
		assertNotNull(trips);
		assertEquals(2, trips.size());
		assertTrip(trips.get(0), Long.valueOf(0), itinerary0, "04:00", "04:30");
		assertTrip(trips.get(1), Long.valueOf(1), itinerary1, "04:40", "05:20");
	}

	/**
	 * @param id
	 * @param line
	 * @param kind
	 * @param direction
	 */
	private void assertItinerary(final Itinerary itinerary, final Long id,
			final String line, final String kind, final String direction)
	{
		assertNotNull(itinerary);
		assertEquals(id, itinerary.getId());
		assertEquals(line, itinerary.getLine());
		assertEquals(kind, itinerary.getItineraryKind());
		assertEquals(direction, itinerary.getDirection());
	}

	/**
	 * @param trip
	 * @param id
	 * @param itinerary
	 * @param startTime
	 * @param endTime
	 */
	private void assertTrip(final Trip trip, final Long id,
			final Itinerary itinerary, final String startTime,
			final String endTime)
	{
		assertNotNull(trip);
		assertEquals(id, trip.getId());
		assertNotNull(itinerary);
		assertSame(itinerary, trip.getItinerary());
		assertEquals(startTime,
				TimeUtil.convertTimeToString(trip.getStartTime()));
		assertEquals(endTime, TimeUtil.convertTimeToString(trip.getEndTime()));
	}
}
