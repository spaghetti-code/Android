package com.wplex.on.model;

public class Itinerary extends Base
{
	private static final long serialVersionUID = 764579242364131539L;

	private final String line;

	private final String itineraryKind;

	private final String direction;

	private final float[] color;

	public Itinerary(final Long id, final String line, final String kind,
			final String direction, final float red, final float green,
			final float blue)
	{
		super(id, EKind.ITINERARY);
		this.line = line;
		this.itineraryKind = kind;
		this.direction = direction;
		this.color = new float[] { red, green, blue };
	}

	public String getLine()
	{
		return this.line;
	}

	public String getItineraryKind()
	{
		return this.itineraryKind;
	}

	public String getDirection()
	{
		return this.direction;
	}

	public float[] getColor()
	{
		return this.color;
	}

	@Override
	public String toString()
	{
		return this.line + " " + this.itineraryKind + " " + this.direction;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!((Base) o).getKind().equals(EKind.ITINERARY))
			return false;
		return super.equals(o);
	}
}
