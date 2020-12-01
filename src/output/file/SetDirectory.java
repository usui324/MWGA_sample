package output.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.entry.Parameter;

public class SetDirectory implements Parameter{
	private String fileName;
	private String pathName;

//------------------------------
	public SetDirectory() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String stldt = dtformat.format(ldt);
		fileName = stldt;
		pathName = DIRECTORY_PATH + fileName;
	}

//------------------------------
	public String set() {

		File directory = new File(pathName);

		if(directory.mkdir()) {
			System.out.println("Succeed in setting Directory : " + fileName);
		}
		else {
			System.out.println("Fail in setting Directory : " + fileName);
			System.exit(1);
		}
		return pathName;
	}




}
