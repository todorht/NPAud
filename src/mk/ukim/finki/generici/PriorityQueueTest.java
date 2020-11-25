package mk.ukim.finki.generici;

import java.util.ArrayList;
import java.util.List;

class Node<T> implements Comparable<Node<T>>{
    T item;
    int priority;

    public Node(T element, int priority) {
        this.item = element;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + item +
                ", priority=" + priority +
                '}';
    }


    @Override
    public int compareTo(Node<T> o) {
        return Integer.compare(this.priority,o.priority);
    }
}

class PriorityQueue<T extends Comparable<T>>{
    List<Node<T>> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    public void add(T item, int priority){
        Node<T> newNode = new Node(item,priority);
        int i;
        for (i=0;i<elements.size();i++){
            if(newNode.compareTo(elements.get(i))<=0){
                break;
            }
        }
        elements.add(i,newNode);
    }

    public T remove(){
        if(elements.size()==0) return null;
        return elements.remove(elements.size()-1).item;
    }
}

public class PriorityQueueTest {
    public static void main(String[] args) {

        PriorityQueue<String> queue = new PriorityQueue<>();

        queue.add("sreden",50);
        queue.add("sreden1",49);
        queue.add("najnizok",10);
        queue.add("sreden",50);
        queue.add("sreden1",49);
        queue.add("najvisok",100);

        String element;
        while ((element= queue.remove())!=null){
            System.out.println(element);
        }

    }
}
