package main.network;

import java.util.List;

import main.agent.Agent;
import main.utility.Sfmt;

public class NmWattsStrogatz extends Network{

	public NmWattsStrogatz(int number) {
		super(number);
		name = "Watts Strogatz";
	}
	public NmWattsStrogatz() {
		super();
		name = "Watts Strogatz";
	}

//----------------------------------------
	@Override
	public synchronized void setNetwork(Sfmt sfmt) {

		if(2*K_IN_WS >= NUM_OF_AGENT) {
			System.out.println("Network Error in WS.");
			System.exit(1);
		}

		for(int number=0; number<NUM_OF_AGENT; number++) {
			Agent agent = new Agent(number);
			addAgent(agent, sfmt);
		}
		for(int number=0; number<NUM_OF_AGENT; number++) {
			for(int distance=1; distance<=K_IN_WS; distance++) {

				addRingLink(number, distance);
			}
		}//finish init network

		for(List<Link> linkList : linkListList) {
			int[] judgeResult = new int[linkList.size()];
			for(Link link : linkList) {

				judgeResult[linkList.indexOf(link)] = reconnectJudge(link, sfmt);
			}
			for(int index=linkList.size()-1; index>=0; index--) {

				if(judgeResult[index] == 0) continue;
				reconnect(linkList.get(index), sfmt);
			}
		}
	}

	private void addRingLink(int number, int distance) {

		Agent agent1 = agentList.get(number);
		int agent2Number = (number + distance) % NUM_OF_AGENT;
		Agent agent2 = agentList.get(agent2Number);

		addBothSideLink(agent1, agent2);
	}
	private int reconnectJudge(Link link, Sfmt sfmt) {

		double beta = sfmt.NextUnif();
		if(beta >= BETA_IN_WS) return 0;

		int fromNumber = link.getFrom().getNumber();
		int toNumber = link.getTo().getNumber();
		int distance = toNumber-fromNumber;
		if(distance<0) distance+=NUM_OF_AGENT;
		if(distance > K_IN_WS) return 0;

		return 1;
	}
	private void reconnect(Link link, Sfmt sfmt) {

		Agent agent1 = link.getFrom();
		Agent agent2 = link.getTo();

		agent1.linkList.remove(link);
		int removeNumber=-1;
		for(Link link2 : agent2.linkList) {
			if(link2.getTo() == agent1) {
				removeNumber = agent2.linkList.indexOf(link2);
			}
		}

		agent2.linkList.remove(removeNumber);
		//finish remove


		int newToNumber=0;
		while(true) {

			newToNumber = sfmt.NextInt(NUM_OF_AGENT);
			agent2 = agentList.get(newToNumber);

			if(agent2 == agent1) continue;
			int alreadyJudge=0;
			for(Link already: agent1.linkList) {
				if(agent2 == already.getTo()) alreadyJudge = 1;
			}
			if(alreadyJudge==0) break;
		}
		addBothSideLink(agent1, agent2);

	}
}
