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

	MultipleNetwork multipleNetwork;

	int simulationTime;

	EvaluateAlgorithm ea;

	GeneticAlgorithm ga = new GeneticAlgorithm(INHERITANCE);

	FileTransfer ft;

	//My Extend
	public int timeout=0;

//----------------------------
	public Simulation(MultipleNetwork multipleNetwork, EvaluateAlgorithm ea, int time, FileTransfer ft) {
		this.multipleNetwork = multipleNetwork;
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

		//ready to record progress
		NetworkProgress np = new NetworkProgress(simulationTime);
		String recordFileName = null;


//		for(Network n : multipleNetwork.networkList) {
//			for(Agent a : n.agentList) {
//				System.out.println("Agent" + a.getNumber() + " B:" + a.getB().getGeneToInt() + " L:" + a.getL().getGeneToInt());
//			}
//		}


		//run mwga
		for(int generation=0; generation<GENERATION; generation++) {

			//evaluate
			for(Network network : multipleNetwork.networkList) {
				timeout += ea.startEA(network, sfmt);
				ea.reset();
			}

			//progress record
			recordFileName = np.write(generation, multipleNetwork.networkList.get(LAST_NETWORK));

			//result record
			if(generation%RESULT_GENERATION == 0) {

				NetworkResult nr = new NetworkResult(simulationTime, generation);
				ft.transfer(nr.write(multipleNetwork.networkList.get(LAST_NETWORK)));
			}

			//genetic algorithm
			multipleNetwork = ga.startGA(multipleNetwork, sfmt);

			System.out.println("Simulation"+ simulationTime + " Generation" + generation + " finish");
		}
		for(Network network : multipleNetwork.networkList) {
			ea.startEA(network, sfmt);
			ea.reset();
		}
		//finish run mwga

		//transfer record file
		ft.transfer(recordFileName);

		//record result
		NetworkResult nr = new NetworkResult(simulationTime, GENERATION);
		ft.transfer(nr.write(multipleNetwork.networkList.get(LAST_NETWORK)));


		System.out.println("Simulation" + simulationTime + " finish. timeout= " + timeout);
	}

}
