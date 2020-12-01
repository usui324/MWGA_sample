package output.csv;

import java.io.FileWriter;
import java.io.IOException;

import main.agent.Agent;
import main.network.Network;

public class NetworkProgress implements ICsv{
	private Network resultNetwork;
	private String fileName;

	private int networkSize;

	//avgs
	private double avgB = 0;
	private double avgL = 0;
	private double avgFitness = 0;
	private double avgLink = 0;
	private double avgPost = 0;
	private double avgComment = 0;
	private double avgMetaComment = 0;

//----------------------------------------
	public NetworkProgress(int simulationTime) {
		this.fileName = "NetworkProgress" + simulationTime + ".csv";
	}

//----------------------------------------
	public String write(int generation, Network network) {
		resultNetwork = network;
		networkSize = resultNetwork.agentList.size();

		resetCalculate();
		calculate();

		if(generation==0) {

			FileWriter filewriter = null;
			writeHeader(filewriter);
			writeContent(filewriter, generation);

		}
		else {

			FileWriter filewriter = null;
			writeContent(filewriter, generation);


		}
		return fileName;
	}

	private void resetCalculate() {
		avgB = 0;
		avgL = 0;
		avgFitness = 0;
		avgLink = 0;
		avgPost = 0;
		avgComment = 0;
		avgMetaComment = 0;

	}

	private void calculate() {
		for(Agent agent : resultNetwork.getAgentList()) {
			avgB += agent.getB().getGeneToDouble();
			avgL += agent.getL().getGeneToDouble();
			avgFitness += agent.getFitness();
			avgLink += agent.getNumOfLink();
			avgPost += agent.numOfPost;
			avgComment += agent.numOfComment;
			avgMetaComment += agent.numOfMetaComment;

		}
		avgB /= (double)networkSize;
		avgL /= (double)networkSize;
		avgFitness /= (double)networkSize;
		avgLink /= (double)networkSize;
		avgPost /= (double)networkSize;
		avgComment /= (double)networkSize;
		avgMetaComment /= (double)networkSize;
	}

	private void writeHeader(FileWriter filewriter) {
		try {

			filewriter = new FileWriter(fileName);

			//header
			filewriter.append("generation");
			filewriter.append(COMMA);
			filewriter.append("parameterB");
			filewriter.append(COMMA);
			filewriter.append("parameterL");
			filewriter.append(COMMA);
			filewriter.append("fitness");
			filewriter.append(COMMA);
			filewriter.append("degree");
			filewriter.append(COMMA);
			filewriter.append("post");
			filewriter.append(COMMA);
			filewriter.append("comment");
			filewriter.append(COMMA);
			filewriter.append("metaComment");
			filewriter.append(NEW_LINE);

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				filewriter.flush();
				filewriter.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeContent(FileWriter filewriter, int generation) {
		try {

			filewriter = new FileWriter(fileName, true);

			filewriter.append(String.valueOf(generation));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgB));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgL));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgFitness));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgLink));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgPost));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgComment));
			filewriter.append(COMMA);
			filewriter.append(String.valueOf(avgMetaComment));
			filewriter.append(NEW_LINE);

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				filewriter.flush();
				filewriter.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}
