package com.wplex.on.model;

public class Itinerary extends Base
{
	private final String line;

	private final String kind;

	private final String direction;

	public Itinerary(final Long id, final String line, final String kind,
			final String direction)
	{
		super(id);
		this.line = line;
		this.kind = kind;
		this.direction = direction;
	}

	public String getLine()
	{
		return this.line;
	}

	public String getKind()
	{
		return this.kind;
	}

	public String getDirection()
	{
		return this.direction;
	}

	@Override
	public String toString()
	{
		return this.line + " " + this.kind + " " + this.direction;
	}
}
