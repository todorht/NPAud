package mk.ukim.finki.aud9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class NumberPrintingThread extends Thread{
    int i;

    public NumberPrintingThread(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(i);
    }
}

public class ThreadsIntroTest {
    public static void main(String[] args) {

        List<NumberPrintingThread> threads = new ArrayList<>();
        for(int i=1;i<=100;i++){
            threads.add(new NumberPrintingThread(i));
        }

        List<Thread> runnables = new ArrayList<>();
        for(int i=1;i<=100;i++) {
            int finalI= i;
            runnables.add(new Thread(()-> System.out.println(finalI)));
        }

        runnables.forEach(Thread::start);
        threads.forEach(Thread::start);

        for (NumberPrintingThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e){
                e.getMessage();
            }
        }
    }
}
