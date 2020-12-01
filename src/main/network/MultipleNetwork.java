package main.network;

import java.util.ArrayList;
import java.util.List;

import main.entry.Parameter;
import main.utility.Sfmt;

public class MultipleNetwork implements Parameter{
	private final int LAST_NETWORK = NUM_OF_NETWORK-1;

	public List<Network> networkList;

//----------------------------------------
	public MultipleNetwork() {
		networkList = new ArrayList<>();
	}
	public MultipleNetwork(MultipleNetwork multipleNetwork, Sfmt sfmt) {
		networkList = new ArrayList<>();

		for(Network network : multipleNetwork.networkList) {

			Network newNetwork = new Network(network.getNumber(), network, sfmt);
			networkList.add(newNetwork);
		}
	}

//----------------------------------------
	public int getNumOfNetwork() {
		return networkList.size();
	}

//----------------------------------------
	public void setMultipleNetwork(Network network, Sfmt sfmt) {

		for(int number=0; number<NUM_OF_NETWORK; number++) {
			Network copyNetwork = new Network(number, network, sfmt);
			networkList.add(copyNetwork);
		}
	}
	public void initMultipleNetwork() {

		for(Network network : networkList) {

			network.initNetwork();
		}
	}
}
