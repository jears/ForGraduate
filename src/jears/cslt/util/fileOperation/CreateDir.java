package jears.cslt.util.fileOperation;

import java.io.File;

public class CreateDir {
	public static void createDir(String dirName) {
		File dir = new File(dirName);
		if (!dir.exists()) 
			dir.mkdirs();
	}
	
	public static void createDirForFile(String fileName) {
		int end1 = fileName.lastIndexOf('/');
		int end2 = fileName.lastIndexOf('\\');
		int end = end1;
		if (end2 > end1)
			end = end2;
		if (end == -1)
			return;
		String dirname = fileName.substring(0, end);
//		System.out.println(dirname);
		createDir(dirname);
	}
	
	public static void main(String[] args) {
		createDirForFile("data/app\\a.txt");
	}
}
