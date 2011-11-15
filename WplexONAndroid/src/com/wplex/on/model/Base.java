package com.wplex.on.model;

import java.io.Serializable;

public class Base implements Serializable
{
	private static final long serialVersionUID = 9065892978231668208L;

	private final Long id;

	private final EKind kind;

	public Base(final Long id, final EKind kind)
	{
		super();
		this.id = id;
		this.kind = kind;
	}

	public Long getId()
	{
		return this.id;
	}

	public EKind getKind()
	{
		return this.kind;
	}

	@Override
	public boolean equals(Object o)
	{
		return this.id.equals(((Base) o).getId());
	}
}
