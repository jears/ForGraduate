package jears.cslt.util.fileOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CopyFile {
	public static void copyfile(String oldfile, String newfile) throws IOException {
		MoveFile mf = new MoveFile(new File(oldfile));
		mf.move(newfile);
	}
	
	public static void main(String[] args) throws IOException {
		copyfile("readme", "readmecopy");
	}
}
