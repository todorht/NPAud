package mk.ukim.finki.aud5;

import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

class LinesConsumer implements Consumer<String> {
    int lines, words , chars;

    @Override
    public void accept(String s) {
        lines++;
        words+=s.split("\\s+").length;
        chars+=s.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Chars: %d", lines, words, chars);
    }
}

//wordCountWithMapReduce
class LineCounter {
    int lines, words , chars;

    public LineCounter(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public LineCounter sum(LineCounter lineCounter){
        return new LineCounter(this.lines+lineCounter.lines,this.words+lineCounter.words,this.chars+lineCounter.chars);
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Chars: %d", lines, words, chars);
    }
}

public class WordCounterTest {

    public static void  wordCountWithScanner(InputStream inputStream){
        int lines = 0, words = 0, chars = 0;
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            ++lines;
            words+=line.split("\\s+").length;
            chars+=line.length();
        }

        System.out.printf("Lines: %d, Words: %d, Chars: %d", lines, words, chars);
    }

    public static void wordCountWithBufferedReader(InputStream inputStream) throws IOException {
        int lines = 0, words = 0, chars = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine())!=null){
            ++lines;
            words+=line.split("\\s+").length;
            chars+=line.length();
        }
        System.out.printf("Lines: %d, Words: %d, Chars: %d", lines, words, chars);
    }

    public static void wordCountWithBufferedReaderAndConsumer(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        LinesConsumer linesConsumer = new LinesConsumer();
        br.lines().forEach(linesConsumer); //sekoja linija na preprakja na accept metodot od LineConsumer
        System.out.println(linesConsumer);
    }

    public static void wordCountWithMapReduce(InputStream inputStream){ //1. maprianje  2.reduciranje  line->{1-lini,10-zborovi,45-chars}
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        LineCounter results = br.lines().
                map(line -> new LineCounter(1, line.split("\\s+").length, line.length()))
                .reduce(new LineCounter(0,0,0), (l,r)->l.sum(r));
        System.out.println(results);
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\FINKI\\Zimski\\NP\\NPAud\\src\\mk\\ukim\\finki\\file");
        wordCountWithScanner(new FileInputStream(file));
    }
}
