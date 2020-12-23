package main.evaluate;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.network.Link;
import main.network.Network;
import main.utility.Sfmt;

public class EaMySns extends EvaluateAlgorithm{

	public EaMySns() {
		super();
		name = "My_SNS-norms Game";
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

		//bonus
		for(Article article : articleList) {

			Agent commenter = article.getMaster();

			for(int readTime=0; readTime<READ_TIME; readTime++) {
				//select
				Article bonus;
				int timeout=0;

				do {
					bonus = articleList.get(sfmt.NextInt(articleList.size()));
					timeout++;

					if(timeout > TIME_OUT) break;
				}while(judgeBonusArticle(article, bonus));

				if(timeout > TIME_OUT) {
					numOfTimeout++;
					continue;
				}

				//read
				commenter.addFitness(M);

				//comment
				/*prob = sfmt.NextUnif();
				if(prob > commenter.getL().getGeneToDouble()) continue;

				Comment comment = new Comment(commenter, bonus);
				comment.comment();

				//meta comment
				Agent metaCommenter = bonus.getMaster();

				prob = sfmt.NextUnif();
				if(prob > metaCommenter.getL().getGeneToDouble()) continue;

				MetaComment metaComment = new MetaComment(metaCommenter, comment);
				metaComment.metaComment();*/

			}
		}


		//calculate reward
		for(Article article : articleList) {

			double commentReward = R * Math.log(article.commentList.size() + 1);

			article.getMaster().addFitness(commentReward);

			for(Comment comment : article.commentList) {

				double metaCommentReward = RN * Math.log(comment.metaCommentList.size() + 1);

				comment.getMaster().addFitness(metaCommentReward);
			}
		}

		return numOfTimeout;
	}

	private boolean judgeBonusArticle(Article article, Article bonus) {

		//article == bonus -> false
		if(article == bonus) return true;

		for(Link link : article.getMaster().linkList) {
			if(link.getTo() == bonus.getMaster()) {
				return true;
			}
		}

		return false;
	}

//---------------------
	@Override
	public EaMySns clone() {
		EaMySns ea = null;

		try {
			ea = (EaMySns)super.clone();
			ea.name = this.name;


		}catch(Exception e) {
			e.printStackTrace();
		}
		return ea;
	}

}
