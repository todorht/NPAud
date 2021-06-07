//package ispti;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class OperationNotAllowedException extends Exception {
//    public OperationNotAllowedException(int term, String studentId){
//        super(String.format("Term %d is not possible for student with ID %s", term, studentId));
//    }
//
//    public OperationNotAllowedException(String studentId, int term){
//        super(String.format("Student %s already has 3 grades in term %d",studentId,term));
//    }
//}
//
//class Course{
//    private String courseName;
//    private int grade;
//    private int students;
//    private int sumGrades;
//
//    public Course(String courseName, int grade) {
//        this.courseName = courseName;
//        this.grade = grade;
//        this.students = 0;
//        this.sumGrades = 0;
//    }
//
//    public int getGrade() {
//        return grade;
//    }
//
//    public String getCourseName() {
//        return courseName;
//    }
//
//    public int getStudents() {
//        return students;
//    }
//
//    public double getAverage(){
//        if(students==0) return 5.0;
//        return sumGrades/(students*1.0);
//    }
//
//    public void addStudentAndGrade(int grade){
//        this.students+=1;
//        this.sumGrades+=grade;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %.2f", this.courseName, this.students, this.getAverage());
//    }
//}
//
//class Student{
//    private final String id;
//    private int yearsOfStudies;
//    private Map<Integer, Map<String, Course>> coursesByTerm;
//    private int courses;
//    private int sum;
//
//    public Student(String id, int yearsOfStudies) {
//        this.sum = 0;
//        this.courses = 0;
//        this.id = id;
//        this.yearsOfStudies = yearsOfStudies;
//        this.coursesByTerm = new TreeMap<>();
//        IntStream.range(1,this.yearsOfStudies*2+1)
//                .forEach(i->this.coursesByTerm.put(i,new HashMap<>()));
//    }
//
//    void setGrade(int term, Course course, int grade) throws OperationNotAllowedException {
//        if(term>this.coursesByTerm.size())
//            throw new OperationNotAllowedException(term,this.id);
//
//        if(this.coursesByTerm.get(term).values().size()==3)
//            throw new OperationNotAllowedException(this.id, term);
//
//        this.coursesByTerm.get(term).computeIfAbsent(course.getCourseName(), v->course);
//
//        this.sum+=grade;
//        this.courses+=1;
//    }
//
//    public int getCourses() {
//        return courses;
//    }
//
//    public int getYearsOfStudies() {
//        return yearsOfStudies;
//    }
//
//    double getAverageByTerm(int term){
//        if(this.coursesByTerm.get(term).entrySet().size()==0) return 5.0;
//
//        int sum = this.coursesByTerm.get(term).values().stream().mapToInt(Course::getGrade).sum();
//        return (sum*1.0)/this.coursesByTerm.get(term).values().size();
//    }
//
//    double getAverage(){
//        if(courses != 0) return sum / (courses * 1.0);
//        return 5.0;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public Map<Integer, Map<String, Course>> getCoursesByTerm() {
//        return coursesByTerm;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Student: %s Courses passed: %d Average grade: %.2f",this.id,this.courses, getAverage());
//    }
//}
//
//class Faculty {
//    private Map<String, Student> studentMap;
//    private Map<String,Course> courses;
//    private StringBuilder log;
//
//    public Faculty() {
//        this.studentMap = new TreeMap<>(Collections.reverseOrder());
//        this.courses = new HashMap<>();
//        this.log = new StringBuilder();
//    }
//
//    void addStudent(String id, int yearsOfStudies) {
//        this.studentMap.put(id, new Student(id,yearsOfStudies));
//    }
//
//    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
//        if(this.courses.containsKey(courseName)) {
//            this.studentMap.get(studentId).setGrade(term, this.courses.get(courseName), grade);
//        }else{
//            Course course = new Course(courseName, grade);
//            this.courses.put(courseName, course);
//            this.studentMap.get(studentId).setGrade(term, course, grade);
//        }
//        this.courses.get(courseName).addStudentAndGrade(grade);
//        Student student = this.studentMap.get(studentId);
//        if(student.getCourses()==student.getYearsOfStudies()*6) {
//            this.log.append(String.format("Student with ID %s graduated with average grade %.2f in %d years.",
//                    student.getId(),
//                    student.getAverage(),
//                    student.getYearsOfStudies()));
//            this.studentMap.remove(studentId);
//        }
//    }
//
//    String getFacultyLogs() {
//        return log.toString();
//    }
//
//    String getDetailedReportForStudent(String id) {
//        String sb = String.format("Student: %s\n%s", id,
//                this.studentMap.get(id).getCoursesByTerm()
//                        .entrySet().stream()
//                        .map(entry -> String.format("Term %d\nCourses: %d\nAverage grade for term: %.2f\n",
//                                entry.getKey(),
//                                entry.getValue().size(),
//                                this.studentMap.get(id).getAverageByTerm(entry.getKey())))
//                        .collect(Collectors.joining(""))) +
//                String.format("Average grade: %.2f\nCourses attended: %s",
//                        this.studentMap.get(id).getAverage(),
//                        this.courses.entrySet().stream().map(course -> String.format("%s", course.getKey()))
//                                .collect(Collectors.joining(",")));
//        return sb;
//    }
//
//    void printFirstNStudents(int n) {
//        if(n>this.studentMap.size()){
//            this.studentMap.values().stream()
//                    .sorted(Comparator.comparing(Student::getCourses)
//                            .thenComparing(Student::getAverage)
//                            .thenComparing(Student::getId)
//                            .reversed())
//                    .collect(Collectors.toList()).subList(0,this.studentMap.size()).forEach(System.out::println);
//        }else {
//            this.studentMap.values().stream()
//                    .sorted(Comparator.comparing(Student::getCourses)
//                            .thenComparing(Student::getAverage)
//                            .thenComparing(Student::getId)
//                            .reversed())
//                    .collect(Collectors.toList()).subList(0, n).forEach(System.out::println);
//        }
//    }
//
//    void printCourses() {
//        this.courses.values().stream().sorted(Comparator.comparing(Course::getStudents).thenComparing(Course::getAverage).thenComparing(Course::getCourseName)).forEach(System.out::println);
//    }
//}
//
//public class FacultyTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int testCase = sc.nextInt();
//
//        if (testCase == 1) {
//            System.out.println("TESTING addStudent AND printFirstNStudents");
//            Faculty faculty = new Faculty();
//            for (int i = 0; i < 10; i++) {
//                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
//            }
//            faculty.printFirstNStudents(10);
//
//        } else if (testCase == 2) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            try {
//                faculty.addGradeToStudent("123", 7, "NP", 10);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//            try {
//                faculty.addGradeToStudent("1234", 9, "NP", 8);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        } else if (testCase == 3) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        } else if (testCase == 4) {
//            System.out.println("Testing addGrade for graduation");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            int counter = 1;
//            for (int i = 1; i <= 6; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            counter = 1;
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
//            //faculty.printFirstNStudents(2);
//        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
//            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            if (testCase == 5)
//                faculty.printFirstNStudents(10);
//            else if (testCase == 6)
//                faculty.printFirstNStudents(3);
//            else
//                faculty.printFirstNStudents(20);
//        } else if (testCase == 8 || testCase == 9) {
//            System.out.println("TESTING DETAILED REPORT");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
//            int grade = 6;
//            int counterCounter = 1;
//            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
//                for (int j = 1; j < 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
//                    } catch (OperationNotAllowedException e) {
//                        e.printStackTrace();
//                    }
//                    grade++;
//                    if (grade == 10)
//                        grade = 5;
//                    ++counterCounter;
//                }
//            }
//            System.out.println(faculty.getDetailedReportForStudent("student1"));
//        } else if (testCase==10) {
//            System.out.println("TESTING PRINT COURSES");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            faculty.printCourses();
//        } else if (testCase==11) {
//            System.out.println("INTEGRATION TEST");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//
//            }
//
//            for (int i=11;i<15;i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= 3; k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("DETAILED REPORT FOR STUDENT");
//            System.out.println(faculty.getDetailedReportForStudent("student2"));
//            try {
//                System.out.println(faculty.getDetailedReportForStudent("student11"));
//                System.out.println("The graduated students should be deleted!!!");
//            } catch (NullPointerException e) {
//                System.out.println("The graduated students are really deleted");
//            }
//            System.out.println("FIRST N STUDENTS");
//            faculty.printFirstNStudents(10);
//            System.out.println("COURSES");
//            faculty.printCourses();
//        }
//    }
//}
