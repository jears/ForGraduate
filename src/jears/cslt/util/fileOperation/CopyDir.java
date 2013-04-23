package jears.cslt.util.fileOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CopyDir {
	public static void copydir(String dir, String newdir) throws IOException {
		if (!dir.endsWith("/") && !dir.endsWith("\\"))
			dir += "/";
		if (!newdir.endsWith("/") && !newdir.endsWith("\\"))
			newdir += "/";
		CreateDir.createDir(newdir);
		File[] dirs = new File(dir).listFiles();
		for(File f : dirs) {
			if (f.isFile()) {
				MoveFile mf = new MoveFile(f);
				mf.move(newdir + f.getName());
			} else {
				String subDir = f.getName();
				copydir(dir + subDir, newdir + subDir);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		copydir("test", "newtest");
	}
}
