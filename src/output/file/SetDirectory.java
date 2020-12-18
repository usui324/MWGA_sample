package output.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.entry.Parameter;

public class SetDirectory implements Parameter{
	private String folderName;
	private String pathName;

//------------------------------
	public SetDirectory() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String stldt = dtformat.format(ldt);
		folderName = stldt;
		pathName = DIRECTORY_PATH + folderName;
	}

//------------------------------
	public String set() {

		File directory = new File(pathName);

		if(directory.mkdir()) {
			System.out.println("Succeed in setting Directory : " + folderName);
		}
		else {
			System.out.println("Fail in setting Directory : " + folderName);
			System.exit(1);
		}
		return pathName;
	}
	public String setInternal(int simulationNumber) {
		String internalFolderName = pathName + "\\simulation" + simulationNumber;
		File internalDirectory = new File(internalFolderName);

		if(internalDirectory.mkdir()) {
			System.out.println("Succeed in setting Internal Directory : Simulation" + simulationNumber);
		}
		else {
			System.out.println("Fail in setting Internal Directory : Simulation" + simulationNumber);
			System.exit(1);
		}
		return internalFolderName;
	}




}
