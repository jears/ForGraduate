package jears.cslt.util.fileOperation;

import java.io.File;

public class DeleteDir {
	public static void deleteDir(String dirname) {
		System.out.println(dirname);
		File f = new File(dirname);
		if (!f.exists()) {
			return;
		}
		if (!f.isDirectory()) {
			f.delete();
			return;
		}
		if (!dirname.endsWith("/") && !dirname.endsWith("\\"))
			dirname += "/";
		String[] files = f.list();
		for (String file : files) {
			deleteDir(dirname + file);
		}
		f.delete();
	}
}
