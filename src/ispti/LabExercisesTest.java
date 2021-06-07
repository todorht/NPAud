package ispti;

import java.util.*;
import java.util.stream.Collectors;

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
//        labExercises.getStatisticsByYear().entrySet().stream()
//                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
//                .forEach(System.out::println);

    }
}

class Student{
    String index;
    List<Integer> points;
    int year;
    String potpis;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.year = index.charAt(1);
        this.points = points;
        if(this.points.size()>7){
            this.potpis = "YES";
        }else this.potpis = "NO";
    }

    double getAverage(){
        int sum =0;
        for(Integer points:points){
            sum+=points;
        }
        return sum/(10*1.0);
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f",this.index,this.potpis,this.getAverage());
    }
}

class LabExercises{
    List<Student> students;
    Map<Integer, List<Student>> studentsByYear;

    public LabExercises() {
        this.students = new ArrayList<>();
        this.studentsByYear = new TreeMap<>();
    }


    public void addStudent(Student student) {
        this.students.add(student);
        if (!this.studentsByYear.containsKey(student.year)) {
            this.studentsByYear.put(student.year, new ArrayList<>());
        }
        this.studentsByYear.get(student.year).add(student);
    }

    public void printByAveragePoints(boolean b, int i) {
        if(b) {
            this.students.stream()
                    .sorted(Comparator.comparing(Student::getAverage).thenComparing(Student::getIndex))
                    .limit(i).forEach(System.out::println);
        }else {
            this.students.stream()
                    .sorted(Comparator.comparing(Student::getAverage).thenComparing(Student::getIndex).reversed())
                    .limit(i).forEach(System.out::println);
        }
    }

    public List<Student> failedStudents() {
        return this.students.stream()
                .filter(student -> student.potpis.equals("NO")).
                        sorted(Comparator.comparing(Student::getIndex)
                                .thenComparing(Student::getAverage))
                .collect(Collectors.toList());
    }
//
//    public Map<Integer, Double> getStatisticsByYear() {
//
//    }
}