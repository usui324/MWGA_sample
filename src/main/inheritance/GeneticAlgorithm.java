package main.inheritance;

import java.util.ArrayList;
import java.util.List;

import main.agent.Agent;
import main.agent.Gene;
import main.network.MultipleNetwork;
import main.network.Network;
import main.utility.Sfmt;

public class GeneticAlgorithm implements IGeneticAlgorithm{

	MultipleNetwork thisGeneration;
	MultipleNetwork nextGeneration;


	public GeneticAlgorithm() {
	}

//-------------------------------
	public MultipleNetwork startGA(MultipleNetwork multipleNetwork, Sfmt sfmt) {

		thisGeneration = multipleNetwork;
		nextGeneration = new MultipleNetwork(multipleNetwork, sfmt);


		for(int networkNumber=0; networkNumber<LAST_NETWORK; networkNumber++) {
			for(Agent agent : thisGeneration.networkList.get(networkNumber).agentList) {
				//ga for one agent
				//roulette
				Agent parent1 = rouletteChoice(agent, null, sfmt);
				Agent parent2 = rouletteChoice(agent, parent1, sfmt);

				//uniform cross over
				uniformCrossOver(agent, networkNumber, parent1, parent2, sfmt);


				//mutaiton
				mutation(nextGeneration.networkList.get(networkNumber).agentList.get(agent.getNumber()), sfmt);
				//finish ga for one agent
			}
		}

		//ga in last network
		geneticAlgorithmInLastNetwork();

		return nextGeneration;
	}
	private Agent rouletteChoice(Agent agent, Agent remove, Sfmt sfmt) {
		double fitnessMin = Double.MAX_VALUE;
		double fitnessSum = 0;
		List<Agent> brotherList = new ArrayList<>();

		//brotherList
		for(Network network : thisGeneration.networkList) {

			Agent brother = network.agentList.get(agent.getNumber());
			brotherList.add(brother);
		}
		if(remove != null) {

			brotherList.remove(remove);
		}

		//fitness min
		for(Agent brother : brotherList) {
			if(brother.getFitness() < fitnessMin) {

				fitnessMin = brother.getFitness();
			}
		}

		//fitness sum
		for(Agent brother : brotherList) {
			fitnessSum += (brother.getFitness() - fitnessMin);
		}


		//select
		double roulette = (double)sfmt.NextInt((int)fitnessSum);

		double selectSum = 0;
		for(Agent brother : brotherList) {
			selectSum += brother.getFitness();

			if(selectSum >= roulette) {
				return brother;
			}
		}

		return brotherList.get(brotherList.size()-1);
	}

	private void uniformCrossOver(Agent thisAgent, int networkNumber, Agent parent1, Agent parent2, Sfmt sfmt) {
		Agent nextAgent = nextGeneration.networkList.get(networkNumber).agentList.get(thisAgent.getNumber());

		Gene gene1 = parent1.getB();
		Gene gene2 = parent2.getB();
		nextAgent.getB().setGene(calculateUCO(gene1, gene2, sfmt));

		gene1 = parent1.getL();
		gene2 = parent2.getL();
		nextAgent.getL().setGene(calculateUCO(gene1, gene2, sfmt));
	}
	private int[] calculateUCO(Gene gene1, Gene gene2, Sfmt sfmt) {
		int[] result = new int[gene1.getDigit()];
		for(int d=0; d<gene1.getDigit(); d++) {
			if(sfmt.NextInt(2) == 0) {
				result[d] = gene1.getGene()[d];
			}else {
				result[d] = gene2.getGene()[d];
			}
		}
		return result;
	}

	private void mutation(Agent agent, Sfmt sfmt) {

		calculateM(agent.getB(), sfmt);
		calculateM(agent.getL(), sfmt);
	}
	private void calculateM(Gene gene, Sfmt sfmt) {

		for(int d=0; d<gene.getDigit(); d++) {
			if(sfmt.NextUnif() < MUTATION_RATE) {
				gene.changeBit(d);

			}
		}
	}

	private void geneticAlgorithmInLastNetwork() {
		for(Agent agent : nextGeneration.networkList.get(LAST_NETWORK).agentList) {

			//brother list
			List<Agent> brotherList = new ArrayList<Agent>();
			for(Network network : thisGeneration.networkList) {
				brotherList.add(network.agentList.get(agent.getNumber()));
			}

			//select
			int maxFitnessIndex=0;
			Agent maxFitnessAgent=null;
			for(Agent brother : brotherList) {

				maxFitnessAgent = brotherList.get(maxFitnessIndex);
				if(brother.getFitness() > maxFitnessAgent.getFitness()) {
					maxFitnessIndex = brotherList.indexOf(brother);
				}
			}
			maxFitnessAgent = brotherList.get(maxFitnessIndex);
			agent.getB().setGene(maxFitnessAgent.getB().getGene());
			agent.getL().setGene(maxFitnessAgent.getL().getGene());


		}
	}

}
