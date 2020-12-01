package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.network.Network;
import main.utility.Sfmt;

public abstract class EvaluateAlgorithm implements IEvaluate {
	List<Article> articleList;

	protected String name = null;

	public int numOfTimeout=0;

//------------------------------
	public EvaluateAlgorithm() {
		articleList = new ArrayList<>();

	}

	public abstract int startEA(Network network, Sfmt sfmt);

	public String getName() {
		return name;
	}
}
