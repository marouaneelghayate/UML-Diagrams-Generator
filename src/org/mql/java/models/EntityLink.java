package org.mql.java.models;

public class EntityLink implements Link{

	private int type;
	private String start;
	private String end;
	public static final int EXTENSION = 1, IMPLEMENTATION = 2,AGGREGATION = 3,  COMPOSITION = 4;

	public EntityLink(int type, String start, String end) {
		super();
		this.type = type;
		this.start = start;
		this.end = end;
	}

	public int getType() {
		return type;
	}

	public String getStart() {
		return start;
	}


	public String getEnd() {
		return end;
	}

	
}
