package jears.cslt.util.fileOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteFile
        implements Constants {
    
    private FileOutputStream fos;
    private OutputStreamWriter osw;
    private BufferedWriter bw;

    public WriteFile(String fileName, boolean append)
            throws IOException {
        fos = new FileOutputStream(fileName, append);
        osw = new OutputStreamWriter(fos, UTF8);
        bw = new BufferedWriter(osw);
    }

    public WriteFile(String fileName, boolean append, String charset)
            throws IOException {
        fos = new FileOutputStream(fileName, append);
        osw = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osw);
    }

    public WriteFile(File file, boolean append, String charset)
            throws IOException {
        fos = new FileOutputStream(file, append);
        osw = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osw);
    }

    synchronized public void write(String str)
            throws IOException {
        bw.write(str);
    }

    synchronized public void writeLine(String str)
            throws IOException {
        bw.write(str + NEW_LINE);
    }

    synchronized public void writeLine(String str, String newline)
            throws IOException {
        bw.write(str + newline);
    }

    synchronized public void close()
            throws IOException {
        bw.close();
        osw.close();
        fos.close();
    }
}
