package main.network;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.utility.Sfmt;

public class NmBarabasiAlbert extends Network{

	public NmBarabasiAlbert(int number) {
		super(number);
		name = "Barabasi Albert";
	}
	public NmBarabasiAlbert() {
		super();
		name = "Barabasi Albert";
	}

//----------------------------------------
	@Override
	public synchronized void setNetwork(Sfmt sfmt) {

		for(int number=0; number<NUM_OF_STARTING_AGENT_IN_BA; number++) {

			addAgent(new Agent(number), sfmt);
		}
		for(Agent agent : agentList) {
			for(Agent bgent : agentList) {

				if(agent == bgent) continue;
				addOneSideLink(agent, bgent);
			}
		}

		for(int number=NUM_OF_STARTING_AGENT_IN_BA; number<NUM_OF_AGENT; number++) {

			Agent agent = new Agent(number);

			List<Agent> edgeList = new ArrayList<>(agentList);
			int linkSum = 0;
			for(Agent edge : edgeList) {
				linkSum += edge.getNumOfLink();
			}

			int rand;
			Agent remove=null;
			//start select
			for(int numOfEdge=0; numOfEdge<NUM_OF_ADD_EDGE_IN_BA; numOfEdge++) {

				rand = sfmt.NextInt(linkSum);
				for(Agent edge : edgeList) {

					rand -= edge.getNumOfLink();
					if(rand >= 0) continue;

					addBothSideLink(agent, edge);
					remove = edge;
					linkSum -= edge.getNumOfLink();
					break;
				}
				edgeList.remove(remove);
			}
			//finish select

			addAgent(agent, sfmt);
		}
	}


}
