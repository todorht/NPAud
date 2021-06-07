package ispti;

import java.util.*;

/**
 * January 2016 Exam problem 2
 */
public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}

abstract class Positionable{
    protected Long identifier;

    public Positionable(long identifier) {
        this.identifier = identifier;
    }

    public long getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier.toString();
    }

    abstract public double getDistanceFrom(Positionable b);
}

class Point2D extends Positionable{

    float x;
    float y;

    public Point2D(long id, float x, float y) {
        super(id);
        this.x = x;
        this.y = y;
    }

    @Override
    public double getDistanceFrom(Positionable a) {
        Point2D b=(Point2D) a;
        return Math.sqrt((x-b.x)*(x-b.x)+(y-b.y)*(y-b.y));
    }
    @Override
    public String toString() {
        return super.toString();
    }

}

class Cluster<T extends Positionable>{

    Map<Long,T> elements;

    public Cluster() {
        this.elements = new HashMap<>();
    }

    void addItem(T point2D) {
        this.elements.put(point2D.getIdentifier(),point2D);
    }

    void near(long id, int top) {
        T from = this.elements.get(id);
        int i =0;
        elements.values().stream()
                .sorted(Comparator.comparingDouble(o->o.getDistanceFrom(from))).skip(1)
                .limit(top).forEach(x ->System.out.printf(x + " -> %.3f\n",x.getDistanceFrom(from)));
    }
}