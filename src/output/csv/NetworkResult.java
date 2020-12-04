package output.csv;

import java.io.FileWriter;
import java.io.IOException;

import main.agent.Agent;
import main.network.Network;

public class NetworkResult implements ICsv{
	private Network resultNetwork;
	private String fileName;

//----------------------------------------
	public NetworkResult(int simulationTime, int generation) {
		this.fileName = "NetworkResult" + simulationTime + "_" + generation +".csv";
	}

//----------------------------------------
	public String write(Network network) {
		resultNetwork = network;

		FileWriter filewriter = null;

		writeHeader(filewriter);

		writeContent(filewriter);

		return fileName;
	}

	//header
	private void writeHeader(FileWriter filewriter) {
		try {

			filewriter = new FileWriter(fileName);

			//header
			filewriter.append("number");
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

	//content
	private void writeContent(FileWriter filewriter) {
		try {

			filewriter = new FileWriter(fileName, true);

			for(Agent agent : resultNetwork.getAgentList()) {

				filewriter.append(String.valueOf(agent.getNumber()));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.getB().getGeneToDouble()));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.getL().getGeneToDouble()));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.getFitness()));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.getNumOfLink()));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.numOfPost));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.numOfComment));
				filewriter.append(COMMA);
				filewriter.append(String.valueOf(agent.numOfMetaComment));
				filewriter.append(NEW_LINE);

			}
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
