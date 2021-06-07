package mk.ukim.finki.aud9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class Searcher extends Thread{
    int start;
    int end;
    int max;


    public Searcher(int start, int end){
        this.start = start;
        this.end = end;
        this.max = FindingMaxWithMultithreading.array[start];
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void run() {
        this.max = findMax();
    }

    int findMax(){
        return IntStream.range(start,end)
                .map(i->FindingMaxWithMultithreading.array[i])
                .max()
                .getAsInt();
    }

}

public class FindingMaxWithMultithreading {

    static int MAX=1000000;
    static int[] array  = new int[MAX];
    static Random random = new Random();
    static  int NO_THREADS = 16;

    public static void main(String[] args) throws InterruptedException {

        for(int i =0;i<MAX;i++){
            array[i] = random.nextInt(1000)+1000;
        }

        int lengthOfSubarray = MAX/NO_THREADS;
        int [] starts = new int[NO_THREADS];

        starts[0] = 0;
        for (int i =1;i<NO_THREADS;i++){
            starts[i] = starts[i] + lengthOfSubarray;
        }

        List<Searcher> searchers = new ArrayList<>();

        for(int i=0;i<NO_THREADS;i++){
            searchers.add(new Searcher(starts[i], starts[i]+lengthOfSubarray));
        }

        searchers.forEach(Searcher::start);

        for (Searcher s: searchers){
            s.join();
        }

        System.out.println(searchers.stream().mapToInt(Searcher::getMax).max().getAsInt());

    }
}
