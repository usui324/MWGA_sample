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

//		if(getMaster().getNumber()==X) {
//			System.out.println("post before:"+ getMaster().getFitness());
//		}
		master.addFitness(F);

//		if(getMaster().getNumber()==X) {
//			System.out.println("post after:"+ getMaster().getFitness());
//			System.out.println("");
//		}

		master.numOfPost++;

		for(Link link : master.linkList) {

//			if(link.getTo().getNumber()==X) {
//				System.out.println("read before:"+ link.getTo().getFitness());
//			}

			link.getTo().addFitness(M);

//			if(link.getTo().getNumber()==X) {
//				System.out.println("read after:"+ link.getTo().getFitness());
//				System.out.println("");
//			}
		}
	}
	public void receiveComment(Comment comment) {

		master.numOfReceiveComment++;
		commentList.add(comment);
	}
}
