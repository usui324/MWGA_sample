package main.entry;

public interface Parameter {
	//Number of Simulation
	public int NUM_OF_SIMULATION = 1;

	//Number of Agent
	public int NUM_OF_AGENT = 20;
	//Number of Generation
	public int GENERATION = 2000;
	//Number of Network
	public int NUM_OF_NETWORK = 30;
	//GeneticAlgorithm
	public double MUTATION_RATE = 0.005;

	//BA
	public int NUM_OF_STARTING_AGENT_IN_BA = 20;
	public int NUM_OF_ADD_EDGE_IN_BA = 20;

	//CNN
	public double U_IN_CNN = 0.9;


	//My Extend
	public int TIME_OUT = 10;

	//Result File
	public int RESULT_GENERATION = 200;


	//Inheritance Pattern
	public enum InheritancePattern {
		DoubleRouletteIncludeSelf,
		SingleRouletteNotIncludeSelf
	}
	public InheritancePattern INHERITANCE = InheritancePattern.SingleRouletteNotIncludeSelf;


	//Dierectory Path
	public final String DIRECTORY_PATH = "C:\\Users\\yuora\\OneDrive\\デスクトップ\\cache\\";
	//public final String DERECTORY_PATH = "C:\\Users\\y.usui\\Desktop\\";

}