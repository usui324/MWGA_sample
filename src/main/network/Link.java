package main.network;

import main.agent.Agent;

public class Link {
	private Agent from;
	private Agent to;

//----------------------------------------
	public Link(Agent from, Agent to) {
		this.from = from;
		this.to = to;
	}
	public Link(Link link) {
		this.from = link.from;
		this.to = link.to;
	}

//----------------------------------------
	public Agent getFrom() {
		return from;
	}
	public Agent getTo() {
		return to;
	}
}
