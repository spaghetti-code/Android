package com.wplex.on.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;

public class BlocksModel
{
	private final List<Itinerary> itineraries = new ArrayList<Itinerary>();

	private final List<Block> blocks = new ArrayList<Block>();

	public BlocksModel(final XmlResourceParser itineraryParser,
			final XmlResourceParser blockParser)
	{
		parseItineraries(itineraryParser);
	}

	public void parseItineraries(final XmlResourceParser itineraryParser)
	{
		Long id = null;
		String line = null;
		String kind = null;
		String direction = null;
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
						id = Long.valueOf(itineraryParser.getAttributeIntValue(
								null, "id", 0));
					else if (name.equals("line"))
						parseLine = true;
					else if (name.equals("kind"))
						parseKind = true;
					else if (name.equals("direction"))
						parseDirection = true;
				} else if (itineraryParser.getEventType() == XmlResourceParser.END_TAG)
				{
					name = itineraryParser.getName();
					if (name.equals("itinerary"))
						this.itineraries.add(new Itinerary(id, line, kind,
								direction));
					else if (name.equals("line"))
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
}
