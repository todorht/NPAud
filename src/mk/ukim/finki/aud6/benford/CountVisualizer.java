package mk.ukim.finki.aud6.benford;

import java.io.OutputStream;
import java.io.PrintWriter;

public class CountVisualizer {

    private final int n;

    public CountVisualizer(int n) {
        this.n = n;
    }

    public void  visualizer(OutputStream outputStream, int[] counts){
        PrintWriter pw = new PrintWriter(outputStream);
        for(Integer count: counts){
            while (count>0) {
                pw.write("*");
                count -= n;
            }
            pw.write("\n");
        }
        pw.flush();
    }
}
