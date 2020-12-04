package output.file;

import java.io.File;

public class FileTransfer {
	private String folderName;

//------------------------------------------------
	public FileTransfer(String folderName) {
		this.folderName = folderName;
	}

//------------------------------------------------
	public synchronized void transfer(String fileName) {

		String fromName = fileName;
		String toName = folderName + "\\" + fileName;

		File from = new File(fromName);
		File to = new File(toName);

		from.renameTo(to);
	}
}
