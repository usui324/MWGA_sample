package main.evaluate;

import main.entry.Parameter;

public interface IEvaluate extends Parameter {
	//meta reward game parameters
	//post cost
	public static final double F = -3.0;
	//read reward
	public static final double M = 1.0;
	//comment cost
	public static final double C = -2.0;
	//comment reward
	public static final double R = 9.0;
	//meta comment cost
	public static final double CN = -2.0;
	//meta comment reward
	public static final double RN = 9.0;

	//time to post in a game
	public static final int NUM_OF_POST = 4;

	//my extend parameter
	//number of bonus read
	public static final int READ_TIME = 2;
}
