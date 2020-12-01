package main.evaluate;

import main.network.Network;
import main.utility.Sfmt;

public abstract class EvaluateAlgorithm implements IEvaluate {

	protected String name = null;

	public int numOfTimeout=0;

//------------------------------
	public EvaluateAlgorithm() {

	}

	public abstract int startEA(Network network, Sfmt sfmt);

	public String getName() {
		return name;
	}

	public void reset() {
		numOfTimeout=0;
	}
}
