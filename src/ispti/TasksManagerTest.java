package ispti;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
class Task{
    String category;
    String taskName;
    String description;
    LocalDateTime deadline;
    int priority;

    public Task(String category, String taskName, String description) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
    }

    public Task(String category, String taskName, String description, int priority) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
    }

    public Task(String category, String taskName, String description, LocalDateTime deadline) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String category, String taskName, String description, LocalDateTime deadline, int priority) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + taskName +
                ", description='" + description  +
                ", deadline=" + deadline +
                ", priority=" + priority +
                '}';
    }

    public Task(String category) {
        this.category = category;
    }
}

class TaskManager{
    List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void readTasks(InputStream in) {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] parts = line.split(",");
            if(parts.length==4){
                if(parts[3].length()==1){
                    this.taskList.add(new Task(parts[0],parts[1],parts[2],Integer.parseInt(parts[3])));
                }else {
                    this.taskList.add(new Task(parts[0],parts[1],parts[2],LocalDateTime.parse(parts[3])));
                }
            }else if(parts.length==5){
                this.taskList.add(new Task(parts[0],parts[1],parts[2],LocalDateTime.parse(parts[3]),Integer.parseInt(parts[4])));
            }else{
                this.taskList.add(new Task(parts[0],parts[1],parts[2]));
            }
        }
    }

    public void printTasks(PrintStream out, boolean category, boolean priority) {
        PrintWriter pw = new PrintWriter(out);

            this.taskList.stream().sorted(Comparator.comparing(Task::getCategory).thenComparing(Task::getPriority)).forEach(pw::println);
            pw.close();

    }
}
