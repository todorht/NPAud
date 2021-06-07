package mk.ukim.finki.aud8;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

class FileSystem{
    Map<Character, Set<File>> filesByDirectories;
    List<File> allFiles;

    public FileSystem() {
        filesByDirectories = new HashMap<>();
        allFiles = new ArrayList<>();
    }


    public void addFile(char folder, String name, int size, LocalDateTime date) {
        File file = new File(name,date,size);
        allFiles.add(file);

        filesByDirectories.putIfAbsent(folder,new TreeSet<>());
        filesByDirectories.get(folder).add(file);
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
       return allFiles.stream()
               .filter(file -> file.isHiddenAndSizeLessThen(size))
               .sorted(Comparator.naturalOrder())
               .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        return folders.stream()
                .filter(folder -> filesByDirectories.containsKey(folder))
                .flatMapToInt(folder -> filesByDirectories.get(folder).stream(). //flatMap zoso imame kolekcija od kolekcija
                        mapToInt(File::getSize)
                ).sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return allFiles.stream()
                .collect(Collectors.groupingBy(
                        f->f.date.getYear(), //key
                        Collectors.toCollection(TreeSet::new) //value
                ));
    }

    public Map<String, Long> sizeByMonthAndDay(){
        return allFiles.stream().collect(Collectors.groupingBy(
                File::createMonthAndDayString,
                TreeMap::new, //dokolku sakame TreeMap, a ne HashMap
                Collectors.summingLong(File::getSize)//vrakja HashMap
        ));
    }
}

class File implements Comparable<File>{
    String name;
    LocalDateTime date;
    int size;

    public File(String name, LocalDateTime date, int size) {
        this.name = name;
        this.date = date;
        this.size = size;
    }

    public boolean isHiddenAndSizeLessThen(int size){
        return name.startsWith(".") && this.size<size;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s",name,size, date.toString());
    }

    public String createMonthAndDayString(){
        return String.format("%s-%d",this.date.getMonth().toString(), this.date.getDayOfMonth());
    }

    @Override
    public int compareTo(File o) {

        Comparator<File> fileComparator = Comparator.comparing(File::getDate)
                .thenComparing(File::getName)
                .thenComparing(File::getSize);

        return fileComparator.compare(this,o);

//        int res = this.date.compareTo(o.date);
//        if(res==0){
//            int res1 = this.name.compareTo(o.name);
//            if(res1==0){
//                return Integer.compare(this.size, o.size);
//            }else{
//                return res1;
//            }
//        }else return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}