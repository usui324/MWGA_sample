package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;

public class Comment implements IEvaluate {
	private Agent master;
	private Article article;

	public List<MetaComment> metaCommentList = new ArrayList<>();

//---------------------------------------
	public Comment(Agent master, Article article) {

		this.master = master;
		this.article = article;
	}

//---------------------------------------
	public int getNumOfMetaComment() {
		return metaCommentList.size();
	}
	public Agent getMaster() {
		return master;
	}
	public Article getArticle() {
		return article;
	}

//---------------------------------------
	public void comment() {
//		if(getMaster().getNumber()==X) {
//			System.out.println("comment before:"+ getMaster().getFitness());
//		}
		master.addFitness(C);

//		if(getMaster().getNumber()==X) {
//			System.out.println("comment after:"+ getMaster().getFitness());
//			System.out.println("");
//		}

		master.numOfComment++;

		article.receiveComment(this);
	}
	public void receiveMetaComment(MetaComment metaComment) {

		master.numOfReceiveMetaComment++;
		metaCommentList.add(metaComment);
	}
}
