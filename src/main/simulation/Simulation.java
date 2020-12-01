package main.simulation;

import main.entry.Parameter;
import main.evaluate.EvaluateAlgorithm;
import main.inheritance.GeneticAlgorithm;
import main.network.MultipleNetwork;
import main.network.Network;
import main.utility.Seed;
import main.utility.Sfmt;
import output.csv.NetworkProgress;
import output.csv.NetworkResult;
import output.file.FileTransfer;

public class Simulation implements Parameter{
	//sfmt
	Sfmt sfmt = new Sfmt(Seed._seeds);

	private final int LAST_NETWORK = NUM_OF_NETWORK - 1;

	Network network;

	int simulationTime;

	EvaluateAlgorithm ea;

	GeneticAlgorithm ga = new GeneticAlgorithm();

	FileTransfer ft;

	//My Extend
	public int timeout=0;

//----------------------------
	public Simulation(Network network, EvaluateAlgorithm ea, int time, FileTransfer ft) {
		this.network = network;
		this.ea = ea;
		simulationTime = time;
		this.ft = ft;


		spendRand(time);
	}

//----------------------------
	private void spendRand(int time) {
		for(int t=0; t<time; t++) {
			sfmt.NextUnif();
		}
	}

	public void startSimulation(){

		System.out.println("Simulation" + simulationTime + " start.");

		//set multiple network
		MultipleNetwork multipleNetwork = new MultipleNetwork();
		multipleNetwork.setMultipleNetwork(network, sfmt);

		//ready to record progress
		NetworkProgress np = new NetworkProgress(simulationTime);
		String recordFileName = null;
		//run mwga
		for(int generation=0; generation<GENERATION; generation++) {

			//evaluate
			for(Network network : multipleNetwork.networkList) {
				timeout += ea.startEA(network, sfmt);
			}

			//record
			recordFileName = np.write(generation, multipleNetwork.networkList.get(LAST_NETWORK));

			//genetic algorithm
			multipleNetwork = ga.startGA(multipleNetwork, sfmt);

//			System.out.println("Generation" + generation + " finish");
		}
		for(Network network : multipleNetwork.networkList) {
			ea.startEA(network, sfmt);
		}
		//finish run mwga

		//transfer record file
		ft.transfer(recordFileName);

		//record result
		NetworkResult nr = new NetworkResult(simulationTime);
		ft.transfer(nr.write(multipleNetwork.networkList.get(LAST_NETWORK)));


		System.out.println("Simulation" + simulationTime + " finish. timeout= " + timeout);
	}

}
