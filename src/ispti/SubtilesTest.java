package ispti;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде

class Part{
    int position;
    String startAt;
    String finishAt;
    String text;

    public Part(){

    }

    @Override
    public String toString() {
        return String.format("%s\n%s-->%s\n%s\n",position,startAt,finishAt,text);
    }

}

class Subtitles{

    List<Part> parts;

    public Subtitles() {
        this.parts = new ArrayList<>();
    }


    int loadSubtitles(InputStream inputStream){
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()){
            Part part = new Part();
            part.position = Integer.parseInt(sc.nextLine());
            String time = sc.nextLine();
            String[] parts = time.split(" --> ");
            part.startAt = parts[0];
            part.finishAt = parts[1];
            StringBuilder text = new StringBuilder(sc.nextLine());
            String tmp;
            if(!sc.hasNextLine()){
                part.text = text.toString();
                this.parts.add(part);
                return this.parts.size();
            }else tmp = sc.nextLine();
            while (tmp!=null && !tmp.equals("")){
               text.append("\n").append(tmp);
               if(sc.hasNextLine()) sc.nextLine();
               break;
            }
            part.text = text.toString();
            this.parts.add(part);
        }
        return this.parts.get(parts.size()-1).position;
    }

    void print(){
        this.parts.forEach(System.out::println);
    }

    void shift(int shift) {
        for(Part part:this.parts){
            part.startAt = calculate(part.startAt, shift);
            part.finishAt = calculate(part.finishAt, shift);
        }
    }

    static String calculate(String time, int shift){
        String tmp = time.substring(6, time.length()-1);
        String[] parts = tmp.split(",");
        if(shift<0) {
            if (Integer.parseInt(parts[1]) < shift) {
                parts[1] = String.valueOf((Integer.parseInt(parts[1]) + 1000) - shift);
                parts[0] = String.valueOf(Integer.parseInt(parts[0]) - 1);
            }
        }else {
            if(Integer.parseInt(parts[1])+shift > 1000){
                parts[1] = String.valueOf(1000 - (Integer.parseInt(parts[1])+shift));
                parts[0] = String.valueOf(Integer.parseInt(parts[0])+1);
            }
        }
        return time.substring(0,6) + parts[1] + "," + parts[1];
    }
}