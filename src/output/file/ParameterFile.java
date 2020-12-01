package output.file;

import java.io.FileWriter;
import java.io.IOException;

import main.entry.Parameter;
import main.evaluate.EvaluateAlgorithm;
import main.network.Network;

public class ParameterFile implements Parameter {
	private final String FILE_NAME = "Parameters.txt";
	private static final String NEW_LINE = "\r\n";

	Network network;
	EvaluateAlgorithm ea;

	public ParameterFile(Network network, EvaluateAlgorithm ea) {
		this.network = network;
		this.ea = ea;
	}

//-----------------------------
	public String write() {

		FileWriter filewriter = null;

		try {

			filewriter = new FileWriter(FILE_NAME);
			filewriter.append("Simulation Time : ");
			filewriter.append(String.valueOf(NUM_OF_SIMULATION));
			filewriter.append("Simulation in MWGA");
			filewriter.append(NEW_LINE);

			filewriter.append(NEW_LINE);
			filewriter.append(NEW_LINE);


			filewriter.append("Network Model : ");
			filewriter.append(network.getName());
			filewriter.append(NEW_LINE);

			filewriter.append("Evaluate Algorithm : ");
			filewriter.append(ea.getName());
			filewriter.append(NEW_LINE);

			filewriter.append("Agent : ");
			filewriter.append(String.valueOf(NUM_OF_AGENT));
			filewriter.append(NEW_LINE);

			filewriter.append("Generation : ");
			filewriter.append(String.valueOf(GENERATION));
			filewriter.append(NEW_LINE);

			filewriter.append("World : ");
			filewriter.append(String.valueOf(NUM_OF_NETWORK));
			filewriter.append(NEW_LINE);

			filewriter.append("Mutaton Rate : ");
			filewriter.append(String.valueOf(MUTATION_RATE));
			filewriter.append(NEW_LINE);



			filewriter.append(NEW_LINE);
			switch(network.getName()) {
			case "Barabasi Albert":
				filewriter.append("Init Number of Network : ");
				filewriter.append(String.valueOf(NUM_OF_STARTING_AGENT_IN_BA));
				filewriter.append(NEW_LINE);
				filewriter.append("Number of Edge with Add Agent : ");
				filewriter.append(String.valueOf(NUM_OF_ADD_EDGE_IN_BA));
				filewriter.append(NEW_LINE);
				break;

			case "Connecting Nearest Neighbor":
				filewriter.append("U : ");
				filewriter.append(String.valueOf(U_IN_CNN));
				filewriter.append(NEW_LINE);
				break;
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

		return FILE_NAME;
	}

}
