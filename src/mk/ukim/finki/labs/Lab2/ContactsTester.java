package mk.ukim.finki.labs.Lab2;

import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.*;

abstract class Contact{
    private int date;

    public Contact(String date){
        String[] parts = date.split("-");
        this.date = Integer.parseInt(parts[0] + parts[1] + parts[2]);
    }

    public boolean isNewerThan(Contact c) {
        return this.date>=c.date;
    }

    public abstract String getType();

    public int compareTo(Contact contact1) {
        return this.isNewerThan(contact1) ? 1 : -1;
    }
}

class EmailContact extends Contact {

    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    @Override
    public String toString() {
        return   "\"" + email + '\"';
    }
}

class PhoneContact extends Contact{

    public enum Operator{ VIP, ONE, TMOBILE}

    private String phone;

    public PhoneContact(String date, String phone)  {
        super(date);
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public Operator getOperator(){
        String prefix = getPhone().substring(0,3);
        if(prefix.equals("070") || prefix.equals("071") || prefix.equals("072")) return Operator.TMOBILE;
        else if(prefix.equals("075") || prefix.equals("076")) return Operator.ONE;
        else return Operator.VIP;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return   "\"" + phone + '\"';
    }
}

class Student{
    private List<Contact> contacts;
    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new ArrayList<>();
    }

    public void addEmailContact(String date, String email)  {
        contacts.add(new EmailContact(date,email));

    }

    public void addPhoneContact(String date, String phone)  {
        contacts.add(new PhoneContact(date, phone));
    }

    public Contact[] getEmailContacts() {
        return contacts.stream().filter(r->r.getType().equals("Email")).toArray(Contact[]::new);
    }

    public Contact[] getPhoneContacts() {
        return contacts.stream().filter(r->r.getType().equals("Phone")).toArray(Contact[]::new);
    }

    public String getCity(){
        return city;
    }

    public String getFullName(){
        return firstName.toUpperCase() + " " + lastName.toUpperCase();
    }

    public long getIndex(){
        return index;
    }

    public Contact getLatestContact() {
        return this.contacts.stream().max(Contact::compareTo).orElse(null);
    }

    public int numOfContacts(){
        return this.contacts.size();
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\"telefonskiKontakti\":[");
        Contact[] phones = this.getPhoneContacts();
        if(phones.length != 0) {
            for(int i=0;i<phones.length;i++) {
                if(i == phones.length-1)
                    s.append(phones[i].toString()).append("], ");
                else
                    s.append(phones[i].toString()).append(", ");
            }
        }
        else
            s.append("], ");
        s.append("\"emailKontakti\":[");
        Contact[] emails = this.getEmailContacts();
        if(emails.length != 0) {
            for(int i=0;i<emails.length;i++) {
                if(i == emails.length-1)
                    s.append(emails[i].toString()).append("]}");
                else
                    s.append(emails[i].toString()).append(", ");
            }
        }
        else
            s.append("]}");

        return  "{"+"\"ime\":" +"\""+ firstName+ "\"" +
                ", \"prezime\":" + "\"" + lastName+ "\"" +
                ", \"vozrast\":" +  age +
                ", \"grad\":" + "\"" + city + "\"" +
                ", \"indeks\":" +  index +", "  + s ;


    }
}

class Faculty{
    private String name;
    private List<Student> students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = Arrays.asList(students);
    }

    public int countStudentsFromCity(String city){
        return (int)students.stream().filter(r->r.getCity().equals(city)).count();
    }

    public Student getStudent(long index){
        return students.stream().filter(r->r.getIndex() == index).findFirst().orElse(null);
    }

    public double getAverageNumberOfContacts(){
        double totalContacts = this.students.stream().mapToInt(Student::numOfContacts).sum();
        return totalContacts/this.students.size();
    }

    public Student getStudentWithMostContacts(){
        return students.stream().max(Comparator.comparing(Student::numOfContacts).thenComparing(Student::getIndex)).orElse(null);
    }

    @Override
    public String toString() {
        return  "{"+"\"fakultet\":" + "\"" + name + '\"' +
                ", \"studenti\":" + students + "}";
    }
}
public class ContactsTester {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
