package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.network.Link;
import main.network.Network;
import main.utility.Sfmt;

public class EaMetaRewardGame extends EvaluateAlgorithm {

	public EaMetaRewardGame() {
		super();
		name = "Meta Reward Game";
	}

//------------------------------
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

					Agent commenter = comment.getMaster();
					for(Link link : commenter.linkList) {

						Agent metaCommenter = link.getTo();

						prob = sfmt.NextUnif();
						if(prob > metaCommenter.getS()) continue;

						prob = sfmt.NextUnif();
						if(prob > metaCommenter.getL().getGeneToDouble()) continue;

						MetaComment metaComment = new MetaComment(metaCommenter, comment);
						metaComment.metaComment();

					}
				}
			}
		}
		//finish all post

		//calculate reward
		for(Article article : articleList) {

			double commentReward = R * article.commentList.size();

//			System.out.println("agent"+ article.getMaster().getNumber()+ " bofore:"+ article.getMaster().getFitness());
			article.getMaster().addFitness(commentReward);
//			System.out.println("agent"+ article.getMaster().getNumber()+ " score:"+ article.getNumOfComment() + " score:" + commentReward);
//			System.out.println("agent"+ article.getMaster().getNumber()+ " after:"+ article.getMaster().getFitness());
//			System.out.println("");


			for(Comment comment : article.commentList) {

				double metaCommentReward = RN * comment.metaCommentList.size();

				comment.getMaster().addFitness(metaCommentReward);
			}
		}

		return numOfTimeout;
	}

//---------------------
	@Override
	public EaMetaRewardGame clone() {
		EaMetaRewardGame ea = null;

		try {
			ea = (EaMetaRewardGame)super.clone();
			ea.name = this.name;


		}catch(Exception e) {
			e.printStackTrace();
		}
		return ea;
	}
}
