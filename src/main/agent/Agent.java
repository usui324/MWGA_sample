package main.agent;

import java.util.ArrayList;
import java.util.List;

import main.network.Link;
import main.utility.Sfmt;

public class Agent implements IAgent{
	private Gene parameterB;
	private Gene parameterL;

	private double fitness=0;

	private double S=0;

	//location in network
	private int number;

	//friendList
	public List<Link> linkList;

	public int numOfPost = 0;
	public int numOfComment = 0;
	public int numOfMetaComment = 0;
	public int numOfReceiveComment = 0;
	public int numOfReceiveMetaComment = 0;

//----------------------------------------
	public Agent(int number) {
		parameterB = new Gene(DIGIT_B);
		parameterL = new Gene(DIGIT_L);
		this.number = number;
		linkList = new ArrayList<>();
	}

//----------------------------------------
	public Gene getB() {
		return parameterB;
	}
	public Gene getL() {
		return parameterL;
	}
	public double getFitness() {
		return fitness;
	}
	public double getS() {
		return S;
	}
	public int getNumber() {
		return number;
	}
	public int getNumOfLink() {
		return linkList.size();
	}

//----------------------------------------
	public void setParameter(Sfmt sfmt) {
		parameterB.setGene(sfmt);
		parameterL.setGene(sfmt);
	}
	public void setFitness(double value) {
		fitness = value;
	}
	public void addFitness(double addValue) {
		fitness += addValue;
	}
	public void setS(double value) {
		S = value;
	}

}
