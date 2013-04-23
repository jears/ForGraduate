package jears.cslt.util.fileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile
        implements Constants {

    private FileInputStream fis;
    private InputStreamReader isr;
    private BufferedReader br;

    public ReadFile(String fileName)
            throws IOException {
        fis = new FileInputStream(fileName);
        isr = new InputStreamReader(fis, UTF8);
        br = new BufferedReader(isr);
    }

    public ReadFile(String fileName, String charset)
            throws IOException {
        fis = new FileInputStream(fileName);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    public ReadFile(String fileName, String charset, int size)
            throws IOException {
        fis = new FileInputStream(fileName);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr, size);
    }

    public ReadFile(File file, String charset)
            throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    synchronized public String readLine()
            throws IOException {
        return br.readLine();
    }

    synchronized public void close()
            throws IOException {
        br.close();
        isr.close();
        fis.close();
    }
}
