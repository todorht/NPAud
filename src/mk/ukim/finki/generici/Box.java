package mk.ukim.finki.generici;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Drawable{
    void draw();
}

public class Box<T extends Drawable> {
    List<T> elements;
    static Random RANDOM = new Random();

    public Box() {
        this.elements = new ArrayList<>();
    }

    public void addElement(T element){
        elements.add(element);
    }

//    public T drawElement(){
//        if(elements.isEmpty()) return null;
//        return elements.remove(RANDOM.nextInt(elements.size()));
//    }

    public void drawElement(){
        if(elements.isEmpty()) return;
        T element = elements.remove(RANDOM.nextInt(elements.size()));
        element.draw();
    }

    public static void main(String[] args) {
       Random random = new Random();
       //Box<Integer> boxInteger = new Box<Integer>();
       Box<Drawable> box = new Box<>();

       for(int i = 0;i<100;i++){
           box.addElement(()-> System.out.println(random.nextInt(50)));
       }

//        for (int i = 0;i<50;i++){
//            box.addElement(i);
//        }

        for (int i = 0;i<50;i++){
            box.drawElement();
        }
    }
}
