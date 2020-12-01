package main.network;

import main.agent.Agent;
import main.utility.Sfmt;

public class NmCompleteGragh extends Network {

	public NmCompleteGragh(int number) {
		super(number);
		name = "Complete Gragh";
	}
	public NmCompleteGragh() {
		super();
		name = "Complete Gragh";
	}

//----------------------------------------
	@Override
	public void setNetwork(Sfmt sfmt) {
		// TODO 自動生成されたメソッド・スタブ
		for(int number=0; number<NUM_OF_AGENT; number++) {

			Agent agent = new Agent(number);
			addAgent(agent, sfmt);
		}

		for(Agent agent : agentList) {
			for(Agent bgent : agentList) {
				if(agent == bgent) continue;
				addOneSideLink(agent, bgent);
			}
		}
	}

}
