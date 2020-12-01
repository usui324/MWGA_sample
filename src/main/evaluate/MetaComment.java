package main.evaluate;

import main.agent.Agent;

public class MetaComment implements IEvaluate {
	Agent master;
	Comment comment;

//---------------------------------------
	public MetaComment(Agent master, Comment comment) {

		this.master = master;
		this.comment = comment;
	}

//---------------------------------------
	public Agent getMaster() {
		return master;
	}
	public Comment getComment() {
		return comment;
	}
//---------------------------------------
	public void metaComment() {

		master.addFitness(CN);

		master.numOfMetaComment++;

		comment.receiveMetaComment(this);
	}
}
