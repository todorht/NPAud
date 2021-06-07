//package ispti;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        //stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде

class Block<T extends Comparable<T>> {
    int id;
    TreeSet<T> elementInBlock;
    int size;

    public Block(int id, int size) {
        this.id = id;
        this.size = size;
        elementInBlock = new TreeSet<>();
    }

    @Override
    public String toString() {
        return String.format("[%s",this.elementInBlock
                .stream()
                .map(element -> String.format("%s", String.valueOf(element)))
                .collect(Collectors.joining(", "))) + "]";
    }
}

class BlockContainer<T extends Comparable<T>>{
    private Map<Integer,Block<T>> blocks;
    int size;
    int lastAdd;

    public BlockContainer(int size) {
        this.size = size;
        this.lastAdd=1;
        this.blocks = new HashMap();
        this.blocks.put(1,new Block<>(1,size));
    }

    void add(T element){
        if (this.blocks.get(this.lastAdd).elementInBlock.size() == size) {
            this.lastAdd += 1;
            this.blocks.put(this.lastAdd, new Block<>(this.lastAdd, size));
        }
        this.blocks.get(this.lastAdd).elementInBlock.add(element);
    }

    boolean remove(T element){
        if(this.blocks.get(lastAdd).elementInBlock.remove(element)) {
            if (this.blocks.get(lastAdd).elementInBlock.size() == 0) {
                this.blocks.remove(lastAdd);
                this.lastAdd -= 1;
            }
            return true;
        }
        return false;
    }

    void sort() {

    }

    @Override
    public String toString() {
       return String.format("%s",this.blocks.entrySet()
               .stream()
               .map(block->block.getValue().toString())
               .collect(Collectors.joining(","))
       );
    }


}


