package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.network.Link;

public class Article implements IEvaluate{
	private Agent master;

	public List<Comment> commentList;

//----------------------------------------
	public Article(Agent master) {
		this.master = master;
		commentList = new ArrayList<Comment>();
	}

//----------------------------------------
	public int getNumOfComment() {
		return commentList.size();
	}
	public int getNumOfMetaComment() {
		int result=0;
		for(Comment comment : commentList) {

			result += comment.metaCommentList.size();
		}
		return result;
	}
	public Agent getMaster() {
		return master;
	}

//----------------------------------------
	public void post() {
		master.addFitness(F);

		master.numOfPost++;

		for(Link link : master.linkList) {

			link.getTo().addFitness(M);
		}
	}
	public void receiveComment(Comment comment) {

		master.numOfReceiveComment++;
		commentList.add(comment);
	}
}
