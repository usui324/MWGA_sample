package main.agent;

import main.entry.Parameter;

public interface IGene extends Parameter{
	/*
	 *・decides distribute in init value
	 *・between 1 - 6 ( about )
	 *・1 : large distribute
	 *・6 : small distribute
	 */
	public final int MAX_NEXT_NORMAL = 4;
}
