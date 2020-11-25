package prvKolovium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

interface Scalable{
    void scale(float scaleFactor);
}

interface Stackable{
    float weight();
}
abstract class Shape{
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String toString(float weight) {
        return String.format("%-5s%10s%10.2f", id, color, weight);
    }
}

class Rectangle extends Shape implements Scalable, Stackable{
    float width;
    float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width = width*scaleFactor;
        height = height*scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public String toString() {
        return String.format("R: %s\n",toString(weight()));
    }
}

class Circle extends Shape implements Scalable,Stackable{

    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius = radius*scaleFactor;
    }

    @Override
    public float weight() {
        return radius * radius * (float) Math.PI;
    }

    @Override
    public String toString() {
        return String.format("C: %s\n",toString(weight()));
    }
}

class Canvas {

    List<Shape> shapes;

    public Canvas(){
        this.shapes = new ArrayList<>();
    }

    void add(String id,Color color,float radius){
        Circle circle = new Circle(id, color, radius);
        shapes.add(circle);
    }

    void add(String id, Color color, float width, float height){
        Rectangle rectangle = new Rectangle(id, color, width, height);
        shapes.add(rectangle);
    }

    Shape find(String id){
        return shapes.stream().filter(s->s.id.equals(id)).findFirst().get();
    }

    void scale(String id, float scaleFactor){


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shape shape:shapes){
            sb.append(shape.toString());
        }
        return sb.toString();
    }
}