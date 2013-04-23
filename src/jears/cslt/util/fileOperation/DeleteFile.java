package jears.cslt.util.fileOperation;

import java.io.File;

public class DeleteFile {
	String fileName;
	File file;

	public DeleteFile(String fileName) {
		this.fileName = fileName;
		file = new File(fileName);
	}
	
	public DeleteFile(File file) {
		this.fileName = file.getPath();
		this.file = file;
	}

	synchronized public void delete() {
		file.delete();
	}
}