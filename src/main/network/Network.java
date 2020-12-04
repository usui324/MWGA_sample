package main.network;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.utility.Sfmt;

public class Network implements INetwork{
	public List<Agent> agentList;
	public List<List<Link>> linkListList;

	protected int number;

	protected String name;

//----------------------------------------
	public Network(int number) {
		agentList = new ArrayList<>();
		linkListList = new ArrayList<>();

		this.number = number;
	}
	public Network() {
		agentList = new ArrayList<>();
		linkListList = new ArrayList<>();

		number = -1;
	}
	public Network(int number, Network network, Sfmt sfmt) {
		agentList = new ArrayList<>();
		linkListList = new ArrayList<>();

		this.number = number;

		if(network.getAgentList() != null) {
			for(Agent agent : network.agentList) {

				addAgent(new Agent(agent.getNumber()), sfmt);
			}
		}
		else {
			System.out.println("No One In Network" + number);
			System.exit(1);
		}

		int fromNumber;
		int toNumber;
		Agent fromAgent;
		Agent toAgent;

		for(Agent agent : network.agentList) {
			for(Link link : agent.linkList) {

				fromNumber = agent.getNumber();
				toNumber = link.getTo().getNumber();
				fromAgent = agentList.get(fromNumber);
				toAgent = agentList.get(toNumber);
				Link copyLink = new Link(fromAgent, toAgent);
				fromAgent.linkList.add(copyLink);
			}
		}
	}

//----------------------------------------
	public synchronized void setNetwork(Sfmt sfmt) {

		System.out.println("This is abstract method in Network.setNetwork");
		System.exit(1);
	}

	public synchronized void resetNetwork() {

		linkListList.clear();
		agentList.clear();

	}

	public String getName() {
		return name;
	}

//----------------------------------------
	public List<Agent> getAgentList(){
		return agentList;
	}
	public List<List<Link>> getLinkListList(){
		return linkListList;
	}
	public int getNumOfAgent() {
		return agentList.size();
	}
	public int getNumOfLink() {
		int result=0;
		for(Agent agent : agentList) {

			result += agent.linkList.size();
		}
		return result/2;/////
	}
	public int getNumber() {
		return number;
	}

//----------------------------------------
	public void addAgent(Agent agent, Sfmt sfmt) {

		agent.setParameter(sfmt);

		agentList.add(agent);
		linkListList.add(agent.linkList);
	}
	private void addLink(Link link) {

		link.getFrom().linkList.add(link);
	}
	public void addOneSideLink(Agent from, Agent to) {

		Link link = new Link(from, to);
		addLink(link);
	}
	public void addBothSideLink(Agent agent, Agent bgent) {

		Link link1 = new Link(agent, bgent);
		addLink(link1);
		Link link2 = new Link(bgent, agent);
		addLink(link2);
	}
	public void initNetwork() {
		for(Agent agent : agentList) {

			agent.setFitness(0);
			agent.setS(0);
		}
	}
}
