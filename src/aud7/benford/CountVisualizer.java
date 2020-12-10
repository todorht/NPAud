package aud7.benford;

import java.io.OutputStream;
import java.io.PrintWriter;

public class CountVisualizer {
    private final int n;

    public CountVisualizer(int n) {
        this.n = n;
    }

    public void visualize(OutputStream outputStream, int[] counts){
        PrintWriter pw = new PrintWriter(outputStream);
        for(Integer count : counts){
            while(count > 0){
                pw.write("*");
                count -= n;
            }
            pw.println();
        }
        pw.flush();
    }
}
