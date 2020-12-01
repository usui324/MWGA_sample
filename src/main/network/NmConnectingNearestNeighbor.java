package main.network;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.utility.Sfmt;

public class NmConnectingNearestNeighbor extends Network{

	List<Link> potentialEdgeList;

//----------------------------------------
	public NmConnectingNearestNeighbor(int number) {
		super(number);
		potentialEdgeList = new ArrayList<>();
		name = "Connecting Nearest Neighbor";
	}
	public NmConnectingNearestNeighbor() {
		super();
		potentialEdgeList = new ArrayList<>();
		name = "Connecting Nearest Neighbor";
	}

//----------------------------------------
	public void setNetwork(Sfmt sfmt) {

		for(int number=0; number<NUM_OF_STARTING_AGENT_IN_CNN;number++) {
			Agent agent = new Agent(number);
			addAgent(agent, sfmt);
		}
		for(Agent agent : agentList) {
			for(Agent bgent : agentList) {
				if(agent == bgent) continue;

				addOneSideLink(agent, bgent);
			}
		}

		double uRand;
		int potentialEdgeRand;
		Link potentialEdge;
		int agentNumber;
		while(true) {

			uRand = sfmt.NextUnif();

			//potentialEdge -> edge
			if(uRand < U_IN_CNN) {
				if(potentialEdgeList.size() == 0) continue;

				//select potentialEdge
				potentialEdgeRand = sfmt.NextInt(potentialEdgeList.size());
				potentialEdge = potentialEdgeList.get(potentialEdgeRand);

				//change into edge
				addBothSideLink(potentialEdge.getFrom(), potentialEdge.getTo());

				//remove selected
				potentialEdgeList.remove(potentialEdge);
			}

			//add agent
			else {
				if((agentNumber = agentList.size()) == NUM_OF_AGENT) break;

				Agent agent = new Agent(agentNumber);

				//select one agent in network
				potentialEdgeRand = sfmt.NextInt(agentNumber);
				Agent bgent = agentList.get(potentialEdgeRand);
				for(Link link : bgent.linkList) {

					potentialEdgeList.add(new Link(link));
				}

				addBothSideLink(agent, bgent);
				addAgent(agent, sfmt);
			}

		}
	}



}
