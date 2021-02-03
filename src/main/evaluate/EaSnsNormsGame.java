package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.network.Link;
import main.network.Network;
import main.utility.Sfmt;

public class EaSnsNormsGame extends EvaluateAlgorithm{

	public EaSnsNormsGame() {
		super();
		name = "SNS-norms Game";
	}
//--------------------------------
	@Override
	public int startEA(Network network, Sfmt sfmt) {

		List<Article> articleList = new ArrayList<>();

		double prob=0;

		for(int articleNumber=0; articleNumber<NUM_OF_POST; articleNumber++) {
			for(Agent poster : network.agentList) {
				//sequence in a post

				for(Agent agent : network.agentList) {

					agent.setS(sfmt.NextUnif());
				}

				//post phase
				prob = sfmt.NextUnif();
				if(prob > poster.getB().getGeneToDouble()) continue;
				//if(poster.getS() >= 1 - poster.getB().getGeneToDouble()) continue;

				Article article = new Article(poster);
				article.post();
				articleList.add(article);

				//comment phase
				for(Link link : poster.linkList) {

					Agent commenter = link.getTo();

					prob = sfmt.NextUnif();
					if(prob > commenter.getS()) continue;

					prob = sfmt.NextUnif();
					if(prob > commenter.getL().getGeneToDouble()) continue;

					Comment comment = new Comment(commenter, article);
					comment.comment();
				}

				//meta comment phase
				for(Comment comment : article.commentList) {

					Agent metaCommenter = poster;

					prob = sfmt.NextUnif();
					if(prob > metaCommenter.getS()) continue;

					prob = sfmt.NextUnif();
					if(prob > metaCommenter.getL().getGeneToDouble()) continue;

					MetaComment metaComment = new MetaComment(metaCommenter, comment);
					metaComment.metaComment();

				}
			}
		}
		//finish all post

		//calculate reward
		for(Article article : articleList) {

			double commentReward = R * Math.log(article.commentList.size() + 1);

//			if(article.getMaster() == network.agentList.get(X)) {
//				System.out.println("getc bofore:"+ article.getMaster().getFitness());
//			}

			article.getMaster().addFitness(commentReward);

//			if(article.getMaster() == network.agentList.get(X)) {
//				System.out.println("getc number:"+ article.getNumOfComment() + " score:" + commentReward);
//				System.out.println("getc after:"+ article.getMaster().getFitness());
//				System.out.println("");
//			}

			for(Comment comment : article.commentList) {

				//try to get out of log
				double metaCommentReward = RN * Math.log(comment.metaCommentList.size() + 1);
				//double metaCommentReward = RN * comment.metaCommentList.size();

//				if(comment.getMaster() == network.agentList.get(X)) {
//					System.out.println("getm bofore:"+ comment.getMaster().getFitness());
//				}

				comment.getMaster().addFitness(metaCommentReward);

//				if(comment.getMaster() == network.agentList.get(X)) {
//					System.out.println("getm number:"+ comment.getNumOfMetaComment() + " score:" + metaCommentReward);
//					System.out.println("getm after:"+ comment.getMaster().getFitness());
//					System.out.println("");
//				}
			}
		}

//		System.out.println("------------------------------------");

		return numOfTimeout;
	}

//---------------------
	@Override
	public EaSnsNormsGame clone() {
		EaSnsNormsGame ea = null;

		try {
			ea = (EaSnsNormsGame)super.clone();
			ea.name = this.name;


		}catch(Exception e) {
			e.printStackTrace();
		}
		return ea;
	}



}
