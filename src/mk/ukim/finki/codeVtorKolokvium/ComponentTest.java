package mk.ukim.finki.codeVtorKolokvium;

import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде

class InvalidPositionException extends RuntimeException{
    public InvalidPositionException(int i){
        super(String.format("Invalid position %d, alredy taken!",i));
    }
}

class Component{
    String color;
    int weight;
    Set<Component> components;

    public Component(String color, int weight) {
        super();
        this.color = color;
        this.weight = weight;
        this.components = new TreeSet<>(Comparator.comparing(Component::getWeight)
                .thenComparing(Component::getColor));
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void changeColor(int weight, String color) {
        if(this.weight < weight)
            this.color = color;
        components.forEach(component -> component.changeColor(weight, color));
    }


    public String format(String crti) {
        String s = String.format("%s%d:%s\n", crti, weight, color);
        for (Component component : components) {
            s+=component.format(crti+"---");
        }
        return s;
    }

    @Override
    public String toString() {
        return format("");
    }
}

class Window{
    String name;
    Map<Integer,Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<>();

    }

    public void addComponent(int i, Component component){
        if(components.containsKey(i)){
            throw new InvalidPositionException(i);
        }else components.put(i,component);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW "+name+"\n");
        components.entrySet().forEach(entry -> sb.append(entry.getKey()+ ":" + entry.getValue()));
        return sb.toString();
    }

    public void changeColor(int weight, String color){
        components.values().stream().forEach(component -> component.changeColor(weight,color));
    }

    public void swichComponents(int pos1, int pos2){
        Component tmp = components.get(pos1);
        components.put(pos1, components.get(pos2));
        components.put(pos2, tmp);
    }

}