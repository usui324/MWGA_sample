package main.entry;

import main.evaluate.EvaluateAlgorithm;
import main.network.MultipleNetwork;
import main.network.Network;
import main.simulation.Simulation;
import main.utility.Seed;
import main.utility.Sfmt;
import output.file.FileTransfer;
import output.file.SetDirectory;

public class SubLauncher extends Thread implements Parameter{

	Network nm;
	EvaluateAlgorithm ea;
	FileTransfer ft;
	SetDirectory sd;

	Sfmt sfmt;

	private static int simulationTime = 0;

//---------------------------
	public SubLauncher(Network nm, EvaluateAlgorithm ea, FileTransfer ft, SetDirectory sd) {
		this.nm = nm;
		this.ea = ea;
		this.ft = ft;
		this.sd = sd;

		sfmt = new Sfmt(Seed._seeds);
	}

//---------------------------
	@Override
	public void run() {

		System.out.println(this.getName());

		while(true) {
			Simulation simulation;
			if((simulation = setSimulation()) == null) break;

			simulation.startSimulation();
		}
	}


	public synchronized Simulation setSimulation() {

		if(simulationTime >= NUM_OF_SIMULATION) {
			return null;
		}

		nm.setNetwork(sfmt);
		Network network = new Network(-1, nm, sfmt);
		nm.resetNetwork();

		MultipleNetwork mn = new MultipleNetwork();
		mn.setMultipleNetwork(network, sfmt);


		Simulation simulation = new Simulation(mn, ea, simulationTime, sd);
		simulationTime++;

		return simulation;
	}
}
